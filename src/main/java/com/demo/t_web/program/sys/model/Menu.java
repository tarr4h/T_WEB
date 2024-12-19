package com.demo.t_web.program.sys.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "id")
    private Long id;

    @Column(name = "menu_id")
    private String menuId;

    @Column(name = "menu_nm")
    private String menuNm;
}
