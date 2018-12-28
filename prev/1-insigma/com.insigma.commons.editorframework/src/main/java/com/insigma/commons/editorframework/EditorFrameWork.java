/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.insigma.commons.application.Application;
import com.insigma.commons.ui.task.PeriodicalTask;
import com.insigma.commons.ui.widgets.MinimizeButtonContainer;
import com.insigma.commons.ui.widgets.SashForm;
import com.swtdesigner.SWTResourceManager;

public class EditorFrameWork extends Composite {

	protected Log logger = LogFactory.getLog(getClass());

	private SashForm leftRight;

	private SashForm upDown;

	private SashForm middleRight;

	private FrameWorkViewFolder leftfolder;

	private FrameWorkViewFolder bottomfolder;

	private FrameWorkViewFolder editor;

	private FrameWorkViewFolder rightfolder;

	private Map<Class<?>, FrameWorkView> views;

	private Map<String, FrameWorkView> namedViews;

	private ToolBar toolBar;

	private List<Action> actions;

	private int[] leftRightW = new int[] { 15, 85 };

	private int[] middleRightW = new int[] { 85, 15 };

	private int[] upDownW = new int[] { 85, 15 };

	private ToolItemSelection toolItemSelection = new ToolItemSelection();

	private List<PeriodicalTask> tasks = new ArrayList<PeriodicalTask>();
	private MinimizeButtonContainer leftBtn;
	private MinimizeButtonContainer rightBtn;

	public class RefreshPeriodicalTask extends PeriodicalTask {

		private FrameWorkView view;

		public RefreshPeriodicalTask(FrameWorkView view) {
			this.view = view;
		}

		@Override
		public void execute() {
			view.refresh();
			setInterval(view.getRefreshInterval());
		}
	}

	public class ActionPeriodicalTask extends PeriodicalTask {

		private Action action;

		public ActionPeriodicalTask(Action action) {
			this.action = action;
		}

		@Override
		public void execute() {
			EditorFrameWork frameWork = EditorFrameWork.this;
			if (action != null && !frameWork.isDisposed()) {
				ActionContext context = new ActionContext(action);
				context.setFrameWork(frameWork);
				action.perform(context);
			}
		}

	}

	public void addRefreshView(FrameWorkView view, int interval) {
		RefreshPeriodicalTask task = new RefreshPeriodicalTask(view);
		task.setInterval(interval);
		tasks.add(task);
		task.start();
	}

	public void addScheduleAction(Action action, int interval) {
		ActionPeriodicalTask task = new ActionPeriodicalTask(action);
		task.setInterval(interval);
		tasks.add(task);
		task.start();
	}

	public void cancelSchedule(Action action) {
		for (PeriodicalTask task : tasks) {
			if (task instanceof ActionPeriodicalTask) {
				ActionPeriodicalTask at = (ActionPeriodicalTask) task;
				if (at.action.equals(action)) {
					task.cancel();
				}
			}
		}

	}

	public void cancelScheduleAll() {
		for (PeriodicalTask task : tasks) {
			task.cancel();
		}

	}

	public void schedule() {
		for (PeriodicalTask task : tasks) {
			task.start();
		}
	}

	/*重新调度*/
	public void reschedule() {
		for (PeriodicalTask task : tasks) {
			task.cancel();
		}
		for (PeriodicalTask task : tasks) {
			task.run();
		}
	}

	public class ToolItemSelection extends SelectionAdapter {
		public void widgetSelected(SelectionEvent arg0) {
			if (arg0.widget instanceof ToolItem) {
				ToolItem item = (ToolItem) arg0.widget;
				if (item.getData() != null && item.getData() instanceof Action) {
					final Action action = ((Action) item.getData());
					action.setFrameWork(EditorFrameWork.this);

					if (action.showProgress) {
						Action innerAction = new Action("showProgress");
						innerAction.setShowProgressText(action.showProgressText);
						innerAction.setHandler(new ActionHandlerAdapter() {
							@Override
							public void perform(final ActionContext context) {
								action.perform(context);
							}
						});
						ProgressBarTaskManager taskManager = new ProgressBarTaskManager();
						taskManager.submit(innerAction);
					} else {
						ActionContext context = new ActionContext(action);
						context.setFrameWork(EditorFrameWork.this);
						action.perform(context);
					}
					updateToolBar(toolBar);
					// updateToolAction(action, item);
				}
			}
			if (arg0.widget instanceof MenuItem) {
				MenuItem item = (MenuItem) arg0.widget;
				if (item.getData() != null && item.getData() instanceof Action) {
					final Action action = ((Action) item.getData());
					action.setFrameWork(EditorFrameWork.this);

					if (action.showProgress) {
						Action innerAction = new Action("showProgress");
						innerAction.setHandler(new ActionHandlerAdapter() {
							@Override
							public void perform(final ActionContext context) {
								action.perform(context);
							}
						});
						ProgressBarTaskManager taskManager = new ProgressBarTaskManager();
						taskManager.submit(innerAction);
					} else {
						ActionContext context = new ActionContext(action);
						context.setFrameWork(EditorFrameWork.this);
						action.perform(context);
					}

					updateMenu(item.getParent());
				}
			}
		}
	}

