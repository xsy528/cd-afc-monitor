/*
 * 日期：2010-8-19 上午09:08:35
 * 描述：预留
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.monitor.module;

import java.util.ArrayList;

import com.insigma.acc.wz.constant.WZApplicationKey;
import com.insigma.acc.wz.module.WZBaseLogModule;
import com.insigma.acc.wz.module.code.WZACCLogModuleCode;
import com.insigma.acc.wz.monitor.form.WZACCSectionFlowMonitorConfigForm;
import com.insigma.acc.wz.monitor.handler.WZACCSectionFlowMonitorConfigActionHandler;
import com.insigma.acc.wz.monitor.handler.WZRefreshMapActionHandler;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.afc.odmonitor.form.SectionPassengerFlowForm;
import com.insigma.afc.odmonitor.handler.SectionODSearchActionHandler;
import com.insigma.afc.view.provider.CommonTreeProvider;
import com.insigma.commons.editorframework.iframework.MapFunction;
import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.action.CloseAction;
import com.insigma.commons.framework.action.ResetAction;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.config.IModuleBuilder;
import com.insigma.commons.framework.function.dialog.StandardDialogFunction;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.form.FormFunction;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.TreeSearchFunction;

public class WZACCSectionFlowMonitorModule extends WZBaseLogModule implements IModuleBuilder {

	public Module getModule() {
		Module group = new Module();
		group.setText("   断面客流   ");
		group.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_SECTION_PASSAGER.toString());
		ArrayList<Function> functions = new ArrayList<Function>();

		getLogService(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_SECTION_PASSAGER.toString());

		CommonTreeProvider commonTreeProvider = new CommonTreeProvider();
		commonTreeProvider.setDepth(AFCNodeLevel.SC);
		{
			CommonTreeProvider comTreeProvider = new CommonTreeProvider();
			MapFunction func = new MapFunction();
			func.setTitle("断面客流监控图");
			func.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_SECTION_PASSAGER_MAP.toString());
			comTreeProvider.setDepth(AFCNodeLevel.LC);
			func.setTreeProvider(comTreeProvider);
			func.setForm(new SectionPassengerFlowForm());

			func.setTreeLabel("网络拓扑结构");
			func.setDefault(true);

			Action action = new Action("刷 新");
			WZRefreshMapActionHandler odHandler = new WZRefreshMapActionHandler();
			action.setActionHandler(odHandler);
			action.setShowProgress(false);

			func.addAction(action);
			func.setOpenAction(action);
			func.setRefreshAction(action);
			func.setRefreshInterval(10);

			func.setImage("/com/insigma/afc/odmonitor/images/section1.png");
			functions.add(func);
		}

		{
			CommonTreeProvider comTreeProvider = new CommonTreeProvider();
			TreeSearchFunction function = new TreeSearchFunction();
			function.setPageSize(40);
			function.setTitle("断面客流查询");
			function.setForm(new SectionPassengerFlowForm());
			comTreeProvider.setDepth(AFCNodeLevel.LC);
			function.setTreeProvider(comTreeProvider);
			function.setImage("/com/insigma/afc/odmonitor/images/section2.png");
			function.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_SECTION_PASSAGER_TABLE.toString());
			function.setColumns(new String[] { "序号", "运营日期 ", "线路/编号", "车站1/编号", "车站2/编号", "上行客流", "下行客流", "总客流" });
			function.setColumnWidth(new int[] { 100, 160, 160, 160, 100, 100, 100, 100 });

			Action odAction = new Action("查询");
			SectionODSearchActionHandler handler = new SectionODSearchActionHandler();
			odAction.setActionHandler(handler);
			function.addAction(odAction);
			function.addAction(new ResetAction());

			Action pAction = new Action();
			pAction.setText("翻页");
			pAction.setActionHandler(handler);
			function.setPageChangedAction(pAction);

			functions.add(function);
		}
		{
			FormFunction func = new FormFunction();
			func.setTitle("断面客流配置");
			func.setImage("/com/insigma/afc/monitor/images/toolbar/view-config.png");

			Form<WZACCSectionFlowMonitorConfigForm> form = new Form<WZACCSectionFlowMonitorConfigForm>(
					new WZACCSectionFlowMonitorConfigForm());
			form.labelWidth = 165;
			form.setColums(1);
			func.setForm(form);

			StandardDialogFunction function = new StandardDialogFunction(func);
			function.setText("断面客流配置");
			function.setTitle("断面客流配置");
			function.setDescription("断面客流配置");
			function.setImage("/com/insigma/afc/monitor/images/toolbar/view-config.png");
			function.setHeight(400);
			function.setWidth(300);

			function.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_SECTION_PASSAGER_CONFIG.toString());

			function.setOpenAction(new Action("打开", new ActionHandlerAdapter() {
				public Response perform(Request request) {
					Response response = new Response();
					SearchRequest req = (SearchRequest) request;
					StandardDialogFunction fun = (StandardDialogFunction) req.getAction().getFunction();
					FormFunction fun2 = (FormFunction) fun.getFunction();
					WZACCSectionFlowMonitorConfigForm form = (WZACCSectionFlowMonitorConfigForm) fun2.getForm()
							.getEntity();
					form.setWarning(SystemConfigManager.getConfigItemForInt(WZApplicationKey.SectionPassengerflowLow));
					form.setAlarm(SystemConfigManager.getConfigItemForInt(WZApplicationKey.SectionPassengerflowHigh));
					response.setValue(form);
					return response;
				}
			}));

			Action saveAction = new Action("保存 (&S)");
			saveAction.setActionHandler(new WZACCSectionFlowMonitorConfigActionHandler(logService));
			function.addAction(saveAction);
			function.addAction(new CloseAction());
			functions.add(function);
		}

		group.setFunction(functions);

		return group;
	}
}
