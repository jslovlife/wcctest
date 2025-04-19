package com.wcc.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.wcc.demo.service.UserService;
import com.wcc.demo.model.enums.ErrorEnum;
import com.wcc.demo.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class APIInterceptor implements HandlerInterceptor {
    
    private static final String USERNAME_HEADER = "x-username";
    private static final String PASSWORD_HEADER = "x-password";

    @Autowired
    UserService userService;
    
    @Autowired
    ObjectMapper objectMapper;

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
            return returnError(response, HttpServletResponse.SC_UNAUTHORIZED, 
                    ErrorEnum.REQUIRED_HEADERS_MISSING);
        }

        if (!userService.validateUser(username, password)) {
            return returnError(response, HttpServletResponse.SC_UNAUTHORIZED, 
                    ErrorEnum.INVALID_USERNAME_OR_PASSWORD);
        }

        return true;
    }
    
    /**
     * Helper method to return a standardized error response
     * 
     * @param response The HttpServletResponse
     * @param status The HTTP status code
     * @param errorEnum The ErrorEnum to use
     * @return false to indicate the request should not continue
     * @throws Exception if there's an error writing the response
     */
    private boolean returnError(HttpServletResponse response, int status, 
                              ErrorEnum errorEnum) throws Exception {
        response.setContentType("application/json");
        response.setStatus(status);
        ErrorResponse errorResponse = new ErrorResponse(errorEnum.getCode(), errorEnum.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        return false;
    }
}
