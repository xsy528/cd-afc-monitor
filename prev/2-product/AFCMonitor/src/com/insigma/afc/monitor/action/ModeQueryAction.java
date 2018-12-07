package com.insigma.afc.monitor.action;

import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.ICommandFactory.Command;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.dic.ModuleCode;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.service.ILogService;

@SuppressWarnings("unchecked")
public class ModeQueryAction extends NodeAction {
	private ILogService logSysService;

	private Command activeCommand;

	private int logModuleCode = ModuleCode.MODULE_OTHER;

	public class ModeQueryHandler extends ActionHandlerAdapter {
		@Override
		public void perform(ActionContext context) {
			ModeQueryDialog dlg = new ModeQueryDialog(context.getFrameWork(), SWT.NONE);
			dlg.setNodeClass(MetroStation.class);
			dlg.setTreeProvider(getTreeProvider());
			dlg.setTreeDepth(getTreeDepth());
			dlg.setActiveCommand(activeCommand);
			dlg.setSelections((List) getNodes());
			if (logSysService != null) {
				dlg.setSynLogService(logSysService);
			}
			dlg.open();
		}
	}

	public ModeQueryAction() {
		setName("模式查询");
		setImage("/com/insigma/afc/ui/monitor/search.png");
		setTargetType(null);
		setHandler(new ModeQueryHandler());
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
	 * @return the logModuleCode
	 */
	public int getLogModuleCode() {
		return logModuleCode;
	}

	/**
	 * @param logModuleCode
	 *            the logModuleCode to set
	 */
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
