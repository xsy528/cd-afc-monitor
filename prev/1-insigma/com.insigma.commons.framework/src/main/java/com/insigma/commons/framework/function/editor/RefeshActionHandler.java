/* 
 * 日期：2010-9-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.function.editor;

import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;

/**
 * 简单的根据form查询刷新的actionHandler实现<br/>
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class RefeshActionHandler extends ActionHandlerAdapter {

	@Autowire
	private ICommonDAO commonDao;

	@SuppressWarnings("unchecked")
	@Override
	public Response perform(Request request) {
		SearchResponse resp = new SearchResponse();
		SearchRequest sres = (SearchRequest) request;
		try {
			Form form = ((SearchRequest) request).getForm();
			int pageIndex = sres.getPage();
			int pageSize = sres.getPageSize();
			PageHolder page = commonDao.fetchListByObject(form, pageSize, pageIndex);
			//判断查询记录是否为空
			if (page != null) {
				TableGrid data = new TableGrid();
				data.setContent(page.getDatas());
				if (pageIndex > -1) {
					data.setTotalSize(page.getTotalCount());
					data.setPageSize(page.getPageSize());
					data.setCurrentPage(pageIndex);
				}
				resp.setData(data);
			}
		} catch (Exception e) {
			resp.addError("信息查询错误", e);
			logger.error("信息查询错误", e);
		}
		return resp;
	}
}
