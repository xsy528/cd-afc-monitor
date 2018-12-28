/**
 * 
 */
package com.insigma.afc.monitor.map.action;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.graphic.GraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;

/**
 * @author DingLuofeng
 *
 */
public class MapItemDoubleClickAction extends Action {
	public MapItemDoubleClickAction() {
		super("双击图元");
		this.setHandler(new ActionHandlerAdapter() {
			@Override
			public void perform(final ActionContext context) {
				MapEditorView view = (MapEditorView) context.getFrameWork().getView(MapEditorView.class);
				if (view == null) {
					return;
				}

				MapItem mapItem = null;
				GraphicItem gitem = (GraphicItem) context.getData(ActionContext.SELECTED_ITEM);
				if (gitem != null) {
					mapItem = gitem.getParent();
				}
				if (mapItem == null) {
					return;
				}
				MapTreeView tree = (MapTreeView) context.getFrameWork().getView(MapTreeView.class);
				if (tree != null) {
					tree.navDown(mapItem);
				} else {
					view.load(mapItem);
				}

			}
		});
	}
}
