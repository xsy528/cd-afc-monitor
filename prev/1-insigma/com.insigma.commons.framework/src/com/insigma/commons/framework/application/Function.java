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
import java.util.LinkedList;
import java.util.List;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.IActionHandler;
import com.insigma.commons.framework.IViewComponent;
import com.insigma.commons.framework.action.FunctionAction;

public class Function {

	protected Module module;

	protected String title;

	protected String image;

	protected String hint;

	protected int groupID;

	protected String functionID;

	protected Function relateFunction;

	protected List<Action> actions;

	protected boolean Default = false;

	protected IFunctionValidator functionValidator;

	protected Action refreshAction;

	// Function 与 界面关联
	protected IViewComponent ViewComponent;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
		if (relateFunction != null) {
			relateFunction.setModule(module);
		}
		if (actions != null) {
			for (Action action : actions) {
				if (action != null && action instanceof FunctionAction
						&& ((FunctionAction) action).getDialogFunction() != null
						&& ((FunctionAction) action).getDialogFunction().getModule() == null) {
					((FunctionAction) action).getDialogFunction().setModule(module);
				}
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public Function getRelateFunction() {
		return relateFunction;
	}

	public void setRelateFunction(Function relateFunction) {
		this.relateFunction = relateFunction;
	}

	public boolean isDefault() {
		return Default;
	}

	public void setDefault(boolean default1) {
		Default = default1;
	}

	public List<Action> getActions() {
		if (actions == null) {
			actions = new LinkedList<Action>();
		}
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void addAction(int index, Action action) {
		if (actions == null) {
			actions = new ArrayList<Action>();
		}
		action.setFunction(this);
		actions.add(index, action);
	}

	public void addAction(Action action) {
		if (actions == null) {
			actions = new ArrayList<Action>();
		}
		action.setFunction(this);
		actions.add(action);
	}

	public void addAction(String text, IActionHandler actionHandler) {
		Action action = new Action();
		action.setText(text);
		action.setActionHandler(actionHandler);
		addAction(action);
	}

	public void addAction(int index, String text, IActionHandler actionHandler) {
		Action action = new Action();
		action.setText(text);
		action.setActionHandler(actionHandler);
		addAction(index, action);
	}

	public IFunctionValidator getFunctionValidator() {
		return functionValidator;
	}

	public void setFunctionValidator(IFunctionValidator functionValidator) {
		this.functionValidator = functionValidator;
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", title, functionID);
	}

	public Action getRefreshAction() {
		return refreshAction;
	}

	public void setRefreshAction(Action refreshAction) {
		this.refreshAction = refreshAction;
	}

	public IViewComponent getViewComponent() {
		return ViewComponent;
	}

	public void setViewComponent(IViewComponent viewComponent) {
		ViewComponent = viewComponent;
	}

}