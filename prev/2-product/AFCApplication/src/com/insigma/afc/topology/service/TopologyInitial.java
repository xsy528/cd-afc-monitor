package com.insigma.afc.topology.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.Application;

public class TopologyInitial {

	private final Log logger = LogFactory.getLog(TopologyInitial.class);

	public boolean init(Short[] lines, Integer[] stations) {

		List<Integer> lcIDs = new ArrayList<Integer>();
		List<Integer> scIDs = new ArrayList<Integer>();
		List<Integer> sleIDs = new ArrayList<Integer>();
		try {
			IMetroNodeService metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);

			// 线路信息
			{
				Map<Short, MetroLine> lineMap = new HashMap<Short, MetroLine>();
				List<MetroLine> metroline = metroNodeService.getMetroLine(lines);
				Application.setData(ApplicationKey.LINE_LIST_KEY, lineMap);
				if (metroline != null && !metroline.isEmpty()) {
					for (MetroLine line : metroline) {
						lcIDs.add((int) line.getLineID());
						lineMap.put(line.getLineID(), line);
						logger.debug("加入线路节点 " + line.getLineName() + " 编号：" + line.getLineID());
					}
				}
			}

			// 车站信息
			{
				List<MetroStation> metroStation = metroNodeService.getMetroStation(lines, null);
				Map<Integer, MetroStation> stationMap = new HashMap<Integer, MetroStation>();
				Application.setData(ApplicationKey.STATION_LIST_KEY, stationMap);
				if (metroStation != null && !metroStation.isEmpty()) {
					for (MetroStation station : metroStation) {
						scIDs.add(station.getId().getStationId());
						stationMap.put(station.getId().getStationId(), station);
						logger.debug("加入车站节点 " + station.getStationName() + " 编号：" + station.getId().getStationId());
					}
				}
			}
			// 设备信息
			{
				List<MetroDevice> metroDevice = metroNodeService.getMetroDevice(stations, null);
				Map<Long, MetroDevice> deviceMap = new HashMap<Long, MetroDevice>();
				Application.setData(ApplicationKey.DEVICE_LIST_KEY, deviceMap);
				if (metroDevice != null && !metroDevice.isEmpty()) {
					for (MetroDevice d : metroDevice) {
						sleIDs.add(d.getId().getDeviceId().intValue());
						deviceMap.put(d.getId().getDeviceId(), d);
						// logger.debug("加入设备节点 " + d.getDeviceName() + " 编号："
						// + d.getId().getDeviceId());
					}
				}
			}

			// 设备全局变量
			{
				Map<String, List<Integer>> toplogyNodes = new HashMap<String, List<Integer>>();
				toplogyNodes.put("ALL_LC_ID", lcIDs);
				toplogyNodes.put("ALL_SC_ID", scIDs);
				toplogyNodes.put("ALL_SLE_ID", sleIDs);
				Application.setData(ApplicationKey.TOPLOGY_NODES, toplogyNodes);
			}
			return true;
		} catch (Exception e) {
			logger.error("程序异常退出。", e);
			return false;
		}
	}

	public void reInit() {
		if (AFCNodeLevel.ACC == AFCApplication.getAFCNodeType()) {
			init(null, null);
		} else if (AFCNodeLevel.LC == AFCApplication.getAFCNodeType()) {
			init(new Short[] { AFCApplication.getLineId() }, null);
		} else if (AFCNodeLevel.SC == AFCApplication.getAFCNodeType()) {
			init(new Short[] { AFCApplication.getLineId() }, new Integer[] { AFCApplication.getStationId() });
		}
	}
}
