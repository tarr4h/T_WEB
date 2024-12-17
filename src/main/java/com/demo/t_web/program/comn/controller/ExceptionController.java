package com.demo.t_web.program.comn.controller;

import com.demo.t_web.comn.exception.JwtValidateException;
import com.demo.t_web.program.comn.service.ComnService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <pre>
 * com.demo.t_web.program.comn.controller.ExceptionController
 *   - ExceptionController.java
 * </pre>
 *
 * @author : 한태우
 * @className : ExceptionController
 * @description :
 * @date : 8/4/24
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @Autowired
    ComnService service;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("any exceptions");
        service.addExceptionHst(e, request);
        return ResponseEntity.ok("error occured");
    }

    @ExceptionHandler(JwtValidateException.class)
    public ResponseEntity<String> jwtException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("jwt exception", e);
        service.addExceptionHst(e, request);
        return ResponseEntity.ok("jwt error occured");
    }
}
