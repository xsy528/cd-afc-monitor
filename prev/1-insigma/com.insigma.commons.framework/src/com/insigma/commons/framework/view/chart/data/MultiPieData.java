package com.insigma.commons.framework.view.chart.data;

import java.util.List;

public class MultiPieData {
	/**
	 * 饼图数据
	 */
	private List<PieChartData> data;

	/**
	 * 对应饼图的名字
	 */
	private List<String> PieChartNames;

	public List<String> getPieChartNames() {
		return PieChartNames;
	}

	public void setPieChartNames(List<String> pieChartNames) {
		PieChartNames = pieChartNames;
	}

	public List<PieChartData> getData() {
		return data;
	}

	public void setData(List<PieChartData> data) {
		this.data = data;
	}

}
