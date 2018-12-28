package com.insigma.commons.editorframework.form.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.editorframework.form.editor.DefaultDialogEtior;
import com.insigma.commons.editorframework.form.editor.IDialogEditor;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.form.ITableEditor;
import com.insigma.commons.ui.form.listener.ITableEditorLisener;

public class ToolBarCopyLisener implements ITableEditorLisener {

	Log logger = LogFactory.getLog(ToolBarCopyLisener.class);

	private ITableEditor tableEditor;

	private IDialogEditor dialogEditor = new DefaultDialogEtior();

	@Override
	public boolean widgetSelected(int[] indexes) {
		if (null == indexes || indexes.length <= 0) {
			return false;
		}

		try {
			Object compareObj = tableEditor.getCompareRow(indexes[0]);
			Object obj = tableEditor.copyRow(indexes[0]);

			Object resultObj = dialogEditor.editor(obj, tableEditor.getField(), compareObj);

			if (null != resultObj) {
				tableEditor.addRow(resultObj);
			}

		} catch (Exception e) {
			MessageForm.Message("错误提示", "复制失败");
		}
		return true;
	}

	@Override
	public String getImagePath() {
		return "/com/insigma/commons/ui/toolbar/copy.png";
	}

	@Override
	public String getName() {
		return "拷贝";
	}

	public void setDialogEditor(IDialogEditor dialogEditor) {
		this.dialogEditor = dialogEditor;
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
