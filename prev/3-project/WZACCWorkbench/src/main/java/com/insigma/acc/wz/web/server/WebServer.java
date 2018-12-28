package com.insigma.acc.wz.web.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * author: xuzhemin
 * 2018/10/8 16:47
 */
public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final String LOG_PATH = "./jetty_log/yyyy_mm_dd.request.log";

    /**
     * 启动Web服务器
     */
    public void startServer() throws Exception {
        //禁用jetty日志
        System.setProperty("org.eclipse.jetty.util.log.class", "com.insigma.commons.bs.embeddedserver.web.NoLogging");

        Server server = new Server();
        Map<String, Object> appConfig = readAppConfig();

        //设置主页
        String mainUrl = (String) appConfig.get("mainUrl");
        System.setProperty("mainUrl", mainUrl);

        //增加本地连接
        ServerConnector localConnector = new ServerConnector(server);
        localConnector.setHost((String) appConfig.get("localIP"));
        localConnector.setPort(Integer.parseInt(appConfig.get("localPort").toString()));
        localConnector.setReuseAddress(true);
        server.addConnector(localConnector);

        //增加远程连接
        ServerConnector remoteConnector = new ServerConnector(server);
        remoteConnector.setHost((String) appConfig.get("remoteIP"));
        remoteConnector.setPort(Integer.parseInt(appConfig.get("remotePort").toString()));
        remoteConnector.setReuseAddress(true);
        server.addConnector(remoteConnector);

        //增加web应用
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        //设置web.xml位置
        String webXmlDir = new ClassPathResource((String) appConfig.get("webXmlDir")).getURL().getPath();
        logger.info("webXmlDir:" + webXmlDir);
        webAppContext.setWar(webXmlDir);

        //整合多个应用
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
        contextHandlerCollection.setHandlers(new Handler[]{webAppContext});
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
        Map<String, Object> map = null;
        try {
            ClassPathResource configResource = new ClassPathResource("web/app.json");
            InputStream configInput = configResource.getInputStream();
            logger.info("配置文件路径：" + configResource.getPath());
            map = objectMapper.readValue(configInput, new TypeReference<Map<String, Object>>(){});
        } catch (JsonParseException e) {
            logger.error("解析配置文件失败", e);
        } catch (JsonMappingException e) {
            logger.error("解析配置文件失败", e);
        } catch (IOException e) {
            logger.error("解析配置文件失败", e);
        }finally {
            if (map==null){
                throw new RuntimeException("jetty服务器启动失败");
            }
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
