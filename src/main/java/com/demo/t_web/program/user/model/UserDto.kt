package com.demo.t_web.program.user.model

import com.demo.t_web.program.sys.model.Menu
import java.util.*

data class UserDto(
    val id : String,
    val name : String,
    val phone : String,
    val email : String,
    val joinDt : Date?,
    val lastLoginDt : Date?,
    val roles : MutableList<UserRole>?,
    val userMenu : List<Menu>?
)
