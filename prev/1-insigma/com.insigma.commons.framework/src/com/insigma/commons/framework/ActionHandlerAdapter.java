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
package com.insigma.commons.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.spring.ServiceWireHelper;

public class ActionHandlerAdapter implements IActionHandler {

	protected Log logger = LogFactory.getLog(getClass());

	protected ServiceWireHelper actionHandlerServiceWire = new ServiceWireHelper();

	protected ILogService logService;

	public ActionHandlerAdapter() {
		actionHandlerServiceWire.wire(this);
	}

	public Response perform(Request request) {
		return null;
	}

	public boolean prepare(Request request) {
		return true;
	}

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	/**
	 * @param searchHandler
	 *            : 对应查询Handler
	 * @param request
	 *            : 对应当前Request
	 * @return
	 */
	protected Response getRefreshResponse(IActionHandler searchHandler, Request request) {
		Response response = searchHandler.perform(request);

		if (response instanceof SearchResponse) {
			TableGrid datagrid = ((SearchResponse) response).getData();
			if (datagrid.getContent() == null || (datagrid.getContent().isEmpty())) {
				SearchResponse res = new SearchResponse();
				res.setData(new TableGrid());
				return res;
			} else {
				return response;
			}
		} else {
			return response;
		}
	}
}
