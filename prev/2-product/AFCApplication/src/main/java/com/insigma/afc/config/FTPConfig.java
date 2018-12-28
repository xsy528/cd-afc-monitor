/* 
 * 日期：2010-8-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.config;

import java.util.List;

import com.insigma.commons.config.ConfigurationItem;

/**
 * FTP配置<br>
 * 目前针对ACC,LC,SC服务器(通信前置机)
 * Ticket: 
 * @author  fenghong
 *
 */
public class FTPConfig extends ConfigurationItem {

	private String localBaseFolder;

	private String tempBaseFolder;

	private String backupBaseFolder;

	private String ftpIpAddr;

	private boolean isPassiveMode = false;

	private List<FolderConfig> folderConfigs;

	public String getLocalBaseFolder() {
		return localBaseFolder;
	}

	public void setLocalBaseFolder(String localBaseFolder) {
		this.localBaseFolder = localBaseFolder;
	}

	public String getTempBaseFolder() {
		return tempBaseFolder;
	}

	public void setTempBaseFolder(String tempBaseFolder) {
		this.tempBaseFolder = tempBaseFolder;
	}

	public String getBackupBaseFolder() {
		return backupBaseFolder;
	}

	public void setBackupBaseFolder(String backupBaseFolder) {
		this.backupBaseFolder = backupBaseFolder;
	}

	public List<FolderConfig> getFolderConfigs() {
		return folderConfigs;
	}

	public void setFolderConfigs(List<FolderConfig> folderConfigs) {
		this.folderConfigs = folderConfigs;
	}

	public String getFtpIpAddr() {
		return ftpIpAddr;
	}

	public void setFtpIpAddr(String ftpIpAddr) {
		this.ftpIpAddr = ftpIpAddr;
	}

	public boolean isPassiveMode() {
		return isPassiveMode;
	}

	public void setPassiveMode(boolean isPassiveMode) {
		this.isPassiveMode = isPassiveMode;
	}

}
