package com.demo.t_web.program.user.model

import com.demo.t_web.program.sys.model.BaseVo
import com.demo.t_web.program.sys.model.Menu
import com.demo.t_web.program.user.enums.ADP_ROLE
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import kotlin.jvm.Transient

@Entity(name = "adp_user")
@Table(name = "adp_user")
data class User(

    @Id
    @Column(name = "id", length = 20, nullable = false)
    var id : String,

    @Column(name = "password", length = 100, nullable = false)
    var pwd : String,

    @Column(name = "name", length = 20, nullable = false)
    var name : String,

    @Column(name = "phone")
    var phone : String,

    @Column(name = "email")
    var email : String,

    @Column(name = "join_dt")
    @Temporal(TemporalType.DATE)
    var joinDt : Date?,

    @Column(name = "last_login_dt")
    @Temporal(TemporalType.TIMESTAMP)
    var lastLoginDt : Date,

    @Transient
    var loginYn : Boolean,

    @Transient
    var token : String,

    @Transient
    var userMenu : List<Menu>,

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = [CascadeType.ALL])
    @JsonManagedReference
    val roles : MutableList<UserRole>?

    ) : BaseVo(), UserDetails {

    override fun prePersist(){
        super.prePersist()
        joinDt ?: Date()
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles ?: mutableListOf()
    }

    override fun getUsername(): String {
        return id
    }

    override fun getPassword(): String {
        return pwd
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun addRole(roleId : String, roleName : String){
        roles ?: mutableListOf()
        if(roles!!.stream().noneMatch { userRole -> userRole.id?.roleId.equals(roleId) }){
            roles.add(UserRole(this, roleId, roleName))
        }
    }

    fun addRoles(adpRoles : List<ADP_ROLE>){
        adpRoles.forEach{role -> addRole(role.id, role.roleName)}
    }


    fun asUser() : UserDto {
        return UserDto(
            id = id,
            name = name,
            phone = phone,
            email = email,
            joinDt = joinDt,
            lastLoginDt = lastLoginDt,
            roles = roles,
            userMenu = userMenu
        )
    }
}
