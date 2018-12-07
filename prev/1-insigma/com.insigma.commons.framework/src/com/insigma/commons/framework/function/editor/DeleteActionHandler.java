package com.insigma.commons.framework.function.editor;

import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;

public class DeleteActionHandler extends ActionHandlerAdapter {

	@Autowire
	private ICommonDAO commonDao;

	public Response perform(Request request) {
		Response response = new Response();
		SearchRequest req = (SearchRequest) request;
		for (Object obj : req.getSelection()) {
			try {
				commonDao.removeObj(obj);
			} catch (OPException e) {
				response.addError("插入信息失败。");
				this.getLogger().error("插入信息失败", e);
				return response;
			}
		}
		response.setCode(IResponseCode.REFRESH);
		response.addInformation("信息删除成功。");
		return response;
	}
}
