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
package com.insigma.commons.framework.function.form;

import java.io.Serializable;

import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.ui.form.IEditorChangedListener;

public final class Form<T> implements Serializable {

	private static final long serialVersionUID = 3532874417955564084L;

	/**
	 * <li>search:查询模式，可接受输入，控件前可带checkbox</li>
	 * <li>new:新增模式，可接受输入</li>
	 * <li>modify:编辑模式，可接受输入，某些字段不可修改</li>
	 * <li>view:只能查看，不接受输入</li>
	 *
	 */
	public enum FormMode {
		SEARCH, NEW, MODIFY, VIEW
	}

	public boolean isColumnsEqualWidth = true; //设置所有列等宽

	public Integer labelWidth = 100;//前面的label的默认宽度

	private Integer colums = 2;//控件的列表，不包含前面的label

	private FormMode formMode = FormMode.NEW;//form的编辑模式

	private T entity;

	public IEditorChangedListener changedListener;//form的值改变监听器

	public Form(T entity) {
		if (entity == null) {
			throw new ApplicationException("Form对应的实体不能为空。");
		}
		this.entity = entity;
	}

	public Form(T entity, int column) {
		this(entity);
		this.colums = column;
	}

	public Integer getColums() {
		return colums;
	}

	public void setColums(Integer colums) {
		this.colums = colums;
	}

	public void setFormMode(FormMode formMode) {
		this.formMode = formMode;
	}

	public FormMode getFormMode() {
		return formMode;
	}

	public T getEntity() {
		return entity;
	}

}
