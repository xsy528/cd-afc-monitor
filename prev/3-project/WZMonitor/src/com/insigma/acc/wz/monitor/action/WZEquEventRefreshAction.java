/* 
 * 日期：2018年8月31日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.monitor.action;

import org.eclipse.swt.SWT;

import com.insigma.acc.wz.monitor.service.IWZModeService;
import com.insigma.afc.monitor.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.listview.ListViewAction;
import com.insigma.afc.monitor.listview.event.EventFilterForm;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.MessageForm;

/**
 * Ticket: 设备事件刷新
 * @author  chenyao
 *
 */
public class WZEquEventRefreshAction extends ListViewAction {

	public static final String LIST_VIEW_NAME = "VIEW_MODULE_LIST";

	private static int[] WIDTH = new int[] { 150, 130, 120, 200, 150, 150, 210, 80, 150 };

	public class RefreshEventListActionHandler extends ActionHandlerAdapter {

		@Autowire
		private IWZModeService modeService;

		@Override
		public void perform(ActionContext action) {

			if (view == null) {
				MessageForm.Message("错误信息", "框架配置错误,未找到视图。", SWT.ICON_ERROR);
				return;
			}

			EventFilterForm filterForm = (EventFilterForm) view.getData(EventFilterForm.FORM_KEY);
			if (filterForm == null) {
				filterForm = new EventFilterForm();
				view.setData(EventFilterForm.FORM_KEY, filterForm);
			}

			try {
				PageHolder<TmoEquStatusCur> list = modeService.getEquStatusList(filterForm, 0);
				if (list == null) {
					//					|| list.getDatas() == null || list.getDatas().isEmpty()) {
					return;
				}
				view.removeAll();
				view.setObjectList(list.getDatas());
				view.setWidths(WIDTH);
			} catch (

			Exception e) {
				logger.error("获取事件列表异常", e);
				view.getTable().removeAll();
				return;
			}
		}

	}

	public WZEquEventRefreshAction(ListView view) {
		super(view);
		setName("事件列表强制刷新");
		setImage("/com/insigma/commons/ui/toolbar/refresh.png");
		setHandler(new RefreshEventListActionHandler());
	}
}
