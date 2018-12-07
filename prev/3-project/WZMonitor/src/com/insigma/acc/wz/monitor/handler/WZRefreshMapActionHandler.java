/* 
 * 日期：2018年10月31日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.afc.odmonitor.form.SectionPassengerFlowForm;
import com.insigma.afc.odmonitor.form.SectionPassengerFlowRefreshForm;
import com.insigma.afc.odmonitor.handler.RefreshMapActionHandler;
import com.insigma.afc.topology.MetroLine;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * Ticket: 
 * @author  matianming
 *
 */
public class WZRefreshMapActionHandler extends RefreshMapActionHandler{
	
	// 刷新标志
	private boolean isRefresh = false;

	public static Date _date;
	
	public static Short[] _lineIds;
	
	public static Integer[] _timeIntervalId;
	
	public static Integer _timeCount;
	
	@SuppressWarnings("unchecked")
	public Response perform(Request request) {

		SearchRequest searchRequest = (SearchRequest) request;
		SearchResponse searchResponse = new SearchResponse();

		String selectPeriod = null;
		Date date = null;

		final Object formValue = searchRequest.getForm().getEntity();
		if (formValue instanceof SectionPassengerFlowForm) {
			SectionPassengerFlowForm form = (SectionPassengerFlowForm) formValue;
			date = form.getDate();
			selectPeriod = form.getTimeSection();
		} else if (formValue instanceof SectionPassengerFlowRefreshForm) {
			SectionPassengerFlowRefreshForm refreshForm = (SectionPassengerFlowRefreshForm) formValue;
			date = refreshForm.getDate();
			selectPeriod = refreshForm.getTimeSection();

			Integer refreshType = refreshForm.getRefreshType();
			if (refreshType == 0) {// 手动
				isRefresh = false;
			} else {// 自动
				isRefresh = true;
			}
		}

		// 获取用户选择的线路
		List<Object> nodeList = (List<Object>) searchRequest.getValue();
		List<Short> lineIdList = new ArrayList<Short>();
		if (nodeList != null && !nodeList.isEmpty()) {
			for (Object object : nodeList) {
				if (object instanceof MetroLine) {
					lineIdList.add(((MetroLine) object).getLineID());
				}
			}
		} else {
			if (!isRefresh) {
				searchResponse.addInformation("请选择线路。");
			}
			return searchResponse;
		}
		Short[] lineIds = null;
		if (!lineIdList.isEmpty()) {
			lineIds = new Short[lineIdList.size()];
			int i = 0;
			for (Short lineId : lineIdList) {
				lineIds[i] = lineId;
				i++;
			}
			_lineIds=lineIds;
		}
		// 时间段 5分钟为一个时间段，第一个时段为1
		Integer[] period = null;
		if (selectPeriod != null) {
			String[] temp = selectPeriod.split("-");

			Date date1 = DateTimeUtil.parseStringToDate(temp[0], "HH:mm");
			Date date2 = DateTimeUtil.parseStringToDate(temp[1], "HH:mm");

			int period1 = DateTimeUtil.convertTimeToIndex(date1, (short) 5);
			int period2 = DateTimeUtil.convertTimeToIndex(date2, (short) 5);
			if (date2.getDate() - date1.getDate() == 1) {
				period2 = 289;
			}
			period = new Integer[period2 - period1];
			for (int i = 0; i < period.length; i++) {
				period[i] = period1 + i;
			}
			_timeCount=period.length;
			_timeIntervalId=period;
		}
		_date=date;
		Map<String, Object> data = new HashMap<>();
		data.put("lineIds", Arrays.toString(lineIdList.toArray()));
		data.put("date", date);
		data.put("selectPeriod", selectPeriod);
		searchResponse.setValue(data);
		searchResponse.setCode(IResponseCode.REFRESH);
		return searchResponse;
	}

	public boolean isRefresh() {
		return isRefresh;
	}

	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}
}
