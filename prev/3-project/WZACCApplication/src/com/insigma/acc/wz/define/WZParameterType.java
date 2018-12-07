/* 
 * 日期：2017年6月27日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.acc.wz.xdr.typedef.EODComponentType_t;
import com.insigma.acc.wz.xdr.typedef.FileHeaderTag_t;
import com.insigma.acc.wz.xdr.typedef.RUNComponentType_t;
import com.insigma.afc.dic.AFCParameterType;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 参数类型
 * @author  mengyifan
 *
 */
@Dic(overClass = AFCParameterType.class)
public class WZParameterType {
	private static WZParameterType s_instance = new WZParameterType();

	@DicItem(name = "EOD参数控制文件", group = "1")
	public static final Integer PARM_EOD_ACCMCNF = FileHeaderTag_t.FILE_EOD_CONTROL;

	@DicItem(name = "AFC运营主控文件", group = "1")
	public static final Integer PARM_RUN_ACCMCNF = FileHeaderTag_t.FILE_AFC_MAIN_CONTROL;

	@DicItem(name = "系统参数", index = 1)
	public static final Integer EOD_SYSTEM = EODComponentType_t.EOD_SYSTEM;

	@DicItem(name = "线网信息参数", index = 2)
	public static final Integer EOD_SYSNetwork = EODComponentType_t.EOD_SYSNetwork;

	@DicItem(name = "日历数据", index = 3)
	public static final Integer EOD_SYSCalendar = EODComponentType_t.EOD_SYSCalendar;

	@DicItem(name = "费率表参数", index = 4)
	public static final Integer EOD_SYSFare = EODComponentType_t.EOD_SYSFare;

	@DicItem(name = "车票参数", index = 5)
	public static final Integer EOD_SYSTicket = EODComponentType_t.EOD_SYSTicket;

	@DicItem(name = "优惠方案参数", index = 6)
	public static final Integer EOD_Concession = EODComponentType_t.EOD_Concession;

	@DicItem(name = "AGM设备参数", index = 7)
	public static final Integer EOD_AGM = EODComponentType_t.EOD_AGM;

	@DicItem(name = "RG通行控制参数", index = 8)
	public static final Integer EOD_RG = EODComponentType_t.EOD_RG;

	@DicItem(name = "BOM设备参数", index = 9)
	public static final Integer EOD_BOM = EODComponentType_t.EOD_BOM;

	@DicItem(name = "TVM设备参数", index = 10)
	public static final Integer EOD_TVM = EODComponentType_t.EOD_TVM;

	@DicItem(name = "TVM界面参数", index = 11)
	public static final Integer EOD_TVM_GUI = EODComponentType_t.EOD_TVM_GUI;

	@DicItem(name = "ISM参数", index = 12)
	public static final Integer EOD_ISM = EODComponentType_t.EOD_ISM;

	@DicItem(name = "PCA参数", index = 13)
	public static final Integer EOD_PCA = EODComponentType_t.EOD_PCA;

	@DicItem(name = "一票通全量明细黑名单", index = 14)
	public static final Integer RUN_BLKACCFULLLIST = RUNComponentType_t.RUN_BLKACCFULLLIST;

	@DicItem(name = "一票通增量明细黑名单", index = 15)
	public static final Integer RUN_BLKACCINCRLIST = RUNComponentType_t.RUN_BLKACCINCRLIST;

	@DicItem(name = "一票通全量号段黑名单", index = 16)
	public static final Integer RUN_BLKACCFULLSECTLIST = RUNComponentType_t.RUN_BLKACCFULLSECTLIST;

	//	@DicItem(name = "一卡通全量明细黑名单", index = 17)
	//	public static final Integer RUN_BLKYKTFULLLIST = RUNComponentType_t.RUN_BLKYKTFULLLIST;

	@DicItem(name = "员工卡全量明细黑名单", index = 17)
	public static final Integer RUN_BLKSTAFFFULLLIST = RUNComponentType_t.RUN_BLKSTAFFFULLLIST;

	@DicItem(name = "单程票回收条件", index = 18)
	public static final Integer RUN_SJTRECYCLE = RUNComponentType_t.RUN_SJTRECYCLE;

	@DicItem(name = "模式履历", index = 19)
	public static final Integer RUN_WAIVERDATE = RUNComponentType_t.RUN_WAIVERDATE;

	@DicItem(name = "票卡库存目录", index = 19)
	public static final Integer RUN_TICKETCATALOG = RUNComponentType_t.RUN_TICKETCATALOG;

	@DicItem(name = "操作员基本信息文件", index = 20)
	public static final Integer RUN_OPERINFO = RUNComponentType_t.RUN_OPERINFO;

	@DicItem(name = "操作员权限文件", index = 21)
	public static final Integer RUN_OPERRIGHT = RUNComponentType_t.RUN_OPERRIGHT;

	@DicItem(name = "二维码公钥参数文件")
	public static final Integer FILE_QRCODEPKI = FileHeaderTag_t.FILE_QRCODEPKI;

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static WZParameterType getInstance() {
		return s_instance;
	}
}
