/**
 * 
 */
package com.insigma.acc.wz.define;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket: 服务商代码
 * @author lianchuanjie
 *
 */
@Dic(overClass = WZServiceCode.class)
public class WZServiceCode extends Definition {
	private static WZServiceCode instance = new WZServiceCode();

	public static WZServiceCode getInstance() {
		return instance;
	}

	@DicItem(name = "ACC")
	public static int SERVICE_ACC = 0x00;

	@DicItem(name = "市民卡公司")
	public static int SERVICE_CITIZEN_CARD = 0x01;

}
