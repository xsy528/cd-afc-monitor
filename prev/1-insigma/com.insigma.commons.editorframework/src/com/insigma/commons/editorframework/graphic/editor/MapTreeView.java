/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TreeItem;

import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.ActionTreeView;
import com.insigma.commons.ui.widgets.Tree;

public class MapTreeView extends ActionTreeView {
	public static final String MAPTREE_SELECTED_MAP_ITEM = "maptree_selected_mapItem";
	private MapEditorView editor;

	IGraphicMapGenerator graphicMapGenerator;

	/**
	 * 通过EditorFrameWork获取地图界面MapEditorView。 <br>
	 * 在创建MapTreeView对象时，将EditorFrameWork对象设置给MapTreeView对象。
	 * 
	 * @return
	 */
	public MapEditorView getEditor() {
		if (editor == null && getFrameWork() != null) {
			return (MapEditorView) getFrameWork().getView(MapEditorView.class);
		}
		return editor;
	}

	public void setEditor(MapEditorView editor) {
		this.editor = editor;
	}

	public void refresh() {
		if (graphicMapGenerator == null) {
			return;
		}
		setEditUI(graphicMapGenerator.isEditUI());
		if (getTree().isDisposed()) {
			return;
		}
		ActionTreeNode selection = getSelection();
		ActionTreeNode root = graphicMapGenerator.getMapTreeRootNode(true);
		if (root == null) {
			return;
		}
		List<ActionTreeNode> list = new ArrayList<ActionTreeNode>();
		list.add(root);

		getTree().removeAll();
		createTree(list);
		setData(MAPTREE_SELECTED_MAP_ITEM, null);
		if (selection == null) {//第一次进入
			getTree().setSelection(getTree().getItem(0));
			if (getTree().getExpandedDepth() == 0) {
				getTree().setExpanded(2);
			} else {
				getTree().setExpanded(getTree().getExpandedDepth());
			}
		} else {
			TreeItem item = find(selection);
			if (item != null) {
				item.setExpanded(true);
				getTree().setExpandedAll(true);
				getTree().setSelection(item);
			}
		}
		getTree().notifyListeners(SWT.Selection, new Event());

		super.refresh();
	}

	public void addTreeNode(MapItem mapitem) {
		ActionTreeNode selection = getSelection();
		if (selection == null) {
			return;
		}
		ActionTreeNode buildTreeNode = graphicMapGenerator.buildTreeNode(mapitem);
		if (buildTreeNode != null) {
			selection.addSubNode(buildTreeNode);
			add(selection, buildTreeNode);
		}

		super.refresh();
	}

	public void removeTreeNode(MapItem mapitem) {
		ActionTreeNode selection = getSelection();
		if (selection == null) {
			return;
		}
		if (selection.getValue().equals(mapitem)) {
			remove(selection);
			navUp();
		} else {
			List<ActionTreeNode> subNodes = selection.getSubNodes();
			for (ActionTreeNode actionTreeNode : subNodes) {
				if (actionTreeNode.getValue().equals(mapitem)) {
					remove(actionTreeNode);
					break;
				}
			}
		}

		super.refresh();
	}

	/**
	 * 地图界面
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public MapTreeView(Composite arg0, int arg1) {
		super(arg0, arg1);
		setLayout(new FillLayout());
		setText("地图列表");
		setIcon("/com/insigma/commons/ui/shape/maplist.png");
	}

	public void navDown(MapItem map) {
		Tree tree = getTree();
		TreeItem[] items = tree.getSelection();
		if (items.length == 1) {
			TreeItem item = items[0];
			for (TreeItem child : item.getItems()) {
				ActionTreeNode data = (ActionTreeNode) child.getData();
				if (data != null && data.getValue() == map) {
					tree.setSelection(child);
					child.setExpanded(true);
					tree.notifyListeners(SWT.Selection, new Event());
				}
			}
		}
	}

	public void navUp() {
		Tree tree = getTree();
		TreeItem[] items = getTree().getSelection();
		if (items.length == 1) {
			TreeItem item = items[0];
			TreeItem parentItem = item.getParentItem();
			if (parentItem != null) {
				getTree().setSelection(parentItem);
				tree.notifyListeners(SWT.Selection, new Event());
			}
		}
	}

	/**
	 * @return the graphicMapGenerator
	 */
	public IGraphicMapGenerator getGraphicMapGenerator() {
		return graphicMapGenerator;
	}

	/**
	 * @param graphicMapGenerator the graphicMapGenerator to set
	 */
	public void setGraphicMapGenerator(IGraphicMapGenerator graphicMapGenerator) {
		this.graphicMapGenerator = graphicMapGenerator;
	}

}
