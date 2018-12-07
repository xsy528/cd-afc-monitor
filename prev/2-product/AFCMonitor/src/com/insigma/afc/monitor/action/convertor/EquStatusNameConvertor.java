package com.insigma.afc.monitor.action.convertor;

import com.insigma.afc.monitor.entity.TmoEquStatus;
import com.insigma.afc.monitor.entity.TmoEquStatusCur;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

public class EquStatusNameConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		String retStr = "未知";

		if (rowObject != null && (rowObject instanceof TmoEquStatusCur)) {
			TmoEquStatusCur tmoEquStatus = (TmoEquStatusCur) rowObject;
			retStr = tmoEquStatus.getStatusName() + "/" + tmoEquStatus.getStatusId();
		} else if (rowObject != null && (rowObject instanceof TmoEquStatus)) {
			TmoEquStatus tmoEquStatus = (TmoEquStatus) rowObject;
			retStr = tmoEquStatus.getStatusName() + "/" + tmoEquStatus.getStatusId();
		}

		return retStr;
	}
}