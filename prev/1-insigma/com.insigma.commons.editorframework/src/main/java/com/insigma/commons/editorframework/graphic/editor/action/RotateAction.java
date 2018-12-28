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

import org.eclipse.swt.SWT;

import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.editorframework.graphic.GraphicItem;
import com.insigma.commons.editorframework.graphic.ImageGraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.ui.MessageForm;

public class RotateAction extends MapAction {

	public enum RotateMode {
		V, H
	};

	private RotateMode adjustMode = RotateMode.V;

	public RotateAction(RotateMode mode) {
		if (mode == RotateMode.H) {
			setName("顺时针");
			setImage("/com/insigma/commons/ui/function/clockwise.png");
		}

		if (mode == RotateMode.V) {
			setName("逆时针");
			setImage("/com/insigma/commons/ui/function/anticlockwise.png");
		}
		this.adjustMode = mode;

		IActionHandler handler = new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				if (getMap() != null) {
					List<GraphicItem> items = getMap().getSelectionItem();
					final List<MapItem> selection = getMap().getSelection();
					//检验是否选中了节点--yang
					if (selection == null || selection.size() < 1) {
						MessageForm.Message("提示信息", "请选择一个节点。", SWT.ICON_INFORMATION);
						return;
					}
					boolean group = getMap().isGroup();
					if (adjustMode == RotateMode.V) {
						//						if (group) {
						//							for (GraphicItem item : items) {
						//								item.setAngle(item.getAngle() - 45);
						//							}
						//						} else {
						for (GraphicItem item : items) {
							if (item instanceof ImageGraphicItem) {
								item.setAngle(item.getAngle() - 45);
							}
							//							}
						}
						if (context.getAction().isUndoable()) {
							getMap().getActionExecutor().perform(selection.get(0), context, context.getAction());
						}
						getMap().redraw();
					}
					if (adjustMode == RotateMode.H) {
						//						if (group) {
						//							for (GraphicItem item : items) {
						//								item.setAngle(item.getAngle() + 45);
						//							}
						//						} else {
						for (GraphicItem item : items) {
							if (item instanceof ImageGraphicItem) {
								item.setAngle(item.getAngle() + 45);
							}
							//							}
						}

						if (context.getAction().isUndoable()) {
							getMap().getActionExecutor().perform(selection.get(0), context, context.getAction());
						}
						getMap().redraw();
					}

					if (selection.size() > 0) {
						final MapItem mapItem = selection.get(0);
						mapItem.getDataState().update();
					}
				}
			}

			@Override
			public void unPerform(ActionContext context) {
				List<GraphicItem> items = getMap().getSelectionItem();
				final List<MapItem> selection = getMap().getSelection();
				boolean group = getMap().isGroup();
				if (adjustMode == RotateMode.V) {
					//					if (group) {
					//						for (GraphicItem item : items) {
					//							item.setAngle(item.getAngle() + 45);
					//						}
					//					} else {
					for (GraphicItem item : items) {
						if (item instanceof ImageGraphicItem) {
							item.setAngle(item.getAngle() + 45);
						}
					}
					//					}
					getMap().redraw();
				}
				if (adjustMode == RotateMode.H) {
					//					if (group) {
					//						for (GraphicItem item : items) {
					//							item.setAngle(item.getAngle() - 45);
					//						}
					//					} else {
					for (GraphicItem item : items) {
						if (item instanceof ImageGraphicItem) {
							item.setAngle(item.getAngle() - 45);
						}
					}
					//					}

					getMap().redraw();
				}

				if (selection.size() > 0) {
					final MapItem mapItem = selection.get(0);
					mapItem.getDataState().update();
				}
			}
		};
		setHandler(handler);
	}
}
