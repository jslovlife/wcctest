package com.wcc.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.wcc.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class APIInterceptor implements HandlerInterceptor {
    
    private static final String USERNAME_HEADER = "x-username";
    private static final String PASSWORD_HEADER = "x-password";

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler) throws Exception {

        log.info("APIInterceptor preHandle called, request: {}", request);
        
        // Check if the required headers are present
        String username = request.getHeader(USERNAME_HEADER);
        String password = request.getHeader(PASSWORD_HEADER);

        log.info("APIInterceptor preHandle called, username: {}", username);

        if (username == null || password == null) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("""
                {"error": "Required headers are missing: x-username and x-password"}
                """);
            return false;
        }

        if (!userService.validateUser(username, password)) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("""
                {"error": "Invalid username or password"}
                """);
            return false;
        }

        return true;
    }
}
