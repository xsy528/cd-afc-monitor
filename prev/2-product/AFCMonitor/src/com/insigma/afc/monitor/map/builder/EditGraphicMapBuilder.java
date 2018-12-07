/**
 * 
 */
package com.insigma.afc.monitor.map.builder;

import org.eclipse.swt.widgets.Event;

import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.map.action.MapDeviceDeleteAction;
import com.insigma.afc.monitor.map.action.MapDeviceModifyAction;
import com.insigma.afc.monitor.map.action.MapItemNewAction;
import com.insigma.afc.monitor.map.action.MapItemSelectionAction;
import com.insigma.afc.monitor.map.action.MapTreeCreateAction;
import com.insigma.afc.topology.AFCNodeLocation;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceModule;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.graphic.GraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.ui.form.IEditorChangedListener;

/**
 * @author Administrator 暂先屏蔽键盘快捷操作 -- 2013-5-3 shenchao
 *
 */
public class EditGraphicMapBuilder extends AbstractGraphicMapBuilder {

	private Action createAction = new MapTreeCreateAction();

	private final class ChangeListener implements IEditorChangedListener {

		private final MetroNode node;
		private final MapItem item;

		private ChangeListener(MetroNode node, MapItem item) {
			this.node = node;
			this.item = item;
		}

		@Override
		public void editorChanged(Event event, boolean isChanged) {
			if (item.getItems().isEmpty()) {
				return;
			}
			// jfq，图形元素的位置、角度被编辑以后，更新信息到MetroNode中
			final GraphicItem imageItem = item.getItems().get(0);
			final GraphicItem textItem = item.getItems().get(1);
			AFCNodeLocation imageLocation = new AFCNodeLocation(imageItem.getX(), imageItem.getY(),
					(int) imageItem.getAngle());
			AFCNodeLocation textLocation = new AFCNodeLocation(textItem.getX(), textItem.getY(),
					(int) textItem.getAngle());

			node.setImageLocation(imageLocation);
			node.setTextLocation(textLocation);
		}
	}

	@Override
	public MapItem buildGraphicMap(final MetroNode node) {
		MapItem item = new MapItem(node.id());
		AFCNodeLevel type = node.level();
		switch (type) {
		case ACC:
			MetroACC acc = (MetroACC) node;
			item = getAcc(acc);
			item.setImage(acc.getPicName()); // 右侧监控布局底图
			item.setValue(node);
			item.addContextAction(new MapItemNewAction("添加线路", item, this, node));
			item.addContextAction(new MapDeviceModifyAction("修改ACC", item) {
				@Override
				public boolean IsEnable() {
					Object selectedItem = getData(ActionContext.SELECTED_ITEM);
					if (selectedItem == null) {
						return true;
					}
					return false;
				}
			});

			item.setLoadSubItem(false);
			break;
		case LC:
			MetroLine line = (MetroLine) node;
			item = getLine(line);
			item.addContextAction(new MapItemNewAction("添加车站", item, this, node));
			//			item.addContextAction(new MapItemPasteAction("粘贴车站", item, this, node));
			item.addContextAction(new MapDeviceModifyAction("修改线路", item) {
				@Override
				public boolean IsEnable() {
					Object selectedItem = getData(ActionContext.SELECTED_ITEM);
					if (selectedItem == null) {
						return true;
					}
					return false;
				}
			});
			// item.addKeyAction(new MapKey(SWT.CTRL, 118), new
			// MapItemPasteAction("粘贴车站", item, this, node));
			break;
		case SC:
			MetroStation station = (MetroStation) node;
			item = getStation(station);
			item.addContextAction(new MapItemNewAction("添加设备", item, this, node));
			//			item.addContextAction(new MapItemPasteAction("粘贴设备", item, this, node));
			//			item.addContextAction(new MapItemCopyAction("复制车站", node));
			item.addContextAction(new MapDeviceModifyAction("修改车站", item));
			item.addContextAction(new MapDeviceDeleteAction("删除车站", item));
			item.setDoubleClickAction(new MapDeviceModifyAction("修改车站", item));
			// item.addKeyAction(new MapKey(SWT.CTRL, 99), new
			// MapItemCopyAction("复制车站", node));
			// item.addKeyAction(new MapKey(SWT.CTRL, 118), new
			// MapItemPasteAction("粘贴设备", item, this, node));
			// item.addKeyAction(new MapKey(SWT.CTRL, SWT.DEL), new
			// MapDeviceDeleteAction("删除车站", item));
			item.addContextAction(new MapDeviceModifyAction("修改车站", item) {
				@Override
				public boolean IsEnable() {
					Object selectedItem = getData(ActionContext.SELECTED_ITEM);
					if (selectedItem == null) {
						return true;
					}
					return false;
				}
			});
			break;
		case SLE:
			item = getDeviceItem((MetroDevice) node);
			if (item == null) {
				return null;
			}
			//			item.addContextAction(new MapItemCopyAction("复制设备", node));
			item.addContextAction(new MapDeviceModifyAction("修改设备", item));
			item.addContextAction(new MapDeviceDeleteAction("删除设备", item));
			item.setDoubleClickAction(new MapDeviceModifyAction("修改设备", item));
			// item.addKeyAction(new MapKey(SWT.CTRL, 99), new
			// MapItemCopyAction("复制设备", node));
			// item.addKeyAction(new MapKey(SWT.CTRL, SWT.DEL), new
			// MapDeviceDeleteAction("删除设备", item));
			break;
		default:
			item = getModule((MetroDeviceModule) node);
			break;
		}
		if (item != null) {
			item.setText(node.name());
			item.setSelectionAction(new MapItemSelectionAction());
			item.addChangedListener(new ChangeListener(node, item));
		}
		return item;
	}

	@Override
	public void treeNodeCreated(ActionTreeNode treeNode) {
		treeNode.addCreateAction(createAction);
	}
}
