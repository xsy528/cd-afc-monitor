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
package com.insigma.commons.framework.action;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;

public class CancelAction extends Action {

	public class CancelActionHandler extends ActionHandlerAdapter {
		public Response perform(Request request) {
			Response response = new Response();
			return response;
		}

		public boolean prepare(Request request) {
			return true;
		}
	}

	public CancelAction() {
		this.text = "取消";
		this.actionHandler = new CancelActionHandler();
	}
}
