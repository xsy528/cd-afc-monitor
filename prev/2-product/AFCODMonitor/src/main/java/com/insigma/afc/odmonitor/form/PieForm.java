package com.insigma.afc.odmonitor.form;

import com.insigma.afc.dic.AFCTicketFamily;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

/**
 * 客流饼图form
 * 
 * @author 廖红自
 */
public class PieForm extends ODForm {

	public static String[] LEGEND = new String[] { "进站", "出站", "购票", "充值" };

	@View(label = "票种 ", type = "Combo", checkable = true)
	@DataSource(provider = AFCTicketFamily.class)
	protected Integer ticketFamily;

	@View(label = "交易类型 ", type = "BitGroup", checked = true)
	@DataSource(list = { "进站", "出站", "购票", "充值" })
	protected Integer transType = 0xff;

	public PieForm() {
	}

	public Integer getTicketFamily() {
		return ticketFamily;
	}

	public void setTicketFamily(Integer ticketFamily) {
		this.ticketFamily = ticketFamily;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}
}