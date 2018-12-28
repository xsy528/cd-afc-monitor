package com.insigma.acc.wz.monitor.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.monitor.action.CommandActionHandler;
import com.insigma.afc.monitor.dialog.TreeDialog;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.dialog.StandardDialog;
import com.insigma.commons.ui.widgets.Label;

@SuppressWarnings("unchecked")
public class WZModeCommandDialog extends TreeDialog {

	Log logger = LogFactory.getLog(WZModeCommandDialog.class);

	//	private int modeNumber = SJZModeOrder.modeNumber;

	//	private Button[] btnMode = new Button[modeNumber];

	//	private Composite[] rGroup = new Composite[modeNumber];

	//	private List<Object> radio = new ArrayList<Object>();
	private Button btnNormal;

	private Button btnDescend;

	private Button btnOnEmergency;

	private Button btnBreakdown;

	private Button[] checkNormal;

	private Button[] checkDescend;

	private Button[] checkEmergency;

	private Button[] checkBreakdown;

	private Group groupNormal;

	private Group groupDescend;

	private Group groupEmergency;

	private Group groupBreakdown;

	private boolean bTreeInvisable = true;

	private ILogService synLogService;

	private Map<String, Object> btnmapCombo = new HashMap();

	//模式名称和ID匹配
	private Map<String, Object> codeByName = new HashMap();
	private int kindNumber = 0;

	public WZModeCommandDialog(EditorFrameWork parent, int style) {
		super(parent, style | StandardDialog.BUTTONBAR);
	}

	@Override
	protected void createAction(List<Action> actions) {
		actions.add(new Action("发送 (&S)", new SendModelAdapter()));
		actions.add(new CloseAction());
	}

