package com.demo.t_web.comn.filter;

import com.demo.t_web.comn.enums.JwtEnum;
import com.demo.t_web.comn.exception.JwtValidateException;
import com.demo.t_web.comn.util.JwtUtil;
import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.login.service.LoginService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <pre>
 * com.demo.t_web.comn.filter.JwtAuthenticationFilter
 *   - JwtAuthenticationFilter.java
 * </pre>
 *
 * @author : tarr4h
 * @className : JwtAuthenticationFilter
 * @description :
 * @date : 12/12/24
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(LoginService loginService, JwtUtil jwtUtil) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String userId = null;
        String jwtToken = null;

        // use Cookie
        Cookie cookie = Utilities.getCookie(request, "jwt");
        if(cookie != null){
            jwtToken = cookie.getValue();

            try {
                userId = jwtUtil.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                throw new JwtValidateException(JwtEnum.NOT_SUITABLE_TOKEN.getValue(), e);
            } catch (ExpiredJwtException e) {
                throw new JwtValidateException(JwtEnum.JWT_TOKEN_EXPIRED.getValue(), e);
            } catch (Exception e){
                log.error(JwtEnum.JWT_EXCEPTION.getValue(), e);
            }
        } else {
            throw new JwtValidateException(JwtEnum.JWT_TOKEN_NOT_FOUND.getValue());
        }

//        header 통해 token 전송 시
//        final String requestHeader = request.getHeader(JwtEnum.HEADER_STRING.getValue());
//        if(requestHeader != null && requestHeader.startsWith(JwtEnum.HEADER_PRE.getValue())){
//            jwtToken = requestHeader.substring(7);
//
//            try {
//                userId = jwtUtil.extractUsername(jwtToken);
//            } catch (IllegalArgumentException e) {
//                throw new JwtValidateException(JwtEnum.NOT_SUITABLE_TOKEN.getValue(), e);
//            } catch (ExpiredJwtException e) {
//                throw new JwtValidateException(JwtEnum.JWT_TOKEN_EXPIRED.getValue(), e);
//            } catch (Exception e){
//                log.error(JwtEnum.JWT_EXCEPTION.getValue(), e);
//            }
//        } else {
//            throw new JwtValidateException(JwtEnum.JWT_TOKEN_NOT_FOUND.getValue());
//        }

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = loginService.selectUser(userId);

            if(jwtUtil.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
