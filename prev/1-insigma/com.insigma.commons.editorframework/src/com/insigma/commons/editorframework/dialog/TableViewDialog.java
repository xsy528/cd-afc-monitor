package com.insigma.commons.editorframework.dialog;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.ui.form.ObjectTableViewer;

public class TableViewDialog extends FrameWorkDialog {

	private ObjectTableViewer viewer;

	private List<?> dataList;

	private int[] widths;

	private Class<?> viewClass;

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

	public TableViewDialog(EditorFrameWork parent, int style) {
		super(parent, style);
	}

	protected void createAction(List<Action> actions) {
		actions.add(new CloseAction());
	}

	@Override
	protected void createContents(Composite parent) {
		parent.setLayout(new GridLayout());
		viewer = new ObjectTableViewer(parent, SWT.BORDER);
		viewer.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setObjectList(dataList);
		if (null != widths) {
			viewer.setWidths(widths);
		}
		if (null != viewClass) {
			viewer.setHeader(viewClass);
		}
		parent.layout();
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}

	public void setViewClass(Class<?> viewClass) {
		this.viewClass = viewClass;
	}
}
