/* 
 * 日期：2011-3-27
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.listview.event;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.entity.TmoEquEventCur;
import com.insigma.afc.monitor.listview.ListViewAction;
import com.insigma.afc.monitor.service.IMetroEventService;
import com.insigma.commons.collection.PageHolder;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.MessageForm;

public class EquEventRefreshAction extends ListViewAction {

	public static final String LIST_VIEW_NAME = "VIEW_MODULE_LIST";

	private static int[] WIDTH = new int[] { 90, 150, 130, 100, 100, 100, 210, 80, 150 };

	public class RefreshEventListActionHandler extends ActionHandlerAdapter {

		@Autowire
		private IMetroEventService metroEventService;

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
				PageHolder<TmoEquEventCur> list = metroEventService.getEventCurList(filterForm, 0);
				view.removeAll();
				view.setObjectList(list.getDatas());
				view.setWidths(WIDTH);
			} catch (Exception e) {
				logger.error("获取事件列表异常", e);
				view.getTable().removeAll();
				return;
			}
		}

	}

	public EquEventRefreshAction(ListView view) {
		super(view);
		setName("事件列表强制刷新");
		setImage("/com/insigma/commons/ui/toolbar/refresh.png");
		setHandler(new RefreshEventListActionHandler());
	}
}