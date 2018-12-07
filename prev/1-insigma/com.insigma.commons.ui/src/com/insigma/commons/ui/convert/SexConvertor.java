/* 
 * 日期：2010-9-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.convert;

import com.insigma.commons.ui.anotation.ColumnView;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class SexConvertor extends ConvertorAdapter {

	public String convertToUI(Object object, ColumnView view) {
		if (object == null) {
			return view.nullvalue();
		}
		if (object.equals(0)) {
			return "男";
		}
		return "女";
	}

}
