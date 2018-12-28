package com.insigma.afc.odmonitor.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.afc.odmonitor.form.SectionPassengerFlowForm;
import com.insigma.afc.odmonitor.form.SectionPassengerFlowRefreshForm;
import com.insigma.afc.topology.MetroLine;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;

/**
 * 创建时间 2010-9-30 下午02:39:38 <br>
 * 描述: 刷新地图界面Action<br>
 * Ticket：
 *
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class RefreshMapActionHandler extends ActionHandlerAdapter {

	// 刷新标志
	private boolean isRefresh = false;

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
