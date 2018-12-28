package com.insigma.commons.editorframework.iframework;

import java.util.Calendar;
import java.util.Date;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.function.form.FormFunction;
import com.insigma.commons.framework.function.handler.FunctionHanlderManager;
import com.insigma.commons.ui.tree.ITreeProvider;

/**
 * 创建时间 2010-9-30 下午04:44:26 <br>
 * 描述: 线路地图Function<br>
 * Ticket：
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */

public class MapFunction extends FormFunction {

	static {
		FunctionHanlderManager.addFunctionHandler(MapFunction.class, new MapFunctionHandler());
	}

	private String mapTitle;

	private ITreeProvider treeProvider;

	private String treeLabel = "网络拓扑结构";

	private int treeDepth;

	protected Action openAction;

	protected Date date;

	protected String timeSection;

	private int timePeriod = 5;//时间间隔(分)
	/**时间间隔个数1、2、3*/
	protected int timePeriodCount = 1;
	/**往前推移时段数*/
	protected int difference = 0;

	private int refreshInterval = 0;

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	public String getTreeLabel() {
		return treeLabel;
	}

	public void setTreeLabel(String treeLabel) {
		this.treeLabel = treeLabel;
	}

	public int getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	public String getMapTitle() {
		return mapTitle;
	}

	public void setMapTitle(String mapTitle) {
		this.mapTitle = mapTitle;
	}

	public Action getOpenAction() {
		return openAction;
	}

	public void setOpenAction(Action openAction) {
		this.openAction = openAction;
	}

	public Date getDate() {
		Calendar calendar = Calendar.getInstance();
		Date dateTime = new Date(calendar.getTimeInMillis() - (difference + 1) * timePeriod * 60 * 1000);
		return dateTime;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTimeSection() {
		return getTimePeriod(timePeriod, timePeriodCount);
	}

	public void setTimeSection(String timeSection) {
		this.timeSection = timeSection;
	}

	public int getTimePeriodCount() {
		return timePeriodCount;
	}

	public void setTimePeriodCount(int timePeriodCount) {
		this.timePeriodCount = timePeriodCount;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}

	public int getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	public String getTimePeriod(int timePeriod, int timePeriodCount) {
		Calendar calendar = Calendar.getInstance();
		Date dateTime = new Date(calendar.getTimeInMillis() - difference * timePeriod * 60 * 1000);
		calendar.setTime(dateTime);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minite = calendar.get(Calendar.MINUTE);
		minite = minite - minite % timePeriod;
		int period = timePeriod * timePeriodCount;
		if (minite >= period) {
			return String.format("%02d:%02d-%02d:%02d", hour, minite - period, hour, minite);
		} else {
			return String.format("%02d:%02d-%02d:%02d", hour == 0 ? 23 : (hour - 1), 60 - (period - minite), hour,
					minite);
		}

	}

}
