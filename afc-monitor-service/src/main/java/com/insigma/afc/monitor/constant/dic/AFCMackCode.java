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

	@DicItem(name = "成功",index = 0)
	public static Integer MACK_SUCCESS = 0x00;

	@DicItem(name = "业务执行失败",index = 1)
	public static Integer MACK_FAIL = 0x01;

	@DicItem(name = "报文格式错误",index = 2)
	public static Integer MACK_PACKAGE_ERROR = 0x02;

	@DicItem(name = "无效的消息分类/类型码",index = 3)
	public static Integer MACK_VOID_MESSAGE_CODE = 0x03;

	@DicItem(name = "无效的数值范围",index = 4)
	public static Integer MACK_VOID_NUMBER_RANGE = 0x04;

	@DicItem(name = "目标节点不可到达",index = 5)
	public static Integer MACK_VOID_NODE = 0x05;

	@DicItem(name = "下游节点应答超时",index = 6)
	public static Integer MACK_LOWER_NODE_RESP_TIMEOUT = 0x06;

	@DicItem(name = "记录数超过消息包范围",index = 7)
	public static Integer MACK_MSG_EXCEED = 0x07;

	@DicItem(name = "消息包中的记录重复",index = 8)
	public static Integer MACK_MSG_REPEAT = 0x08;

	@DicItem(name = "记录数与消息包长度不符",index = 9)
	public static Integer MACK_MSG_NUM_NO_MATCH = 0x09;

	@DicItem(name = "交易明细或寄存器采集时间无效",index = 11)
	public static Integer MACK_MSG_INVALID = 0x0B;

	@DicItem(name = "非期望的参数版本号",index = 16)
	public static Integer MACK_UNEXPECTED_VERSION = 0x10;

	@DicItem(name = "非期望的节点类型",index = 17)
	public static Integer MACK_UNEXPECTED_NODE = 0x11;

	@DicItem(name = "重复申请退款",index = 18)
	public static Integer MACK_REPEAT_REFUND = 0x12;

	@DicItem(name = "文件打开/读取失败",index = 240)
	public static Integer MACK_FILE_OP_FAILED = 0xF0;

	@DicItem(name = "文件写入失败",index = 241)
	public static Integer MACK_FILE_WR_FAILED = 0xF1;

	@DicItem(name = "其它未定义的错误",index = 255)
	public static Integer MACK_NO_DEFINE_ERROR = 0xFF;

}
