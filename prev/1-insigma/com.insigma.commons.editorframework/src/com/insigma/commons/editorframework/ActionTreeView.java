/* 
 * 项目:    Insigma 编辑器框架
 * 版本:  1.0
 * 日期:  2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import com.insigma.commons.application.Application;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.log.Logs;
import com.insigma.commons.ui.widgets.Tree;
import com.swtdesigner.SWTResourceManager;

public class ActionTreeView extends FrameWorkView {

	private Tree tree;

	private TreeItem preTreeItem; // 点击树节点前保持上次点击节点

	private TreeListener treeListener;

	/**
	 * 是否修改
	 */
	private boolean isChanged = false;

	private boolean isEditUI = false;

	private boolean isParameterEditor = false;

	public Tree getTree() {
		return tree;
	}

	private List<ActionTreeNode> nodes;

	public List<ActionTreeNode> getNodes() {
		return nodes;
	}

	public void createTree(List<ActionTreeNode> nodes) {
		this.nodes = nodes;
		for (ActionTreeNode node : nodes) {
			TreeItem item = new TreeItem(tree, SWT.NONE);

			if (isEditUI()) {
				if (node.getExpendIcon() != null) {
					item.setImage(SWTResourceManager.getImage(ActionTreeView.class, node.getExpendIcon()));
				}
			} else {
				String imagePath = null;
				if (node.getValue() instanceof MapItem) {
					MapItem mapItem = (MapItem) node.getValue();
					long id = (long) mapItem.getMapId();
					imagePath = Application.getImagePath(id);

				}
				if (imagePath != null) {
					item.setImage(SWTResourceManager.getImage(ActionTreeView.class, imagePath));

				} else if (node.getExpendIcon() != null) {
					item.setImage(SWTResourceManager.getImage(ActionTreeView.class, node.getExpendIcon()));
				}
			}

			item.setText(node.getName());
			item.setData(node);
			nofityCreateAction(node);
			if (node.getSubNodes() != null) {
				buildTree(node.getSubNodes(), item);
			}
		}
	}

	/**
	 * 
	 */
	private void nofityCreateAction(ActionTreeNode node) {
		List<Action> actions = node.getCreateAction();
		for (Action action : actions) {
			if (action != null) {
				try {
					ActionContext context = new ActionContext(action);
					context.setFrameWork(getFrameWork());
					context.setData(node);
					action.getHandler().perform(context);
				} catch (Exception e) {
					Logs.get().error("执行createAction异常", e);
				}
			}
		}
	}

	public TreeItem find(ActionTreeNode node) {
		return this.find(tree.getItems(), node);
	}

	public TreeItem find(TreeItem item, ActionTreeNode node) {
		if (item.getData() != null && item.getData().equals(node)) {
			return item;
		}
		return this.find(item.getItems(), node);
	}

	protected TreeItem find(TreeItem[] items, ActionTreeNode node) {
		for (TreeItem item : items) {
			if (!isParameterEditor) {
				if (item.getData() != null && ((ActionTreeNode) item.getData()).getId() == node.getId()) {
					return item;
				} else {
					TreeItem subitem = find(item.getItems(), node);
					if (subitem != null) {
						return subitem;
					}
				}
			} else {
				if (item.getData() != null && item.getData().equals(node)) {
					return item;
				} else {
					TreeItem subitem = find(item.getItems(), node);
					if (subitem != null) {
						return subitem;
					}
				}

			}
		}
		return null;
	}

	public void remove(ActionTreeNode node) {
		ActionTreeNode pnode = node.getParentNode();
		if (pnode != null) {
			pnode.getSubNodes().remove(node);
		}
		if (pnode.getSubNodes().size() == 0) {
			pnode.doUnChanged(this);
		}
		TreeItem item = find(tree.getItems(), node);
		if (item != null) {
			item.dispose();
		}
	}

	public void add(ActionTreeNode parent, ActionTreeNode node, boolean checked) {
		TreeItem parentItem = find(tree.getItems(), parent);
		if (parentItem != null) {
			TreeItem item = new TreeItem(parentItem, SWT.NONE);
			if (node.getExpendIcon() != null) {
				item.setImage(SWTResourceManager.getImage(ActionTreeView.class, node.getExpendIcon()));
			}
			item.setText(node.getName());
			item.setData(node);
			if (checked) {
				item.setChecked(true);
			}
			if (node.getSubNodes() != null) {
				buildTree(node.getSubNodes(), item);
			}
		}
	}

	public void add(ActionTreeNode parent, ActionTreeNode node) {
		this.add(parent, node, false);
	}

	public ActionTreeNode getSelection() {
		if (tree.isDisposed()) {
			return null;
		}
		if (tree.getSelectionCount() > 0 && tree.getSelection()[0].getData() != null) {
			ActionTreeNode node = (ActionTreeNode) tree.getSelection()[0].getData();
			return node;
		}
		return null;
	}

	public TreeItem getSelectionTreeItem() {
		if (tree.getSelectionCount() > 0) {
			return tree.getSelection()[0];
		}
		return null;
	}

	public ActionTreeView(Composite arg0, int arg1) {
		super(arg0, arg1);
		setLayout(new FillLayout());
		tree = new Tree(this, SWT.BORDER);
		setText("");
		setIcon("/com/insigma/commons/ui/shape/maplist.png");

		tree.addTreeListener(new TreeListener() {

			private ActionTreeNode getNode(TreeEvent arg0) {
				TreeItem item = (TreeItem) arg0.item;
				if (item != null && item.getData() != null && item.getData() instanceof ActionTreeNode) {
					return (ActionTreeNode) item.getData();
				}
				return null;
			}

			public void treeCollapsed(TreeEvent arg0) {
				ActionTreeNode node = getNode(arg0);
				if (node != null) {
					((TreeItem) arg0.item)
							.setImage((SWTResourceManager.getImage(ActionTreeView.class, node.getCloseIcon())));
				}
			}

			public void treeExpanded(TreeEvent arg0) {
				ActionTreeNode node = getNode(arg0);
				if (node != null) {
					((TreeItem) arg0.item)
							.setImage((SWTResourceManager.getImage(ActionTreeView.class, node.getExpendIcon())));
				}
			}
		});
		tree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (tree.getSelectionCount() > 0 && tree.getSelection()[0].getData() != null) {
					ActionTreeNode node = (ActionTreeNode) tree.getSelection()[0].getData();
					// 判断节点整个对象是否有效
					if ((null != preTreeItem && !preTreeItem.isDisposed()) && null != preTreeItem.getData()) {

						ActionTreeNode treeItemCom = (ActionTreeNode) preTreeItem.getData();
						Object value = treeItemCom.getValue();
						//						if (treeItemCom.isChanged() && treeItemCom instanceof Component) {//值有改变的时候验证
						//							String validateMessage = ValidationUtil.objectValidateMessage(((Component) treeItemCom)
						//									.getValue());
						//							if (null != validateMessage) {
						//								MessageForm.Message("提示", validateMessage + "\n====== 请修改标红的字段！ ======",
						//										SWT.ICON_WARNING);
						//								tree.setSelection(preTreeItem);
						//								return;
						//							}
						//						} else if (treeItemCom.isChanged() && value instanceof MapItem) {
						//							MapItem map = (MapItem) value;
						//							if (MessageForm.Query("确认", "地图信息已经更改，请先保存更改信息！ ", SWT.OK | SWT.CANCEL
						//									| SWT.ICON_INFORMATION) == SWT.OK) {
						//								tree.setSelection(preTreeItem);
						//								return;
						//							} else {
						//								//TODO map.getDataState().undo();
						//							}
						//						}

					}
					if (node.getSelectionAction() != null) {
						for (Action action : node.getSelectionAction()) {
							if (action.IsEnable()) {
								action.setFrameWork(getFrameWork());
								ActionContext context = new ActionContext(action);
								context.setFrameWork(getFrameWork());
								action.getHandler().perform(context);
								preTreeItem = tree.getSelection()[0];
							}
						}

					}
					if (getFrameWork() != null) {
						getFrameWork().updateToolBar();
					}
				}
			}
		});

		if (treeListener != null) {
			tree.addTreeListener(treeListener);
		}
	}

	public void buildTree(List<ActionTreeNode> maps, TreeItem parent) {
		if (parent == null) {
			return;
		}
		if (parent.getItemCount() > 0) {
			parent.removeAll();
		}

		for (ActionTreeNode node : maps) {
			TreeItem item = new TreeItem(parent, SWT.NONE);

			if (isEditUI()) {
				if (node.getExpendIcon() != null) {
					item.setImage(SWTResourceManager.getImage(ActionTreeView.class, node.getExpendIcon()));
				}
			} else {
				String imagePath = null;
				if (node.getValue() instanceof MapItem) {
					MapItem mapItem = (MapItem) node.getValue();
					int id = (int) mapItem.getMapId();
					imagePath = Application.getImagePath(id);

				}

				if (imagePath != null) {
					item.setImage(SWTResourceManager.getImage(ActionTreeView.class, imagePath));

				} else if (node.getExpendIcon() != null) {
					item.setImage(SWTResourceManager.getImage(ActionTreeView.class, node.getExpendIcon()));
				}
			}

			item.setText(node.getName());
			item.setData(node);

			if (node.getSubNodes() != null && node.getSubNodes().size() > 0) {
				buildTree(node.getSubNodes(), item);
			}
			if (!node.isLeaf) {
				TreeItem lazyItem = new TreeItem(item, SWT.NONE);
				lazyItem.setExpanded(false);
				lazyItem.setText("正在加载...");
			}
			nofityCreateAction(node);
		}

	}

	public void redrawTree() {
		TreeItem[] items = tree.getItems();
		if (items != null && items.length > 0) {
			for (TreeItem item : items) {
				redrawItem(item);
			}
		}
		super.redraw();
	}

	private void redrawItem(TreeItem item) {
		ActionTreeNode node = (ActionTreeNode) item.getData();
		if (node == null) {
			return;
		}
		item.setImage(SWTResourceManager.getImage(ActionTreeView.class, node.getExpendIcon()));

		TreeItem[] items = item.getItems();
		if (items != null && items.length > 0) {
			for (TreeItem it : items) {
				redrawItem(it);
			}
		}
	}

	public void setTreeListener(TreeListener treeListener) {
		this.treeListener = treeListener;
	}

	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public boolean isEditUI() {
		return isEditUI;
	}

	public void setEditUI(boolean isEditUI) {
		this.isEditUI = isEditUI;
	}

	public void setParameterEditor(boolean isParameterEditor) {
		this.isParameterEditor = isParameterEditor;
	}

}
