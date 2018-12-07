package com.insigma.afc.monitor.entity.convertor;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.monitor.entity.TmoEquStatus;
import com.insigma.afc.monitor.entity.TmoEquStatusCur;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.RowColorConvertor;

public class EventCtrRowColorConvertor implements RowColorConvertor {

	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		if (rowObject instanceof TmoEquStatus) {
			TmoEquStatus eventCtr = (TmoEquStatus) rowObject;
			if (eventCtr.getItem1() != null) {
				short statusLevel = eventCtr.getItem1().shortValue();
				return EventCtrConvertorProvider.getEventStatusColor(statusLevel);
			}
		} else if (rowObject instanceof TmoEquStatusCur) {
			TmoEquStatusCur eventCtr = (TmoEquStatusCur) rowObject;
			if (eventCtr.getItem1() != null) {
				short statusLevel = eventCtr.getItem1().shortValue();
				return EventCtrConvertorProvider.getEventStatusColor(statusLevel);
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
