/*
 * 日期：2010-10-11
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.commons.ui.convert;

import java.util.Date;

import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 创建时间 2010-10-29 下午06:08:04 <br>
 * 描述: <br>
 * Ticket：
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class DateTimeConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		if (itemObject == null) {
			return "--";
		}
		if (itemObject instanceof Date) {
			return DateTimeUtil.formatDate((Date) itemObject, "yyyy-MM-dd HH:mm:ss");
		}
		return itemObject.toString();
	}
}
