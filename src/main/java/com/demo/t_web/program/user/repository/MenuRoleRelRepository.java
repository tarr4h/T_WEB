package com.demo.t_web.program.user.repository;

import com.demo.t_web.program.sys.model.MenuRoleRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.user.repository.MenuRoleRelRepository
 *   - MenuRoleRelRepository.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : MenuRoleRelRepository
 * @description :
 * @date : 12/20/24
 */
public interface MenuRoleRelRepository extends JpaRepository<MenuRoleRel, MenuRoleRel.MenuRoleId> {

    List<MenuRoleRel> findByIdRoleId(String roleId);
}
