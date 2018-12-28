/* 
 * 日期：2017年10月10日
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
 * Ticket:加密机相关配置
 * 
 * @author chenfuchun
 */
@XStreamAlias("EncryptorConfig")
public class EncryptorConfig extends ConfigurationItem {

	// 加密机IP
	private String encriptIp = "192.168.144.82";

	// 加密机端口
	private int encriptPort = 6666;

	// 联机充值密钥索引
	private int loadkeyIndex = 0x22;

	//单程票tac密钥索引
	private int sjCardkeyIndex;

	//储值卡tac密钥索引
	private int storeCardkeyIndex;

	/**
	 * @return the encriptIp
	 */
	public String getEncriptIp() {
		return encriptIp;
	}

	/**
	 * @param encriptIp
	 *            the encriptIp to set
	 */
	public void setEncriptIp(String encriptIp) {
		this.encriptIp = encriptIp;
	}

	/**
	 * @return the encriptPort
	 */
	public int getEncriptPort() {
		return encriptPort;
	}

	/**
	 * @param encriptPort
	 *            the encriptPort to set
	 */
	public void setEncriptPort(int encriptPort) {
		this.encriptPort = encriptPort;
	}

	/**
	 * @return the loadkeyIndex
	 */
	public int getLoadkeyIndex() {
		return loadkeyIndex;
	}

	/**
	 * @param loadkeyIndex
	 *            the loadkeyIndex to set
	 */
	public void setLoadkeyIndex(int loadkeyIndex) {
		this.loadkeyIndex = loadkeyIndex;
	}

	/**
	 * @return the sjCardkeyIndex
	 */
	public int getSjCardkeyIndex() {
		return sjCardkeyIndex;
	}

	/**
	 * @param sjCardkeyIndex the sjCardkeyIndex to set
	 */
	public void setSjCardkeyIndex(int sjCardkeyIndex) {
		this.sjCardkeyIndex = sjCardkeyIndex;
	}

	/**
	 * @return the storeCardkeyIndex
	 */
	public int getStoreCardkeyIndex() {
		return storeCardkeyIndex;
	}

	/**
	 * @param storeCardkeyIndex the storeCardkeyIndex to set
	 */
	public void setStoreCardkeyIndex(int storeCardkeyIndex) {
		this.storeCardkeyIndex = storeCardkeyIndex;
	}

}
