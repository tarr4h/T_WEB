package com.demo.t_web.program.login.model;

import jakarta.persistence.*;
import lombok.*;
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
public class User implements UserDetails {

    @Id
    @Column(name = "id", length = 20, nullable = false)
    @Order(1)
    private String id;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "join_dt")
    @Temporal(TemporalType.DATE)
    private Date joinDt;

    @Column(name = "last_login_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserRoles> roles = new ArrayList<>();

    @Transient
    private boolean loginYn;

    @Transient
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @PrePersist
    public void prePersist(){
        if(joinDt == null){
            joinDt = new Date();
        }
    }

    @Override
    public String getUsername() {
        return getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
