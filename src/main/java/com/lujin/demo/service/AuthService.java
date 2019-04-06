package com.lujin.demo.service;

import com.lujin.demo.bean.UserInfo;
import com.lujin.demo.config.JwtProperties;
import com.lujin.demo.mapper.UserMapper;
import com.lujin.demo.pojo.User;
import com.lujin.demo.utils.CookieUtils;
import com.lujin.demo.utils.JwtUtils;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtProperties prop;
    public String authentication(String username) {
        String token =null;
        User user = new User();
        user.setUsername(username);
        User record=new User();
        record=userMapper.selectOne(user);
        try {

            token = JwtUtils.
                    generateToken(new UserInfo(user.getId(), username), prop.getPrivateKey(), prop.getExpire());
        }
        catch (Exception e)
        {

            if (null==user)
                return null;
        }
        return token;
    }

    public boolean checkToken(String token, HttpServletRequest request,HttpServletResponse response) {
        if(StringUtils.isBlank(token)){
           return false;
        }
        //解析对错
        try {
            UserInfo info = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
//            //刷新token
//            String newToken = JwtUtils.generateToken(info, prop.getPrivateKey(), prop.getExpire());
//            CookieUtils.setCookie(request, response, prop.getCookieName(),
//                    token, prop.getCookieMaxAge(), true);
//
//            return true;//
            JwtUtils.getInfoFromToken(token, this.prop.getPublicKey());
            return true;
        } catch (Exception e) {

            return false;

        }
    }

    public UserInfo getUserInfo(String token) {
        try {
            return JwtUtils.getInfoFromToken(token, prop.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
