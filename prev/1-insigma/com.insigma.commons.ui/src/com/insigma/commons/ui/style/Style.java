/*
 * 日期：2010-7-12
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.commons.ui.style;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Style")
public class Style {

	private List<TableStyle> Tables = new ArrayList<TableStyle>();

	public List<TableStyle> getTable() {
		return Tables;
	}

	public void setTable(List<TableStyle> table) {
		this.Tables = table;
	}
}
