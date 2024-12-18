package com.demo.t_web.comn.config;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * <pre>
 * com.demo.t_web.comn.config.JpaDatabaseConfig
 *   - JpaDatabaseConfig.java
 * </pre>
 *
 * @author : tarr4h
 * @className : JpaDatabaseConfig
 * @description :
 * @date : 12/12/24
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.demo.t_web"
)
public class JpaDatabaseConfig extends DataSourceUtil {

    @Bean(name = "jpaDataSource")
    public DataSource dataSource(){
        return DataSourceBuilder
                .create()
                .url(isLocalProfile() ? fetchProps().getJdbcUrl() : fetchProps().getUrl())
                .username(fetchProps().getUsername())
                .password(fetchProps().getPassword())
                .driverClassName(fetchProps().getDriverClassName())
                .build();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.demo.t_web");
        Properties prop = new Properties();
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        prop.setProperty("hibernate.hbm2ddl.auto", "update");
        prop.setProperty("hibernate.show_sql", "true");
        prop.setProperty("hibernate.format_sql", "true");
        prop.setProperty("hibernate.highlight_sql", "true");
        prop.setProperty("hibernate.use_sql_comments", "true");
        prop.setProperty("hibernate.jdbc.batch_size", "10");
        em.setJpaProperties(prop);

        return em;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
}
