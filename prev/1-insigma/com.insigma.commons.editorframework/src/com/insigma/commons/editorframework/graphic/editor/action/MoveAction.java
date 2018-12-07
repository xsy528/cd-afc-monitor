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
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.ui.MessageForm;

public class MoveAction extends MapAction {

	public enum MoveMode {
		TOP, LEFT, BOTTOM, RIGHT
	};

	private MoveMode moveMode = MoveMode.TOP;

	public MoveMode getAlignMode() {
		return moveMode;
	}

	public void setAlignMode(MoveMode alignMode) {
		this.moveMode = alignMode;
		initMoveMode(alignMode);
	}

	public MoveAction(MoveMode mode) {
		initMoveMode(mode);
		this.moveMode = mode;

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
					if (moveMode == MoveMode.TOP) {
						for (GraphicItem item : items) {
							item.setY(item.getY() - 1);
						}
						if (context.getAction().isUndoable()) {
							getMap().getActionExecutor().perform(selection.get(0), context, context.getAction());
						}
						getMap().redraw();
					}
					if (moveMode == MoveMode.BOTTOM) {
						for (GraphicItem item : items) {
							item.setY(item.getY() + 1);
						}
						if (context.getAction().isUndoable()) {
							getMap().getActionExecutor().perform(selection.get(0), context, context.getAction());
						}
						getMap().redraw();
					}
					if (moveMode == MoveMode.LEFT) {
						for (GraphicItem item : items) {
							item.setX(item.getX() - 1);
						}
						if (context.getAction().isUndoable()) {
							getMap().getActionExecutor().perform(selection.get(0), context, context.getAction());
						}
						getMap().redraw();
					}
					if (moveMode == MoveMode.RIGHT) {
						for (GraphicItem item : items) {
							item.setX(item.getX() + 1);
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
				if (getMap() != null) {
					List<GraphicItem> items = getMap().getSelectionItem();
					if (moveMode == MoveMode.TOP) {
						for (GraphicItem item : items) {
							item.setY(item.getY() + 1);
						}
						getMap().redraw();
					}
					if (moveMode == MoveMode.BOTTOM) {
						for (GraphicItem item : items) {
							item.setY(item.getY() - 1);
						}
						getMap().redraw();
					}
					if (moveMode == MoveMode.LEFT) {
						for (GraphicItem item : items) {
							item.setX(item.getX() + 1);
						}
						getMap().redraw();
					}
					if (moveMode == MoveMode.RIGHT) {
						for (GraphicItem item : items) {
							item.setX(item.getX() - 1);
						}
						getMap().redraw();
					}
					final List<MapItem> selection = getMap().getSelection();
					if (selection.size() > 0) {
						final MapItem mapItem = selection.get(0);
						mapItem.getDataState().update();
					}
				}
			}
		};
		setHandler(handler);
	}

	private void initMoveMode(MoveMode mode) {
		if (mode == MoveMode.TOP) {
			setName("上移");
			setImage("/com/insigma/commons/ui/toolbar/up.png");
		}

		if (mode == MoveMode.LEFT) {
			setName("左移");
			setImage("/com/insigma/commons/ui/toolbar/left.png");
		}

		if (mode == MoveMode.RIGHT) {
			setName("右移");
			setImage("/com/insigma/commons/ui/toolbar/right.png");
		}

		if (mode == MoveMode.BOTTOM) {
			setName("下移");
			setImage("/com/insigma/commons/ui/toolbar/down.png");
		}
	}
}
