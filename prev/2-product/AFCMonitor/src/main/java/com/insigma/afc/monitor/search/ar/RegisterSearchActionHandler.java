/* 
 * 日期：2010-11-30
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.search.ar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.afc.constant.DatabaseType;
import com.insigma.afc.monitor.search.DeviceRegisterSearchForm;
import com.insigma.afc.monitor.service.IDeviceRegisterService;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.MetroStationId;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.spring.Autowire;

/**
 * Ticket: 设备寄存器查询
 * 
 * @author Zhou-Xiaowei
 */
public class RegisterSearchActionHandler extends ActionHandlerAdapter {

	@Autowire
	private IDeviceRegisterService deviceRegisterService;

	private DatabaseType dbType = DatabaseType.DB2;

	@SuppressWarnings("unchecked")
	public Response perform(Request request) {

		SearchRequest searchRequest = (SearchRequest) request;
		SearchResponse searchResponse = new SearchResponse();

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

		searchResponse.setData(datagrid);

		// 获取用户选择的车站
		List<DeviceQueryRegisterItem> list = null;
		int totalNum = 0;
		List<Object> nodeList = (List<Object>) searchRequest.getValue();
		List<Short> lineIdList = new ArrayList<Short>();
		List<Integer> stationIdList = new ArrayList<Integer>();
		List<Long> equIdList = new ArrayList<Long>();
		if (nodeList != null && !nodeList.isEmpty()) {
			for (Object object : nodeList) {
				if (object instanceof MetroStation) {
					MetroStationId mId = ((MetroStation) object).getId();
					stationIdList.add(mId.getStationId());
					if (!lineIdList.contains(mId.getLineId())) {
						lineIdList.add(mId.getLineId());
					}
				} else if (object instanceof MetroDevice) {
					MetroDevice metroDevice = (MetroDevice) object;
					equIdList.add(metroDevice.getId().getDeviceId());
				}
			}
		} else {
			searchResponse.addInformation("请至少选择一个节点。");
			return searchResponse;
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

		Long[] deviceIds = new Long[equIdList.size()];
		equIdList.toArray(deviceIds);

		try {
			DeviceRegisterSearchForm searchForm = (DeviceRegisterSearchForm) searchRequest.getRequestValue();
			Date startTime = searchForm.getStartTime();
			Date endTime = searchForm.getEndTime();
			Integer queryPeriod = searchForm.getQueryPeriod();
			String deviceId = searchForm.getDeviceId();

			if (startTime != null && endTime != null) {
				if (startTime.equals(endTime) || startTime.after(endTime)) {
					searchResponse.addInformation("开始时间不能大于或者等于结束时间。");
					return searchResponse;
				}
			}

			if (queryPeriod == null) {
				searchResponse.addInformation("请选择查询周期。");
				return searchResponse;
			}

			if (deviceId != null) {
				// 16进制转为10进制
				deviceId = Long.parseLong(searchForm.getDeviceId(), 16) + "";
			}

			list = deviceRegisterService.getDeviceRegisterViewList(searchForm.getQueryPeriod(), lineIds, stationIds,
					deviceIds, searchForm.getDeviceType(), deviceId, searchForm.getStartTime(), searchForm.getEndTime(),
					pageNum, searchRequest.getPageSize(), dbType);
			totalNum = deviceRegisterService.getDeviceRegisterCount(searchForm.getQueryPeriod(), lineIds, stationIds,
					deviceIds, searchForm.getDeviceType(), deviceId, searchForm.getStartTime(),
					searchForm.getEndTime());
		} catch (Exception e) {
			logger.error("设备寄存器查询失败", e);
			searchResponse.addError("数据库连接异常。");
			return searchResponse;
		}

		if (list != null && !list.isEmpty()) {
			datagrid.setTotalSize(totalNum);
			datagrid.setContent(list);
		} else {
			searchResponse.addInformation("查询结果为空。");
		}

		return searchResponse;
	}

	public DatabaseType getDbType() {
		return dbType;
	}

	public void setDbType(DatabaseType dbType) {
		this.dbType = dbType;
	}

}
