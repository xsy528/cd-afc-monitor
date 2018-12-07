package com.insigma.afc.monitor.entity.convertor;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.ColorConstants;
import com.insigma.afc.dic.AFCMackCode;
import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.RowColorConvertor;

public class CmdResultRowColorConvertor implements RowColorConvertor {

	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		TmoCmdResult eventCtr = (TmoCmdResult) rowObject;
		short result = eventCtr.getCmdResult().shortValue();
		if (result == AFCMackCode.MACK_SUCCESS.shortValue()) {
			return ColorConstants.COLOR_NORMAL;
		} else {
			return ColorConstants.COLOR_ERROR;
		}
	}

	@Override
	public Color getBackgroundColor(List<?> rows, Object rowObject, ColumnViewData view) {
		// TODO Auto-generated method stub
		return null;
	}

}
