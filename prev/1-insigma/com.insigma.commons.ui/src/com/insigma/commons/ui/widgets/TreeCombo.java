package com.insigma.commons.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.widgets.TreeItem;

public class TreeCombo extends CustomCombo implements IInputControl {

	private Tree tree;

	public TreeCombo(Composite parent, int style) {
		super(parent, style);
	}

	public Control createControl(Shell shell) {
		tree = new Tree(shell, SWT.CHECK);
		{
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText("jiangxufeng");
			{
				TreeItem subitem = new TreeItem(item, SWT.NONE);
				subitem.setText("jiangxufeng");
			}
			{
				TreeItem subitem = new TreeItem(item, SWT.NONE);
				subitem.setText("jiangxufeng1");
			}
			{
				TreeItem subitem = new TreeItem(item, SWT.NONE);
				subitem.setText("jiangxufeng2");
			}
			{
				TreeItem subitem = new TreeItem(item, SWT.NONE);
				subitem.setText("jiangxufeng3");
			}
			{
				TreeItem subitem = new TreeItem(item, SWT.NONE);
				subitem.setText("jiangxufeng5");
			}
		}

		{
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText("richard");
		}

		{
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText("hello world");
		}

		return tree;
	}

	public Object getObjectValue() {
		return null;
	}

	public void setObjectValue(Object value) {

	}

	public void reset() {

	}
}
