/*
 * 项目: 图形监控组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.graphic2d.impl.draw2d;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ScaledGraphics;

public class SWTScaledGraphics extends ScaledGraphics {

	protected Graphics g;

	protected double scale;

	public SWTScaledGraphics(Graphics g) {
		super(g);
		this.g = g;
	}

	@Override
	public void rotate(float degrees) {
		g.rotate(degrees);
	}

	@Override
	public void scale(double amount) {
		scale = amount;
		super.scale(amount);
	}

	@Override
	public void setLineWidth(int width) {
		super.setLineWidth((int) (width * scale));
	}
}
