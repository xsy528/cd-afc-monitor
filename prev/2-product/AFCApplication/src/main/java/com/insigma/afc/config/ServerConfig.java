/* 
 * 日期：2010-12-17
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.config;

import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 服务器配置<br>
 * 目前针对ACC,LC,SC服务器(通信前置机) Ticket:
 * 
 * @author fenghong
 */
@XStreamAlias("ServerConfig")
public class ServerConfig extends ConfigurationItem {

	public String getTaskGroups() {
		return taskGroups;
	}

	public void setTaskGroups(String taskGroups) {
		this.taskGroups = taskGroups;
	}

	//需要运行的任务组
	private String taskGroups;

	//数据处理基本目录
	private String dataManagerBaseFolder;

	private FTPConfig ftpConfig;

	public FTPConfig getFtpConfig() {
		return ftpConfig;
	}

	public void setFtpConfig(FTPConfig ftpConfig) {
		this.ftpConfig = ftpConfig;
	}

	public String getDataManagerBaseFolder() {
		return dataManagerBaseFolder;
	}

	public void setDataManagerBaseFolder(String dataManagerBaseFolder) {
		this.dataManagerBaseFolder = dataManagerBaseFolder;
	}

}
