package com.demo.t_web.program.sys.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "adp_menu_role_rel")
data class MenuRoleRel(
    @EmbeddedId
    var id : MenuRoleId? = null,

    @Column(name = "menu_cd")
    var menuCd : String? = null,

    @ManyToOne
    @MapsId("menuId")
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    var menu : Menu? = null
) : BaseVo() {

    companion object {
        @Embeddable
        data class MenuRoleId(
            @Column(name = "menu_id")
            var menuId : Long? = null,
            @Column(name = "role_id")
            var roleId : String? = null
        ) : Serializable
    }

    fun setId(menuId : Long, roleId : String){
        id = MenuRoleId(menuId, roleId)
    }

    constructor(menu : Menu, roleId : String) :
            this(menu = menu) {
                setId(menu.id, roleId)
            }

    override fun prePersist() {
        super.prePersist()
        this.menuCd = menu?.menuCd
    }

}
