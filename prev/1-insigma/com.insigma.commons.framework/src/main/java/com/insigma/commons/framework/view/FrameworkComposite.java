/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @history       
 */
package com.insigma.commons.framework.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;

import com.insigma.commons.application.Application;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.function.dialog.DialogFunction;
import com.insigma.commons.ui.widgets.ToolBar;
import com.swtdesigner.SWTResourceManager;

public class FrameworkComposite extends CompositeFrameWork {

	protected Module module;

	protected ToolBar maintoolbar;

	protected ToolItemSelectionAdapter toolItemSelectionAdapter = new ToolItemSelectionAdapter();

	class ToolItemSelectionAdapter extends SelectionAdapter {

		private ToolItem preItem = null;

		public void widgetSelected(SelectionEvent arg0) {
			ToolItem item = (ToolItem) arg0.widget;
			if (!item.getSelection()) {
				preItem = item;
				return;
			}
			Object data = arg0.widget.getData();
			if (data == null || !(data instanceof Function)) {
				return;
			}
			Function function = (Function) data;
			handleFunction(function);

			focus(function, preItem, item);

		}
	}

	public void focus(Function function, ToolItem preItem, ToolItem curItem) {

		if (function.getRelateFunction() != null) {
			// focus(function.getRelateFunction(), preItem, curItem);
			for (ToolItem item : maintoolbar.getItems()) {
				if (item.getData() == function.getRelateFunction()) {
					item.setSelection(true);
				} else {
					item.setSelection(false);
				}
			}
			return;
		}
		// 暂时不处理ActionFunction
		if (function instanceof DialogFunction) {
			curItem.setSelection(false);
			if (preItem != null) {
				preItem.setSelection(true);
			}
		}

	}

	public Module getModule() {
		return module;
	}

	public FrameworkComposite(Composite parent, int style) {
		super(parent, style | SWT.BORDER);

	}

	public void setModule(Module module) {
		this.module = module;
		List<Function> notDefinedFunctions = new ArrayList<Function>();

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		setLayout(layout);

		if (module.getFunction() != null && module.getFunction().size() > 0) {
			maintoolbar = new ToolBar(this, SWT.NONE);
			maintoolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		createMainComposite();

		boolean bo = false;
		Function fun = null;
		for (Function function : module.getFunction()) {
			String fid = function.getFunctionID();
			if (fid == null) {
				notDefinedFunctions.add(function);
			} else {
				boolean hasAuth = Application.getUser().hasFunction(fid);
				if (!hasAuth) {
					continue;
				}
			}
			if (fun == null) {
				fun = function;
			}
			if (maintoolbar != null) {
				if (function.isDefault()) {
					bo = true;
					break;
				}
			}
		}
		if (!bo && fun != null) {
			fun.setDefault(true);
		}
		for (Function function : module.getFunction()) {
			String fid = function.getFunctionID();
			if (fid == null) {
				logger.warn("function:[" + function.getTitle() + "]没有定义functionId,默认初始化该function");
				notDefinedFunctions.add(function);
			} else {
				boolean hasAuth = Application.getUser().hasFunction(fid);
				if (!hasAuth) {
					logger.debug("用户:[" + Application.getUser().getUserName() + "]没有function:" + function
							+ "的权限,忽略该function的初始化");
					continue;
				}
			}

			if (maintoolbar != null) {
				// int style = SWT.RADIO;
				// if (function.getRelateFunction() != null) {
				// style = SWT.NORMAL;
				// }
				ToolItem toolitem = new ToolItem(maintoolbar, SWT.RADIO);
				toolitem.setData(function);
				toolitem.setImage(SWTResourceManager.getImage25x25(FrameworkComposite.class, function.getImage()));
				toolitem.setText(function.getTitle());
				toolitem.addSelectionListener(toolItemSelectionAdapter);
				if (function.isDefault()) {
					toolitem.setSelection(true);
				}
			}
			if (function.isDefault()) {
				handleFunction(function);
			}
		}
		validate(null);
	}

	public void validate(Request request) {
		if (maintoolbar != null) {
			for (ToolItem item : maintoolbar.getItems()) {
				Function function = (Function) item.getData();
				if (function.getFunctionValidator() != null) {
					item.setEnabled(function.getFunctionValidator().validate(request));
				}
			}
		}
	}
}
