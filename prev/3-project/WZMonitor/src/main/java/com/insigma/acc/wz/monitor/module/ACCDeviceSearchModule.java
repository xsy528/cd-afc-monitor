/* 
 * 日期：2017年6月13日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.module;

import java.util.ArrayList;

import com.insigma.acc.wz.module.WZBaseLogModule;
import com.insigma.acc.wz.module.code.WZACCLogModuleCode;
import com.insigma.acc.wz.monitor.form.WZCommandLogSearchForm;
import com.insigma.acc.wz.monitor.form.WZModeUploadForm;
import com.insigma.acc.wz.monitor.handler.CommandLogSearchActionHandler;
import com.insigma.acc.wz.monitor.handler.DeviceEventSearchActionHandler;
import com.insigma.acc.wz.monitor.handler.ModeBroadCastInfoRetransimmitActionHandler;
import com.insigma.acc.wz.monitor.handler.ModeBroadcastInfoValidationActionHandler;
import com.insigma.acc.wz.monitor.handler.WZModeCmdSearchActionHandler;
import com.insigma.acc.wz.monitor.handler.WZModeUploadSearchActionHandler;
import com.insigma.acc.wz.provider.WZDeviceEventTreeProvider;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.search.DeviceEventSearchForm;
import com.insigma.afc.monitor.search.ModeBroadCastInfoForm;
import com.insigma.afc.monitor.search.ModeBroadCastInfoSearchActionHandler;
import com.insigma.afc.monitor.search.ModeCmdSearchForm;
import com.insigma.afc.view.provider.CommonTreeProvider;
import com.insigma.commons.application.Application;
import com.insigma.commons.config.NetworkConfig;
import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.action.ResetAction;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.config.IModuleBuilder;
import com.insigma.commons.framework.function.search.TreeSearchFunction;
import com.insigma.commons.service.ILogService;

/**
 * Ticket: 各类查询模块
 * @author  mengyifan
 *
 */
public class ACCDeviceSearchModule extends WZBaseLogModule implements IModuleBuilder {

	private Short[] lineIds;

	private WZDeviceEventTreeProvider treeProvider;

	protected ILogService logService = null;

	public ACCDeviceSearchModule() {
		// 设置指定线路
		NetworkConfig networkConfig = (NetworkConfig) Application.getConfig(NetworkConfig.class);
		lineIds = new Short[] { networkConfig.getLineNo().shortValue() };

		treeProvider = new WZDeviceEventTreeProvider();
		treeProvider.setRootId(1);
		treeProvider.setDepth(3);
		treeProvider.setLineIDs(lineIds);
	}

	public Module getModule() {

		Module group = new Module();
		group.setText("   各类查询   ");
		logService = getLogService(WZACCLogModuleCode.MODULE_DEVICE_SEARCH.toString());
		ArrayList<Function> functions = new ArrayList<Function>();

		{
			TreeSearchFunction modeUploadSearchFunction = new TreeSearchFunction();
			modeUploadSearchFunction
					.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_QUERY_MODE_UPLOAD.toString());

			modeUploadSearchFunction.setTitle("模式上传");
			modeUploadSearchFunction.setForm(new WZModeUploadForm());
			CommonTreeProvider treeProvider = new CommonTreeProvider();
			treeProvider.setDepth(AFCNodeLevel.SC);
			modeUploadSearchFunction.setTreeProvider(treeProvider);
			modeUploadSearchFunction.setImage("/com/insigma/afc/images/search.png");
			modeUploadSearchFunction.setColumns(new String[] { "序号", "线路/编号", "车站/编号", "进入的模式/编号", "模式发生时间" });
			modeUploadSearchFunction.setColumnWidth(new int[] { 50, 150, 150, 200, 150 });
			modeUploadSearchFunction.setbShowCurrentPageExportButton(false);
			functions.add(modeUploadSearchFunction);

			Action sAction = new Action();
			sAction.setShowProgress(true);
			WZModeUploadSearchActionHandler modeUploadSearchActionHandler = new WZModeUploadSearchActionHandler();
			sAction.setText("查询");
			sAction.setActionHandler(modeUploadSearchActionHandler);
			modeUploadSearchFunction.addAction(sAction);

			Action pAction = new Action();
			pAction.setText("翻页");
			pAction.setActionHandler(modeUploadSearchActionHandler);
			modeUploadSearchFunction.setPageChangedAction(pAction);

			modeUploadSearchFunction.addAction(new ResetAction());
			modeUploadSearchFunction.setDefault(true);
		}

		{

			TreeSearchFunction func = new TreeSearchFunction();
			func.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_QUERY_MODE_BROADCAST.toString());
			func.setTitle("模式广播");

			ModeBroadCastInfoForm form = new ModeBroadCastInfoForm();
			//					form.labelWidth = 120;
			func.setForm(form);
			CommonTreeProvider treeProvider = new CommonTreeProvider();
			treeProvider.setDepth(AFCNodeLevel.SC);
			func.setTreeProvider(treeProvider);
			func.setImage("/com/insigma/afc/images/search.png");
			func.setColumnWidth(new int[] { 50, 130, 130, 150, 150, 150, 100, 100, 150, 150 });
			func.setColumns(new String[] { "序号", "模式发生源线路/编号", "模式发生源车站/编号", "进入的模式名称/编号", "模式发生时间", "模式广播时间", "模式广播方式",
					"模式广播状态", "模式广播目的车站/编号", "操作员姓名/编号" });
			func.setbShowCurrentPageExportButton(false);
			functions.add(func);

