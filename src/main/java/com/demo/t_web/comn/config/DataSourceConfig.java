package com.demo.t_web.comn.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

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
@MapperScan(value = "com.demo.t_web.program.comn", sqlSessionFactoryRef = "SqlSessionFactory")
@Slf4j
public class DataSourceConfig extends DataSourceUtil {

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
