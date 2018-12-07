/* 
 * 日期：2014-12-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.view;

import com.insigma.acc.wz.admin.WZACCInfoInitor;
import com.insigma.acc.wz.admin.WZAdminInitor;
import com.insigma.acc.wz.config.WaiverSearchDayConfig;
import com.insigma.acc.wz.dialog.WZAFCWorkBench;
import com.insigma.acc.wz.monitor.MonitorPlatFormBuilder;
import com.insigma.acc.wz.util.RouteAddressUtil;
import com.insigma.afc.initor.*;
import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.commons.application.Application;
import com.insigma.commons.framework.config.IPlatFormBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Ticket: ACC工作台
 * @author  
 *
 */
public class WZACCWorkbench {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WZAFCWorkBench workbench = new WZAFCWorkBench("温州ACC工作台");
		ConfigLoadSystemInitor configInitor = new ConfigLoadSystemInitor("config/Config.xml");
		configInitor.init();
		new ApplicationInitor().init();
		new AFCApplicationInitor(new RouteAddressUtil()).init();
		new DicSystemInitor().init();
		new WZAdminInitor().init();
		new AFCUIInitor().init();;
		new WZACCInfoInitor().init();

		//加载各类节点信息
		workbench.systemInit();

		workbench.setPlatformBuilders(getMergedPlatForms());

		//初始化tsy_config成全局变量
		{
			Application.setData(SystemConfigKey.VIEW_REFRESH_INTERVAL,
					SystemConfigManager.getConfigItemForInt(SystemConfigKey.VIEW_REFRESH_INTERVAL));
			Application.setData(SystemConfigKey.ALARM_THRESHHOLD,
					SystemConfigManager.getConfigItemForInt(SystemConfigKey.ALARM_THRESHHOLD));
			Application.setData(SystemConfigKey.WARNING_THRESHHOLD,
					SystemConfigManager.getConfigItemForInt(SystemConfigKey.WARNING_THRESHHOLD));
		}

		workbench.setMainClazz(WZACCWorkbench.class);

		workbench.start();

	}

	public static List<IPlatFormBuilder> getMergedPlatForms() {
		List<IPlatFormBuilder> list = new ArrayList<IPlatFormBuilder>();

		{
			// 监控管理
			MonitorPlatFormBuilder monitor = new MonitorPlatFormBuilder();
			list.add(monitor);
		}

		return list;
	}

}
