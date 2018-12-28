/* 
 * 日期：2010-10-11
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.entity.convertor;

import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * 创建时间 2010-10-11 下午04:53:05<br>
 * 描述：<br>
 * Ticket: 
 * @author  DingLuofeng
 *
 */
public class TagValueConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		String pow = String.valueOf(itemObject);
		if ("0".equals(pow)) {
			return "否";
		}
		return "是";
	}
}
