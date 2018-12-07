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
package com.insigma.commons.framework;

import java.lang.reflect.Constructor;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.framework.function.dialog.StandardDialogFunction;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.framework.function.table.TableViewFunction;
import com.insigma.commons.framework.view.dialog.TableViewDialog;
import com.insigma.commons.ui.IDialog;
import com.insigma.commons.ui.MessageForm;

@SuppressWarnings("unchecked")
public class Request {

	protected Request parent;

	protected Action action;

	protected Context context;

	protected Object value;

	public void reset() {
		context = null;
		action = null;
		value = null;
	}

	public Request getParent() {
		return parent;
	}

	public void setParent(Request parent) {
		this.parent = parent;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int Query(String title, String message) {
		return MessageForm.Query(title, message);
	}

	public class TableViewFunctionUI implements Runnable {

		int result;

		String title;

		String message;

		List items;

		public TableViewFunctionUI(String title, String message, List items) {
			this.title = title;
			this.message = message;
			this.items = items;
		}

		public void run() {
			StandardDialogFunction dlgfunc = new StandardDialogFunction();
			TableViewFunction func = new TableViewFunction();
			dlgfunc.setText(title);
			dlgfunc.setDescription(message);
			dlgfunc.setFunction(func);
			TableViewDialog dlg = new TableViewDialog(Display.getDefault().getActiveShell());
			dlg.setFunction(dlgfunc);
			dlgfunc.setOpenAction(new Action("", new ActionHandlerAdapter() {
				public Response perform(Request request) {
					Response response = new Response();
					TableGrid grid = new TableGrid();
					grid.setContent(items);
					response.setValue(grid);
					return response;
				}

			}));
			dlgfunc.addAction(new Action("确认 (&O)", new ActionHandlerAdapter() {
				public Response perform(Request request) {
					Response response = new Response();
					response.setCode(IResponseCode.DIALOG_CLOSE);
					result = 1;
					return response;
				}

			}));
			dlgfunc.addAction(new Action("取消 (&C)", new ActionHandlerAdapter() {
				public Response perform(Request request) {
					Response response = new Response();
					response.setCode(IResponseCode.DIALOG_CLOSE);
					result = 0;
					return response;
				}

			}));
			dlg.open();
		}

		public int getResult() {
			return result;
		}

		public void setResult(int result) {
			this.result = result;
		}
	}

	public int Query(String title, String message, List items) {
		TableViewFunctionUI ui = new TableViewFunctionUI(title, message, items);
		if (Display.getDefault().getThread().getId() != Thread.currentThread().getId())
			Display.getDefault().syncExec(ui);
		else
			ui.run();
		return ui.getResult();
	}

	public Object open(Class clazz) {
		OpenDialogUI ui = new OpenDialogUI(clazz);
		if (Display.getDefault().getThread().getId() != Thread.currentThread().getId())
			Display.getDefault().syncExec(ui);
		else
			ui.run();
		return ui.getResult();
	}

	private class OpenDialogUI implements Runnable {

		private Object result;

		private Class clazz;

		public OpenDialogUI(Class clazz) {
			this.clazz = clazz;
		}

		public void run() {
			if (Display.getDefault().getShells().length == 0) {
				return;
			}
			Shell shell = Display.getDefault().getActiveShell();
			if (shell == null) {
				shell = new Shell(Display.getDefault(), SWT.APPLICATION_MODAL | SWT.ON_TOP);
			}
			Constructor constructor;
			try {
				constructor = clazz.getConstructor(Shell.class);
				Object dlg = constructor.newInstance(shell);

				IDialog dialog = (IDialog) dlg;
				result = dialog.open();
			} catch (Exception e) {

			}
		}

		public Object getResult() {
			return result;
		}
	}

	public <T> T getValue(T form) {
		return (T) this.value;
	}
}
