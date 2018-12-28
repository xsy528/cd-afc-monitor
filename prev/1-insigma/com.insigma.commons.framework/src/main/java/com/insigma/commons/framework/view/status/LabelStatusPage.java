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

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.insigma.commons.application.Application;
import com.insigma.commons.framework.application.StatusPage;

public class LabelStatusPage extends StatusPage {

	public LabelStatusPage() {
		this.composite = Label.class;
	}

	public void update() {

		if (key != null) {
			Object value = Application.getData(key);
			if (value == null) {
				value = "";
				Application.setData(key, value);
			}
			setText(String.valueOf(value));
		}

		if (getControl() instanceof Label) {
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					if (getText() != null) {
						((Label) getControl()).setText(getText());
						((Label) getControl()).setToolTipText(getText());
						getControl().getParent().layout();
					}
				}

			});

		}
	}
}
