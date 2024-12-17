package com.demo.t_web.comn.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 * com.demo.t_web.comn.model.JwtProperties
 *   - JwtProperties.java
 * </pre>
 *
 * @author : tarr4h
 * @className : JwtProperties
 * @description :
 * @date : 12/17/24
 */
@ConfigurationProperties("jwt")
@Data
public class JwtProperties {

    private String signKey;
}
