/* 
 * 日期：2013-1-14
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.constant.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 
 * Ticket: 
 * @author  shenchao
 *
 */
@Dic(overClass = AFCMackCode.class)
public class AFCMackCode extends Definition {

	private static AFCMackCode instance = new AFCMackCode();

	public static AFCMackCode getInstance() {
		return instance;
	}

	/**
	 * 成功
	 */
	@DicItem(name = "成功", index = 1)
	public static Integer MACK_SUCCESS = 0x00;

	/**
	 * 报文格式错误
	 */
	@DicItem(name = "报文格式错误", index = 2)
	public static Integer MACK_PACKAGE_ERROR = 0x01;

	/**
	 * 无效的消息分类/类型码
	 */
	@DicItem(name = "无效的消息分类/类型码", index = 3)
	public static Integer MACK_VOID_MESSAGE_CODE = 0x02;

	/**
	 * 重复交易应答
	 */
	@DicItem(name = "重复交易应答", index = 4)
	public static Integer MACK_REPEAT_TRANS_RESP = 0x03;

	/**
	 * 目标结点不可达
	 */
	@DicItem(name = "目标结点不可达", index = 5)
	public static Integer MACK_TARGET_NODE_UNREACH = 0x04;

	/**
	 * 通信超时
	 */
	@DicItem(name = "通信超时", index = 6)
	public static Integer LOWER_NODE_RESP_TIMEOUT = 0x05;

	/**
	 * 交易未完成
	 */
	@DicItem(name = "交易未完成", index = 7)
	public static Integer MACK_TRANS_UNFINISHED = 0x06;

	/**
	 * 原交易不存在
	 */
	@DicItem(name = "原交易不存在", index = 8)
	public static Integer MACK_ORIGINAL_TRANS_UNEXIT = 0x10;

	/**
	 * 非法节点类型
	 */
	@DicItem(name = "非法节点类型", index = 9)
	public static Integer MACK_ILLEGAL_NODE_TYPE = 0x11;

	/**
	 * 文件名重复
	 */
	//	@DicItem(name = "文件名重复", index = 10)
	//	public static Integer MACK_FILENAME_REPEAT = 0x12;

	/**
	 * 文件不存在
	 */
	//	@DicItem(name = "文件不存在", index = 11)
	//	public static Integer MACK_FILE_UNEXIT = 0x13;

	/**
	 * 文件长度错误
	 */
	//	@DicItem(name = "文件长度错误", index = 12)
	//	public static Integer MACK_VOID_FILE_LENGTH = 0x14;

	/**
	 * 文件校验错误
	 */
	//	@DicItem(name = "文件校验错误", index = 13)
	//	public static Integer MACK_VOID_FILE_CHECK = 0x15;

	/**
	 * 设备信息不存在
	 */
	@DicItem(name = "设备信息不存在", index = 14)
	public static Integer MACK_DEVICE_INFO_UNEXIT = 0x20;

	/**
	 * 设备不可用
	 */
	@DicItem(name = "设备不可用", index = 15)
	public static Integer MACK_DEVICE_UNUSE = 0x21;

	/**
	 * 无效票种
	 */
	@DicItem(name = "无效票种", index = 16)
	public static Integer MACK_IN_VAIN_TICKET = 0x2D;

	/**
	 * 批复/确认数量必须大于0
	 */
	//	@DicItem(name = "批复/确认数量必须大于0", index = 17)
	//	public static Integer MACK_APPROVALOROK_ABOVE_ZERO = 0x2E;

	/**
	 * 批复/确认数量必须小于等于申请/下发数量
	 */
	//	@DicItem(name = "批复/确认数量必须小于等于申请/下发数量", index = 18)
	//	public static Integer MACK_APPROVALOROK_UNABOVE_APPLICATIONORISSUED = 0x2F;

	/**
	 * 票卡账户不存在
	 */
	@DicItem(name = "票卡账户不存在", index = 19)
	public static Integer MACK_TICKET_ACCOUNT_UNEXIT = 0x30;

	/**
	 * MAC1错误
	 */
	@DicItem(name = "MAC1错误", index = 20)
	public static Integer MACK_MAC1_ERROR = 0x31;

	/**
	 * MAC2生成失败
	 */
	@DicItem(name = "MAC2生成失败", index = 21)
	public static Integer MACK_MAC2_PRODUCE_FAIL = 0x32;

	/**
	 * 查询条件不存在
	 */
	@DicItem(name = "查询条件不存在", index = 22)
	public static Integer MACK_QUERY_CONDITION_UNEXIT = 0x33;

	/**
	 * TAC码不正确
	 */
	//	@DicItem(name = "TAC码不正确", index = 23)
	//	public static Integer MACK_MAC2_WRONG = 0xE1;

	/**
	 * TAC码生成错误
	 */
	//	@DicItem(name = "TAC码生成错误", index = 24)
	//	public static Integer MACK_MAC_PRODUCE_WRONG = 0xE2;

	/**
	 * 数据库操作错
	 */
	@DicItem(name = "数据库操作错", index = 25)
	public static Integer MACK_DB_OPERATIONS_ERROR = 0xFA;

	/**
	 * 不在规定的时间之内
	 */
	@DicItem(name = "不在规定的时间之内", index = 26)
	public static Integer MACK_WITHIN_SPECIFIED_TIME = 0xFE;

	/**
	 * 其它未定义的错误
	 */
	@DicItem(name = "其它未定义的错误", index = 27)
	public static Integer UNDEFINED_ERROR = 0xFF;

}
