package com.insigma.afc.log.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.insigma.afc.constant.LogDefines;
import com.insigma.afc.log.form.LogInfo;
import com.insigma.afc.log.service.impl.ILogQueryService;
import com.insigma.commons.application.Application;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.LogMessage.LogLevel;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.op.ImExportResult;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.FileDialog;

public class LogExportActionHandler extends ActionHandlerAdapter {

	private String filePath;

	private final long exportMaxCount = 65536;

	@SuppressWarnings("unchecked")
	public Response perform(Request request) {
		SearchRequest<LogInfo> searchRequest = (SearchRequest<LogInfo>) request;
		SearchResponse response = new SearchResponse();
		// 获取导出日志的查询条件信息
		LogInfo logForm = searchRequest.getForm().getEntity();
		Date endTime = logForm.getEndTime();
		Date startTime = logForm.getStartTime();
		if (endTime != null && startTime != null && startTime.after(endTime)) {
			response.addInformation("开始时间不能大于结束时间。");
			getLogger().info("开始时间不能大于结束时间");
			return response;
		}
		Short logClass = logForm.getLogClass() == null ? null
				: logForm.getLogClass() <= 2 && logForm.getLogClass() >= 0
						? LogDefines.logClassValue[logForm.getLogClass()] : 0;
		logForm.setLogClass(logClass);
		List<Integer> moduleNumbers = getModuleNumbers((List<Object>) searchRequest.getValue());

		if (moduleNumbers != null && moduleNumbers.size() == 0) {
			response.addInformation("请选择需要查询的模块。");
			getLogger().info("没有选择任何模块");
			return response;
		}

		// 获取文件路径
		try {
			ILogQueryService queryService = (ILogQueryService) Application.getService(ILogQueryService.class);

			long count = queryService.getOpLogCount(logForm, moduleNumbers);

			if (count == 0) {
				response.addInformation("没有满足条件的记录，无需导出。");
				getLogger().info("没有满足条件的记录，无需导出");
				return response;
			}

			if (count > exportMaxCount) {
				response.addInformation("目前系统能支持每次导出记录数为65536，请调整查询量分次导出。");
				getLogger().info("目前系统能支持每次导出记录数为65536，请调整查询量分次导出");
				return response;
			}

			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell(), SWT.SAVE);
						fileDialog.setFilterExtensions(new String[] { ".CSV", ".txt", "*.*" });
						fileDialog.setText("日志文件导出");
						filePath = fileDialog.open();
					} catch (Exception e) {
						getLogger().error("日志文件导出失败", e);
					}
				}
			});

			if (filePath == null) {
				return null;
			}

			final File file = new File(filePath);
			if (file.exists() && !file.isDirectory()) {
				int result = MessageForm.Query("日志文件导出", "文件" + file.getName() + "已经存在，是否要替换原有文件？");
				if (result == SWT.YES) {
					file.delete();
				} else {
					return null;
				}
			}

			// Display.getDefault().syncExec
			// export.setEnabled(false);
			// 进度条有效

			getLogger().debug("日志导出线程开始执行");
			ImExportResult result = null;
			Future<ImExportResult> f = null;
			f = queryService.logToCvs(file, logForm, moduleNumbers);
			result = f.get();
			String logDesc = "系统日志导出结果：" + result.toString();
			if (result.getFailRowCount() > 0) {
				response.addLog(logDesc);
			} else {
				response.addLog(LogLevel.WARN, logDesc);
			}
			MessageForm.Message("系统日志管理导出", result.toString());
		} catch (Exception e) {
			response.addLog("系统日志导出异常 。", e);
			MessageForm.Message("系统日志管理导出", "系统日志导出异常 ", SWT.ICON_WARNING);
			getLogger().error("导出异常", e);
		} finally {
			// Display.getDefault().syncExec
			// export.setEnabled(true);
			// 进度条失效
		}
		getLogger().debug("日志导出线程执行结束");

		return null;
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
