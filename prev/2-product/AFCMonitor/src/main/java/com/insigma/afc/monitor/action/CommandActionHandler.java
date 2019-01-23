package com.insigma.afc.monitor.action;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.dic.AFCCmdResultType;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.afc.workbench.rmi.ICommandService;
import com.insigma.commons.application.Application;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.thread.EnhancedThread;
import com.insigma.commons.util.lang.DateTimeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public abstract class CommandActionHandler extends ActionHandlerAdapter {

	protected CommandActionHandler(boolean autowire){
		super(autowire);
	}

	protected CommandActionHandler(){
	}

	@Autowire
	protected ICommandService commandService;

	@Autowire
	private ICommonDAO commonDAO;

	private List<CommandResult> results = new Vector<>();

	private CountDownLatch countDownLatch;

	private List<MetroNode> nodes;

	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(final ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public List<CommandResult> send(final int id, final String name, final Object arg,
			final List<MetroNode> nodes, final short cmdType) {
		if (nodes==null||countDownLatch!=null){
			return null;
		}
		this.nodes = nodes;
		results.clear();
		countDownLatch = new CountDownLatch(nodes.size());
		for (MetroNode node : nodes) {
			CommandThread thread = new CommandThread(id, name, arg, node, cmdType);
			thread.start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			logger.error("",e);
		}
		countDownLatch=null;
		return results = sortResults(results);
	}

	public class CommandThread extends EnhancedThread {

		private final int id;

		private final String name;

		private final Object arg;

		private final MetroNode node;

		private final short cmdType;

		CommandThread(final int id, final String name, final Object arg,
				final MetroNode node, final short cmdType) {
			super("命令发送线程");
			this.id = id;
			this.name = name;
			this.arg = arg;
			this.node = node;
			this.cmdType = cmdType;
		}

		@Override
		public void execute() {
			if (arg instanceof int[]) {
				int[] a = (int[]) arg;
				logger.info("向节点" + node.name() + "发送" + name + " 参数：" + a[0] + "," + a[1]);
			} else {
				logger.info("向节点" + node.name() + "发送" + name + " 参数：" + arg);
			}
			int result = AFCCmdResultType.SEND_FAILED;
			String resultDesc = null;
			try {
				String userId = "0";
				CmdHandlerResult command = commandService.command(id, userId,
						AFCApplication.getAFCNode().id(), arg, node);
				Serializable returnValue = command.returnValue;
				if (returnValue != null && returnValue instanceof Integer) {
					result = (Integer) returnValue;
				} else if (command.isOK) {
					result = AFCCmdResultType.SEND_SUCCESSFUL;
				}
				resultDesc = command.getResultMessage();
			} catch (Exception e) {
				logger.error("发送" + name + "错误", e);
			}
			logger.info("向节点" + node.name() + "发送" + name + "  返回：" + result);
			results.add(save(node, name, arg, result, cmdType, resultDesc));
			countDownLatch.countDown();
		}
	}

	public List<CommandResult> sortResults(final List<CommandResult> results) {
		List<CommandResult> orderResulsts = new ArrayList<CommandResult>();

		if (nodes == null || results == null) {
			return orderResulsts;
		}

		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < results.size(); j++) {
				String id = nodes.get(i).name() + "/0x" + String.format("%08x", nodes.get(i).getNodeId());
				if (id.equals(results.get(j).getId())) {
					orderResulsts.add(results.get(j));
					// break;
				}
			}
		}

		return orderResulsts;
	}

	public CommandResult save(final MetroNode node, String command, final Object arg, final int result,
			final short cmdType, final String resultDesc) {

		String resultMessageShow = "发送结果：\n\n";

		if (result == 0) {
			resultMessageShow += "向节点" + node.name() + "发送 " + command + " 命令发送成功。\n";
			if (this.logService != null) {
				logService.doBizLog(resultMessageShow);
			}
		} else {
			resultMessageShow += "向节点" + node.name() + "发送 " + command + " 命令失败。错误码：" + result + "。";
			if (this.logService != null) {
				try {
					logService.doBizErrorLog(resultMessageShow);
				} catch (Exception e) {
					logger.error("发送命令保存日志失败", e);
				}
			}
		}

		TmoCmdResult tmoCmdResult = new TmoCmdResult();
		tmoCmdResult.setOccurTime(DateTimeUtil.getNow());
		tmoCmdResult.setCmdName(command);

		if (node instanceof MetroLine) {
			MetroLine line = (MetroLine) node;
			short lineID = line.getLineID();
			tmoCmdResult.setLineId(lineID);
			tmoCmdResult.setStationId(0);
			tmoCmdResult.setNodeId(AFCApplication.getNodeId(lineID));
			tmoCmdResult.setNodeType(AFCDeviceType.LC);
		}

		if (node instanceof MetroStation) {
			MetroStation station = (MetroStation) node;
			tmoCmdResult.setLineId(station.getId().getLineId());
			Integer stationId = station.getId().getStationId();
			tmoCmdResult.setStationId(stationId);
			tmoCmdResult.setNodeId(AFCApplication.getNodeId(stationId));
			tmoCmdResult.setNodeType(AFCDeviceType.SC);
		}

		if (node instanceof MetroDevice) {
			MetroDevice device = (MetroDevice) node;
			tmoCmdResult.setLineId(device.getId().getLineId());
			tmoCmdResult.setStationId(device.getId().getStationId());
			tmoCmdResult.setNodeId(device.getId().getDeviceId());
			tmoCmdResult.setNodeType(device.getDeviceType());
		}

		tmoCmdResult.setUploadStatus((short) 0);
		//操作员id
		//tmoCmdResult.setOperatorId(Application.getUser().getUserID());
		tmoCmdResult.setCmdResult((short) result);
		tmoCmdResult.setRemark(resultDesc);
		tmoCmdResult.setCmdType(cmdType);
		try {
			commonDAO.saveObj(tmoCmdResult);
		} catch (Exception e) {
			logger.error("保存命令日志异常", e);
		}

		CommandResult resultitem = new CommandResult();
		resultitem.setId(node.name() + "/0x" + String.format("%08x", node.getNodeId()));
		resultitem.setCmdName(tmoCmdResult.getCmdName());
		resultitem.setResult((short) result);
		resultitem.setCmdResult(tmoCmdResult.getRemark());
		resultitem.setOccurTime(DateTimeUtil.formatCurrentDateToString("yyyy-MM-dd HH:mm:ss"));
		if (arg != null) {
			resultitem.setArg(arg.toString());
		} else {
			resultitem.setArg("无");
		}

		return resultitem;
	}
}
