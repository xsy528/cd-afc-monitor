package com.insigma.commons.framework.view;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.Action.ActionModel;
import com.insigma.commons.framework.ErrorMessage;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.InformationMessage;
import com.insigma.commons.framework.LogMessage;
import com.insigma.commons.framework.LogMessage.LogLevel;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.service.EmptyLogService;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.ProgressBarDialog;

public class ActionExecutor {

	private Log logger = LogFactory.getLog(ActionExecutor.class);

	private IResponseDisplayer responseDisplayer;

	private IActionExecutorCallback callback;

	private ProgressBarDialog progress = null;

	ILogService bizLogger = null;

	public interface IActionExecutorCallback {
		public void finish();
	}

	public ActionExecutor(IResponseDisplayer responseDisplayer) {
		this.responseDisplayer = responseDisplayer;
	}

	protected class UIThread implements Runnable {

		private Response response;

		private boolean bDialogMute;

		public UIThread(Response response, boolean bDialogMute) {
			this.response = response;
			this.bDialogMute = bDialogMute;
		}

		public void run() {
			if (progress != null) {
				progress.close();
				progress = null;
			}

			if (response == null || responseDisplayer == null) {
				return;
			}

			if (responseDisplayer instanceof Widget && ((Widget) responseDisplayer).isDisposed()) {
				return;
			}

			responseDisplayer.setResponse(response);

			if (!bDialogMute) {
				if (response.getErrors() != null) {
					for (ErrorMessage message : response.getErrors()) {
						MessageForm.Message("错误信息", message.getMessage(), SWT.ICON_ERROR);
					}
				}
				if (response.getMessages() != null) {
					for (InformationMessage message : response.getMessages()) {
						MessageForm.Message("提示信息", message.getMessage(), SWT.ICON_INFORMATION);
					}
				}
			}

			if ((response.getCode() & IResponseCode.RESET) != 0) {
				responseDisplayer.reset();
			}

			if ((response.getCode() & IResponseCode.REFRESH) != 0) {
				responseDisplayer.refresh();
			}
			if (callback != null) {
				callback.finish();
			}
		}
	}

	protected class HandlerThread extends Thread {

		private Action action;

		private Request request;

		private Response response;

		public HandlerThread(Action action, Request request) {
			this.action = action;
			this.request = request;
		}

		public void run() {
			if (request != null) {
				request.setAction(action);
				if (request instanceof SearchRequest) {
					SearchRequest rq = (SearchRequest) request;
					if (action.getActionModel() == ActionModel.SEARCH) {
						rq.setPage(1);// 查询则当前页设置为1
					}
				}
			}

			response = action.getActionHandler().perform(request);

			if (response != null && responseDisplayer != null) {
				Display.getDefault().asyncExec(new UIThread(response, action.isbMute()));
			}
			try {
				doSaveLog(action, response);
			} catch (Exception e) {
				logger.error("记录日志错误", e);
			}
		}
	}

	private void doSaveLog(Action action, Response response) {
		if (response == null) {
			return;
		}

		try {
			if (bizLogger == null)
				bizLogger = (ILogService) Application.getService(ILogService.class);
		} catch (ServiceException e) {
			bizLogger = new EmptyLogService();
		}
		String actionId = action.getActionID();
		int moduleId = 0;
		if (actionId != null) {
			try {
				int funId = Integer.parseInt(actionId);
				moduleId = funId / 100;
			} catch (Exception e) {
				logger.warn("Action ID转换为整数异常，请检查Action ID设置", e);
			}
		} else {
			Function function = action.getFunction();
			if (function != null) {
				Module module = function.getModule();
				if (module != null && module.getFunctionID() != null && !module.getFunctionID().equals("")) {
					moduleId = Integer.parseInt(module.getFunctionID());
				} else {
					if (function.getFunctionID() != null) {
						moduleId = Integer.parseInt(function.getFunctionID()) / 100;
					} else {
						function = function.getRelateFunction();
						if (function != null && function.getFunctionID() != null) {
							moduleId = Integer.parseInt(function.getFunctionID()) / 100;
						}
					}
				}
			}
		}
		bizLogger.setModule(moduleId);
		List<LogMessage> logs = response.getLogs();
		if (logs == null || logs.isEmpty()) {
			return;
		}
		for (LogMessage logMessage : logs) {
			LogLevel level = logMessage.getLevel();
			switch (level) {
			case INFO:
				bizLogger.doBizLog(logMessage.getMessage());
				break;
			case WARN:
				bizLogger.doBizWarningLog(logMessage.getMessage(), logMessage.getException());
				break;
			case ERROR:
				bizLogger.doBizErrorLog(logMessage.getMessage(), logMessage.getException());
				break;

			default:
				break;
			}
		}
	}

	public void execute(Action action, Request request) {
		if (action != null && action.getActionHandler() != null) {
			try {
				logger.debug("执行[" + action.getText() + "]");
				HandlerThread handlerThread = new HandlerThread(action, request);
				handlerThread.start();

				if (action.isShowProgress()) {
					progress = new ProgressBarDialog(Display.getDefault().getActiveShell() == null ? new Shell()
							: Display.getDefault().getActiveShell(), SWT.NONE);
					if ((action.getShowProgressText() != null) && (!"".equals(action.getShowProgressText()))) {
						progress.setText(action.getShowProgressText());
					}
					progress.open();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public IActionExecutorCallback getCallback() {
		return callback;
	}

	public void setCallback(IActionExecutorCallback callback) {
		this.callback = callback;
	}

}
