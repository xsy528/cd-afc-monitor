/* 
 * $Source: $
 * $Revision: $ 
 * $Date: $
 * 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.constant;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.swtdesigner.SWTResourceManager;

/**
 * 颜色常量
 * 
 * @author CaiChunye
 */
public class ColorConstants {
	/** 正常级别的颜色 */
	public static final Color COLOR_NORMAL = SWTResourceManager.getColor(new RGB(57, 155, 60));

	/** 警告级别的颜色 */
	public static final Color COLOR_WARN = SWTResourceManager.getColor(new RGB(203, 188, 7));

	/** 报警级别的颜色 */
	public static final Color COLOR_ERROR = SWTResourceManager.getColor(new RGB(255, 0, 0));
}
