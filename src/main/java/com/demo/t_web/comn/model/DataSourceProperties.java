package com.demo.t_web.comn.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * <pre>
 * com.demo.t_web.comn.model.DataSourceProps
 *   - DataSourceProps.java
 * </pre>
 *
 * @author : 한태우
 * @className : DataSourceProps
 * @description :
 * @date : 7/7/24
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {

    private String activeDb;
    private String activeEnv;
    private Map<String, DbConfig> dbConfig;

    @Data
    public static class DbConfig{
        private String dbName;
        private Map<String, DbEnv> env;
    }

    @Data
    public static class DbEnv{
        private String envName;
        private DbProps props;
    }

    @Data
    public static class DbProps{
        private String url;
        private String jdbcUrl;
        private String driverClassName;
        private String username;
        private String password;
    }
}
