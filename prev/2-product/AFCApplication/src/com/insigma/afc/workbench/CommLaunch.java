/* 
 * 日期：2010-2-6
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.workbench;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.afc.application.AFCApplication;
import com.insigma.commons.thread.ThreadManager;
import com.insigma.commons.util.SystemInfo;

/**
 * 通信公共类 Ticket:
 * 
 * @author fenghong
 */
public class CommLaunch {

	private static Log logger = LogFactory.getLog(CommLaunch.class);

	/**
	 * 记录系统信息
	 */
	public static void loadSystemInfo(String componentName) {
		if (componentName == null) {
			componentName = "";
		}
		logger.debug(AFCApplication.getApplicationName() + " " + componentName + "程序启动参数--\n[线路号]:"
				+ AFCApplication.getLineId() + " \n[车站号]:" + AFCApplication.getStationId(), null);
		logger.debug(AFCApplication.getApplicationName() + " " + componentName + "操作系统启动参数--\n[操作系统]:"
				+ SystemInfo.getSystemName() + " \n[操作系统用户]:" + SystemInfo.getCurrentName() + " \n[JAVA运行路径]:"
				+ SystemInfo.getJavaHome(), null);
		//        String dataSourceInfo = "";
		//        DataSourceConfig dsc = (DataSourceConfig) ConfigurationUtil
		//                .getConfigItem(DataSourceConfig.class);
		//        if (dsc != null) {
		//            for (AFCDataSource ds : dsc.getDsconfigs()) {
		//                dataSourceInfo += "\n[数据库类型]:" + ds.getType();
		//                dataSourceInfo += " \n[数据库地址]:" + ds.getDatasourceUrl();
		//                dataSourceInfo += " \n[数据库用户名]:" + ds.getDatasourceUserName();
		//                // dataSourceInfo+="[数据库密码]:"+ds.getDatasourcePassword();
		//            }
		//            logger.doLaunchLog(ConfigurationUtil.getApplicationName() + " " + componentName
		//                    + "数据库启动参数--" + dataSourceInfo, null);
		//        }
	}

	/**
	 * 添加程序的关闭监听:LINUX下关闭程序支持命令kill -15 PID,kill PID,killall java,或者当前运行的则以CTRl+C关闭
	 * 
	 * @param componentName
	 */
	public static void addShutdownHook(String componentName) {
		if (componentName == null) {
			componentName = "";
		}
		final String name = AFCApplication.getApplicationName() + componentName;
		logger.info(name + "添加关闭程序监听。");
		Runtime.getRuntime().addShutdownHook(new Thread("系统关闭线程") {
			public void run() {
				logger.info("开始关闭" + name + "。");
				// 如果超过1分钟系统都无法关闭则执行强制关闭
				MonitorThread mt = new MonitorThread(60000, name);
				mt.setThread(this);
				mt.start();

				ThreadManager.getInstance().stopAll();
				logger.warn(name + "关闭。");
				logger.info("关闭" + name + "成功。");
			}
		});
	}

}
