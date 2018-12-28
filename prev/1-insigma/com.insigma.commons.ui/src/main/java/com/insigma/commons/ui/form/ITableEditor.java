/* 
 * 日期：2016-7-7
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.form;

import java.lang.reflect.Field;

public interface ITableEditor {

	public Class getDataClass();

	public Field getField();

	public Object getCompareRow(int index);

	public Object getRow(int index);

	public void updateRow(int index, Object obj) throws Exception;

	public Object copyRow(int index);

	public boolean addRow(Object obj);

	public boolean removeRow(int[] indexes);
}
