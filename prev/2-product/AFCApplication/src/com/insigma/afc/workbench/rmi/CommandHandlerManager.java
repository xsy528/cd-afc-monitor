package com.insigma.afc.workbench.rmi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.application.IService;
import com.insigma.commons.spring.ServiceWireHelper;

public class CommandHandlerManager implements IService {

	Map<Integer, ICommandHandler> handlers = new HashMap<Integer, ICommandHandler>();

	protected ServiceWireHelper actionHandlerServiceWire = new ServiceWireHelper();

	public CommandHandlerManager(List<ICommandHandler> handlers) {
		super();
		for (ICommandHandler commandHandler : handlers) {
			register(commandHandler);
		}
	}

	public void register(ICommandHandler handler) {
		handlers.put(handler.getID(), handler);
	}

	public CmdHandlerResult process(int id, String userid, Long src, Object arg, List<MetroNode> targets) {
		ICommandHandler handler = handlers.get(id);
		if (handler != null) {
			actionHandlerServiceWire.wire(handler);
			return handler.execute(id, userid, src, arg, targets);
		}
		CmdHandlerResult result = new CmdHandlerResult();
		result.isOK = false;
		result.messages.add("该命令未定义");
		return result;
	}
}
