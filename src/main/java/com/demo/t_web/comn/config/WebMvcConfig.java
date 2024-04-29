package com.demo.t_web.comn.config;

import com.demo.t_web.comn.interceptor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
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
        log.debug("addInterCeptor*****");
        registry.addInterceptor(new LogInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**");
    }
}
