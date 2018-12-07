/* 
 * 日期：2011-3-15
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.application.IllegalType;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = AFCFileIllegalType.class)
public class AFCFileIllegalType extends Definition {

	private static AFCFileIllegalType instance = new AFCFileIllegalType();

	public AFCFileIllegalType() {

	}

	public static AFCFileIllegalType getInstance() {
		return instance;
	}

	@DicItem(name = "文件名格式异常")
	public static Integer FILE_NAME_INVALID = IllegalType.FILE_NAME_INVALID;

	@DicItem(name = "签名不正确")
	public static Integer FILE_SIGNATURE_INVALID = IllegalType.FILE_SIGNATURE_INVALID;

	@DicItem(name = "文件名重复")
	public static Integer FILE_NAME_DUPLICATE = IllegalType.FILE_NAME_DUPLICATE;

	@DicItem(name = "文件内容异常")
	public static Integer FILE_CONTENT_INVALID = IllegalType.FILE_CONTENT_INVALID;

	@DicItem(name = "文件中存在不合法交易")
	public static Integer TRANSACTION_INVALID = IllegalType.TRANSACTION_INVALID;

	@DicItem(name = "交易丢失")
	public static Integer TRANSACTION_LOSS = IllegalType.TRANSACTION_LOSS;

	@DicItem(name = "交易重复")
	public static Integer TRANSACTION_DUPLICATE = IllegalType.TRANSACTION_DUPLICATE;

	@DicItem(name = "文件解压异常")
	public static Integer FILE_EXTRACT_INVALID = IllegalType.FILE_EXTRACT_INVALID;

	@DicItem(name = "未知异常")
	public static Integer OTHER_EXCEPTION = IllegalType.OTHER_EXCEPTION;
}
