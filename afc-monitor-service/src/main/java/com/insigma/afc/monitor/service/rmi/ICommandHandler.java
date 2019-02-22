package com.insigma.afc.monitor.service.rmi;


import com.insigma.afc.monitor.model.entity.MetroNode;

import java.util.List;

public interface ICommandHandler {

	int getID();

	CmdHandlerResult execute(int id, String userid, Long src, Object arg, List<MetroNode> targets);
}
