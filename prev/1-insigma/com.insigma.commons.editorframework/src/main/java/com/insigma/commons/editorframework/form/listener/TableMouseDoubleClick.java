/* 
 * 日期：2016-7-8
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.form.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;

import com.insigma.commons.editorframework.form.editor.DefaultDialogEtior;
import com.insigma.commons.editorframework.form.editor.IDialogEditor;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.form.ITableEditor;
import com.insigma.commons.ui.form.listener.ITableEditorLisener;

public class TableMouseDoubleClick implements ITableEditorLisener {

	private Log logger = LogFactory.getLog(TableMouseDoubleClick.class);

	private ITableEditor tableEditor;

	private IDialogEditor dialogEditor = new DefaultDialogEtior();

	@Override
	public boolean widgetSelected(int[] indexes) {
		try {

			Object compareObj = tableEditor.getCompareRow(indexes[0]);

			Object originalObj = tableEditor.getRow(indexes[0]);

			Object editorObj = originalObj;
			if (!BeanUtil.isBaseType(originalObj.getClass())) {
				editorObj = BeanUtil.newInstanceObject(originalObj.getClass());
				BeanUtil.copyObject(originalObj, editorObj);
			}

			Object resultObj = dialogEditor.editor(editorObj, tableEditor.getField(), compareObj);

			if (null != resultObj) {
				tableEditor.updateRow(indexes[0], resultObj);
			}

			return true;
		} catch (Exception e) {
			logger.error("新增失败", e);
			return false;
		}
	}

	@Override
	public String getName() {
		return "鼠标双击";
	}

	@Override
	public int getStyle() {
		return SWT.MouseDoubleClick;
	}

	@Override
	public LisenerType getType() {
		return LisenerType.LISENER;
	}

	@Override
	public String getImagePath() {
		return null;
	}

	@Override
	public void setParentTableEditor(ITableEditor tableEditor) {
		this.tableEditor = tableEditor;
	}

}
