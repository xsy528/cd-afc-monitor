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

import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.editorframework.graphic.GraphicItem;
import com.insigma.commons.editorframework.graphic.ImageGraphicItem;
import com.insigma.commons.editorframework.graphic.TextGraphicItem;

public class AlignAction extends MapAction {
	public enum AlignMode {
		TOP, LEFT, BOTTOM, RIGHT
	};

	private AlignMode alignMode = AlignMode.TOP;

	public AlignMode getAlignMode() {
		return alignMode;
	}

	public void setAlignMode(AlignMode alignMode) {
		this.alignMode = alignMode;
	}

	public AlignAction(AlignMode mode) {
		if (mode == AlignMode.TOP) {
			setName("上对齐");
			setImage("/com/insigma/commons/ui/function//align-top.png");
		}
		if (mode == AlignMode.LEFT) {
			setName("左对齐");
			setImage("/com/insigma/commons/ui/function/align-left.png");
		}
		if (mode == AlignMode.RIGHT) {
			setName("右对齐");
			setImage("/com/insigma/commons/ui/function/align-right.png");
		}
		if (mode == AlignMode.BOTTOM) {
			setName("下对齐");
			setImage("/com/insigma/commons/ui/function/align-bottom.png");
		}
		this.alignMode = mode;
		IActionHandler handler = new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				if (getMap() != null) {
					List<GraphicItem> items = getMap().getSelectionItem();
					if (items.size() <= 1) {
						return;
					}
					if (alignMode == AlignMode.TOP) {
						int imageMinY = items.get(0).getY();
						int textMinY = items.get(1).getY();
						for (GraphicItem item : items) {
							if (item instanceof ImageGraphicItem) {
								if (item.getY() < imageMinY) {
									imageMinY = item.getY();
								}
							} else if (item instanceof TextGraphicItem) {
								if (item.getY() < textMinY) {
									textMinY = item.getY();
								}
							}
						}
						for (GraphicItem item : items) {
							if (item instanceof ImageGraphicItem) {
								item.setY(imageMinY);
							} else if (item instanceof TextGraphicItem) {
								item.setY(textMinY);
							}
						}
						getMap().redraw();
					}
					if (alignMode == AlignMode.BOTTOM) {
						int imageMaxY = items.get(0).getY();
						int textMaxY = items.get(1).getY();
						for (GraphicItem item : items) {
							if (item instanceof ImageGraphicItem) {
								if (item.getY() > imageMaxY) {
									imageMaxY = item.getY();
								}
							} else if (item instanceof TextGraphicItem) {
								if (item.getY() > textMaxY) {
									textMaxY = item.getY();
								}
							}
						}
						for (GraphicItem item : items) {
							if (item instanceof ImageGraphicItem) {
								item.setY(imageMaxY);
							} else if (item instanceof TextGraphicItem) {
								item.setY(textMaxY);
							}
						}
						getMap().redraw();
					}

					if (alignMode == AlignMode.LEFT) {
						int minX = items.get(0).getX();
						for (GraphicItem item : items) {
							if (item.getX() < minX) {
								minX = item.getX();
							}
						}
						for (GraphicItem item : items) {
							item.setX(minX);
						}
						getMap().redraw();
					}

					if (alignMode == AlignMode.RIGHT) {
						int maxX = items.get(0).getX();
						for (GraphicItem item : items) {
							if (item.getX() > maxX) {
								maxX = item.getX();
							}
						}
						for (GraphicItem item : items) {
							item.setX(maxX);
						}
						getMap().redraw();
					}
				}
			}
		};
		setHandler(handler);
	}
}
