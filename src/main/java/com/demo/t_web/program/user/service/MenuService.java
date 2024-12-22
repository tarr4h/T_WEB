package com.demo.t_web.program.user.service;

import com.demo.t_web.comn.util.JwtUtil;
import com.demo.t_web.program.sys.model.Menu;
import com.demo.t_web.program.sys.model.MenuRoleRel;
import com.demo.t_web.program.user.enums.ADP_ROLE;
import com.demo.t_web.program.user.model.User;
import com.demo.t_web.program.user.repository.MenuRepository;
import com.demo.t_web.program.user.repository.MenuRoleRelRepository;
import com.demo.t_web.program.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    private final JwtUtil jwtUtil;
    private MenuRepository menuRepository;
    private MenuRoleRelRepository menuRoleRelRepository;
    private UserRepository userRepository;

    public String addMenu(Menu menu){
        menuRepository.save(menu);
        menu.setMenuCd("M_" + String.format("%05d", menu.getId()));
        menu.addRels(ADP_ROLE.getRolesByLevel(2));

        return "123123";
    }

    public List<Menu> selectUserMenuList(User user) {
        return user.getRoles().stream()
                .map(role -> menuRoleRelRepository.findByIdRoleId(role.getId().getRoleId()))
                .flatMap(List::stream)
                .map(MenuRoleRel::getMenu)
                .toList();
    }

    public List<Menu> selectMenuList(){
        boolean login = jwtUtil.checkTokenExist();
        if(login){
            Optional<User> user = userRepository.findById(jwtUtil.getUserIdFromToken());
            if(user.isPresent()){
                return selectUserMenuList(user.get());
            }
        }

        return ADP_ROLE.getRolesByLevel(1).stream()
                .map(role -> menuRoleRelRepository.findByIdRoleId(role.getId()))
                .flatMap(List::stream)
                .map(MenuRoleRel::getMenu)
                .toList();
    }
}
