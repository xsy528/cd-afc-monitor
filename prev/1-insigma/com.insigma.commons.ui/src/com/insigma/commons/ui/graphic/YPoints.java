/*
 * 项目: 监控运行图表组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.graphic;

public class YPoints {

	private long xpos;

	private int[] points;

	public YPoints(int size) {
		points = new int[size];
	}

	public int getY(int index) {
		if (index >= 0 && index < points.length) {
			return points[index];
		}
		return 0;
	}

	public void setY(int index, int value) {
		if (index >= 0 && index < points.length) {
			points[index] = value;
		}
	}

	public long getXpos() {
		return xpos;
	}

	public void setXpos(long xpos) {
		this.xpos = xpos;
	}

	public int size() {
		if (points != null) {
			return points.length;
		}
		return 0;
	}

	public int getYmax() {
		int max = 0;
		if (points != null) {
			for (int i = 0; i < points.length; i++) {
				if (points[i] > max) {
					max = points[i];
				}
			}
		}
		return max;
	}

	public String toString() {
		String str;
		str = xpos + ":";
		for (int i = 0; i < points.length; i++) {
			str = str + points[i] + ",";
		}
		return str;
	}
}
