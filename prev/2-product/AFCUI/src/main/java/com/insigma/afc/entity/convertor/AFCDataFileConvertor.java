package com.insigma.afc.entity.convertor;

import com.insigma.afc.dic.AFCDeviceFileType;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

public class AFCDataFileConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		Short res = (Short) itemObject;
		return AFCDeviceFileType.getInstance().getDicItemByName(res.intValue()).desc();
	}
}
