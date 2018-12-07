package com.insigma.afc.monitor.action.convertor;

import com.insigma.afc.monitor.entity.TmoEquEvent;
import com.insigma.afc.monitor.entity.TmoEquEventCur;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

public class EventNameConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {

		if (rowObject != null && (rowObject instanceof TmoEquEventCur)) {
			TmoEquEventCur tmoEquEvent = (TmoEquEventCur) rowObject;
			return tmoEquEvent.getEventType() + "/" + itemObject;
		}

		if (rowObject != null && (rowObject instanceof TmoEquEvent)) {
			TmoEquEvent tmoEquEvent = (TmoEquEvent) rowObject;
			return tmoEquEvent.getEventType() + "/" + itemObject;
		}

		return null;
	}
}
