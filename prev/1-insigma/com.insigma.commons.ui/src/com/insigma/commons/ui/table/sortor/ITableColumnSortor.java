package com.insigma.commons.ui.table.sortor;

import org.eclipse.swt.widgets.TableColumn;

import com.insigma.commons.ui.widgets.TableView;

public interface ITableColumnSortor {

	/**
	* 给表格的指定列添加数值排序器，使用Collator类比较两个float类型数值的大小
	* 
	* @param table
	*            记录数据的表对象
	* @param column
	*            表中的某数值列
	*/
	public abstract void addSorter(final TableView table, final TableColumn column);

	public abstract void addSorter(final TableView table, final int columnIndex);

}