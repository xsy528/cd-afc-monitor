/**
 * 
 */
package com.insigma.commons.framework.view.dialog.confirm;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.IViewComponent;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.WindowCloseListener;
import com.insigma.commons.framework.function.dialog.StandardDialogFunction;
import com.insigma.commons.framework.view.dialog.FrameworkDialog;

/**
 * @author DingLuofeng
 *    
 */
public class ExitConfirmDialog extends FrameworkDialog {

	List<WindowCloseListener> dataList;

	private StandardDialogFunction function;

	public ExitConfirmDialog(Shell parent, int style) {
		super(parent, style);
		function = new StandardDialogFunction();
		function.setTitle("退出确认");
		function.setWidth(400);
		function.setHeight(450);
		function.setText("退出确认");
		function.setDescription("描述：选择需要保存的资源信息");
		setFunction(function);

	}

	@Override
	protected IViewComponent createComposite() {
		ExitConfirmComposite exitConfirmComposite = new ExitConfirmComposite(shell, SWT.CHECK);
		Table table = exitConfirmComposite.createTree(dataList);
		addActions(function, table);
		return exitConfirmComposite;
	}

	public void setObjectList(List<WindowCloseListener> dataList) {
		this.dataList = dataList;
	}

	public void addActions(StandardDialogFunction function, Table table) {
		function.addAction(new TableItemSelectAction(table, true));
		function.addAction(new TableItemSelectAction(table, false));
		function.addAction(new ConfirmAction());
		function.addAction(new ExitCancelAction());
	}

	@Override
	public void setResponse(Response response) {
		result = response.getValue();
		if ((response.getCode() & IResponseCode.DIALOG_CLOSE) != 0) {
			if (!shell.isDisposed()) {
				shell.close();
			}
		}
	}

}
