package com.insigma.afc.workbench.rmi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.afc.topology.MetroNode;

public class CommandService implements ICommandService {

	private Log logger = LogFactory.getLog(CommandService.class);

	private CommandHandlerManager commandHandlerManager;

	public CommandHandlerManager getCommandHandlerManager() {
		return commandHandlerManager;
	}

	public void setCommandHandlerManager(CommandHandlerManager commandHandlerManager) {
		this.commandHandlerManager = commandHandlerManager;
	}

	public CmdHandlerResult command(int id, String userid, Long src, Object arg, List<MetroNode> targets) {
		String tagStr = "";
		String argStr = "";
		for (int i = 0; i < targets.size(); i++) {
			MetroNode tag = (MetroNode) targets.get(i);
			tagStr += tag.getNodeId();
		}
		if (arg instanceof int[]) {
			int[] a = (int[]) arg;
			argStr = a[0] + "," + a[1];
			logger.info("ID=" + id + ",参数为" + argStr + " 目标为" + tagStr);
		} else {
			logger.info("ID=" + id + ",参数为" + arg + " 目标为" + tagStr);
		}
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				logger.info(targets.get(i));
			}
		}
		if (commandHandlerManager != null) {
			try {
				CmdHandlerResult process = commandHandlerManager.process(id, userid, src, arg, targets);
				return process;
			} catch (Exception e) {
				logger.error("命令执行异常", e);
				CmdHandlerResult result = new CmdHandlerResult();
				result.isOK = false;
				result.messages.add("命令执行异常：" + e.getMessage());
				return result;
			}
		} else {
			logger.error("未配置命令管理器");
		}
		CmdHandlerResult result = new CmdHandlerResult();
		result.isOK = false;
		result.messages.add("该命令未定义");
		return result;
	}

	public CmdHandlerResult command(int id, String userid, Long src, Object arg, MetroNode... targets) {
		List<MetroNode> dests = new ArrayList<MetroNode>();
		for (int i = 0; i < targets.length; i++) {
			dests.add(targets[i]);
		}
		return command(id, userid, src, arg, dests);
	}

	public CmdHandlerResult command(int id, String userid, Long src, MetroNode... targets) {
		return command(id, userid, src, null, targets);
	}

	public CmdHandlerResult command(int id, String userid, Long src, List<MetroNode> targets) {
		return command(id, userid, src, null, targets);
	}

	public void alive() {

	}
}
