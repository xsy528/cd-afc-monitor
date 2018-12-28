/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableView extends Table implements IControl {

	protected static int DEFAULT_COLUMN_WIDTH = 100;

	public static String DEFAULT_NULL_VALUE = "";

	public static final String ITEM_DATA_KEY_PREFIX = "DATA_";

	public static final String ITEM_FIELD_KEY_PREFIX = "FIELD_";

	protected String[] column;

	protected int[] weight;

	protected int height = 20;

	protected int rowIndex;

	public TableView(Composite parent, int style) {
		super(parent, style | SWT.FULL_SELECTION);
		setLinesVisible(true);
		setHeaderVisible(true);
		addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event arg0) {
				arg0.height = height;
			}
		});
	}

	protected void checkSubclass() {
	}

	public String[] getColumn() {
		return column;
	}

	public void setColumn(String[] column) {
		this.column = column;
		refresh();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int[] getWeight() {
		return weight;
	}

	public void setWeight(int[] weight) {
		this.weight = weight;
		int k = 0;
		for (int i : weight) {
			if (i > 0) {
				getColumn(k).setWidth(i);
			}
			k++;
		}
	}

	/**
	 * 对table控件进行刷新 设置列宽，列名
	 */
	protected void refresh() {
		removeAll();
		if (column != null) {
			for (int i = 0; i < column.length; i++) {
				TableColumn tablecol = new TableColumn(this, SWT.NONE);
				if ((weight != null) && (i < weight.length)) {
					tablecol.setWidth(weight[i]);
				} else {
					tablecol.setWidth(DEFAULT_COLUMN_WIDTH);
				}
				tablecol.setText(column[i]);
			}
		}
	}

	public void removeAll() {
		if (this.isDisposed()) {
			return;
		}
		super.removeAll();
		this.setSortDirection(SWT.NONE);
	}

	public void reset() {
		removeAll();
	}

	public void setAlignment(int index, int alignment) {
		getColumns()[index].setAlignment(alignment);
	}

	public void setAlignment(int[] index, int alignment) {
		for (int element : index) {
			getColumns()[element].setAlignment(alignment);
		}
	}

	public void setAllAlignment(int alignment) {
		for (TableColumn col : getColumns()) {
			col.setAlignment(alignment);
		}
	}

	protected void startFill() {
		rowIndex = 0;
	}

	protected void endFill() {
		// rowIndex++;
		int count = getItemCount();
		for (int i = count - 1; i >= rowIndex; i--) {
			remove(i);
		}
	}

	/**
	 * 填充 table
	 * 
	 * @param dataList
	 *            <code>List<?></code>如果dataList为空 或Null清空表内容
	 * @return
	 */
	public void fillObjectList(List<?> dataList) {
		fillObjectList(dataList, null);
	}

	/**
	 * 填充 table
	 * 
	 * @param dataList
	 *            <code>List<?></code>如果dataList为空 或Null清空表内容
	 * @param rowStyleListener
	 * @return
	 */
	public void fillObjectList(List<?> dataList, IRowStyleListener rowStyleListener) {
		if (dataList == null) {
			removeAll();
			return;
		}
		startFill();
		for (int i = 0; i < dataList.size(); i++) {
			TableItem rowItem = getRowItem(i);
			Object rowData = dataList.get(i);
			fillRowByObject(rowData, rowItem);
			if (null != rowStyleListener) {
				rowStyleListener.setStyle(dataList, rowData, rowItem);
			}
			rowIndex++;
		}
		endFill();
	}

	/**
	 * @param rowData
	 * @param rowItem
	 */
	protected void fillRowByObject(Object rowData, TableItem rowItem) {
		// TODO
		rowItem.setText(rowData.toString());
	}

	/**
	 * 填充 table
	 * 
	 * @param dataList
	 *            <code>List<Object[]></code>数组 ,如果dataList为空 或Null清空表内容
	 * @return
	 */
	public void fillArrayList(List<Object[]> dataList) {
		fillArrayList(dataList, null);
	}

	/**
	 * 填充 table
	 * 
	 * @param dataList
	 *            <code>List<Object[]></code>数组 ,如果dataList为空 或Null清空表内容
	 * @param rowStyleListener
	 * @return
	 */
	public void fillArrayList(List<? extends Object[]> dataList, IRowStyleListener rowStyleListener) {
		if (dataList == null) {
			removeAll();
			return;
		}
		startFill();
		for (int i = 0; i < dataList.size(); i++) {
			TableItem rowItem = getRowItem(i);
			Object[] rowData = dataList.get(i);
			fillRowByArray(rowData, rowItem);
			if (null != rowStyleListener) {
				rowStyleListener.setStyle(dataList, rowData, rowItem);
			}
			rowIndex++;
		}
		endFill();
	}

	/**
	 * @param rowData
	 * @param rowItem
	 */
	protected void fillRowByArray(Object[] rowData, TableItem rowItem) {
		rowItem.setData(rowData);
		for (int j = 0; j < rowData.length; j++) {
			if (rowData[j] != null) {
				rowItem.setText(j, String.valueOf(rowData[j]));
				rowItem.setData(ITEM_FIELD_KEY_PREFIX + j, rowData[j]);
			} else {
				rowItem.setText(j, DEFAULT_NULL_VALUE);
			}
		}
	}

	protected TableItem getRowItem(int itemIndex) {
		if (itemIndex < getItemCount()) {
			final TableItem item = getItem(itemIndex);
			// 设置颜色
			int columnCount = getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				item.setBackground(i, null);
				item.setForeground(i, null);
				item.setFont(i, null);
				item.setImage(i, null);
			}
			item.setBackground(null);
			item.setForeground(null);
			item.setFont(null);
			return item;
		} else {
			return new TableItem(this, SWT.NONE);
		}
	}

	public interface IRowStyleListener {

		void setStyle(List<?> objectList, Object row, TableItem rowItem);

	}
}
