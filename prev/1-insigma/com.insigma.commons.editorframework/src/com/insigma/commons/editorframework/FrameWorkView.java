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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.widgets.EnhanceComposite;

public class FrameWorkView extends EnhanceComposite {

	protected Log logger = LogFactory.getLog(getClass());

	private String text;

	private String icon;

	private List<Action> actions = new ArrayList<Action>();

	private Action refreshAction;

	private EditorFrameWork frameWork;

	private int refreshInterval;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public EditorFrameWork getFrameWork() {
		return this.frameWork;
	}

	public void setFrameWork(EditorFrameWork frameWork) {
		this.frameWork = frameWork;
	}

	public FrameWorkView(Composite arg0, int arg1) {
		super(arg0, arg1);
	}

	protected void checkSubclass() {
	}

	public Action[] getActions() {
		Action[] arr = new Action[actions.size()];
		return actions.toArray(arr);
	}

	public void addAction(Action action) {
		actions.add(action);
	}

	public int getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
		if (this.refreshInterval != 0 && frameWork != null) {
			frameWork.addRefreshView(this, refreshInterval);
		}
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public Action getRefreshAction() {
		return refreshAction;
	}

	public void setRefreshAction(Action refreshAction) {
		this.refreshAction = refreshAction;
		if (refreshAction != null) {
			refresh();
		}
	}

	public void refresh() {
		if (refreshAction != null && !getFrameWork().isDisposed()) {
			refreshAction.setFrameWork(getFrameWork());

			ActionContext context = new ActionContext(refreshAction);
			context.setFrameWork(getFrameWork());
			refreshAction.perform(context);
		}
	}
}
