/*
 * 项目: 图形监控组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.graphic2d.impl.draw2d;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableLayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;

public class SWTScalableLayeredPane extends ScalableLayeredPane {

	@Override
	protected void paintClientArea(Graphics graphics) {
		if (getChildren().isEmpty())
			return;

		SWTScaledGraphics graphic = new SWTScaledGraphics(graphics);

		boolean optimizeClip = getBorder() == null || getBorder().isOpaque();
		if (!optimizeClip)
			graphic.clipRect(getBounds().getCropped(getInsets()));

		graphic.pushState();
		graphic.scale(getScale());
		paintChildren(graphic);
		graphic.popState();

		graphic.dispose();

	}

	@Override
	protected void paintChildren(Graphics graphics) {
		IFigure child;
		Rectangle clip = Rectangle.SINGLETON;

		for (int i = 0; i < this.getChildren().size(); i++) {
			child = (IFigure) this.getChildren().get(i);
			if (child.isVisible() && child.intersects(graphics.getClip(clip))) {
				graphics.pushState();
				graphics.clipRect(child.getBounds());
				graphics.translate(child.getBounds().x + child.getBounds().width / 2,
						child.getBounds().y + child.getBounds().height / 2);
				graphics.setAntialias(1);

				if (child instanceof SWTFigure) {
					float rotate = ((SWTFigure) child).getRotate();
					graphics.rotate(rotate);
					graphics.setAlpha(((SWTFigure) child).getAlpha());
				}
				child.paint(graphics);
				graphics.restoreState();
			}
		}
	}

	public List<SWTFigure> getSelection(Rectangle rect) {
		SWTFigure child;
		rect.scale(1 / getScale());
		List<SWTFigure> list = new ArrayList<SWTFigure>();
		for (int i = 0; i < this.getChildren().size(); i++) {
			child = (SWTFigure) this.getChildren().get(i);
			if (child.isVisible() && child.intersects(rect)) {
				list.add(child);
				child.setSelected(true);
			} else {
				child.setSelected(false);
			}
		}
		return list;
	}

	public void UnSelectAll() {
		SWTFigure child;
		for (int i = 0; i < this.getChildren().size(); i++) {
			child = (SWTFigure) this.getChildren().get(i);
			child.setSelected(false);
		}
	}
}
