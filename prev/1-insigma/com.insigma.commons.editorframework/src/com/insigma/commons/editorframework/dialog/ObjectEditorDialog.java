package com.insigma.commons.editorframework.dialog;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.framework.view.form.FormEditor;
import com.insigma.commons.ui.form.IEditorChangedListener;
import com.swtdesigner.SWTResourceManager;

/**
 * 
 * @author DingLuofeng
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ObjectEditorDialog extends FrameWorkDialog {

	private FormEditor<Object> beanEditor;

	private Object beanData;

	public ObjectEditorDialog(EditorFrameWork parent, Object beanData, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
		this.beanData = beanData;
	}

	protected void createAction(List<Action> actions) {
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {
		parent.setLayout(new GridLayout());
		beanEditor = new FormEditor(parent, SWT.BORDER);
		beanEditor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		beanEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		beanEditor.setObjectValue(beanData);
		parent.layout();
	}

	@Override
	public Object getResult() {
		return beanEditor.getForm().getEntity();
	}

	public void setChangedListener(IEditorChangedListener changedListener) {
		beanEditor.setChangedListener(changedListener);
	}

}
