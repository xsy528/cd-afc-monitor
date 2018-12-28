/**
 * 
 */
package com.insigma.afc.topology.convertor;

import java.util.Map;
import java.util.Map.Entry;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.Application;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * @author yangyang
 *
 */
public class MetroNodeConvertor extends ConvertorAdapter {

	private static Map<Integer, MetroStation> stationMap = (Map<Integer, MetroStation>) Application
			.getData(ApplicationKey.STATION_LIST_KEY);

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {

		if (itemObject instanceof Integer) {
			int stationId = (Integer) itemObject;
			MetroNode node = AFCApplication.getNode(stationId);
			if (node == null) {
				return String.format(Application.getStationlineIdFormat(), stationId);
			}
			return String.format("%s/0x" + Application.getStationlineIdFormat(), node.name(), stationId);
		} else if (itemObject instanceof Short) {
			short lineId = (Short) itemObject;
			MetroNode node = AFCApplication.getNode(lineId);
			if (node == null) {
				String nodeName = String.format("温州S%s线",lineId);
				return String.format("%s/0x" + Application.getLineIdFormat(),nodeName, lineId);
			}
			return String.format("%s/0x" + Application.getLineIdFormat(), node.name(), lineId);
		} else if ("0".equals(itemObject.toString())) {
			return "清分中心/0x00000000";
		}

		if (itemObject instanceof Number) {
			Number nodeId = (Number) itemObject;

			if (AFCApplication.getNodeLevel(nodeId.longValue()) != null) {
				//车站节点
				if (AFCApplication.getNodeLevel(nodeId.longValue()).equals(AFCNodeLevel.SC)) {
					int stationId = AFCApplication.getStationId(nodeId.longValue());

					for (Entry<Integer, MetroStation> entry : stationMap.entrySet()) {
						if (entry.getKey().equals(stationId)) {
							return String.format("%s/0x" + Application.getDeviceIdFormat(),
									entry.getValue().getStationName(), nodeId);
						}
					}
					return "--/" + String.format(Application.getDeviceIdFormat(),
							AFCApplication.getNodeId(nodeId.longValue()));

				}
				//设备节点
				else if (AFCApplication.getNodeLevel(nodeId.longValue()).equals(AFCNodeLevel.SLE)) {
					MetroNode node = AFCApplication.getNode(nodeId.longValue());
					if (node == null) {
						return "--/" + String.format(Application.getDeviceIdFormat(),
								AFCApplication.getNodeId(nodeId.longValue()));
					}
					return String.format("%s/0x" + Application.getDeviceIdFormat(), node.name(), nodeId.longValue());
				}
			}

		} else if (itemObject instanceof MetroNode) {
			return AFCApplication.getNodeName(((MetroNode) itemObject).id());
		} else if (itemObject == null) {
			return "--";
		}

		return super.getText(rowObject, itemObject, row, column, view);
	}

}
