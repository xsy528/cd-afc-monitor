package com.insigma.acc.wz.define;

import com.insigma.afc.config.FTPConfig;
import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 温州ACC服务器的FTP配置，目前取消注册报文，由本地来配置ACC的FTP信息
 * Ticket: 
 * @author  shenchao
 *
 */
@XStreamAlias("UPSetConfig")
public class UPSetConfig extends ConfigurationItem {

	private FTPConfig ftpConfig;

	public FTPConfig getFtpConfig() {
		return ftpConfig;
	}

	public void setFtpConfig(FTPConfig ftpConfig) {
		this.ftpConfig = ftpConfig;
	}

}
