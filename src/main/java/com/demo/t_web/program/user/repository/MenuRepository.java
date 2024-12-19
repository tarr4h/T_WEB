package com.demo.t_web.program.user.repository;

import com.demo.t_web.program.sys.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * com.demo.t_web.program.user.repository.MenuRepository
 *   - MenuRepository.java
 * </pre>
 *
 * @author : tarr4h
 * @className : MenuRepository
 * @description :
 * @date : 12/19/24
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
