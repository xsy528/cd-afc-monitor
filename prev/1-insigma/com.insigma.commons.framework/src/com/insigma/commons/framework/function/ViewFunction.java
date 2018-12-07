package com.insigma.commons.framework.function;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.application.Function;

public abstract class ViewFunction extends Function {

	private Action loadAction;

	private Action activeAction;

	public Action getLoadAction() {
		return loadAction;
	}

	public void setLoadAction(Action action) {
		this.loadAction = action;
		action.setFunction(this);
	}

	public Action getActiveAction() {
		return activeAction;
	}

	public void setActiveAction(Action action) {
		this.activeAction = action;
		action.setFunction(this);
	}
}
