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

import com.insigma.commons.framework.Request;

public class FormRequest<T> extends Request {

	protected Form<T> form;

	public Form<T> getForm() {
		return form;
	}

	public T getRequestValue() {
		if (form == null) {
			return null;
		}
		return form.getEntity();
	}

	public void setForm(Form<T> form) {
		this.form = form;
	}

}
