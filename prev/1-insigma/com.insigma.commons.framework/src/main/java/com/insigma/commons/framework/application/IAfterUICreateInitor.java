/* 
 * 日期：2010-11-4
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.framework.application;

import com.insigma.commons.framework.view.FrameworkWindow;

/**
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public interface IAfterUICreateInitor {

	public abstract void init(FrameworkWindow mainFrame);

	abstract String getName();
}
