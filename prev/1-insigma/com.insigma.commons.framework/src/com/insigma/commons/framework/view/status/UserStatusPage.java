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
package com.insigma.commons.framework.view.status;

import org.eclipse.swt.widgets.Label;

import com.insigma.commons.application.Application;
import com.insigma.commons.framework.application.StatusPage;

public class UserStatusPage extends StatusPage {
	public UserStatusPage() {
		this.composite = Label.class;
		this.width = 150;
	}

	public void update() {
		if (getControl() instanceof Label) {
			Label label = (Label) getControl();
			String uname = getText();
			if (Application.getUser() != null) {
				String userName = Application.getUser().getUserName();
				uname = userName == null ? "匿名" : userName;
			}
			label.setText(uname);
			// label.setToolTipText(uname);
			getControl().getParent().layout();
		}
	}
}
