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

import com.insigma.commons.framework.application.StatusPage;

public class ScrollLabelStatusPage extends StatusPage {

	public ScrollLabelStatusPage() {
		this.composite = Label.class;
	}

	public void update() {
		if (getControl() instanceof Label) {
			if (getText() != null) {
				((Label) getControl()).setText(getText());
				getControl().getParent().layout();
			}
		}
	}
}
