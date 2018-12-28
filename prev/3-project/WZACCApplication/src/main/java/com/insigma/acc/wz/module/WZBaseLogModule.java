/* 
 * 日期：2011-12-29
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.module;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.application.Application;
import com.insigma.commons.service.ILogService;

/**
 * Ticket: LOGMODULE基础类
 * 
 * @author yaoyue
 */
public class WZBaseLogModule {
	private final Log logger = LogFactory.getLog(this.getClass());

	protected ILogService logService = null;

	public ILogService getLogService(String moduleCode) {
		if (logService == null) {
			try {
				logService = (ILogService) Application.getService(ILogService.class);
			} catch (Exception e) {
				logger.error("获取ILogService失败。", e);
			}
		}

		if (logService != null) {
			logService.setModule(Integer.valueOf(moduleCode));
		}

		return logService;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

}
