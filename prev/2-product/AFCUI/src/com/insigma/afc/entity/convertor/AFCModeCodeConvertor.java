package com.insigma.afc.entity.convertor;

import org.eclipse.swt.graphics.Color;

import com.insigma.afc.dic.AFCModeCode;
import com.insigma.commons.ui.anotation.data.ColumnViewData;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * 
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public class AFCModeCodeConvertor extends ConvertorAdapter {

	@Override
	public String getText(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {

		if (itemObject != null) {
			Integer modeCode = getValue(itemObject);

			if (modeCode != null) {
				return AFCModeCode.getInstance().getNameByValue(modeCode) + "/" + String.format("0x%02x", modeCode);
			}
		}
		//		if (itemObject instanceof String) {
		//			//			String currentModeCode = (String) itemObject;
		//			//
		//			//			String[] modeCollection = currentModeCode.split("\\_");
		//			//			currentModeCode = "";
		//			//			for (String element : modeCollection) {
		//			//				if (Integer.valueOf(element) != 0) {
		//			//					currentModeCode = currentModeCode + String.format("%02d", Integer.valueOf(element));
		//			//				}
		//			//			}
		//			//
		//			//			if (currentModeCode == "") {
		//			//				currentModeCode = "0";
		//			//			}
		//			//			return ModeCodeConvert(Long.valueOf(currentModeCode));
		//		}
		//		if (itemObject instanceof Number) {
		//
		//			//			Long key = Long.valueOf(itemObject.toString());
		//			//
		//			//			return ModeCodeConvert(key);
		//
		//		}

		return "未知模式/" + itemObject == null ? "" : itemObject.toString();
	}

	@Override
	public Color getBackgroundColor(Object rowObject, Object itemObject, int row, int column, ColumnViewData view) {
		//		if (itemObject instanceof Number) {
		//			Number key = (Number) itemObject;
		//			if (AFCModeCode.NORMAL_MODE_CODE.intValue() != key.intValue()) {
		//				return SWTResourceManager.getColor(SWT.COLOR_RED);
		//			}
		//		}
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
		} else if (object instanceof String) {
			return Integer.valueOf(object.toString());
		}

		return null;
	}

	//石家庄特殊性，特别处理modecode=1、2、9的状态
	//	private String ModeCodeConvert(Long modeCode) {
	//		if (modeCode == -1) {
	//			return "正常模式/0";
	//		}
	//		String Text = "";
	//		if (modeCode == 0) {
	//			Text = "正常模式/" + modeCode.toString();
	//			return Text;
	//		} else if (modeCode == 1) {
	//			Text = "正常服务模式/" + modeCode.toString();
	//			return Text;
	//		} else if (modeCode == 2) {
	//			Text = "关站服务模式/" + modeCode.toString();
	//			return Text;
	//		} else if (modeCode == 9) {
	//			Text = "紧急模式/" + modeCode.toString();
	//			return Text;
	//		} else {
	//			Long temp = modeCode;
	//			while (true) {
	//				Text = Text + AFCModeCode.getInstance().getNameByValue(Integer.valueOf((int) (temp % 100))) + "/";
	//				temp = temp / 100;
	//				if (temp == 0) {
	//					break;
	//				}
	//			}
	//
	//			return Text + modeCode;
	//		}
	//	}
}
