package com.insigma.afc.monitor.map.builder;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Image;

import com.insigma.afc.dic.AFCDeviceSubType;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.monitor.map.IGraphicMapBuilder;
import com.insigma.afc.topology.AFCNodeLocation;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceModule;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.graphic.ImageGraphicItem;
import com.insigma.commons.editorframework.graphic.TextGraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.swtdesigner.SWTResourceManager;

/**
 * 
 * @author DingLuofeng
 *
 */
public abstract class AbstractGraphicMapBuilder implements IGraphicMapBuilder {

	protected static final Log S_LOG = LogFactory.getLog(AbstractGraphicMapBuilder.class);

	@Override
	public void treeNodeCreated(ActionTreeNode treeNode) {

	}

	public static MapItem getAcc(MetroACC acc) {
		MapItem item = new MapItem(acc.id());
		item.setImage(acc.getPicName());
		item.setValue(acc);

		AFCNodeLocation location = acc.getImageLocation();
		ImageGraphicItem iamgeitem = new ImageGraphicItem(item, location.getX(), location.getY(), location.getAngle());
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/green.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/grey.png");

		location = acc.getTextLocation();
		TextGraphicItem textitem = new TextGraphicItem(item, location.getX(), location.getY(), location.getAngle());
		textitem.setText(acc.name());

		item.addGraphicItem(iamgeitem);
		item.addGraphicItem(textitem);
		return item;
	}

	public static MapItem getLine(MetroLine line) {
		MapItem item = new MapItem(line.id());
		item.setImage(line.getPicName());
		item.setValue(line);

		AFCNodeLocation location = line.getImageLocation();
		ImageGraphicItem iamgeitem = new ImageGraphicItem(item, location.getX(), location.getY(), location.getAngle());
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/green.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/grey.png");

		location = line.getTextLocation();
		TextGraphicItem textitem = new TextGraphicItem(item, location.getX(), location.getY(), location.getAngle());
		textitem.setText(line.name());

		item.addGraphicItem(iamgeitem);
		item.addGraphicItem(textitem);
		return item;
	}

	public static MapItem getStation(MetroStation station) {
		MapItem item = new MapItem(station.id());
		item.setImage(station.getPicName());
		item.setValue(station);
		// item, 50, 20
		AFCNodeLocation location = station.getImageLocation();
		ImageGraphicItem iamgeitem = new ImageGraphicItem(item, location.getX(), location.getY(), location.getAngle());

		// NORMAL(0), WARNING(1), ALARM(2), CLOSE(3),
		// OFFLINE(4),ONLINE(5),MODE_NORMAL(6),MODE_DEMOTION(7),MODE_URGENCY(8);
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/green.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/yellow.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/red.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/grey.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/mode_normal.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/mode_demotion.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/mode_urgency.png");

		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/urgency.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/green_new.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/station/stop.png");

		location = station.getTextLocation();
		TextGraphicItem textitem = new TextGraphicItem(item, location.getX(), location.getY(), location.getAngle());
		textitem.setText(station.name());

		item.addGraphicItem(iamgeitem);
		item.addGraphicItem(textitem);
		return item;
	}

