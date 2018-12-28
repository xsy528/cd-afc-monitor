/* 
 * 日期：Jan 8, 2008
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.task;

/**
 * Ticket:
 * 
 * @author jiangxf
 * 
 */
public interface ITaskCallback {

	public void error();

	public void finished();

	public void setProgress(int progress);
}
