/*
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor.action;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.editorframework.graphic.editor.MapComposite;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;

public class MapAction extends Action {

	public MapAction(String name, IActionHandler handler) {
		super(name, handler);
	}

	public MapAction(String name) {
		super(name);
	}

	public MapAction() {
		this("MapAction");
	}

	private MapComposite map;

	public MapComposite getMap() {
		if (map == null) {
			MapEditorView view = (MapEditorView) getFrameWork().getView(MapEditorView.class);
			if (view != null)
				map = view.getMap();
		}
		return map;
	}

	public void setMap(MapComposite map) {
		this.map = map;
	}
}
