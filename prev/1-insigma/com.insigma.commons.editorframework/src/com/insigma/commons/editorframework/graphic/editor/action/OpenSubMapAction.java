/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor.action;

import java.util.List;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;

public class OpenSubMapAction extends Action {

	private MapTreeView mapTreeView;

	private MapEditorView mapEditorView;

	public MapTreeView getMapTreeView() {
		if (mapTreeView == null) {
			mapTreeView = (MapTreeView) getFrameWork().getView(MapTreeView.class);
		}
		return mapTreeView;
	}

	public void setMapTreeView(MapTreeView mapTreeView) {
		this.mapTreeView = mapTreeView;
	}

	public MapEditorView getMapEditorView() {
		if (mapEditorView == null) {
			mapEditorView = (MapEditorView) getFrameWork().getView(MapEditorView.class);
		}
		return mapEditorView;
	}

	public void setMapEditorView(MapEditorView mapEditorView) {
		this.mapEditorView = mapEditorView;
	}

	public OpenSubMapAction() {
		super("向下");
		setImage("/com/insigma/commons/ui/toolbar//nav-down.png");
		IActionHandler handler = new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				MapTreeView mapTreeView2 = getMapTreeView();
				if (mapTreeView2 != null) {
					List<MapItem> items = getMapEditorView().getMap().getSelection();
					if (items != null && items.size() == 1) {
						MapItem activeMap = getMapEditorView().getActiveMap();
						mapTreeView2.navDown(activeMap);

					}
				}
			}
		};
		setHandler(handler);
	}
}
