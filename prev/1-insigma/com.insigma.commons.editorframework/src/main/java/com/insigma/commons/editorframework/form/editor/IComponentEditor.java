/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.form.editor;

import com.insigma.commons.ui.form.IEditorChangedListener;

public interface IComponentEditor {

	public void setCompareValue(Object value);

	public void setValue(Object value);

	public Object getValue();

	public void setEditable(boolean isEditable);

	public void setChangedListener(IEditorChangedListener changedListener);

}
