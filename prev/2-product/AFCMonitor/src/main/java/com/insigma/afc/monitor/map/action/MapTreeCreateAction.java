package com.insigma.afc.monitor.map.action;

import java.util.List;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Event;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.editorframework.view.PropertyView;
import com.insigma.commons.ui.form.IEditorChangedListener;

public class MapTreeCreateAction extends Action {

	/**
	 * @author qcj(qiuchangjin@zdwxgd.com)
	 *
	 */
	private final class MapChangeListener implements IEditorChangedListener {
		private final ActionTreeNode selectNode;

		private final MapItem map;

		private final MapTreeView treeView;

		private MapChangeListener(ActionTreeNode selectNode, MapItem map, MapTreeView tree) {
			this.selectNode = selectNode;
			this.map = map;
			this.treeView = tree;
		}

		@Override
		public void editorChanged(Event event, boolean isChanged) {
			ActionTreeNode myNode = selectNode;
			List<ActionTreeNode> subNodes = selectNode.getSubNodes();
			for (ActionTreeNode node : subNodes) {
				if (node.getValue().equals(map)) {
					myNode = node;
					break;
				}
			}

			treeView.setChanged(isChanged);
			if (isChanged) {
				myNode.doChanged(treeView);
			} else {
				myNode.doUnChanged(treeView);
			}

			MapEditorView mapEditorView = (MapEditorView) getFrameWork().getView(MapEditorView.class);
			final Object tabItem = mapEditorView.getData(mapEditorView.getClass().getName());
			if (tabItem instanceof CTabItem) {
				String orgText = mapEditorView.getText();
				if (isChanged) {
					((CTabItem) tabItem).setText("*" + orgText);
				} else {
					((CTabItem) tabItem).setText(orgText);
				}
			}

			try {
				PropertyView propertyView = (PropertyView) getFrameWork().getView(PropertyView.class);
				if (propertyView != null) {
					propertyView.setBeanData(map.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public MapTreeCreateAction() {
		super("创建地图树节点");
		this.setHandler(new ActionHandlerAdapter() {
			IEditorChangedListener pre_changedListener;

			@Override
			public void perform(ActionContext context) {
				final MapTreeView tree = (MapTreeView) context.getFrameWork().getView(MapTreeView.class);
				final ActionTreeNode node = (ActionTreeNode) context.getData();
				final MapItem map = (MapItem) node.getValue();
				if (pre_changedListener != null) {
					map.removeChangedListener(pre_changedListener);//去掉上次的listener，防止多次添加
				}
				if (node != null) {
					IEditorChangedListener changedListener = new MapChangeListener(node, map, tree);
					map.addChangedListener(changedListener);
					pre_changedListener = changedListener;
				}
			}

		});
	}
}
