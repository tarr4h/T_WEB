package com.demo.t_web.program.login.service;

import com.demo.t_web.comn.util.JwtUtil;
import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.login.model.User;
import com.demo.t_web.program.login.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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
//        user.addRoles(ADP_ROLE.getRoles(ADP_ROLE.ADMIN, ADP_ROLE.USER));
//        user.addRoles(ADP_ROLE.getRoles(ADP_ROLE.USER));
//        userRepository.save(user);

        Optional<User> findUser = userRepository.findById(user.getId());
        boolean loginBool = false;
        if (findUser.isPresent()) {
            loginBool = true;
            String token = jwtUtil.generateToken(findUser.get());
            Utilities.addCookie("jwt", token);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", loginBool);
        return result;
    }

    public User selectUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }
}
