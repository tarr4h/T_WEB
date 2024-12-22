package com.demo.t_web.program.sys.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * <pre>
 * com.demo.t_web.program.sys.model.MenuRoleRel
 *   - MenuRoleRel.java
 * </pre>
 *
 * @author : tarr4h
 * @className : MenuRoleRel
 * @description :
 * @date : 12/20/24
 */
@Entity
@Table(name = "adp_menu_role_rel")
@Getter
@Setter
@NoArgsConstructor
public class MenuRoleRel extends BaseVo {

    @EmbeddedId
    private MenuRoleId id;

    @Column(name = "menu_cd")
    private String menuCd;

    @ManyToOne
    @MapsId("menuId")
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private Menu menu;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class MenuRoleId implements Serializable{

        public MenuRoleId(Long menuId, String roleId){
            this.menuId = menuId;
            this.roleId = roleId;
        }

        @Column(name = "menu_id")
        private Long menuId;

        @Column(name = "role_id")
        private String roleId;
    }

    public void setId(Long menuId, String roleId){
        this.id = new MenuRoleId(menuId, roleId);
    }

    public MenuRoleRel(Menu menu, String roleId){
        setId(menu.getId(), roleId);
        this.menu = menu;
//        this.userRole = userRole;
    }

    @Override
    public void prePersist(){
        super.prePersist();
        this.menuCd = menu.getMenuCd();
    }
}
