package com.demo.t_web.program.login.service;

import com.demo.t_web.comn.util.JwtUtil;
import com.demo.t_web.program.login.model.User;
import com.demo.t_web.program.login.model.UserRoles;
import com.demo.t_web.program.login.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class LoginService {

    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    public Object login(User user) {
        log.debug("login run ---------------");
//        user.setRole(ROLE.USER);
        userRepository.save(user);
        long count = userRepository.count();
        Optional<User> findUser = userRepository.findById(user.getId());
        log.debug("--- count = ? {}", count);

        List<UserRoles> roles = findUser.get().getRoles();
        for(UserRoles role : roles) {
            log.debug("role : {}", role.getRoleName());
        }

        String token = jwtUtil.generateToken(findUser.get());
        return token;
    }

    public User selectUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }
}
