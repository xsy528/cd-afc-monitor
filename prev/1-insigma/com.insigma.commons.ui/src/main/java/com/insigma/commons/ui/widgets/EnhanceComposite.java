/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.insigma.commons.ui.manager.FunctionManager;

public class EnhanceComposite extends Composite implements IControl {

	protected Integer functionID;

	protected Image image;

	public EnhanceComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout());

		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				if (image != null) {
					arg0.gc.drawImage(image, 0, 0);
				}
			}
		});
	}

	@Override
	protected void checkSubclass() {
	}

	public void setImage(Image image) {
		this.image = image;
		this.redraw();
	}

	public Image getImage() {
		return image;
	}

	public void clear() {
		Control[] controls = this.getChildren();
		if (controls != null && controls.length > 0) {
			for (int i = 0; i < controls.length; i++) {
				controls[i].dispose();
			}
		}
	}

	public void reset() {
		Control[] controls = this.getChildren();
		if (controls != null && controls.length > 0) {
			for (int i = 0; i < controls.length; i++) {
				if (controls[i] instanceof IControl) {
					((IControl) controls[i]).reset();
				}
			}
		}
	}

	@Override
	public void setBackground(Color arg0) {
		super.setBackground(arg0);
		Control[] controls = this.getChildren();
		if (controls != null && controls.length > 0) {
			for (int i = 0; i < controls.length; i++) {
				controls[i].setBackground(arg0);
			}
		}
	}

	/**
	 * 当界面处于显示状态时调用（目前只在UserFunction中调用）
	 * 
	 * @param arg
	 */
	public void active(Object arg) {

	}

	public Integer getFunctionID() {
		return this.functionID;
	}

	public void setFunctionID(Integer functionID) {
		this.functionID = functionID;
		setEnabled(FunctionManager.validate(functionID));
	}
}
