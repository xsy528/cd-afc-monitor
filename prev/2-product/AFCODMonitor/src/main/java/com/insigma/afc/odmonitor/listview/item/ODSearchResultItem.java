package com.insigma.afc.odmonitor.listview.item;

import com.insigma.commons.ui.anotation.ColumnView;

/**
 * 客流table显示form
 * 
 * @author 廖红自
 */
public class ODSearchResultItem {

	@ColumnView(name = "序号")
	private Integer id;

	@ColumnView(name = "车站/编号", sortAble = false)
	private String stationName;

	@ColumnView(name = "票种/编码")
	private String ticketFamily;

	@ColumnView(name = "进站客流")
	private Long odIn;

	@ColumnView(name = "出站客流")
	private Long odOut;

	@ColumnView(name = "购票客流")
	private Long odBuy;

	@ColumnView(name = "充值客流")
	private Long odAdd;

	//	@ColumnView(name = "合计")
	private Long odTotal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getTicketFamily() {
		return ticketFamily;
	}

	public void setTicketFamily(String ticketFamily) {
		this.ticketFamily = ticketFamily;
	}

	public Long getOdIn() {
		return odIn;
	}

	public void setOdIn(Long odIn) {
		this.odIn = odIn;
	}

	public Long getOdOut() {
		return odOut;
	}

	public void setOdOut(Long odOut) {
		this.odOut = odOut;
	}

	public Long getOdBuy() {
		return odBuy;
	}

	public void setOdBuy(Long odBuy) {
		this.odBuy = odBuy;
	}

	public Long getOdAdd() {
		return odAdd;
	}

	public void setOdAdd(Long odAdd) {
		this.odAdd = odAdd;
	}

	public Long getOdTotal() {
		return odTotal;
	}

	public void setOdTotal(Long odTotal) {
		this.odTotal = odTotal;
	}

}
