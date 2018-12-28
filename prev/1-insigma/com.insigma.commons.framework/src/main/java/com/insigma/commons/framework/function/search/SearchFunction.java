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
package com.insigma.commons.framework.function.search;

import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.table.TableViewFunction;

public class SearchFunction<T> extends TableViewFunction {

	protected Form<T> form;

	public static final int ACTIONBAR = 0x01;

	public static final int TOOLBAR = 0x02;

	public static final int KTABLE = 0x04;//ktable显示
	protected int[] columnIndexes;

	protected int alignment = 0x4000;// SWT.LEFT

	protected int style = ACTIONBAR;

	protected boolean isCellSelectionMode = false;

	public Form<T> getForm() {
		return form;
	}

	public void setForm(Form<T> form) {
		this.form = form;
	}

	public void setForm(T formObject) {
		this.form = new Form<T>(formObject);
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public boolean isCellSelectionMode() {
		return isCellSelectionMode;
	}

	public void setCellSelectionMode(boolean isCellSelectionMode) {
		this.isCellSelectionMode = isCellSelectionMode;
	}

	/**
	 * @param columnIndexes
	 *            the columnIndexes to set
	 */
	public void setColumnIndexes(int[] columnIndexes) {
		this.columnIndexes = columnIndexes;
	}

	/**
	 * @return the columnIndexes
	 */
	public int[] getColumnIndexes() {
		return columnIndexes;
	}

	/**
	 * @param alignment
	 *            the alignment to set
	 */
	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	/**
	 * @return the alignment
	 */
	public int getAlignment() {
		return alignment;
	}

}
