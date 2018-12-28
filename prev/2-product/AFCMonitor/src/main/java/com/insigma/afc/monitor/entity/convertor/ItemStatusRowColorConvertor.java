package com.insigma.afc.monitor.entity.convertor;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.constant.ColorConstants;
import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.monitor.listview.equstatus.StationStatustViewItem;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.RowColorConvertor;

public class ItemStatusRowColorConvertor implements RowColorConvertor {

	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		StationStatustViewItem eventCtr = (StationStatustViewItem) rowObject;
		long currentMode = eventCtr.getMode();
		Integer alarmNum = (Integer) AFCApplication.getData(SystemConfigKey.ALARM_THRESHHOLD);
		if (alarmNum == null) {
			alarmNum = 0;
		}
		Integer warningNum = (Integer) AFCApplication.getData(SystemConfigKey.WARNING_THRESHHOLD);
		if (warningNum == null) {
			warningNum = 0;
		}
		if (eventCtr.isOnline()) {
			if (eventCtr.getAlarmEvent() < alarmNum && eventCtr.getAlarmEvent() >= warningNum) {
				return ColorConstants.COLOR_WARN;
			} else if (eventCtr.getAlarmEvent() >= alarmNum) {
				return ColorConstants.COLOR_ERROR;
			} else {
				return ColorConstants.COLOR_NORMAL;
			}
		}
		return null;
	}

	@Override
	public Color getBackgroundColor(List<?> rows, Object rowObject, ColumnViewData view) {
		// TODO Auto-generated method stub
		return null;
	}

}
