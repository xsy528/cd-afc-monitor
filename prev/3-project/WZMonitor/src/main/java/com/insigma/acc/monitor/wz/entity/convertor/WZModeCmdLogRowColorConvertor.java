package com.insigma.acc.monitor.wz.entity.convertor;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.acc.wz.monitor.handler.WZModeCmdLogViewItem;
import com.insigma.afc.constant.ColorConstants;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.RowColorConvertor;

/* 
 * 日期：2017年8月14日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */

/**
 * Ticket: 
 * @author  wangzezhi
 *
 */
public class WZModeCmdLogRowColorConvertor implements RowColorConvertor {

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.convert.RowColorConvertor#getFrontColor(java.util.List, java.lang.Object, com.insigma.commons.ui.anotation.data.ColumnViewData)
	 */
	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		WZModeCmdLogViewItem modeCmdLog = (WZModeCmdLogViewItem) rowObject;
		Color color = ColorConstants.COLOR_NORMAL;
		if (null != modeCmdLog && 0 == modeCmdLog.getCmdResultCode()) {
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