	public EditorFrameWork(Composite arg0, int arg1) {

		super(arg0, arg1);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.numColumns = 3;
		setLayout(gridLayout);

		views = new HashMap<Class<?>, FrameWorkView>();
		namedViews = new HashMap<String, FrameWorkView>();

		toolBar = new ToolBar(this, SWT.NONE);
		toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));

		leftBtn = new MinimizeButtonContainer(this, SWT.NONE);

		leftRight = new SashForm(this, SWT.HORIZONTAL);
		leftRight.setLayoutData(new GridData(GridData.FILL_BOTH));

		leftfolder = new FrameWorkViewFolder(leftRight, SWT.BORDER);
		leftfolder.setEditorFrameWork(this);
		upDown = new SashForm(leftRight, SWT.VERTICAL);

		middleRight = new SashForm(upDown, SWT.HORIZONTAL);
		bottomfolder = new FrameWorkViewFolder(upDown, SWT.BORDER);
		bottomfolder.setEditorFrameWork(this);

		editor = new FrameWorkViewFolder(middleRight, SWT.BORDER);
		editor.setEditorFrameWork(this);

		rightfolder = new FrameWorkViewFolder(middleRight, SWT.BORDER);
		rightfolder.setEditorFrameWork(this);

		leftRight.setWeights(new int[] { 0, 100 });
		upDown.setWeights(new int[] { 100, 0 });
		middleRight.setWeights(new int[] { 100, 0 });

		rightBtn = new MinimizeButtonContainer(this, SWT.NONE);

		{
			leftfolder.setButtonContainer(leftBtn);
			rightfolder.setButtonContainer(rightBtn);
			editor.setButtonContainer(rightBtn);
			bottomfolder.setButtonContainer(rightBtn);
		}
		schedule();
	}

	//	private boolean checkFunction(FrameWorkView view) {
	//		{
	//			// 权限验证
	//			String fid = view.getFunctionID();
	//			if (fid == null) {
	//				logger.warn("view:[" + view.getText()
	//						+ "]没有定义functionId，默认初始化该view");
	//				// continue;
	//			} else {
	//				boolean hasAuth = Application.getUser().hasFunction(fid);
	//				if (!hasAuth) {
	//					logger.debug("用户：[" + Application.getUser().getUserName()
	//							+ "]没有view:" + view + "的权限,忽略该view的初始化");
	//					if (view != null && !view.isDisposed()) {
	//						view.dispose();
	//					}
	//					return false;
	//				}
	//			}
	//		}
	//		return true;
	//	}

	public void addView(FrameWorkView view) {
		addView(view, SWT.LEFT);
	}

	public void addView(String name, FrameWorkView view, int pos) {
		namedViews.put(name, view);
		addView(view, pos);
		Action refreshAction = view.getRefreshAction();
		if (refreshAction != null) {
			refreshAction.setFrameWork(this);
			ActionContext context = new ActionContext(refreshAction);
			context.setFrameWork(EditorFrameWork.this);
			refreshAction.perform(context);
		}
	}

	public void addView(FrameWorkView view, int pos) {
		switch (pos) {
		case SWT.LEFT:
			leftfolder.addView(view);
			leftRight.setWeights(leftRightW);
			break;
		case SWT.RIGHT:
			rightfolder.addView(view);
			middleRight.setWeights(middleRightW);
			break;
		case SWT.BOTTOM:
			upDown.setWeights(upDownW);
			bottomfolder.addView(view);
			break;
		case SWT.CENTER:
			editor.addView(view);
			break;
		}
		view.setFrameWork(this);
		views.put(view.getClass(), view);
		if (view.getRefreshInterval() != 0) {
			addRefreshView(view, view.getRefreshInterval());
		}
	}

	public FrameWorkView getView(String name) {
		if (namedViews.containsKey(name)) {
			return namedViews.get(name);
		}
		return null;
	}

	public FrameWorkView getView(Class<?> id) {
		if (views.containsKey(id)) {
			return views.get(id);
		}
		return null;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
		updateToolBar(toolBar, actions);
	}

	public void updateToolBar(ToolBar toolbar, List<Action> actions) {

		for (int i = 0; i < toolbar.getItemCount(); i++) {
			toolbar.getItem(0).dispose();
		}
		// 构建工具栏
		for (int i = 0; i < actions.size(); i++) {
			Action action = actions.get(i);
			{
				// 权限验证
				String fid = action.getID();
				if (fid == null) {
					logger.warn("action:[" + action.getName() + "]没有定义functionId，默认初始化该action");
					// continue;
				} else {
					boolean hasAuth = Application.getUser().hasFunction(fid);
					if (!hasAuth) {
						logger.debug("用户：[" + Application.getUser().getUserName() + "]没有action:" + action
								+ "的权限,忽略该action的初始化");
						continue;
					}
				}
			}
			// 多级子项
			if (action.getItems().size() != 0) {
				final ToolItem item = new ToolItem(toolbar, SWT.DROP_DOWN);

				final Menu menu = new Menu(toolbar);
				for (Action subAction : action.getItems()) {
					MenuItem mitem = new MenuItem(menu, SWT.CHECK);
					mitem.setText(subAction.getName());
					mitem.setData(subAction);
					mitem.setSelection(subAction.isChecked());
					mitem.addSelectionListener(toolItemSelection);
				}
				item.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						if (event.detail == SWT.ARROW) {
							Rectangle rect = item.getBounds();
							Point pt = new Point(rect.x, rect.y + rect.height);
							pt = toolBar.toDisplay(pt);
							menu.setLocation(pt.x, pt.y);
							menu.setVisible(true);
						}
					}
				});
				item.setData(action);
			} else {
				final ToolItem item = new ToolItem(toolbar, SWT.NONE);
				item.addSelectionListener(toolItemSelection);
				item.setData(action);
			}
		}
		updateToolBar(toolbar);
	}

	protected void updateToolBar(ToolBar toolbar) {
		for (ToolItem item : toolbar.getItems()) {
			Action action = (Action) item.getData();
			action.setFrameWork(this);
			updateToolAction(action, item);
		}
	}

	public void updateToolBar() {
		updateToolBar(toolBar);
	}

	protected void updateToolAction(Action action, ToolItem item) {
		//		logger.debug("验证工具栏使能权限:" + action.getName());
		if (action.getName() != null) {
			item.setText(action.getName());
		}
		if (action.getImage() != null) {
			item.setImage(SWTResourceManager.getImage25x25(action.getClass(), action.getImage()));
		}
		item.setEnabled(action.IsEnable());
	}

	public void updateMenu(Menu menu) {
		for (MenuItem item : menu.getItems()) {
			Action action = (Action) item.getData();
			updateMenuItem(action, item);
		}
	}

	protected void updateMenuItem(Action action, MenuItem item) {
		if (action.getName() != null) {
			item.setText(action.getName());
		}
		if (action.getImage() != null) {
			item.setImage(SWTResourceManager.getImage(action.getClass(), action.getImage()));
		}
		item.setSelection(action.isChecked());
	}

	public void toggleView(int view, boolean isViewLeft) {
		switch (view) {
		case SWT.LEFT:
			if (leftRight.getWeights()[0] == 0 && isViewLeft) {
				leftRight.setWeights(leftRightW);
			} else {
				leftRightW = leftRight.getWeights();
				leftRight.setWeights(new int[] { 0, 100 });
			}
			break;
		case SWT.RIGHT:
			if (middleRight.getWeights()[1] == 0) {
				middleRight.setWeights(middleRightW);
			} else {
				middleRightW = middleRight.getWeights();
				middleRight.setWeights(new int[] { 100, 0 });
			}
			break;
		case SWT.BOTTOM:
			if (upDown.getWeights()[1] == 0) {
				upDown.setWeights(upDownW);
			} else {
				upDownW = upDown.getWeights();
				upDown.setWeights(new int[] { 100, 0 });
			}
			break;
		}
	}

	public Collection<FrameWorkView> getViews() {
		List<FrameWorkView> _views = new ArrayList<FrameWorkView>();
		_views.addAll(this.views.values());
		_views.addAll(namedViews.values());
		return _views;
	}

	public int[] getMiddleRightW() {
		return middleRightW;
	}

	public void setMiddleRightW(int[] middleRightW) {
		this.middleRightW = middleRightW;
	}

	public int[] getUpDownW() {
		return upDownW;
	}

	public void setUpDownW(int[] upDownW) {
		this.upDownW = upDownW;
	}

	public int[] getLeftRightW() {
		return leftRightW;
	}

	public void setLeftRightW(int[] leftRightW) {
		this.leftRightW = leftRightW;
	}

	@Override
	public void dispose() {
		cancelScheduleAll();
		super.dispose();
	}

	public FrameWorkViewFolder getEditor() {
		return editor;
	}

	public void setEditor(FrameWorkViewFolder editor) {
		this.editor = editor;
	}

}
