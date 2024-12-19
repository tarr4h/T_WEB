package com.demo.t_web.program.user.repository;

import com.demo.t_web.program.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * com.demo.t_web.program.login.repository.UserRepository
 *   - UserRepository.java
 * </pre>
 *
 * @author : tarr4h
 * @className : UserRepository
 * @description :
 * @date : 12/12/24
 */
public interface UserRepository extends JpaRepository<User, String> {

}
