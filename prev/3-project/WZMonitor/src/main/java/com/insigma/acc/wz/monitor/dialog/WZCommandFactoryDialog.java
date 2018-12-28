/*
 * 日期：2011-7-22
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.monitor.dialog;

import java.util.ArrayList;
import java.util.List;

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

import com.insigma.acc.wz.monitor.form.WZUploadAssignFileForm;
import com.insigma.afc.monitor.ICommandFactory;
import com.insigma.afc.monitor.ICommandFactory.Command;
import com.insigma.afc.monitor.action.CommandActionHandler;
import com.insigma.afc.monitor.dialog.TreeDialog;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.view.form.FormEditor;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.thread.EnhancedThread;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.StandardDialog;
import com.insigma.commons.ui.widgets.Combo;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IInputControl;

/**
 * Ticket:
 * 
 * @author 董飞
 */
public class WZCommandFactoryDialog extends TreeDialog {

	private ICommandFactory commandFactory;

	private final List<Button> buttons = new ArrayList<Button>();

	private final List<IInputControl> controls = new ArrayList<IInputControl>();

	private IInputControl activeCombo;

	private Command activeCommand;

	private ILogService synLogService;

	//private List<IInputControl> activeCombos;

	private List<Command> activeCommands;

	/**
	 * @return
	 */
	public ICommandFactory getCommandFactory() {
		return commandFactory;
	}

	/**
	 * @param commandFactory
	 */
	public void setCommandFactory(final ICommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	/**
	 * @return
	 */
	public CommandActionHandler getActionHandler() {
		return actionHandler;
	}

	/**
	 * @param actionHandler
	 */
	public void setActionHandler(final CommandActionHandler actionHandler) {
		this.actionHandler = actionHandler;
	}

	/**
	 * 实例化
	 */
	private CommandActionHandler actionHandler = new CommandActionHandler() {
		private final Log logger = LogFactory.getLog(CommandActionHandler.class);

		private Object arg = null;

		List<Object> args;

		List<Integer> cmdIds;

		List<String> cmdIdTexts;

		@Override
		@SuppressWarnings("unchecked")
		public void perform(final ActionContext action) {

			final List<MetroNode> ids = (List) getSelections();

			final List<MetroNode> deviceIds = new ArrayList<MetroNode>();
			for (MetroNode metroNode : ids) {
				if (metroNode instanceof MetroDevice) {
					deviceIds.add(metroNode);
				}
			}

			if (deviceIds == null || deviceIds.isEmpty()) {
				MessageForm.Message("提示信息", "请选择节点。", SWT.ICON_INFORMATION);
				return;
			}

			if (activeCombo != null) {
				arg = activeCombo.getObjectValue();

				if (arg != null && arg instanceof Form) {
					Form form = (Form) arg;
					boolean rst = checkEmpty(form);
					if (!rst) {
						MessageForm.Message("请选择参数。");
						return;
					}
				}
			}

			if (!authority()) {
				return;
			}

			//			 
			//			for (Command command : activeCommands) {
			//				cmdIds.add(command.getId());
			//				cmdIdTexts.add(command.getText());
			//
			//				if (command.getId() == WZCommandType.CMD_UPLOAD_SPEC_FILE.shortValue()) {
			//					Form form = (Form) arg;
			//					WZUDRequestForm askForm = (WZUDRequestForm) form.getEntity();
			//					Long startSN = askForm.getStartSN();
			//					Long endSN = askForm.getEndSN();
			//					if (startSN == null || endSN == null) {
			//						MessageForm.Message("提示信息", "流水号不能为空。", SWT.ICON_ERROR);
			//						return;
			//					}
			//					if (startSN > endSN) {
			//						MessageForm.Message("提示信息", "结束流水号应大于起始流水号。", SWT.ICON_INFORMATION);
			//						return;
			//					}
			//				}
			//			}

			{

				if (synLogService != null) {

					this.logService = synLogService;
				}
				EnhancedThread th = new EnhancedThread("发送命令") {

					@Override
					public void error(final Exception e) {
						logger.error("发送命令异常。", e);
					}

					@Override
					public void execute() {
						//						List<MetroNode> nodes = new ArrayList<MetroNode>();
						//						nodes.addAll(stationIds);
						send(action, activeCommand.getId(), activeCommand.getText(), arg, deviceIds,
								activeCommand.getCmdType());
					}

				};
				th.start();

			}
			getShell().close();
		}

		//		private boolean checkEmpty(Form form) {
		//            if (form != null) {
		//                Field[] fields = form.getClass().getDeclaredFields();
		//                for (Field field : fields) {
		//                    View view = field.getAnnotation(View.class);
		//                    if (view == null) {
		//                        continue;
		//                    }
		//                    if (view.type().equals("CheckBox")) {
		//                        Object obj = BeanUtil.getValue(form, field);
		//                        if (obj == null) {
		//                            return false;
		//                        } else if (obj instanceof List) {
		//                            List list = (List) obj;
		//                            if (list.size() <= 0) {
		//                                return false;
		//                            }
		//                        }
		//                    }
		//                }
		//            } else {
		//                return false;
		//            }
		//
		//            return true;
		//        }

		private boolean checkEmpty(Form form) {
			if (form != null) {
				WZUploadAssignFileForm udForm = null;
				if (form.getEntity() instanceof WZUploadAssignFileForm) {
					udForm = (WZUploadAssignFileForm) form.getEntity();
				} else {
					return true;
				}
				if (udForm.getFileHeaderTag() == null || udForm.getStartSN() == null || udForm.getEndSN() == null) {
					return false;
				}
			} else {
				return false;
			}

			return true;
		}

	};

	/**
	 * @param parent
	 * @param style
	 */
	public WZCommandFactoryDialog(final EditorFrameWork parent, final int style) {
		super(parent, style | StandardDialog.BUTTONBAR);
	}

	/**
	 *
	 */
	@Override
	public void createAction(final List<Action> actions) {
		actions.add(new Action("发送 (&S)", actionHandler));
		actions.add(new CloseAction());
	}

	@Override
	protected void createContents(final Composite parent) {
		setSize(850, 600);
		setTitle("线路控制命令");
		setText("线路控制命令");
		setDescription("描述：向终端发送控制命令");

		super.createContents(parent);

		EnhanceComposite composite = new EnhanceComposite(parent, SWT.BORDER);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.marginHeight = 20;
		gridLayout.marginLeft = 20;
		gridLayout.marginWidth = 20;
		gridLayout.horizontalSpacing = 20;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);

		for (final Command command : commandFactory.getCommands()) {

			if (command.getFilter() != null) {
				boolean valid = false;
				for (Object obj : selections) {
					if (obj instanceof MetroStation) {
						if (command.getFilter().validate(command, null)) {
							valid = true;
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

	/**
	 * @param synLogService
	 */
	public void setSynLogService(final ILogService synLogService) {
		this.synLogService = synLogService;
	}

}
