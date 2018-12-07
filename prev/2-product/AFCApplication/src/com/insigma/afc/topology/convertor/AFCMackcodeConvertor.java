package com.insigma.afc.topology.convertor;

import com.insigma.afc.dic.AFCMackCode;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

public class AFCMackcodeConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		Short res = (Short) itemObject;
		return AFCMackCode.getInstance().getNameByValue(res.intValue() & 0xff)
				+ String.format("/0x%02x", res.intValue() & 0xff);
	}
}
