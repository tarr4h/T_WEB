package com.demo.t_web.program.user.repository

import com.demo.t_web.program.sys.model.MenuRoleRel
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRoleRelRepository : JpaRepository<MenuRoleRel, MenuRoleRel.Companion.MenuRoleId> {

    fun findByIdRoleId(roleId : String) : List<MenuRoleRel>
}