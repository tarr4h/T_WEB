package com.demo.t_web.program.login.controller;

import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.login.model.User;
import com.demo.t_web.program.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * com.demo.t_web.program.login.controller.LoginController
 *   - LoginController.java
 * </pre>
 *
 * @author : tarr4h
 * @className : LoginController
 * @description :
 * @date : 12/12/24
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    LoginService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        log.debug("login request");
        return Utilities.retValue(service.login(user));
    }
}
