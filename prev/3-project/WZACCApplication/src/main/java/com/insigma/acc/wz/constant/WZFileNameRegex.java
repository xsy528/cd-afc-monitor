/* 
 * 日期：2012-4-9
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.constant;

import com.insigma.acc.wz.define.DeviceFileType;

/**
 * Ticket: 文件名称正则表达式
 * 
 * @author Zhou-Xiaowei
 */
public class WZFileNameRegex {

	public static String MAP_FILE_REGEX = "EquNode_\\p{XDigit}{2}_\\p{XDigit}{2}.\\p{XDigit}{6}";

	public static String AUT_FILE_REGEX = "SystemRight_\\p{XDigit}{2}.\\p{XDigit}{6}";

	public static String STOCKOUTIN_FIlE_REGEX = "StockInOutBill_\\p{XDigit}{4}_\\p{Digit}{14}_\\p{XDigit}{2}.\\p{XDigit}{6}";

	public static String STOCKSNAPSHOT_FILE_REGEX = "StockSnapshot_\\p{XDigit}{4}_\\p{Digit}{8}_\\p{XDigit}{2}";

	public static String SOFT_DEVICE_REGEX = "^0[1|0]_(BOM|TVM|AGM|ISM|PCA)_(01|02|03)_[0-9]{4}_.{1,20}";

	public static String SOFT_TP_REGEX = "^0[1|0]_(01|02|03)_[0-9]{4}_.{1,20}";

	public static String getSoftRegex(Short[] lineIds, Integer[] fileTypes, Integer[] suppliers) {
		String softDeviceRegex = "[0-9]{4}_.{1,20}";
		String softTPRegex = "[0-9]{4}_.{1,20}";

		String lineId = "01|02|03";
		String fileType = "BOM|TVM|AGM|ISM|PCA";
		String supplier = "01|00";

		boolean isDevice = false;
		boolean isTp = false;

		if (fileTypes != null && fileTypes.length > 0) {
			boolean flag = false;
			for (int i = 0; i < fileTypes.length; i++) {
				if (fileTypes[i].intValue() != DeviceFileType.FILE_TP_Soft) {
					if (flag) {
						fileType += "|";
					} else {
						fileType = "";
						isDevice = true;
						flag = true;
					}
					if (fileTypes[i] == DeviceFileType.FILE_TVM_Soft) {
						fileType += "TVM";
					} else if (fileTypes[i] == DeviceFileType.FILE_BOM_Soft) {
						fileType += "BOM";
					} else if (fileTypes[i] == DeviceFileType.FILE_AGM_Soft) {
						fileType += "AGM";
					} else if (fileTypes[i] == DeviceFileType.FILE_ISM_Soft) {
						fileType += "ISM";
					}else if (fileTypes[i] == DeviceFileType.FILE_PCA_Soft) {
						fileType += "PCA";
					}
				} else {
					isTp = true;
				}
			}
		}

		if (lineIds != null && lineIds.length > 0) {
			lineId = "";
			for (int i = 0; i < lineIds.length; i++) {
				if (i > 0) {
					lineId += "|";
				}
				lineId += String.format("%02d", lineIds[i]);
			}
		}

		if (suppliers != null && suppliers.length > 0) {
			supplier = "";
			for (int i = 0; i < suppliers.length; i++) {
				if (i > 0) {
					supplier += "|";
				}
				supplier += String.format("%02d", suppliers[i]);
			}
		}

		softDeviceRegex = "^(" + supplier + ")_" + "(" + fileType + ")_" + "(" + lineId + ")_" + softDeviceRegex;
		softTPRegex = "^(" + supplier + ")_" + "(" + lineId + ")_" + softTPRegex;

		String regex = "";
		if (fileTypes == null || (isDevice && isTp)) {
			regex = "(" + softDeviceRegex + ")|(" + softTPRegex + ")";
		} else if (isDevice) {
			regex = softDeviceRegex;
		} else if (isTp) {
			regex = softTPRegex;
		}

		return regex;
	}


}
