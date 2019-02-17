package com.insigma.afc.monitor.config;

import com.insigma.afc.monitor.service.rmi.CommandHandlerManager;
import com.insigma.afc.monitor.service.rmi.ICommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-31:10:15
 */
@Configuration
public class AppConfig {

    @Bean
    public CommandHandlerManager commandHandlerManager(){
        List<ICommandHandler> commandHandlerList = new ArrayList<>();
        return new CommandHandlerManager(commandHandlerList);
    }
}
