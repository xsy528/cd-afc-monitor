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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.Context;
import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.action.FunctionAction;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.view.ActionExecutor.IActionExecutorCallback;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.spring.ServiceWireHelper;
import com.insigma.commons.ui.widgets.Button;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IControl;
import com.insigma.commons.ui.widgets.ToolBar;
import com.insigma.commons.ui.widgets.ToolItem;
import com.swtdesigner.SWTResourceManager;

public abstract class FunctionComposite extends EnhanceComposite implements IRequestGenerator, IResponseDisplayer {

	protected Log logger = LogFactory.getLog(getClass());

	protected Context context = new Context();

	protected CompositeFrameWork frameworkComposite;

	protected Font font;

	protected Point buttonSize;

	protected ServiceWireHelper actionHandlerServiceWire = new ServiceWireHelper();

	protected ActionButtonSelectionAdapter actionButtonSelectionAdapter = new ActionButtonSelectionAdapter();

	protected List<PairValue<Action, IControl>> actionMap = new ArrayList<PairValue<Action, IControl>>();;

	protected DialogFunctionHandler dialogFunctionHandler = new DialogFunctionHandler();

	protected IActionExecutorCallback callback = new IActionExecutorCallback() {
		public void finish() {
			Request request = getRequest();
			if (frameworkComposite != null) {
				frameworkComposite.validate(request);
			}
			validate(request);
		}
	};

	public CompositeFrameWork getFrameworkComposite() {
		return frameworkComposite;
	}

	public void setFrameworkComposite(CompositeFrameWork frameworkComposite) {
		this.frameworkComposite = frameworkComposite;
	}

	public void validate(Request request) {
		for (PairValue<Action, IControl> p : actionMap) {
			if (p.getKey().getActionHandler() != null) {
				request.setAction(p.getKey());
				boolean enable = p.getKey().getActionHandler().prepare(request);

				p.getValue().setEnabled(enable);
			}
		}
	}

	protected void performAction(Action action) {
		if (action != null) {
			try {
				logger.debug("执行[ " + action.getText() + " ]");
				Request request = getRequest();
				if (action instanceof FunctionAction) {
					FunctionAction faction = (FunctionAction) action;
					dialogFunctionHandler.setShell(getShell());
					dialogFunctionHandler.setCallback(callback);
					dialogFunctionHandler.process(faction.getDialogFunction(), request, FunctionComposite.this);
				} else {
					ActionExecutor executor = new ActionExecutor(this);
					executor.setCallback(callback);
					executor.execute(action, request);
				}
			} catch (Exception e) {
				logger.error("执行[ " + action.getText() + " ]错误", e);
			}
		}
	}

	private class ActionButtonSelectionAdapter extends SelectionAdapter {
		public void widgetSelected(SelectionEvent arg0) {
			Object data = arg0.widget.getData();
			if (data == null || !(data instanceof Action)) {
				return;
			}
			Action action = (Action) data;
			performAction(action);
		}
	}

	protected ToolBar buildToolBar(Composite composite, List<Action> actions) {

		ToolBar bar = new ToolBar(composite, SWT.NONE);
		bar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 20;
		gridLayout.marginWidth = 10;
		gridLayout.marginTop = 10;

		gridLayout.numColumns = actions.size();
		composite.setLayout(gridLayout);

		final GridData gd_composite = new GridData(SWT.RIGHT, SWT.FILL, false, false);
		gd_composite.heightHint = 50;
		composite.setLayoutData(gd_composite);

		for (Action action : actions) {
			ToolItem item = new ToolItem(bar, SWT.NONE);
			item.setText(action.getText());
			item.setData(action);
			if (action.getImage() != null) {
				Image image = SWTResourceManager.getImage(action.getClass(), action.getImage());
				if (image != null && image.getImageData().alphaData != null) {
					item.setImage(image);
				}
			}
			item.addSelectionListener(actionButtonSelectionAdapter);
			PairValue<Action, IControl> pvalue = new PairValue<Action, IControl>();
			pvalue.setKey(action);
			pvalue.setValue(item);
			actionMap.add(pvalue);
		}
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite.layout();
		composite.setBackground(composite.getParent().getBackground());

		return bar;

	}

	protected EnhanceComposite buildButtonBar(Composite parent, List<Action> actions) {

		EnhanceComposite composite = new EnhanceComposite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 20;
		gridLayout.marginWidth = 10;
		gridLayout.marginTop = 5;

		gridLayout.numColumns = actions.size();
		composite.setLayout(gridLayout);

		final GridData gd_composite = new GridData(SWT.RIGHT, SWT.FILL, false, false);
		gd_composite.heightHint = 50;
		composite.setLayoutData(gd_composite);

		for (Action action : actions) {
			Button item = new Button(composite, SWT.NONE);
			if (font != null) {
				item.setFont(font);
			}
			final GridData gdbutton = new GridData(SWT.FILL, SWT.FILL, false, false);
			if (this.buttonSize != null) {
				gdbutton.heightHint = buttonSize.y;
				gdbutton.widthHint = buttonSize.x;
			} else {
				gdbutton.heightHint = 30;
				gdbutton.widthHint = action.getLen();
			}
			item.setText(action.getText());
			item.setData(action);
			item.setLayoutData(gdbutton);
			item.addSelectionListener(actionButtonSelectionAdapter);
			PairValue<Action, IControl> pvalue = new PairValue<Action, IControl>();
			pvalue.setKey(action);
			pvalue.setValue(item);
			actionMap.add(pvalue);
		}
		composite.setBackground(parent.getBackground());

		return composite;

	}

	public Function getFunction() {
		return null;
	}

	public void refresh() {
		if (getFunction() != null && getFunction().getRefreshAction() != null) {
			Action refresh = getFunction().getRefreshAction();
			performAction(refresh);
		}
	}

	public FunctionComposite(Composite parent, int style) {
		super(parent, style);
	}
}
