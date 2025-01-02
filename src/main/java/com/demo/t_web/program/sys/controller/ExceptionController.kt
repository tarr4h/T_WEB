package com.demo.t_web.program.sys.controller

import com.demo.t_web.program.sys.service.ExceptionService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class ExceptionController(
    private var service : ExceptionService
) {
    @ExceptionHandler(Exception::class)
    fun exception(e : Exception, request : HttpServletRequest, response : HttpServletResponse) : ResponseEntity<String> {
        service.addExceptionHst(e, request)
        return ResponseEntity.ok().body("error occured")
    }
}