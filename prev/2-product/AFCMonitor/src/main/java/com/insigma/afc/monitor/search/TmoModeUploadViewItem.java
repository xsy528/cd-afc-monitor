/* 
 * 日期：2017年8月20日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.search;

import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.anotation.TableView;

/**
 * Ticket: 模式上传查询结果集
 * @author  wangzezhi
 *
 */
@TableView
//@TableView(colorConvertor = UploadInfoRowColorConvertor.class)
public class TmoModeUploadViewItem {

	@ColumnView(name = "序号", sortAble = false)
	private int index;

	@ColumnView(name = "线路/编号", sortAble = false)
	private String lineIdItem;

	@ColumnView(name = "车站/编号", sortAble = false)
	private String stationIdItem;

	@ColumnView(name = "进入的模式/编号", sortAble = false)
	private String modeCodeItem;

	@ColumnView(name = "模式发生时间", sortAble = false)
	private String modeUploadtimeItem;

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the lineIdItem
	 */
	public String getLineIdItem() {
		return lineIdItem;
	}

	/**
	 * @param lineIdItem the lineIdItem to set
	 */
	public void setLineIdItem(String lineIdItem) {
		this.lineIdItem = lineIdItem;
	}

	/**
	 * @return the stationIdItem
	 */
	public String getStationIdItem() {
		return stationIdItem;
	}

	/**
	 * @param stationIdItem the stationIdItem to set
	 */
	public void setStationIdItem(String stationIdItem) {
		this.stationIdItem = stationIdItem;
	}

	/**
	 * @return the modeCodeItem
	 */
	public String getModeCodeItem() {
		return modeCodeItem;
	}

	/**
	 * @param modeCodeItem the modeCodeItem to set
	 */
	public void setModeCodeItem(String modeCodeItem) {
		this.modeCodeItem = modeCodeItem;
	}

	/**
	 * @return the modeUploadtimeItem
	 */
	public String getModeUploadtimeItem() {
		return modeUploadtimeItem;
	}

	/**
	 * @param modeUploadtimeItem the modeUploadtimeItem to set
	 */
	public void setModeUploadtimeItem(String modeUploadtimeItem) {
		this.modeUploadtimeItem = modeUploadtimeItem;
	}

}
