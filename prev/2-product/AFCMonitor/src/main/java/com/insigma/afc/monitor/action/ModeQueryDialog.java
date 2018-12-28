package com.insigma.afc.monitor.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.monitor.ICommandFactory.Command;
import com.insigma.afc.monitor.dialog.TreeDialog;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;

public class ModeQueryDialog extends TreeDialog {

	private static Short cmdType = AFCCmdLogType.LOG_MODE.shortValue();

	private Command activeCommand;

	private ILogService synLogService;

	public ModeQueryDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	@Override
	protected void createAction(List<Action> actions) {
		actions.add(new Action("发送 (&S)", new ModeQueryActionHandler()));
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {
		setSize(350, 400);
		setDescription("描述：发送模式查询命令");
		setTitle("模式查询");
		setText("模式查询");
		super.createContents(parent);
	}

	class ModeQueryActionHandler extends CommandActionHandler {

		@Override
		public void perform(ActionContext context) {

			List<MetroNode> ids = (List) getSelections();

			// 只留下车站节点
			List<MetroNode> stationIds = new ArrayList<MetroNode>();
			for (MetroNode metroNode : ids) {
				if (metroNode instanceof MetroStation) {
					stationIds.add(metroNode);
				}
			}

			if (stationIds == null || stationIds.isEmpty()) {
				MessageForm.Message("请至少选择一个节点。");
				return;
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

			send(context, CommandType.CMD_QUERY_MODE, "模式查询命令", null, stationIds, cmdType);

			getShell().close();
		}
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
