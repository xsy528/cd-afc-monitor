/* 
 * 日期：Aug 13, 2009
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.task;

public interface TaskCallback<T> {
	public void finish(T result);
}
