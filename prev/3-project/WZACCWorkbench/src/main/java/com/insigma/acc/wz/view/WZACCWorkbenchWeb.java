/* 
 * 日期：2014-12-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.view;

import com.insigma.acc.wz.web.server.WebServer;
import com.insigma.afc.initor.ConfigLoadSystemInitor;

/**
 * 
 * Ticket: ACC工作台
 * @author  
 *
 */
public class WZACCWorkbenchWeb {

	public static void main(String[] args) {

		//加载配置
		new ConfigLoadSystemInitor("config/Config.xml").init();

		try {
			new WebServer().startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
