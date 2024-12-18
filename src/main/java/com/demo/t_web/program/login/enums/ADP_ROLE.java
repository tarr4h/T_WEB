package com.demo.t_web.program.login.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.login.enums.Role
 *   - Role.java
 * </pre>
 *
 * @author : tarr4h
 * @className : Role
 * @description :
 * @date : 12/12/24
 */
@Getter
@AllArgsConstructor
public enum ADP_ROLE {
    USER("USER", "사용자"),
    ADMIN("ADMIN", "관리자"),
    PRIVATE("PRIV", "숨김");

    private final String id;
    private final String name;

    public static List<ADP_ROLE> getRoles(ADP_ROLE... ADPRole){
        return Arrays.asList(ADPRole);
    }
}
