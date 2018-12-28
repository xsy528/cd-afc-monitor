/* 
 * 日期：2012-7-11
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 一卡通文件类型
 * 
 * @author chenhangwen
 */
@Dic(overClass = WZYKTFileType.class)
public class WZYKTFileType extends Definition {

	private static WZYKTFileType instance = new WZYKTFileType();

	public static WZYKTFileType getInstance() {
		return instance;
	}

	// 消费文件
	@DicItem(name = "消费明细文件", group = "upload")
	public final static String XF_FILE_TYPE = "XF";

	//	// 售卡充值明细文件
	//	@DicItem(name = "售卡充值明细文件", group = "upload")
	//	public final static int FS_FILE_TYPE = 1002;

	// 消费调整及拒付明细文件
	@DicItem(name = "消费调整及拒付明细文件", group = "download")
	public final static String SS_FILE_TYPE = "SS";

	// 消费清算文件
	@DicItem(name = "消费清算文件", group = "download")
	public final static String QS_FILE_TYPE = "QS";

	// 已清算消费文件名文件
	@DicItem(name = "已清算消费文件名文件", group = "download")
	public final static String NS_FILE_TYPE = "NS";

	//	// 售充拒付明细文件
	//	@DicItem(name = "售充拒付明细文件", group = "download")
	//	public final static int CF_FILE_TYPE = 2101;
	//
	//	// 售充清算文件
	//	@DicItem(name = "售充清算文件", group = "download")
	//	public final static int CR_FILE_TYPE = 2102;
	//
	//	// 已清算售充文件名文件
	//	@DicItem(name = "已清算售充文件名文件", group = "download")
	//	public final static int CN_FILE_TYPE = 2103;
	//
	//	// 售充对账不平明细文件
	//	@DicItem(name = "售充对账不平明细文件", group = "download")
	//	public final static int CS_FILE_TYPE = 2201;
	//
	//	// 白名单文件
	//	@DicItem(name = "白名单文件", group = "download")
	//	public final static int WL_FILE_TYPE = 3001;
	//
	//	// 卡类型参数文件
	//	@DicItem(name = "可用卡类型参数文件", group = "download")
	//	public final static int CT_FILE_TYPE = 3002;

	// 通知文件(临时定义)
	// @DicItem(name = "通知文件", group = "download")
	// public final static int NT_FILE_TYPE = 4001;

	//	// 黑名单文件(临时定义)
	//	@DicItem(name = "黑名单文件", group = "download")
	//	public final static int BL_FILE_TYPE = 5001;

}
