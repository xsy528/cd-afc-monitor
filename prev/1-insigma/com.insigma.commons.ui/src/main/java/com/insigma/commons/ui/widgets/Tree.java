/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class Tree extends org.eclipse.swt.widgets.Tree implements IControl {

	private TreeSelectListener treeListener = new TreeSelectListener();

	private int height = 20;

	private int expandedDepth;

	public Tree(Composite arg0, int arg1) {
		super(arg0, arg1);
		addSelectionListener(treeListener);
		addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event arg0) {
				arg0.height = height;
			}
		});
	}

	public class TreeSelectListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if ((e.item instanceof TreeItem) && (e.detail == SWT.CHECK)) {
				((TreeItem) e.item).setGrayed(false);
				checkAllSub((TreeItem) e.item);
				changeAllParent((TreeItem) e.item);
			}
		}
	}

	public void removeTreeSelectionListner() {
		removeSelectionListener(treeListener);
	}

	public int getCheckedCount() {
		int count = 0;
		for (TreeItem item : getItems()) {
			count += getCheckedCount(item);
		}
		return count;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	private int getCheckedCount(TreeItem item) {
		int count = 0;
		for (TreeItem subItem : item.getItems()) {
			count += getCheckedCount(subItem);
		}
		if (item.getChecked()) {
			count++;
		}
		return count;
	}

	private final void changeAllParent(TreeItem item) {
		if (item.getParentItem() == null) {
			return;
		}
		TreeItem par = item.getParentItem();
		int count = 0;
		for (TreeItem subItem : par.getItems()) {
			if (subItem.getChecked()) {
				count++;
			}
		}
		if (count == 0) {
			par.setChecked(false);
		} else if (count == par.getItemCount()) {
			par.setChecked(true);
			par.setGrayed(false);
		} else {
			par.setChecked(true);
			par.setGrayed(true);
		}
		changeAllParent(par);
	}

	protected void checkSubclass() {
	}

	public void reset() {
	}

	/**
	 * 展开一层节点
	 * 
	 * @param expanded
	 */
	public void setExpanded(boolean expanded) {
		TreeItem[] tree = getItems();
		if (tree != null) {
			for (TreeItem element : tree) {
				element.setExpanded(expanded);
			}
		}
	}

	/**
	 * 展开expandedDepth层节点
	 * 
	 * @param expandedDepth
	 *            展开节点层数
	 */
	public void setExpanded(int expandedDepth) {
		TreeItem[] tree = getItems();
		ExpandedNode(tree, 0, expandedDepth);
	}

	public void ExpandedNode(TreeItem[] tree, int currentDepth, int expandedDepth) {
		if (tree != null) {
			currentDepth++;
			if ((currentDepth < expandedDepth) || expandedDepth <= 0) {
				for (TreeItem element : tree) {
					element.setExpanded(true);
					if (element.getItems().length > 0) {
						TreeItem[] items2 = element.getItems();
						ExpandedNode(items2, currentDepth, expandedDepth);
					}
				}
			}
		}
	}

	/**
	 * 展开所有节点
	 * 
	 * @param expanded
	 */
	public void setExpandedAll(boolean expanded) {
		TreeItem[] tree = getItems();
		ExpandedNode(tree);
	}

	public void ExpandedNode(TreeItem[] tree) {
		if (tree != null) {
			for (TreeItem element : tree) {
				element.setExpanded(true);
				if (element.getItems().length > 0) {
					TreeItem[] items2 = element.getItems();
					ExpandedNode(items2);
				}
			}
		}
	}

	public void setColumn(String[] strs) {
		if (strs == null) {
			return;
		}
		for (String element : strs) {
			TreeColumn column = new TreeColumn(this, SWT.NONE);
			column.setText(element);
			column.setWidth(100);
		}
	}

	public TreeItem addRow(String[] row) {
		TreeItem item = new TreeItem(this, SWT.NONE);
		item.setText(row);
		return item;
	}

	public void setWeight(int[] weight) {
		int i = 0;
		for (TreeColumn column : this.getColumns()) {
			column.setWidth(weight[i]);
			i++;
		}
	}

	/**
	 * 如果带check框，设置选择状态默认选择
	 * 
	 * @param text
	 *            tree的显示数据
	 * @param data
	 *            tree数据对应的实体
	 */
	public void addRow(Object[] text, Object data) {
		addRow(text, data, true);
	}

	/**
	 * @param supItem
	 *            父项数据
	 * @param subItem
	 *            子项数据，无数据可以为null
	 */
	public void addRow(Object[] supItem, Object[] subItem) {
		TreeItem supitem = new TreeItem(this, SWT.NONE);
		supitem.setChecked(true);
		for (int i = 0; i < supItem.length; i++) {
			if (supItem[i] != null) {
				supitem.setText(i, supItem[i].toString());
			} else {
				supitem.setText(i, "无");
			}
		}
		if (subItem != null) {
			TreeItem subitem = new TreeItem(supitem, SWT.NONE);
			subitem.setChecked(true);
			for (int i = 0; i < subItem.length; i++) {
				if (subItem[i] != null) {
					subitem.setText(supItem.length + i, subItem[i].toString());
				} else {
					subitem.setText(supItem.length + i, "无");
				}
			}
		}

	}

	/**
	 * @param text
	 *            tree的显示数据
	 * @param data
	 *            tree数据对应的实体
	 * @param flag
	 *            如果带check框，设置选择状态
	 */
	public void addRow(Object[] text, Object data, boolean flag) {
		TreeItem item = new TreeItem(this, SWT.NONE);
		item.setChecked(flag);
		item.setData(data);
		for (int i = 0; i < text.length; i++) {
			if (text[i] != null) {
				item.setText(i, text[i].toString());
			} else {
				item.setText(i, "无");
			}
		}
	}

	public void setAlignment(int index, int alignment) {
		getColumns()[index].setAlignment(alignment);
	}

	public TreeItem addRow(String[] text, Color color) {
		TreeItem item = new TreeItem(this, SWT.NONE);
		item.setForeground(color);
		item.setText(text);
		return item;
	}

	/**
	 * 清楚checkBox的所有Items
	 */
	public void clearCheck() {
		TreeItem[] items = getItems();
		setCheck(items, false);
	}

	/**
	 *选择checkBox的所有Items
	 */
	public void checkAll() {
		TreeItem[] items = getItems();
		setCheck(items, true);
	}

	/**
	 * @param items
	 */
	public void setCheck(TreeItem[] items, boolean flag) {
		for (TreeItem element : items) {
			if (element.getItems().length > 0) {
				TreeItem[] items2 = element.getItems();
				setCheck(items2, flag);
			}
			element.setChecked(flag);
		}
	}

	protected final void checkAllSub(TreeItem item) {
		TreeItem[] children = item.getItems();
		if (children == null) {
			return;
		}
		for (TreeItem subItem : item.getItems()) {
			boolean isCheck = item.getChecked();
			subItem.setChecked(isCheck);
			subItem.setGrayed(!isCheck);
			checkAllSub(subItem);
		}
	}

	public void selectAllItem(TreeItem[] items, boolean flag) {
		for (TreeItem item : items) {
			item.setChecked(flag);
			selectAllItem(item.getItems(), flag);
		}
	}

	/**
	 * 用于选择监听事件
	 * 
	 * @param item
	 */
	public void SelectChecked(TreeItem item) {
		for (TreeItem childItem : item.getItems()) {
			childItem.setChecked(item.getChecked());
			SelectChecked(childItem);
		}
	}

	/**
	 * 设置节点状态：选取时展开一层树形
	 * 
	 * @param item
	 */
	public void setItemSelected(TreeItem item) {
		item.setGrayed(false);
		checkAllSub(item);
		changeAllParent(item);
		if (item.getChecked()) {
			item.setExpanded(true);
			if (null != item.getParentItem()) {
				item.getParentItem().setExpanded(true);
			}
		}
	}

	public int getExpandedDepth() {
		return expandedDepth;
	}

	public void setExpandedDepth(int expandedDepth) {
		this.expandedDepth = expandedDepth;
	}
}
