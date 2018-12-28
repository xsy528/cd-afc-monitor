/* 
 * 日期：2011-11-4
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * SAM卡状态 0-备用；1-已用；2-注销 Ticket:
 * 
 * @author shenchao
 */
@Dic(overClass = WZACCSamCardStatus.class)
public class WZACCSamCardStatus extends Definition {

	public static WZReaderCode instance = new WZReaderCode();

	public static WZReaderCode getInstance() {
		return instance;
	}

	@DicItem(name = "备用")
	public final static Integer SAM_STATUS_SPARE = 0x00;

	@DicItem(name = "已用")
	public final static Integer SAM_STATUS_ISDOING = 0x01;

	@DicItem(name = "注销")
	public final static Integer SAM_STATUS_DESTROY = 0x02;

}
