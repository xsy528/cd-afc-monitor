package com.insigma.afc.monitor.entity.convertor;

import java.util.List;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.constant.ColorConstants;
import com.insigma.afc.dic.AFCModeCode;
import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.RowColorConvertor;

public class BroadcastRowColorConvertor implements RowColorConvertor {

	@Override
	public Color getFrontColor(List<?> rows, Object rowObject, ColumnViewData view) {
		TmoModeBroadcast eventCtr = (TmoModeBroadcast) rowObject;
		Integer modeCode = getValue(eventCtr.getModeCode());
		if (modeCode instanceof Integer) {
			int statusLevel = Integer.valueOf(((Object) eventCtr.getModeCode()).toString());
			if (statusLevel == AFCModeCode.NORMAL_MODE_CODE) {
				return ColorConstants.COLOR_NORMAL;
			} else if (statusLevel > AFCModeCode.NORMAL_MODE_CODE
					&& statusLevel < AFCModeCode.START_URGENCY_MODE_CODE) {
				return ColorConstants.COLOR_WARN;
			} else {
				return ColorConstants.COLOR_ERROR;
			}
		} else {
			//适用于成都项目
			String modeCodeForStr = ((Object) eventCtr.getModeCode()).toString();
			String[] statusLevel = modeCodeForStr.split("\\_");
			boolean isNomal = true;
			for (int i = 0; i < statusLevel.length; i++) {
				//紧急模式的模式代号为3
				if (Integer.valueOf(statusLevel[i]) == 3) {
					return ColorConstants.COLOR_ERROR;
				} else if (Integer.valueOf(statusLevel[i]) != 0) {
					isNomal = false;
				} else {
					continue;
				}

			}
			if (isNomal) {
				return ColorConstants.COLOR_NORMAL;
			} else {
				return ColorConstants.COLOR_WARN;
			}

		}
	}

	@Override
	public Color getBackgroundColor(List<?> rows, Object rowObject, ColumnViewData view) {
		// TODO Auto-generated method stub
		return null;
	}

	private Integer getValue(Object object) {
		if (object == null) {
			return null;
		} else if (object instanceof Short) {
			return ((Short) object).intValue();
		} else if (object instanceof Integer) {
			return (Integer) object;
		} else if (object instanceof Long) {
			return ((Long) object).intValue();
		}

		return null;
	}

}
