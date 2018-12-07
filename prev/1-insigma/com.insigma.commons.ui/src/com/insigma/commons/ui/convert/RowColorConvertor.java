/**
 * 
 */
package com.insigma.commons.ui.convert;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.commons.ui.anotation.data.ColumnViewData;

/**
 * @author 邱昌进(qiuchangjin@zdwxgd.com)
 *
 */
public interface RowColorConvertor {
	/**
	 * 
	 * @param rows 所有的数据集
	 * @param rowObject 当前行的数据
	 * @param view
	 * @return
	 */
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view);

	/**
	 * 
	 * @param rows 所有的数据集
	 * @param rowObject 当前行的数据
	 * @param view
	 * @return
	 */
	public Color getBackgroundColor(List<?> rows, Object rowObject, ColumnViewData view);
}
