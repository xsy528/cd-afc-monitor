package com.insigma.afc.entity.convertor;

import java.util.List;

import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.commons.lang.PairValue;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * 创建时间 2011-1-6 下午09:35:36 <br>
 * 描述: <br>
 * Ticket：
 *
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class DeviceTypeConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		List<PairValue<Object, String>> list = AFCDeviceType.getInstance().getByGroup("SLE");
		for (PairValue<Object, String> pValue : list) {
			if (itemObject.equals(pValue.getKey())) {
				return pValue.getValue();
			}
		}
		return "未知类型";
	}
}
