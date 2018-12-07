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
package com.insigma.commons.framework.extend;

import com.insigma.commons.framework.IViewComponentBuilder;
import com.insigma.commons.framework.application.Function;

public class UserFunction extends Function {

	private IViewComponentBuilder viewComponentBuilder;

	private Class<?> composite;

	public Class<?> getComposite() {
		return composite;
	}

	public void setComposite(Class<?> composite) {
		this.composite = composite;
	}

	public IViewComponentBuilder getViewComponentBuilder() {
		return viewComponentBuilder;
	}

	public void setViewComponentBuilder(IViewComponentBuilder viewComponentBuilder) {
		this.viewComponentBuilder = viewComponentBuilder;
	}

}
