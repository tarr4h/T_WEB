package com.demo.t_web.program.login.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * com.demo.t_web.program.login.model.UserRoles
 *   - UserRoles.java
 * </pre>
 *
 * @author : tarr4h
 * @className : UserRoles
 * @description :
 * @date : 12/13/24
 */
@Entity
@Table(name = "adp_user_roles")
@Getter
@Setter
public class UserRoles implements GrantedAuthority {

    @EmbeddedId
    private RoleId id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "reg_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDt;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist(){
        if(regDt == null){
            regDt = new Date();
        }
    }

    @Override
    public String getAuthority() {
        return getId().getRoleId();
    }

    @Embeddable
    @Getter
    @Setter
    public static class RoleId implements Serializable {
        @Column(name = "role_id")
        private String roleId;
        @Column(name = "user_id")
        private String userId;
    }
}
