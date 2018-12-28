package com.insigma.afc.odmonitor.form;

import com.insigma.afc.dic.AFCTicketFamily;
import com.insigma.commons.ui.anotation.DataSource;
import com.insigma.commons.ui.anotation.View;

/**
 * 客流曲线图form
 * 
 * @author 廖红自
 */
public class SeriesForm extends ODForm {

	public static String[] LEGEND = new String[] { "进站", "出站", "购票", "充值" };

	@View(label = "时间间隔(x5分钟) ", type = "Combo")
	@DataSource(list = { "1", "2", "5", "6", "8", "10", "15", "20", "25", "30" }, values = { "1", "2", "5", "6", "8",
			"10", "15", "20", "25", "30" })
	protected Integer intervalCount;

	@View(label = "票种 ", type = "Combo", checkable = true)
	@DataSource(provider = AFCTicketFamily.class)
	protected Integer ticketFamily;

	@View(label = "交易类型 ", type = "BitGroup")
	@DataSource(list = { "进站", "出站", "购票", "充值" })
	protected Integer transType = 0xff;

	public SeriesForm() {
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

	public Integer getIntervalCount() {
		return intervalCount;
	}

	public void setIntervalCount(Integer intervalCount) {
		this.intervalCount = intervalCount;
	}

}