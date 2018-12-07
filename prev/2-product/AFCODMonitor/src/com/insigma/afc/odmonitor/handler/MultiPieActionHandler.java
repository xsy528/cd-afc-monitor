package com.insigma.afc.odmonitor.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.afc.odmonitor.form.TicketPieForm;
import com.insigma.afc.odmonitor.service.IChartService;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.view.chart.data.MultiPieData;
import com.insigma.commons.lang.DateSpan;
import com.insigma.commons.spring.Autowire;

/**
 * 饼图客流刷新处理
 * 
 * @author 廖红自
 */
public class MultiPieActionHandler extends ActionHandlerAdapter {

	private Map<Integer, String> stationNameMap = new HashMap<Integer, String>();

	@Autowire
	private IChartService chartService;

	private String[] LEGEND = new String[] { "进站", "出站", "购票", "充值" };

	@Override
	public Response perform(Request request) {

		SearchRequest req = (SearchRequest) request;
		SearchResponse response = new SearchResponse();

		TicketPieForm ticketpieForm = (TicketPieForm) req.getRequestValue();

		int trunsType = ticketpieForm.getTransType();

		if (ticketpieForm != null) {
			List<Object> selectStations = req.getSelection();
			if (selectStations == null || selectStations.isEmpty()) {
				response.addInformation("请至少选择一个节点。");
				return response;
			} else if (trunsType == 0 || trunsType == 0xff) {
				response.addInformation("请至少选择一种交易类型。");
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
				Integer[] stations = new Integer[stationList.size()];
				stationList.toArray(stations);
				ticketpieForm.setStationId(stations);
				ticketpieForm.setStationNameMap(stationNameMap);
			}

			DateSpan time = ticketpieForm.getDateTimeSpan();
			Date startDate = time.getStartDate();
			Date endDate = time.getEndDate();
			if (startDate.getTime() > endDate.getTime()) {
				response.addInformation("开始时间不能大于结束时间。");
				return response;
			}
			ticketpieForm.setStartDate(startDate);
			ticketpieForm.setEndDate(endDate);
			List<Boolean> showRows = new ArrayList<Boolean>();
			showRows.add(true);
			ticketpieForm.setPartNames(LEGEND);
			ticketpieForm.setTimeInterval(5);

			try {
				// 从配置表获取客流统计间隔
				String timePeriodStr = SystemConfigManager.getConfigItem(SystemConfigKey.OD_INTERVAL);
				if (timePeriodStr != null) {
					ticketpieForm.setTimeInterval(Integer.valueOf(timePeriodStr));
				}
			} catch (Exception e) {
				logger.error("查询客流统计间隔参数异常", e);
			}

			try {
				MultiPieData data = chartService.getMultiPieData(ticketpieForm);
				if (data != null) {
					response.setValue(data);
				}
			} catch (Exception e) {
				logger.error("查询车站客流异常", e);
				//				response.addLog("查询车站客流失败。", e);
				response.addError("数据库连接异常。", e);
				return response;
			}
		}
		return response;
	}
}
