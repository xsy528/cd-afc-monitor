package com.insigma.afc.odmonitor.form;

import com.insigma.afc.dic.AFCTicketFamily;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

/**
 * 客流柱状图查询表单
 * 
 * @author 廖红自
 */
public class BarForm extends ODForm {

	public static String[] LEGEND = new String[] { "进站", "出站", "购票", "充值", "合计" };

	@View(label = "票种 ", type = "Combo", checkable = true)
	@DataSource(provider = AFCTicketFamily.class)
	protected Integer ticketFamily;

	@View(label = "交易类型 ", type = "BitGroup")
	@DataSource(list = { "进站", "出站", "购票", "充值", "合计" })
	protected Integer transType = 0xff;

	public BarForm() {

	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public Integer getTicketFamily() {
		return ticketFamily;
	}

	public void setTicketFamily(Integer ticketFamily) {
		this.ticketFamily = ticketFamily;
	}
}