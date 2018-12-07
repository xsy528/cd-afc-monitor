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
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Table")
public class TableStyle {

	@XStreamAsAttribute
	private String name = "NO_NAME";

	private List<Column> Columns = new ArrayList<Column>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getColumns() {
		return Columns;
	}

	public void setColumns(List<Column> columns) {
		this.Columns = columns;
	}
}
