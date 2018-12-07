package com.insigma.afc.monitor.listview.equstatus;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.listview.ListViewAction;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.MessageForm;

@SuppressWarnings("unchecked")
public class EquStatusRefreshAction extends ListViewAction {

	public static final String LIST_VIEW_NAME = "FORM_EQU_STATUS_CONDITION";

	public static int[] WIDTH = new int[] { 170, 100, 100, 100, 100, 150 };

	public class RefreshEquStatusActionHandler extends ActionHandlerAdapter {

		@Autowire
		private IMetroNodeStatusService metroNodeStatusService;

		@Override
		public void perform(ActionContext action) {

			if (view == null) {
				MessageForm.Message("错误信息", "EquStatus框架配置错误,未找到视图[" + EquStatusRefreshAction.LIST_VIEW_NAME + "]。",
						SWT.ICON_ERROR);
				return;
			}

			try {
				EquStatusFilterForm filterForm = (EquStatusFilterForm) view.getData(EquStatusFilterForm.FORM_KEY);

				if (filterForm == null) {
					filterForm = new EquStatusFilterForm();
					filterForm.setPageSize(200);
					view.setData(EquStatusFilterForm.FORM_KEY, filterForm);
				}

				MetroNode metroNode = getMetroNode();

				if (filterForm.getNodeSelectMap() == null
						|| filterForm.getNodeSelectMap().get(metroNode.getNodeId()) == null) {
					List<Object> list = new ArrayList<Object>();
					if (metroNode != null && metroNode instanceof MetroStation) {
						List<? extends MetroNode> subNodes = metroNode.getSubNodes();
						if (subNodes == null || subNodes.size() == 0) {
							view.removeAll();
							return;
						}
						list.addAll(subNodes);
					} else if (metroNode != null && metroNode instanceof MetroLine) {
						List<MetroStation> subNodes = (List<MetroStation>) metroNode.getSubNodes();
						for (MetroStation metroStation : subNodes) {
							List<MetroDevice> subNodes2 = metroStation.getSubNodes();
							list.addAll(subNodes2);
							list.add(metroStation);
						}
					} else if (metroNode != null && metroNode instanceof MetroACC) {
						List<MetroLine> subNodes = (List<MetroLine>) metroNode.getSubNodes();
						for (MetroLine metroLine : subNodes) {
							List<MetroStation> subNodes2 = metroLine.getSubNodes();
							for (MetroStation metroStation : subNodes2) {
								List<MetroDevice> subNodes3 = metroStation.getSubNodes();
								list.addAll(subNodes3);
								list.add(metroStation);
							}
						}

					}
					list.add(metroNode);
					filterForm.setSelections(list);
				} else {
					filterForm.setSelections(filterForm.getNodeSelectMap().get(metroNode.getNodeId()));
				}

				List<EquStatusViewItem> data = metroNodeStatusService.getEquStatusView(filterForm);
				view.removeAll();
				if (data == null || data.isEmpty()) {
					return;
				}
				view.setObjectList(data);
				view.setWidths(WIDTH);
			} catch (Exception e) {
				logger.error("获取设备状态信息失败", e);
				view.reset();
				return;
			}
		}
	}

	public EquStatusRefreshAction(ListView view) {
		super(view);
		setName("设备状态刷新");
		setImage("/com/insigma/commons/ui/toolbar/refresh.png");
		setHandler(new RefreshEquStatusActionHandler());
	}
}
