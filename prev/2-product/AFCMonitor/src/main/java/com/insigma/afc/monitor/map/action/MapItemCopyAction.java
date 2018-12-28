/**
 * 
 */
package com.insigma.afc.monitor.map.action;

import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.reflect.BeanUtil;

/**
 * @author Administrator
 *
 */
public class MapItemCopyAction extends MapItemAction<String> {

	public MapItemCopyAction(String name, final MetroNode data) {
		super(name);
		setImage("/com/insigma/commons/ui/toolbar/copy.gif");
		setUndoable(false);
		setHandler(new ActionHandlerAdapter() {
			@Override
			public void perform(final ActionContext context) {
				final EditorFrameWork editorFrameWork = context.getFrameWork();
				if (editorFrameWork == null) {
					return;
				}

				MapEditorView view = (MapEditorView) editorFrameWork.getView(MapEditorView.class);
				MetroNode newMetroNode = null;
				try {
					newMetroNode = BeanUtil.cloneObject(data);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				view.setData(MAP_COPIED_DATA, newMetroNode);

			}

		});
	}

	@Override
	public boolean IsEnable() {
		Object selectedItem = getData(ActionContext.SELECTED_ITEM);
		if (selectedItem == null) {
			return false;
		}
		return true;
	}

}
