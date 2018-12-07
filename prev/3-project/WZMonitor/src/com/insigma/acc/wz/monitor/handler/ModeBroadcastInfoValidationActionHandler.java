/* 
 * 日期：2010-12-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.acc.wz.monitor.service.ModeBroadcastControlService;
import com.insigma.afc.monitor.search.TmoModeBroadcastViewItem;
import com.insigma.afc.monitor.service.IModeService;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.ui.dialog.PWDDialog;
import com.insigma.commons.application.Application;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.spring.Autowire;

/**
 * Ticket: 模式广播确认
 * 
 * @author Zhou-Xiaowei
 */
public class ModeBroadcastInfoValidationActionHandler extends ActionHandlerAdapter {

	@Autowire
	private IModeService modeService;

	private String userId = "";

	boolean validationResult = false;

	private ILogService logService = null;

	public ModeBroadcastInfoValidationActionHandler(ILogService logService) {
		this.logService = logService;
	}

	public Response perform(Request request) {
		SearchRequest searchRequest = (SearchRequest) request;
		SearchResponse searchResponse = new SearchResponse();

		List<Object> nodeList = (List<Object>) searchRequest.getValue();
		List<Integer> stationIdList = new ArrayList<Integer>();

		if (nodeList != null && !nodeList.isEmpty()) {
			for (Object object : nodeList) {
				if (object instanceof MetroStation) {
					stationIdList.add(((MetroStation) object).getId().getStationId());
				}
			}
		} else {
			searchResponse.addInformation("请至少选择一个节点。");
			return searchResponse;
		}

		List<Object> searchList = searchRequest.getSelectionData();
		if (searchList == null || searchList.size() == 0) {
			searchResponse.setCode(IResponseCode.REFRESH);
			searchResponse.addInformation("没有选中需要确认(手动)的记录。");
			return searchResponse;
		}

		boolean validateLogin = false;
		List<Object> list = new ArrayList<Object>();
		for (Object obj : searchList) {
			TmoModeBroadcastViewItem item = (TmoModeBroadcastViewItem) obj;
			if (item.getBroadcastType().equals("手动")) {
				list.add(obj);
			}

			if (item.getBroadcastStatus().equals("未确认")) {
				validateLogin = true;
			}

			if (item.getOperatorId() == null || item.getOperatorId().equals("")) {
				validateLogin = true;
			}
		}

		if (list == null || list.size() == 0) {
			searchResponse.setCode(IResponseCode.REFRESH);
			searchResponse.addInformation("没有选中需要确认(手动)的记录。");
			return searchResponse;
		}

		if (validateLogin) {
			ModeBroadcastValidationDialogRunnable runnable = new ModeBroadcastValidationDialogRunnable();
			Display.getDefault().syncExec(runnable);
			Boolean bl = (Boolean) runnable.getResult();

			if (bl == null || !bl) {
				logger.error("模式广播用户确认失败！");
				// ModeBroadCastInfoSearchActionHandler handler = new
				// ModeBroadCastInfoSearchActionHandler();
				// searchResponse = (SearchResponse) handler.perform(request);
				// searchResponse.addError("模式广播用户确认失败！");
				searchResponse.setCode(IResponseCode.REFRESH);
				return searchResponse;
			}
		}

		// 获取 Service
		if (modeService == null) {
			try {
				modeService = (IModeService) Application.getService(IModeService.class);
			} catch (Exception e) {
				logger.error("模式上传查询失败。", e);
				searchResponse.setCode(IResponseCode.REFRESH);
				searchResponse.addError("获取IModeService失败");
				return searchResponse;
			}
		}

		Object[] result = new Object[1];
		result[0] = list;
		ModeBroadcastControlService control = new ModeBroadcastControlService(result, logService);
		control.send();

		searchResponse.setCode(IResponseCode.REFRESH);

		return searchResponse;
	}

	private Object dialogGo(int style) {
		if (Display.getDefault().getShells().length == 0) {
			return "";
		}

		Shell shell = Display.getDefault().getActiveShell();
		if (shell == null) {
			shell = new Shell(Display.getDefault(), SWT.APPLICATION_MODAL | SWT.ON_TOP);
		}

		PWDDialog dialog = new PWDDialog(shell, SWT.None, Application.getUser().getUserID());
		dialog.setPwdFocus();

		return dialog.open();
	}

	private class ModeBroadcastValidationDialogRunnable implements Runnable {
		private Object result;

		public void run() {
			result = dialogGo(0);
		}

		public Object getResult() {
			return result;
		}
	}

	@Override
	public boolean prepare(Request request) {
		SearchRequest searchRequest = (SearchRequest) request;
		List<Object> searchList = searchRequest.getSelectionData();
		if (searchList == null || searchList.size() == 0) {
			return false;
		}

		for (Object obj : searchList) {
			TmoModeBroadcastViewItem item = (TmoModeBroadcastViewItem) obj;
			if (item.getBroadcastType().equals("手动")) {
				return true;
			}
		}

		return false;
	}
}
