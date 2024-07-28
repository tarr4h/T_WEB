package com.demo.t_web.comn.config;

import com.demo.t_web.comn.model.DataBaseFrameworkProperties;
import com.demo.t_web.comn.model.DataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * <pre>
 * com.web.trv.comn.config.MybatisConfig
 *  - MybatisConfig.java
 * </pre>
 *
 * @author : tarr4h
 * @className : MybatisConfig
 * @description :
 * @date : 2023-04-20
 */
@Primary
@Configuration("datasourceConfig")
@MapperScan(value = "com.demo.t_web", sqlSessionFactoryRef = "SqlSessionFactory")
@Slf4j
public class DataSourceConfig {

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

    private DataSourceProperties.DbProps fetchProps(){
        return props().getDbConfig().get(props().getActiveDb()).getEnv().get(props().getActiveEnv()).getProps();
    }

    private DataBaseFrameworkProperties.MybatisProperties fetchMybatisProps(){
        return frameworkProps().getMybatisProperties();
    }

    private boolean isLocalProfile(){
        String[] profile = env.getActiveProfiles();
        return Arrays.asList(profile).contains("local");
    }

    @Bean(name = "dataSource")
    public DataSource DataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(fetchProps().getDriverClassName());
        dataSource.setUrl(isLocalProfile() ? fetchProps().getJdbcUrl() : fetchProps().getUrl());
        dataSource.setUsername(fetchProps().getUsername());
        dataSource.setPassword(fetchProps().getPassword());

        return dataSource;
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource(fetchMybatisProps().getConfigLocation()));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(fetchMybatisProps().getMapperLocation()));
        sqlSessionFactoryBean.setTypeAliasesPackage(fetchMybatisProps().getAliasPackage());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "SessionTemplate")
    public SqlSessionTemplate SqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory firstSqlSessionFactory) {
        return new SqlSessionTemplate(firstSqlSessionFactory);
    }
}
