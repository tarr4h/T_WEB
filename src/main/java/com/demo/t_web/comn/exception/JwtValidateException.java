package com.demo.t_web.comn.exception;

import io.jsonwebtoken.JwtException;

/**
 * <pre>
 * com.demo.t_web.comn.exception.JwtValidateException
 *   - JwtValidateException.java
 * </pre>
 *
 * @author : tarr4h
 * @className : JwtValidateException
 * @description :
 * @date : 12/13/24
 */

public class JwtValidateException extends JwtException {

    public JwtValidateException(String msg){
        super(msg);
    }

    public JwtValidateException(String msg, Throwable ex){
        super(msg, ex);
    }
}
