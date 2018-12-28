/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.editorframework.graphic.editor.action;

import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;

public class SpanAdjustAction extends MapAction {

	public enum SpanAdjustMode {
		V, H
	};

	protected SpanAdjustMode adjustMode = SpanAdjustMode.V;

	public SpanAdjustAction(SpanAdjustMode mode) {
		if (mode == SpanAdjustMode.H) {
			setName("水平间距");
			setImage("/com/insigma/commons/ui/function/widthspanadjust.png");
		}

		if (mode == SpanAdjustMode.V) {
			setName("垂直间距");
			setImage("/com/insigma/commons/ui/function/heightspanadjust.png");
		}
		this.adjustMode = mode;

		IActionHandler handler = new ActionHandlerAdapter() {
			@Override
			public void perform(ActionContext context) {
				if (getMap() != null) {
				}
			}
		};
		setHandler(handler);
	}
}
