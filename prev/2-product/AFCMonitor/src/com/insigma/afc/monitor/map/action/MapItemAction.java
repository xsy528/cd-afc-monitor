/**
 * 
 */
package com.insigma.afc.monitor.map.action;

import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.topology.AFCNodeLocation;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.AsynAction;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;

/**
 * @author DLF
 *
 */
public class MapItemAction<Result> extends AsynAction<MetroNode, Result> {

	public static final String MAP_COPIED_DATA = "MAP_COPIED_DATA";

	public MapItemAction(String name, IActionHandler handler) {
		super(name, handler);
	}

	public MapItemAction(String name) {
		super(name);
	}

	protected final void addTreeNode(MapItem mapitem) {
		MapTreeView mapTreeView = (MapTreeView) getFrameWork().getView(MapTreeView.class);
		if (mapTreeView != null) {
			mapTreeView.addTreeNode(mapitem);
		}
	}

	protected final void removeTreeNode(MapItem mapitem) {
		MapTreeView mapTreeView = (MapTreeView) getFrameWork().getView(MapTreeView.class);
		if (mapTreeView != null) {
			mapTreeView.removeTreeNode(mapitem);
		}
	}

	/**
	 * @return
	 */
	protected final MetroNode copyOf(MetroNode node) {
		AFCNodeLevel type = node.level();
		switch (type) {
		case SC:
			MetroStation station = (MetroStation) node;
			MetroStation metroStation = new MetroStation(station);
			final AFCNodeLocation imageLocation = setLocation(metroStation.getImageLocation());
			metroStation.setImageLocation(imageLocation);
			final AFCNodeLocation textLocation = setLocation(metroStation.getTextLocation());
			metroStation.setTextLocation(textLocation);
			if (!metroStation.getStationName().startsWith("CopyOf")) {
				metroStation.setStationName("CopyOf " + metroStation.getStationName());
			}
			return metroStation;
		default:
			MetroDevice device = (MetroDevice) node;
			final MetroDevice metroDevice = new MetroDevice(device);
			final AFCNodeLocation imageLocation2 = setLocation(metroDevice.getImageLocation());
			metroDevice.setImageLocation(imageLocation2);
			final AFCNodeLocation textLocation2 = setLocation(metroDevice.getTextLocation());
			metroDevice.setTextLocation(textLocation2);
			if (!metroDevice.getDeviceName().startsWith("CopyOf")) {
				metroDevice.setDeviceName("CopyOf " + metroDevice.getDeviceName());
			}
			return metroDevice;
		}
	}

	private final AFCNodeLocation setLocation(AFCNodeLocation afcNodeLocation) {
		final AFCNodeLocation location = new AFCNodeLocation(afcNodeLocation);
		location.setX(location.getX() + 10);
		location.setY(location.getY() + 10);
		return location;
	}

}
