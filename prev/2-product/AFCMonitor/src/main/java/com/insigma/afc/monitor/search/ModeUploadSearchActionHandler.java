package com.insigma.afc.monitor.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.service.IModeService;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.framework.function.editor.SearchActionHandlerAdapter;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.service.Service;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 模式上传查询的处理类
 * 
 * @author CaiChunye
 */
@SuppressWarnings("unchecked")
public class ModeUploadSearchActionHandler extends SearchActionHandlerAdapter {

	@Autowire
	private IModeService modeService;

	@Autowire
	protected ICommonDAO commonDAO;

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
		}
		int totalNum = 0;
		try {
			ModeUploadForm modeUploadForm = (ModeUploadForm) request.getRequestValue();
			Date startTime = modeUploadForm.getStartTime();
			Date endTime = modeUploadForm.getEndTime();
			if (startTime != null && endTime != null) {
				if (startTime.equals(endTime) || startTime.after(endTime)) {
					response.addInformation("开始时间不能大于或者等于结束时间。");
					return;
				}
			}

			totalNum = modeService.getMaxofModeUploadInfo(null, stationIds, modeUploadForm.getModeCode(),
					modeUploadForm.getBroadCastStatus(), startTime, endTime);
			//			if (totalNum == 0) {
			//				response.addInformation("查询结果为空。");
			//			}

			List<TmoModeUploadInfo> list = modeService.getModeUploadInfoList(null, stationIds, null,
					modeUploadForm.getModeCode(), modeUploadForm.getBroadCastStatus(), startTime, endTime,
					request.getPage(), request.getPageSize());

			List<TmoModeUploadViewItem> resultList = converToItem(list);

			if (resultList != null && !resultList.isEmpty()) {
				response.getData().setTotalSize(totalNum);
				response.getData().setContent(resultList);
			} else {
				response.addInformation("查询结果为空。");
			}
		} catch (Exception e) {
			logger.error("模式上传查询异常", e);
			response.addError("数据库连接异常。");
			return;
		}

	}

	public List<TmoModeUploadViewItem> converToItem(List<TmoModeUploadInfo> list) {
		if (list == null || "".equals(list)) {
			return null;
		}

		List<TmoModeUploadViewItem> resultList = new ArrayList<TmoModeUploadViewItem>();
		int index = 1;
		Service service = new Service();
		for (TmoModeUploadInfo modeUp : list) {

			TmoModeUploadViewItem item = new TmoModeUploadViewItem();
			item.setIndex(index);

			item.setLineIdItem(getLineName(modeUp.getLineId()) + "/" + String.format("0x%02x", modeUp.getLineId()));

			item.setStationIdItem(
					getStationName(modeUp.getStationId()) + "/" + String.format("0x%04x", modeUp.getStationId()));

			item.setModeCodeItem(modeUp.getModeCode() == null ? "未知"
					: AFCModeCode.getInstance().getNameByValue((int) modeUp.getModeCode()) + "/"
							+ String.format("0x%02x", modeUp.getModeCode()));

			item.setModeUploadtimeItem(DateTimeUtil.formatDate(modeUp.getModeUploadTime(), "yyyy-MM-dd HH:mm:ss"));

			resultList.add(item);
			index++;
		}

		return resultList;
	}

	/**
	* 获取车站信息
	* 
	* @param stationID
	* @return
	*/
	private String getStationName(int stationID) {
		String hql = "from MetroStation t where t.id.stationId = ?";
		List<MetroStation> metroStationList = null;
		try {
			metroStationList = commonDAO.getEntityListHQL(hql, stationID);
		} catch (OPException e) {
			logger.error("获取车站信息异常", e);
		}
		if (metroStationList != null && !metroStationList.isEmpty()) {
			MetroStation metroStation = metroStationList.get(0);
			return metroStation.getStationName();
		} else {
			return null;
		}
	}

	/**
	* 获取线路信息
	* 
	* @param lineID
	* @return
	*/
	private String getLineName(short lineId) {
		String hql = "from MetroLine t where t.lineID = ?";
		List<MetroLine> metroLineList = null;
		try {
			metroLineList = commonDAO.getEntityListHQL(hql, lineId);
		} catch (OPException e) {
			logger.error("获取车站信息异常", e);
		}
		if (metroLineList != null && !metroLineList.isEmpty()) {
			MetroLine metroLine = metroLineList.get(0);
			return metroLine.getLineName();
		} else {
			return null;
		}
	}

}
