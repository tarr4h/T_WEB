package com.demo.t_web.program.sys.model;

import com.demo.t_web.program.user.enums.ADP_ROLE;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.sys.model.Menu
 *   - Menu.java
 * </pre>
 *
 * @author : tarr4h
 * @className : Menu
 * @description :
 * @date : 12/19/24
 */
@Entity
@Table(name = "adp_menu")
@SequenceGenerator(name = "menu_generator", sequenceName = "menu_seq", initialValue = 1, allocationSize = 1)
@Getter
@Setter
public class Menu extends BaseVo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_generator")
    @Column(name = "id", length = 20, nullable = false)
    @Order(1)
    private Long id;

    @Column(name = "menu_cd")
    private String menuCd;

    @Column(name = "menu_nm")
    private String menuNm;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MenuRoleRel> menuRoleRels = new ArrayList<>();

    public void addRel(String roleId){
        if(this.menuRoleRels == null){
            this.menuRoleRels = new ArrayList<>();
        }
        if(this.menuRoleRels.stream().noneMatch(menuRel -> menuRel.getId().getRoleId().equals(roleId))){
            this.menuRoleRels.add(new MenuRoleRel(this, roleId));
        }
    }

    public void addRels(List<ADP_ROLE> roles){
        roles.forEach(role -> addRel(role.getId()));
    }
}
