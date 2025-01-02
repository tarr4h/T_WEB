package com.demo.t_web.program.user.repository

import com.demo.t_web.program.sys.model.Menu
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<Menu, Long>