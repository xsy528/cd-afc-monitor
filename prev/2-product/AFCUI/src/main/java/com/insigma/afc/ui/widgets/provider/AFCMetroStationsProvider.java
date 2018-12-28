/* 
 * 日期：2010-12-17
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.ui.widgets.provider;

import java.util.List;

import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.ui.provider.IComboDataSource;

/**
 * Ticket:提供车站列表
 * 
 * @author Zhou-Xiaowei
 */
public class AFCMetroStationsProvider implements IComboDataSource<Integer> {

	public String[] getText() {

		List<MetroStation> stationList = getMetroStation();

		if ((stationList != null) && (stationList.size() > 0)) {
			String[] stations = new String[stationList.size()];

			for (int i = 0; i < stationList.size(); i++) {
				stations[i] = stationList.get(i).getStationName();
			}

			return stations;
		} else {
			return null;
		}
	}

	public Integer[] getValue() {

		List<MetroStation> stationList = getMetroStation();

		if ((stationList != null) && (stationList.size() > 0)) {
			Integer[] stations = new Integer[stationList.size()];

			for (int i = 0; i < stationList.size(); i++) {
				stations[i] = (int) stationList.get(i).getId().getStationId();
			}

			return stations;
		} else {
			return null;
		}
	}

	private List<MetroStation> getMetroStation() {
		List<MetroStation> stationList = null;

		try {
			IMetroNodeService metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
			stationList = metroNodeService.getAllMetroStation();
		} catch (ServiceException e) {

			e.printStackTrace();
		}

		return stationList;
	}

}
