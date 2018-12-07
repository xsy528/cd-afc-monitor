/* 
 * 日期：2018年10月31日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.dic.AFCCmdResultType;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.monitor.action.CommandResult;
import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.ui.dialog.PWDDialog;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.afc.workbench.rmi.ICommandService;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.IUser;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.dialog.TableViewDialog;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.thread.EnhancedThread;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.ProgressBarDialog;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * Ticket: 
 * @author  matianming
 *
 */
public class WZCommandActionHandler extends ActionHandlerAdapter{
	@Autowire
	protected ICommandService commandService;

	@Autowire
	protected ICommonDAO commonDAO;

	private static Long counter=0L;
	
	private volatile boolean locker=true;
	
	final List<CommandResult> results = new Vector<CommandResult>();

	private List<MetroNode> nodes;
	
	private Lock lock=new ReentrantLock();

	// 对话框是否已经显示过，避免由于线程异步导致多次弹出对话框
//	private volatile boolean hasShow = false;
	
	private Map<Long, ProgressBarDialog> hasShowMap=new ConcurrentHashMap<>();

	private ProgressBarDialog progress = null;

	public void setProgress(final ProgressBarDialog progress) {
		this.progress = progress;
	}

	public ICommandService getCommandService() {
		return commandService;
	}

	public void setCommandService(final ICommandService commandService) {
		this.commandService = commandService;
	}

	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(final ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void send(final ActionContext context, final int id, final String name, final Object arg,
			final List<MetroNode> nodes, final short cmdType) {
		locker=true;
		this.nodes = nodes;
		results.clear();
		try {
			commandService.alive();
		} catch (Exception e) {
			if (logService != null) {
				logService.doBizErrorLog("发送命令失败：工作台与通信服务器离线。", e);
			}
			MessageForm.Message("错误信息", "发送命令失败：工作台与通信服务器离线。", SWT.ICON_ERROR);
			return;
		}

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell();
				progress = new ProgressBarDialog(shell, SWT.NONE);
				progress.setText("正在发送命令，请稍候...");
				hasShowMap.put(counter, progress);
				locker=false;
				progress.open();
				if (shell != null && !shell.isDisposed()) {
					shell.dispose();
					shell = null;
				}
			}
		});
		Date date=new Date();
		while(locker){
			if((new Date().getTime()-date.getTime())>5000){
				Iterator<ProgressBarDialog > iterator=hasShowMap.values().iterator();
				for(;iterator.hasNext();){
					iterator.next().close();
				}
				hasShowMap.clear();
				logger.info("locker等待超时");
				return;
			}
		}
		for (MetroNode node : nodes) {
			CommandThread thread = new CommandThread(counter,context, id, name, arg, node, cmdType, nodes.size());
			thread.start();
		}
		counter++;
	}

	public void send(final ActionContext context, final List<Integer> cmdIds, final List<String> cmdIdTexts,
			final Object arg, final List<MetroNode> nodes, final short cmdType) {
		this.nodes = nodes;
		results.clear();
		locker=true;
		try {
			commandService.alive();
		} catch (Exception e) {
			if (logService != null) {
				logService.doBizErrorLog("发送命令失败：工作台与通信服务器离线。", e);
			}
			MessageForm.Message("错误信息", "发送命令失败：工作台与通信服务器离线。", SWT.ICON_ERROR);
			return;
		}

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell();
				progress = new ProgressBarDialog(shell, SWT.NONE);
				progress.setText("正在发送命令，请稍候...");
				hasShowMap.put(counter, progress);
				progress.open();
				if (shell != null && !shell.isDisposed()) {
					shell.dispose();
					shell = null;
				}
			}
		});
		Date date=new Date();
		while(locker){
			if((new Date().getTime()-date.getTime())>5000){
				Iterator<ProgressBarDialog > iterator=hasShowMap.values().iterator();
				for(;iterator.hasNext();){
					iterator.next().close();
				}
				hasShowMap.clear();
				logger.info("locker等待超时");
				return;
			}
		}
		List<Object> args = (ArrayList) arg;
		// 每一个目的节点对应的一种设备命令起一个线程
		for (int i = 0; i < cmdIds.size(); i++) {
			Integer id = cmdIds.get(i);
			String name = cmdIdTexts.get(i);
			Object e = null;
			if (args != null) {
				e = args.get(i);
			}
			for (MetroNode node : nodes) {
				CommandThread thread = new CommandThread(counter,context, id, name, e, node, cmdType,
						nodes.size() * cmdIds.size());
				thread.start();
			}
		}
		counter++;
	}

	public class CommandThread extends EnhancedThread {

		private ProgressBarDialog dialog;
		
		private Long threadId;
		
		private final int id;

		private final String name;

		private final Object arg;

		private final ActionContext context;

		private final MetroNode node;

		private final short cmdType;

		private final int totalCount;

		public CommandThread(Long threadId,final ActionContext context, final int id, final String name, final Object arg,
				final MetroNode node, final short cmdType, final int totalCount) {
			super("命令发送线程");
			this.context = context;
			this.id = id;
			this.name = name;
			this.arg = arg;
			this.node = node;
			this.cmdType = cmdType;
			this.totalCount = totalCount;
			this.threadId=threadId;
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
				CmdHandlerResult command = commandService.command(id, Application.getUser().getUserID(),
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

			if (hasShowMap.get(threadId)!=null) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
//						if (progress != null) {
//							progress.close();
//							progress = null;
//						}
						hasShowMap.get(threadId).close();
						hasShowMap.remove(threadId);
						int[] widths = new int[] { 115, 250, 165, 150, 150 };
						TableViewDialog viewdlg = new TableViewDialog(context.getFrameWork(), SWT.NONE);
						viewdlg.setText("命令发送结果");
						viewdlg.setTitle("命令发送结果");
						viewdlg.setDescription("描述：命令发送结果列表");
						viewdlg.setSize(700, 500);
						try{
							lock.lockInterruptibly();
							if(results!=null && results.size()>0){
								viewdlg.setDataList(sortResults(results));
								results.clear();
							}else{
								viewdlg=null;
								return;
							}
						}catch(Exception e){
							logger.info("CommandThread locker等待超时");
						}finally {
							lock.unlock();
						}
						viewdlg.setWidths(widths);
						viewdlg.open();
					}
				});
			}
		}
	}

	public List<CommandResult> sortResults(final List<CommandResult> results) {
		List<CommandResult> orderResulsts = new ArrayList<CommandResult>();

		if (nodes == null || results == null) {
			return orderResulsts;
		}

		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < results.size(); j++) {
				if (nodes.get(i).name().equals(results.get(j).getId())) {
					orderResulsts.add(results.get(j));
					// break;
				}
			}
		}

		return orderResulsts;
	}

	public boolean authority() {
		String uid = "";
		IUser user = Application.getUser();
		if (user != null) {
			uid = user.getUserID();
		}

		PWDDialog pwdDialog = new PWDDialog(Display.getDefault().getActiveShell(), uid);
		pwdDialog.setPwdFocus();
		Boolean success = (Boolean) pwdDialog.open();
		if (success == null || !success) {
			return false;
		}

		return true;
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
		//		if (arg != null) {
		//			// tmoCmdResult.setRemark("命令参数：" + BeanUtil.toString(arg));
		//			// tmoCmdResult.setTagValue(tagValue)
		//			if (arg instanceof Form) {
		//				Form form = (Form) arg;
		//				String string = form.getEntity().toString();
		//				command = string;
		//			}
		//		}

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
		tmoCmdResult.setOperatorId(Application.getUser().getUserID());
		tmoCmdResult.setCmdResult((short) result);
		tmoCmdResult.setRemark(resultDesc);
		tmoCmdResult.setCmdType(cmdType);
		try {
			commonDAO.saveObj(tmoCmdResult);
		} catch (Exception e) {
			logger.error("保存命令日志异常", e);
		}

		CommandResult resultitem = new CommandResult();
		resultitem.setId(node.name());
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
