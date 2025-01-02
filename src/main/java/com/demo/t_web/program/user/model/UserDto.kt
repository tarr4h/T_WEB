package com.demo.t_web.program.user.model;

import com.demo.t_web.program.sys.model.Menu;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.user.model.UserBase
 *   - UserBase.java
 * </pre>
 *
 * @author : tarr4h
 * @className : UserBase
 * @description :
 * @date : 12/19/24
 */
@Data
@Builder
public class UserDto {

    private String id;
    private String name;
    private String phone;
    private String email;
    private Date joinDt;
    private Date lastLoginDt;
    private List<UserRole> roles;
    private List<Menu> userMenu;

}
