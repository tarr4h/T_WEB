package com.demo.t_web.program.user.service

import com.demo.t_web.program.user.model.User
import com.demo.t_web.program.user.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.math.log

@Component
class TestCom(
    userRepository: UserRepository
) {

    @Transactional
    fun test(user : User){
        println("test")
    }
}