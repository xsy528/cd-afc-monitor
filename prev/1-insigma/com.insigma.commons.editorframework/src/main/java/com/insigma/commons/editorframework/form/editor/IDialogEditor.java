/* 
 * 日期：2016-7-8
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.form.editor;

import java.lang.reflect.Field;

public interface IDialogEditor {
	public Object editor(Object value, Field field);

	public Object editor(Object value, Field field, Object compareValue);
}
