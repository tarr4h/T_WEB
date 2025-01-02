package com.demo.t_web.comn.config;

import com.demo.t_web.comn.filter.JwtAuthenticationFilter;
import com.demo.t_web.comn.filter.JwtExceptionHandlerFilter;
import com.demo.t_web.comn.handler.SecurityAccessDeniedHandler;
import com.demo.t_web.comn.util.JwtUtil;
import com.demo.t_web.program.user.enums.ADP_ROLE;
import com.demo.t_web.program.user.service.UserServiceOld;
import com.demo.t_web.program.sys.service.ExceptionService;
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
//@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] uris = new String[]{"/comn/**", "/awsMas/**"
            , "/user/login", "/user/join", "/user/checkLogin"
            , "/menu/selectMenuList"
            , "/ai/**"
            , "/memo/**"
    };

    @Autowired
    UserServiceOld userService;

    @Autowired
    ExceptionService exceptionService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private SecurityAccessDeniedHandler securityAccessDeniedHandler;

    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userService, jwtUtil);
    }

    public JwtExceptionHandlerFilter jwtExceptionHandlerFilter(){
        return new JwtExceptionHandlerFilter(exceptionService);
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
            .authorizeHttpRequests(auth ->
                    auth
                        .requestMatchers("/admin/**").hasAuthority(ADP_ROLE.ADMIN.getId())
                        .anyRequest().authenticated()
            )
            .exceptionHandling(e -> e.accessDeniedHandler(securityAccessDeniedHandler))
            .addFilterBefore(jwtExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}