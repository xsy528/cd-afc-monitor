/*
 * 
 */
package com.insigma.commons.framework.function.table;

import org.eclipse.swt.SWT;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.application.Function;

/**
 * 创建时间 2010-11-10 下午03:10:21<br>
 * 描述：<br>
 * Ticket:
 * 
 * @author DingLuofeng
 */
public class MultiTableViewFunction extends Function {

	private PageNavigatorTableViewFunction parentFunction;

	private PageNavigatorTableViewFunction childFunction;

	private int layoutStyle = SWT.HORIZONTAL;

	private Action loadAction;

	/**
	 * @param layoutStyle
	 *            the layoutStyle to set
	 */
	public void setLayoutStyle(int layoutStyle) {
		this.layoutStyle = layoutStyle;
	}

	/**
	 * @return the layoutStyle
	 */
	public int getLayoutStyle() {
		return layoutStyle;
	}

	/**
	 * @param parentFunction
	 *            the parentFunction to set
	 */
	public void setParentFunction(PageNavigatorTableViewFunction parentFunction) {
		this.parentFunction = parentFunction;
	}

	/**
	 * @return the parentFunction
	 */
	public PageNavigatorTableViewFunction getParentFunction() {
		return parentFunction;
	}

	/**
	 * @param childFunction
	 *            the childFunction to set
	 */
	public void setChildFunction(PageNavigatorTableViewFunction childFunction) {
		this.childFunction = childFunction;
	}

	/**
	 * @return the childFunction
	 */
	public PageNavigatorTableViewFunction getChildFunction() {
		return childFunction;
	}

	/**
	 * @param loadAction the loadAction to set
	 */
	public void setLoadAction(Action loadAction) {
		this.loadAction = loadAction;
	}

	/**
	 * @return the loadAction
	 */
	public Action getLoadAction() {
		return loadAction;
	}

}
