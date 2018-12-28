package com.insigma.afc.monitor.action.convertor;

import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

public class BooleanConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		String retStr = "未知";
		if (itemObject != null && itemObject instanceof Number) {
			Number boValue = (Number) itemObject;
			if (boValue != null) {
				boolean isTrue = (boValue.intValue() == 1) ? true : false;
				if (isTrue) {
					retStr = "是";
				} else {
					retStr = "否";
				}
			}
		}
		return retStr;
	}
}
