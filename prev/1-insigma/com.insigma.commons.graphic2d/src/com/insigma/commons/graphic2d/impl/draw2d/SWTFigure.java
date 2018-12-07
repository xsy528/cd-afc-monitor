/*
 * 项目: 图形监控组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.graphic2d.impl.draw2d;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.graphic2d.Color;

public class SWTFigure extends Figure {

	protected Color transparent;

	protected boolean selected;

	protected boolean showText;

	protected double scale;

	protected int alpha = 255;

	protected float rotate;

	protected Image image;

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		if (this.scale != scale) {
			this.scale = scale;
			this.repaint();
		}
	}

	public float getRotate() {
		return rotate;
	}

	public void setRotate(float rotate) {
		if (this.rotate != rotate) {
			this.rotate = rotate;
			this.repaint();
		}
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		if (this.alpha != alpha) {
			this.alpha = alpha;
			this.repaint();
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			this.repaint();
		}
	}

	public boolean isShowText() {
		return showText;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (super.containsPoint(x, y)) {
			int xOffset = x - this.getBounds().x;
			int yOffset = y - this.getBounds().y;
			Image image = getImage();
			if (image.getImageData().getPixel(xOffset, yOffset) == 0xFFFFFF00) {
				return false;
			}
			return true;
		}
		return false;
	}

	public Image getImage() {
		if (image == null) {
			image = new Image(Display.getDefault(), this.getBounds().width, this.getBounds().height);
			GC gc = new GC(image);
			SWTGraphics swtGC = new SWTGraphics(gc);
			SWTScaledGraphics graphic = new SWTScaledGraphics(swtGC);
			graphic.translate(this.getBounds().width / 2, this.getBounds().height / 2);
			paint(graphic);
		}
		return image;
	}

	public Color getTransparent() {
		return transparent;
	}

	public void setTransparent(Color transparent) {
		this.transparent = transparent;
	}
}
