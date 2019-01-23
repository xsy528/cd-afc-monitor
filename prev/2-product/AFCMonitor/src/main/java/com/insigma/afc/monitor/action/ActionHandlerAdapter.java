package com.insigma.afc.monitor.action;

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

	public ActionHandlerAdapter(boolean autowire) {
		if (autowire) {
			actionHandlerServiceWire.wire(this);
		}
	}

	protected void wire(){
	    actionHandlerServiceWire.wire(this);
    }

	public Log getLogger() {
		return logger;
	}

}
