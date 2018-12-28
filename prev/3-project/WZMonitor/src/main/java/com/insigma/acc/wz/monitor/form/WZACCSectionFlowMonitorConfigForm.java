package com.insigma.acc.wz.monitor.form;

import com.insigma.acc.wz.constant.WZApplicationKey;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.commons.ui.anotation.View;

public class WZACCSectionFlowMonitorConfigForm {

    @View(label = "客流密度警告阀值[人次/分钟]", type = "Spinner")
    private Integer warning = 20000;

    @View(label = "客流密度报警阀值[人次/分钟]", type = "Spinner")
    private Integer alarm = 40000;

    public WZACCSectionFlowMonitorConfigForm() {
		warning = SystemConfigManager.getConfigItemForInt(WZApplicationKey.SectionPassengerflowLow);
		alarm = SystemConfigManager.getConfigItemForInt(WZApplicationKey.SectionPassengerflowHigh);
    }

    public Integer getWarning() {
        return warning;
    }

    public void setWarning(Integer warning) {
        this.warning = warning;
    }

    public Integer getAlarm() {
        return alarm;
    }

    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
    }
}
