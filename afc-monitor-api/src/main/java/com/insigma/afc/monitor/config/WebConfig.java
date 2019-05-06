/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.config;

import com.insigma.commons.exception.ControllerExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/5/6 16:01
 */
@Configuration
public class WebConfig {

    @Bean
    public ControllerExceptionHandler controllerExceptionHandler(){
        return new ControllerExceptionHandler();
    }
}
