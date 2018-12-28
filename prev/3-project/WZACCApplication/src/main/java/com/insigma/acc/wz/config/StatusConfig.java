/* 
 * 日期：2011-3-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias(value = "StatusConfig")
public class StatusConfig {

	@XStreamImplicit
	@XStreamAlias(value = "StatusInfo")
	private List<StatusInfo> statusDataList;

	public List<StatusInfo> getStatusDataList() {
		return statusDataList;
	}

	public void setStatusDataList(List<StatusInfo> statusDataList) {
		this.statusDataList = statusDataList;
	}

}
