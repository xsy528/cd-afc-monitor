/* 
 * 日期：2011-7-16
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.handler;

import org.eclipse.swt.SWT;

import com.insigma.acc.wz.constant.WZApplicationKey;
import com.insigma.acc.wz.monitor.form.WZACCSectionFlowMonitorConfigForm;
import com.insigma.acc.wz.util.WZAppCommonUtil;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.commons.application.Application;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.IResponseCode;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.form.FormRequest;
import com.insigma.commons.service.ILogService;
import com.insigma.commons.ui.MessageForm;

/**
 * Ticket: 保存段面客流监控配置参数
 * 
 * @author Zhou-Xiaowei
 */
public class WZACCSectionFlowMonitorConfigActionHandler extends ActionHandlerAdapter {

	Form<WZACCSectionFlowMonitorConfigForm> form = new Form<WZACCSectionFlowMonitorConfigForm>(
			new WZACCSectionFlowMonitorConfigForm());

    private ILogService logService = null;

    public WZACCSectionFlowMonitorConfigActionHandler(ILogService logService) {
        this.logService = logService;
    }

    public Response perform(Request request) {
        Response response = new Response();
        FormRequest searchRequest = (FormRequest) request;

        form = searchRequest.getForm();

        String errorMsg = null;

        if (null == form) {
            response.addError("获取表单信息为空");
            return response;
        }

		if (form.getEntity() instanceof WZACCSectionFlowMonitorConfigForm) {
			WZACCSectionFlowMonitorConfigForm flowMonitorConfigForm = (WZACCSectionFlowMonitorConfigForm) form
					.getEntity();
            if (flowMonitorConfigForm.getWarning() <= 0) {
                MessageForm.Message("警告信息", "客流密度警告阀值要大于0。", SWT.ICON_WARNING);
                logService.doBizWarningLog("客流密度警告阀值要大于0");
                return response;
            }

            if (flowMonitorConfigForm.getAlarm() <= 0) {
                MessageForm.Message("警告信息", "客流密度报警阀值要大于0。", SWT.ICON_WARNING);
                logService.doBizWarningLog("客流密度报警阀值要大于0");
                return response;
            }

            if (flowMonitorConfigForm.getWarning() >= flowMonitorConfigForm.getAlarm()) {
                MessageForm.Message("警告信息", "客流密度警告阀值要小于客流密度报警阀值。", SWT.ICON_WARNING);
                logService.doBizWarningLog("客流密度警告阀值要小于客流密度报警阀值");
                return response;
            }

			errorMsg = saveSectionFlowMonitorConfigInfo(flowMonitorConfigForm);
        }

        String showMsg = null;
        if (errorMsg == null) {
            showMsg = "参数配置成功";
            logService.doBizLog(showMsg);
            response.setCode(IResponseCode.DIALOG_CLOSE);
            response.addInformation(showMsg);
        } else {
            showMsg = errorMsg;
            response.addError(errorMsg);
        }

        return response;
    }

	private String saveSectionFlowMonitorConfigInfo(WZACCSectionFlowMonitorConfigForm form) {
        String errorMsg = null;

		SystemConfigManager.setConfigItem(WZApplicationKey.SectionPassengerflowLow,
                form.getWarning());
		SystemConfigManager.setConfigItem(WZApplicationKey.SectionPassengerflowHigh,
                form.getAlarm());

        // 用新的配置参数更新全局变量值
		Application.setData(WZApplicationKey.SectionPassengerflowLow, form.getWarning());
		Application.setData(WZApplicationKey.SectionPassengerflowHigh, form.getAlarm());

        String saveMsg = "保存成功数据：\n";
		saveMsg += "报警阈值[" + Application.getData(WZApplicationKey.SectionPassengerflowLow) + "]";
		saveMsg += "警告阈值[" + Application.getData(WZApplicationKey.SectionPassengerflowHigh) + "]";
        logger.info(saveMsg);
		WZAppCommonUtil.redPrint(saveMsg);

        return errorMsg;
    }

}
