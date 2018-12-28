package com.insigma.afc.view;

import java.util.ArrayList;
import java.util.List;

import com.insigma.afc.ui.status.CommunicationStatusPage;
import com.insigma.afc.ui.status.DatabaseStatusPage;
import com.insigma.afc.view.action.LogoutAction;
import com.insigma.commons.framework.application.Module;
import com.insigma.commons.framework.application.StatusPage;
import com.insigma.commons.framework.application.UIPlatForm;
import com.insigma.commons.framework.config.IPlatFormBuilder;
import com.insigma.commons.framework.view.status.DateStatusPage;
import com.insigma.commons.framework.view.status.LabelStatusPage;
import com.insigma.commons.framework.view.status.ProgressStatusPage;
import com.insigma.commons.framework.view.status.TimeStatusPage;
import com.insigma.commons.framework.view.status.UserStatusPage;

/**
 * 创建时间 2010-9-1 下午03:45:26<br>
 * 描述：票务管理的平台构建器<br>
 * Ticket:
 * 
 * @author DingLuofeng
 */
public class DefaultPlatFormBuilder implements IPlatFormBuilder {

	private final List<Module> moduleList = new ArrayList<Module>();

	private StatusPage[] statusPages;

	public UIPlatForm getPlatForm() {

		UIPlatForm platForm = new UIPlatForm();
		platForm.addAction(new LogoutAction());

		LabelStatusPage labelStatusPage = new LabelStatusPage();
		labelStatusPage.setWidth(-1);
		platForm.addStatusPage(labelStatusPage);

		getStatus(platForm);

		platForm.setModules(moduleList);

		return platForm;
	}

	/**
	 * 添加状态栏
	 * 
	 * @param platForm
	 */
	protected void getStatus(UIPlatForm platForm) {

		LabelStatusPage labelStatusPage = new LabelStatusPage();
		labelStatusPage.setWidth(-1);

		platForm.addStatusPage(labelStatusPage);
		platForm.addStatusPage(new CommunicationStatusPage());
		platForm.addStatusPage(new DatabaseStatusPage());
		if (getStatusPages() != null) {
			for (int i = 0; i < getStatusPages().length; i++) {
				platForm.addStatusPage(getStatusPages()[i]);
			}
		}
		// 用户
		platForm.addStatusPage(new UserStatusPage());
		// 进程
		platForm.addStatusPage(new ProgressStatusPage());
		// 日期
		platForm.addStatusPage(new DateStatusPage());
		// 时间
		platForm.addStatusPage(new TimeStatusPage());
	}

	/**
	 * 在UI平台上添加功能模块
	 * 
	 * @param module
	 */
	public void addModule(Module module) {
		moduleList.add(module);
	}

	public StatusPage[] getStatusPages() {
		return statusPages;
	}

	public void setStatusPages(StatusPage[] statusPages) {
		this.statusPages = statusPages;
	}

}
