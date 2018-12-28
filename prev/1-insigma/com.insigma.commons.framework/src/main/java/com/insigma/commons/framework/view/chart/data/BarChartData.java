/* 
 * 日期：2010-4-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.view.chart.data;

import java.util.ArrayList;
import java.util.List;

public class BarChartData {

	/**
	 * 显示每个柱的数值，并修改该数值的字体属性
	 */
	private boolean barLableShow;

	/**
	 * 图表的列
	 */
	private List<ColumnData> columnItems = new ArrayList<ColumnData>();

	/**
	 * 图表的列名中包含的行名,rowNames的大小要与showRows大小一致，比如包含进站，出站等
	 */
	private List<String> rowNames = new ArrayList<String>();

	/**
	 * 图表的列名中包含的行是否显示，（showRows的大小要与rowNames大小一致，或者showRows为null,默认全部显示），<br>
	 * 比如包含进站值，出站值等
	 */
	private List<Boolean> showRows = new ArrayList<Boolean>();

	public BarChartData() {

	}

	public BarChartData(List<String> rowNames, List<ColumnData> columnItems) {
		super();
		this.rowNames = rowNames;
		this.columnItems = columnItems;
	}

	public List<String> getRowNames() {
		return rowNames;
	}

	public void setRowNames(List<String> rowNames) {
		this.rowNames = rowNames;
	}

	public List<Boolean> getShowRows() {
		return showRows;
	}

	public void setShowRows(List<Boolean> showRows) {
		this.showRows = showRows;
	}

	public static class ColumnData {
		/**
		 * 图表的列名，比如以车站名为列名
		 */
		private String columnName = "col";

		/**
		 * 图表的列名中包含的行名对于的图表值，valueOfRows的大小要与rowNames大小一致，比如包含进站值，出站值等
		 */
		private List<Number> valueOfRows = new ArrayList<Number>();

		public ColumnData() {

		}

		public ColumnData(String columnName, List<Number> valueOfRows) {
			this.columnName = columnName;
			this.valueOfRows = valueOfRows;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public List<Number> getValueOfRows() {
			return valueOfRows;
		}

		public void setValueOfRows(List<Number> valueOfRows) {
			this.valueOfRows = valueOfRows;
		}
	}

	public List<ColumnData> getColumnItems() {
		return columnItems;
	}

	public void setColumnItems(List<ColumnData> columnItems) {
		this.columnItems = columnItems;
	}

	public void setBarLableShow(boolean barLableShow) {
		this.barLableShow = barLableShow;
	}

	public boolean isBarLableShow() {
		return barLableShow;
	}

}
