/* 
 * 日期：2010-11-12
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.initor;

import com.insigma.commons.dic.DicOverwriteInitor;

/**
 * 字典定义初始化 Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public class DicSystemInitor extends SystemInitor {

	@Override
	public String getName() {
		return "字典定义初始化";
	}

	@Override
	public boolean init() {
		DicOverwriteInitor.init();
		return true;
	}

	@Override
	public boolean isDaemon() {
		return false;
	}

}
