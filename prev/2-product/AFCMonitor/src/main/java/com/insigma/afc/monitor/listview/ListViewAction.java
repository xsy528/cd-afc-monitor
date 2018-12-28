package com.insigma.afc.monitor.listview;

import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.editorframework.view.ListView;

public class ListViewAction extends Action {
	protected ListView view;

	public ListViewAction(ListView view) {
		super("ListViewAction");
		this.view = view;
		if (this.view != null) {
			this.view.addAction(this);
		}
	}

	protected MetroNode metroNode;

	public MetroNode getMetroNode() {
		MapTreeView tree = (MapTreeView) getFrameWork().getView(MapTreeView.class);
		if (tree != null) {
			ActionTreeNode treeNode = tree.getSelection();
			if (treeNode != null && treeNode.getValue() != null) {
				if (treeNode.getValue() instanceof MapItem) {
					MapItem map = (MapItem) treeNode.getValue();
					if (map != null && map.getValue() != null) {
						return (MetroNode) map.getValue();
					}
				}
			}
		}
		return null;
	}
}
