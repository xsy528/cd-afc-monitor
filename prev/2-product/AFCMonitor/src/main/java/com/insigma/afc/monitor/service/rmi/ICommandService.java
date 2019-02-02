package com.insigma.afc.monitor.service.rmi;

import com.insigma.afc.topology.model.entity.MetroNode;
import java.util.List;

public interface ICommandService {

	CmdHandlerResult command(int id, String userid, Long src, Object arg, List<MetroNode> targets);

	CmdHandlerResult command(int id, String userid, Long src, Object arg, MetroNode... targets);

	CmdHandlerResult command(int id, String userid, Long src, MetroNode... targets);

	CmdHandlerResult command(int id, String userid, Long src, List<MetroNode> targets);

	void alive();
}
