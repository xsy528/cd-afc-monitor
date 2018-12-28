/* 
 * 日期：2011-1-5
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.dic.loader;

import java.util.List;

/**
 * 需要初始化的字典列表提供者<br/>
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public interface IDicClassListProvider {
	/**
	 * 获取所有的需要初始化的字典列表
	 * 
	 * @return
	 */
	public List<Class<?>> getDicClassList();
}
