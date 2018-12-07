package com.insigma.afc.monitor.entity.convertor;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.ColorConstants;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.RowColorConvertor;

public class UploadInfoRowColorConvertor implements RowColorConvertor {
	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		TmoModeUploadInfo eventCtr = (TmoModeUploadInfo) rowObject;
		Integer statusLevel = eventCtr.getModeCode().intValue();
		//		short flag = eventCtr.getCurrentModeStatus();
		short flag = eventCtr.getModeCode();
		if (flag == 0) {
			return ColorConstants.COLOR_NORMAL;
		} else if (AFCModeCode.getInstance().getCodeMap().containsKey(statusLevel)
				&& statusLevel != AFCModeCode.START_URGENCY_MODE_CODE) {
			return ColorConstants.COLOR_WARN;
		} else {
			return ColorConstants.COLOR_ERROR;
		}

		//		if (statusLevel == AFCModeCode.NORMAL_MODE_CODE) {
		//			return ColorConstants.COLOR_NORMAL;
		//		} else if (statusLevel > AFCModeCode.NORMAL_MODE_CODE && statusLevel < AFCModeCode.START_URGENCY_MODE_CODE) {
		//			return ColorConstants.COLOR_WARN;
		//		} else {
		//			return ColorConstants.COLOR_ERROR;
		//		}
	}

	@Override
	public Color getBackgroundColor(List<?> rows, Object rowObject, ColumnViewData view) {
		// TODO Auto-generated method stub
		return null;
	}
}
