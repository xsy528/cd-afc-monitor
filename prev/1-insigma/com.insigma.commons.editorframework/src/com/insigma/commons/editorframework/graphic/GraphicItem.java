/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic;

import java.awt.Shape;
import java.awt.geom.AffineTransform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

import com.insigma.commons.editorframework.graphic.editor.MapItem;

public class GraphicItem {

	private float angle;

	private int x;

	private int y;

	private int width;

	private int height;

	private boolean selected;

	private boolean visible;

	private MapItem parent;

	public GraphicItem() {

	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		angle = angle % 360;
		if (angle < 0) {
			angle = angle + 360;
		}
		this.angle = angle;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@JsonIgnore
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@JsonIgnore
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@JsonIgnore
	public MapItem getParent() {
		return parent;
	}

	public void setParent(MapItem parent) {
		this.parent = parent;
	}

	public GraphicItem(MapItem parent, int x, int y, float angle) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		setAngle(angle);
	}

	public boolean contains(int x, int y) {
		return intersects(new Rectangle(x, y, 1, 1));
	}

	public boolean intersects(Rectangle rect) {
		if (angle != 0) {
			java.awt.Rectangle awtRect = new java.awt.Rectangle(rect.x, rect.y, rect.width, rect.height);
			java.awt.Rectangle awtRectthis = new java.awt.Rectangle(x - width / 2, y - height / 2, this.width,
					this.height);
			AffineTransform affineTransform = AffineTransform.getRotateInstance((angle / 180) * Math.PI, x, y);
			Shape shape = affineTransform.createTransformedShape(awtRectthis);
			return shape.intersects(awtRect);
		}
		return rect.intersects(getBounds());
	}

	@JsonIgnore
	public Rectangle getBounds() {
		java.awt.Rectangle awtRectthis = new java.awt.Rectangle(x - width / 2, y - height / 2, this.width, this.height);
		AffineTransform affineTransform = AffineTransform.getRotateInstance((angle / 180) * Math.PI, x, y);
		Shape shape = affineTransform.createTransformedShape(awtRectthis);
		return new Rectangle(shape.getBounds().x, shape.getBounds().y, shape.getBounds().width,
				shape.getBounds().height);
	}

	@JsonIgnore
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			getParent().setSelected(selected);
		}
	}

	@JsonIgnore
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void paint(GC gc) {
	};
}
