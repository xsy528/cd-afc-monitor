package com.insigma.afc.monitor.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceId;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.MetroStationId;
import com.insigma.commons.framework.function.editor.SearchActionHandlerAdapter;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;

public class CommandLogSearchActionHandler extends SearchActionHandlerAdapter {

	@Override
	public void perform(SearchRequest request, SearchResponse response) {
		// 获取用户选择的车站
		List list = null;
		int totalNum = 0;
		List<Object> nodeList = (List<Object>) request.getValue();
		List<Short> lineIdList = new ArrayList<Short>();
		List<Integer> stationIdList = new ArrayList<Integer>();
		List<Long> deviceIdList = new ArrayList<Long>();

		if (nodeList != null && !nodeList.isEmpty()) {
			for (Object object : nodeList) {
				if (object instanceof MetroStation) {
					MetroStationId mId = ((MetroStation) object).getId();
					stationIdList.add(mId.getStationId());
					if (!lineIdList.contains(mId.getLineId())) {
						lineIdList.add(mId.getLineId());
					}
				} else if (object instanceof MetroDevice) {
					MetroDeviceId mId = ((MetroDevice) object).getId();
					deviceIdList.add(mId.getDeviceId());
				}
			}
		} else {
			response.addInformation("请至少选择一个节点。");
			return;
		}
		Short[] lineIds = null;
		if (!lineIdList.isEmpty()) {
			lineIds = lineIdList.toArray(new Short[lineIdList.size()]);
		}
		// 天津需要查车站时，也将线路的日志查询出来
		Integer[] stationIds = null;
		if (!stationIdList.isEmpty()) {
			stationIds = new Integer[stationIdList.size() + 1];
			// 线路
			stationIds[0] = 0;
			int i = 1;
			for (Integer stationId : stationIdList) {
				stationIds[i] = stationId;
				i++;
			}
		}
		Long[] deviceIds = null;
		if (deviceIdList.size() > 0) {
			deviceIds = deviceIdList.toArray(new Long[deviceIdList.size()]);
		}
		try {
			CommandLogSearchForm cmdLogForm = (CommandLogSearchForm) request.getForm().getEntity();
			Date startTime = cmdLogForm.getStartTime();
			Date endTime = cmdLogForm.getEndTime();
			if (startTime != null && endTime != null) {
				if (startTime.equals(endTime) || startTime.after(endTime)) {
					response.addInformation("开始时间不能大于或者等于结束时间。");
					return;
				}
			}

			Boolean searchFlg = false;
			if (cmdLogForm.getCmdResult() == null) {
				searchFlg = null;
			} else if (cmdLogForm.getCmdResult().intValue() == 0) {
				searchFlg = true;
			}
			//			List<TmoCmdResult> resultList = cmdLogService.getCommandLogList(lineIds, stationIds, deviceIds,
			//					cmdLogForm.getOperatorId(), null, cmdLogForm.getStartTime(), cmdLogForm.getEndTime(), searchFlg,
			//					cmdLogForm.getCmdType(), request.getPage(), request.getPageSize());
			//
			//			if (null == deviceIds) {
			//				list = getCommandLogViewList(resultList, request.getPage(), request.getPageSize());
			//			} else {
			//
			//				list = getCommandLogDeviceViewList(resultList, request.getPage(), request.getPageSize());
			//			}
			//			totalNum = cmdLogService.getMaxofCommandLog(lineIds, stationIds, deviceIds, cmdLogForm.getOperatorId(),
			//					null, cmdLogForm.getStartTime(), cmdLogForm.getEndTime(), searchFlg, cmdLogForm.getCmdType());
		} catch (Exception e) {
			logger.error("命令日志查询失败", e);
			response.addError("数据库连接异常。");
			return;
		}
		System.out.println(" totalNum =" + totalNum);
		if (totalNum <= 0) {
			response.getData().setTotalSize(0);
			response.getData().setCurrentPage(1);
			response.addInformation("查询结果为空。");
		} else {
			response.getData().setTotalSize(totalNum);
			response.getData().setContent(list);
			if (list == null || list.isEmpty()) {
				response.getData().setTotalSize(0);
				response.getData().setCurrentPage(1);
				response.addInformation("查询第 " + (request.getPage() + 1) + " 页结果为空。");
			}
		}
	}

}
