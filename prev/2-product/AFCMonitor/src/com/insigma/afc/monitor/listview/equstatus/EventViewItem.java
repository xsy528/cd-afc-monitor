package com.insigma.afc.monitor.listview.equstatus;

import java.util.Map;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.ColorConstants;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.topology.AFCNode;
import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.form.IColorItem;

/**
 *  设备事件列表Item
 */

public class EventViewItem extends AFCNode implements IColorItem {

	private static final long serialVersionUID = -1955051659336715131L;

	@ColumnView(name = "线路/编号")
	private String linename = "";

	@ColumnView(name = "车站/编号")
	private String stationame = "";

	@ColumnView(name = "设备/编号")
	private String deviceId = "";

	@ColumnView(name = "设备类型/编号")
	private String deviceType = "";

	private short equipmentType;

	@ColumnView(name = "事件名称/编号")
	private String eventname = "";

	@ColumnView(name = "事件值")
	private String value = "";

	@ColumnView(name = "事件描述")
	private String eventDesc = "";

	@ColumnView(name = "事件等级")
	private String strLevel = "";

	private Integer level = 0;

	@ColumnView(name = "发生时间")
	private String time = "";

	public String getLinename() {
		return linename;
	}

	public void setLinename(String linename) {
		this.linename = linename;
	}

	public String getStationame() {
		return stationame;
	}

	public void setStationame(String stationame) {
		this.stationame = stationame;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		Map<Object, String> equTypeMap = (Map<Object, String>) AFCDeviceType.getInstance().getCodeMap();
		if (equTypeMap != null) {
			if (equTypeMap.containsKey(new Integer(equipmentType))) {
				deviceType = equTypeMap.get(new Integer(equipmentType));
			} else {
				deviceType = "未知设备";
			}
		} else {
			deviceType = "未知设备";
		}
		return deviceType + '/' + String.format("0x%02x", equipmentType);
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public short getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(short equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Color getBackgound() {
		return null;
	}

	public Color getForeground() {
		Color color = ColorConstants.COLOR_NORMAL;
		if (level == 0) {
			color = ColorConstants.COLOR_NORMAL;
		} else if (level == 1) {
			color = ColorConstants.COLOR_WARN;
		} else if (level == 2) {
			color = ColorConstants.COLOR_ERROR;
		}
		return color;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public String getStrLevel() {
		return strLevel;
	}

	public void setStrLevel(String strLevel) {
		this.strLevel = strLevel;
	}

}
