package com.insigma.afc.log.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.insigma.afc.constant.LogDefines;
import com.insigma.afc.log.entity.TsyOpLog;
import com.insigma.afc.log.form.LogInfo;
import com.insigma.afc.log.result.LogResult;
import com.insigma.afc.log.service.impl.ILogQueryService;
import com.insigma.commons.application.Application;
import com.insigma.commons.framework.function.editor.SearchActionHandlerAdapter;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.util.lang.DateTimeUtil;

public class LogQueryActionHandler extends SearchActionHandlerAdapter<LogInfo> {
	@Autowire
	private ILogQueryService logQueryService;

	@SuppressWarnings("unchecked")
	public void perform(SearchRequest<LogInfo> request, SearchResponse response) {
		// 获取日志查询的条件信息
		LogInfo logForm = request.getForm().getEntity();
		Date endTime = logForm.getEndTime();
		Date startTime = logForm.getStartTime();
		if (endTime != null && startTime != null && startTime.after(endTime)) {
			response.addInformation("开始时间不能大于结束时间。");
			getLogger().info("开始时间不能大于结束时间");
			return;
		}
		Short logClass = logForm.getLogClass() == null ? null
				: logForm.getLogClass() <= 2 && logForm.getLogClass() >= 0
						? LogDefines.logClassValue[logForm.getLogClass()] : 0;
		logForm.setLogClass(logClass);
		List<Integer> moduleNumbers = getModuleNumbers((List<Object>) request.getValue());
		if (moduleNumbers != null && moduleNumbers.size() == 0) {
			response.addInformation("请至少选择一个功能模块。");
			getLogger().info("没有选择任何模块");
			return;
		}
		List<LogResult> resultList = new ArrayList<LogResult>();
		try {

			long totalNum = logQueryService.getOpLogCount(logForm, moduleNumbers);
			List<TsyOpLog> logs = logQueryService.getOpLogs(logForm, moduleNumbers, request.getPage(),
					request.getPageSize());
			resultList = changeOpLogToLogResult(logs, request.getPage(), request.getPageSize());

			// 返回查询结果
			if (resultList != null && !resultList.isEmpty()) {
				response.getData().setTotalSize((int) totalNum);
				response.getData().setContent(resultList);
			} else {
				response.addInformation("查询结果为空。");
			}
		} catch (Exception e) {
			logger.error("系统日志查询异常", e);
			response.addInformation("查询失败，请检查网络和数据库连接情况。");
		}
	}

	private List<LogResult> changeOpLogToLogResult(List<TsyOpLog> logs, int searchIndex, int pageSize) {
		List<LogResult> logResultList = new ArrayList<LogResult>();
		int id = 1;
		for (TsyOpLog opLog : logs) {
			String occurTime = DateTimeUtil.formatDate(opLog.getOccurTime(), "yyyy-MM-dd HH:mm:ss");
			int moduleCode = opLog.getModuleCode() == null ? 0 : opLog.getModuleCode();
			String module = Application.getData(moduleCode) == null ? "未知模块/" + moduleCode
					: Application.getData(moduleCode).toString() + "/" + moduleCode;
			// log.debug("模式实体："+ReflectionToStringBuilder.toString(opLog));
			// log.debug("模式编码："+moduleCode);
			// log.debug("模式描述："+module);
			short logClassValue = opLog.getLogClass() == null ? 0 : opLog.getLogClass();
			String logId = String.valueOf(searchIndex * pageSize + id);
			String logClass = LogDefines.getLogClassName(logClassValue);
			LogResult result = new LogResult(logId, occurTime, opLog.getLogDesc(), opLog.getOperatorId(), module,
					logClass, opLog.getIpAddress());
			logResultList.add(result);
			id++;
		}
		return logResultList;
	}

	@SuppressWarnings("unchecked")
	private List<Integer> getModuleNumbers(List<Object> nodeList) {
		int depth = checkTreeDepth(nodeList);
		List<Integer> moduleList = new ArrayList<Integer>();
		for (Object node : nodeList) {
			if (node != null) {
				switch (depth) {
				case 1: {
					for (Integer obj : (ArrayList<Integer>) node) {
						moduleList.add(obj);
					}
					break;
				}
				case 2: {
					if (node.getClass().isArray()) {
						CollectionUtils.addAll(moduleList, (Integer[]) node);
					}
					break;
				}
				case 3: {
					if (node instanceof Integer) {
						moduleList.add(Integer.valueOf(String.valueOf(node)));
					}
					break;
				}
				default: {
					break;
				}
				}
			}
		}
		return moduleList;
	}

	private int checkTreeDepth(List<Object> nodeList) {
		for (Object node : nodeList) {
			if (node instanceof Integer) {
				return 3;
			}
		}
		for (Object node : nodeList) {
			if (node.getClass().isArray()) {
				return 2;
			}
		}
		return 1;
	}
}
