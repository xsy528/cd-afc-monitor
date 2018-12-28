/* 
 * 日期：2010-12-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.handler;

import java.util.ArrayList;
import java.util.List;

import com.insigma.acc.wz.monitor.service.ModeBroadcastControlService;
import com.insigma.afc.monitor.search.TmoModeBroadcastViewItem;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.service.ILogService;

/**
 * Ticket: 模式广播重发
 * 
 * @author Zhou-Xiaowei
 */
public class ModeBroadCastInfoRetransimmitActionHandler extends ActionHandlerAdapter {

	private ILogService logService = null;

	public ModeBroadCastInfoRetransimmitActionHandler(ILogService logService) {
		this.logService = logService;
	}

	public Response perform(Request request) {
		SearchRequest searchRequest = (SearchRequest) request;
		SearchResponse searchResponse = new SearchResponse();

		List<Object> nodeList = (List<Object>) searchRequest.getValue();
		List<Integer> stationIdList = new ArrayList<Integer>();

		if (nodeList != null && !nodeList.isEmpty()) {
			for (Object object : nodeList) {
				if (object instanceof MetroStation) {
					stationIdList.add(((MetroStation) object).getId().getStationId());
				}
			}
		} else {
			searchResponse.addInformation("请至少选择一个节点。");
			return searchResponse;
		}

		List<Object> searchList = searchRequest.getSelectionData();
		if (searchList == null || searchList.size() == 0) {
			searchResponse.setCode(IResponseCode.REFRESH);
			searchResponse.addInformation("没有选中需要重发(自动)的记录。");
			return searchResponse;
		}

		List<Object> list = new ArrayList<Object>();
		for (Object obj : searchList) {
			TmoModeBroadcastViewItem item = (TmoModeBroadcastViewItem) obj;
			if (item.getBroadcastType().equals("自动")) {
				list.add(obj);
			}
		}

		if (list == null || list.size() == 0) {
			searchResponse.setCode(IResponseCode.REFRESH);
			searchResponse.addInformation("没有选中需要重发(自动)的记录。");
			return searchResponse;
		}

		Object[] result = new Object[1];
		result[0] = list;

		ModeBroadcastControlService control = new ModeBroadcastControlService(result, logService);
		control.send();

		searchResponse.setCode(IResponseCode.REFRESH);

		return searchResponse;
	}

	@Override
	public boolean prepare(Request request) {
		SearchRequest searchRequest = (SearchRequest) request;
		List<Object> searchList = searchRequest.getSelectionData();
		if (searchList == null || searchList.size() == 0) {
			return false;
		}

		for (Object obj : searchList) {
			TmoModeBroadcastViewItem item = (TmoModeBroadcastViewItem) obj;
			if (item.getBroadcastType().equals("自动")) {
				return true;
			}
		}

		return false;
	}
}
