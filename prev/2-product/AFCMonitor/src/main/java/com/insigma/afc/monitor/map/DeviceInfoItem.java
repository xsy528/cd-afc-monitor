package com.insigma.afc.monitor.map;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;

public class DeviceInfoItem {

	@XmlElementWrapper(name = "Map")
	@XmlElement(name = "DEVICE", type = DeviceNodeQueryItem.class)
	@XmlList
	private List<DeviceNodeQueryItem> Map = new ArrayList<DeviceNodeQueryItem>();

	public List<DeviceNodeQueryItem> getDeviceItems() {
		return Map;
	}

	public void setDeviceItems(List<DeviceNodeQueryItem> deviceItems) {
		this.Map = deviceItems;
	}

}
