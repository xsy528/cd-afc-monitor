/* 
 * 日期：2018年1月15日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.acc.wz.xdr.typedef.FileHeaderTag_t;
import com.insigma.afc.dic.AFCBLKType;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 温州运营参数类型
 * @author chenfuchun
 *
 */
@Dic(overClass = AFCBLKType.class)
public class WZRunParamType {

	private static WZRunParamType instance = new WZRunParamType();

	public static WZRunParamType getInstance() {
		return instance;
	}

	@DicItem(name = "一票通全量黑名单", group = "blkfiletype", index = 0)
	public final static Integer BLK_CCHS_FULL = FileHeaderTag_t.FILE_BLK_ACC_FULL_LIST;

	@DicItem(name = "一票通增量黑名单", group = "blkfiletype", index = 1)
	public final static Integer BLK_CCHS_INCR = FileHeaderTag_t.FILE_BLK_ACC_INCR_LIST;

	@DicItem(name = "一票通号段黑名单", group = "blkfiletype", index = 2)
	public final static Integer BLK_CCHS_RANGE = FileHeaderTag_t.FILE_BLK_ACC_FULL_SECT;

	@DicItem(name = "员工票黑名单", group = "blkfiletype", index = 3)
	public final static Integer BLK_STAFF = FileHeaderTag_t.FILE_BLK_STAFF_FULL_LIST;

	@DicItem(name = "单程票回收条件", group = "sjt")
	public final static Integer SJT = FileHeaderTag_t.FILE_SJT_RECYCLE;

	@DicItem(name = "模式履历", group = "waive")
	public final static Integer WAIVE = FileHeaderTag_t.FILE_WAIVERDATE;

	@DicItem(name = "票卡库存目录", group = "ticket")
	public final static Integer TICKET_CATALOG = FileHeaderTag_t.FILE_TICKET_CATALOG;

	@DicItem(name = "操作员基本参数", group = "optrator")
	public final static Integer OPERATOR_INFO = FileHeaderTag_t.FILE_OPERINFO;

	@DicItem(name = "操作员权限参数", group = "optrator")
	public final static Integer OPERATOR_PERMISSION = FileHeaderTag_t.FILE_OPERRIGHT;

}
