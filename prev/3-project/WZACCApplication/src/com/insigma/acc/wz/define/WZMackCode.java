/* 
 * 日期：2017年6月6日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.afc.dic.AFCMackCode;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: Mack应答码
 * @author  mengyifan
 *
 */
@Dic(overClass = AFCMackCode.class)
public class WZMackCode {
	@DicItem(name = "成功")
	public static Integer MACK_SUCCESS = 0x00;

	@DicItem(name = "报文格式错误")
	public static Integer MACK_PACKAGE_ERROR = 0x01;

	@DicItem(name = "包长度不匹配")
	public static Integer MACK_HEAD_LENGTH_ERROR = 0x02;

	@DicItem(name = "包头中变长部分长度值与包体中变长部分数据不一致")
	public static Integer MACK_LENGTH_UNMATCH = 0x03;

	@DicItem(name = "无效的消息分类/类型码")
	public static Integer MACK_VOID_MESSAGE_CODE = 0x04;

	@DicItem(name = "无效的数值范围")
	public static Integer MACK_VOID_NUMBER_RANGE = 0x05;

	@DicItem(name = "目标节点不可到达")
	public static Integer MACK_TARGET_NODE_UNREACH = 0x06;

	@DicItem(name = "下游节点应答超时")
	public static Integer LOWER_NODE_RESP_TIMEOUT = 0x06;

	@DicItem(name = "多包消息的固定部分不附")
	public static Integer MACK_MULTI_FIXED_PART_UNMATCH = 0x08;

	@DicItem(name = "记录数超过消息包范围")
	public static Integer MACK_RECORD_NUM_PAST = 0x09;

	@DicItem(name = "报文MAC错误")
	public static Integer MACK_MAC_ERROR = 0x0a;

	@DicItem(name = "设备无法进入正常服务")
	public static Integer MACK_DEVICE_NOT_IN_NORMAL = 0x40;

	@DicItem(name = "无效的设备事件代码")
	public static Integer MACK_VOID_EVENT_TYPE = 0X41;

	@DicItem(name = "无效的文件类型码")
	public static Integer MACK_VOID_FILE_CODE = 0x42;

	@DicItem(name = "无效的运营模式代码")
	public static Integer MACK_VOID_MODE_CODE = 0x43;

	@DicItem(name = "库存不足")
	public static Integer MACK_STORGE_LACK = 0x44;

	@DicItem(name = "无效的节点代码")
	public static Integer MACK_VOID_NODE = 0x45;

	@DicItem(name = "充值BOM/TVM/ISM是黑名单")
	public static Integer MACK_RECHARGE_BOM_IS_BLACK = 0x46;

	@DicItem(name = "充值BOM/TVM/ISM节点和IP信息不匹配")
	public static Integer MACK_RECHARGE_BOM_NO_MATCH_IP = 0x47;

	@DicItem(name = "符合条件票卡帐户不存在")
	public static Integer MACK_ACCOUNT_NOT_EXIST = 0x48;

	@DicItem(name = "非即时退款还未受理")
	public static Integer MACK_UNIM_UN_DEAL = 0x49;

	@DicItem(name = "无效票卡物理号")
	public static Integer MACK_VOID_PHY_CARD = 0x4a;

	@DicItem(name = "无效票卡印刷号")
	public static Integer MACK_VOID_PRINT_CARD = 0x4b;

	//    @DicItem(name = "该票卡充值记录不存在")
	//    public static Integer MACK_TICKET_RECHARGE_RECORD_UNEXEIST = 0x4c;

	//    @DicItem(name = "该票卡交易记录不存在")
	//    public static Integer MACK_TICKET_TRANS_RECORD_UNEXEIST = 0x4d;

	@DicItem(name = "该票卡已经挂失")
	public static Integer MACK_TICKET_IS_LOSS_REPORT = 0x4e;

	@DicItem(name = "该票卡已经解挂")
	public static Integer MACK_TICKET_CANCEL_LOSS = 0x4f;

	@DicItem(name = "该票卡是黑名单票卡")
	public static Integer MACK_TICKET_IS_BLACK = 0x50;

	@DicItem(name = "用户不存在")
	public static Integer MARK_USER_NOT_EXIST = 0x51;

	@DicItem(name = "密码不符")
	public static Integer MARK_USER_PASSWD_NOT_EQUAL = 0x52;

	@DicItem(name = "新密码不符合要求")
	public static Integer MARK_NEW_PASSWD_UNMATCH = 0x53;

	@DicItem(name = "其它未定义的错误")
	public static Integer MACK_UNDEFINE_FILE_FAIL = 0xFF;

	// 以下为与文档不同的编码定义

	//    @DicItem(name = "通信超时")
	//    public static Integer LOWER_NODE_RESP_TIMEOUT = 0x05;

	@DicItem(name = "MD5验证错误")
	public static Integer MACK_VOID_MD5_VALIDATE = 0x06;
}
