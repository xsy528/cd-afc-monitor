/* 
 * 日期：2010-10-14
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.monitor.handler;

import org.eclipse.swt.graphics.Color;

import com.insigma.acc.monitor.wz.entity.convertor.WZModeCmdLogRowColorConvertor;
import com.insigma.afc.constant.ColorConstants;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.TableView;

@TableView(colorConvertor = WZModeCmdLogRowColorConvertor.class)
public class WZModeCmdLogViewItem {

	@ColumnView(name = "序号", sortAble = false)
	private long index;

	@ColumnView(name = "发送时间", sortAble = false)
	private String occurTime;

	@ColumnView(name = "操作员姓名/编号", sortAble = false)
	private String operatorId;

	@ColumnView(name = "车站/编号", sortAble = false)
	private String TargetName;

	@ColumnView(name = "命令名称", sortAble = false)
	private String cmdName;

	@ColumnView(name = "发送结果/编号", sortAble = false)
	private String cmdResult;

	private Short cmdResultCode;

	public Color getBackgound() {
		return null;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getTargetName() {
		return TargetName;
	}

	public void setTargetName(String targetName) {
		TargetName = targetName;
	}

	public String getCmdName() {
		return cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	public String getCmdResult() {
		return cmdResult;
	}

	public void setCmdResult(String cmdResult) {
		this.cmdResult = cmdResult;
	}

	public Short getCmdResultCode() {
		return cmdResultCode;
	}

	public void setCmdResultCode(Short cmdResultCode) {
		this.cmdResultCode = cmdResultCode;
	}

	public Color getForeground() {
		Color color = ColorConstants.COLOR_NORMAL;
		if (null != cmdResultCode && 0 == cmdResultCode) {
			color = ColorConstants.COLOR_NORMAL;
		} else {
			color = ColorConstants.COLOR_ERROR;
		}
		return color;
	}

}
