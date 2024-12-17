package com.demo.t_web.program.login.service;

import com.demo.t_web.program.login.model.User;
import com.demo.t_web.program.login.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Object login(User user) {
//        user.setRole(ROLE.USER);
        userRepository.save(user);
        long count = userRepository.count();
        Optional<User> findUser = userRepository.findById("1234");
        log.debug("--- count = ? {}", count);
        return "111";
    }

    public User selectUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }
}
