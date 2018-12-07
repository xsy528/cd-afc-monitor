/**
 * 
 */
package com.insigma.afc.topology.convertor;

import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * @author shenchao
 *
 */
public class DeviceModuleStatusConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {

		if (itemObject == null) {
			return "未知";
		}

		Short status = Short.valueOf(itemObject.toString());

		if (status >= 2) {
			return "报警";
		} else if (status >= 1) {
			return "警告";
		} else {
			return "正常";
		}
	}

}
