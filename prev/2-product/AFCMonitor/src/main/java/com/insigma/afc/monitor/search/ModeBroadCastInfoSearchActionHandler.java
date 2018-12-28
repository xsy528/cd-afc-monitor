package com.insigma.afc.monitor.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.afc.monitor.service.IModeService;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.framework.function.editor.SearchActionHandlerAdapter;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.spring.Autowire;

/**
 * 模式广播查询的处理类
 * 
 * @author CaiChunye
 */
public class ModeBroadCastInfoSearchActionHandler extends SearchActionHandlerAdapter {

	@Autowire
	private IModeService modeService;

	@SuppressWarnings("unchecked")
	@Override
	public void perform(SearchRequest request, SearchResponse response) {

		List<Object> nodeList = (List<Object>) request.getValue();
		List<Integer> stationIdList = new ArrayList<Integer>();

		if (nodeList != null && !nodeList.isEmpty()) {
			for (Object object : nodeList) {
				if (object instanceof MetroStation) {
					stationIdList.add(((MetroStation) object).getId().getStationId());
				}
			}
		} else {
			response.addInformation("请至少选择一个节点。");
			return;
		}
		Integer[] stationIds = null;
		if (!stationIdList.isEmpty()) {
			stationIds = new Integer[stationIdList.size()];
			int i = 0;
			for (Integer stationId : stationIdList) {
				stationIds[i] = stationId;
				i++;
			}
		} else {
			response.addInformation("选择节点出错。");
			return;
		}

		try {
			ModeBroadCastInfoForm modeUploadForm = (ModeBroadCastInfoForm) request.getRequestValue();
			Date startTime = modeUploadForm.getStartTime();
			Date endTime = modeUploadForm.getEndTime();
			if (startTime != null && endTime != null) {
				if (startTime.equals(endTime) || startTime.after(endTime)) {
					response.addInformation("开始时间不能大于或者等于结束时间。");
					return;
				}
			}

			List<TmoModeBroadcastViewItem> list = modeService.getTmoBroadcastViewItemList(null, stationIds,
					modeUploadForm.getModeCode(), modeUploadForm.getOperatorId(),
					modeUploadForm.getBroadcastDestStations(), modeUploadForm.getBroadcastStatus(),
					modeUploadForm.getBroadcastType(), modeUploadForm.getStartTime(), modeUploadForm.getEndTime(),
					request.getPage(), request.getPageSize());
			int totalNum = modeService.getMaxOfModeBroadcast(null, stationIds, modeUploadForm.getModeCode(),
					modeUploadForm.getOperatorId(), modeUploadForm.getBroadcastDestStations(),
					modeUploadForm.getBroadcastStatus(), modeUploadForm.getBroadcastType(),
					modeUploadForm.getStartTime(), modeUploadForm.getEndTime());

			// 返回查询结果
			if (list != null && !list.isEmpty()) {
				response.getData().setTotalSize(totalNum);
				response.getData().setContent(list);

			} else {
				response.addInformation("查询结果为空。");
			}
		} catch (Exception e) {
			logger.error("模式广播查询异常", e);
			response.addError("数据库连接异常。");
			return;
		}
	}

}
