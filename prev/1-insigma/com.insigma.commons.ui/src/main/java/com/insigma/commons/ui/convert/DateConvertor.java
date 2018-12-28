/*
 * 日期：2013-2-11
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
 * 运营日
 * @author shenchao
 */
public class DateConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		if (itemObject == null) {
			return "--";
		}
		if (itemObject instanceof Date) {
			return DateTimeUtil.formatDate((Date) itemObject, "yyyy-MM-dd");
		}
		return itemObject.toString();
	}
}
