package com.demo.t_web.program.sys.model

import com.demo.t_web.program.user.enums.ADP_ROLE
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.springframework.core.annotation.Order

@Entity
@Table(name = "adp_menu")
@SequenceGenerator(name = "menu_generator", sequenceName = "menu_seq", initialValue = 1, allocationSize = 1)
data class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_generator")
    @Column(name = "id", length = 20, nullable = false)
    @Order(1)
    var id : Long = 0L,

    @Column(name = "menu_cd")
    var menuCd : String? = "",

    @Column(name = "menu_nm")
    var menuNm : String,

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu", cascade = [CascadeType.ALL])
    @JsonManagedReference
    var menuRoleRels : MutableList<MenuRoleRel>? = mutableListOf()

) : BaseVo(){

    fun addRel(roleId : String) {
        if(menuRoleRels!!.stream().noneMatch { menuRel -> menuRel.id?.roleId.equals(roleId) }){
            menuRoleRels!!.add(MenuRoleRel(this, roleId))
        }
    }

    fun addRels(roles : List<ADP_ROLE>){
        roles.forEach{role -> addRel(role.id)}
    }
}
