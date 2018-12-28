/**
 * 
 */
package com.insigma.afc.monitor.map.action;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.eclipse.swt.events.MouseEvent;

import com.insigma.afc.monitor.map.builder.AbstractGraphicMapBuilder;
import com.insigma.afc.topology.AFCNodeLocation;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.Application;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.ui.MessageForm;

/**
 * @author Administrator
 *
 */
public class MapItemPasteAction extends MapItemAction<String> {

	private static final String NEW_PASTE_ITEM = "new_paste_item";

	private MapItem mapItem;

	private AbstractGraphicMapBuilder mapItembuilder;

	MetroNode currentNode;

	public MapItemPasteAction(String name, MapItem item, AbstractGraphicMapBuilder itembuilder, MetroNode node) {
		super(name);
		setData(item);
		setImage("/com/insigma/commons/ui/toolbar/paste.gif");
		this.mapItem = item;
		this.currentNode = node;
		this.mapItembuilder = itembuilder;

		setHandler(new ActionHandlerAdapter() {

			@Override
			public void unPerform(ActionContext context) {
				MapItem newMapItem = (MapItem) context.getData(NEW_PASTE_ITEM);
				if (newMapItem != null) {
					mapItem.getDataState().delete(newMapItem);
					//刷新树
					removeTreeNode(newMapItem);
				}
			}

			@Override
			public void perform(final ActionContext context) {
				final EditorFrameWork editorFrameWork = context.getFrameWork();
				if (editorFrameWork == null) {
					return;
				}

				MapEditorView view = (MapEditorView) editorFrameWork.getView(MapEditorView.class);
				MetroNode copyedNode = (MetroNode) view.getData(MAP_COPIED_DATA);

				if (copyedNode == null) {
					return;
				}
				MetroNode newMetroNode = copyOf(copyedNode);
				view.setData(MAP_COPIED_DATA, newMetroNode);

				final MouseEvent event = (MouseEvent) context.getData(ActionContext.ACTION_EVENTOBJECT);
				setMetroData(event, newMetroNode, currentNode, mapItem);
				System.out.println("paste:" + ReflectionToStringBuilder.toString(newMetroNode));

				MapItem newMapItem = mapItembuilder.buildGraphicMap(newMetroNode);
				newMapItem.setValue(newMetroNode);
				if (mapItem.isExists(newMapItem)) {
					MessageForm.Message("节点ID" + newMetroNode.id() + "已经存在。");
					return;
				}
				mapItem.getDataState().add(newMapItem);
				context.setData(NEW_PASTE_ITEM, newMapItem);
				//刷新树
				addTreeNode(newMapItem);

				//				dialog = new ObjectEditorDialog(editorFrameWork, newMetroNode, SWT.None);
				//				dialog.setText(getName());
				//				dialog.setDescription(getName());
				//				dialog.setSize(600, 400);
				//				SaveAction saveaction = new SaveAction(context);
				//
				//				dialog.getActions().add(saveaction);
				//				dialog.open();

			}

			private void setMetroData(MouseEvent event, MetroNode newMetroNode, MetroNode data, MapItem parent) {
				if (event != null) {
					newMetroNode.setImageLocation(new AFCNodeLocation(event.x, event.y, 0));
					newMetroNode.setTextLocation(new AFCNodeLocation(event.x, event.y + 20, 0));
				}
				if (newMetroNode instanceof MetroDevice && data instanceof MetroStation) {
					MetroDevice device = (MetroDevice) newMetroNode;
					MetroStation station = (MetroStation) data;
					device.getId().setStationId(station.getId().getStationId());
					device.setStationName(station.getStationName());
					setDeviceID(device, 1);
				} else if (newMetroNode instanceof MetroStation && data instanceof MetroLine) {
					MetroStation station = (MetroStation) newMetroNode;
					MetroLine line = (MetroLine) data;
					station.getId().setLineId(line.getLineID());
					station.setLineName(line.getLineName());
					setStationID(station, station.getId().getStationId());
				} else {
					return;
				}

			}

			private void setDeviceID(MetroDevice device, long deviceIndex) {
				final long deviceID = Long.decode(String.format("%02d" + Application.getDeviceTypeFormat() + "%02d",
						device.getId().getStationId(), device.getDeviceType(), deviceIndex));
				device.getId().setDeviceId(deviceID);
				final MapItem[] mapItems = mapItem.getMapItems();
				if (mapItems.length == 0) {
					return;
				}

				for (MapItem mapItem : mapItems) {
					if (mapItem.getMapId() == deviceID) {
						deviceIndex++;
						setDeviceID(device, deviceIndex);
					}
				}
			}

			private void setStationID(MetroStation device, int deviceID) {
				device.getId().setStationId(deviceID);
				final MapItem[] mapItems = mapItem.getMapItems();
				if (mapItems.length == 0) {
					return;
				}

				for (MapItem mapItem : mapItems) {
					if (mapItem.getMapId() == deviceID) {
						deviceID++;
						setStationID(device, deviceID);
					}
				}
			}
		});
	}

	@Override
	public boolean IsEnable() {

		final EditorFrameWork editorFrameWork = getFrameWork();
		if (editorFrameWork == null) {
			return false;
		}

		MapEditorView view = (MapEditorView) editorFrameWork.getView(MapEditorView.class);
		MetroNode newMetroNode = (MetroNode) view.getData(MAP_COPIED_DATA);

		if (view == null || newMetroNode == null
				|| (currentNode.level().ordinal() - newMetroNode.level().ordinal() != -1)) {
			return false;
		}
		return true;
	}
}
