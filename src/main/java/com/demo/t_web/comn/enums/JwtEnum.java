package com.demo.t_web.comn.enums;


import lombok.Getter;

/**
 * <pre>
 * com.demo.t_web.comn.enums.JwtEnum
 *   - JwtEnum.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : JwtEnum
 * @description :
 * @date : 12/13/24
 */
@Getter
public enum JwtEnum {

    HEADER_STRING("Authorization"),
    HEADER_PRE("Bearer ")
    ;

    private String value;

    JwtEnum(String value) {
        this.value = value;
    }
}
