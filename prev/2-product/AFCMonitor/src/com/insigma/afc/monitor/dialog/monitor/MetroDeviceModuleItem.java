package com.insigma.afc.monitor.dialog.monitor;

import com.insigma.afc.topology.convertor.DeviceModuleStatusConvertor;
import com.insigma.commons.ui.anotation.ColumnView;

/**
 * 
 * @author shenchao
 */
public class MetroDeviceModuleItem {

	@ColumnView(name = "部件简写", width = 100, sortAble = false)
	private String moduleName;

	@ColumnView(name = "部件名称", width = 150, sortAble = false)
	private String remark;

	@ColumnView(name = "状态值", width = 100, convertor = DeviceModuleStatusConvertor.class, sortAble = false)
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
