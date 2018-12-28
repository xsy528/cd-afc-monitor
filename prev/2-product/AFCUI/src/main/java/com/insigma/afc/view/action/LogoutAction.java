/* 
 * 日期：2010-9-17
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.view.action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.application.Application;
import com.insigma.commons.application.IUser;
import com.insigma.commons.application.SystemState;
import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.thread.ThreadManager;
import com.insigma.commons.ui.task.Task;
import com.insigma.commons.ui.task.TaskCallback;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@SuppressWarnings("unchecked")
public class LogoutAction extends Action {

	public LogoutAction() {
		// 导致了ID转换异常,是个警告 ByChenYao
		//		setActionID("afc_action_logout");
		setText("注销");
		setImage("/com/insigma/acc/common/images/exit.png");
		setActionHandler(new ActionHandlerAdapter() {

			public Response perform(Request request) {
				IUser user = Application.getUser();
				if (request.Query("确认信息",
						String.format("确定要注销用户:%s(%s)吗?", user.getUserName(), user.getUserID())) != SWT.YES) {
					return null;
				}
				Response logoutRes = new Response();
				doBeforeLogout(logoutRes);
				ThreadManager.getInstance().stopAll();
				String msg = String.format("用户：%s(%s)，注销成功。", user.getUserName(), user.getUserID());
				logger.info(msg);

				logoutRes.setCode(IResponseCode.DIALOG_CLOSE);
				Application.systemState = SystemState.LOGOUT;
				return logoutRes;
			}

			private void doBeforeLogout(Response resp) {
				try {
					if (beforeTask != null) {
						final Task<?> task = beforeTask;
						final Object result = task.execute();
						final TaskCallback callback = task.getCallback();
						if (callback != null) {
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									callback.finish(result);
								}
							});
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					String msg = "注销异常";
					logger.error(msg, e);
					resp.addError(msg, e);

				}
			}

			private Task<?> beforeTask;

		});
	}

}