	public static ArrayList<Image> getDeviceImages(MetroDevice device) {
		ArrayList<Image> imgs = new ArrayList<Image>();
		if (device.getDeviceType() == AFCDeviceType.POST.shortValue()) {
			imgs.add(SWTResourceManager.getImage(SWTResourceManager.class,
					"/com/insigma/afc/ui/monitor/bom/bom_green.png"));
		} else if (device.getDeviceType() == AFCDeviceType.TVM.shortValue()) {
			imgs.add(SWTResourceManager.getImage(SWTResourceManager.class,
					"/com/insigma/afc/ui/monitor/tvm/TVM_green.png"));
		} else if (device.getDeviceType() == AFCDeviceType.TSM.shortValue()) {
			imgs.add(SWTResourceManager.getImage(SWTResourceManager.class,
					"/com/insigma/afc/ui/monitor/tsm/TSM_green.png"));
		} else if (device.getDeviceType() == AFCDeviceType.AVM.shortValue()) {
			imgs.add(SWTResourceManager.getImage(SWTResourceManager.class,
					"/com/insigma/afc/ui/monitor/avm/AVM_green.png"));
		} else if (device.getDeviceType() == AFCDeviceType.TCM.shortValue()) {
			imgs.add(SWTResourceManager.getImage(SWTResourceManager.class,
					"/com/insigma/afc/ui/monitor/tcm/TCM_green.png"));
		} else if ((AFCDeviceType.GATE == null
				&& AFCDeviceSubType.getInstance().getDicItemMap().containsKey(device.getDeviceType()))
				|| (AFCDeviceType.GATE != null && device.getDeviceType() == AFCDeviceType.GATE)) {
			{
				if (device.getDeviceType() == AFCDeviceSubType.DEVICE_GATE_ENTRY.shortValue()) {
					imgs.add(SWTResourceManager.getImage(SWTResourceManager.class,
							"/com/insigma/afc/ui/monitor/entry_green.png"));
				} else if (device.getDeviceType() == AFCDeviceSubType.DEVICE_GATE_EXIT.shortValue()) {
					imgs.add(SWTResourceManager.getImage(SWTResourceManager.class,
							"/com/insigma/afc/ui/monitor/gate/exit_green.png"));
				} else if (device.getDeviceType() == AFCDeviceSubType.DEVICE_GATE_BOTH.shortValue()) {
					imgs.add(SWTResourceManager.getImage(SWTResourceManager.class,
							"/com/insigma/afc/ui/monitor/gate/both_green.png"));
				} else {
					S_LOG.warn("GATE不存在设备类型=" + device.getDeviceType());
				}
			}
		} else {
			S_LOG.warn("不存在设备类型=" + device.getDeviceType());
		}
		return imgs;
	}

	public static MapItem getDeviceItem(MetroDevice device) {
		MapItem item = new MapItem(device.id());
		item.setImage(device.getPicName());
		item.setValue(device);

		AFCNodeLocation location = device.getImageLocation();
		ImageGraphicItem iamgeitem = new ImageGraphicItem(item, location.getX(), location.getY(), location.getAngle());
		String imagePathName = null;
		if (device.getDeviceType() == AFCDeviceType.POST.shortValue()) {
			imagePathName = "bom/BOM";


		} else if (device.getDeviceType() == AFCDeviceType.TVM.shortValue()) {
			imagePathName = "tvm/TVM";
		} else if (device.getDeviceType() == AFCDeviceType.PCA.shortValue()) {
			imagePathName = "pca/PCA";
		} else if (device.getDeviceType() == AFCDeviceType.TSM.shortValue()) {
			imagePathName = "tsm/TSM";
		} else if (device.getDeviceType() == AFCDeviceSubType.DEVICE_GATE_ENTRY.shortValue()) {
				imagePathName = "gate/entry";
			} else if (device.getDeviceType() == AFCDeviceSubType.DEVICE_GATE_EXIT.shortValue()) {
				imagePathName = "gate/exit";
			} else if (device.getDeviceType() == AFCDeviceSubType.DEVICE_GATE_BOTH.shortValue()) {
				imagePathName = "gate/both";

		} else {
			S_LOG.warn("不存在设备类型=" + device.getDeviceType());
			return null;
		}
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/" + imagePathName + "_green.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/" + imagePathName + "_yellow.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/" + imagePathName + "_red.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/" + imagePathName + "_close.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/" + imagePathName + "_grey.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/" + imagePathName + "_close.png");

		location = device.getTextLocation();
		TextGraphicItem textitem = new TextGraphicItem(item, location.getX(), location.getY(), location.getAngle());
		textitem.setText(device.name());

		item.addGraphicItem(iamgeitem);
		item.addGraphicItem(textitem);
		return item;
	}

	public static MapItem getModule(MetroDeviceModule node) {
		MapItem item = new MapItem(node.id());
		item.setValue(node);

		AFCNodeLocation location = node.getImageLocation();
		ImageGraphicItem iamgeitem = new ImageGraphicItem(item, location.getX(), location.getY(), location.getAngle());

		iamgeitem.addImage("/com/insigma/afc/ui/monitor/lable_green.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/lable_yellow.png");
		iamgeitem.addImage("/com/insigma/afc/ui/monitor/lable_red.png");

		location = node.getTextLocation();
		TextGraphicItem textitem = new TextGraphicItem(item, location.getX(), location.getY(), location.getAngle());
		textitem.setText(node.name());

		item.addGraphicItem(iamgeitem);
		item.addGraphicItem(textitem);

		return item;
	}

	public AbstractGraphicMapBuilder() {
		super();
	}

}