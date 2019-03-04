package com.insigma.afc.monitor.config;

import com.insigma.ms.rmi.ICommandService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * Ticket: rmi配置
 *
 * @author xuzhemin
 * 2019-02-28 10:49
 */
@Configuration
public class RmiConfig {

    @Bean
    public RmiProxyFactoryBean rmiCommandService(@Value("${rmiHostIpAddr}")String rmiHostIpAddr,
                                                 @Value("${commandServiceRmiPort}")Integer commandServiceRmiPort){
        RmiProxyFactoryBean bean = new RmiProxyFactoryBean();
        bean.setServiceInterface(ICommandService.class);
        bean.setServiceUrl("rmi://"+rmiHostIpAddr+":"+commandServiceRmiPort+"/CommandService");
        bean.setLookupStubOnStartup(false);
        bean.setRefreshStubOnConnectFailure(true);
        return bean;
    }
}
