package com.insigma.commons.framework.view;

import java.lang.reflect.Constructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.dialog.CustomDialogFunction;
import com.insigma.commons.framework.function.dialog.DialogFunction;
import com.insigma.commons.framework.function.dialog.FileDialogFunction;
import com.insigma.commons.framework.function.dialog.FileDialogFunction.FileDialogType;
import com.insigma.commons.framework.function.dialog.MessageDialogFunction;
import com.insigma.commons.framework.function.dialog.ModuleDialogFunction;
import com.insigma.commons.framework.function.dialog.StandardDialogFunction;
import com.insigma.commons.framework.function.dialog.UserDialogFunction;
import com.insigma.commons.framework.function.form.FormFunction;
import com.insigma.commons.framework.function.search.SearchFunction;
import com.insigma.commons.framework.function.table.MultiTableViewFunction;
import com.insigma.commons.framework.function.table.TableViewFunction;
import com.insigma.commons.framework.view.ActionExecutor.IActionExecutorCallback;
import com.insigma.commons.framework.view.dialog.CustomViewDialog;
import com.insigma.commons.framework.view.dialog.FormViewDialog;
import com.insigma.commons.framework.view.dialog.ModuleDialog;
import com.insigma.commons.framework.view.dialog.MultiTableViewDialog;
import com.insigma.commons.framework.view.dialog.SearchViewDialog;
import com.insigma.commons.framework.view.dialog.TableViewDialog;
import com.insigma.commons.ui.IDialog;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.FileDialog;

public class DialogFunctionHandler {

	private Log logger = LogFactory.getLog(DialogFunctionHandler.class);

	protected IActionExecutorCallback callback;

	private Shell shell;

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public void process(DialogFunction function, Request request, IResponseDisplayer responseDisplayer) {
		if (function instanceof FileDialogFunction) {
			handleOpenDialogFunction((FileDialogFunction) function, responseDisplayer);
			return;
		}
		if (function instanceof ModuleDialogFunction) {
			handleModuleDialogFunction((ModuleDialogFunction) function);
			return;
		}

		if (function instanceof UserDialogFunction) {
			handleDialogFunction((UserDialogFunction) function);
			return;
		}
		if (function instanceof StandardDialogFunction) {
			handleCustomDialogFunction((StandardDialogFunction) function, request, responseDisplayer);
			return;
		}

		if (function instanceof MessageDialogFunction) {
			handleMessageDialogFunction((MessageDialogFunction) function, request, responseDisplayer);
			return;
		}
	}

	protected void handleMessageDialogFunction(MessageDialogFunction function, Request request,
			IResponseDisplayer responseDisplayer) {
		try {
			String description = function.getDescription();
			if (function.getOpenAction() != null && function.getOpenAction().getActionHandler() != null) {
				Response response = function.getOpenAction().getActionHandler().perform(request);
				if (response.getMessages().size() > 0) {
					description = response.getMessages().get(0).getMessage();
				}
			}
			if (MessageForm.Query(function.getTitle(), description) != SWT.NO) {
				executeCloseAction(function, request, responseDisplayer);
			}
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}

	/**
	 * 处理标准对话框功能
	 * 
	 * @param function
	 */
	protected void handleDialogFunction(UserDialogFunction function) {
		Class<?> dialog = function.getDialogClass();
		try {
			Constructor<?> constructor = dialog.getConstructor(Shell.class);
			Object dg = constructor.newInstance(getShell());
			if (dg instanceof IDialog) {
				IDialog dlg = (IDialog) dg;
				dlg.open();
			}
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}

	protected void handleOpenDialogFunction(FileDialogFunction function, IResponseDisplayer responseDisplayer) {
		try {
			String path = null;
			if (function.getDialogType() == FileDialogType.OPEN_DIR) {
				DirectoryDialog dlg = new DirectoryDialog(getShell(), SWT.OPEN);
				path = dlg.open();

			}
			if (function.getDialogType() == FileDialogType.SAVE_DIR) {
				DirectoryDialog dlg = new DirectoryDialog(getShell(), SWT.SAVE);
				path = dlg.open();
			}
			if (function.getDialogType() == FileDialogType.OPEN_FILE) {
				FileDialog dlg = new FileDialog(getShell(), SWT.OPEN);
				if (function.getFilter() != null) {
					dlg.setFilterExtensions(function.getFilter());
				}
				path = dlg.open();
			}
			if (function.getDialogType() == FileDialogType.SAVE_FILE) {
				FileDialog dlg = new FileDialog(getShell(), SWT.SAVE);
				if (function.getFilter() != null) {
					dlg.setFilterExtensions(function.getFilter());
				}
				path = dlg.open();
			}
			if (path != null) {
				Request request = new Request();
				request.setValue(path);
				executeCloseAction(function, request, responseDisplayer);
			}
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}

	protected void handleModuleDialogFunction(ModuleDialogFunction function) {
		try {
			ModuleDialog dlg = new ModuleDialog(getShell());
			dlg.setFunction(function);
			dlg.open();
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}

	protected void handleCustomDialogFunction(StandardDialogFunction func, Request request,
			IResponseDisplayer responseDisplayer) {
		try {
			if (func instanceof CustomDialogFunction) {
				CustomViewDialog dialog = new CustomViewDialog(getShell());
				dialog.setFunction(func);
				dialog.setOpenRequest(request);
				dialog.open();
				executeCloseAction(func, request, responseDisplayer);
				return;
			}

			Function function = func.getFunction();
			if (function instanceof SearchFunction) {
				SearchViewDialog dialog = new SearchViewDialog(getShell());
				dialog.setFunction(func);
				dialog.setOpenRequest(request);
				dialog.open();
				executeCloseAction(func, request, responseDisplayer);
				return;
			}

			if (function instanceof TableViewFunction) {
				TableViewDialog dialog = new TableViewDialog(getShell());
				dialog.setFunction(func);
				dialog.setOpenRequest(request);
				dialog.open();
				executeCloseAction(func, request, responseDisplayer);
				return;
			}

			if (function instanceof MultiTableViewFunction) {
				MultiTableViewDialog dialog = new MultiTableViewDialog(getShell());
				dialog.setFunction(func);
				dialog.setOpenRequest(request);
				dialog.open();
				executeCloseAction(func, request, responseDisplayer);
				return;
			}

			if (function instanceof FormFunction) {
				FormViewDialog<Object> dialog = new FormViewDialog<Object>(getShell());
				dialog.setFunction(func);
				dialog.setOpenRequest(request);
				dialog.open();
				executeCloseAction(func, request, responseDisplayer);
				return;
			}
		} catch (Exception e) {
			logger.error("Action执行失败", e);
		}
	}

	/**
	 * 创建时间 2010-10-8 下午05:16:11<br>
	 * 描述：标准对话框执行关闭Action，如果存在CloseAction
	 * 
	 * @param func
	 * @param request
	 * @param responseDisplayer
	 */
	private void executeCloseAction(DialogFunction func, Request request, IResponseDisplayer responseDisplayer) {
		if (func.getCloseAction() != null) {
			ActionExecutor executor = new ActionExecutor(responseDisplayer);
			executor.setCallback(callback);
			executor.execute(func.getCloseAction(), request);
		}
	}

	public IActionExecutorCallback getCallback() {
		return callback;
	}

	public void setCallback(IActionExecutorCallback callback) {
		this.callback = callback;
	}

}
