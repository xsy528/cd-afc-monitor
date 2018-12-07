/* 
 * 日期：2017年8月14日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.entity.convertor;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.ColorConstants;
import com.insigma.afc.monitor.search.TmoModeBroadcastViewItem;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.RowColorConvertor;

/**
 * Ticket: 
 * @author  wangzezhi
 *
 */
public class ModeBroadcastRowColorConvertor implements RowColorConvertor {

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.convert.RowColorConvertor#getFrontColor(java.util.List, java.lang.Object, com.insigma.commons.ui.anotation.data.ColumnViewData)
	 */
	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		TmoModeBroadcastViewItem modeBroadcast = (TmoModeBroadcastViewItem) rowObject;
		Color color = ColorConstants.COLOR_NORMAL;
		if (null != modeBroadcast && "失败".equals(modeBroadcast.getBroadcastStatus())) {
			color = ColorConstants.COLOR_ERROR;
		} else if (null != modeBroadcast && "未确认".equals(modeBroadcast.getBroadcastStatus())) {
			color = ColorConstants.COLOR_WARN;
		} else if (null != modeBroadcast && "成功".equals(modeBroadcast.getBroadcastStatus())) {
			color = ColorConstants.COLOR_NORMAL;
		} else {
			color = ColorConstants.COLOR_ERROR;
		}
		return color;
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.convert.RowColorConvertor#getBackgroundColor(java.util.List, java.lang.Object, com.insigma.commons.ui.anotation.data.ColumnViewData)
	 */
	@Override
	public Color getBackgroundColor(List<?> rows, Object rowObject, ColumnViewData view) {
		return null;
	}

}
