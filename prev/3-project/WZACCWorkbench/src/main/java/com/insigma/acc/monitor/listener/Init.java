package com.insigma.acc.monitor.listener;

import com.insigma.acc.monitor.init.WZACCInfoInitor;
import com.insigma.acc.monitor.init.WZAdminInitor;
import com.insigma.acc.monitor.util.RouteAddressUtil;
import com.insigma.acc.monitor.service.FileService;
import com.insigma.afc.initor.AFCApplicationInitor;
import com.insigma.afc.initor.DicSystemInitor;
import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.commons.application.Application;
import com.insigma.commons.spring.BeanFactoryUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Init implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        //设置spring容器
        BeanFactoryUtil.setApplicationContext(webApplicationContext);
        ((FileService)webApplicationContext.getBean("fileService")).synResources();

        //初始化Application
        new AFCApplicationInitor(new RouteAddressUtil()).init();

        //字典初始化
        new DicSystemInitor().init();

        //数据管理初始化
        new WZAdminInitor().init();

        //加载线路列表
        new WZACCInfoInitor().init();

        //初始化tsy_config成全局变量
        Application.setData(SystemConfigKey.VIEW_REFRESH_INTERVAL, SystemConfigManager.getConfigItemForInt(SystemConfigKey.VIEW_REFRESH_INTERVAL));
        Application.setData(SystemConfigKey.ALARM_THRESHHOLD, SystemConfigManager.getConfigItemForInt(SystemConfigKey.ALARM_THRESHHOLD));
        Application.setData(SystemConfigKey.WARNING_THRESHHOLD, SystemConfigManager.getConfigItemForInt(SystemConfigKey.WARNING_THRESHHOLD));

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
