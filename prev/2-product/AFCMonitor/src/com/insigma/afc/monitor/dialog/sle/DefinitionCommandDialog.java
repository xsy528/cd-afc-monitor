package com.insigma.afc.monitor.dialog.sle;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.monitor.action.CommandActionHandler;
import com.insigma.afc.monitor.dialog.TreeDialog;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.commons.dic.IDefinition;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.widgets.EnhanceComposite;

public class DefinitionCommandDialog extends TreeDialog {

	private IDefinition definition;

	private CommandActionHandler actionHandler;

	private int currentCommandID;

	private ILogService synLogService;

	public IDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(IDefinition definition) {
		this.definition = definition;
	}

	public CommandActionHandler getActionHandler() {
		return actionHandler;
	}

	public void setActionHandler(CommandActionHandler actionHandler) {
		this.actionHandler = actionHandler;
	}

	public DefinitionCommandDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	@Override
	public void createAction(List<Action> actions) {
		actions.add(new Action("发送 (&S)", new DefinitionCommandActionHandler()));
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {

		super.createContents(parent);

		EnhanceComposite composite = new EnhanceComposite(parent, SWT.BORDER);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginHeight = 20;
		gridLayout.marginLeft = 20;
		gridLayout.marginWidth = 20;
		gridLayout.horizontalSpacing = 20;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);

		for (final String name : definition.getNameList()) {
			Button btn = new Button(composite, SWT.RADIO | SWT.INHERIT_DEFAULT);
			btn.setText(name);
			btn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					currentCommandID = ((Number) definition.getValueByName(name)).intValue();
				}
			});
		}

		composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}

	class DefinitionCommandActionHandler extends CommandActionHandler {

		@Override
		public void perform(final ActionContext context) {

			List<MetroNode> ids = (List) getSelections();

			if (ids == null || ids.isEmpty()) {
				MessageForm.Message("提示信息", "请选择节点。", SWT.ICON_INFORMATION);
				return;
			}
			if (!authority()) {
				return;
			}

			if (synLogService != null) {
				this.logService = synLogService;
			}

			send(context, CommandType.CMD_UPLOAD_FILE, "文件上传命令-" + definition.getNameByValue(currentCommandID),
					currentCommandID, ids, AFCCmdLogType.LOG_DEVICE_CMD.shortValue());

			getShell().close();
		}
	}

	public void setSynLogService(ILogService synLogService) {
		this.synLogService = synLogService;
	}
}
