package com.insigma.commons.ui.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import com.insigma.commons.ui.widgets.IInputControl;
import com.insigma.commons.ui.widgets.Tree;

public class ObjectTree extends Tree implements IInputControl {

	private ITreeProvider treeProvider;

	private int depth;

	private int cur_depth;

	private int maxDepth;

	private Map<Integer, Boolean> isNodeType;

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
		if (treeProvider == null) {
			return;
		}
		TreeNode node = treeProvider.getTree();
		if (node == null) {
			return;
		}
		maxDepth = 1;
		while (null != node.getChilds() && node.getChilds().size() > 0) {
			node = node.getChilds().get(0);
			maxDepth++;
		}
		buildTree(treeProvider.getTree(), this);
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		if (treeProvider == null || depth < 0) {
			return;
		}
		this.depth = depth;
		removeAll();
		buildTree(treeProvider.getTree(), this);
	}

	public ObjectTree(Composite arg0, int arg1) {
		super(arg0, arg1);
	}

	public void buildTree(TreeNode node, Tree tree) {
		if (node == null) {
			return;
		}
		TreeItem item = new TreeItem(tree, SWT.CHECK);
		item.setText(node.getText());
		item.setData(node.getKey());
		if (node.isChecked()) {
			item.setChecked(true);
		}

		cur_depth = 1;

		if (node.getKey() == null) {
			this.putIsNodeType(cur_depth, true);
		} else {
			this.putIsNodeType(cur_depth, false);
		}

		buildTree(node, item);
	}

	public void buildTree(TreeNode node, TreeItem parent) {
		if (node.getChilds() == null) {
			return;
		}
		if (depth == 0 || cur_depth < depth) {
			for (TreeNode child : node.getChilds()) {
				TreeItem item = new TreeItem(parent, SWT.CHECK);
				item.setText(child.getText());
				item.setData(child.getKey());
				if (child.isChecked()) {
					item.setChecked(true);
				}

				cur_depth++;
				if (child.getKey() == null) {
					this.putIsNodeType(cur_depth, true);
				} else {
					this.putIsNodeType(cur_depth, false);
				}
				buildTree(child, item);
				cur_depth--;
			}

		}
	}

	public List<Object> getChecked(Class<?> cls) {
		List<Object> selection = new ArrayList<Object>();
		for (TreeItem item : this.getItems()) {
			if (item.getChecked() && item.getData() != null) {
				if (cls != null) {
					if (item.getData().getClass() == cls) {
						selection.add(item.getData());
					}
				} else {
					selection.add(item.getData());
				}
			}
			walk(item, selection, cls);
		}
		return selection;
	}

	public List<Object> getChecked() {
		return getChecked(null);
	}

	protected void walk(TreeItem parent, List<Object> selection, Class<?> cls) {
		for (TreeItem item : parent.getItems()) {
			if (item.getChecked() && item.getData() != null) {
				if (cls != null && item.getData().getClass() == cls) {
					selection.add(item.getData());
				} else {
					selection.add(item.getData());
				}
			}
			if (item.getItemCount() > 0) {
				walk(item, selection, cls);
			}
		}
	}

	protected void setChecked(TreeItem item, List<Object> selections) {
		item.setChecked(false);
		for (Object selection : selections) {
			if (item.getText().equals(selection) || (item.getData() != null && item.getData().equals(selection))) {
				item.setChecked(true);
			}
		}
		for (TreeItem subitem : item.getItems()) {
			setChecked(subitem, selections);
		}
	}

	public void setChecked(List<Object> texts) {
		if (texts != null) {
			for (TreeItem item : getItems()) {
				setChecked(item, texts);
			}
		}
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public Object getObjectValue() {
		return getChecked();
	}

	public void setObjectValue(Object value) {

	}

	public Map<Integer, Boolean> getIsNodeType() {
		return isNodeType;
	}

	public void setIsNodeType(Map<Integer, Boolean> isNodeType) {
		this.isNodeType = isNodeType;
	}

	public Boolean putIsNodeType(Integer arg0, Boolean arg1) {
		if (isNodeType == null) {
			isNodeType = new HashMap<Integer, Boolean>();
		}

		return isNodeType.put(arg0, arg1);
	}
}
