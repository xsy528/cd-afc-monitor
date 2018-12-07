/* 
 * 日期：2012-3-6
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.acc.wz.xdr.typedef.TransactionType_t;
import com.insigma.afc.dic.AFCTransactionType;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = AFCTransactionType.class)
public class WZTransactionType {

	@DicItem(name = "单程票发售", group = "YPT", index = 0)
	public final static Integer TX_SJT_Sale = TransactionType_t.TX_SJTSale;/* 单程票发售 */

	@DicItem(name = "非记名储值票发售", group = "YPT", index = 1)
	public final static Integer TX_NonNamedCPUSale = TransactionType_t.TX_NonNamedCPUSale;

	@DicItem(name = "记名储值票发售", group = "YPT", index = 2)
	public final static Integer TX_NamedCPUSalee = TransactionType_t.TX_NamedCPUSale;

	@DicItem(name = "储值票充值", group = "YPT", index = 3)
	public final static Integer TX_CPUCardAddValue = TransactionType_t.TX_CPUCardAddValue;

	@DicItem(name = "单程票即时退票", group = "YPT", index = 4)
	public final static Integer TX_SJTRefund = TransactionType_t.TX_SJTRefund;

	@DicItem(name = "储值票即时退票", group = "YPT", index = 5)
	public final static Integer TX_CPUCardImmediateRefund = TransactionType_t.TX_CPUCardImmediateRefund;

	@DicItem(name = "储值票非即时退票", group = "YPT", index = 6)
	public final static Integer TX_CPUCardNonImmediateRefund = TransactionType_t.TX_CPUCardNonImmediateRefund;

	@DicItem(name = "储值票延期", group = "YPT", index = 7)
	public final static Integer TX_TicketValidityPeriod = TransactionType_t.TX_TicketValidityPeriod;

	@DicItem(name = "储值票锁卡", group = "YPT", index = 8)
	public final static Integer TX_CPUCardBlock = TransactionType_t.TX_CPUCardBlock;

	@DicItem(name = "储值票解锁", group = "YPT", index = 9)
	public final static Integer TX_CPUCardUnblock = TransactionType_t.TX_CPUCardUnblock;

	@DicItem(name = "一票通更新", group = "YPT", index = 10)
	public final static Integer TX_TicketSurcharge = TransactionType_t.TX_TicketSurcharge;

	@DicItem(name = "一票通进站", group = "YPT", index = 11)
	public final static Integer TX_TicketEntry = TransactionType_t.TX_TicketEntry;

	@DicItem(name = "一票通出站", group = "YPT", index = 12)
	public final static Integer TX_TicketExit = TransactionType_t.TX_TicketExit;

	@DicItem(name = "储值票扣款（预留）", group = "YPT", index = 13)
	public final static Integer TX_CPUCardDeduction = TransactionType_t.TX_CPUCardDeduction;

	@DicItem(name = "一卡通售卖（预留）", group = "YKT", index = 14)
	public final static Integer TX_YKTSale = TransactionType_t.TX_YKTSale;

	@DicItem(name = "一卡通充值（预留）", group = "YKT", index = 15)
	public final static Integer TX_YKTCardAddValue = TransactionType_t.TX_YKTCardAddValue;

	@DicItem(name = "一卡通锁卡（预留）", group = "YKT", index = 16)
	public final static Integer TX_YKTCardBlock = TransactionType_t.TX_YKTCardBlock;

	@DicItem(name = "一卡通解锁（预留）", group = "YKT", index = 17)
	public final static Integer TX_YKTCardUnblock = TransactionType_t.TX_YKTCardUnblock;

	@DicItem(name = "一卡通更新", group = "YKT", index = 18)
	public final static Integer TX_YKTCardSurcharge = TransactionType_t.TX_YKTCardSurcharge;

	@DicItem(name = "一卡通进站", group = "YKT", index = 19)
	public final static Integer TX_YKTCardEntry = TransactionType_t.TX_YKTCardEntry;

	@DicItem(name = "一卡通出站", group = "YKT", index = 20)
	public final static Integer TX_YKTCardExit = TransactionType_t.TX_YKTCardExit;

	@DicItem(name = "一卡通扣款（预留）", group = "YKT", index = 21)
	public final static Integer TX_YKTCardDeduction = TransactionType_t.TX_YKTCardDeduction;

	@DicItem(name = "手机卡更新", group = "CELLPHONE", index = 22)
	public final static Integer TX_MobileSurcharge = TransactionType_t.TX_MobileSurcharge;

	@DicItem(name = "手机卡进站", group = "CELLPHONE", index = 23)
	public final static Integer TX_MobileEntry = TransactionType_t.TX_MobileEntry;

	@DicItem(name = "手机卡出站", group = "CELLPHONE", index = 24)
	public final static Integer TX_MobileExit = TransactionType_t.TX_MobileExit;

	@DicItem(name = "手机卡扣款 （预留） ", group = "CELLPHONE", index = 25)
	public final static Integer TX_MobileDeduction = TransactionType_t.TX_MobileDeduction;

	@DicItem(name = "银行卡扣款", group = "BANKCARD", index = 26)
	public final static Integer TX_BankCardDeduction = TransactionType_t.TX_BankCardDeduction;

	@DicItem(name = "单程票初始化", group = "ES", index = 27)
	public final static Integer TX_SJTInit = TransactionType_t.TX_SJTInit;

	@DicItem(name = "储值票初始化", group = "ES", index = 28)
	public final static Integer TX_CPUCardInit = TransactionType_t.TX_CPUCardInit;

	@DicItem(name = "票卡注销 ", group = "ES", index = 29)
	public final static Integer TX_TicketCancel = TransactionType_t.TX_TicketCancel;

	@DicItem(name = "储值票个性化", group = "ES", index = 30)
	public final static Integer TX_CPUCardPersonal = TransactionType_t.TX_CPUCardPersonal;

	@DicItem(name = "预赋值单程票抵销 ", group = "ES", index = 31)
	public final static Integer TX_PrePaySJTOffset = TransactionType_t.TX_PrePaySJTOffset;

	@DicItem(name = "银行卡更新", group = "BANKCARD", index = 32)
	public final static Integer TX_BankCardSurcharge = TransactionType_t.TX_BankCardSurcharge;

	@DicItem(name = "银行卡进站", group = "BANKCARD", index = 33)
	public final static Integer TX_BankCardEntry = TransactionType_t.TX_BankCardEntry;

	@DicItem(name = "银行卡出站", group = "BANKCARD", index = 34)
	public final static Integer TX_BankCardExit = TransactionType_t.TX_BankCardExit;

	@DicItem(name = "二维码更新", group = "QR", index = 32)
	public final static Integer TX_QRSurcharge = TransactionType_t.TX_QRCodeSurcharge;

	@DicItem(name = "二维码进站", group = "QR", index = 33)
	public final static Integer TX_QREntry = TransactionType_t.TX_QRCodeEntry;

	@DicItem(name = "二维码出站", group = "QR", index = 34)
	public final static Integer TX_QRExit = TransactionType_t.TX_QRCodeExit;
}
