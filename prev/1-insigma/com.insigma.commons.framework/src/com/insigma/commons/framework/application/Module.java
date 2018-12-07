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
package com.insigma.commons.framework.application;

import java.util.ArrayList;
import java.util.List;

public class Module {

	protected String text;

	protected String image;

	protected String functionID;

	protected List<Function> function;

	protected IModuleEventListener moduleListener;

	protected List<Module> items;

	private int hotKey;

	protected boolean needInit = false;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public List<Function> getFunction() {
		return function;
	}

	public void setFunction(List<Function> functions) {
		this.function = functions;
		if (functions != null) {
			for (Function func : functions) {
				if (null != func && func.getModule() == null) {
					func.setModule(this);
				}
			}
		}
	}

	public IModuleEventListener getModuleListener() {
		return moduleListener;
	}

	public void setModuleListener(IModuleEventListener moduleListener) {
		this.moduleListener = moduleListener;
	}

	public List<Module> getItems() {
		if (items == null) {
			items = new ArrayList<Module>();
		}
		return items;
	}

	public void setItems(List<Module> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", text, functionID);
	}

	public boolean isNeedInit() {
		return needInit;
	}

	public void setNeedInit(boolean needInit) {
		this.needInit = needInit;
	}

	/**
	 * @param hotKey
	 *            the hotKey to set
	 */
	public void setHotKey(int hotKey) {
		this.hotKey = hotKey;
	}

	/**
	 * @return the hotKey
	 */
	public int getHotKey() {
		return hotKey;
	}
}
