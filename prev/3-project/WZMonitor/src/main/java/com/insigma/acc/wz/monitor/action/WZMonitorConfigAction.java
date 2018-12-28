package com.insigma.acc.wz.monitor.action;

import org.eclipse.swt.SWT;

import com.insigma.acc.wz.monitor.form.WZMonitorConfigInfo;
import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.afc.monitor.dialog.config.ConfigActionHandler;
import com.insigma.afc.monitor.dialog.config.ConfigDialog;
import com.insigma.afc.monitor.dialog.config.MonitorConfigInfo;
import com.insigma.commons.application.Application;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;

public class WZMonitorConfigAction extends Action {

	private ILogService logSysService;

	public class MonitorConfigActionHandler extends ConfigActionHandler {

		public void perform(ActionContext action) {
			final ConfigDialog<WZMonitorConfigInfo> dlg = new ConfigDialog<WZMonitorConfigInfo>(action.getFrameWork(),
					SWT.NONE);
			dlg.setText("监控设置");
			dlg.setTitle("监控设置");
			dlg.setSize(300, 400);
			dlg.setDescription("描述：配置监控参数信息");

			dlg.setValue(new WZMonitorConfigInfo());

			dlg.setActionHandler(new ActionHandlerAdapter() {
				public void perform(ActionContext context) {
					WZMonitorConfigInfo result = (WZMonitorConfigInfo) dlg.getValue();

					if (result.getAlarm() <= result.getWarning()) {
						MessageForm.Message("提示信息", "报警阀值不得低于警告阀值，参数设置失败。", SWT.ICON_WARNING);
					} else if (result.getInterval() < 5) {
						MessageForm.Message("提示信息", "刷新周期不能小于5秒。", SWT.ICON_WARNING);
					} else {

						try {
							SystemConfigManager.setConfigItem(MonitorConfigInfo.ALARM_THRESHHOLD, result.getAlarm());
							SystemConfigManager.setConfigItem(MonitorConfigInfo.WARNING_THRESHHOLD,
									result.getWarning());
							SystemConfigManager.setConfigItem(MonitorConfigInfo.VIEW_REFRESH_INTERVAL,
									result.getInterval());

						} catch (ApplicationException e) {
							logger.error("保存参数设置失败");
							logSysService.doBizLog("监控参数设置失败。");
							MessageForm.Message("提示信息", "监控参数设置失败。", SWT.ICON_ERROR);
							dlg.close();
							return;
						}

						// 用新的配置参数更新全局变量值
						Application.setData(SystemConfigKey.VIEW_REFRESH_INTERVAL, result.getInterval());
						Application.setData(SystemConfigKey.ALARM_THRESHHOLD, result.getAlarm());
						Application.setData(SystemConfigKey.WARNING_THRESHHOLD, result.getWarning());

						MessageForm.Message("提示信息", "监控参数设置成功。", SWT.ICON_INFORMATION);
						logSysService.doBizLog("监控参数设置成功。");
					}
				}
			});
			dlg.setRestoreHandler(new ActionHandlerAdapter() {
				public void perform(ActionContext action) {
					dlg.setValue(new WZMonitorConfigInfo());
				}
			});
			dlg.open();
		}
	}

	public WZMonitorConfigAction() {
		super("监控配置");
		setImage("/com/insigma/afc/monitor/images/toolbar/view-config.png");
		setHandler(new MonitorConfigActionHandler());
	}

	public ILogService getLogSysService() {
		return logSysService;
	}

	public void setLogSysService(ILogService logSysService) {
		this.logSysService = logSysService;
	}

}
