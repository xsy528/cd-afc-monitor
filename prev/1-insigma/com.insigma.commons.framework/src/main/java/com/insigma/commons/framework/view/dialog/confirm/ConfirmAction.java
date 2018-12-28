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

import java.util.List;

import org.eclipse.swt.widgets.Display;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.application.WindowCloseListener;

/**
 * 
 * @author DingLuofeng
 *
 */
@SuppressWarnings("unchecked")
public class ConfirmAction extends Action {

	public class ConfirmActionHandler extends ActionHandlerAdapter {
		public Response perform(final Request request) {
			Response response = new Response();
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					List<WindowCloseListener> dataList = (List<WindowCloseListener>) request.getValue();
					for (WindowCloseListener closeListener : dataList) {
						closeListener.beforeClose();
					}
				}
			});
			response.setCode(IResponseCode.DIALOG_CLOSE);
			response.setValue(1);
			return response;
		}

		public boolean prepare(Request request) {
			return true;
		}
	}

	public ConfirmAction() {
		this.text = "确认";
		this.actionHandler = new ConfirmActionHandler();
	}
}
