package com.insigma.commons.editorframework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;

public class ActionTreeNode {

	private String name;

	private String expendIcon;

	private String closeIcon;

	private List<Action> selectionAction;

	private List<Action> toolAction;

	private List<Action> createAction;

	private List<Action> contextAction;

	private List<ActionTreeNode> subNodes;

	private ActionTreeNode parentNode;

	private Object value;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	public boolean isLeaf = false;//是否为叶子节点

	/**
	 * 是否修改
	 */
	private boolean isChanged = false;

	private long id;

	public ActionTreeNode(String name) {
		this.name = name;
		subNodes = new ArrayList<ActionTreeNode>();
		selectionAction = new ArrayList<Action>();
		createAction = new ArrayList<Action>();
		toolAction = new ArrayList<Action>();
		contextAction = new ArrayList<Action>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpendIcon() {
		return expendIcon;
	}

	public void setExpendIcon(String expendIcon) {
		this.expendIcon = expendIcon;
		if (closeIcon == null) {
			this.closeIcon = expendIcon;
		}
	}

	public String getCloseIcon() {
		return closeIcon;
	}

	public void setCloseIcon(String closeIcon) {
		this.closeIcon = closeIcon;
		if (expendIcon == null) {
			expendIcon = closeIcon;
		}
	}

	public List<ActionTreeNode> getSubNodes() {
		return subNodes;
	}

	public void addSubNode(ActionTreeNode subNode) {
		boolean ok = subNodes.add(subNode);
		if (ok) {
			subNode.setParentNode(this);
		}
	}

	public boolean removeSubNode(List<ActionTreeNode> subNode) {
		boolean remove = subNodes.removeAll(subNode);
		return remove;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Iterator<String> getDataMapKeys() {
		return dataMap.keySet().iterator();
	}

	public Object getData(String key) {
		return dataMap.get(key);
	}

	public void setData(String key, Object value) {
		dataMap.put(key, value);
	}

	public List<Action> getSelectionAction() {
		return selectionAction;
	}

	public void addSelectionAction(Action selectionAction) {
		this.selectionAction.add(selectionAction);
	}

	public List<Action> getToolAction() {
		return toolAction;
	}

	public void addToolAction(Action toolAction) {
		this.toolAction.add(toolAction);
	}

	public List<Action> getContextAction() {
		return contextAction;
	}

	public void setContextAction(List<Action> contextAction) {
		this.contextAction = contextAction;
	}

	public ActionTreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(ActionTreeNode parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * @return the isChanged
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * @param isChanged
	 *            the isChanged to set
	 */
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	/**
	 * @param actionTreeView
	 * @param actionTreeNode
	 * @param isChanged
	 */
	public void toggleChanged(final ActionTreeView actionTreeView) {
		if (actionTreeView == null) {
			return;
		}
		if (isChanged) {
			doUnChanged(actionTreeView);
		} else {
			doChanged(actionTreeView);
		}
	}

	public void doChanged(final ActionTreeView actionTreeView) {
		if (!isChanged()) {
			ActionTreeNode pnode = this;
			TreeItem _item = actionTreeView.find(pnode);
			if (_item != null) {
				_item.setText("* " + _item.getText());
				_item.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
				pnode.setChanged(true);
			}

			pnode = pnode.getParentNode();
			if (pnode != null) {
				pnode.doChanged(actionTreeView);
			}
		}
	}

	public void doUnChanged(final ActionTreeView actionTreeView) {
		if (isChanged()) {
			ActionTreeNode pnode = this;

			List<ActionTreeNode> snodes = getSubNodes();
			if (snodes != null && snodes.size() > 0) {
				for (ActionTreeNode snode : snodes) {
					snode.doUnChanged(actionTreeView);
				}
			}

			TreeItem _item = actionTreeView.find(pnode);
			if (_item != null) {
				_item.setText(this.getName());
				_item.setForeground(null);
				pnode.setChanged(false);
			}
			pnode = pnode.getParentNode();
			if (pnode != null) {
				List<ActionTreeNode> psnode = pnode.getSubNodes();
				boolean ok = true;
				for (ActionTreeNode psn : psnode) {
					// 有一个未保存则退出
					if (psn.isChanged()) {
						ok = false;
						break;
					}
				}
				if (ok) {
					pnode.doUnChanged(actionTreeView);
				}
			} else {
				List<ActionTreeNode> list = getSubNodes();
				for (ActionTreeNode node : list) {
					if (node.isChanged) {
						return;
					}
				}
				actionTreeView.setChanged(false);
			}
		}
	}

	/**
	 * @return the createAction
	 */
	public List<Action> getCreateAction() {
		return createAction;
	}

	/**
	 * @param createAction the createAction to set
	 */
	public void addCreateAction(Action createAction) {
		this.createAction.add(createAction);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
