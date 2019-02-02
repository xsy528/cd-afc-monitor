package com.insigma.afc.monitor.service.rmi;


import com.insigma.afc.topology.model.entity.MetroNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandlerManager{

	Map<Integer,ICommandHandler> handlers = new HashMap<>();

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
			return handler.execute(id, userid, src, arg, targets);
		}
		CmdHandlerResult result = new CmdHandlerResult();
		result.isOK = false;
		result.messages.add("该命令未定义");
		return result;
	}
}
