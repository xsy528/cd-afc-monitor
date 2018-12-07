package com.insigma.afc.monitor.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.monitor.dialog.TreeDialog;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.form.ObjectTableViewer;

public class ModeBroadcastDialog extends TreeDialog {

	private ObjectTableViewer tableViewer;

	private List<TmoModeUploadInfo> modes = new ArrayList<TmoModeUploadInfo>();

	public List<TmoModeUploadInfo> getModes() {
		return modes;
	}

	public void setModes(List<TmoModeUploadInfo> modes) {
		this.modes = modes;
	}

	public ModeBroadcastDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	@Override
	protected void createAction(List<Action> actions) {
		actions.add(new Action("发送 (&S)", new ModeBroadcastActionHandler()));
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {
		setSize(950, 520);
		setDescription("描述：发送车站模式广播指令");
		setTitle("模式广播");

		super.createContents(parent);

		tableViewer = new ObjectTableViewer(parent, SWT.BORDER | SWT.CHECK);
		tableViewer.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		tableViewer.setObjectList(modes);
	}

	class ModeBroadcastActionHandler extends CommandActionHandler {

		@Override
		public void perform(ActionContext context) {

			List<MetroNode> ids = (List) getSelections();

			List<?> list = tableViewer.getSelectionIndicesObject();
			if (ids == null || ids.isEmpty()) {
				MessageForm.Message("提示信息", "请选择节点。", SWT.ICON_INFORMATION);
				return;
			}
			if (list.isEmpty()) {
				MessageForm.Message("提示信息", "请选择模式。", SWT.ICON_INFORMATION);
				return;
			}

			if (!authority()) {
				return;
			}

			send(context, CommandType.CMD_BROADCAST_MODE, "模式广播命令", null, ids, AFCCmdLogType.LOG_MODE.shortValue());

			getShell().close();
			return;
		}
	}
}
