/* 
 * 日期：2011-3-27
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.listview.equstatus;

import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.listview.ListViewAction;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.MessageForm;

public class EquModuleRefreshAction extends ListViewAction {

	private static int[] WIDTH = new int[] { 100, 100, 150, 100, 100, 100, 100, 150 };

	public class RefreshEquModuleActionHandler extends ActionHandlerAdapter {

		@Autowire
		private IMetroNodeStatusService metroNodeStatusService;

		@Override
		public void perform(ActionContext action) {

			if (view == null) {
				MessageForm.Message("错误信息", "框架配置错误,未找到视图。", SWT.ICON_ERROR);
				return;
			}

			try {
				EquStatusFilterForm filterForm = (EquStatusFilterForm) view.getData(EquStatusFilterForm.FORM_KEY);

				if (filterForm == null) {
					filterForm = new EquStatusFilterForm();
					filterForm.setPageSize(200);
					view.setData(EquStatusFilterForm.FORM_KEY, filterForm);
				}

				List<EquStatusViewItem> data = metroNodeStatusService.getEquStatusView(filterForm);
				view.removeAll();
				if (data == null || data.isEmpty()) {
					return;
				}
				view.setObjectList(data);
				view.setWidths(WIDTH);
			} catch (Exception e) {
				logger.error("获取设备状态信息失败。", e);
				return;
			}
		}
	}

	public EquModuleRefreshAction(ListView view) {
		super(view);
		setName("设备状态刷新");
		setImage("/com/insigma/commons/editorframework/images/refresh.png");
		setHandler(new RefreshEquModuleActionHandler());
	}
}
