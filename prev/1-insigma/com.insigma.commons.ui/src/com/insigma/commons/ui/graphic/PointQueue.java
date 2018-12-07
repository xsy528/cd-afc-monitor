/*
 * 项目: 监控运行图表组件
 * 版本: 1.0
 * 作者  姜旭锋
 * Email: jiangxufeng@zdwxjd.com

 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.graphic;

import java.util.LinkedList;
import java.util.List;

public class PointQueue {

	private int lines = 0;

	private int maxSize = 300;

	private List<YPoints> pointList;

	private List<String> lineNames;

	private int Ymax = 0;

	public PointQueue() {
		pointList = new LinkedList<YPoints>();
	}

	public void addPoint(YPoints point) {
		if (pointList.size() >= maxSize) {
			pointList.remove(0);
		}
		pointList.add(point);
		lines = point.size();
		if (point.getYmax() > Ymax) {
			Ymax = point.getYmax();
		}
	}

	public void clear() {
		pointList.clear();
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public List<YPoints> getPointList() {
		return pointList;
	}

	public void setPointList(List<YPoints> pointList) {
		this.pointList = pointList;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public int size() {
		return pointList.size();
	}

	public int getYmax() {
		return Ymax;
	}

	public void setYmax(int ymax) {
		Ymax = ymax;
	}

	public long getLatest() {
		if (pointList != null && pointList.size() != 0) {
			return pointList.get(pointList.size() - 1).getXpos();
		}
		return 0;
	}

	public void setLineNames(List<String> lineNames) {
		this.lineNames = lineNames;
	}

	public List<String> getLineNames() {
		return lineNames;
	}

}
