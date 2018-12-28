/* 
 * 日期：2011-7-13
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.style;

import java.util.List;

import org.eclipse.swt.graphics.Color;

/**
 * Ticket: 
 * @author  DLF
 *
 */
public interface IStyleFormat {

	public String format(List<?> dataList, Column column, Object value);

	public Color color(List<?> dataList, Column column, Object value);

}
