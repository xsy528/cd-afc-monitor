/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-4
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework.application;

import org.eclipse.swt.widgets.Control;

import com.insigma.commons.framework.Action;

public class StatusPage {

	protected String key;

	protected Class<?> composite;

	protected Control control;

	protected boolean visible;

	protected int width;

	protected String text;

	protected Integer status;

	protected Long statuss = (long) -1;

	protected Action selectionAction;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public Class<?> getComposite() {
		return composite;
	}

	public void setComposite(Class<?> composite) {
		this.composite = composite;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public String getText() {
		return text;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	//chengdu为组合模式模式编码设为long型
	public void setStatuss(long status) {
		this.statuss = status;
	}

	//chengdu为组合模式模式编码设为long型
	public long getStatuss() {
		return statuss;
	}

	public void update() {
	}

	public void destroy() {
	}

	public Action getSelectionAction() {
		return selectionAction;
	}

	public void setSelectionAction(Action selectionAction) {
		this.selectionAction = selectionAction;
	}

}
