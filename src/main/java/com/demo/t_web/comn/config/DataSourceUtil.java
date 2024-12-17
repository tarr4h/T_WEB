package com.demo.t_web.comn.config;

import com.demo.t_web.comn.model.DataBaseFrameworkProperties;
import com.demo.t_web.comn.model.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * <pre>
 * com.demo.t_web.comn.config.DataSourceUtil
 *   - DataSourceUtil.java
 * </pre>
 *
 * @author : tarr4h
 * @className : DataSourceUtil
 * @description :
 * @date : 12/12/24
 */
abstract class DataSourceUtil {

    @Autowired
    private Environment env;

    @Bean
    public DataSourceProperties props(){
        return new DataSourceProperties();
    }

    @Bean
    public DataBaseFrameworkProperties frameworkProps(){
        return new DataBaseFrameworkProperties();
    }

    public DataSourceProperties.DbProps fetchProps(){
        return props().getDbConfig().get(props().getActiveDb()).getEnv().get(props().getActiveEnv()).getProps();
    }

    public DataBaseFrameworkProperties.MybatisProperties fetchMybatisProps(){
        return frameworkProps().getMybatisProperties();
    }

    public boolean isLocalProfile(){
        String[] profile = env.getActiveProfiles();
        return Arrays.asList(profile).contains("local");
    }
}
