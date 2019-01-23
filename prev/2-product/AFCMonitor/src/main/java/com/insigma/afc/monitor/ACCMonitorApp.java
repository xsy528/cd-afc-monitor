/* 
 * 日期：2014-12-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor;

import com.insigma.afc.monitor.server.WebServer;
import com.insigma.afc.initor.ConfigLoadSystemInitor;

/**
 * 
 * Ticket: ACC工作台
 * @author  
 *
 */
public class ACCMonitorApp {

	public static void main(String[] args) {

		//加载配置
		new ConfigLoadSystemInitor("config/Config.xml").init();
		try {
			new WebServer().startServer();
		} catch (Exception e) {
			throw new RuntimeException("web容器启动失败");
		}
	}

}
