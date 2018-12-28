/*
 * 日期：2012-3-6
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.monitor.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.insigma.afc.monitor.ICommandFactory;
import com.insigma.afc.monitor.ICommandFactory.Command;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.framework.view.form.FormEditor;
import com.insigma.commons.ui.widgets.Combo;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IInputControl;

public class WZCommandComposite extends EnhanceComposite {

	private Command activeCommand;

	private IInputControl activeCombo;

	private List<IInputControl> activeCombos = new ArrayList<IInputControl>();

	private List<Command> activeCommands = new ArrayList<Command>();

	private ICommandFactory commandFactory;

	private List<?> selections;

	private List<Button> buttons = new ArrayList<Button>();

	private final List<IInputControl> controls = new ArrayList<IInputControl>();

	private CallBackHandler callBackHandler;

	private final Map<Button, Command> cmdMap = new HashMap<Button, Command>();

	/**
	 * @param callBackHandler
	 *            the callBackHandler to set
	 */
	public void setCallBackHandler(CallBackHandler callBackHandler) {
		this.callBackHandler = callBackHandler;
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public WZCommandComposite(Composite parent, int style) {
		super(parent, style);
		// createContents();
	}

	public void createContents() {
		// 获取所有设备命令
		boolean isSelected = false;
		for (final Command command : commandFactory.getCommands()) {
			if (!isValid(command)) {
				continue;
			}
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

			final Button btn;
			btn = new Button(this, SWT.CHECK | SWT.INHERIT_DEFAULT);
			if (!isSelected) {
				isSelected = true;
				// command.setSelected(true);
			}
			buttons.add(btn);
			btn.setText(command.getText());
			btn.setSelection(command.isSelected());
			btn.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
			cmdMap.put(btn, command);

			Combo combo;
			if (command.getDefinition() != null) {
				combo = new Combo(this, SWT.READ_ONLY);
				combo.setEnabled(false);
				combo.setItems(command.getDefinition().getNameList());
				combo.setValues(command.getDefinition().getValueList());
				controls.add(combo);
				btn.setData(combo);
				combo.select(0);
			} else if (command.getForm() != null) {
				FormEditor editor;
				editor = new FormEditor(this, SWT.NONE);
				editor.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				editor.setEnabled(false);
				editor.setForm(command.getForm());
				controls.add(editor);
				btn.setData(editor);
			} else {
				new Label(this, SWT.NONE);
			}
			if (command.isSelected()) {
				activeCommand = command;
				if (btn.getData() != null) {
					((Control) btn.getData()).setEnabled(true);
					activeCombo = ((IInputControl) btn.getData());
				}
			}

			btn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					activeCommand = command;
					if (btn.getData() != null) {
						if (((Control) btn.getData()).isEnabled()) {
							((Control) btn.getData()).setEnabled(false);
						} else {
							((Control) btn.getData()).setEnabled(true);
						}
					}
					activeCombos.clear();
					activeCommands.clear();
					for (Button btn : buttons) {
						if (btn.getSelection()) {
							if (btn.getData() != null) {
								activeCombos.add((IInputControl) btn.getData());
							} else {
								activeCombos.add(null);
							}
							activeCommands.add(cmdMap.get(btn));
						}
					}

					for (IInputControl control : controls) {
						// control.setEnabled(false);
						activeCombo = null;
						if (btn.getData() != null) {
							// ((Control) btn.getData()).setEnabled(true);
							activeCombo = ((IInputControl) btn.getData());
						}
					}
					if (callBackHandler != null) {
						callBackHandler.callBack(activeCommand, activeCombo, activeCommands, activeCombos);
					}
				}

			});

			if (callBackHandler != null) {
				callBackHandler.callBack(activeCommand, activeCombo, activeCommands, activeCombos);
			}
		}
	}

	public boolean isValid(Command command) {
		return true;
	}

	// public Command getActiveCommand() {
	// return activeCommand;
	// }
	//
	// public IInputControl getActiveControl() {
	// return activeCombo;
	// }

	@Override
	public void active(Object arg) {
		if (callBackHandler != null) {
			callBackHandler.callBack(activeCommand, activeCombo, activeCommands, activeCombos);
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * @param commandFactory
	 *            the commandFactory to set
	 */
	public void setCommandFactory(ICommandFactory commandFactory, List<?> selections) {
		this.commandFactory = commandFactory;
		this.selections = selections;
		createContents();
	}

	public interface CallBackHandler {

		void callBack(Command activeCommand, IInputControl activeControl, List<Command> activeCommands,
				List<IInputControl> activeCombos);
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

}
