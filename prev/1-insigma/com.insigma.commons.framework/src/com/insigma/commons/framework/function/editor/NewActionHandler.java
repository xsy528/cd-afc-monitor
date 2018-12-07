package com.insigma.commons.framework.function.editor;

import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.form.FormRequest;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;

public class NewActionHandler extends ActionHandlerAdapter {

	@Autowire
	private ICommonDAO commonDao;

	public Response perform(Request request) {
		Response response = new Response();
		FormRequest req = (FormRequest) request;
		try {
			commonDao.saveObj(req.getForm());
			response.addInformation("信息添加成功。");
		} catch (OPException e) {
			response.addError("信息添加失败。", e);
			getLogger().error("信息添加失败", e);
			return response;
		}
		return response;
	}
}
