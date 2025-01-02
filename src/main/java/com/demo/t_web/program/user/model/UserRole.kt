package com.demo.t_web.program.user.model

import com.demo.t_web.program.sys.model.BaseVo
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable

@Entity
@Table(name = "adp_user_roles")
data class UserRole(

    @EmbeddedId
    var id : RoleId? = null,

    @Column(name = "role_name")
    var roleName: String = "",

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonBackReference
    var user: User? = null,
) : BaseVo(), GrantedAuthority {

    constructor(user: User, roleId: String, roleName: String) : this() {
        setId(roleId, user.id)
        this.roleName = roleName
        this.user = user
    }

    override fun getAuthority() = id?.roleId

    companion object {
        @Embeddable
        data class RoleId(
            @Column(name = "role_id")
            var roleId : String = "",
            @Column(name = "user_id")
            var userId : String = "",
        ) : Serializable
    }

    fun setId(roleId: String, userId: String){
        this.id = RoleId(roleId, userId)
    }
}
