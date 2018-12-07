package com.insigma.commons.editorframework.graphic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;

import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.swtdesigner.SWTResourceManager;

/**
 * 创建时间 2010-10-8 上午09:36:58 <br>
 * 描述: <br>
 * Ticket：支持双向箭头线 2011-11-9 15:21:37
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class LineGraphicItem extends GraphicItem {

	private int imageIndex;

	/* 起点x */
	int startX;

	/* 起点y */
	int startY;

	/* 终点x */
	int endX;

	/* 终点y */
	int endY;

	/* 颜色 RGB */
	private RGB rgb;

	/* 直线颜色  */
	private int[] colorValues = new int[] { 0, 0 };

	/* 类型 */
	private int lineStyle = SWT.LINE_SOLID;

	/* 宽度 */
	int lineWidth = 3;

	/* 双线距离 */
	int lineDistance = 5;

	/* 箭头： 0：单无箭头；1单箭头；2双箭头 */
	private int arrowType = 2;

	public Integer getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
	}

	public LineGraphicItem(MapItem parent, int x1, int y1, int x2, int y2, float angle) {
		super(parent, x1, y1, angle);
		this.startX = x1;
		this.startY = y1;
		this.endX = x2;
		this.endY = y2;
	}

	@Override
	public void paint(GC gc) {
		gc.setLineStyle(lineStyle);
		gc.setLineWidth(lineWidth);
		gc.setAntialias(SWT.ON);

		switch (arrowType) {
		case 0:
			gc.drawLine(startX, startY, endX, endY);
			break;
		case 1:
			gc.drawLine(startX, startY, endX, endY);
			paintArrow(gc, startX, startY, endX, endY);
			break;
		case 2:
			paintDoubleLine(gc, startX, startY, endX, endY);
			break;

		default:
			break;
		}

		if (lineStyle != SWT.LINE_SOLID) {
			gc.setLineWidth(SWT.LINE_SOLID);
		}
		if (lineWidth != 1) {
			gc.setLineWidth(1);
		}

	}

	@Override
	public boolean contains(int x, int y) {
		double distance = pointToLine(x, y, startX, startY, endX, endY);
		if (distance < lineDistance)
			return true;
		else
			return false;
	}

	public double pointToLine(double x, double y, double x1, double y1, double x2, double y2) {
		double dis = 0;
		double a, b, c;
		a = distance(x1, y1, x2, y2);
		b = distance(x1, y1, x, y);
		c = distance(x2, y2, x, y);

		if (c * c > a * a || b * b > a * a) {
			dis = b > c ? b : c;
			return dis;
		}
		if (Math.abs(x1 - x2) < 0.00001) {
			dis = Math.abs(x - x1);
			return dis;
		}
		double lineK = (y2 - y1) / (x2 - x1);// 斜率
		double lineC = (x2 * y1 - x1 * y2) / (x2 - x1);
		dis = Math.abs(lineK * x - y + lineC) / (Math.sqrt(lineK * lineK + 1));
		return dis;
	}

	private double distance(double x1, double y1, double x2, double y2) {
		double lineLength = 0;
		lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return lineLength;
	}

	/**
	 * 双箭头线
	 * @param g
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void paintDoubleLine(GC g, int x1, int y1, int x2, int y2) {
		double awrad = Math.PI / 2;
		double arraow_len = lineDistance;
		double[] arrXY_1 = rotateVec(x2 - x1, y2 - y1, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(x2 - x1, y2 - y1, -awrad, true, arraow_len);

		double x_3 = x2 - arrXY_1[0]; // (x3,y3)是第一端点
		double y_3 = y2 - arrXY_1[1];
		double x_4 = x2 - arrXY_2[0]; // (x4,y4)是第二端点
		double y_4 = y2 - arrXY_2[1];

		double x_5 = x1 - arrXY_1[0]; // (x5,y5)是第三端点
		double y_5 = y1 - arrXY_1[1];
		double x_6 = x1 - arrXY_2[0]; // (x6,y6)是第四端点
		double y_6 = y1 - arrXY_2[1];

		Double X3 = new Double(x_3);
		int x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		int y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		int x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		int y4 = Y4.intValue();

		Double X5 = new Double(x_5);
		int x5 = X5.intValue();
		Double Y5 = new Double(y_5);
		int y5 = Y5.intValue();
		Double X6 = new Double(x_6);
		int x6 = X6.intValue();
		Double Y6 = new Double(y_6);
		int y6 = Y6.intValue();
		//开始画双线
		//上行
		g.setForeground(SWTResourceManager.getColor(colorValues[0]));
		g.setBackground(SWTResourceManager.getColor(colorValues[0]));
		g.drawLine(x3, y3, x5, y5);
		paintArrow(g, x5, y5, x3, y3);
		//下行
		g.setForeground(SWTResourceManager.getColor(colorValues[1]));
		g.setBackground(SWTResourceManager.getColor(colorValues[1]));
		g.drawLine(x4, y4, x6, y6);
		paintArrow(g, x4, y4, x6, y6);

	}

	public void paintArrow(GC g, int x1, int y1, int x2, int y2) {

		double H = 15; // 箭头高度
		double L = 4; // 底边的一半
		double awrad = Math.atan(L / H); // 箭头角度
		double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
		double[] arrXY_1 = rotateVec(x2 - x1, y2 - y1, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(x2 - x1, y2 - y1, -awrad, true, arraow_len);
		double x_3 = x2 - arrXY_1[0]; // (x3,y3)是第一端点
		double y_3 = y2 - arrXY_1[1];
		double x_4 = x2 - arrXY_2[0]; // (x4,y4)是第二端点
		double y_4 = y2 - arrXY_2[1];

		Double X3 = new Double(x_3);
		int x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		int y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		int x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		int y4 = Y4.intValue();
		//填充箭头
		g.fillPolygon(new int[] { x2, y2, x3, y3, x4, y4 });

	}

	/** */
	/**
	 *取得箭头的绘画范围
	 */

	public double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {

		double mathstr[] = new double[2];
		// 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
		double vx = px * Math.cos(ang) - py * Math.sin(ang);
		double vy = px * Math.sin(ang) + py * Math.cos(ang);
		if (isChLen) {
			double d = Math.sqrt(vx * vx + vy * vy);
			vx = vx / d * newLen;
			vy = vy / d * newLen;
			mathstr[0] = vx;
			mathstr[1] = vy;
		}
		return mathstr;
	}

	public int getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(int lineStyle) {
		this.lineStyle = lineStyle;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public RGB getRgb() {
		return rgb;
	}

	public void setRgb(int red, int green, int blue) {
		this.rgb = new RGB(red, green, blue);
	}

	public int[] getColorValues() {
		return colorValues;
	}

	public void setColorValues(int[] colorValues) {
		this.colorValues = colorValues;
	}

}
