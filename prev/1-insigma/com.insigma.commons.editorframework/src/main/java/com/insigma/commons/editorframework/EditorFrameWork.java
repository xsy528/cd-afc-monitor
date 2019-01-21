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

	private List<PeriodicalTask> tasks = new ArrayList<>();
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

	public void addRefreshView(FrameWorkView view, int interval) {
		RefreshPeriodicalTask task = new RefreshPeriodicalTask(view);
		task.setInterval(interval);
		tasks.add(task);
		task.start();
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

	public FrameWorkView getView(Class<?> id) {
		if (views.containsKey(id)) {
			return views.get(id);
		}
		return null;
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

	public Collection<FrameWorkView> getViews() {
		List<FrameWorkView> _views = new ArrayList<FrameWorkView>();
		_views.addAll(this.views.values());
		_views.addAll(namedViews.values());
		return _views;
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
