package com.insigma.acc.wz.monitor.action;

import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.acc.wz.monitor.dialog.WZModeCommandDialog;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.action.NodeAction;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.dic.ModuleCode;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.service.ILogService;

@SuppressWarnings("unchecked")
public class WZModeCommandAction extends NodeAction {
	private ILogService logSysService;

	private int logModuleCode = ModuleCode.MODULE_OTHER;

	public class ModeActionHandler extends ActionHandlerAdapter {
		@Override
		public void perform(ActionContext context) {
			WZModeCommandDialog dlg = new WZModeCommandDialog(context.getFrameWork(), SWT.NONE);
			AFCNodeLevel afcNodeType = AFCApplication.getAFCNodeType();
			Class type = MetroLine.class;
			if (afcNodeType == AFCNodeLevel.ACC) {
				type = MetroStation.class;
				dlg.setTreeDepth(getTreeDepth());
				dlg.setTreeProvider(getTreeProvider());
			}
			//			else if (afcNodeType == AFCNodeLevel.SC) {
			//				type = MetroDevice.class;
			//			}
			dlg.setNodeClass(type);
			//			dlg.setTreeDepth(getTreeDepth());
			//			dlg.setTreeProvider(getTreeProvider());
			dlg.setSelections((List) getNodes());
			try {
				if (logSysService != null) {
					dlg.setSynLogService(logSysService);
				}
			} catch (Exception e) {
				logger.error("执行[运营模式]", e);
			}
			dlg.open();
		}
	}

	public WZModeCommandAction() {
		setName("运营模式");
		setImage("/com/insigma/afc/monitor/images/toolbar/send-mode.png");
		setTargetType(null);
		setHandler(new ModeActionHandler());
	}

	public void setLogModuleCode(int logModuleCode) {
		this.logModuleCode = logModuleCode;
	}

	public ILogService getLogSysService() {
		return logSysService;
	}

	public void setLogSysService(ILogService logSysService) {
		this.logSysService = logSysService;
	}
}
