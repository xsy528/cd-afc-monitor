package com.insigma.afc.monitor.config;

import com.insigma.afc.monitor.model.properties.NetworkConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ticket: 配置属性bean
 *
 * @author xuzhemin
 * 2019-01-30:18:29
 */
@Configuration
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "app")
    public NetworkConfig networkConfig(){
        return new NetworkConfig();
    }
}
