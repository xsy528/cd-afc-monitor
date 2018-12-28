package com.insigma.afc.monitor.dialog.config;

import org.eclipse.swt.SWT;

import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.commons.application.Application;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.ui.MessageForm;

public class MonitorConfigAction extends Action {

	public class MonitorConfigActionHandler extends ConfigActionHandler {

		public void perform(ActionContext action) {
			final ConfigDialog<MonitorConfigInfo> dlg = new ConfigDialog<MonitorConfigInfo>(action.getFrameWork(),
					SWT.NONE);
			dlg.setText("监控设置");
			dlg.setTitle("监控设置");
			dlg.setSize(300, 400);
			dlg.setDescription("描述：配置监控参数信息");

			dlg.setValue(new MonitorConfigInfo());

			dlg.setActionHandler(new ActionHandlerAdapter() {
				public void perform(ActionContext context) {
					MonitorConfigInfo result = (MonitorConfigInfo) dlg.getValue();
					SystemConfigManager.setConfigItem(MonitorConfigInfo.ALARM_THRESHHOLD, result.getAlarm());
					SystemConfigManager.setConfigItem(MonitorConfigInfo.WARNING_THRESHHOLD, result.getWarning());
					SystemConfigManager.setConfigItem(MonitorConfigInfo.IS_AUTO_BROADCAST, result.getBroadcastmode());
					SystemConfigManager.setConfigItem(MonitorConfigInfo.VIEW_REFRESH_INTERVAL, result.getInterval());

					// 用新的配置参数更新全局变量值
					Application.setData(SystemConfigKey.VIEW_REFRESH_INTERVAL, result.getInterval());
					Application.setData(SystemConfigKey.ALARM_THRESHHOLD, result.getAlarm());
					Application.setData(SystemConfigKey.WARNING_THRESHHOLD, result.getWarning());
					Application.setData(SystemConfigKey.IS_AUTO_BROADCAST, result.getBroadcastmode());

					MessageForm.Message("提示信息", "参数设置成功。", SWT.ICON_INFORMATION);
				}
			});
			dlg.setRestoreHandler(new ActionHandlerAdapter() {
				public void perform(ActionContext action) {
					dlg.setValue(new MonitorConfigInfo());
				}
			});
			dlg.open();
		}
	}

	public MonitorConfigAction() {
		super("监控配置");
		setImage("/com/insigma/afc/monitor/images/toolbar/view-config.png");
		setHandler(new MonitorConfigActionHandler());
	}
}
