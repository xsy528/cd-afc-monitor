package com.insigma.afc.workbench.rmi;

import java.util.List;

import com.insigma.afc.topology.MetroNode;

public interface ICommandHandler {

	public int getID();

	public CmdHandlerResult execute(int id, String userid, Long src, Object arg, List<MetroNode> targets);
}
