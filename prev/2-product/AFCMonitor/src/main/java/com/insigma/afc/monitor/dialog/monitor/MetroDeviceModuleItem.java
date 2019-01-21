package com.insigma.afc.monitor.dialog.monitor;

/**
 * 
 * @author shenchao
 */
public class MetroDeviceModuleItem {

	private String moduleName;

	private String remark;

	private Short status;

	public MetroDeviceModuleItem(String moduleName, Short status, String remark) {
		super();
		this.moduleName = moduleName;
		this.status = status;
		this.remark = remark;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
