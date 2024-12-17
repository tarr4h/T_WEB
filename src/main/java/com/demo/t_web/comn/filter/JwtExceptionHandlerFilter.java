package com.demo.t_web.comn.filter;

import com.demo.t_web.comn.exception.JwtValidateException;
import com.demo.t_web.program.comn.service.ComnService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * com.demo.t_web.comn.filter.ExceptionHandlerFilter
 *   - ExceptionHandlerFilter.java
 * </pre>
 *
 * @author : tarr4h
 * @className : ExceptionHandlerFilter
 * @description :
 * @date : 12/14/24
 */
@Component
@Slf4j
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private final ComnService comnService;

    public JwtExceptionHandlerFilter(ComnService comnService) {
        this.comnService = comnService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtValidateException e){
            handleError(request, response, e);
        }
    }

    public void handleError(HttpServletRequest request, HttpServletResponse response, Exception ex){
        response.setStatus(491);
        response.setContentType("application/json;charset=utf-8");
        comnService.addExceptionHst(ex, request);
        try {
            Map<String, String> obj = new HashMap<>();
            obj.put("errorType", "JWT_ERROR");
            obj.put("msg", ex.getMessage());
            String json = new ObjectMapper().writeValueAsString(obj);
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (Exception e){
            log.error("jwt exception handler ex : ", e);
        }
    }
}
