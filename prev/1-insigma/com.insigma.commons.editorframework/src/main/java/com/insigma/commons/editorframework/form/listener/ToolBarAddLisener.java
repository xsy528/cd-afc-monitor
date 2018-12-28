package com.insigma.commons.editorframework.form.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.editorframework.form.editor.DefaultDialogEtior;
import com.insigma.commons.editorframework.form.editor.IDialogEditor;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.form.ITableEditor;
import com.insigma.commons.ui.form.listener.ITableEditorLisener;

public class ToolBarAddLisener implements ITableEditorLisener {

	Log logger = LogFactory.getLog(ToolBarAddLisener.class);

	private ITableEditor tableEditor;

	private IDialogEditor dialogEditor = new DefaultDialogEtior();

	@Override
	public boolean widgetSelected(int[] indexes) {

		try {
			Object resultObj = dialogEditor.editor(BeanUtil.newInstanceObject(tableEditor.getDataClass()),
					tableEditor.getField());

			if (null != resultObj) {
				tableEditor.addRow(resultObj);
			}

			return true;
		} catch (Exception e) {
			logger.error("新增失败", e);
			return false;
		}
	}

	@Override
	public String getImagePath() {
		return "/com/insigma/commons/ui/toolbar/new.png";
	}

	@Override
	public String getName() {
		return "新增";
	}

	@Override
	public void setParentTableEditor(ITableEditor tableEditor) {
		this.tableEditor = tableEditor;
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
