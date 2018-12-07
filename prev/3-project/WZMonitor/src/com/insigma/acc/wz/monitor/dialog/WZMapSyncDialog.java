package com.insigma.acc.wz.monitor.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.monitor.ICommandFactory.Command;
import com.insigma.afc.monitor.action.CommandActionHandler;
import com.insigma.afc.monitor.dialog.TreeDialog;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.afc.workbench.rmi.CmdHandlerResult;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.afc.workbench.rmi.ICommandService;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;

public class WZMapSyncDialog extends TreeDialog {

	private int id;

	private static Short cmdType = AFCCmdLogType.LOG_DEVICE_CMD.shortValue();

	private Command activeCommand;

	private ILogService synLogService;

	static IMetroNodeService metroNodeService;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public WZMapSyncDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	@Override
	protected void createAction(List<Action> actions) {
		actions.add(new Action("发送 (&S)", new MapSyncActionHandler()));
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {
		setSize(350, 400);
		setDescription("描述：同步地图");
		setTitle("同步地图");
		setText("同步地图");
		super.createContents(parent);
	}

	class MapSyncActionHandler extends CommandActionHandler {

		@Override
		public void perform(final ActionContext context) {
			CmdHandlerResult command = null;
			try {
				if (metroNodeService == null) {
					metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			List<MetroNode> ids = (List) getSelections();
			for (MetroNode metroNode : ids) {
				logger.info("地图同步数据 ：" + metroNode.name());
			}

			if (ids == null || ids.isEmpty()) {
				MessageForm.Message("请至少选择一个节点。");
				return;
			}
			Short[] lines = new Short[ids.size()];
			for (int i = 0; i < ids.size(); i++) {
				lines[i] = ((MetroLine) ids.get(i)).getLineID();
			}
			List<MetroStation> metroStations = metroNodeService.getMetroStation(lines, null);
			List<MetroNode> lists = new ArrayList<MetroNode>();
			for (MetroStation metroNode : metroStations) {
				if (metroNode.getStatus() != 0) {
					continue;
				}
				lists.add(metroNode);
			}
			if (!authority()) {
				return;
			}

			if (activeCommand != null) {
				cmdType = activeCommand.getCmdType();
			}

			if (synLogService != null) {
				this.logService = synLogService;
			}

			command = rmiGenerateFiles();
			if (command.isOK) {
				send(context, CommandType.CMD_SYNC_MAP, "地图同步命令", null, lists, cmdType);
			} else {
				//				logService.doBizErrorLog(command.getResultMessage());
				MessageForm.Message("错误信息",
						command.getResultMessage() == null ? "车站地图导出失败。" : command.getResultMessage(), SWT.ICON_ERROR);
			}

			getShell().close();
		}
	}

	public CmdHandlerResult rmiGenerateFiles() {

		ICommandService commandService = null;
		CmdHandlerResult command = new CmdHandlerResult();
		try {
			commandService = Application.getService(ICommandService.class);
			commandService.alive();
		} catch (Exception e) {
			//			MessageForm.Message("错误信息", "发送地图同步失败：工作台与通信服务器离线。", SWT.ICON_ERROR);
			command.messages.add("发送地图同步失败：工作台与通信服务器离线。" + e.getMessage());
			command.isOK = false;
			return command;
		}
		command = commandService.command(CommandType.CMD_EXPORT_MAP, Application.getUser().getUserID(), 1l);
		return command;
	}

	/**
	 * @return the activeCommand
	 */
	public Command getActiveCommand() {
		return activeCommand;
	}

	/**
	 * @param activeCommand
	 *            the activeCommand to set
	 */
	public void setActiveCommand(Command activeCommand) {
		this.activeCommand = activeCommand;
	}

	/**
	 * @return the synLogService
	 */
	public ILogService getSynLogService() {
		return synLogService;
	}

	/**
	 * @param synLogService
	 *            the synLogService to set
	 */
	public void setSynLogService(ILogService synLogService) {
		this.synLogService = synLogService;
	}
}
