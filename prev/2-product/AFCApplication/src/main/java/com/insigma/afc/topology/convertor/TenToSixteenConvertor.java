/* 
 * 日期：2018年2月6日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.topology.convertor;

import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * Ticket: 十进制转十六进制
 * @author chenfuchun
 *
 */
public class TenToSixteenConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {

		if (itemObject != null) {

			if (itemObject instanceof Long) {
				long o = (Long) itemObject;
				if (o == -1) {
					return "--";
				}
				String result = Long.toHexString(o).toUpperCase();
				return result;
			} else if (itemObject instanceof Integer) {
				int o = (Integer) itemObject;
				String result = Long.toHexString(o).toUpperCase();
				return result;
			} else if (itemObject instanceof String) {
				String o = (String) itemObject;
				String result = Long.toHexString(Long.parseLong(o));
				return result;
			}
		}

		return "--";
	}

}
