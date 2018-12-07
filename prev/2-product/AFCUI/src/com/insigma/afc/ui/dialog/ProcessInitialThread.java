/* 
 * 日期：2010-12-21
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.ui.dialog;

import org.eclipse.swt.widgets.Display;

import com.insigma.commons.ui.InitialThread;

public class ProcessInitialThread extends InitialThread {

	private StringBuffer sb = new StringBuffer();

	public void setMessage(String message) {
		sb.append(message).append("\n");
		this.message = message;
		if (statusChangeListener != null) {
			Display.getDefault().asyncExec(refresh);
		}
	}

	public StringBuffer getAllMessage() {
		return sb;
	}

	public boolean isSuccessful = true;
}