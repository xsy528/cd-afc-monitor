/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*
 *
 */
package com.insigma.afc.monitor.config;

import com.insigma.afc.monitor.service.rest.NodeTreeRestService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/21 15:29
 */
@Configuration
@ConfigurationProperties
public class RestConfig {

    /**
     * 节点拓扑服务接口
     */
    private String topologyServerUrl = "http://localhost:8080";

    @Bean
    public TopologyService topologyService(){
        return Feign.builder()
                .requestInterceptor(new RequestIntercepotor())
                .client(new OkHttpClient())
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(TopologyService.class, topologyServerUrl);
    }

    @Bean
    public NodeTreeRestService nodeTreeRestService(){
        return Feign.builder()
                .requestInterceptor(new RequestIntercepotor())
                .client(new OkHttpClient())
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(NodeTreeRestService.class, topologyServerUrl);
    }

    /**
     * 请求拦截器，增加header信息
     */
    class RequestIntercepotor implements RequestInterceptor{

        @Override
        public void apply(RequestTemplate template) {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            String afcUserId = "Afc-User-Id";
            template.header(afcUserId,request.getHeader(afcUserId));
        }
    }

    public String getTopologyServerUrl() {
        return topologyServerUrl;
    }

    public void setTopologyServerUrl(String topologyServerUrl) {
        this.topologyServerUrl = topologyServerUrl;
    }
}
