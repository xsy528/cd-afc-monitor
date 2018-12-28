/* 
 * 日期：2018年6月25日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.dialog;

import org.eclipse.swt.widgets.Display;

import com.insigma.commons.ui.SplashDialog;

/**
 * Ticket: 
 * @author chenfuchun
 *
 */
public class WZSplashDialog extends SplashDialog {

	public WZSplashDialog(Display display) {
		super(display);
		height = 375;
		createContents();
	}

}
