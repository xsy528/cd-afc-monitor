/* 
 * 日期：2017年6月13日
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

import com.insigma.acc.wz.monitor.form.WZCommandLogSearchForm;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.service.ICommonDAO;

/**
 * Ticket: 命令日志查询handler
 * @author  mengyifan
 *
 */
public class CommandLogSearchActionHandler extends ActionHandlerAdapter {

	@Override
	public Response perform(Request request) {
		SearchRequest<WZCommandLogSearchForm> searchRequest = (SearchRequest) request;
		SearchResponse response = new SearchResponse();
		int pageIndex = searchRequest.getPage();
		TableGrid datagrid = new TableGrid();
		int pageSize = searchRequest.getPageSize();

		WZCommandLogSearchForm form = searchRequest.getRequestValue();

		List<Short> lineList = new ArrayList<Short>();
		List<Integer> stationList = new ArrayList<Integer>();
		List<Long> equipList = new ArrayList<Long>();

		List<Object> nodeList = searchRequest.getSelection();
		if (nodeList.size() == 0) {
			response.addInformation("请至少选择一个节点。");
			response.setData(datagrid);
			return response;
		}
		int nodeType = AFCNodeLevel.LC.getStatusCode();
		for (Object node : nodeList) {
			if (node != null) {
				if (node instanceof MetroLine) {
					//					if (AFCApplication.getNodeLevel(((MetroLine) node).getLineID()).getStatusCode() > nodeType) {
					//						nodeType = AFCApplication.getNodeLevel(((MetroLine) node).getLineID()).getStatusCode();
					//					}
					lineList.add(((MetroLine) node).getLineID());
				} else if (node instanceof MetroStation) {
					//					if (AFCApplication.getNodeLevel(((MetroStation) node).getId().getStationId())
					//							.getStatusCode() > nodeType) {
					//						nodeType = AFCApplication.getNodeLevel(((MetroStation) node).getId().getStationId())
					//								.getStatusCode();
					//					}
					nodeType = AFCNodeLevel.SC.getStatusCode();
					stationList.add(((MetroStation) node).getId().getStationId());
				} else if (node instanceof MetroDevice) {
					//					if (AFCApplication.getNodeLevel(((MetroDevice) node).getId().getDeviceId())
					//							.getStatusCode() > nodeType) {
					//						nodeType = AFCApplication.getNodeLevel(((MetroDevice) node).getId().getDeviceId())
					//								.getStatusCode();
					//					}
					nodeType = AFCNodeLevel.SLE.getStatusCode();
					equipList.add(((MetroDevice) node).getId().getDeviceId());
				}
			}
		}

		try {
			ICommonDAO commonDao = Application.getService(ICommonDAO.class);
			PageHolder<TmoCmdResult> page = null;

			try {
				Date startTime = form.getStartTime();
				Date endTime = form.getEndTime();
				if (startTime != null && endTime != null) {
					if (startTime.equals(endTime) || startTime.after(endTime)) {
						response.addInformation("开始时间不能大于或者等于结束时间。");
						response.setData(datagrid);
						return response;
					}
				}

				if (nodeType == AFCNodeLevel.LC.getStatusCode()) {
					if (lineList.size() > 0) {
						for (short lindId : lineList) {
							equipList.add(AFCApplication.getNodeId(lindId));
						}
						form.setDeviceIds(equipList);
					}
				} else if (nodeType == AFCNodeLevel.SC.getStatusCode()) {
					if (stationList.size() > 0) {
						for (int stationId : stationList) {
							equipList.add(AFCApplication.getNodeId(stationId));
						}
						form.setDeviceIds(equipList);
					}
				} else if (nodeType == AFCNodeLevel.SLE.getStatusCode()) {
					if (equipList.size() > 0) {
						form.setDeviceIds(equipList);
					}
				}
				page = commonDao.fetchListByObject(form, pageSize, pageIndex);
				form.setDeviceIds(null);
				//				form.getNodeType().clear();

			} catch (Exception e) {
				e.printStackTrace();
				response.addError("数据库连接异常。");
				return response;
			}

			long count = page.getTotalCount();
			if (count == 0) {
				datagrid.setCurrentPage(0);
				datagrid.setTotalSize(0);
				datagrid.setContent(new ArrayList<TmoCmdResult>());
				response.setData(datagrid);
				response.addInformation("查询数据为空。");
				logger.info("查询数据为空");
				return response;
			}

			if (searchRequest.getPage() == 0 && count > 0) {
				searchRequest.setPage(1);
			}
			datagrid.setCurrentPage(pageIndex);

			datagrid.setTotalSize((int) count);
			datagrid.setContent(page.getDatas());
			datagrid.setPageSize(searchRequest.getPageSize());
			response.setData(datagrid);
		} catch (ServiceException e) {
			response.addLog("命令日志信息查询失败。", e);
			response.addError("命令日志信息查询异常。");
		}

		return response;
	}
}
