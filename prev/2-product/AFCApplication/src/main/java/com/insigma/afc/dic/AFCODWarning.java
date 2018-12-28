/*
 * 日期：2007-11-14
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 创建时间 2010-12-23 下午12:51:07 <br>
 * 描述: 客流告警<br>
 * Ticket：
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
@Dic(overClass = AFCODWarning.class)
public class AFCODWarning extends Definition {
	private static AFCODWarning instance = new AFCODWarning();

	public AFCODWarning() {
	}

	public static AFCODWarning getInstance() {
		return instance;
	}

	@DicItem(name = "客流刷新频率", group = "afcodwarning")
	public static Integer s_oDRefreshTime = 30;

	@DicItem(name = "阀值A", group = "afcodwarning")
	public static Integer s_normalValue = 10;

	@DicItem(name = "阀值B", group = "afcodwarning")
	public static Integer s_errorValue = 100;

	// public static Integer[] sysConfig;

}
