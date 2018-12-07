package com.insigma.afc.monitor.listview.equstatus;

import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.monitor.listview.ListViewAction;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.MessageForm;

public class NodeStatusRefreshAction extends ListViewAction {

	public AFCNodeLevel level = AFCNodeLevel.SLE;

	public class RefreshEquStatusActionHandler extends ActionHandlerAdapter {

		@Autowire
		private IMetroNodeStatusService metroNodeStatusService;

		@Override
		public void perform(ActionContext action) {
			if (view == null) {
				MessageForm.Message("错误信息", "StationStatus框架配置错误,未找到视图。", SWT.ICON_ERROR);
				return;
			}

			try {
				EquStatusFilterForm filterForm = (EquStatusFilterForm) view.getData(EquStatusFilterForm.FORM_KEY);

				if (filterForm == null) {
					filterForm = new EquStatusFilterForm();
					filterForm.setPageSize(200);
					if (level == AFCNodeLevel.SC) {
						filterForm.getEquTypeList().add(AFCDeviceType.SC);
					} else {

					}
					view.setData(EquStatusFilterForm.FORM_KEY, filterForm);
				}
				Short[] lineIDs = new Short[1];
				Integer[] stationIDs = null;
				MetroNode node = getMetroNode();
				if (node != null) {
					switch (node.level()) {
					case LC:
						MetroLine line = (MetroLine) node;
						lineIDs[0] = line.getLineID();
						break;
					case SC:
						MetroStation station = (MetroStation) node;
						lineIDs[0] = station.getId().getLineId();
						stationIDs = new Integer[1];
						stationIDs[0] = station.getId().getStationId();
						break;
					default:
						break;
					}
				}

				List<StationStatustViewItem> data = metroNodeStatusService.getStationStatusView(lineIDs, stationIDs);
				view.removeAll();
				if (data == null || data.isEmpty()) {
					return;
				}
				view.setObjectList(data);
			} catch (Exception e) {
				logger.error("获取车站状态信息失败。", e);
				view.reset();
				return;
			}
		}
	}

	public NodeStatusRefreshAction(ListView view) {
		super(view);
		setName("车站状态刷新");
		setImage("/com/insigma/commons/ui/toolbar/refresh.png");
		setHandler(new RefreshEquStatusActionHandler());
	}
}
