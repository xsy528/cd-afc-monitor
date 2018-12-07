package com.insigma.afc.monitor.map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MapItemFactory {

	private static final Log S_LOG = LogFactory.getLog(MapItemFactory.class);

	public enum MapItemType {
		STATION(0), POST(1), GATE(2), TVM(3), GATE_ENTRY(4), GATE_EXIT(5), GATE_BOTH(6), GATE_WIDE(7), MODULE(8), AVM(
				9), TCM(10), TSM(11), TFM(12), GATE_SD(15);

		private final int typeCode;

		MapItemType(int typeCode) {
			this.typeCode = typeCode;
		}

		public int getTypeCode() {
			return typeCode;
		}
	};

	public enum ItemStatusType {
		NORMAL(0), WARNING(1), ALARM(2), CLOSE(3), OFFLINE(4), ONLINE(5), MODE_NORMAL(6), MODE_DEMOTION(
				7), MODE_URGENCY(8);

		private final int statusCode;

		ItemStatusType(int statusCode) {
			this.statusCode = statusCode;
		}

		public int getStatusCode() {
			return statusCode;
		}
	};

	//	public static MapItem getMapItem(MetroMapItem metroMapItem) {
	//		if (metroMapItem.getMapType() == MapItemType.STATION.getTypeCode()) {
	//			return getStation(metroMapItem);
	//		} else if (metroMapItem.getMapType() == MapItemType.MODULE.getTypeCode()) {
	//			return getModule(metroMapItem);
	//		}
	//
	//		return getDeviceItem(metroMapItem);
	//	}
	//
	//	public static MapItem getStation(MetroMapItem station) {
	//		MetroDevice metroDevice = new MetroDevice();
	//		MapItem item = new MapItem();
	//		item.setValue(metroDevice);
	//		// item, 50, 20
	//		ImageGraphicItem iamgeitem = new ImageGraphicItem(item, (int) station.getPosX(), (int) station.getPosY(),
	//				station.getAngle());
	//
	//		// NORMAL(0), WARNING(1), ALARM(2), CLOSE(3),
	//		// OFFLINE(4),ONLINE(5),MODE_NORMAL(6),MODE_DEMOTION(7),MODE_URGENCY(8);
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/green.png");
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/yellow_s.png");
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/red.png");
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/stop_s.png");
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/grey.png");
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/green.png");
	//
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/mode_normal.png");
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/mode_demotion.png");
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/urgency_s.png");
	//
	//		TextGraphicItem textitem = new TextGraphicItem(item, (int) station.getTextPosX(), (int) station.getTextPosY());
	//		textitem.setText(station.getItemName());
	//
	//		item.getItems().add(iamgeitem);
	//		item.getItems().add(textitem);
	//		return item;
	//	}
	//
	//	private static MapItem getDeviceItem(MetroMapItem deviecItem) {
	//		MapItem item = new MapItem();
	//		item.setValue(deviecItem);
	//
	//		ImageGraphicItem iamgeitem = new ImageGraphicItem(item, (int) deviecItem.getPosX(), (int) deviecItem.getPosY(),
	//				deviecItem.getAngle());
	//
	//		if (deviecItem.getMapType() == MapItemType.POST.getTypeCode()) {
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomgreen.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomyellow.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomred.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomclose.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomoffline.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomgreen.png");
	//		} else if (deviecItem.getMapType() == MapItemType.GATE.getTypeCode()) {
	//			if (deviecItem.getSubMapType() == MapItemType.GATE_ENTRY.getTypeCode()) {
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_green.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_yellow.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_red.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_close.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_grey.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_green.png");
	//			} else if (deviecItem.getSubMapType() == MapItemType.GATE_EXIT.getTypeCode()) {
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_greenX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_yellowX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_redX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_closeX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_greyX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_greenX.png");
	//			} else if (deviecItem.getSubMapType() == MapItemType.GATE_BOTH.getTypeCode()) {
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_green.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_yellow.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_red.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_close.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_grey.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_green.png");
	//			} else if (deviecItem.getSubMapType() == MapItemType.GATE_WIDE.getTypeCode()) {
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBgreen.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGByellow.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBred.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBclose.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBgrey.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBgreen.png");
	//			} else if (deviecItem.getSubMapType() == MapItemType.GATE_SD.getTypeCode()) {
	//
	//				short lineid = Short.parseShort(String
	//						.format("%08d", Long.parseLong(Long.toHexString(deviecItem.getItemId()))).substring(0, 2));
	//				if (lineid == 2) {
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_GREEN.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_YELLOW.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_RED.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_CLOSE.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_GRAY.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_GREEN.png");
	//				} else {
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_GREEN.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_YELLOW.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_RED.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_CLOSE.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_GRAY.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_GREEN.png");
	//				}
	//			} else {
	//				S_LOG.warn("GATE不存在设备子类型=" + deviecItem.getSubMapType());
	//				return null;
	//			}
	//		} else if (deviecItem.getMapType() == MapItemType.TVM.getTypeCode()) {
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMgreen.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMyellow.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMred.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMclose.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMoffline.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMgreen.png");
	//		} else if (deviecItem.getMapType() == MapItemType.AVM.getTypeCode()) {
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/AVMgreen.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/AVMyellow.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/AVMred.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/AVMclose.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/AVMoffline.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/AVMgreen.png");
	//		} else if (deviecItem.getMapType() == MapItemType.TCM.getTypeCode()) {
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TCMgreen.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TCMyellow.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TCMred.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TCMclose.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TCMoffline.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TCMgreen.png");
	//		} else if (deviecItem.getMapType() == MapItemType.TSM.getTypeCode()) {
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TSMgreen.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TSMyellow.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TSMred.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TSMclose.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TSMoffline.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TSMgreen.png");
	//		} else if (deviecItem.getMapType() == MapItemType.TFM.getTypeCode()) {
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TFMgreen.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TFMyellow.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TFMred.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TFMclose.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TFMoffline.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TFMgreen.png");
	//		} else {
	//			S_LOG.warn("不存在设备类型=" + deviecItem.getMapType());
	//			return null;
	//		}
	//
	//		TextGraphicItem textitem = new TextGraphicItem(item, (int) deviecItem.getTextPosX(),
	//				(int) deviecItem.getTextPosY());
	//		textitem.setText(deviecItem.getItemName());
	//
	//		item.getItems().add(iamgeitem);
	//		item.getItems().add(textitem);
	//		return item;
	//	}
	//
	//	public static MapItem getModule(MetroMapItem module) {
	//		MapItem item = new MapItem();
	//
	//		ImageGraphicItem iamgeitem = new ImageGraphicItem(item, (int) module.getPosX(), (int) module.getPosY(),
	//				module.getAngle());
	//
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/lable_green.png");
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/lable_yellow.png");
	//		iamgeitem.addImage("/com/insigma/afc/monitor/images/lable_red.png");
	//
	//		TextGraphicItem textitem = new TextGraphicItem(item, (int) module.getTextPosX(), (int) module.getTextPosY());
	//		textitem.setText(module.getItemName());
	//
	//		item.getItems().add(iamgeitem);
	//		item.getItems().add(textitem);
	//
	//		return item;
	//	}
	//
	//	public static MapItem getDevice(MetroDevice metroDevice) {
	//		MapItem item = new MapItem();
	//		item.setValue(metroDevice);
	//
	//		float angle = 0;
	//
	//		ImageGraphicItem iamgeitem = new ImageGraphicItem(item, metroDevice.getPosX(), metroDevice.getPosY(), angle);
	//		int textPosX = metroDevice.getPosX();
	//		int textPosY = metroDevice.getPosY();
	//
	//		if (metroDevice.getDeviceType() == AFCDeviceType.POST.shortValue()) {
	//			textPosY = textPosY + 20;
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomgreen.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomyellow.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomred.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomclose.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomoffline.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/bomgreen.png");
	//		} else if (metroDevice.getDeviceType() == AFCDeviceType.GATE.shortValue()) {
	//			textPosY = textPosY + 30;
	//			if (metroDevice.getDeviceSubType() == AFCDeviceSubType.DEVICE_GATE_ENTRY.shortValue()) {
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_green.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_yellow.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_red.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_close.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_grey.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_green.png");
	//			} else if (metroDevice.getDeviceSubType() == AFCDeviceSubType.DEVICE_GATE_EXIT.shortValue()) {
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_greenX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_yellowX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_redX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_closeX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_greyX.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/s_greenX.png");
	//			} else if (metroDevice.getDeviceSubType() == AFCDeviceSubType.DEVICE_GATE_BOTH.shortValue()) {
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_green.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_yellow.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_red.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_close.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_grey.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/d_green.png");
	//			} else if (metroDevice.getDeviceSubType() == AFCDeviceSubType.DEVICE_GATE_WIDE.shortValue()) {
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBgreen.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGByellow.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBred.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBclose.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBgrey.png");
	//				iamgeitem.addImage("/com/insigma/afc/monitor/images/WGBgreen.png");
	//			} else if (metroDevice.getDeviceSubType() == AFCDeviceSubType.DEVICE_GATE_SD.shortValue()) {
	//				short lineid = Short.parseShort(
	//						String.format("%08d", Long.parseLong(Long.toHexString(metroDevice.getId().getLineId()))));
	//				if (lineid == 2) {
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_GREEN.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_YELLOW.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_RED.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_CLOSE.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_GRAY.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_" + lineid + "_GREEN.png");
	//				} else {
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_GREEN.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_YELLOW.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_RED.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_CLOSE.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_GRAY.png");
	//					iamgeitem.addImage("/com/insigma/afc/monitor/images/DST_SD_GREEN.png");
	//				}
	//			} else {
	//				S_LOG.warn("GATE不存在设备子类型=" + metroDevice.getDeviceSubType());
	//				return null;
	//			}
	//		} else if (metroDevice.getDeviceType() == AFCDeviceType.TVM.shortValue()) {
	//			textPosY = textPosY + 25;
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMgreen.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMyellow.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMred.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMclose.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMoffline.png");
	//			iamgeitem.addImage("/com/insigma/afc/monitor/images/TVMgreen.png");
	//		} else {
	//			S_LOG.warn("不存在设备类型=" + metroDevice.getDeviceType());
	//			return null;
	//		}
	//
	//		TextGraphicItem textitem = new TextGraphicItem(item, textPosX, textPosY);
	//		textitem.setText(metroDevice.getDeviceName());
	//
	//		item.getItems().add(iamgeitem);
	//		item.getItems().add(textitem);
	//		return item;
	//	}
}
