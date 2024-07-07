package com.demo.t_web.comn.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 * com.demo.t_web.comn.model.MybatisConfig
 *   - MybatisConfig.java
 * </pre>
 *
 * @author : 한태우
 * @className : MybatisConfig
 * @description :
 * @date : 7/7/24
 */
@ConfigurationProperties("spring.database-framework")
@Data
public class DataBaseFrameworkProperties {

    private MybatisProperties mybatisProperties;
    private JpaProperties jpaProperties;

    @Data
    public static class MybatisProperties{
        private String configLocation;
        private String mapperLocation;
        private String aliasPackage;
    }

    @Data
    public static class JpaProperties{

    }
}
