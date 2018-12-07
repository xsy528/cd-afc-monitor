/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.service.EmptyLogService;
import com.insigma.commons.service.ILogService;

public class Action {

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	protected Log logger = LogFactory.getLog(getClass());

	private String ID;

	private String name;

	private String image;

	private int style;

	private boolean checked;

	private List<Action> items;

	private IActionHandler handler;

	private EditorFrameWork frameWork;

	private FrameWorkView workView;

	private Object data;

	protected ILogService bizLogger;

	// 控制显示进度条
	protected boolean showProgress = false;

	protected String showProgressText;

	private boolean undoable = true;

	public Action(String name) {
		this.name = name;
	}

	public Action(String name, IActionHandler handler) {
		this.name = name;
		this.handler = handler;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IActionHandler getHandler() {
		return handler;
	}

	public void setHandler(IActionHandler handler) {
		this.handler = handler;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Action> getItems() {
		if (items == null) {
			items = new ArrayList<Action>();
		}
		return items;
	}

	public void setItems(List<Action> items) {
		this.items = items;
	}

	/*private*/void perform(ActionContext context) {
		if (handler != null && IsEnable()) {
			try {
				logger.debug("执行[" + getName() + "]");
				if (handler instanceof ActionHandlerAdapter) {
					((ActionHandlerAdapter) handler).setLogService(getBizLogger());
				}
				if (getFrameWork() == null) {
					Thread.dumpStack();
				}
				context.setFrameWork(frameWork);
				handler.perform(context);
			} catch (Exception e) {
				logger.error("执行[" + getName() + "]", e);
			}
		}
	}

	public EditorFrameWork getFrameWork() {
		return frameWork;
	}

	public void setFrameWork(EditorFrameWork frameWork) {
		this.frameWork = frameWork;
	}

	public FrameWorkView getWorkView() {
		return workView;
	}

	public void setWorkView(FrameWorkView workView) {
		this.workView = workView;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean IsEnable() {
		return true;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData(String key) {
		return dataMap.get(key);
	}

	public void setData(String key, Object value) {
		dataMap.put(key, value);
	}

	public ILogService getBizLogger() {
		if (this.bizLogger == null) {
			try {
				bizLogger = (ILogService) Application.getService(ILogService.class);
			} catch (ServiceException e) {
				bizLogger = new EmptyLogService();
			}
			if (ID != null) {
				try {
					int funId = Integer.parseInt(ID);
					int moduleId = funId / 100;
					bizLogger.setModule(moduleId);
				} catch (Exception e) {
					logger.warn("Action ID转换为整数异常，请检查Action ID设置", e);
				}
			}
		}
		return bizLogger;
	}

	public void setBizLogger(ILogService bizLogger) {
		this.bizLogger = bizLogger;
	}

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public ActionTreeNode getSelection() {
		ActionTreeView view = (ActionTreeView) getFrameWork().getView(ActionTreeView.class);
		if (view != null) {
			return view.getSelection();
		}
		return null;
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

	/**
	 * @param showProgressText
	 *            the showProgressText to set
	 */
	public void setShowProgressText(String showProgressText) {
		this.showProgressText = showProgressText;
	}

	/**
	 * @return the undoable
	 */
	public boolean isUndoable() {
		return undoable;
	}

	/**
	 * @param undoable the undoable to set
	 */
	public void setUndoable(boolean undoable) {
		this.undoable = undoable;
	}
}
