/* 
 * 日期：2010-4-30
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.odmonitor.service;

import java.util.List;

import com.insigma.afc.odmonitor.form.BarForm;
import com.insigma.afc.odmonitor.form.ConditionForm;
import com.insigma.afc.odmonitor.form.PieForm;
import com.insigma.afc.odmonitor.form.SeriesForm;
import com.insigma.afc.odmonitor.form.TicketPieForm;
import com.insigma.commons.application.IService;
import com.insigma.commons.framework.view.chart.data.BarChartData;
import com.insigma.commons.framework.view.chart.data.MultiPieData;
import com.insigma.commons.framework.view.chart.data.PieChartData;
import com.insigma.commons.framework.view.chart.data.SeriesChartData;

/**
 * 客流数据获取接口
 * 
 * @author lhz
 */
public interface IChartService extends IService {
	/**
	 * 获取客流柱状图数据
	 * 
	 * @param form
	 *            包括车站编号，日期，起始时间，结束时间，显示项(比如进站、出站、购票、充值等)
	 * @return
	 */
	BarChartData getBarChartData(BarForm form);

	/**
	 * 获取客流饼图数据
	 * 
	 * @param form
	 *            包括车站编号，日期，起始时间，结束时间，显示项(比如进站、出站、购票、充值等)
	 * @return
	 */
	PieChartData getPieChartData(PieForm form);

	MultiPieData getMultiPieData(TicketPieForm form);

	/**
	 * 获取客流曲线图数据
	 * 
	 * @param form
	 *            包括车站编号，日期，起始时间，结束时间，显示项(比如进站、出站、购票、充值等)，时间点长度(单位为分钟)
	 * @return
	 */
	SeriesChartData getSeriesChartData(SeriesForm form);

	/**
	 * 获取客流记录数
	 * 
	 * @param para
	 *            车站数组、日期、开始时间下标，结束时间下标，统计分类、票种归类
	 * @return 记录数
	 */
	Long getODCount(ConditionForm form);

	/**
	 * 获取客流记录
	 * 
	 * @param para
	 *            车站数组、日期、开始时间下标，结束时间下标，统计分类、票种归类、当前页、页面大小
	 * @return 客流记录
	 */
	List<Object[]> getODList(ConditionForm form, int page, int pageSize);

	/**
	 * 获取总客流记录数
	 * 
	 * @param para
	 *            日期、开始时间下标，结束时间下标，统计分类、票种归类
	 * @return 记录数
	 */
	List<Object[]> getTotalODList(ConditionForm form);
}
