/* 
 * 日期：2011-7-13
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.style;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.graphics.Color;

/**
 * Ticket: 
 * @author  DLF
 *
 */
public class StyleFormatAdapter implements IStyleFormat {

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Color color(List<?> dataList, Column column, Object value) {
		return null;
	}

	public String format(List<?> dataList, Column column, Object value) {
		if (value != null) {
			if (value instanceof Date) {
				return df.format((Date) value);
			} else {
				return value.toString();
			}
		}
		return null;
	}

}
