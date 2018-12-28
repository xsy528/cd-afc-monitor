package com.insigma.commons.ui.widgets.ktable.listener;

import java.util.List;

public interface KTablePopMenuListener {
	public String getName();

	/**
	 * 右键弹出框处理
	 * @param col 列号
	 * @param row 行号
	 * @param list table数据
	 * @return 是否需要刷新table
	 */
	public boolean MenuClicked(int col, int row, List<Object> list);
}
