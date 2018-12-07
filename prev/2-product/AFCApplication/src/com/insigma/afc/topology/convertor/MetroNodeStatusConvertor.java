/**
 * 
 */
package com.insigma.afc.topology.convertor;

import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class MetroNodeStatusConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		if (itemObject instanceof Number) {
			Number status = (Number) itemObject;
			String str = null;
			if (status.shortValue() == 0) {
				str = "启用";
			} else if (status.shortValue() == 1) {
				str = "未启用";
			} else {
				str = "未知" + status.shortValue();
			}
			return str;
		}
		return null;
	}

}
