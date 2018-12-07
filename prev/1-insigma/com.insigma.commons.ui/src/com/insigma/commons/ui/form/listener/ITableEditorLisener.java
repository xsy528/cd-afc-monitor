package com.insigma.commons.ui.form.listener;

import com.insigma.commons.ui.form.ITableEditor;

public interface ITableEditorLisener {
	enum LisenerType {
		TOOL_BAR, POP_UP, LISENER
	}

	public LisenerType getType();

	public int getStyle();

	public String getName();

	public String getImagePath();

	public boolean widgetSelected(int[] indexes);

	public void setParentTableEditor(ITableEditor tableEditor);
}
