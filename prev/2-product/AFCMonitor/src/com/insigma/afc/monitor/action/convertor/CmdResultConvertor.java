/* 
 * 日期：2012-6-28
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.action.convertor;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.ColorConstants;
import com.insigma.afc.monitor.action.CommandResult;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

public class CmdResultConvertor extends ConvertorAdapter {

	@Override
	public Color getFrontColor(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		CommandResult cmdResult = (CommandResult) rowObject;
		if (cmdResult.getResult() != 0) {
			return ColorConstants.COLOR_ERROR;
		}
		return ColorConstants.COLOR_NORMAL;

	}
}
