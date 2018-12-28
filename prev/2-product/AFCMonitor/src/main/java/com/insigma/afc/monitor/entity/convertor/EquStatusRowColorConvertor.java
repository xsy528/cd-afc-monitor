package com.insigma.afc.monitor.entity.convertor;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.ColorConstants;
import com.insigma.afc.dic.DeviceStatus;
import com.insigma.afc.monitor.listview.equstatus.EquStatusViewItem;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.RowColorConvertor;

public class EquStatusRowColorConvertor implements RowColorConvertor {

	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		EquStatusViewItem equStatus = (EquStatusViewItem) rowObject;
		int equstatus = equStatus.getStatus();
		if (equstatus == DeviceStatus.NORMAL) {
			return ColorConstants.COLOR_NORMAL;
		} else if (equstatus == DeviceStatus.WARNING) {
			return ColorConstants.COLOR_WARN;
		} else if (equstatus == DeviceStatus.ALARM) {
			return ColorConstants.COLOR_ERROR;
		} else {
			return null;
		}
	}

	@Override
	public Color getBackgroundColor(List<?> rows, Object rowObject, ColumnViewData view) {
		// TODO Auto-generated method stub
		return null;
	}

}
