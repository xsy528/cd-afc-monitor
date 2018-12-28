/*
 * 日期：2010-6-2 下午04:10:33
 * 描述：预留
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * CHANGELOG
 */
package com.insigma.commons.service;

import com.insigma.commons.application.IService;

/**
 * 统一的系统日志接口，所有需要写系统日志的项目，都需要实现该类
 * 
 * @author 廖红自
 */
public interface ILogService extends IService {

	void setModule(int moduleid);

	/**
	 * 保存错误的系统日志
	 * 
	 * @param message
	 */
	void doBizLog(String message);

	/**
	 * 保存警告的系统日志
	 * 
	 * @param message
	 */
	void doBizWarningLog(String message);

	/**
	 * 保存错误的系统日志
	 * 
	 * @param message
	 */
	void doBizErrorLog(String message);

	/**
	 * 保存警告的系统日志
	 * 
	 * @param message
	 * @param exception
	 */
	void doBizWarningLog(String message, Throwable exception);

	/**
	 * 保存错误的系统日志
	 * 
	 * @param message
	 * @param moduleCode
	 *            如果为null，表示默认的模块
	 * @param exception
	 */
	void doBizErrorLog(String message, Throwable exception);

}
