/* 
 * 日期：2011-1-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.acc.wz.xdr.typedef.FileHeaderTag_t;
import com.insigma.afc.dic.AFCDeviceFileType;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 设备产生的交易文件类型
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = AFCDeviceFileType.class)
public class DeviceFileType {

	private static DeviceFileType instance = new DeviceFileType();

	public static DeviceFileType getInstance() {
		return instance;
	}

	public static final String SOFT = "SoftFile";
	public static final String DATA = "DataFile";
	// 交易数据文件
	@DicItem(name = "UA", desc = "一票通交易文件", group = DATA, index = 0)
	public static final int UD_TICKET = FileHeaderTag_t.FILE_YPT_TRANSACTION;

	@DicItem(name = "UY", desc = "一卡通交易文件", group = DATA, index = 1)
	public static final int UD_CARD = FileHeaderTag_t.FILE_YKT_TRANSACTION;

	@DicItem(name = "UM", desc = "手机票交易文件", group = DATA, index = 2)
	public static final int UD_MOBILE = FileHeaderTag_t.FILE_MOBILE_TRANSACTION;

	@DicItem(name = "UB", desc = "银行卡交易文件", group = DATA, index = 3)
	public static final int UD_BANK = FileHeaderTag_t.FILE_BANK_TRANSACTION;

	@DicItem(name = "UQ", desc = "二维码交易文件", group = DATA, index = 9)
	public static final int UD_QR = FileHeaderTag_t.FILE_QRCODE_TRANSACTION;

	// 审计数据文件
	@DicItem(name = "AG", desc = "AGM审计文件", group = DATA, index = 4)
	public final static int FILE_AGM_AUDIT_REGISTER = FileHeaderTag_t.FILE_AGM_AUDIT_REGISTER;

	@DicItem(name = "AB", desc = "BOM审计文件", group = DATA, index = 5)
	public final static int FILE_BOM_AUDIT_REGISTER = FileHeaderTag_t.FILE_BOM_AUDIT_REGISTER;

	@DicItem(name = "AT", desc = "TVM审计文件", group = DATA, index = 6)
	public final static int FILE_TVM_AUDIT_REGISTER = FileHeaderTag_t.FILE_TVM_AUDIT_REGISTER;

	@DicItem(name = "AS", desc = "ISM审计文件", group = DATA, index = 7)
	public final static int FILE_ISM_AUDIT_REGISTER = FileHeaderTag_t.FILE_ISM_AUDIT_REGISTER;

	@DicItem(name = "AP", desc = "PCA审计文件", group = DATA, index = 8)
	public final static int FILE_PCA_AUDIT_REGISTER = FileHeaderTag_t.FILE_PCA_AUDIT_REGISTER;

	@DicItem(name = "StockInOutBill", desc = "出入库清单", group = DATA, index = 9)
	public final static int FILE_STOCK_INOUT_BILL = FileHeaderTag_t.FILE_STOCK_INOUT_BILL;

	@DicItem(name = "StockSnapshot", desc = "日终库存信息", group = DATA, index = 10)
	public final static int FILE_ENDOFDAY_STOCK_SANPSHOT = FileHeaderTag_t.FILE_ENDOFDAY_STOCK_SANPSHOT;

	//设备程序文件
	@DicItem(name = "TVM软件程序文件", key = "TVM", desc = "TS", group = SOFT)
	public final static int FILE_TVM_Soft = FileHeaderTag_t.FILE_TVM_Soft;

	@DicItem(name = "BOM软件程序文件", key = "BOM", desc = "BS", group = SOFT)
	public final static int FILE_BOM_Soft = FileHeaderTag_t.FILE_BOM_Soft;

	@DicItem(name = "AGM软件程序文件", key = "AGM", desc = "AS", group = SOFT)
	public final static int FILE_AGM_Soft = FileHeaderTag_t.FILE_AGM_Soft;

	@DicItem(name = "ISM软件程序文件", key = "ISM", desc = "IS", group = SOFT)
	public final static int FILE_ISM_Soft = FileHeaderTag_t.FILE_ISM_Soft;

	@DicItem(name = "TP软件程序文件", key = "TP", desc = "PS", group = SOFT)
	public final static int FILE_TP_Soft = FileHeaderTag_t.FILE_TP_Soft;

	@DicItem(name = "PCA软件程序文件", key = "PCA", desc = "PS", group = SOFT)
	public final static int FILE_PCA_Soft = FileHeaderTag_t.FILE_PCA_Soft;

	// ES数据文件
	@DicItem(name = "ES交易文件")
	public final static int FILE_ES_TRANSACTION = FileHeaderTag_t.FILE_ES_TRANSACTION;

	@DicItem(name = "ES编码文件")
	public final static int FILE_ES_ENCODING = FileHeaderTag_t.FILE_ES_ENCODING;

}
