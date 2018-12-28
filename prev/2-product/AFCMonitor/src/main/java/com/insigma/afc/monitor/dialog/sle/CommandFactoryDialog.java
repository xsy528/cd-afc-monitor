package com.insigma.afc.monitor.dialog.sle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.insigma.afc.monitor.ICommandFactory;
import com.insigma.afc.monitor.ICommandFactory.Command;
import com.insigma.afc.monitor.action.CommandActionHandler;
import com.insigma.afc.monitor.dialog.TreeDialog;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.framework.view.form.FormEditor;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.thread.EnhancedThread;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.widgets.Combo;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IInputControl;

public class CommandFactoryDialog extends TreeDialog {
	private ICommandFactory commandFactory;

	private final List<Button> buttons = new ArrayList<Button>();

	private final List<IInputControl> controls = new ArrayList<IInputControl>();

	private IInputControl activeCombo;

	private Command activeCommand;

	private ILogService synLogService;

	public ICommandFactory getCommandFactory() {
		return commandFactory;
	}

	public void setCommandFactory(ICommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	public CommandActionHandler getActionHandler() {
		return actionHandler;
	}

	public void setActionHandler(CommandActionHandler actionHandler) {
		this.actionHandler = actionHandler;
	}

	private CommandActionHandler actionHandler = new CommandActionHandler() {
		private Log logger = LogFactory.getLog(CommandActionHandler.class);

		private Object arg = null;

		@Override
		public void perform(final ActionContext context) {

			final List<MetroNode> ids = (List) getSelections();

			if (ids == null || ids.isEmpty()) {
				MessageForm.Message("请至少选择一个节点。");
				return;
			}
			if (!authority()) {
				return;
			}

			if (activeCombo != null) {
				arg = activeCombo.getObjectValue();
				logger.info("选择点信息 ： " + ReflectionToStringBuilder.toString(arg));
			}

			if (synLogService != null) {
				this.logService = synLogService;
			}

			EnhancedThread th = new EnhancedThread("发送命令") {

				@Override
				public void error(Exception e) {
					logger.error("发送命令异常。", e);
				}

				@Override
				public void execute() {
					List<MetroNode> nodes = new ArrayList<MetroNode>();
					nodes.addAll(ids);
					logger.info("发送命令 : " + activeCommand.getText());
					send(context, activeCommand.getId(), activeCommand.getText(), arg, nodes,
							activeCommand.getCmdType());
				}

			};
			th.start();
			getShell().close();
		}
	};

	public CommandFactoryDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	@Override
	public void createAction(List<Action> actions) {
		Action action = new Action("发送 (&S)", actionHandler);
		actions.add(action);
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {
		setSize(700, 600);
		setTitle("发送控制命令");
		setText("发送控制命令");

		//		List<Object> MetroNodeList = getSelections();
		//		String currNodeNameList = "";
		//		for (Object object : MetroNodeList) {
		//			MetroDevice metroNode = (MetroDevice) object;
		//			currNodeNameList += metroNode.getDeviceName() + " ";
		//		}
		//		setDescription("描述：向设备[ " + currNodeNameList + "]发送控制命令");
		setDescription("描述：向设备发送控制命令");

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

		for (final Command command : commandFactory.getCommands()) {

			if (command.getFilter() != null) {
				boolean valid = true;
				for (Object obj : selections) {
					if (obj instanceof MetroDevice) {
						if (!command.getFilter().validate(command, (MetroDevice) obj)) {
							valid = false;
						}
					}
				}

				if (!valid) {
					continue;
				}
			}

			final Button btn = new Button(composite, SWT.RADIO | SWT.INHERIT_DEFAULT);
			buttons.add(btn);
			btn.setText(command.getText());
			btn.setSelection(command.isSelected());
			if (command.isSelected()) {
				activeCommand = command;
			}

			if (command.getDefinition() != null) {
				Combo combo = new Combo(composite, SWT.READ_ONLY);
				combo.setEnabled(false);
				combo.setItems(command.getDefinition().getNameList());
				combo.setValues(command.getDefinition().getValueList());
				controls.add(combo);
				btn.setData(combo);
				combo.select(0);
			} else if (command.getForm() != null) {
				FormEditor editor = new FormEditor(composite, SWT.NONE);
				editor.setEnabled(false);
				command.getForm().setColums(1);
				editor.setForm(command.getForm());
				controls.add(editor);
				btn.setData(editor);
			} else {
				new Label(composite, SWT.NONE);
			}

			btn.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent arg0) {
					activeCommand = command;
					for (IInputControl control : controls) {
						control.setEnabled(false);
						activeCombo = null;
						if (btn.getData() != null) {
							((Control) btn.getData()).setEnabled(true);
							activeCombo = ((IInputControl) btn.getData());
						}
					}
				}

			});
		}

		composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}

	public void setSynLogService(ILogService synLogService) {
		this.synLogService = synLogService;
	}
}
