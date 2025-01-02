package com.demo.t_web.program.user.model;

import com.demo.t_web.program.sys.model.BaseVo;
import com.demo.t_web.program.sys.model.Menu;
import com.demo.t_web.program.user.enums.ADP_ROLE;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.login.model.User
 *   - User.java
 * </pre>
 *
 * @author : tarr4h
 * @className : User
 * @description :
 * @date : 12/12/24
 */
@Entity(name = "adp_user")
@Table(name = "adp_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Slf4j
public class User extends BaseVo implements UserDetails{

    @Id
    @Column(name = "id", length = 20, nullable = false)
    @Order(1)
    private String id;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "join_dt")
    @Temporal(TemporalType.DATE)
    private Date joinDt;

    @Column(name = "last_login_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDt;

    @Transient
    private boolean loginYn;

    @Transient
    private String token;

    @Transient
    private List<Menu> userMenu = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserRole> roles = new ArrayList<>();

    @Override
    public void prePersist(){
        super.prePersist();
        if(joinDt == null){
            joinDt = new Date();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addRole(String roleId, String roleName){
        if(this.roles == null){
            this.roles = new ArrayList<>();
        }
        if(this.roles.stream().noneMatch(userRole -> userRole.getId().getRoleId().equals(roleId)))
            this.roles.add(new UserRole(this, roleId, roleName));
    }

    public void addRoles(List<ADP_ROLE> adpRoles){
        adpRoles.forEach(role -> addRole(role.getId(), role.getName()));
    }

    public UserDto asUser(){
        return UserDto.builder()
                .id(this.id)
                .name(this.name)
                .phone(this.phone)
                .email(this.email)
                .joinDt(this.joinDt)
                .lastLoginDt(this.lastLoginDt)
                .roles(this.roles)
                .userMenu(userMenu)
                .build();
    }

}
