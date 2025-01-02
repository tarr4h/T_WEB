package com.demo.t_web.program.user.controller

import com.demo.t_web.comn.model.Tmap
import com.demo.t_web.comn.util.Utilities
import com.demo.t_web.program.user.model.User
import com.demo.t_web.program.user.service.UserService
import com.demo.t_web.program.user.service.UserServiceOld
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController (
    private val service : UserService
) {

    @PostMapping("/join")
    fun join(@RequestBody user: User): ResponseEntity<Tmap> {
        return ResponseEntity.ok(service.join(user))
    }

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<Tmap> {
        return ResponseEntity.ok(service.login(user))
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<Tmap> {
        return ResponseEntity.ok(service.logout())
    }

    @GetMapping("/checkLogin")
    fun checkLogin(): ResponseEntity<Boolean> {
        return ResponseEntity.ok(service.checkLogin())
    }

    @PostMapping("/selectUser")
    fun selectUser(): ResponseEntity<*> {
        return Utilities.retValue(service.selectUser())
    }

    @GetMapping("/jwtTest")
    fun jwtTest(@RequestParam params: Map<String?, String?>?): ResponseEntity<String> {
        return ResponseEntity.accepted().body("asdfase")
    }
}