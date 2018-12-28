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
package com.insigma.commons.framework.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.commons.framework.Action;

public class UIPlatForm {

	private Map<String, StatusPage> statusPageMap = new HashMap<String, StatusPage>();

	private String id;

	private String icon;

	private String image;

	private String title;

	private String name;

	private List<Action> actions;

	private List<Module> modules;

	private List<StatusPage> statusPages;

	private IAfterUICreateInitor afterUICreateInitor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void addAction(Action action) {
		if (actions == null) {
			actions = new ArrayList<Action>();
		}
		actions.add(action);
	}

	public List<StatusPage> getStatusPages() {
		return statusPages;
	}

	public void setStatusPages(List<StatusPage> statusPages) {
		this.statusPages = statusPages;
	}

	public void addStatusPage(StatusPage statusPage) {
		addStatusPage(statusPage, null);
	}

	public void addStatusPage(StatusPage statusPage, String name) {
		if (statusPages == null) {
			statusPages = new ArrayList<StatusPage>();
		}
		statusPages.add(statusPage);
		if (name == null) {
			name = statusPage.getClass().getName();
		}
		statusPageMap.put(name, statusPage);
	}

	public StatusPage getStatusPage(int index) {
		if (index >= 0 && index < statusPages.size()) {
			return statusPages.get(index);
		}
		return null;
	}

	public StatusPage getStatusPage(String name) {
		return statusPageMap.get(name);
	}

	public IAfterUICreateInitor getAfterUICreateInitor() {
		return afterUICreateInitor;
	}

	public void setAfterUICreateInitor(IAfterUICreateInitor afterUICreateInitor) {
		this.afterUICreateInitor = afterUICreateInitor;
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

}
