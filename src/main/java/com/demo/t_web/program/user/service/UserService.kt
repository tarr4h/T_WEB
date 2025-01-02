package com.demo.t_web.program.user.service

import com.demo.t_web.comn.model.Tmap
import com.demo.t_web.comn.util.JwtUtil
import com.demo.t_web.comn.util.Utilities
import com.demo.t_web.program.sys.model.Menu
import com.demo.t_web.program.user.enums.ADP_ROLE
import com.demo.t_web.program.user.model.User
import com.demo.t_web.program.user.model.UserDto
import com.demo.t_web.program.user.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService (
    private val userRepository: UserRepository,
    private val menuService: MenuService,
    private val jwtUtil: JwtUtil
){
    private val log = KotlinLogging.logger {}

    fun join(user: User): Tmap {
        user.addRoles(ADP_ROLE.getRoles(ADP_ROLE.ADMIN, ADP_ROLE.USER))
        userRepository.save(user)
        return Tmap().direct("success", true)
    }

    @Transactional
    fun login(user : User) : Tmap{
        log.debug { "user login requested -------------------------" }
        val findUser : Optional<User> = userRepository.findById(user.id)
        var loginBool = false
        if(findUser.isPresent){
            log.debug { "lastLoginDt ? ${findUser.get().lastLoginDt}" }
            loginBool = true
            val token = jwtUtil.generateToken(findUser.get())
            Utilities.addCookie("jwt", token)

            findUser.get().lastLoginDt = Date()
            findUser.get().pwd = "holololo"
            log.debug { "changed LastLoginDt ? ${findUser.get().lastLoginDt}" }
//            userRepository.save(findUser.get())
//            var user : User = User()
        }

        return Tmap().direct("success", loginBool)
    }

    fun logout() : Tmap {
        Utilities.deleteCookie("jwt")
        return Tmap().direct("success", true)
    }

    fun checkLogin() : Boolean {
        var bool = false
        if(jwtUtil.checkTokenExist()){
            if(!jwtUtil.isTokenExpired){
                bool = true
            }
        }

        return bool
    }

    fun selectUser() : UserDto {
        val userId : String = jwtUtil.userIdFromToken
        var user : User = selectUser(userId)
        user.userMenu = menuService.selectUserMenuList(user)
        return user.asUser()
    }

    fun selectUser(userId : String) : User {
        return userRepository.findById(userId).orElse(null)
    }
}