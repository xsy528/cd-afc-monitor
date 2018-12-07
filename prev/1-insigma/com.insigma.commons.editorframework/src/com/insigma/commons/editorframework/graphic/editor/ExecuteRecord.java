package com.insigma.commons.editorframework.graphic.editor;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;

public class ExecuteRecord {

	ActionContext context;

	Action action;

	MapItem mapItem;

	/**
	 * @param context
	 * @param action
	 */
	public ExecuteRecord(ActionContext context, Action action, MapItem mapItem) {
		super();
		this.context = context;
		this.action = action;
		this.mapItem = mapItem;
	}

}
