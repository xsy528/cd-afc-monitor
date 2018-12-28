/* 
 * 日期：2017年8月10日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.acc.wz.monitor.service.IWZModeService;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.monitor.entity.TmoEquEvent;
import com.insigma.afc.monitor.listview.event.EventFilterForm;
import com.insigma.afc.monitor.search.DeviceEventSearchForm;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.framework.function.editor.SearchActionHandlerAdapter;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.spring.Autowire;

/**
 * Ticket: 设备事件查询Handler
 * @author  mengyifan
 *
 */
public class DeviceEventSearchActionHandler extends SearchActionHandlerAdapter {

	@Autowire
	private IWZModeService metroEventService;

	@Override
	public void perform(SearchRequest request, SearchResponse response) {

		int totalNum = 0;
		int indexPage = request.getPage();
		//节点id
		List<Object> nodeList = (List<Object>) request.getValue();

		if (metroEventService == null) {
			try {
				metroEventService = AFCApplication.getService(IWZModeService.class);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (nodeList.size() <= 0) {
			response.addInformation("请至少选择一个节点。");
			return;
		}

		DeviceEventSearchForm searchForm = (DeviceEventSearchForm) request.getRequestValue();
		Date startTime = searchForm.getStartTime();
		Date endTime = searchForm.getEndTime();

		if (null != startTime && null != endTime && startTime.after(endTime)) {
			response.addInformation("开始时间不能大于结束时间。");
			return;
		}

		//		//设备id
		//		List<Long> deviceList = new ArrayList<Long>();
		//		//List<Short> equTypeList = new ArrayList<Short>();
		//
		//		for (int i = 0; i < nodeList.size(); i++) {
		//			Object nodeInfo = nodeList.get(i);
		//			if (nodeInfo instanceof MetroDevice) {
		//				MetroDevice deviceInfo = (MetroDevice) nodeInfo;
		//				deviceList.add(deviceInfo.getId().getDeviceId());
		//				//equTypeList.add(deviceInfo.getDeviceType());
		//			}
		//		}

		//		Long[] deviceIds = deviceList.toArray(new Long[deviceList.size()]);

		//事件级别
		List<Integer> eventLevs = searchForm.getEventType();

		//查询信息
		EventFilterForm filterForm = new EventFilterForm();
		filterForm.setStartTime(startTime);
		filterForm.setEndTime(endTime);
		filterForm.setPageSize(request.getPageSize());
		filterForm.setEventLevelList(eventLevs);
		filterForm.setSelections(nodeList);

		List<TmoEquEvent> itemList = new ArrayList<TmoEquEvent>();

		try {
			PageHolder page = metroEventService.getEventList(filterForm, indexPage);
			itemList = page.getDatas();
			totalNum = page.getTotalCount();
		} catch (Exception e) {
			logger.error("设备事件日志查询失败", e);
			response.addError("数据库连接异常。");
			return;
		}
		if (itemList != null && !itemList.isEmpty()) {
			response.getData().setTotalSize(totalNum);
			response.getData().setContent(itemList);
		} else {
			response.addInformation("查询结果为空。");
		}
	}
}
