package com.insigma.commons.framework.view.dialog.confirm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.insigma.commons.framework.IRequestGenerator;
import com.insigma.commons.framework.IViewComponent;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.application.WindowCloseListener;
import com.insigma.commons.ui.widgets.EnhanceComposite;

/**
 * 
 * @author DingLuofeng
 *
 */
public class ExitConfirmComposite extends EnhanceComposite implements IViewComponent, IRequestGenerator {

	private Table tableView;

	/** 
	 * Create the composite. 
	 * @param parent
	 * @param style
	 */
	public ExitConfirmComposite(Composite parent, int style) {
		super(parent, style);

		tableView = new Table(this, SWT.CHECK | SWT.BORDER);
		tableView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

	}

	/**
	 * 
	 */
	public Table createTree(List<WindowCloseListener> dataList) {
		for (WindowCloseListener windowCloseListener : dataList) {
			if (windowCloseListener.prepare()) {
				createTreeItem(windowCloseListener);
			}
		}
		return tableView;
	}

	protected void createTreeItem(WindowCloseListener windowCloseListener) {
		TableItem item = new TableItem(tableView, SWT.None);
		item.setText(windowCloseListener.getName());
		item.setData(windowCloseListener);
		item.setChecked(true);
		if (windowCloseListener.getImage() != null) {
			item.setImage(windowCloseListener.getImage());
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public Request getRequest() {
		Request request = new Request();
		List<WindowCloseListener> select = getSelections(tableView);
		request.setValue(select);
		return request;
	}

	private List<WindowCloseListener> getSelections(Table table) {
		TableItem[] items = table.getItems();
		List<WindowCloseListener> listeners = new ArrayList<WindowCloseListener>();
		if (items != null) {
			for (TableItem tableItem : items) {
				if (tableItem.getChecked()) {
					WindowCloseListener windowCloseListener = (WindowCloseListener) tableItem.getData();
					listeners.add(windowCloseListener);
				}
			}
		}
		return listeners;
	}

}