	@Override
	protected void createContents(Composite parent) {
		setSize(600, 600);
		setDescription("描述：向车站发送运营模式");
		setTitle("发送运营模式");
		setText("发送运营模式");
		super.createContents(parent);

		Group group = new Group(parent, SWT.NONE);
		group.setText("选择模式");
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		//正常模式
		btnNormal = new Button(group, SWT.RADIO);
		btnNormal.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				for (int i = 0; i < checkNormal.length; i++) {
					checkNormal[i].setEnabled(true);
				}
				checkNormal[0].setSelection(true);
				for (int i = 0; i < checkDescend.length; i++) {
					checkDescend[i].setEnabled(false);
				}
				for (int i = 0; i < checkEmergency.length; i++) {
					checkEmergency[i].setEnabled(false);
					checkEmergency[i].setSelection(false);
				}
				for (int i = 0; i < checkBreakdown.length; i++) {
					checkBreakdown[i].setEnabled(false);
					checkEmergency[i].setSelection(false);
				}
				groupNormal.setEnabled(true);
				groupDescend.setEnabled(false);
				groupEmergency.setEnabled(false);
				groupBreakdown.setEnabled(false);
			}
		});

		btnNormal.setSelection(true);
		btnNormal.setText("正常运行模式");
		btnNormal.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupNormal = new Group(group, SWT.NONE);
		groupNormal.setLayout(new GridLayout());
		groupNormal.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label lineNormal = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineNormal.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		//降级模式
		btnDescend = new Button(group, SWT.RADIO);
		btnDescend.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				for (int i = 0; i < checkNormal.length; i++) {
					checkNormal[i].setEnabled(false);
					checkNormal[i].setSelection(false);
				}
				for (int i = 0; i < checkDescend.length; i++) {
					checkDescend[i].setEnabled(true);
				}
				for (int i = 0; i < checkEmergency.length; i++) {
					checkEmergency[i].setEnabled(false);
					checkEmergency[i].setSelection(false);
				}
				for (int i = 0; i < checkBreakdown.length; i++) {
					checkBreakdown[i].setEnabled(false);
					checkEmergency[i].setSelection(false);
				}
				groupNormal.setEnabled(false);
				groupDescend.setEnabled(true);
				groupEmergency.setEnabled(false);
				groupBreakdown.setEnabled(false);
			}
		});
		btnDescend.setText("降级运行模式");
		btnDescend.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupDescend = new Group(group, SWT.NONE);
		groupDescend.setLayout(new GridLayout(2, true));
		groupDescend.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupDescend.setEnabled(false);

		Label lineDescend = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineDescend.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		//紧急放行
		btnOnEmergency = new Button(group, SWT.RADIO);
		btnOnEmergency.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				for (int i = 0; i < checkNormal.length; i++) {
					checkNormal[i].setEnabled(false);
					checkNormal[i].setSelection(false);
				}
				for (int i = 0; i < checkDescend.length; i++) {
					checkDescend[i].setEnabled(false);
				}
				for (int i = 0; i < checkEmergency.length; i++) {
					checkEmergency[i].setEnabled(true);
				}
				checkEmergency[0].setSelection(true);
				for (int i = 0; i < checkBreakdown.length; i++) {
					checkBreakdown[i].setEnabled(false);
					checkBreakdown[i].setSelection(false);
				}
				groupNormal.setEnabled(false);
				groupDescend.setEnabled(false);
				groupEmergency.setEnabled(true);
				groupBreakdown.setEnabled(false);
			}
		});
		btnOnEmergency.setText("紧急运营模式");
		btnOnEmergency.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupEmergency = new Group(group, SWT.NONE);
		groupEmergency.setLayout(new GridLayout());
		groupEmergency.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupEmergency.setEnabled(false);

		Label lineEmergency = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineEmergency.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		//列车故障模式
		btnBreakdown = new Button(group, SWT.RADIO);
		btnBreakdown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				for (int i = 0; i < checkNormal.length; i++) {
					checkNormal[i].setEnabled(false);
					checkNormal[i].setSelection(false);
				}
				for (int i = 0; i < checkDescend.length; i++) {
					checkDescend[i].setEnabled(false);
				}
				for (int i = 0; i < checkEmergency.length; i++) {
					checkEmergency[i].setEnabled(false);
					checkEmergency[i].setSelection(false);
				}
				for (int i = 0; i < checkBreakdown.length; i++) {
					checkBreakdown[i].setEnabled(true);
				}
				checkBreakdown[0].setSelection(true);
				groupNormal.setEnabled(false);
				groupDescend.setEnabled(false);
				groupEmergency.setEnabled(false);
				groupBreakdown.setEnabled(true);
			}
		});
		btnBreakdown.setText("列车故障模式");
		btnBreakdown.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupBreakdown = new Group(group, SWT.NONE);
		groupBreakdown.setLayout(new GridLayout());
		groupBreakdown.setLayoutData(new GridData(GridData.FILL_BOTH));
		groupBreakdown.setEnabled(false);

		// 显示checkBox
		List<PairValue<Object, String>> normalList = AFCModeCode.getInstance().getByGroup(AFCModeCode.MODE_SIGN_NORMAL);
		List<PairValue<Object, String>> descendList = AFCModeCode.getInstance()
				.getByGroup(AFCModeCode.MODE_SIGN_DESCEND);
		List<PairValue<Object, String>> emergencyList = AFCModeCode.getInstance()
				.getByGroup(AFCModeCode.MODE_SIGN_URGENCY);
		//List<PairValue<Object, String>> emergencyList = SJZModeOrder.getInstance().getUrgencyGroup();
		List<PairValue<Object, String>> breakdownList = AFCModeCode.getInstance()
				.getByGroup(AFCModeCode.MODE_SIGN_BREAKDOWN);
		checkNormal = new Button[normalList.size()];
		checkDescend = new Button[descendList.size()];
		checkEmergency = new Button[emergencyList.size()];
		checkBreakdown = new Button[breakdownList.size()];

		// 正常模式的checkbox
		int i = 0;
		for (PairValue<Object, String> pairValue : normalList) {
			checkNormal[i] = new Button(groupNormal, SWT.RADIO);
			checkNormal[i].setText(pairValue.getValue().toString());
			checkNormal[i].setLayoutData(new GridData(GridData.FILL_BOTH));
			i++;
		}
		// 降级模式的checkbox
		i = 0;
		for (PairValue<Object, String> pairValue : descendList) {
			checkDescend[i] = new Button(groupDescend, SWT.RADIO);
			checkDescend[i].setText(pairValue.getValue().toString());
			checkDescend[i].setLayoutData(new GridData(GridData.FILL_BOTH));
			i++;
		}
		// 紧急模式的checkbox
		i = 0;
		for (PairValue<Object, String> pairValue : emergencyList) {
			checkEmergency[i] = new Button(groupEmergency, SWT.RADIO);
			checkEmergency[i].setText(pairValue.getValue().toString());
			checkEmergency[i].setLayoutData(new GridData(GridData.FILL_BOTH));
			i++;
		}
		//列车故障模式的checkbox
		i = 0;
		for (PairValue<Object, String> pairValue : breakdownList) {
			checkBreakdown[i] = new Button(groupBreakdown, SWT.RADIO);
			checkBreakdown[i].setText(pairValue.getValue().toString());
			checkBreakdown[i].setLayoutData(new GridData(GridData.FILL_BOTH));
			i++;
		}

		// 初始化界面，除正常模式外，其他内容全部灰掉
		for (i = 0; i < checkNormal.length; i++) {
			checkNormal[i].setEnabled(true);
		}
		checkNormal[0].setSelection(true);
		for (i = 0; i < checkDescend.length; i++) {
			checkDescend[i].setEnabled(false);
		}
		for (i = 0; i < checkEmergency.length; i++) {
			checkEmergency[i].setEnabled(false);
		}
		for (i = 0; i < checkBreakdown.length; i++) {
			checkBreakdown[i].setEnabled(false);
		}

	}

	public int[] getSelectModeCode() {
		int modeCode = AFCModeCode.NORMAL_MODE_CODE;
		int urgencyMode = -1;
		int[] arg = new int[2];
		boolean flag = false;
		if (groupNormal.isEnabled()) {
			for (int i = 0; i < checkNormal.length; i++) {
				if (checkNormal[i].getSelection()) {
					modeCode = Integer
							.valueOf(AFCModeCode.getInstance().getValueByName(checkNormal[i].getText()).toString());
					flag = true;
				}
			}
		} else if (groupDescend.isEnabled()) {
			//			String[] descendStr = new String[] { "0", "0", "0", "0", "0", "0" };
			// 判断组合是否存在、
			//无组合模式 chenfuchun 2018-03-25
			for (int i = 0; i < checkDescend.length; i++) {
				//				if (checkDescend[i].getSelection()) {
				//					descendStr[i] = "1";
				//					flag = true;
				//				}
				if (checkDescend[i].getSelection()) {
					modeCode = Integer
							.valueOf(AFCModeCode.getInstance().getValueByName(checkDescend[i].getText()).toString());
					flag = true;
				}
			}
			//			if (AFCModeCode.getInstance().getNameByValue(modeCode).equals("未知")) {
			//				modeCode = -2;
			//				arg[0] = modeCode;
			//				arg[1] = urgencyMode;
			//				return arg;
			//			}
		} else if (groupEmergency.isEnabled()) {
			for (int i = 0; i < checkEmergency.length; i++) {
				if (checkEmergency[i].getSelection()) {
					urgencyMode = Integer
							.valueOf(AFCModeCode.getInstance().getValueByName(checkEmergency[i].getText()).toString());
					flag = true;
				}
			}
		} else if (groupBreakdown.isEnabled()) {
			for (int i = 0; i < checkBreakdown.length; i++) {
				if (checkBreakdown[i].getSelection()) {
					modeCode = Integer
							.valueOf(AFCModeCode.getInstance().getValueByName(checkBreakdown[i].getText()).toString());
					flag = true;
				}
			}
		}
		if (!flag) {
			modeCode = -1;
		}
		arg[0] = modeCode;
		arg[1] = urgencyMode;
		return arg;
	}

	class SendModelAdapter extends CommandActionHandler {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void perform(ActionContext context) {

			List<MetroNode> ids = new ArrayList();
			// 只留下车站节点
			List<MetroNode> stationIds = new ArrayList<MetroNode>();

			//SC不需要要树，默认给全部子节点设备发送
			if (AFCApplication.getAFCNodeType().equals(AFCNodeLevel.SC)) {
				ids.add(AFCApplication.getAFCNode());
			} else {
				ids = (List) getSelections();
				for (MetroNode metroNode : ids) {
					if (metroNode instanceof MetroStation) {
						stationIds.add(metroNode);
					}
				}
			}

			int[] arg = getSelectModeCode();

			if (stationIds == null || stationIds.isEmpty()) {
				MessageForm.Message("提示信息", "请选择节点。", SWT.ICON_INFORMATION);
				return;
			}

			if (-1 == arg[0]) {
				MessageForm.Message("提示信息", "请选择具体模式。", SWT.ICON_INFORMATION);
				return;
			}

			if (-2 == arg[0]) {
				MessageForm.Message("提示信息", "该模式组合不存在。", SWT.ICON_INFORMATION);
				return;
			}
			if (!authority()) {
				return;
			}
			String name = "";

			//			name = AFCModeCode.getInstance().getNameByValue(arg[0]);

			for (PairValue<Object, String> pairValue : AFCModeCode.getInstance()
					.getByGroup(AFCModeCode.MODE_SIGN_NORMAL)) {
				if (pairValue.getKey().equals(arg[0])) {
					name = pairValue.getValue().toString();
				}
			}
			for (PairValue<Object, String> pairValue : AFCModeCode.getInstance()
					.getByGroup(AFCModeCode.MODE_SIGN_DESCEND)) {
				if (pairValue.getKey().equals(arg[0])) {
					name = pairValue.getValue().toString();
				}
			}
			for (PairValue<Object, String> pairValue : AFCModeCode.getInstance()
					.getByGroup(AFCModeCode.MODE_SIGN_URGENCY)) {
				if (pairValue.getKey().equals(arg[1])) {
					name = pairValue.getValue().toString();
				}
			}
			for (PairValue<Object, String> pairValue : AFCModeCode.getInstance()
					.getByGroup(AFCModeCode.MODE_SIGN_BREAKDOWN)) {
				if (pairValue.getKey().equals(arg[0])) {
					name = pairValue.getValue().toString();
				}
			}
			//因modecode与urgencyCode值冲突，故特殊处理
			//			if(-1!=arg[1]){
			//				name = SJZModeOrder.getInstance().getUrgencyNameByValue(arg[1]);
			//			}

			if (null == name || "".equals(name)) {
				name = "模式";
			}
			name += "切换命令";
			if (synLogService != null) {
				this.logService = synLogService;
			}
			send(context, CommandType.CMD_CHANGE_MODE, name, arg, stationIds, AFCCmdLogType.LOG_MODE.shortValue());

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
