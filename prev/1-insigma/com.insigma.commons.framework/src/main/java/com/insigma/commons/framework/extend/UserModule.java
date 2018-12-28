/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-3
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework.extend;

import com.insigma.commons.framework.IUserModuleListener;
import com.insigma.commons.framework.IViewComponentBuilder;
import com.insigma.commons.framework.application.Module;

public class UserModule extends Module {

	protected IViewComponentBuilder viewComponentBuilder;

	protected IUserModuleListener userModuleListener;

	protected Class<?> composite;

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

	public IUserModuleListener getUserModuleListener() {
		return userModuleListener;
	}

	public void setUserModuleListener(IUserModuleListener userModuleListener) {
		this.userModuleListener = userModuleListener;
	}

}
