package com.insigma.acc.wz.web.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insigma.acc.wz.web.filter.CorsFilter;
import com.insigma.acc.wz.web.listener.Init;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Map;

/**
 * author: xuzhemin
 * 2018/10/8 16:47
 */
public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final String LOG_PATH = "./jetty_log/yyyy_mm_dd.request.log";

    private int port = 8080;

    /**
     * 启动Web服务器
     */
    public void startServer() throws Exception {
        //禁用jetty日志
        System.setProperty("org.eclipse.jetty.util.log.class", "com.insigma.commons.bs.embeddedserver.web.NoLogging");

        Server server = new Server();
        Map<String, Object> appConfig = readAppConfig();

        //增加本地连接
        ServerConnector localConnector = new ServerConnector(server);
        String portStr = System.getProperty("server.port");
        if (portStr!=null){
            port = Integer.parseInt(portStr);
        }else{
            port = Integer.parseInt(appConfig.get("localPort").toString());
        }
        localConnector.setPort(port);
        localConnector.setReuseAddress(true);
        server.addConnector(localConnector);

        //servlet容器
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setInitParameter("contextConfigLocation","classpath*:spring-config.xml");
        //添加dispatchServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setContextConfigLocation("classpath*:spring-config.xml");
        servletContextHandler.addServlet(new ServletHolder(dispatcherServlet),"/*");
        //添加listener
        servletContextHandler.addEventListener(new ContextLoaderListener());
        servletContextHandler.addEventListener(new Init());
        //添加corsFilter
        servletContextHandler.addFilter(new FilterHolder(new CorsFilter()),
                "/*", EnumSet.allOf(DispatcherType.class));

        //整合多个应用
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
        contextHandlerCollection.setHandlers(new Handler[]{servletContextHandler});

        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.addHandler(contextHandlerCollection);

        boolean requestLogEnable = Boolean.valueOf(appConfig.get("requestLogEnable").toString());
        if (requestLogEnable) {
            //增加log
            RequestLogHandler logHandler = new RequestLogHandler();
            logHandler.setRequestLog(createRequestLog());
            handlerCollection.addHandler(logHandler);
        }
        server.setHandler(handlerCollection);
        server.setStopAtShutdown(true);
        server.start();
    }

    /**
     * 读取web配置文件信息
     * @return map对象
     */
    private Map<String, Object> readAppConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map;
        try {
            ClassPathResource configResource = new ClassPathResource("web/app.json");
            InputStream configInput = configResource.getInputStream();
            logger.info("配置文件路径：" + configResource.getPath());
            map = objectMapper.readValue(configInput, new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            logger.error("解析配置文件失败", e);
            throw new RuntimeException("jetty服务器启动失败");
        }
        return map;
    }

    /**
     * 创建请求日志
     * @return 请求日志
     */
    private RequestLog createRequestLog() {
        NCSARequestLog ret = new NCSARequestLog();

        File logPath = new File(LOG_PATH);
        logPath.getParentFile().mkdirs();

        ret.setFilename(logPath.getPath());
        ret.setRetainDays(10);
        ret.setExtended(false);
        ret.setAppend(true);
        ret.setLogTimeZone("GMT");
        ret.setLogLatency(true);
        return ret;
    }
}
