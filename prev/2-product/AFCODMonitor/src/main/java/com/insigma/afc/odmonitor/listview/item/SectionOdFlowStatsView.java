package com.insigma.afc.odmonitor.listview.item;

import org.eclipse.swt.graphics.Color;

import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.form.IColorItem;

/**
 * 创建时间 2010-10-8 下午08:46:38 <br>
 * 描述: 断面客流Table<br>
 * Ticket：
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class SectionOdFlowStatsView implements IColorItem {

	@ColumnView(name = "序号", sortAble = false)
	private long index;

	@ColumnView(name = "运营日期", sortAble = false)
	private String businessday;

	@ColumnView(name = "线路/编号", sortAble = false)
	private String line;

	@ColumnView(name = "车站1/编号", sortAble = false)
	private String upstation;

	@ColumnView(name = "车站2/编号", sortAble = false)
	private String downstation;

	@ColumnView(name = "上行客流", sortAble = false)
	private String upcount;

	@ColumnView(name = "下行客流", sortAble = false)
	private String downcount;

	@ColumnView(name = "总客流", sortAble = false)
	private String totalcount;

	private long sectionId;

	private long downSectionId;

	private Long downFlowCount;

	/**
	 * 上行车站位置X
	 */
	private long upStationPosX;

	/**
	 * 上行车站位置Y
	 */
	private long upStationPosY;

	/**
	 * 下行车站位置X
	 */
	private long downStationPosX;

	/**
	 * 下行车站位置
	 */
	private long downStationPosY;

	/**
	 * 上下行标记位(0:上行，1：下行)
	 */
	private short upDownFlag;

	/**
	 * 客流高中低标记位(0:低，1：中，2：高)
	 */
	private short OdLevelFlag;

	/**
	 * 下行客流高中低标记位(0:低，1：中，2：高)
	 */
	private short downOdLevelFlag;

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getUpstation() {
		return upstation;
	}

	public void setUpstation(String upstation) {
		this.upstation = upstation;
	}

	public String getDownstation() {
		return downstation;
	}

	public void setDownstation(String downstation) {
		this.downstation = downstation;
	}

	public String getBusinessday() {
		return businessday;
	}

	public void setBusinessday(String businessday) {
		this.businessday = businessday;
	}

	public String getUpcount() {
		return upcount;
	}

	public void setUpcount(String upcount) {
		this.upcount = upcount;
	}

	public String getDowncount() {
		return downcount;
	}

	public void setDowncount(String downcount) {
		this.downcount = downcount;
	}

	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}

	public Color getBackgound() {
		return null;
	}

	public Color getForeground() {
		return null;
	}

	public Long getUpStationPosX() {
		return upStationPosX;
	}

	public void setUpStationPosX(long upStationPosX) {
		this.upStationPosX = upStationPosX;
	}

	public Long getUpStationPosY() {
		return upStationPosY;
	}

	public void setUpStationPosY(long upStationPosY) {
		this.upStationPosY = upStationPosY;
	}

	public Long getDownStationPosX() {
		return downStationPosX;
	}

	public void setDownStationPosX(long downStationPosX) {
		this.downStationPosX = downStationPosX;
	}

	public Long getDownStationPosY() {
		return downStationPosY;
	}

	public void setDownStationPosY(long downStationPosY) {
		this.downStationPosY = downStationPosY;
	}

	public short getUpDownFlag() {
		return upDownFlag;
	}

	public void setUpDownFlag(short upDownFlag) {
		this.upDownFlag = upDownFlag;
	}

	public short getOdLevelFlag() {
		return OdLevelFlag;
	}

	public void setOdLevelFlag(short odLevelFlag) {
		OdLevelFlag = odLevelFlag;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public long getDownSectionId() {
		return downSectionId;
	}

	public void setDownSectionId(long downSectionId) {
		this.downSectionId = downSectionId;
	}

	public Long getDownFlowCount() {
		return downFlowCount;
	}

	public void setDownFlowCount(Long downFlowCount) {
		this.downFlowCount = downFlowCount;
	}

	public short getDownOdLevelFlag() {
		return downOdLevelFlag;
	}

	public void setDownOdLevelFlag(short downOdLevelFlag) {
		this.downOdLevelFlag = downOdLevelFlag;
	}

}
