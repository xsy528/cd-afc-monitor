package com.insigma.afc.monitor.dialog.sle;

import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.ICommandFactory;
import com.insigma.afc.monitor.action.NodeAction;
import com.insigma.commons.dic.ModuleCode;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.spring.Autowire;

public class CommandFactoryAction extends NodeAction {
	private ILogService logSysService;

	private int logModuleCode = ModuleCode.MODULE_OTHER;

	public class CommandHandler extends ActionHandlerAdapter {

		@Autowire
		private ICommandFactory commandFactory;

		@SuppressWarnings("unchecked")
		@Override
		public void perform(final ActionContext context) {
			logger.info("节点树是否为空 ？ " + treeProvider);
			CommandFactoryDialog dlg = new CommandFactoryDialog(context.getFrameWork(), SWT.BAR);
			try {
				dlg.setTreeProvider(treeProvider);
				dlg.setCommandFactory(commandFactory);
				dlg.setSelections((List) getNodes());
				if (logSysService != null) {
					dlg.setSynLogService(logSysService);
				}
			} catch (Exception e) {
				logger.error("执行[发送命令]", e);
			}
			dlg.open();
		}
	}

	public CommandFactoryAction() {
		setName("发送命令");
		setSelectionType(SelectionType.MULTI);
		setImage("/com/insigma/afc/monitor/images/toolbar/send-command.png");
		setHandler(new CommandHandler());
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
