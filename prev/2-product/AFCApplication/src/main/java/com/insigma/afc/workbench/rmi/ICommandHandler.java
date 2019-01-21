package com.insigma.afc.workbench.rmi;

import com.insigma.afc.topology.MetroNode;

import java.util.List;

public interface ICommandHandler {

	public int getID();

	public CmdHandlerResult execute(int id, String userid, Long src, Object arg, List<MetroNode> targets);
}
