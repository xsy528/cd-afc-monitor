package com.insigma.acc.wz.web.listener;

import com.insigma.acc.wz.admin.WZACCInfoInitor;
import com.insigma.acc.wz.admin.WZAdminInitor;
import com.insigma.acc.wz.util.RouteAddressUtil;
import com.insigma.afc.initor.AFCApplicationInitor;
import com.insigma.afc.initor.DicSystemInitor;
import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.commons.application.Application;
import com.insigma.commons.spring.BeanFactoryUtil;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Init implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //设置spring容器
        BeanFactoryUtil.setApplicationContext(WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContextEvent.getServletContext()));

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
