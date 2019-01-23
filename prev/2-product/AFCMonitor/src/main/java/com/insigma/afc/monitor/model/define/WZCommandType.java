/* 
 * 日期：2017年8月3日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.afc.monitor.model.define;

import com.insigma.afc.workbench.rmi.CommandType;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 
 * @author  wangzezhi
 *
 */
@Dic(overClass = CommandType.class)
public class WZCommandType {

	@DicItem
	public static Integer CMD_SYNC_INFO = 0x01;

	@DicItem
	public static Integer CMD_UPLOAD_FILE = 0x02;

	@DicItem
	public static Integer CMD_CHANGE_MODE = 0x03;

	@DicItem
	public static Integer CMD_TIME_SYNC = 0x1104;

	@DicItem
	public static Integer CMD_QUERY_MODE = 0x05;

	@DicItem
	public static Integer CMD_BROADCAST_MODE = 0x06;

	// 查询设备最新流水号
	@DicItem
	public static Integer CMD_QUERY_DEVICE_SERIAL = 0x07;

	@DicItem
	public static Integer CMD_SYNC_MAP = 0x9999;

	@DicItem
	public static Integer CMD_EXPORT_MAP = 0x1111;

	// 要求下级上传指定文件
	@DicItem
	public static Integer CMD_UPLOAD_SPEC_FILE = 0x1404;

	@DicItem
	public static Integer CMD_CLEAR_TICHET_BOX = 0x9998;

	@DicItem
	public static Integer CMD_EQU_CTRL = 0x3000;

	@DicItem
	public static Integer CMD_TCM_MESSAGE = 0x3006;

	@DicItem
	public static Integer CMD_FILE_REQUEST = 0x3307;

	@DicItem
	public static Integer CMD_UD_REQUEST = 0x3314;

	@DicItem
	public static Integer CMD_QUERY_MONEY_BOX = 0x1308;

	@DicItem
	public static Integer CMD_QUERY_TICKET_BOX = 0x1310;

	@DicItem
	public static Integer COM_SLE_CONTROL_CMD = 0x1103;

	//	@DicItem
	//	public static Integer CMD_QUERY_BOM_AGM_TICKET_BOX = 0x4004;
	//	
	//	@DicItem
	//	public static Integer CMD_QUERY_AVM_MONEY_BOX = 0x4005;

	@DicItem
	public static Integer CMD_BOTHWAY_GATE_ONLY = 0x5000;

	@DicItem
	public static Integer CMD_QUERY_DEVICE_STATUS = 0x4000;

	@DicItem
	public static Integer CMD_WAKE_ON_LAN = 0x4005;

	@DicItem
	public static Integer CMD_QUERY_DEVICE_FILE_VERSION = 0x4006;

	@DicItem
	public static Integer CMD_OPT_VERSION_SYNC = 0x1402;

	@DicItem
	public static Integer CMD_QRKEY_VERSION_QUERY = 0x5201;

	@DicItem
	public static Integer CMD_QRKEY_VERSION_SYNC = 0x5202;

}
