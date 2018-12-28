/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.editorframework.graphic.editor.MapItem;

public class TextGraphicItem extends GraphicItem {

	//private static Color selected = SWT.COLOR_RED;
	//Display.getCurrent().getSystemColor(SWT.COLOR_RED);

	//private static Color normal = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);

	private String text;

	protected boolean hasAngle = true;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TextGraphicItem(MapItem parent, int x, int y, float angle) {
		super(parent, x, y, angle);
	}

	public TextGraphicItem(MapItem parent, int x, int y) {
		super(parent, x, y, 0);
		hasAngle = false;
	}

	public void paint(GC gc) {

		if (getText() == null) {
			return;
		}

		if (isSelected()) {
			//gc.setForeground(selected);
		} else {
			//gc.setForeground(normal);
		}

		setWidth(gc.stringExtent(getText()).x);
		setHeight(gc.stringExtent(getText()).y);

		Transform transform = new Transform(gc.getDevice());
		transform.translate(getX(), getY());
		transform.rotate(getAngle());
		gc.setTransform(transform);
		gc.drawText(getText(), -getWidth() / 2, -getHeight() / 2, true);
		transform.rotate(-getAngle());
		transform.dispose();
		gc.setTransform(null);

	};

	@Override
	public void setAngle(float angle) {
		if (hasAngle) {
			super.setAngle(angle);
		}
	}

	public void setHasAngle(boolean hasAngle) {
		this.hasAngle = hasAngle;
	}
}
