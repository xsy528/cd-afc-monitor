package com.insigma.afc.monitor.listview.mode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.listview.ListViewAction;
import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.op.OPException;
import com.insigma.commons.query.QueryFilter;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.MessageForm;

public class ModeBroadcastRefreshAction extends ListViewAction {

	private static int[] WIDTH = new int[] { 90, 150, 300, 150, 100 };

	public class RefreshModeBroadcastListActionHandler extends ActionHandlerAdapter {

		@Autowire
		protected ICommonDAO commonDAO;

		@Override
		public void perform(ActionContext action) {

			if (view == null) {
				MessageForm.Message("错误信息", "ModeBroadcast框架配置错误,未找到视图。", SWT.ICON_ERROR);
				return;
			}
			List<TmoModeBroadcast> viewList = null;
			try {
				//			Integer[] stationIDs = null;
				MetroNode node = getMetroNode();
				long nodeId;
				if (node != null) {
					nodeId = node.getNodeId();
					//				switch (node.level()) {
					//				case LC:
					//					break;
					//				case SC:
					//					MetroStation station = (MetroStation) node;
					//					stationIDs = new Integer[1];
					//					stationIDs[0] = station.getId().getStationId();
					//					break;
					//				default:
					//					break;
					//				}
				} else {
					nodeId = AFCApplication.getNodeId();
				}

				QueryFilter filters = new QueryFilter();
				// changeBy ChenYao----根据模式广播时间查询
				// filters.propertyFilter("nodeId", nodeId);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filters.propertyFilter("modeBroadcastTime", sdf.parse(sdf.format(date)), ">=");

				filters.addOrderBy("modeBroadcastTime", true);
				filters.addOrderBy("targetId", true);
				// changeBy ChenYao

				//List<TmoModeBroadcast> viewList = modeService.getModeBroadcast(nodeId, null, null);
				viewList = commonDAO.fetchListByFilter(filters, TmoModeBroadcast.class);

			} catch (OPException e) {
				logger.error("刷新模式广播信息错误", e);
				view.getTable().removeAll();
				return;
			} catch (ParseException e) {
				logger.error("刷新模式广播信息错误(时间解析失败)", e);
				view.getTable().removeAll();
				return;
			}
			view.removeAll();
			view.setObjectList(viewList);
		}
	}

	public ModeBroadcastRefreshAction(ListView view) {
		super(view);
		setName("模式广播信息");
		setImage("/com/insigma/commons/ui/toolbar/refresh.png");
		setHandler(new RefreshModeBroadcastListActionHandler());
	}
}
