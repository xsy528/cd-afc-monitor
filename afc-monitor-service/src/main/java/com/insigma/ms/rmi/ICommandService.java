package com.insigma.ms.rmi;

import java.util.List;

public interface ICommandService {

	CmdHandlerResult command(int id, String userid, Long src, Object arg, List<Long> targets);

	CmdHandlerResult command(int id, String userid, Long src, Object arg, Long... targets);

	CmdHandlerResult command(int id, String userid, Long src, Long... targets);

	CmdHandlerResult command(int id, String userid, Long src, List<Long> targets);

	void alive();
}