			Action searchAction = new Action();
			searchAction.setShowProgress(true);
			ModeBroadCastInfoSearchActionHandler modeBroadCastInfoHandler = new ModeBroadCastInfoSearchActionHandler();
			searchAction.setText("查询");
			searchAction.setActionHandler(modeBroadCastInfoHandler);
			func.addAction(searchAction);

			Action pAction = new Action();
			pAction.setText("翻页");
			pAction.setActionHandler(modeBroadCastInfoHandler);

			func.setPageChangedAction(pAction);

			Action retryAction = new Action();
			ModeBroadCastInfoRetransimmitActionHandler modeBroadCastInfoRetransimmitHandler = new ModeBroadCastInfoRetransimmitActionHandler(
					logService);
			retryAction.setText("重发(自动)");
			retryAction.setActionHandler(modeBroadCastInfoRetransimmitHandler);
			func.addAction(retryAction);

			Action confirmAction = new Action();
			ModeBroadcastInfoValidationActionHandler modeBroadcastInfoValidationHandler = new ModeBroadcastInfoValidationActionHandler(
					logService);
			confirmAction.setText("确认(手动)");
			confirmAction.setActionHandler(modeBroadcastInfoValidationHandler);
			func.addAction(confirmAction);

			func.addAction(new ResetAction());
		}

		{
			TreeSearchFunction function = new TreeSearchFunction();
			function.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_QUERY_MODE_LOG.toString());

			function.setTitle("模式日志");
			function.setForm(new ModeCmdSearchForm());
			CommonTreeProvider treeProvider = new CommonTreeProvider();
			treeProvider.setDepth(AFCNodeLevel.SC);
			function.setTreeProvider(treeProvider);
			function.setImage("/com/insigma/afc/images/search.png");
			function.setColumns(new String[] { "序号", "发送时间", "操作员姓名/编号", "车站/编号", "模式名称", "发送结果/编号" });
			int[] columnWidth = new int[] { 50, 150, 150, 150, 220, 150 };
			function.setColumnWidth(columnWidth);

			function.setbShowCurrentPageExportButton(false);
			functions.add(function);

			Action sAction = new Action();
			sAction.setShowProgress(true);
			WZModeCmdSearchActionHandler handler = new WZModeCmdSearchActionHandler();
			sAction.setText("查询");
			sAction.setActionHandler(handler);
			function.addAction(sAction);

			Action pAction = new Action();
			pAction.setText("翻页");
			pAction.setActionHandler(handler);
			function.setPageChangedAction(pAction);

			function.addAction(new ResetAction());
		}

		{
			TreeSearchFunction function = new TreeSearchFunction();
			function.setNavigatable(true);
			function.setTreeDepth(5);
			function.setTitle("命令日志");
			function.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_DEVICE_COMMAND.toString());
			function.setForm(new WZCommandLogSearchForm());
			CommonTreeProvider treeProvider = new CommonTreeProvider();
			treeProvider.setDepth(AFCNodeLevel.SLE);
			function.setTreeProvider(treeProvider);
			function.setImage("/com/insigma/afc/images/search.png");
			function.setColumns(new String[] { "节点名称/节点编码", "命令名称", "操作员名称/编号", "发送时间", "命令结果/应答码" });
			int[] columnWidth = new int[] { 200, 220, 150, 150, 250 };
			function.setColumnWidth(columnWidth);

			function.setbShowCurrentPageExportButton(false);
			functions.add(function);

			Action cmdlogAction = new Action();
			CommandLogSearchActionHandler commandLogSearchActionHandler = new CommandLogSearchActionHandler();
			cmdlogAction.setText("查询");
			cmdlogAction.setActionHandler(commandLogSearchActionHandler);
			function.addAction(cmdlogAction);

			Action pCmdLogAction = new Action();
			pCmdLogAction.setText("翻页");
			pCmdLogAction.setActionHandler(commandLogSearchActionHandler);
			function.setPageChangedAction(pCmdLogAction);
			function.addAction(new ResetAction());
		}

		{
			TreeSearchFunction function = new TreeSearchFunction();
			function.setTitle("设备事件");
			function.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_DEVICE_ENEVT.toString());
			function.setForm(new DeviceEventSearchForm());
			CommonTreeProvider treeProvider = new CommonTreeProvider();
			treeProvider.setDepth(AFCNodeLevel.SLE);
			function.setTreeProvider(treeProvider);
			function.setImage("/com/insigma/afc/images/search.png");
			function.setColumns(new String[] { "节点名称/节点编码", "事件名称/编号", "事件描述", "发生时间" });
			int[] columnWidth = new int[] { 200, 300, 200, 200 };
			function.setColumnWidth(columnWidth);

			function.setbShowCurrentPageExportButton(false);
			functions.add(function);

			Action deviceEventAction = new Action();
			DeviceEventSearchActionHandler eventSearchHandler = new DeviceEventSearchActionHandler();
			deviceEventAction.setText("查询");
			deviceEventAction.setShowProgress(true);
			deviceEventAction.setActionHandler(eventSearchHandler);
			function.addAction(deviceEventAction);

			Action pAction = new Action();
			pAction.setText("翻页");
			pAction.setActionHandler(eventSearchHandler);
			function.setPageChangedAction(pAction);
			function.addAction(new ResetAction());
		}

		group.setFunction(functions);

		return group;
	}
}
