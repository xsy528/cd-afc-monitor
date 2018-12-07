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
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;

public class OpenParentMapAction extends Action {

	private MapTreeView mapTreeView;

	public MapTreeView getMapTreeView() {
		if (mapTreeView == null) {
			mapTreeView = (MapTreeView) getFrameWork().getView(MapTreeView.class);
		}
		return mapTreeView;
	}

	public void setMapTreeView(MapTreeView mapTreeView) {
		this.mapTreeView = mapTreeView;
	}

	public OpenParentMapAction() {
		super("向上");
		setImage("/com/insigma/commons/ui/toolbar/nav-up.png");
		IActionHandler handler = new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				if (getMapTreeView() != null) {
					getMapTreeView().navUp();
				}
			}
		};
		setHandler(handler);
	}
}
