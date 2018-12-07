package com.insigma.commons.editorframework.graphic.editor.action;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.editorframework.view.PropertyView;

/**
 * 监控地图左边树的选择事件触发的Action
 * @see MapItemSelectionAction 两者的区别是MapItemSelectionAction 是地图图元的选择事件
 * @author DingLuofeng
 *
 */
public class MapTreeSelectionAction extends Action {

	// jfq，左侧树中选择了一个节点，需要（1）加载右上方的地图（2）加载右下方的属性表格。
	public MapTreeSelectionAction() {
		super("选择地图树节点");
		this.setHandler(new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				MapEditorView view = (MapEditorView) context.getFrameWork().getView(MapEditorView.class);
				MapTreeView tree = (MapTreeView) context.getFrameWork().getView(MapTreeView.class);
				if (view != null && tree != null) {
					MapItem pre = (MapItem) tree.getData(MapTreeView.MAPTREE_SELECTED_MAP_ITEM);
					ActionTreeNode selection = tree.getSelection();
					MapItem map = (MapItem) selection.getValue();
					if (map != null && !map.equals(pre)) {
						view.load(map);
						tree.setData(MapTreeView.MAPTREE_SELECTED_MAP_ITEM, map);
						mapValueView(context, map);
					}
				}
			}

			private void mapValueView(ActionContext context, MapItem map) {
				try {
					PropertyView propertyView = (PropertyView) context.getFrameWork().getView(PropertyView.class);
					if (propertyView != null) {
						propertyView.setBeanData(map.getValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
