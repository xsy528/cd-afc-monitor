/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework.function.chart;

import com.insigma.commons.framework.function.form.FormFunction;
import com.insigma.commons.ui.tree.ITreeProvider;

public class ChartFunction extends FormFunction {

	private String chartTitle;

	public enum ChartType {
		BAR, PIE, SERIES, MULTIPIE
	}

	private ITreeProvider treeProvider;

	private ChartType chartType = ChartType.BAR;

	private boolean hideTree = false;

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	/**
	 * @return the hideTree
	 */
	public boolean isHideTree() {
		return hideTree;
	}

	/**
	 * @param hideTree
	 *            the hideTree to set
	 */
	public void setHideTree(boolean hideTree) {
		this.hideTree = hideTree;
	}

}
