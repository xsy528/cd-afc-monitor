/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.ui.FormUtil;
import com.swtdesigner.SWTResourceManager;

public class Shell extends org.eclipse.swt.widgets.Shell implements IControl {

	@Override
	public void open() {
		this.posCenter();
		super.open();
	}

	public Shell() {
		super();
		setLayout(new FillLayout());
		this.setImage(SWTResourceManager.getImage(Shell.class, "/com/insigma/afc/images/logo.png"));
	}

	public Shell(Display arg0, int arg1) {
		super(arg0, arg1);
		this.setImage(SWTResourceManager.getImage(Shell.class, "/com/insigma/afc/images/logo.png"));
	}

	public Shell(Display arg0) {
		super(arg0);
		this.setImage(SWTResourceManager.getImage(Shell.class, "/com/insigma/afc/images/logo.png"));
	}

	public Shell(int arg0) {
		super(arg0);
		this.setImage(SWTResourceManager.getImage(Shell.class, "/com/insigma/afc/images/logo.png"));
	}

	public Shell(org.eclipse.swt.widgets.Shell arg0, int arg1) {
		super(arg0, arg1);
		this.setImage(SWTResourceManager.getImage(Shell.class, "/com/insigma/afc/images/logo.png"));
	}

	public Shell(org.eclipse.swt.widgets.Shell arg0) {
		super(arg0);
		this.setImage(SWTResourceManager.getImage(Shell.class, "/com/insigma/afc/images/logo.png"));
	}

	/**
	 * 描述：这个方法把shell居中放置
	 */
	public void posCenter() {
		setLocation(FormUtil.getCenterLocation(getSize().x, getSize().y));
	}

	@Override
	protected void checkSubclass() {
	}

	public void reset() {
		Control[] childrens = getChildren();
		if (childrens != null) {
			for (int i = 0; i < childrens.length; i++) {
				if (childrens[i] instanceof IControl) {
					((IControl) childrens[i]).reset();
				}
			}
		}

	}
}
