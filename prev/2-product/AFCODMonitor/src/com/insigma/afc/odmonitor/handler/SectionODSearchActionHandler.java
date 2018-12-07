package com.insigma.afc.odmonitor.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.afc.odmonitor.form.SectionPassengerFlowForm;
import com.insigma.afc.odmonitor.listview.item.SectionOdFlowStatsView;
import com.insigma.afc.odmonitor.service.ISectionODFlowService;
import com.insigma.afc.topology.MetroLine;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 创建时间 2010-10-22 下午01:55:16 <br>
 * 描述: 断面客流查询<br>
 * Ticket：
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public class SectionODSearchActionHandler extends ActionHandlerAdapter {

	@Autowire
	private ISectionODFlowService sectionOdFlowService;

	public Response perform(Request request) {
		SearchRequest searchRequest = (SearchRequest) request;
		SearchResponse searchResponse = new SearchResponse();

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
			searchResponse.addInformation("请选择线路。");
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
		}
		// 方向
		Short direction = null;
		// 日期
		Date date = null;
		// 时段
		String timeSection = null;

		Object form = searchRequest.getForm().getEntity();

		if (form instanceof SectionPassengerFlowForm) {
			SectionPassengerFlowForm sectionForm = (SectionPassengerFlowForm) form;
			if (sectionForm.getDirection() != null && sectionForm.getDirection() > 0) {
				direction = sectionForm.getDirection().shortValue();
				direction--;
			} else {
				direction = null;
			}
			date = sectionForm.getDate();
			timeSection = sectionForm.getTimeSection();
		}

		// 时间段 5分钟为一个时间段，第一个时段为1
		Integer[] period = null;

		if (timeSection != null) {
			String[] temp = timeSection.split("-");

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

		}

		TableGrid datagrid = new TableGrid();
		int pageNum = 0;
		datagrid.setPageSize(searchRequest.getPageSize());
		if (request.getAction().getText().equals("查询")) {
			datagrid.setCurrentPage(1);
			pageNum = 0;
		} else {
			datagrid.setCurrentPage(searchRequest.getPage());
			pageNum = searchRequest.getPage() - 1;
		}

		List<SectionOdFlowStatsView> list = sectionOdFlowService.getSectionODFlowStatsViewList(lineIds, period,
				direction, date, -1, 0);
		if (list != null && !list.isEmpty()) {
			datagrid.setTotalSize(list.size());
			int fromIndex = pageNum * searchRequest.getPageSize();
			int toIndex = (pageNum + 1) * searchRequest.getPageSize();
			if (toIndex > list.size()) {
				toIndex = list.size();
			}
			datagrid.setContent(list.subList(fromIndex, toIndex));
			searchResponse.setData(datagrid);
		} else {
			searchResponse.addInformation("查询结果为空。");
		}
		return searchResponse;
	}
}
