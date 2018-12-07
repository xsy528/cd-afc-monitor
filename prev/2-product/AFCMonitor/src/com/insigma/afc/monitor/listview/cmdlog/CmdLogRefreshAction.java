package com.insigma.afc.monitor.listview.cmdlog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.listview.ListViewAction;
import com.insigma.afc.monitor.search.CommandLogSearchForm;
import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.query.QueryCondition;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.MessageForm;

public class CmdLogRefreshAction extends ListViewAction {

	public static int[] WIDTH = new int[] { 90, 150, 100, 150, 120, 200, 212, 100, 150 };

	CommandLogSearchForm form = new CommandLogSearchForm();

	public class RefreshCmdLogActionHandler extends ActionHandlerAdapter {

		@Autowire
		ICommonDAO commonDao;

		@Override
		public void perform(ActionContext action) {

			if (view == null) {
				MessageForm.Message("提示信息", "CmdLog框架配置错误,未找到视图。", SWT.ICON_ERROR);
				return;
			}

			List<?> list = null;
			try {
				Calendar calendar = new GregorianCalendar();
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				form.setStartTime(calendar.getTime());
				form.setEndTime(new Date());
				QueryCondition metroNodeConditoin = null;
				MetroNode node = getMetroNode();
				if (node != null) {
					switch (node.level()) {
					case LC:
						metroNodeConditoin = new QueryCondition("lineId", (short) node.id());
						break;
					case SC:
						metroNodeConditoin = new QueryCondition("stationId", (int) node.id());
						break;
					case SLE:
						metroNodeConditoin = new QueryCondition("nodeId", node.id());
						break;
					default:
						break;
					}
				}

				if (metroNodeConditoin == null) {
					list = this.commonDao.fetchListByObject(form, 100, 1).getDatas();
				} else {
					list = this.commonDao.fetchListByObject(form, 100, 1, metroNodeConditoin).getDatas();
				}
			} catch (Exception e) {
				logger.error("获取命令日志列表异常", e);
				view.removeAll();
				return;
			}
			view.removeAll();
			view.setObjectList(list);
		}

	}

	public CmdLogRefreshAction(ListView view) {
		super(view);
		setName("日志列表");
		setImage("/com/insigma/commons/ui/toolbar/refresh.png");
		setHandler(new RefreshCmdLogActionHandler());
	}
}
