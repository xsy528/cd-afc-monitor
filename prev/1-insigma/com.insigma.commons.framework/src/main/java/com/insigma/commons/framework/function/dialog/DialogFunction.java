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
package com.insigma.commons.framework.function.dialog;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.application.Module;

public class DialogFunction extends Function {

	protected int width;

	protected int height;

	protected Action openAction;

	protected Action closeAction;

	protected String textTip;

	public String getTextTip() {
		return textTip;
	}

	public void setTextTip(String textTip) {
		this.textTip = textTip;
	}

	public Action getOpenAction() {
		return openAction;
	}

	public void setOpenAction(Action openAction) {
		this.openAction = openAction;
		if (openAction != null) {
			openAction.setFunction(this);
		}
	}

	public Action getCloseAction() {
		return closeAction;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setCloseAction(Action closeAction) {
		this.closeAction = closeAction;
		if (closeAction != null) {
			closeAction.setFunction(this);
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
