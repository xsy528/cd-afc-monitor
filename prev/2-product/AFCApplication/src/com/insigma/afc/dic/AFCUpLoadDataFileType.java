/* 
 * 日期：2010-11-15
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import java.io.Serializable;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = AFCUpLoadDataFileType.class)
public class AFCUpLoadDataFileType extends Definition implements Serializable {
	private static AFCUpLoadDataFileType instance = new AFCUpLoadDataFileType();

	private AFCUpLoadDataFileType() {

	}

	public static AFCUpLoadDataFileType getInstance() {
		return instance;
	}

	@DicItem(name = "单程票交易文件")
	public static Integer singleFile = 0x1;

	@DicItem(name = "储值票交易文件")
	public static Integer StoredFile = 0x2;

	@DicItem(name = "一卡通交易文件")
	public static Integer YKTFile = 0x3;

	@DicItem(name = "收益文件")
	public static Integer proceedsFile = 0x4;

	@DicItem(name = "审计文件")
	public static Integer auditFile = 0x5;

	@DicItem(name = "事件文件")
	public static Integer eventFile = 0x6;

}
