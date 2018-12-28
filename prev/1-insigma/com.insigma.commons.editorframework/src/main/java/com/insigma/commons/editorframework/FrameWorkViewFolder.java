/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;

import com.insigma.commons.ui.widgets.CTabFolder;
import com.insigma.commons.ui.widgets.ToolBar;
import com.swtdesigner.SWTResourceManager;

public class FrameWorkViewFolder extends CTabFolder {

	private ToolBar bar;

	private Composite toolbar;

	private EditorFrameWork editorFrameWork;

	public EditorFrameWork getEditorFrameWork() {
		return editorFrameWork;
	}

	public void setEditorFrameWork(EditorFrameWork editorFrameWork) {
		this.editorFrameWork = editorFrameWork;
	}

	public FrameWorkViewFolder(Composite arg0, int arg1) {
		super(arg0, arg1);
		this.setTabHeight(20);
		setMaximizeVisible(true);
		setMinimizeVisible(true);

		toolbar = new Composite(FrameWorkViewFolder.this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginBottom = 0;
		layout.marginTop = 0;
		layout.marginHeight = 0;

		layout.numColumns = 2;
		toolbar.setLayout(layout);
		Composite hintBar = new Composite(toolbar, SWT.NONE);
		hintBar.setLayoutData(new GridData(GridData.FILL_BOTH));
		bar = new ToolBar(toolbar, SWT.NONE);

		setTopRight(toolbar, SWT.FILL);

		addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				CTabItem item = FrameWorkViewFolder.this.getSelection();
				select(item);
			}
		});
	}

	protected void select(CTabItem item) {
		if (item != null && item.getControl() instanceof FrameWorkView) {
			FrameWorkView view = (FrameWorkView) item.getControl();
			Action[] actions = view.getActions();
			for (int i = 0; i < actions.length; i++) {
				Action action = actions[i];
				ToolItem toolitem = null;
				if (i < bar.getItemCount()) {
					toolitem = bar.getItem(i);
					toolitem.setData(action);
					toolitem.setImage(SWTResourceManager.getImage16x16(ActionTreeView.class, action.getImage()));
				} else {
					toolitem = new ToolItem(bar, SWT.NONE);
					toolitem.setImage(SWTResourceManager.getImage16x16(ActionTreeView.class, action.getImage()));
					toolitem.setData(action);
					toolitem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							ToolItem item = (ToolItem) arg0.widget;
							if (item.getData() != null && item.getData() instanceof Action) {
								Action action = (Action) item.getData();
								ActionContext context = new ActionContext(action);
								context.setFrameWork(editorFrameWork);
								action.setData(item);
								action.perform(context);
							}
						}
					});
				}
			}
			int remains = bar.getItemCount() - actions.length;
			for (int i = 0; i < remains; i++) {
				bar.getItem(bar.getItemCount() - 1).dispose();
			}
			layout();
			toolbar.layout(true);
		}
	}

	public void addView(FrameWorkView view) {
		CTabItem item = new CTabItem(this, SWT.NONE);
		if (view.getIcon() != null) {
			item.setImage(SWTResourceManager.getImage(view.getClass(), view.getIcon()));
		}
		item.setText(view.getText());
		view.setParent(this);
		view.setData(view.getClass().getName(), item);
		item.setControl(view);
		setSelection(0);
		select(getItem(0));
	}
}
