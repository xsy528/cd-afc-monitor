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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.application.Application;
import com.insigma.commons.framework.application.StatusPage;
import com.swtdesigner.SWTResourceManager;

public class IconStatusPage extends StatusPage {

	private String[] image;

	private String[] statusDescS;

	private String[] toolTipTexts;

	public IconStatusPage() {
		this.composite = CLabel.class;
	}

	public String[] getImage() {
		return image;
	}

	public void setImage(String[] image) {
		this.image = image;
	}

	public String[] getStatusDesc() {
		return statusDescS;
	}

	public void setStatusDesc(String[] statusDesc) {
		this.statusDescS = statusDesc;
	}

	private boolean enable = true;

	public void destroy() {
		enable = false;
	}

	public void update() {

		if (key != null) {
			Object value = Application.getData(key);
			if (value == null) {
				value = 0;
				Application.setData(key, value);
			}
			int newstatus = Integer.valueOf(String.valueOf(value));
			if (status != null && newstatus == status) {
				return;
			}
			setStatus(newstatus);

			specificProcess(status);// 通信状态发生变化后，执行指定操作。
		}
		if (status == null) {
			status = 0;
		}

		Image statusTempImage = null;
		String tempStatusDesc = null;
		String temptoolTipText = null;
		if (status < image.length) {
			String imagePath = image[status];
			statusTempImage = SWTResourceManager.getImage(IconStatusPage.class, imagePath);
		}
		if (null != status && null != statusDescS && status < statusDescS.length) {
			tempStatusDesc = statusDescS[status];
		}
		if (null != status && null != toolTipTexts && status < toolTipTexts.length) {
			temptoolTipText = toolTipTexts[status];
		}
		final Image statusImage = statusTempImage;
		final String statusDesc = tempStatusDesc;
		final String toolTipText = temptoolTipText;
		if (getControl() instanceof CLabel && !getControl().isDisposed()) {
			if (statusImage != null) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (!enable) {
							return;
						}
						CLabel clabel = (CLabel) getControl();
						if (clabel == null || clabel.isDisposed()) {
							return;
						}
						((CLabel) getControl()).setText(statusDesc);
						((CLabel) getControl()).setToolTipText(toolTipText);
						((CLabel) getControl()).setImage(statusImage);
						final GridData gdLblCommInfo = new GridData(SWT.LEFT, SWT.CENTER, false, true);
						((CLabel) getControl()).setLayoutData(gdLblCommInfo);
						getControl().getParent().layout();
					}
				});
			}
		}
	}

	protected void specificProcess(Integer status) {
	}

	public void setToolTipTexts(String[] toolTipTexts) {
		this.toolTipTexts = toolTipTexts;
	}

	public String[] getToolTipTexts() {
		return toolTipTexts;
	}
}
