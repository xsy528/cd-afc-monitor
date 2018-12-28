/* 
 * 日期：2011-11-28
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic;

import java.util.Comparator;

import com.insigma.commons.dic.annotation.DicItem;

public final class DicitemEntry {
	public final static Comparator<DicitemEntry> DICCMPR = new Comparator<DicitemEntry>() {

		public int compare(DicitemEntry o1, DicitemEntry o2) {
			return o1.dicitem.index() - o2.dicitem.index();
		}
	};

	/**
	 * 字段名称
	 */
	public String fieldName;

	/**
	 * 定义的字典项
	 */
	public DicItem dicitem;

	/**
	 * 字段的值
	 */
	public Object value;

	/**
	 * @param fieldName
	 * @param dicitem
	 * @param value
	 */
	public DicitemEntry(String fieldName, DicItem dicitem, Object value) {
		super();
		this.fieldName = fieldName;
		this.dicitem = dicitem;
		this.value = value;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("field:").append(fieldName).append(",value:").append(",dicitem:")
				.append(dicitem).toString();
	}

}