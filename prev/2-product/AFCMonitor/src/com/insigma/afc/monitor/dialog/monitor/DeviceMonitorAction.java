package com.insigma.afc.monitor.dialog.monitor;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.action.NodeAction;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.graphic.GraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapItem;

public class DeviceMonitorAction extends NodeAction {

	public class DeviceMonitorActionHandler extends ActionHandlerAdapter {

		@Override
		public void perform(ActionContext action) {
			MetroDevice device = null;
			MapItem mapItem = null;
			GraphicItem gitem = (GraphicItem) action.getData(ActionContext.SELECTED_ITEM);
			if (gitem != null) {
				mapItem = gitem.getParent();
			}
			if (mapItem == null) {
				if (getNode() instanceof MetroDevice) {
					device = (MetroDevice) getNode();
				}
			} else if (mapItem.getValue() instanceof MetroDevice) {
				device = (MetroDevice) mapItem.getValue();
			}

			if (device == null) {
				return;
			}
			DeviceMonitorDialog dlg = new DeviceMonitorDialog(action.getFrameWork(), SWT.NONE);
			dlg.setDevice(device);
			dlg.open();
		}
	}

	public DeviceMonitorAction() {
		setName("监视设备");
		setImage("/com/insigma/afc/monitor/images/toolbar/device-monitor.png");
		setHandler(new DeviceMonitorActionHandler());
	}
}
