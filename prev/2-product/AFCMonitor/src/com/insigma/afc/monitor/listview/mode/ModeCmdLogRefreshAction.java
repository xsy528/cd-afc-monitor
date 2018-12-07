package com.insigma.afc.monitor.listview.mode;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.listview.ListViewAction;
import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.query.QueryFilter;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.MessageForm;

public class ModeCmdLogRefreshAction extends ListViewAction {

	public static int[] WIDTH = new int[] { 90, 150, 100, 150, 200, 200, 212, 100, 200 };

	public class RefreshModeCmdLogActionHandler extends ActionHandlerAdapter {
		@Autowire
		protected ICommonDAO commonDAO;

		@Override
		public void perform(ActionContext action) {

			if (view == null) {
				MessageForm.Message("错误信息", "ModeCmdLog框架配置错误,未找到视图。", SWT.ICON_ERROR);
				return;
			}

			List<TmoModeUploadInfo> list = null;
			try {
				Calendar calendar = new GregorianCalendar();
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);

				QueryFilter filters = new QueryFilter();
				filters.propertyFilter("modeUploadTime", calendar.getTime(), ">=");
				filters.propertyFilter("modeUploadTime", new Date(), "<=");
				filters.addOrderBy("modeUploadTime", false);
				MetroNode node = getMetroNode();
				if (node != null) {
					switch (node.level()) {
					case LC:
						filters.propertyFilter("lineId", (short) node.id());
						break;
					case SC:
						filters.propertyFilter("stationId", (int) node.id());
						break;
					case SLE:
						filters.propertyFilter("nodeId", node.id());
						break;
					default:
						break;
					}
				}
				list = commonDAO.fetchListByFilter(filters, TmoModeUploadInfo.class);
			} catch (Exception e) {
				logger.error("获取模式日志列表异常", e);
				view.getTable().removeAll();
				return;
			}

			view.removeAll();
			view.setObjectList(list);
		}
	}

	public ModeCmdLogRefreshAction(ListView view) {
		super(view);
		setName("模式日志列表");
		setImage("/com/insigma/commons/ui/toolbar/refresh.png");
		setHandler(new RefreshModeCmdLogActionHandler());
	}

}