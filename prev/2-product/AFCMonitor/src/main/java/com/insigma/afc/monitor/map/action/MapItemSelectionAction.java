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
import com.insigma.commons.editorframework.view.PropertyView;

/**
 * @author DingLuofeng
 *
 */
public class MapItemSelectionAction extends Action {
	public MapItemSelectionAction() {
		super("选择图元");
		setUndoable(false);
		this.setHandler(new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext action) {
				MapEditorView view = (MapEditorView) action.getFrameWork().getView(MapEditorView.class);
				if (view == null) {
					return;
				}
				GraphicItem mapItem = (GraphicItem) action.getData(ActionContext.SELECTED_ITEM);
				PropertyView propertyView = (PropertyView) action.getFrameWork().getView(PropertyView.class);
				if (propertyView != null) {
					if (mapItem == null) {
						MapItem map = view.getMap().getMap();
						Object value = map.getValue();
						propertyView.setBeanData(value);
						return;
					} else {
						Object value = mapItem.getParent().getValue();
						propertyView.setBeanData(value);
					}
				}

			}
		});
	}
}
