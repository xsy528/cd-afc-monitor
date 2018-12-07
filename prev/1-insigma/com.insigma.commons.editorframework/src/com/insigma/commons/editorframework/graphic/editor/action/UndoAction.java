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
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.editorframework.graphic.editor.MapComposite;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;

public class UndoAction extends Action {

	private MapEditorView editor;

	public MapEditorView getMapEditorView() {
		if (editor == null) {
			editor = (MapEditorView) getFrameWork().getView(MapEditorView.class);
		}
		return editor;
	}

	public void setMapEditorView(MapEditorView editor) {
		this.editor = editor;
	}

	public UndoAction() {
		super("撤销");
		setImage("/com/insigma/commons/ui/toolbar/undo.png");
		IActionHandler handler = new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				getMapEditorView().getMap().undo();
			}

		};
		setHandler(handler);
	}

	@Override
	public boolean IsEnable() {
		MapEditorView mapEditorView = getMapEditorView();
		if (mapEditorView == null) {
			return false;
		}
		MapComposite map = mapEditorView.getMap();
		if (map == null) {
			return false;
		}
		return map.canUndo();
	}

}
