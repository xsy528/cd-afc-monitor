package com.insigma.afc.monitor.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.monitor.dialog.TreeDialog;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.commons.dic.annotation.DicItem;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;

@SuppressWarnings("unchecked")
public class ModeCommandDialog extends TreeDialog {

	private Combo comboDescend;

	private Combo comboNormal;

	private Combo comboCombine;

	private Button btnNormal;

	private Button btnDescend;

	private Button btnOnEmergency;

	private boolean bTreeInvisable = true;

	private ILogService synLogService;

	public ModeCommandDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	@Override
	protected void createAction(List<Action> actions) {
		actions.add(new Action("发送 (&S)", new SendModelAdapter()));
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {

		setSize(600, 500);
		setDescription("描述：向车站发送运营模式");
		setTitle("发送运营模式");
		setText("发送运营模式");
		super.createContents(parent);

		Group group = new Group(parent, SWT.NONE);
		group.setText("选择模式");
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		btnNormal = new Button(group, SWT.RADIO);
		btnNormal.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				comboNormal.setEnabled(true);
				comboDescend.setEnabled(false);
				comboCombine.setEnabled(false);
			}
		});
		btnNormal.setSelection(true);
		btnNormal.setText("正常运行模式");
		btnNormal.setLayoutData(new GridData(GridData.FILL_BOTH));

		comboNormal = new Combo(group, SWT.READ_ONLY);
		comboNormal.setLayoutData(new GridData(GridData.FILL_BOTH));

		btnDescend = new Button(group, SWT.RADIO);
		btnDescend.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				comboDescend.setEnabled(true);
				comboNormal.setEnabled(false);
				comboCombine.setEnabled(false);
			}
		});
		btnDescend.setText("降级运行模式");
		btnDescend.setLayoutData(new GridData(GridData.FILL_BOTH));

		comboDescend = new Combo(group, SWT.READ_ONLY);
		comboDescend.setLayoutData(new GridData(GridData.FILL_BOTH));
		comboDescend.setEnabled(false);

		btnOnEmergency = new Button(group, SWT.RADIO);
		btnOnEmergency.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				comboCombine.setEnabled(true);
				comboDescend.setEnabled(false);
				comboNormal.setEnabled(false);
			}
		});
		btnOnEmergency.setText("紧急放行模式");
		btnOnEmergency.setLayoutData(new GridData(GridData.FILL_BOTH));
		comboCombine = new Combo(group, SWT.READ_ONLY);
		comboCombine.setLayoutData(new GridData(GridData.FILL_BOTH));
		comboCombine.setEnabled(false);

		Map<Object, DicItem> modeCodeMap = AFCModeCode.getInstance().getDicItemMap();
		for (Object code : modeCodeMap.keySet()) {
			DicItem view = modeCodeMap.get(code);
			if (null == view) {
				continue;
			}
			if (AFCModeCode.MODE_SIGN_NORMAL.equals(view.group())) {
				comboNormal.add(view.name());
			} else if (AFCModeCode.MODE_SIGN_URGENCY.equals(view.group())) {
				comboCombine.add(view.name());
			} else {
				comboDescend.add(view.name());
			}
		}
		comboNormal.select(0);
		comboDescend.select(0);
		comboCombine.select(0);
	}

	public int getSelectModeCode() {
		int modeCode = 0;
		String modeCodeName = "";
		if (comboNormal.isEnabled()) {
			modeCodeName = comboNormal.getText();
		} else if (comboDescend.isEnabled()) {
			modeCodeName = comboDescend.getText();
		} else if (comboCombine.isEnabled()) {
			modeCodeName = comboCombine.getText();
		}
		Map<String, Object> modeNameMap = AFCModeCode.getInstance().getNameMap();
		if (modeNameMap.containsKey(modeCodeName)) {
			modeCode = ((Number) modeNameMap.get(modeCodeName)).intValue();
		}
		return modeCode;
	}

	class SendModelAdapter extends CommandActionHandler {

		@Override
		public void perform(ActionContext context) {

			List<MetroNode> ids = new ArrayList<MetroNode>();
			//SC不需要要树，默认给全部子节点设备发送
			if (AFCApplication.getAFCNodeType().equals(AFCNodeLevel.SC)) {
				ids.add(AFCApplication.getAFCNode());
			} else {
				ids = (List) getSelections();
			}
			int modeCode = getSelectModeCode();

			if (ids == null || ids.isEmpty()) {
				MessageForm.Message("提示信息", "请至少选择一个节点。", SWT.ICON_INFORMATION);
				return;
			}
			if (!authority()) {
				return;
			}
			String name = AFCModeCode.getInstance().getNameByValue(modeCode);
			if (null != name && !"".equals(name)) {
				name = "[" + name + "]";
			} else {
				name = "模式";
			}
			name += "切换命令";
			if (synLogService != null) {
				this.logService = synLogService;
			}
			send(context, CommandType.CMD_CHANGE_MODE, name, modeCode, ids, AFCCmdLogType.LOG_MODE.shortValue());

			getShell().close();
			return;
		}

	}

	public boolean isbTreeInvisable() {
		return bTreeInvisable;
	}

	public void setbTreeInvisable(boolean bTreeInvisable) {
		this.bTreeInvisable = bTreeInvisable;
	}

	public void setSynLogService(ILogService synLogService) {
		this.synLogService = synLogService;
	}

}
