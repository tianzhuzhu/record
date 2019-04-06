package com.lujin.demo.controller;

import com.lujin.demo.bean.UserInfo;
import com.lujin.demo.config.JwtProperties;
import com.lujin.demo.pojo.User;
import com.lujin.demo.service.AuthService;
import com.lujin.demo.service.UserService;
import com.lujin.demo.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.ognl.ASTList;
import org.springframework.beans.factory.annotation.Autowired;
import com.lujin.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.persistence.GeneratedValue;
import javax.security.auth.login.LoginContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtProperties prop;

    @RequestMapping("login")
    public String  queryUser(
            ModelMap model, @Valid @ModelAttribute(value = "user") User user, HttpServletRequest request, HttpServletResponse response){
        String go=new String();
//
       int count= userService.ckeckuser(user);
        if(count>0){
//            HttpSession session = request.getSession();
//            session.setAttribute("user",user);
//            return "index";

            String token = this.authService.authentication(user.getUsername());
            System.out.println(token.toString());
            if(StringUtils.isBlank(token))
                go= "login";
            else
            {
                CookieUtils.setCookie(request, response, prop.getCookieName(),
                        token, prop.getCookieMaxAge(), true);
                go="home";
            }
        }
        else
        {
            go= "login";
        }
        return go;
    }
    @RequestMapping("toRegister")
    public String toRegister(){
        return  "register";
    }
    @ResponseBody
    @PostMapping("checkUsermame")
    public boolean checkUserByUsername(@Param("username") String username){
//        System.out.print(username);
//        System.out.println("1");
        boolean b = userService.ckeckHaveNotuser(username);
        System.out.println(b);
        return b;


    }
    @PostMapping("registy")
    public String registy(ModelMap model, User user, HttpServletRequest request,HttpServletResponse response, BindingResult result ){
        if(result.hasErrors()){
            String error = result.getFieldErrors().stream()
                    .map(e->e.getDefaultMessage()).collect(Collectors.joining(" | "));
            throw new RuntimeException(error);
        }

        this.userService.register(user);
        String token = this.authService.authentication(user.getUsername());
        CookieUtils.setCookie(request, response, prop.getCookieName(),
                token, prop.getCookieMaxAge(), true);
        return "home";


    }

    @ResponseBody
    @RequestMapping("check")
    public UserInfo CheckUserInfo(HttpServletRequest request, HttpServletResponse response){
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
//        System.out.println("check");

        UserInfo userInfo = authService.getUserInfo(token);
        CookieUtils.setCookie(request, response, "username",userInfo.getUsername() );
        return userInfo;
    }

}
