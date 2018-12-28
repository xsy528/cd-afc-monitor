/* 
 * 日期：Aug 13, 2009
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.task;

public abstract class Task<T> {

	protected TaskCallback<T> callback;

	public abstract T execute();

	public TaskCallback<T> getCallback() {
		return callback;
	}

	public void setCallback(TaskCallback<T> callback) {
		this.callback = callback;
	}
}
