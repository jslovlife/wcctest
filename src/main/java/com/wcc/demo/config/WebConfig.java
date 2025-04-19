package com.wcc.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    APIInterceptor apiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor)
            .addPathPatterns("/api/**") // Only protect "/api/**" endpoints
            .excludePathPatterns("/api/user/**"); // Exclude "/api/user/**" endpoints
    }
}