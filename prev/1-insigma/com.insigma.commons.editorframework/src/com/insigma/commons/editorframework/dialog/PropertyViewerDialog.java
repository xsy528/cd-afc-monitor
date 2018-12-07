/* 
 * 日期：2010-11-13
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.dialog;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.ui.form.PropertyViewer;

public class PropertyViewerDialog extends FrameWorkDialog {

	private PropertyViewer viewer;

	private Object value;

	private int[] weight;

	public PropertyViewerDialog(EditorFrameWork parent, int style) {
		super(parent, style);
		setSize(500, 550);
	}

	@Override
	public void createAction(List<Action> actions) {
		actions.add(new CloseAction());
	}

	@Override
	protected void createContents(Composite parent) {
		parent.setLayout(new GridLayout());
		viewer = new PropertyViewer(parent, SWT.BORDER);
		viewer.setLayoutData(new GridData(GridData.FILL_BOTH));

		viewer.setObject(value);
		if (null != weight) {
			viewer.setWeight(weight);
		}
		parent.layout();
	}

	public PropertyViewer getViewer() {
		return viewer;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setWeight(int[] weight) {
		this.weight = weight;
	}
}