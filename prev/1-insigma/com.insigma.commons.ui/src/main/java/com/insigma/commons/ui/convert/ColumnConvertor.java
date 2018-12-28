/**
 * 
 */
package com.insigma.commons.ui.convert;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.insigma.commons.ui.anotation.data.ColumnViewData;

/**
 * @author 邱昌进(qiuchangjin@zdwxgd.com)
 *
 */
public interface ColumnConvertor {
	/**
	 * 
	 * @param rowObject 本行的数据
	 * @param itemObject 该单元格的数据
	 * @param row 行号
	 * @param column 列号
	 * @param view
	 * @return
	 */
	public Color getFrontColor(Object rowObject, Object itemObject, int row, int column, ColumnViewData view);

	/**
	 * 
	 * @param rowObject 本行的数据
	 * @param itemObject 该单元格的数据
	 * @param row 行号
	 * @param column 列号
	 * @param view
	 * @return
	 */
	public Color getBackgroundColor(Object rowObject, Object itemObject, int row, int column, ColumnViewData view);

	/**
	 * 
	 * @param rowObject 本行的数据
	 * @param itemObject 该单元格的数据
	 * @param row 行号
	 * @param column 列号
	 * @param view
	 * @return
	 */
	public Image getImage(Object rowObject, Object itemObject, int row, int column, ColumnViewData view);

	/**
	 * 
	 * @param rowObject 本行的数据
	 * @param itemObject 该单元格的数据
	 * @param row 行号
	 * @param column 列号
	 * @param view
	 * @return
	 */
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view);

	/**
	 * 
	 * @param rowObject 本行的数据
	 * @param itemObject 该单元格的数据
	 * @param row 行号
	 * @param column 列号
	 * @param view
	 * @return
	 */
	public Font getFont(Object rowObject, Object itemObject, int row, int column, ColumnViewData view);

}
