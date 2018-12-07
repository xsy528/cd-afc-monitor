/*
 * 项目: 图形监控组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.graphic2d;

public class Color {

	public static Color BLANK = new Color(0xFF, 0xFF, 0xFF, 100);

	public static Color WIHTE = new Color(0xFF, 0xFF, 0xFF, 100);

	public static Color RED = new Color(0xFF, 0, 0, 100);

	public static Color GREEN = new Color(0, 0xFF, 0, 100);

	protected int r;

	protected int g;

	protected int b;

	protected int a;

	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}
}
