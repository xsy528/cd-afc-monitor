/* 
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.monitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.acc.wz.module.WZBaseLogModule;
import com.insigma.acc.wz.module.code.WZACCLogModuleCode;
import com.insigma.acc.wz.monitor.module.ACCDeviceSearchModule;
import com.insigma.acc.wz.monitor.module.WZACCSectionFlowMonitorModule;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.map.GraphicMapGenerator;
import com.insigma.afc.monitor.map.builder.EditGraphicMapBuilder;
import com.insigma.afc.view.DefaultPlatFormBuilder;
import com.insigma.afc.view.action.LogoutAction;
import com.insigma.afc.view.provider.CommonTreeProvider;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.application.UIPlatForm;
import com.insigma.commons.service.ILogService;

/**
 * 
 * Ticket: 监控管理
 * @author  hexingyue
 *
 */
public class MonitorPlatFormBuilder extends DefaultPlatFormBuilder {

	Log logger = LogFactory.getLog(MonitorPlatFormBuilder.class);

	public List<Module> getFunction() {

		List<Module> moduleList = new ArrayList<Module>();

		{
			//路网监控
			MonitorLineModule moduleLineModule = new MonitorLineModule();
			//日志处理
			WZBaseLogModule logModule = new WZBaseLogModule();
			ILogService logSysService = logModule.getLogService(WZACCLogModuleCode.MODULE_MONITOR + "");
			moduleLineModule.setLogSysService(logSysService);

			Module module = moduleLineModule.getModule();
			module.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR.toString());
			moduleList.add(module);

		}

		{
			//客流监控
			ACCODMonitorModule odModule = new ACCODMonitorModule();

			CommonTreeProvider commonTreeProvider = new CommonTreeProvider();
			commonTreeProvider.setDepth(AFCNodeLevel.SC);

			odModule.setCommonTreeProvider(commonTreeProvider);
			Module module = odModule.getModule();
			module.setFunctionID(WZACCLogModuleCode.MODULE_OD_MONITOR.toString());

			moduleList.add(module);
		}

		{
			//断面客流
			WZACCSectionFlowMonitorModule odModule = new WZACCSectionFlowMonitorModule();
			Module module = odModule.getModule();
			//			module.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_SECTION_PASSAGER.toString());
			moduleList.add(module);
		}

		{
			// 节点管理
			GraphicMapGenerator graphicMapGenerator = new GraphicMapGenerator();
			graphicMapGenerator.setEditUI(true);
			graphicMapGenerator.setGraphicMapBuilder(new EditGraphicMapBuilder());
			//
			ACCMapEditorModule mapEditorModule = new ACCMapEditorModule();
			mapEditorModule.setGraphicMapGenerator(graphicMapGenerator);
			// 赋予权限
			Module module = mapEditorModule.getModule();
			module.setFunctionID(WZACCLogModuleCode.MODULE_MAP_EDITOR.toString());
			moduleList.add(module);
		}

		//		{
		//			// 主机监控
		//			NBMonitorHostMoudle module = new NBMonitorHostMoudle();
		//			Module module2 = module.getModule();
		//			module2.setFunctionID(NBLCLogModuleCode.MODULE_MONITOR_MANAGE_SYSTEM.toString());
		//			moduleList.add(module2);
		//
		//		}

		{
			// 各类查询
			ACCDeviceSearchModule scDeviceSearchModule = new ACCDeviceSearchModule();
			Module module = scDeviceSearchModule.getModule();
			module.setFunctionID(WZACCLogModuleCode.MODULE_DEVICE_SEARCH.toString());
			moduleList.add(module);
		}

		return moduleList;
	}

	/**
	 * 取得状态栏信息
	 */
	@Override
	public UIPlatForm getPlatForm() {
		UIPlatForm platForm = new UIPlatForm();
		platForm.setName("监控管理");
		platForm.setId(WZACCLogModuleCode.MODULE_MONITOR_MANAGE.toString());
		platForm.setTitle(AFCApplication.getApplicationName() + "-监控管理-" + AFCApplication.getVersion(this.getClass()));
		platForm.setIcon("/com/insigma/afc/images/logo.png");
		platForm.setImage("/com/insigma/afc/images/Monitor.png");

		getStatus(platForm);

		// UPS
		//		platForm.addStatusPage(new UPSStatusPage());

		platForm.setModules(getFunction());

		platForm.addAction(new LogoutAction());


		return platForm;
	}
}
