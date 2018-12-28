/* 
 * 日期：2017年8月8日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.form;

import java.io.Serializable;
import java.util.Date;

import com.insigma.acc.wz.provider.DeviceFileTypeProvider;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

/**
 * Ticket: 上传指定文件通知实体
 * @author  wangzezhi
 *
 */
public class WZUploadAssignFileForm implements Serializable {

	private static final long serialVersionUID = 1L;

	public WZUploadAssignFileForm() {
		super();
	}

	@View(label = "开始流水号", regexp = "[0-9]{0,8}$")
	private Long startSN;

	@View(label = "结束流水号", regexp = "[0-9]{0,8}$")
	private Long endSN;

	@View(label = "文件类型", type = "Combo")
	@DataSource(provider = DeviceFileTypeProvider.class)
	private Short fileHeaderTag;

	@View(label = "运营日期", type = "Date", checkable = true, checked = false)
	private Date businessday;

	/**
	 * @return the startSN
	 */
	public Long getStartSN() {
		return startSN;
	}

	/**
	 * @param startSN the startSN to set
	 */
	public void setStartSN(Long startSN) {
		this.startSN = startSN;
	}

	/**
	 * @return the endSN
	 */
	public Long getEndSN() {
		return endSN;
	}

	/**
	 * @param endSN the endSN to set
	 */
	public void setEndSN(Long endSN) {
		this.endSN = endSN;
	}

	@Override
	public String toString() {
		return "上传指定文件通知";
	}

	public Short getFileHeaderTag() {
		return fileHeaderTag;
	}

	public void setFileHeaderTag(Short fileHeaderTag) {
		this.fileHeaderTag = fileHeaderTag;
	}

	public Date getBusinessday() {
		return businessday;
	}

	public void setBusinessday(Date businessday) {
		this.businessday = businessday;
	}
}
