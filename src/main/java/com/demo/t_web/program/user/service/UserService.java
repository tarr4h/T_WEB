package com.demo.t_web.program.user.service;

import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.comn.util.JwtUtil;
import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.user.model.User;
import com.demo.t_web.program.user.model.UserDto;
import com.demo.t_web.program.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * <pre>
 * com.demo.t_web.program.login.service.LoginService
 *   - LoginService.java
 * </pre>
 *
 * @author : tarr4h
 * @className : LoginService
 * @description :
 * @date : 12/12/24
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    public Tmap login(User user) {
        log.debug("login run ---------------");
//        user.addRoles(ADP_ROLE.getRoles(ADP_ROLE.ADMIN, ADP_ROLE.USER));
//        user.addRoles(ADP_ROLE.getRoles(ADP_ROLE.USER));
//        userRepository.save(user);

        Optional<User> findUser = userRepository.findById(user.getId());
        boolean loginBool = false;
        if (findUser.isPresent()) {
            user = findUser.get();
            loginBool = true;
            String token = jwtUtil.generateToken(user);
            Utilities.addCookie("jwt", token);

            user.setLastLoginDt(new Date());
        }
        return new Tmap().direct("success", loginBool);
    }

    public UserDto selectUser(){
        String userId = jwtUtil.getUserIdFromToken();
        log.debug("userId ? {}", userId);
        UserDto user = selectUser(userId).asUser();
        log.debug("user ? {}", user.getId());
        return user;
    }

    public User selectUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }
}