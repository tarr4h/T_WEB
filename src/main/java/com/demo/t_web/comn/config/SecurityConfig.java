package com.demo.t_web.comn.config;

import com.demo.t_web.comn.filter.JwtAuthenticationFilter;
import com.demo.t_web.comn.filter.JwtExceptionHandlerFilter;
import com.demo.t_web.program.comn.service.ComnService;
import com.demo.t_web.program.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <pre>
 * com.demo.t_web.comn.config.SecurityConfig
 *   - SecurityConfig.java
 * </pre>
 *
 * @author : tarr4h
 * @className : SecurityConfig
 * @description :
 * @date : 12/11/24
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] uris = new String[]{"/comn/**", "/awsMas"};

    @Autowired
    LoginService loginService;

    @Autowired
    ComnService comnService;

    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(loginService);
    }

    public JwtExceptionHandlerFilter jwtExceptionHandlerFilter(){
        return new JwtExceptionHandlerFilter(comnService);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(uris)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authorizeHttpRequests(auth -> auth.requestMatchers(uris.toArray(String[]::new)).permitAll().anyRequest().authenticated())
            .authorizeHttpRequests(auth -> auth.requestMatchers(uris).permitAll())
        ;
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults()) /// custom status 사용 시 preflight에서 cors 방지
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .addFilterBefore(jwtExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}