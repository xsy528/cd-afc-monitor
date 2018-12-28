/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor.action;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.editorframework.graphic.editor.MapItem;

public class PrintAction extends MapAction {

	public PrintAction() {
		setName("打印");
		setImage("/com/insigma/commons/ui/toolbar/print.png");
		IActionHandler handler = new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				if (getMap() != null) {
					Image image = new Image(Display.getDefault(), getMap().getBackgroundImage().getImageData());
					GC gc = new GC(image);
					if (getMap().getMap() != null) {
						for (MapItem item : getMap().getMap().getMapItems()) {
							item.paint(gc);
						}
					}
					ImagePrinter imagePrinter = new ImagePrinter();
					imagePrinter.print(image);
					image.dispose();
				}
			}
		};
		setHandler(handler);
	}
}
