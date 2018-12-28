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

import com.insigma.commons.framework.application.Function;

public class FormFunction<T> extends Function {

	protected Form<T> form;

	public Form<T> getForm() {
		return form;
	}

	public void setForm(Form<T> form) {
		this.form = form;
	}

	public void setForm(T formObject) {
		this.form = new Form<T>(formObject);
	}
}
