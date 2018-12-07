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
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;

public class ResetAction extends Action {

	public class ResetActionHandler extends ActionHandlerAdapter {
		public Response perform(Request request) {
			Response response = new Response();
			response.setCode(IResponseCode.RESET);
			return response;
		}

		public boolean prepare(Request request) {
			return true;
		}
	}

	public ResetAction() {
		this.text = "重置(&R)";
		this.actionHandler = new ResetActionHandler();
	}
}
