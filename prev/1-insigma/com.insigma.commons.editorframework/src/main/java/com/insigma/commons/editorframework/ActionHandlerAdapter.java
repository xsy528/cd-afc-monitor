package com.insigma.commons.editorframework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.service.ILogService;
import com.insigma.commons.spring.ServiceWireHelper;

public class ActionHandlerAdapter implements IActionHandler {

	protected Log logger = LogFactory.getLog(getClass());

	protected ILogService logService;

	public ILogService getLogService() {
		return logService;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	protected ServiceWireHelper actionHandlerServiceWire = new ServiceWireHelper();

	public ActionHandlerAdapter() {
		actionHandlerServiceWire.wire(this);
	}

	public Log getLogger() {
		return logger;
	}

	@Override
	public void perform(ActionContext context) {

	}

	@Override
	public void unPerform(ActionContext context) {

	}

	//	public void rePerform(ActionContext context) {
	//		logger.debug("执行" + context.getAction().getName() + "的redo，默认调用perform，当前上下文：" + context);
	//		perform(context);
	//	}

}
