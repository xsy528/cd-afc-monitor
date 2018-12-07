/* 
 * 日期：2010-9-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.function.editor;

import java.util.List;

import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;

@SuppressWarnings("unchecked")
public class EntitySearchActionHandler extends ActionHandlerAdapter {

	@Autowire
	private ICommonDAO commonDao;

	private Class entity;

	public Class getEntity() {
		return entity;
	}

	public void setEntity(Class entity) {
		this.entity = entity;
	}

	public EntitySearchActionHandler(Class entity) {
		this.entity = entity;
	}

	@Override
	public Response perform(Request request) {
		SearchResponse resp = new SearchResponse();
		try {
			List list = commonDao.getEntityListHQL("from " + entity.getSimpleName());
			if (list != null && list.size() > 0) {
				TableGrid data = new TableGrid();
				data.setContent(list);

				data.setTotalSize(list.size());
				data.setPageSize(list.size());

				resp.setData(data);
			} else {
				resp.addError("记录为空。");
			}
		} catch (Exception e) {
			resp.addError("信息查询错误。", e);
			logger.error("信息查询错误", e);
		}
		return resp;
	}
}
