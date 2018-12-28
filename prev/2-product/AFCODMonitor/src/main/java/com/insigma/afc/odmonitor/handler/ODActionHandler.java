package com.insigma.afc.odmonitor.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.afc.odmonitor.form.ODForm;
import com.insigma.afc.odmonitor.service.ChartService;
import com.insigma.afc.odmonitor.service.IChartService;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.spring.Autowire;

/**
 * 柱状图客流刷新处理
 * 
 * @author 廖红自
 */
public abstract class ODActionHandler extends ActionHandlerAdapter {

	private int maxCountOfStation = 6;

	public void setMaxCountOfStation(int maxCountOfStation) {
		this.maxCountOfStation = maxCountOfStation;
	}

	protected Map<Integer, String> stationNameMap = new HashMap<Integer, String>();

	@Autowire
	protected IChartService chartService;

	public Response perform(Request request) {
		// 根据查询条件获取客流数据
		SearchRequest req = (SearchRequest) request;
		SearchResponse response = new SearchResponse();

		ODForm form = (ODForm) req.getForm().getEntity();

		// 包括:车站编号，日期，起始时间，结束时间，显示项(比如进站、出站、购票、充值等)，车站编号对应的车站名（ Map<Integer,String> 不能为空）
		List<Object> selectStations = req.getSelection();
		if (selectStations == null || selectStations.isEmpty()) {
			response.addInformation("请至少选择一个节点。");
			return response;
		} else {
			List<Integer> stationList = new ArrayList<Integer>();
			for (Object obj : selectStations) {
				if (obj instanceof MetroStation) {
					Integer stationId = ((MetroStation) obj).getId().getStationId();
					stationList.add(stationId);
					stationNameMap.put(stationId, ((MetroStation) obj).getStationName());
				}
			}
			if (stationList.size() > maxCountOfStation) {
				response.addInformation(
						"界面最多只能显示" + maxCountOfStation + "个车站的客流，请重新选择" + maxCountOfStation + "个以下的车站。");
				return response;
			}
			Integer[] stations = new Integer[stationList.size()];
			stationList.toArray(stations);
			form.setStationId(stations);
			form.setStationNameMap(stationNameMap);
		}

		form.setDate(form.getDate());

		String time = form.getTime();
		String startTimePeriod = time.substring(0, 5);
		String endTimePeriod = time.substring(6);
		int timePeriod = 5;
		form.setTimeInterval(timePeriod);
		try {
			// 从配置表获取客流统计间隔
			String timePeriodStr = SystemConfigManager.getConfigItem(SystemConfigKey.OD_INTERVAL);
			if (timePeriodStr != null) {
				form.setTimeInterval(Integer.valueOf(timePeriodStr));
				timePeriod = Integer.valueOf(timePeriodStr);
			}
		} catch (Exception e) {
			logger.error("查询客流统计间隔参数异常", e);
		}

		form.setStartTimeIndex(ChartService.convertTimeToIndex(startTimePeriod, timePeriod));
		form.setEndTimeIndex(ChartService.convertTimeToIndex(endTimePeriod, timePeriod));

		perform(req, response);

		return response;
	}

	public abstract void perform(SearchRequest request, SearchResponse response);
}
