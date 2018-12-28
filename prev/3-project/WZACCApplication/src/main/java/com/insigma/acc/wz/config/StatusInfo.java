/* 
 * 日期：2011-3-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias(value = "StatusInfo")
public class StatusInfo {
	@XStreamAsAttribute
	private String statusId;

	@XStreamAsAttribute
	private int componentType;

	@XStreamAsAttribute
	private boolean isUpload;

	@XStreamAsAttribute
	private String applyDevice;

	@XStreamAsAttribute
	private String statusName;

	@XStreamAsAttribute
	private Short statusLevel;

	@XStreamAsAttribute
	private String statusDesc;

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public int getComponentType() {
		return componentType;
	}

	public void setComponentType(int componentType) {
		this.componentType = componentType;
	}

	public boolean isUpload() {
		return isUpload;
	}

	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}

	public String getApplyDevice() {
		return applyDevice;
	}

	public void setApplyDevice(String applyDevice) {
		this.applyDevice = applyDevice;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Short getStatusLevel() {
		return statusLevel;
	}

	public void setStatusLevel(Short statusLevel) {
		this.statusLevel = statusLevel;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

}
