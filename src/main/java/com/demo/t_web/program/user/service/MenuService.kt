package com.demo.t_web.program.user.service

import com.demo.t_web.comn.util.JwtUtil
import com.demo.t_web.program.sys.model.Menu
import com.demo.t_web.program.sys.model.MenuRoleRel
import com.demo.t_web.program.user.enums.ADP_ROLE
import com.demo.t_web.program.user.model.User
import com.demo.t_web.program.user.model.UserRole
import com.demo.t_web.program.user.repository.MenuRepository
import com.demo.t_web.program.user.repository.MenuRoleRelRepository
import com.demo.t_web.program.user.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class MenuService (
    private val menuRepository: MenuRepository,
    private val menuRoleRelRepository: MenuRoleRelRepository,
    private val userRepository: UserRepository,
    private val jwtUtil : JwtUtil
) {
    private val log = KotlinLogging.logger {}

    fun addMenu(menu : Menu) : String {
        menuRepository.save(menu)
        menu.menuCd = "M_" + String.format("%05d", menu.id)
        menu.addRels(ADP_ROLE.getRolesByLevel(2))

        return "123123"
    }

    fun selectUserMenuList(user: User): List<Menu> {
        return user.roles!!.stream()
            .map{ role: UserRole -> menuRoleRelRepository.findByIdRoleId(role.id!!.roleId) }
            .flatMap { obj: List<MenuRoleRel> -> obj.stream() }
            .map<Menu>(MenuRoleRel::menu)
            .toList()
    }

    fun selectMenuList() : List<Menu> {
        val login = jwtUtil.checkTokenExist()
        if(login){
            val user = userRepository.findById(jwtUtil.userIdFromToken)
            if(user.isPresent){
                return selectUserMenuList(user.get())
            }
        }

        return ADP_ROLE.getRolesByLevel(1)
            .stream()
            .map{ role -> menuRoleRelRepository.findByIdRoleId(role.id)}
            .flatMap {obj : List<MenuRoleRel> -> obj.stream()}
            .map<Menu>(MenuRoleRel::menu)
            .toList()
    }
}