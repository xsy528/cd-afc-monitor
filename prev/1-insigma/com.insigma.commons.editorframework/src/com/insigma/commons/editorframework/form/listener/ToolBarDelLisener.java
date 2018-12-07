package com.insigma.commons.editorframework.form.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;

import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.form.ITableEditor;
import com.insigma.commons.ui.form.listener.ITableEditorLisener;

public class ToolBarDelLisener implements ITableEditorLisener {

	Log logger = LogFactory.getLog(ToolBarDelLisener.class);

	private ITableEditor tableEditor;

	@Override
	public boolean widgetSelected(int[] indexes) {

		int result = MessageForm.Query("提示信息", "是否确认删除？");

		if (SWT.YES == result) {
			tableEditor.removeRow(indexes);
		}
		return true;
	}

	@Override
	public void setParentTableEditor(ITableEditor tableEditor) {
		this.tableEditor = tableEditor;
	}

	@Override
	public String getImagePath() {
		return "/com/insigma/commons/ui/toolbar/delete.png";
	}

	@Override
	public String getName() {
		return "删除";
	}

	@Override
	public int getStyle() {
		return 0;
	}

	@Override
	public LisenerType getType() {
		return LisenerType.TOOL_BAR;
	}
}
