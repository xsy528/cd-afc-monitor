package com.insigma.commons.editorframework.iframework;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import com.insigma.commons.editorframework.graphic.editor.BrowserView;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.view.FunctionComposite;
import com.insigma.commons.framework.view.form.FormEditor;
import com.insigma.commons.ui.form.IEditorChangedListener;
import com.insigma.commons.ui.task.PeriodicalTask;
import com.insigma.commons.ui.tree.ObjectTree;
import com.insigma.commons.ui.widgets.CTabFolder;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.ToolBar;
import com.insigma.commons.ui.widgets.ToolItem;
import com.swtdesigner.SWTResourceManager;

/**
 * 创建时间 2010-9-30 下午04:44:01 <br>
 * 描述: 显示线路地图<br>
 * Ticket：
 *
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class MapViewComposite extends FunctionComposite {

	private MapFunction function;

	private FormEditor formEditor;

	private ObjectTree leftTree;

	private BrowserView browserView;

	protected Date date;

	protected String timeSection;

	private int refreshInterval = 0;

	private RefreshPeriodicalTask task;

	private Response response;

	public class RefreshPeriodicalTask extends PeriodicalTask {

		@Override
		public void execute() {
			try {
				if (function.getRefreshAction() != null) {
					// 刷新form界面
					if (formEditor != null && !formEditor.isDisposed()) {
						Control[] controls = formEditor.getChildren();
						if (controls != null && controls.length >= 4) {
							if (controls.length >= 6) {
								Integer refreshType = (Integer) ((IInputControl) controls[5]).getObjectValue();
								if (refreshType == 0) {// 手动
									return;
								}
							}
							date = function.getDate();
							timeSection = function.getTimeSection();
							((IInputControl) controls[1]).setObjectValue(date);
							((IInputControl) controls[3]).setObjectValue(timeSection);
						}
						// 刷新地图界面
						performAction(function.getRefreshAction());
					}
				}
			} catch (Exception e) {
				logger.error("断面客流自动刷新失败", e);
			}
		}
	}

	public MapViewComposite(Composite parent, int style) {
		super(parent, style);
		setBackground(getParent().getBackground());

		addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent arg0) {
				if (task != null) {
					task.cancel();
					task.setEnable(false);
					task = null;
				}
			}
		});
	}

	protected void createTree() {
		CTabFolder folder = new CTabFolder(this, SWT.BORDER);
		GridData treeGridData = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 4);
		treeGridData.widthHint = 188;
		folder.setLayoutData(treeGridData);
		folder.setTabHeight(22);

		Composite toolbar = new Composite(folder, SWT.NONE);
		GridLayout barlayout = new GridLayout();
		barlayout.horizontalSpacing = 0;
		barlayout.verticalSpacing = 0;
		barlayout.marginBottom = 0;
		barlayout.marginTop = 0;
		barlayout.marginHeight = 0;
		barlayout.numColumns = 2;
		toolbar.setLayout(barlayout);
		Composite hintBar = new Composite(toolbar, SWT.NONE);
		hintBar.setLayoutData(new GridData(GridData.FILL_BOTH));
		ToolBar bar = new ToolBar(toolbar, SWT.NONE);
		folder.setTopRight(toolbar, SWT.FILL);

		final ToolItem subitem = new ToolItem(bar, SWT.NONE);
		final ToolItem additem = new ToolItem(bar, SWT.NONE);
		subitem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (leftTree.getDepth() <= 1) {
					subitem.setEnabled(false);
					return;
				}
				leftTree.setDepth(leftTree.getDepth() - 1);
				if (leftTree.getDepth() <= 1) {
					subitem.setEnabled(false);
				} else {
					subitem.setEnabled(true);
				}

				additem.setEnabled(true);
			}
		});
		subitem.setImage(SWTResourceManager.getImage(getClass(), "/com/insigma/commons/framework/images/left.png"));

		additem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (leftTree.getDepth() >= leftTree.getMaxDepth()) {
					additem.setEnabled(false);
					return;
				}
				leftTree.setDepth(leftTree.getDepth() + 1);
				if (leftTree.getDepth() >= leftTree.getMaxDepth()) {
					additem.setEnabled(false);
				} else {
					additem.setEnabled(true);
				}

				subitem.setEnabled(true);

			}
		});
		additem.setImage(SWTResourceManager.getImage(getClass(), "/com/insigma/commons/framework/images/right.png"));
		bar.layout();

		toolbar.layout();

		toolbar.setVisible(false);

		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText(function.getTreeLabel());

		leftTree = new ObjectTree(folder, SWT.BORDER | SWT.CHECK);// 可变为单选
		leftTree.setTreeProvider(function.getTreeProvider());
		leftTree.setExpandedAll(true);
		leftTree.setCheck(leftTree.getItems(), true);

		item.setControl(leftTree);

		folder.setSelection(0);

	}

	public void setFunction(MapFunction func) {

		if (this.function != null) {
			return;
		}
		this.function = func;
		context.setFunction(function);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		setLayout(layout);

		if (function.getTreeProvider() != null) {
			createTree();
		}

		EnhanceComposite composite = new EnhanceComposite(this, SWT.BORDER);
		// composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_composite.heightHint = 86;// 设置高度，可以从外面传进来
		composite.setLayoutData(gd_composite);
		composite.setLayout(new GridLayout());
		composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		Form form = function.getForm();
		if (form != null) {
			/*
			 * form.changedListener = (event, isChanged) ->
			 * performAction(function.getRefreshAction());
			 */

			form.changedListener = new IEditorChangedListener() {
				@Override
				public void editorChanged(Event evet, boolean isChanged) {
					performAction(function.getRefreshAction());
				}
			};

			formEditor = new FormEditor(composite, SWT.NONE);
			GridData pdata = new GridData(GridData.FILL_HORIZONTAL);
			formEditor.setLayoutData(pdata);
			formEditor.setBackground(formEditor.getParent().getBackground());
			formEditor.setForm(form);
			formEditor.setSize(formEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			formEditor.layout();
		}

		browserView = new BrowserView(this, SWT.NONE);
		browserView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		if (function.getOpenAction() != null) {
			performAction(function.getOpenAction());
		}

		if (function.getActions() != null && function.getActions().size() > 0) {
			buildButtonBar(composite, function.getActions());
		}

		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));// 设置高度，可以从外面传进来
		composite.layout();

		schedule();
	}

	/**
	 *
	 */
	private void schedule() {
		setRefreshInterval(function.getRefreshInterval());
		if (refreshInterval > 0) {
			task = new RefreshPeriodicalTask();
			task.setInterval(refreshInterval * 1000);
			task.start();
		}
	}

	public Request getRequest() {

		SearchRequest request = new SearchRequest();
		if (formEditor != null) {
			request.setForm(formEditor.getForm());
		}
		if (leftTree != null) {
			request.setValue(leftTree.getChecked());
		}
		return request;
	}

	@Override
	public void refresh() {
		formEditor.setForm(function.getForm());
		browserView.refresh(response);
	}

	@SuppressWarnings("unchecked")
	public void setResponse(Response resp) {
		response = resp;
	}

	public Function getFunction() {
		return function;
	}

	public int getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	public FormEditor getFormEditor() {
		return formEditor;
	}

}
