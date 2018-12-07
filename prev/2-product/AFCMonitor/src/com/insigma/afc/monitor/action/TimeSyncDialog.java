package com.insigma.afc.monitor.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCCmdLogType;
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

public class TimeSyncDialog extends TreeDialog {

	private ILogService synLogService;

	public TimeSyncDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	@Override
	protected void createAction(List<Action> actions) {
		actions.add(new Action("发送 (&S)", new TimeSyncActionHandler()));
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {
		setSize(350, 400);
		setDescription("描述：发送时钟同步命令");
		setText("时钟同步");
		setTitle("时钟同步");
		super.createContents(parent);
	}

	class TimeSyncActionHandler extends CommandActionHandler {

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
				MessageForm.Message("提示信息", "请至少选择一个节点。", SWT.ICON_INFORMATION);
				return;
			}
			if (AFCApplication.getAFCNodeType().equals(AFCNodeLevel.SC)) {
				int i = 0;
				for (; i < stationIds.size(); i++) {
					MetroNode node = stationIds.get(i);
					if (node instanceof MetroStation) {
						break;
					}

				}
				if (i < stationIds.size()) {
					stationIds.remove(i);
				}
			}
			if (!authority()) {
				return;
			}

			if (synLogService != null) {
				this.logService = synLogService;
			}

			send(context, CommandType.CMD_TIME_SYNC, "时间同步命令", null, stationIds,
					Short.valueOf(AFCCmdLogType.LOG_TIME_SYNC.toString()));

			getShell().close();
		}
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
