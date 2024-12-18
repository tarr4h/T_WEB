package com.demo.t_web.comn.enums;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

/**
 * <pre>
 * com.demo.t_web.comn.enums.ErrorType
 *   - ErrorType.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : ErrorType
 * @description :
 * @date : 12/18/24
 */
@Getter
public enum ErrorType {

    JWT_ERR(491, "JWT_ERROR", null, "로그인 오류"),
    SECURITY_ERR(HttpServletResponse.SC_FORBIDDEN, "SECURITY_ERROR", "권한이 없습니다.", "권한 오류");

    ErrorType(int code, String type, String msg, String title) {
        this.code = code;
        this.type = type;
        this.msg = msg;
        this.title = title;
    }

    private final int code;
    private final String type;
    private final String msg;
    private final String title;

}
