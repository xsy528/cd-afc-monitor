package com.insigma.afc.odmonitor.form;

import java.util.Date;

import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.ui.anotation.View;

/**
 * 创建时间 2010-10-12 下午08:43:58 <br>
 * 描述: 断面客流查询<br>
 * Ticket：
 *
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class SectionPassengerFlowForm {

	//    @View(label = "客流方向 ", type = "RadioBox", list = {
	//        "上行", "下行"},values={"0","1"}, checkable = false)
	protected Integer direction;

	@View(label = "选择日期 ", type = "Date")
	protected Date date;

	@View(label = "选择时段 ", type = "TimeSection", format = "5,3")
	protected String TimeSection;

	public SectionPassengerFlowForm() {

	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTimeSection() {
		return TimeSection;
	}

	public void setTimeSection(String timeSection) {
		TimeSection = timeSection;
	}

}