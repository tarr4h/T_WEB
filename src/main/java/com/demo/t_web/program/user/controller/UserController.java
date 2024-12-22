package com.demo.t_web.program.user.controller;

import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.user.model.User;
import com.demo.t_web.program.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/join")
    public ResponseEntity<Tmap> join(@RequestBody User user) {
        return ResponseEntity.ok(service.join(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Tmap> login(@RequestBody User user) {
        return ResponseEntity.ok(service.login(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<Tmap> logout() {
        return ResponseEntity.ok(service.logout());
    }

    @GetMapping("/checkLogin")
    public ResponseEntity<Boolean> checkLogin(){
        return ResponseEntity.ok(service.checkLogin());
    }

    @PostMapping("/selectUser")
    public ResponseEntity<?> selectUser() {
        return Utilities.retValue(service.selectUser());
    }

    @GetMapping("/jwtTest")
    public ResponseEntity<String> jwtTest(@RequestParam Map<String, String> params) {
        log.debug("jwt test- --- - - -- - - - -- ");
        return ResponseEntity.accepted().body("asdfase");
    }
}
