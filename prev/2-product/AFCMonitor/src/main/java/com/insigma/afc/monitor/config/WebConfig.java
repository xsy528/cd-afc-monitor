package com.insigma.afc.monitor.config;

import com.insigma.afc.monitor.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-24:13:16
 */
@Configuration
public class WebConfig {

    /**
     * 跨域调用配置
     */
    @Bean
    public FilterRegistrationBean registrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter());
        //可以通过配置关闭此功能
        bean.setEnabled(true);
        return bean;
    }
}
