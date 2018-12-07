/* 
 * 日期：2014-12-2
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

//import com.insigma.acc.clear.define.SingleTransactionType;
import com.insigma.acc.wz.xdr.typedef.TransactionType_t;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket:单边交易涉及的交易类型
 * 
 * @author hexingyue
 */
//@Dic(overClass = SingleTransactionType.class)
public class WZSingleTransactionType extends Definition {
	private static WZSingleTransactionType instance = new WZSingleTransactionType();

	private WZSingleTransactionType() {
	}

	public static WZSingleTransactionType getInstance() {
		return instance;
	}

	/** 单程票发售 */
	@DicItem(name = "单程票发售", group = "SINGLE")
	public static Integer SJT_SALE = TransactionType_t.TX_SJTSale;

	/** 单程票退票 */
	@DicItem(name = "单程票即时退票", group = "SINGLE")
	public static Integer SJT_RETURN = TransactionType_t.TX_SJTRefund;

	/** 单程票出站 */
	@DicItem(name = "一票通出站", group = "SINGLE")
	public static Integer SJT_EXIT = TransactionType_t.TX_TicketExit;

}
