/* 
 * 项目:    Insigma 编辑器框架
 * 版本: 	1.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.map.action;

import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.editorframework.IActionHandler;

public class RefreshAction extends Action {

	IActionHandler handler = new ActionHandlerAdapter() {
		@Override
		public void perform(final ActionContext context) {
			//AFCApplication.init();
			EditorFrameWork editorFrameWork = context.getFrameWork();
			for (FrameWorkView view : editorFrameWork.getViews()) {
				view.refresh();
			}
		}
	};

	public RefreshAction() {
		super("刷新");
		setImage("/com/insigma/commons/ui/function/refresh.png");
		setHandler(handler);
	}
}
