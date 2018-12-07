/* 
 * 日期：2010-8-20
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.acc.wz.xdr.typedef.FileHeaderTag_t;
import com.insigma.afc.dic.AFCFTPFileType;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = AFCFTPFileType.class)
public class WZFTPFileType {
	private static WZFTPFileType instance = new WZFTPFileType();

	public static WZFTPFileType getInstance() {
		return instance;
	}

	// 自定义类型， 文件导入类型定义,工作台使用
	@DicItem(name = "文件导入类型定义")
	public static final Integer IMPORT = 0x99;

	// 自定义类型， 软件文件导出类型定义,工作台使用
	@DicItem(name = "软件文件导出类型定义")
	public static final Integer SOFT = 0x2E;

	// 自定义类型， EOD参数的资源文件
	@DicItem(name = "EOD参数的资源文件")
	public static final Integer EOD_RES_FILE = 0x98;

	// 自定义类型， FTP审计文件
	@DicItem(name = "FTP审计文件")
	public static final Integer FTP_AR_FILE = 0x97;

	// 自定义类型， 结算文件
	@DicItem(name = "结算文件")
	public static final Integer FOOT_FILE = 0x96;

	// 自定义类型， 黑名单文件
	@DicItem(name = "黑名单文件")
	public static final Integer BLK = 0x95;

	// 自定义类型， EOD文件
	@DicItem(name = "EOD文件")
	public static final Integer EOD = 0x20;

	// ACC级参数目录
	@DicItem(name = "清分中心级参数目录")
	public static final Integer ACC_EOD = FileHeaderTag_t.FILE_EOD_CONTROL;

	// 一票通交易文件
	@DicItem(name = "一票通交易文件")
	public static final Integer UD_TICKET = FileHeaderTag_t.FILE_YPT_TRANSACTION;

	/** 市民卡/公交卡交易文件 */
	@DicItem(name = "市民卡/公交卡交易文件")
	public static final Integer UD_CARD = FileHeaderTag_t.FILE_YKT_TRANSACTION;

	/** 手机卡交易文件 */
	@DicItem(name = "手机卡交易文件")
	public static final Integer UD_PHONECARD = FileHeaderTag_t.FILE_MOBILE_TRANSACTION;

	/** 银行卡交易文件 */
	@DicItem(name = "银行卡交易文件")
	public static final Integer UD_BANKCARD = FileHeaderTag_t.FILE_BANK_TRANSACTION;

	/** 二维码交易文件 */
	@DicItem(name = "二维码交易文件")
	public static final Integer UD_QR = FileHeaderTag_t.FILE_QRCODE_TRANSACTION;

	/** AGM审计文件 */
	@DicItem(name = "AGM审计文件")
	public static final Integer AGM_AR = FileHeaderTag_t.FILE_AGM_AUDIT_REGISTER;

	/** BOM审计文件 */
	@DicItem(name = "BOM审计文件")
	public static final Integer BOM_AR = FileHeaderTag_t.FILE_BOM_AUDIT_REGISTER;

	/** TVM审计文件 */
	@DicItem(name = "TVM审计文件")
	public static final Integer TVM_AR = FileHeaderTag_t.FILE_TVM_AUDIT_REGISTER;

	/** ISM审计文件 */
	@DicItem(name = "ISM审计文件")
	public static final Integer ISM_AR = FileHeaderTag_t.FILE_ISM_AUDIT_REGISTER;

	/** PCA审计文件 */
	@DicItem(name = "PCA审计文件")
	public static final Integer PCA_AR = FileHeaderTag_t.FILE_PCA_AUDIT_REGISTER;

	/** ES交易文件 */
	@DicItem(name = "ES交易文件")
	public static final Integer ES_TRANSACTION = FileHeaderTag_t.FILE_ES_TRANSACTION;

	/** ES编码文件 */
	@DicItem(name = "ES编码文件")
	public static final Integer ES_ENCODING = FileHeaderTag_t.FILE_ES_ENCODING;

	@DicItem(name = "日终库存快照")
	public static final Integer ENDOFDAY_STOCK_SANPSHOT = FileHeaderTag_t.FILE_ENDOFDAY_STOCK_SANPSHOT;

	// 库存清单文件
	@DicItem(name = "出入库清单文件")
	public static final Integer STOCK_INOUT_BILL = FileHeaderTag_t.FILE_STOCK_INOUT_BILL;

	@DicItem(name = "AFC运营主控文件")
	public static final Integer AFC_MAIN_CONTROL = FileHeaderTag_t.FILE_AFC_MAIN_CONTROL;

	@DicItem(name = "一票通全量黑名单")
	public static final Integer BLK_ACC_FULL_LIST = FileHeaderTag_t.FILE_BLK_ACC_FULL_LIST;

	@DicItem(name = "一票通增量黑名单")
	public static final Integer BLK_ACC_INCR_LIST = FileHeaderTag_t.FILE_BLK_ACC_INCR_LIST;

	@DicItem(name = "一票通号段黑名单")
	public static final Integer BLK_ACC_FULL_SECT = FileHeaderTag_t.FILE_BLK_ACC_FULL_SECT;

	@DicItem(name = "一卡通全量黑名单")
	public static final Integer BLK_YKT_FULL_LIST = FileHeaderTag_t.FILE_BLK_YKT_FULL_LIST;

	@DicItem(name = "员工卡黑名单")
	public static final Integer BLK_STAFF_FULL_LIST = FileHeaderTag_t.FILE_BLK_STAFF_FULL_LIST;

	@DicItem(name = "单程票回收条件")
	public static final Integer SJT_RECYCLE = FileHeaderTag_t.FILE_SJT_RECYCLE;

	@DicItem(name = "模式履历文件")
	public static final Integer WAIVERDATE = FileHeaderTag_t.FILE_WAIVERDATE;

	@DicItem(name = "票卡库存目录")
	public static final Integer TICKET_CATALOG = FileHeaderTag_t.FILE_TICKET_CATALOG;

	@DicItem(name = "操作员基本信息")
	public static final Integer OPERINFO = FileHeaderTag_t.FILE_OPERINFO;

	@DicItem(name = "操作员权限")
	public static final Integer OPERRIGHT = FileHeaderTag_t.FILE_OPERRIGHT;

	@DicItem(name = "车站设备节点配置信息文件 ;")
	public static final Integer Map = FileHeaderTag_t.FILE_STATION_EQUNODE_CONFIG;

	@DicItem(name = "日终库存差异")
	public static final Integer ENDOFDAY_STOCK_DIFF = FileHeaderTag_t.FILE_ENDOFDAY_STOCK_DIFF;

	@DicItem(name = "交易对账文件")
	public static final Integer TRANS_ACCOUNT = FileHeaderTag_t.FILE_TRANS_ACCOUNT;

	@DicItem(name = "可疑交易处理文件")
	public static final Integer DOUBTFULDEAL_RESULT = FileHeaderTag_t.FILE_DOUBTFULDEAL_RESULT;

	@DicItem(name = "与一卡通对账文件")
	public static final Integer COMPARISON_WITH_YKT = FileHeaderTag_t.FILE_COMPARISON_WITH_YKT;

	@DicItem(name = "与移动运营商对账文件")
	public static final Integer COMPARISON_WITH_MOBILE = FileHeaderTag_t.FILE_COMPARISON_WITH_MOBILE;

	@DicItem(name = "与银联对账文件")
	public static final Integer COMPARISON_WITH_BANK = FileHeaderTag_t.FILE_COMPARISON_WITH_BANK;

	//
	//	@DicItem(name = "通信前置机获取文件处理FTP配置,UD类型")
	//	public static final Integer FTP_UD_FILESERVER_TAG = 0x100;

	@DicItem(name = "ES个性化文件")
	public static final Integer ES_PERSON_FILE_TYPE = 0x201;

	@DicItem(name = "ESTP文件")
	public static final Integer ES_TP_FILE_TYPE = 0x202;

	@DicItem(name = "ES初始化文件")
	public static final Integer ES_SVTINIT_FILE_TYPE = 0x206;

	@DicItem(name = "ES设备参数")
	public static final Integer ES_PARAMETER_TYPE = 0x203;

	@DicItem(name = "一卡通下载")
	public static final Integer YKT_DOWNLOAD = 0x204;

	@DicItem(name = "一卡通上传")
	public static final Integer YKT_UPLOAD = 0x205;

	@DicItem(name = "二维码公钥参数文件")
	public static final Integer QRCODEPKI = FileHeaderTag_t.FILE_QRCODEPKI;
}
