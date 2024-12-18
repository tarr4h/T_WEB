package com.demo.t_web.comn.filter;

import com.demo.t_web.comn.enums.ErrorType;
import com.demo.t_web.comn.exception.JwtValidateException;
import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.sys.service.ExceptionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private final ExceptionService exceptionService;

    public JwtExceptionHandlerFilter(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
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
        exceptionService.addExceptionHst(ex, request);
        Utilities.sendHandleError(response, ErrorType.JWT_ERR, ex);
    }
}
