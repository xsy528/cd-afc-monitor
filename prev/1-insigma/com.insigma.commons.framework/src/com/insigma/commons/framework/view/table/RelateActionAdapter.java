/* 
 * 日期：2010-11-10
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.framework.view.table;

import java.util.List;

import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;

/**
 * 创建时间 2010-11-10 下午06:20:17<br>
 * 描述：<br>
 * Ticket: 
 * @author  DingLuofeng
 *
 */
@SuppressWarnings("unchecked")
public abstract class RelateActionAdapter<T> implements IRelateAction {

	/* (non-Javadoc)
	 * @see com.insigma.commons.framework.view.table.IRelateAction#executeAction(com.insigma.commons.framework.Request)
	 */
	public Response executeAction(Request request) {
		List<T> selectedItems = getSelection(request);
		// if (selectedItems == null) {
		// return new Response();
		// }
		return getRelateResponse(selectedItems);
	}

	protected List<T> getSelection(Request request) {
		if (request instanceof SearchRequest) {
			SearchRequest req = (SearchRequest) request;
			if (req.getSelection().size() > 0) {
				List<T> selectedItems = (List<T>) req.getSelection();
				return selectedItems;
			}
		}
		return null;
	}

	protected abstract Response getRelateResponse(List<T> selectedItem);

}
