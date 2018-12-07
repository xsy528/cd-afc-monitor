/* 
 * 日期：2012-7-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.config;

import com.insigma.commons.config.ConfigurationItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Ticket:一卡通上传Config
 * 
 * @author chenhangwen
 */
@XStreamAlias("YKTTransUpConfig")
public class YKTTransUpConfig extends ConfigurationItem {

	private String user;

	private String pass;

	private String host;

	private Integer port = 21;

	private String workDir = null;

	private int fileType;

	private String ftpKey;

	private int fileNum;

	private String merchantCode;

	private boolean isPassiveMode;

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass
	 *            the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the workDir
	 */
	public String getWorkDir() {
		return workDir;
	}

	/**
	 * @param workDir
	 *            the workDir to set
	 */
	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}

	/**
	 * @return the fileType
	 */
	public int getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the ftpKey
	 */
	public String getFtpKey() {
		return ftpKey;
	}

	/**
	 * @param ftpKey
	 *            the ftpKey to set
	 */
	public void setFtpKey(String ftpKey) {
		this.ftpKey = ftpKey;
	}

	/**
	 * @return the fileNum
	 */
	public int getFileNum() {
		return fileNum;
	}

	/**
	 * @param fileNum
	 *            the fileNum to set
	 */
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	/**
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * @param merchantCode the merchantCode to set
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	/**
	 * @return the isPassiveMode
	 */
	public boolean isPassiveMode() {
		return isPassiveMode;
	}

	/**
	 * @param isPassiveMode the isPassiveMode to set
	 */
	public void setPassiveMode(boolean isPassiveMode) {
		this.isPassiveMode = isPassiveMode;
	}

}
