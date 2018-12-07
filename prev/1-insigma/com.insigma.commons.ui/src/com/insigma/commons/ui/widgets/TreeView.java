/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

public class TreeView extends Tree implements IControl {

	public TreeView(Composite arg0, int arg1) {
		super(arg0, arg1 | SWT.FULL_SELECTION);
		setLinesVisible(true);
		setHeaderVisible(true);
	}

	/**
	 * @param supItem
	 *            父项数据
	 * @param subItemList
	 *            子项数据集合，无数据可以为null
	 * @param defaultChecked
	 *            如果带check框，设置选择状态
	 * @param retractive
	 *            是否缩进显示
	 */
	public void addRow(Object[] supItem, List<Object[]> subItemList, boolean defaultChecked, boolean retractive) {
		TreeItem supitem = new TreeItem(this, SWT.NONE);
		supitem.setChecked(defaultChecked);
		for (int i = 0; i < supItem.length; i++) {
			if (supItem[i] != null) {
				supitem.setText(i, supItem[i].toString());
			} else {
				supitem.setText(i, "无");
			}
		}
		for (Object[] subItem : subItemList) {
			if (subItem != null) {
				TreeItem subitem = new TreeItem(supitem, SWT.NONE);
				subitem.setChecked(true);
				for (int i = 0; i < subItem.length; i++) {
					if (subItem[i] != null) {
						if (retractive) {
							subitem.setText(supItem.length + i, subItem[i].toString());
						} else {
							subitem.setText(i, subItem[i].toString());
						}
					} else {
						if (retractive) {
							subitem.setText(supItem.length + i, "无");
						} else {
							subitem.setText(i, "无");
						}
					}
				}
			}
		}
	}

	/**
	 * @param supItem
	 *            父项数据
	 * @param subItems
	 *            子项数据集合，无数据可以为null
	 */
	public void addRow(Object[] supItem, Object[] subItems) {
		TreeItem supitem = new TreeItem(this, SWT.NONE);
		supitem.setChecked(true);
		for (int i = 0; i < supItem.length; i++) {
			if (supItem[i] != null) {
				supitem.setText(i, supItem[i].toString());
			} else {
				supitem.setText(i, "无");
			}
		}
		if (subItems != null) {
			for (Object subItem1 : subItems) {
				if (subItem1 != null && subItem1 instanceof Object[]) {
					Object[] subItem = (Object[]) subItem1;
					TreeItem subitem = new TreeItem(supitem, SWT.NONE);
					subitem.setChecked(true);
					for (int i = 0; i < subItem.length; i++) {
						if (subItem[i] != null) {
							subitem.setText(supItem.length + i, subItem[i].toString());
						} else {
							subitem.setText(supItem.length + i, "无");
						}
					}
				} else {
					TreeItem subitem = new TreeItem(supitem, SWT.NONE);
					subitem.setChecked(true);
					for (int i = 0; i < subItems.length; i++) {
						if (subItems[i] != null) {
							subitem.setText(supItem.length + i, subItems[i].toString());
						} else {
							subitem.setText(supItem.length + i, "无");
						}
					}
					break;
				}
			}
		}
	}

}
