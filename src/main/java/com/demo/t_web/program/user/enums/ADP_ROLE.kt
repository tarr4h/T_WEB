package com.demo.t_web.program.user.enums;

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
    PUBLIC("PUBLIC", "전체", 1),
    USER("USER", "사용자", 2),
    ADMIN("ADMIN", "관리자", 99),
    PRIVATE("PRIV", "숨김", 999);

    private final String id;
    private final String name;
    private final int level;

    public static List<ADP_ROLE> getRoles(ADP_ROLE... ADPRole){
        return Arrays.asList(ADPRole);
    }

    public static List<ADP_ROLE> getRolesByLevel(int level){
        return Arrays.stream(ADP_ROLE.values())
                .filter(lvl -> lvl.getLevel() <= level)
                .toList();
    }
}
