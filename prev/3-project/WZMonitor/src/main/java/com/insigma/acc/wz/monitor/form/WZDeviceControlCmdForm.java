/* 
 * 日期：2018年9月7日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.form;

import java.io.Serializable;

import com.insigma.acc.wz.provider.WZDeviceControlCmdTypeProvider;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

/**
 * Ticket: 设备控制命令
 * @author chenfuchun
 *
 */
public class WZDeviceControlCmdForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@View(label = "", type = "Combo")
	@DataSource(provider = WZDeviceControlCmdTypeProvider.class)
	private Integer cmdType;

	/**
	 * @return the cmdType
	 */
	public Integer getCmdType() {
		return cmdType;
	}

	/**
	 * @param cmdType the cmdType to set
	 */
	public void setCmdType(Integer cmdType) {
		this.cmdType = cmdType;
	}

}
