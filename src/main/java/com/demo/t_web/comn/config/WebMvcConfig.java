package com.demo.t_web.comn.config;

import com.demo.t_web.comn.interceptor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <pre>
 * com.trv.comn.config.WebMvcConfig
 *  - WebMvcConfig.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : WebMvcConfig
 * @description :
 * @date : 2023-04-19
 */

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/login/**")
                .allowedOrigins("https://tarr4h.github.io", "https://localhost:3001")
//                .allowedHeaders("*")
                .allowedMethods("OPTIONS", "POST")
        ;

        registry.addMapping("/comn/**")
                .allowedOrigins("https://tarr4h.github.io", "https://localhost:3001")
//                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Custom-Header")
                .allowedMethods("*")
        ;

    }
}
