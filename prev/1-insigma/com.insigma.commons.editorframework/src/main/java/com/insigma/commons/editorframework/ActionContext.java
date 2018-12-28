/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework;

import java.util.HashMap;
import java.util.Map;

public class ActionContext {

	public static final String SELECTED_ITEM = "SELECTED_ITEM";

	public static final String ACTION_EVENTOBJECT = "ACTION_EVENTOBJECT";

	public static final String RESULT_DATA = "RESULT_DATA";

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private EditorFrameWork frameWork;

	private Object data;

	protected Action action;

	/**
	 * @param action
	 */
	public ActionContext(Action action) {
		super();
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	public EditorFrameWork getFrameWork() {
		return frameWork;
	}

	public void setFrameWork(EditorFrameWork frameWork) {
		this.frameWork = frameWork;
		if (action != null) {
			action.setFrameWork(frameWork);
		}
	}

	public Object getData(String key) {
		return dataMap.get(key);
	}

	public void setData(String key, Object value) {
		dataMap.put(key, value);
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
