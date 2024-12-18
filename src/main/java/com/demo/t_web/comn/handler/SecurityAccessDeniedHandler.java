package com.demo.t_web.comn.handler;

import com.demo.t_web.comn.enums.ErrorType;
import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.sys.service.ExceptionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <pre>
 * com.demo.t_web.comn.handler.SecurityAccessDeniedHandler
 *   - SecurityAccessDeniedHandler.java
 * </pre>
 *
 * @author : tarr4h
 * @className : SecurityAccessDeniedHandler
 * @description :
 * @date : 12/18/24
 */
@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ExceptionService exceptionService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        exceptionService.addExceptionHst(ex, request);
        Utilities.sendHandleError(response, ErrorType.SECURITY_ERR);
    }
}
