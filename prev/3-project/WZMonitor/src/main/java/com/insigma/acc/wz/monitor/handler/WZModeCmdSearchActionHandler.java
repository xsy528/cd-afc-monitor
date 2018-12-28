/* 
 * 日期：2010-12-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.monitor.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.dic.AFCCmdResultType;
import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.afc.monitor.search.ModeCmdSearchForm;
import com.insigma.afc.monitor.service.ICommandLogService;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.MetroStationId;
import com.insigma.commons.application.Application;
import com.insigma.commons.framework.function.editor.SearchActionHandlerAdapter;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.util.lang.DateTimeUtil;

@SuppressWarnings("unchecked")
public class WZModeCmdSearchActionHandler extends SearchActionHandlerAdapter {

	@Autowire
	private ICommandLogService cmdLogService;

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

		List<WZModeCmdLogViewItem> resultList = new ArrayList<WZModeCmdLogViewItem>();
		try {
			ModeCmdSearchForm form = (ModeCmdSearchForm) request.getRequestValue();
			Date startTime = form.getStartTime();
			Date endTime = form.getEndTime();
			if (startTime != null && endTime != null) {
				if (startTime.after(endTime)) {
					response.addInformation("开始时间不能大于结束时间。");
					return;
				}
			}

			List<TmoCmdResult> list = cmdLogService.getCommandLogList(lineIds, stationIds, null, form.getOperatorId(),
					null, startTime, endTime, null, AFCCmdLogType.LOG_MODE.shortValue(), request.getPage(),
					request.getPageSize());

			totalNum = cmdLogService.getMaxofCommandLog(lineIds, stationIds, null, form.getOperatorId(), null,
					startTime, endTime, null, AFCCmdLogType.LOG_MODE.shortValue());

			// 结果转换显示
			int i = 1 + request.getPage() * request.getPageSize();

			int j = 0;
			for (Object object : list) {

				TmoCmdResult tmoCmdResult = (TmoCmdResult) object;

				if (tmoCmdResult.getStationId() == 0) {
					j++;
					continue;
				}

				WZModeCmdLogViewItem modeCmdLogViewItem = new WZModeCmdLogViewItem();
				// 命令名
				modeCmdLogViewItem.setCmdName(tmoCmdResult.getCmdName() == null ? "未知" : tmoCmdResult.getCmdName());
				String mackName = AFCCmdResultType.getInstance().getNameByValue(tmoCmdResult.getCmdResult().intValue());
				if (null == mackName || "".equals(mackName)) {
					mackName = "未知错误";
				}

				modeCmdLogViewItem.setCmdResult(mackName + "/" + tmoCmdResult.getCmdResult());
				modeCmdLogViewItem.setCmdResultCode(tmoCmdResult.getCmdResult());
				// 序号
				modeCmdLogViewItem.setIndex(i++);
				// 发送时间
				modeCmdLogViewItem.setOccurTime(
						DateTimeUtil.formatDateToString(tmoCmdResult.getOccurTime(), "yyyy-MM-dd HH:mm:ss"));
				// 操作员
				modeCmdLogViewItem.setOperatorId(AFCApplication.getUserNameByUserId(tmoCmdResult.getOperatorId()));

				String targetName = "";
				Integer stationId = tmoCmdResult.getStationId();
				Short lineId = tmoCmdResult.getLineId();

				Map<Integer, MetroStation> stationMap = (Map<Integer, MetroStation>) Application
						.getData(ApplicationKey.STATION_LIST_KEY);
				Map<Short, MetroLine> lineMap = (Map<Short, MetroLine>) Application
						.getData(ApplicationKey.LINE_LIST_KEY);

				if (stationId != null && stationId != 0) {
					MetroStation metroStation = stationMap.get(stationId);
					targetName = (null != metroStation ? metroStation.getStationName() : "") + "/"
							+ String.format("0x%04x", stationId);
				} else if (lineId != null && lineId != 0) {
					MetroLine metroLine = lineMap.get(lineId);
					targetName = (null != metroLine ? metroLine.getLineName() : "") + "/"
							+ String.format("0x%02x", lineId);
				} else {
					targetName = "全路网";
				}

				modeCmdLogViewItem.setTargetName(targetName);

				resultList.add(modeCmdLogViewItem);
			}
			totalNum = totalNum - j;

		} catch (Exception e) {
			logger.error("模式日志查询失败。", e);
			response.addError("数据库连接异常");
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
