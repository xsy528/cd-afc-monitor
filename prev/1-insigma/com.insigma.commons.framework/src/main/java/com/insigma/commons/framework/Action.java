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
package com.insigma.commons.framework;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.framework.application.Function;
import com.insigma.commons.framework.exeption.NotImplementedException;

public class Action {
	public enum ActionModel {
		SEARCH, PAGE_CHANGE
	}

	protected Log logger = LogFactory.getLog(getClass());

	protected Function function;

	protected String actionID;

	protected Action parent;

	protected int type;

	protected int len = 70;

	protected String text;

	protected String image;

	protected boolean enabled;

	protected boolean visiable;

	protected IActionHandler actionHandler;

	protected Map<Object, Object> data;

	protected List<Action> childs;

	protected int flag;

	// 控制显示进度条
	protected boolean showProgress = false;

	protected String showProgressText;

	protected ActionModel actionModel = ActionModel.SEARCH;// action类型，查询|翻页

	// 控制是否弹出对话框
	private boolean bMute = false;

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public Action() {

	}

	public Action(String text) {
		this.text = text;
	}

	public Action(String text, IActionHandler actionHandler) {
		this.text = text;
		this.actionHandler = actionHandler;
	}

	public Action(IActionHandler actionHandler) {
		this.actionHandler = actionHandler;
	}

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

	public IActionHandler getActionHandler() {
		return actionHandler;
	}

	public void setActionHandler(IActionHandler actionHandler) {
		this.actionHandler = actionHandler;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getActionID() {
		return actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}

	public boolean isVisiable() {
		return visiable;
	}

	public void setVisiable(boolean visiable) {
		this.visiable = visiable;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Action getParent() {
		return parent;
	}

	public void setParent(Action parent) {
		this.parent = parent;
	}

	public List<Action> getChilds() {
		return childs;
	}

	public void setChilds(List<Action> childs) {
		this.childs = childs;
	}

	public void put(Object key, Object value) throws NotImplementedException {
		throw new NotImplementedException();
	}

	public Object get(Object key) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * @return the showProgress
	 */
	public boolean isShowProgress() {
		return showProgress;
	}

	/**
	 * @param showProgress
	 *            the showProgress to set
	 */
	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}

	/**
	 * @return the showProgressText
	 */
	public String getShowProgressText() {
		return showProgressText;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	/**
	 * @param showProgressText
	 *            the showProgressText to set
	 */
	public void setShowProgressText(String showProgressText) {
		this.showProgressText = showProgressText;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * @return the actionModel
	 */
	public ActionModel getActionModel() {
		return actionModel;
	}

	/**
	 * @param actionModel
	 *            the actionModel to set
	 */
	public void setActionModel(ActionModel actionModel) {
		this.actionModel = actionModel;
	}

	public boolean isbMute() {
		return bMute;
	}

	public void setbMute(boolean bMute) {
		this.bMute = bMute;
	}

	// public ILogService getLogService() {
	// return logService;
	// }

}
