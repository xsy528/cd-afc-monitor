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

import com.insigma.commons.framework.application.Function;

public class StandardDialogFunction extends DialogFunction {

	protected String text;

	protected String description;

	protected int width;

	protected int height;

	protected Function function;

	public StandardDialogFunction() {
	}

	public StandardDialogFunction(Function function) {
		this.function = function;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

}
