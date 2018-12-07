/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.ui.form.ObjectTableViewer;

public class ListView extends FrameWorkView {

	private ObjectTableViewer table;

	public ObjectTableViewer getTable() {
		return table;
	}

	public void setTable(ObjectTableViewer table) {
		this.table = table;
	}

	public ListView(Composite arg0, int arg1) {
		this(arg0, arg1, null);
	}

	public ListView(Composite arg0, int arg1, Class viewClass) {
		super(arg0, arg1);
		this.setFrameWork((EditorFrameWork) arg0);
		setText("属性");
		setLayout(new FillLayout());
		setIcon("/com/insigma/commons/ui/shape/property.png");
		table = new ObjectTableViewer(this, SWT.BORDER);
		if (viewClass != null) {
			table.setHeader(viewClass);
		}
	}

	public void setObjectList(List dataList) {
		table.setObjectList(dataList);
	}

	public void setWidths(int[] widths) {
		table.setWidths(widths);
	}

	public void setColumn(String[] columnNames) {
		table.setColumn(columnNames);
	}

	public void removeAll() {
		table.removeAll();
	}

}
