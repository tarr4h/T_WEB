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
    HEADER_PRE("Bearer "),
    NOT_SUITABLE_TOKEN("로그인 정보가 잘못되었습니다."),
    JWT_TOKEN_EXPIRED("로그인이 만료되었습니다."),
    JWT_TOKEN_NOT_FOUND("로그인 정보가 없습니다."),
    JWT_EXCEPTION("토큰 오류가 발생했습니다.")
    ;

    private final String value;

    JwtEnum(String value) {
        this.value = value;
    }
}
