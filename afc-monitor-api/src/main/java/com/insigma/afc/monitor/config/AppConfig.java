package com.insigma.afc.monitor.config;

import com.insigma.afc.monitor.service.rest.TopologyService;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-31:10:15
 */
@Configuration
public class AppConfig {

    @Bean
    public TopologyService topologyService(@Value("${topology-server-url}")String url){
        return Feign.builder()
                .client(new OkHttpClient())
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(TopologyService.class, url);
    }
}
