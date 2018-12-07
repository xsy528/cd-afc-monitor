/* 
 * 日期：2011-7-22
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.monitor;

import com.insigma.afc.monitor.ICommandFactory.Command;
import com.insigma.commons.framework.function.form.Form;

/**
 * Ticket:
 * 
 * @author 董飞
 */
public class ExtCommand extends Command {

	public ExtCommand(int id, String text) {
		super(id, text);

	}

	/**
	 * 
	 * @param id
	 * @param text
	 * @param form
	 */
	public ExtCommand(int id, String text, Form form) {
		super(id, text, form);
	}

}
