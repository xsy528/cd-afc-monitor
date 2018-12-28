package com.insigma.afc.monitor.listview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ToolItem;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.widgets.Menu;
import com.insigma.commons.ui.widgets.MenuItem;

public class SortAction extends Action {

	private String viewName;

	private String formKey;

	private List<PairValue<String, String>> fields;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public List<PairValue<String, String>> getFields() {
		if (fields == null) {
			fields = new ArrayList<PairValue<String, String>>();
		}
		return fields;
	}

	public void setFields(List<PairValue<String, String>> fields) {
		this.fields = fields;
	}

	public class EventSortActionHandler extends ActionHandlerAdapter {

		public void perform(ActionContext action) {

			EditorFrameWork editorFrameWork = action.getFrameWork();
			final ListView view = (ListView) editorFrameWork.getView(viewName);
			if (view == null) {
				MessageForm.Message("错误信息", "框架配置错误,未找到视图[" + viewName + "]。", SWT.ICON_ERROR);
				return;
			}

			final FilterForm filterForm = (FilterForm) view.getData(formKey);
			final String orderFileld = filterForm.getOrderField();
			String orderType = filterForm.getOrderType();
			ToolItem toolItemOrder = (ToolItem) action.getAction().getData();

			final Menu menu = new Menu(toolItemOrder.getParent());
			menu.setVisible(true);

			final MenuItem menuItemUp = new MenuItem(menu, SWT.RADIO);
			menuItemUp.setText("升序排列");
			menuItemUp.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent arg0) {
					filterForm.setOrderType("asc");
					view.refresh();
				}
			});

			final MenuItem menuItemDown = new MenuItem(menu, SWT.RADIO);
			menuItemDown.setText("降序排列");
			menuItemDown.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent arg0) {
					filterForm.setOrderType("desc");
					view.refresh();
				}
			});

			if (orderType == "asc") {
				menuItemUp.setSelection(true);
			} else {
				menuItemDown.setSelection(true);
			}

			new MenuItem(menu, SWT.SEPARATOR);

			for (final PairValue<String, String> field : fields) {
				final MenuItem menuItem = new MenuItem(menu, SWT.RADIO);
				if (orderFileld == field.getKey()) {
					menuItem.setSelection(true);
				}
				menuItem.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent arg0) {
						filterForm.setOrderField(field.getKey());
						view.refresh();
					}
				});
				menuItem.setText(field.getValue());
			}
		}

	}

	public SortAction() {
		super("排序");
		setImage("/com/insigma/commons/ui/toolbar/sort.png");
		setHandler(new EventSortActionHandler());
	}

}
