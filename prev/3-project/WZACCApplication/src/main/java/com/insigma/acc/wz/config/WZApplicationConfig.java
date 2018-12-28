/* 
 * 日期：2014-8-20
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
 * Ticket:加锁文件地址获取
 * 
 * @author LiHaiDong
 */

@XStreamAlias(value = "WZApplicationConfig")
public class WZApplicationConfig extends ConfigurationItem {

	/**
	 * 锁文件地址
	 */
	private String lockFilePath;

	//	/**
	//	 * 8005参数同步报文是否向设备转发
	//	 */
	//	private boolean syncAfter;
	//
	//	/**
	//	 * SC设备状态定时上送LC时间间隔
	//	 */
	//	private long statusUploadInterval;
	//
	//	/**
	//	 *  权限参数是否自动生成
	//	 */
	//	private boolean opeAuthAutoCreate;
	//
	//	/**
	//	 * 权限参数自动下发
	//	 */
	//	private boolean opeAuthAutoSend;
	//	
	//	/**
	//	 * 参数自动下发列表,以','隔开
	//	 */
	//	private String eodAutoSendList;
	//
	//	/**
	//	 * 权限参数自动生成轮训周期，单位秒
	//	 */
	//	private long opeAuthAutoTime;

	//	public boolean isOpeAuthAutoCreate() {
	//		return opeAuthAutoCreate;
	//	}
	//
	//	public void setOpeAuthAutoCreate(boolean opeAuthAutoCreate) {
	//		this.opeAuthAutoCreate = opeAuthAutoCreate;
	//	}
	//
	//	public boolean isOpeAuthAutoSend() {
	//		return opeAuthAutoSend;
	//	}
	//
	//	public void setOpeAuthAutoSend(boolean opeAuthAutoSend) {
	//		this.opeAuthAutoSend = opeAuthAutoSend;
	//	}
	//
	//	public long getOpeAuthAutoTime() {
	//		return opeAuthAutoTime;
	//	}
	//
	//	public void setOpeAuthAutoTime(long opeAuthAutoTime) {
	//		this.opeAuthAutoTime = opeAuthAutoTime;
	//	}

	public String getLockFilePath() {
		return lockFilePath;
	}

	public void setLockFilePath(String lockFilePath) {
		this.lockFilePath = lockFilePath;
	}

	//	public boolean isSyncAfter() {
	//		return syncAfter;
	//	}
	//
	//	public void setSyncAfter(boolean syncAfter) {
	//		this.syncAfter = syncAfter;
	//	}
	//
	//	public long getStatusUploadInterval() {
	//		return statusUploadInterval;
	//	}
	//
	//	public void setStatusUploadInterval(long statusUploadInterval) {
	//		this.statusUploadInterval = statusUploadInterval;
	//	}
	//
	//	public String getEodAutoSendList() {
	//		return eodAutoSendList;
	//	}
	//
	//	public void setEodAutoSendList(String eodAutoSendList) {
	//		this.eodAutoSendList = eodAutoSendList;
	//	}

}
