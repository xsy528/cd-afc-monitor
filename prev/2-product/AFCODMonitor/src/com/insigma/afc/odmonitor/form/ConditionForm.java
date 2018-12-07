package com.insigma.afc.odmonitor.form;

import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

/**
 * 客流查询form
 * 
 * @author 廖红自
 */
public class ConditionForm extends ODForm {

	@View(label = "统计类型 ", type = "Combo")
	@DataSource(list = { "按车站和票种", "按车站统计" })
	private Short statType;

	//	@View(label = "合计类别 ", type = "ButtonGroup", checkable = true, checked = false)
	//@DataSource(list = { "进站客流", "出站客流", "购票客流", "充值客流" }, values = { "0", "1", "2", "3" })
	private Short countType;

	public ConditionForm() {

	}

	public Short getStatType() {
		return statType;
	}

	public void setStatType(Short statType) {
		this.statType = statType;
	}

	public Short getCountType() {
		return countType;
	}

	public void setCountType(Short countType) {
		this.countType = countType;
	}

}