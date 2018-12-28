package com.insigma.commons.framework.function.editor;

import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.form.FormRequest;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;

public class ModifyActionHandler extends ActionHandlerAdapter {

	@Autowire
	private ICommonDAO commonDao;

	public Response perform(Request request) {
		Response response = new Response();
		FormRequest req = (FormRequest) request;
		try {
			commonDao.updateObj(req.getForm());
			response.setCode(IResponseCode.DIALOG_CLOSE);
		} catch (OPException e) {
			response.addError("信息更新失败。");
			getLogger().error("信息更新失败", e);
			return response;
		}
		response.addInformation("信息更新成功。");
		return response;
	}

}
