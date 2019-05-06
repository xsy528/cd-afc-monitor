package com.insigma.afc.monitor.config;

import com.insigma.afc.monitor.healthIndicator.RegisterHealthIndicator;
import com.insigma.afc.monitor.properties.HealthProperties;
import com.insigma.afc.monitor.properties.RmiProperties;
import com.insigma.afc.workbench.rmi.IBaseCommandService;
import com.insigma.afc.workbench.rmi.ICommandService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({RmiProperties.class,HealthProperties.class})
public class RmiAutoConfig {

    private RmiProperties rmiProperties;

    public RmiAutoConfig(RmiProperties rmiProperties) {
        this.rmiProperties = rmiProperties;
    }

    @Bean
    @ConditionalOnMissingBean(ICommandService.class)
    public RmiProxyFactoryBean rmiCommandService() {
        RmiProxyFactoryBean bean = new RmiProxyFactoryBean();
        bean.setServiceInterface(ICommandService.class);
        bean.setServiceUrl("rmi://" + rmiProperties.getRmiHostIpAddr() + ":" + rmiProperties.getCommandServiceRmiPort()
                + "/CommandService");
        bean.setLookupStubOnStartup(false);
        bean.setRefreshStubOnConnectFailure(true);
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean(IBaseCommandService.class)
    public RmiProxyFactoryBean baseCommandService() {
        RmiProxyFactoryBean bean = new RmiProxyFactoryBean();
        bean.setServiceInterface(IBaseCommandService.class);
        bean.setServiceUrl("rmi://" + rmiProperties.getRmiHostIpAddr() + ":"
                + rmiProperties.getCommunicationRegistRmiPort() + "/CommunicationRegisterService");
        bean.setLookupStubOnStartup(false);
        bean.setRefreshStubOnConnectFailure(true);
        return bean;
    }

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public RegisterHealthIndicator registerHealthIndicator(IBaseCommandService baseCommandService,
                                                           HealthProperties healthProperties){
        return new RegisterHealthIndicator(baseCommandService,healthProperties);
    }
}
