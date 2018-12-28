/*
 * 日期：2010-11-30
 * 描述：预留
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */

package com.insigma.acc.wz.monitor;

import java.util.ArrayList;

import com.insigma.acc.wz.module.WZBaseLogModule;
import com.insigma.acc.wz.module.code.WZACCLogModuleCode;
import com.insigma.afc.odmonitor.form.BarForm;
import com.insigma.afc.odmonitor.form.ConditionForm;
import com.insigma.afc.odmonitor.form.PieForm;
import com.insigma.afc.odmonitor.form.SeriesForm;
import com.insigma.afc.odmonitor.handler.BarActionHandler;
import com.insigma.afc.odmonitor.handler.ODSearchActionHandler;
import com.insigma.afc.odmonitor.handler.PieActionHandler;
import com.insigma.afc.odmonitor.handler.SeriesActionHandler;
import com.insigma.afc.view.provider.CommonTreeProvider;
import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.action.ResetAction;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.config.IModuleBuilder;
import com.insigma.commons.framework.function.chart.ChartFunction;
import com.insigma.commons.framework.function.chart.ChartFunction.ChartType;
import com.insigma.commons.framework.function.search.TreeSearchFunction;

/**
 * 
 * Ticket: 监控管理->客流监控
 * @author  hexingyue
 *
 */
public class ACCODMonitorModule extends WZBaseLogModule implements IModuleBuilder {

	private CommonTreeProvider commonTreeProvider = null;

	/**
	 * @return the commonTreeProvider
	 */
	public CommonTreeProvider getCommonTreeProvider() {
		return commonTreeProvider;
	}

	/**
	 * @param commonTreeProvider the commonTreeProvider to set
	 */
	public void setCommonTreeProvider(CommonTreeProvider commonTreeProvider) {
		this.commonTreeProvider = commonTreeProvider;
	}

	@Override
	public Module getModule() {
		Module group = new Module();
		group.setText(" 客流监控   ");
		ArrayList<Function> functions = new ArrayList<Function>();
		getLogService(WZACCLogModuleCode.MODULE_OD_MONITOR.toString());

		{
			ChartFunction func = new ChartFunction();
			func.setTitle("柱状图");
			func.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_PASSAGER_BAR.toString());
			func.setTreeProvider(commonTreeProvider);
			func.setForm(new BarForm());
			func.setChartType(ChartType.BAR);
			func.setChartTitle("");

			Action action = new Action("刷 新");
			action.setShowProgress(true);

			BarActionHandler odHandler = new BarActionHandler();
			action.setActionHandler(odHandler);
			odHandler.setMaxCountOfStation(Integer.MAX_VALUE);

			func.addAction(action);
			func.setImage("/com/insigma/afc/odmonitor/images/bar.png");
			functions.add(func);
			func.setDefault(true);
		}

		{
			ChartFunction func = new ChartFunction();
			func.setTitle("饼 图");
			func.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_PASSAGER_PIE.toString());
			func.setTreeProvider(commonTreeProvider);
			func.setForm(new PieForm());
			func.setChartType(ChartType.PIE);
			func.setChartTitle("");

			Action action = new Action("刷 新");
			action.setShowProgress(true);
			PieActionHandler odHandler = new PieActionHandler();
			action.setActionHandler(odHandler);
			odHandler.setMaxCountOfStation(Integer.MAX_VALUE);

			func.addAction(action);

			func.setImage("/com/insigma/afc/odmonitor/images/pie.png");
			functions.add(func);
		}

		{
			ChartFunction func = new ChartFunction();
			func.setTitle("曲线图");
			func.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_PASSAGER_CHAR.toString());
			func.setChartType(ChartType.SERIES);
			func.setTreeProvider(commonTreeProvider);
			func.setForm(new SeriesForm());
			func.setChartTitle("");

			Action action = new Action("刷 新");
			action.setShowProgress(true);
			SeriesActionHandler odHandler = new SeriesActionHandler();
			action.setActionHandler(odHandler);
			odHandler.setMaxCountOfStation(Integer.MAX_VALUE);

			func.addAction(action);

			func.setImage("/com/insigma/afc/odmonitor/images/serier.png");
			functions.add(func);
		}
		//		{
		//			ChartFunction chart = new ChartFunction();
		//			chart.setTitle("票卡对比");
		//			chart.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_PASSAGER_TICKETCARD.toString());
		//			chart.setImage("/com/insigma/afc/odmonitor/images/contrast-pie.png");
		//			chart.setChartType(ChartType.MULTIPIE);
		//			Form<TicketPieForm> form = new Form<TicketPieForm>(new TicketPieForm());
		//			form.setColums(1);
		//			chart.setForm(form);
		//			chart.setTreeProvider(commonTreeProvider);
		//			functions.add(chart);
		//
		//			Action sAction = new Action();
		//			sAction.setShowProgress(true);
		//			sAction.setText("刷 新");
		//
		//			MultiPieActionHandler handler = new MultiPieActionHandler();
		//			sAction.setActionHandler(handler);
		//			chart.addAction(sAction);
		//		}
		{
			TreeSearchFunction function = new TreeSearchFunction();
			function.setTitle("客流查询");
			function.setFunctionID(WZACCLogModuleCode.MODULE_MONITOR_MANAGE_PASSAGER_MOINTOR.toString());
			function.setForm(new ConditionForm());
			function.setTreeProvider(commonTreeProvider);
			function.setImage("/com/insigma/afc/odmonitor/images/search.png");
			function.setColumnWidth(new int[] { 100, 150, 200, 100, 100, 100, 100, 100 });
			function.setColumns(new String[] { "序号", "车站/编号", "票种/编码", "进站客流", "出站客流", "购票客流", "充值客流" });

			Action odAction = new Action("查询");
			odAction.setShowProgress(true);
			ODSearchActionHandler handler = new ODSearchActionHandler();
			handler.setMaxCountOfStation(Integer.MAX_VALUE);
			odAction.setActionHandler(handler);
			function.addAction(odAction);
			function.addAction(new ResetAction());

			Action pAction = new Action();
			pAction.setText("翻页");
			pAction.setActionHandler(handler);
			function.setPageChangedAction(pAction);

			functions.add(function);
		}

		group.setFunction(functions);

		return group;
	}
}