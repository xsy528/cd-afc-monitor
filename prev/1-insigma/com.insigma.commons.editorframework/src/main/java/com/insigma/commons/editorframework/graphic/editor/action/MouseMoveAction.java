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
import com.insigma.commons.editorframework.graphic.GraphicItem;

public class MouseMoveAction extends MapAction {

	GraphicItem oldImageItem;

	GraphicItem oldTextItem;
	GraphicItem newImageItem;

	GraphicItem newTextItem;
	private GraphicItem parentGItem;

	public MouseMoveAction(String name, final List<GraphicItem> orgItems, GraphicItem parentItem) {
		super(name);
		this.parentGItem = parentItem;
		{
			final GraphicItem graphicItem = orgItems.get(0);
			oldImageItem = new GraphicItem(graphicItem.getParent(), graphicItem.getX(), graphicItem.getY(),
					graphicItem.getAngle());
		}
		{
			final GraphicItem graphicItem = orgItems.get(1);
			oldTextItem = new GraphicItem(graphicItem.getParent(), graphicItem.getX(), graphicItem.getY(),
					graphicItem.getAngle());
		}
		this.setHandler(new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				if (newImageItem != null && newTextItem != null) {
					final List<GraphicItem> items = parentGItem.getParent().getItems();
					{
						final GraphicItem graphicItem = items.get(0);
						graphicItem.setX(newImageItem.getX());
						graphicItem.setY(newImageItem.getY());

					}
					{
						final GraphicItem graphicItem = items.get(1);
						graphicItem.setX(newTextItem.getX());
						graphicItem.setY(newTextItem.getY());

					}
					getMap().redraw();
				}
				parentGItem.getParent().getDataState().update();
			}

			@Override
			public void unPerform(ActionContext context) {
				final List<GraphicItem> items = parentGItem.getParent().getItems();
				{
					final GraphicItem graphicItem = items.get(0);
					newImageItem = new GraphicItem(parentGItem.getParent(), graphicItem.getX(), graphicItem.getY(),
							graphicItem.getAngle());
					graphicItem.setX(oldImageItem.getX());
					graphicItem.setY(oldImageItem.getY());

				}
				{
					final GraphicItem graphicItem = items.get(1);
					newTextItem = new GraphicItem(parentGItem.getParent(), graphicItem.getX(), graphicItem.getY(),
							graphicItem.getAngle());
					graphicItem.setX(oldTextItem.getX());
					graphicItem.setY(oldTextItem.getY());

				}
				getMap().redraw();
			}
		});

	}

}
