package com.insigma.afc.odmonitor.odwarning;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.dialog.config.ConfigDialog;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;

public class PassengerFlowConfigAction extends Action {

	public class MonitorConfigActionHandler extends ActionHandlerAdapter {

		@Override
		public void perform(ActionContext action) {
			final ConfigDialog<PassengerFlowConfigForm> dlg = new ConfigDialog<PassengerFlowConfigForm>(
					action.getFrameWork(), SWT.NONE);
			dlg.setTitle("监控设置");
			dlg.setSize(300, 400);
			dlg.setDescription("监控设置");

			PassengerFlowConfigForm form = new PassengerFlowConfigForm();

			dlg.setValue(form);
			dlg.setActionHandler(new ActionHandlerAdapter() {
				public void perform(ActionContext action) {

				}
			});
			dlg.setRestoreHandler(new ActionHandlerAdapter() {
				@Override
				public void perform(ActionContext action) {
					dlg.setValue(new PassengerFlowConfigForm());
				}
			});
			dlg.open();
		}
	}

	public PassengerFlowConfigAction() {
		super("监控配置");
		setImage("/com/insigma/afc/monitor/images/toolbar/equGroup.png");
		setHandler(new MonitorConfigActionHandler());
	}
}
