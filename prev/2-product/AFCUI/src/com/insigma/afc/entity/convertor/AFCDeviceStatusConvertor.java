package com.insigma.afc.entity.convertor;

import com.insigma.afc.dic.DeviceStatus;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * 创建时间 2012-7-18 上午09:29:36 <br>
 * 描述: <br>
 * Ticket：
 *
 * @author liuchao
 */
public class AFCDeviceStatusConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		Short itemTemp = null;
		if (null != itemObject) {
			if (itemObject instanceof Number) {
				itemTemp = ((Number) itemObject).shortValue();
			}
		} else {
			return "未知";
		}
		return DeviceStatus.getInstance().getNameByValue(itemTemp) + "/" + itemTemp.shortValue();
	}
}
