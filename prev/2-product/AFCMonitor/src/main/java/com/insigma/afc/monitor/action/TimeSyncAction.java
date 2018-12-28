package com.insigma.afc.monitor.action;

import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.service.ILogService;

@SuppressWarnings("unchecked")
public class TimeSyncAction extends NodeAction {
	private ILogService logSysService;

	public class TimeActionHandler extends ActionHandlerAdapter {
		@Override
		public void perform(ActionContext context) {
			TimeSyncDialog dlg = new TimeSyncDialog(context.getFrameWork(), SWT.NONE);
			dlg.setNodeClass(MetroStation.class);
			dlg.setTreeProvider(getTreeProvider());
			dlg.setTreeDepth(getTreeDepth());
			dlg.setSelections((List) getNodes());

			if (logSysService != null) {
				dlg.setSynLogService(logSysService);
			}
			dlg.open();
		}
	}

	public TimeSyncAction() {
		setName("时钟同步");
		setImage("/com/insigma/afc/ui/monitor/clocksync.png");
		setTargetType(null);
		setHandler(new TimeActionHandler());
	}

	public ILogService getLogSysService() {
		return logSysService;
	}

	public void setLogSysService(ILogService logSysService) {
		this.logSysService = logSysService;
	}
}
