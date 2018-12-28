/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework.view.dialog.confirm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.WindowCloseListener;

/**
 * 
 * @author DingLuofeng
 *
 */
public class TableItemSelectAction extends Action {

	private boolean selectAll;

	private Table table;

	public class SelectActionHandler extends ActionHandlerAdapter {
		public Response perform(Request request) {
			Response response = new Response();
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					select(table, selectAll);
				}
			});
			return response;
		}

		public boolean prepare(Request request) {
			return true;
		}
	}

	public TableItemSelectAction(Table table, boolean selectAll) {
		this.selectAll = selectAll;
		this.table = table;
		this.text = (selectAll ? "全选" : "全不选");
		this.actionHandler = new SelectActionHandler();
	}

	private List<WindowCloseListener> select(Table table, boolean select) {
		TableItem[] items = table.getItems();
		List<WindowCloseListener> listeners = new ArrayList<WindowCloseListener>();
		if (items != null) {
			for (TableItem tableItem : items) {
				if (select) {
					WindowCloseListener windowCloseListener = (WindowCloseListener) tableItem.getData();
					listeners.add(windowCloseListener);
				}
				tableItem.setChecked(select);
			}
		}
		return listeners;
	}
}
