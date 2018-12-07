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

public class EventRefreshAction extends ListViewAction {

	public static final String LIST_VIEW_NAME = "VIEW_EVENT_LIST";

	private static int[] WIDTH = new int[] { 90, 110, 150, 100, 150, 70, 150, 80, 150 };

	public class RefreshEventListActionHandler extends ActionHandlerAdapter {

		@Autowire
		private IMetroEventService metroEventService;

		@Override
		public void perform(ActionContext action) {

			logger.info("开始事件刷新");

			if (view == null) {
				MessageForm.Message("错误信息", "EquEvent框架配置错误,未找到视图。", SWT.ICON_ERROR);
				return;
			}

			EventFilterForm filterForm = (EventFilterForm) view.getData(EventFilterForm.FORM_KEY);
			if (filterForm == null) {
				filterForm = new EventFilterForm();
				view.setData(EventFilterForm.FORM_KEY, filterForm);
			}
			logger.info("准备事件列表");
			try {
				PageHolder<TmoEquEventCur> list = metroEventService.getEventCurList(filterForm, 0);
				view.removeAll();
				view.setObjectList(list.getDatas());
			} catch (Exception e) {
				logger.error("获取事件列表异常", e);
				view.getTable().removeAll();
				return;
			}
		}

	}

	public EventRefreshAction(ListView view) {
		super(view);
		setName("事件列表强制刷新");
		setImage("/com/insigma/commons/ui/toolbar/refresh.png");
		setHandler(new RefreshEventListActionHandler());
	}
}
