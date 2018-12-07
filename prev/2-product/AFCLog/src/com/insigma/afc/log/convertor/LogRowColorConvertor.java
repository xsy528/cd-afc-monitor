package com.insigma.afc.log.convertor;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.LogDefines;
import com.insigma.afc.log.result.LogResult;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.RowColorConvertor;

public class LogRowColorConvertor implements RowColorConvertor {

	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		LogResult logResult = (LogResult) rowObject;
		String result = logResult.getLogClass();
		return LogDefines.getLogStatusColor(result);
	}

	@Override
	public Color getBackgroundColor(List<?> rows, Object rowObject, ColumnViewData view) {
		// TODO Auto-generated method stub
		return null;
	}

}
