/* 
 * 日期：2010-12-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.MetroStationId;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.framework.function.editor.SearchActionHandlerAdapter;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.query.QueryFilter;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;

@SuppressWarnings("unchecked")
public class ModeCmdSearchActionHandler extends SearchActionHandlerAdapter {

	@Autowire
	private ICommonDAO cmdLogService;

	@Override
	public void perform(SearchRequest request, SearchResponse response) {
		int totalNum = 0;
		List<Object> nodeList = (List<Object>) request.getValue();
		List<Short> lineIdList = new ArrayList<Short>();
		List<Integer> stationIdList = new ArrayList<Integer>();
		if (nodeList != null && !nodeList.isEmpty()) {
			for (Object object : nodeList) {
				if (object instanceof MetroStation) {
					MetroStationId mId = ((MetroStation) object).getId();
					stationIdList.add(mId.getStationId());
					if (!lineIdList.contains(mId.getLineId())) {
						lineIdList.add(mId.getLineId());
					}
				}
			}
		} else {
			response.addInformation("请至少选择一个节点。");
			return;
		}

		Short[] lineIds = null;
		if (!lineIdList.isEmpty()) {
			lineIds = new Short[lineIdList.size()];
			for (int i = 0; i < lineIdList.size(); i++) {
				lineIds[i] = lineIdList.get(i);
			}
		}
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

		List<TmoCmdResult> resultList = new ArrayList<TmoCmdResult>();
		try {
			ModeCmdSearchForm form = (ModeCmdSearchForm) request.getRequestValue();
			Date startTime = form.getStartTime();
			Date endTime = form.getEndTime();
			if (startTime != null && endTime != null) {
				if (startTime.equals(endTime) || startTime.after(endTime)) {
					response.addInformation("开始时间不能大于或者等于结束时间。");
					return;
				}
			}
			QueryFilter filter = new QueryFilter();
			filter.propertyFilter("lineId", lineIds, "in");
			filter.propertyFilter("station", stationIds, "in");
			filter.propertyFilter("operator", form.getOperatorId());
			filter.propertyFilter("cmdType", AFCCmdLogType.LOG_MODE.shortValue());

			PageHolder<TmoCmdResult> list = cmdLogService.fetchPageByFilter(filter, TmoCmdResult.class);
			resultList = list.getDatas();
			totalNum = list.getTotalCount();

		} catch (Exception e) {
			logger.error("模式日志查询失败", e);
			response.addError("数据库连接异常。");
			return;
		}

		if (resultList != null && !resultList.isEmpty()) {
			response.getData().setTotalSize(totalNum);
			response.getData().setContent(resultList);
		} else {
			response.addInformation("查询结果为空。");
		}
	}
}
