package com.insigma.commons.editorframework.dialog;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.ui.form.BeanEditor;
import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.tree.ObjectTree;

public class TreeFormDialog extends FrameWorkDialog {

	private ObjectTree tree;

	private BeanEditor formEditor;

	private ITreeProvider treeProvider;

	private Object value;

	public TreeFormDialog(EditorFrameWork parent, int style) {
		super(parent, style);
	}

	protected void createAction(List<Action> actions) {
		actions.add(new CloseAction());
	}

	@Override
	protected void createContents(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		parent.setLayout(layout);
		tree = new ObjectTree(parent, SWT.BORDER);
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		tree.setTreeProvider(treeProvider);
		formEditor = new BeanEditor(parent, SWT.BORDER);
		formEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
		formEditor.setObject(value);
		parent.layout();
	}

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
