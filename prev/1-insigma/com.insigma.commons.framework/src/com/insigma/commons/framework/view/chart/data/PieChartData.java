/* 
 * 日期：2010-4-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.view.chart.data;

import java.util.ArrayList;
import java.util.List;

public class PieChartData {

	/**
	 * 饼图中的各个饼块
	 */
	private List<PieData> pieItems = new ArrayList<PieData>();

	/**
	 * 图表每个饼块中包含的各项名,partNames的大小要与valueOfPies大小一致，比如包含进站，出站等
	 */
	private List<String> partNames = new ArrayList<String>();

	/**
	 * 图表每个饼块中包含的各项是否显示，（showRows的大小要与valueOfPies大小一致，或者showRows为null,默认全部显示），<br>
	 * 比如包含进站值，出站值等
	 */
	private List<Boolean> showRows = new ArrayList<Boolean>();

	public PieChartData() {

	}

	public PieChartData(List<PieData> pieItems) {
		super();
		this.pieItems = pieItems;
	}

	public List<Boolean> getShowRows() {
		return showRows;
	}

	public void setShowRows(List<Boolean> showRows) {
		this.showRows = showRows;
	}

	public static class PieData {
		/**
		 * 图表的列名，比如以车站名为列名
		 */
		private String pieName = "pie";

		/**
		 * 饼图中各个饼块包含的各种值，valueOfPies的大小要与showRows大小一致，比如包含进站值，出站值等
		 */
		private List<Double> valueOfPies = new ArrayList<Double>();

		public PieData() {

		}

		public PieData(String pieName, List<Double> valueOfPies) {
			this.pieName = pieName;
			this.valueOfPies = valueOfPies;
		}

		public String getPieName() {
			return pieName;
		}

		public void setPieName(String pieName) {
			this.pieName = pieName;
		}

		public List<Double> getValueOfPies() {
			return valueOfPies;
		}

		public void setValueOfPies(List<Double> valueOfPies) {
			this.valueOfPies = valueOfPies;
		}

	}

	public List<PieData> getPieItems() {
		return pieItems;
	}

	public void setPieItems(List<PieData> pieItems) {
		this.pieItems = pieItems;
	}

	public List<String> getPartNames() {
		return partNames;
	}

	public void setPartNames(List<String> partNames) {
		this.partNames = partNames;
	}
}
