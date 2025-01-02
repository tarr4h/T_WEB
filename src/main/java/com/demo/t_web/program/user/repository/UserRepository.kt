package com.demo.t_web.program.user.repository

import com.demo.t_web.program.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>