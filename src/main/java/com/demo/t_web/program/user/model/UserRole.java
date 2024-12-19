package com.demo.t_web.program.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class UserRole implements GrantedAuthority {

    @EmbeddedId
    private RoleId id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "reg_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDt;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonBackReference
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
    @NoArgsConstructor
    public static class RoleId implements Serializable {

        public RoleId(String roleId, String userId) {
            this.roleId = roleId;
            this.userId = userId;
        }

        @Column(name = "role_id")
        private String roleId;
        @Column(name = "user_id")
        private String userId;
    }

    public void setId(String roleId, String userId){
        this.id = new RoleId(roleId, userId);
    }

    public UserRole(User user, String roleId, String roleName){
        setId(roleId, user.getId());
        this.roleName = roleName;
        this.user = user;
    }
}
