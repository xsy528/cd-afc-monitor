/* 
 * 日期：2011-8-1
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

/**
 * Ticket: 票务管理相关报文处理结果类型
 * 
 * @author Zhou-Xiaowei
 */
public class WZTicketHandlerInfo {

	public final static String EMPTY = "卡帐户表中不存在此申请卡的账户信息";

	public final static String MORE_THAN_ONE = "卡帐户表中此申请卡的账户信息不唯一";

	public final static String LOST_ALREADY_FLAGED = "申请卡已处于黑卡状态";

	public final static String DISLOST_ALREADY_FLAGED = "申请卡已处于非黑卡状态";

	public final static String CARD_ALREADY_CANCELED = "申请卡已注销";

	public final static String TICKET_CARD_EMPTY = "申请卡卡号为空";

	public final static String APP_NO_ERROR = "申请单编号格式错误";

	public final static String APP_DEVICE_ID_EMPTY = "申请设备ID为空";
	
	public final static String APP_EXISTED="申请已存在";
}
