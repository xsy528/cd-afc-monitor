/* 
 * 日期：2012-2-1
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import java.io.Serializable;

import com.insigma.afc.dic.AFCUpLoadDataFileType;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 
 * Ticket:  上传的文件位信息
 * @author  wangzezhi
 *
 */
@Dic(overClass = AFCUpLoadDataFileType.class)
public class WZDeviceFileUploadType implements Serializable {

	private static final long serialVersionUID = 1L;

	@DicItem(name = "一票通交易文件")
	public static Integer YPTTransFile = 1 << 0;

	@DicItem(name = "一卡通交易文件")
	public static Integer citizenBusCardTransFile = 1 << 1;

	@DicItem(name = "手机卡交易文件")
	public static Integer cellPhoneCardTransFile = 1 << 2;

	@DicItem(name = "银行卡交易文件")
	public static Integer unionPayCardTransFile = 1 << 3;

	@DicItem(name = "二维码交易文件")
	public static Integer qrCodeCardTransFile = 1 << 5;

	@DicItem(name = "设备审计文件")
	public static Integer deviceAuditFile = 1 << 4;

}
