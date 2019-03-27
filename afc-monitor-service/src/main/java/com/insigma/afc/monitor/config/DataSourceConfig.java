package com.insigma.afc.monitor.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Ticket: 数据源配置
 *
 * @author xuzhemin
 * 2019-01-24:11:49
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource(){
        return DataSourceBuilder.create().type(BasicDataSource.class).build();
    }
}
