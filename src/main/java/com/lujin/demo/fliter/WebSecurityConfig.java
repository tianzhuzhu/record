package com.lujin.demo.fliter;

import com.lujin.demo.config.JwtProperties;
import com.lujin.demo.pojo.User;
import com.lujin.demo.service.AuthService;
import com.lujin.demo.service.UserService;
import com.lujin.demo.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yStar on 2018/2/23 16:36:16
 * 拦截器配置
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
    @Autowired
    UserService userService ;
    @Autowired
    AuthService authService;
    public static final String SESSION_KEY = "name";
    @Autowired
    JwtProperties pros;
    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        //排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login");
        addInterceptor.excludePathPatterns("/login.html");
        addInterceptor.excludePathPatterns("/tologin");
        //拦截配置
        addInterceptor.addPathPatterns("/*");
        addInterceptor.addPathPatterns("/");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            boolean b=false;
            String token = CookieUtils.getCookieValue(request, pros.getCookieName());
            System.out.println(StringUtils.isBlank(token));
                    b= authService.checkToken(token, request, response);


            if(b==false) {
                try {
                    request.getRequestDispatcher("/tologin").forward(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            }
            return b;

        }
    }
}