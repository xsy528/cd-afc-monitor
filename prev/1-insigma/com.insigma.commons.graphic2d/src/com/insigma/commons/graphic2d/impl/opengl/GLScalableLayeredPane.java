/*
 * 项目: 图形监控组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.graphic2d.impl.opengl;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ScalableLayeredPane;

public class GLScalableLayeredPane extends ScalableLayeredPane {

	@Override
	protected void paintClientArea(Graphics graphics) {
		if (getChildren().isEmpty())
			return;
		if (getScale() == 1.0) {
			super.paintClientArea(graphics);
		} else {
			GLScaledGraphics g = new GLScaledGraphics();
			boolean optimizeClip = getBorder() == null || getBorder().isOpaque();
			if (!optimizeClip)
				g.clipRect(getBounds().getCropped(getInsets()));
			g.scale(getScale());
			g.pushState();
			paintChildren(g);
			g.dispose();
			graphics.restoreState();
		}
	}

}
