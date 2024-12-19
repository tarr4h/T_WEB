package com.demo.t_web.program.user.service;

import com.demo.t_web.program.sys.model.Menu;
import com.demo.t_web.program.user.repository.MenuRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * com.demo.t_web.program.user.service.MenuService
 *   - MenuService.java
 * </pre>
 *
 * @author : tarr4h
 * @className : MenuService
 * @description :
 * @date : 12/19/24
 */
@Service
@Slf4j
@AllArgsConstructor
public class MenuService {

    private MenuRepository menuRepository;

    public String addMenu(Menu menu){
        menuRepository.save(menu);
        menu.setMenuId("M_" + String.format("%05d", menu.getId()));

        return "123123";
    }
}
