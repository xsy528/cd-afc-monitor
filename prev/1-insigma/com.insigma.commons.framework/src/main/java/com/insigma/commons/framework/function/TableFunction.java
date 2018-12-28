package com.insigma.commons.framework.function;

import java.util.List;

import com.insigma.commons.framework.Action;

public abstract class TableFunction extends ViewFunction {

	private Action pageChangedAction;

	private List<Action> tableContext;

	private Action tableDoubleClickAction;

	public Action getPageChangedAction() {
		return pageChangedAction;
	}

	public void setPageChangedAction(Action action) {
		this.pageChangedAction = action;
		action.setFunction(this);
	}

	public List<Action> getTableContext() {
		setTableContext(tableContext);
		return tableContext;
	}

	public void setTableContext(List<Action> actions) {
		this.tableContext = actions;
		if (actions != null) {
			for (Action action : actions) {
				action.setFunction(this);
			}
		}
	}

	public Action getTableDoubleClickAction() {
		return tableDoubleClickAction;
	}

	public void setTableDoubleClickAction(Action action) {
		this.tableDoubleClickAction = action;
		action.setFunction(this);
	}
}
