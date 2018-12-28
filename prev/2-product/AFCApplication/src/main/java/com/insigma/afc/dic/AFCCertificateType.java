/* 
 * $Source: $
 * $Revision: $ 
 * $Date: $
 * 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 产品级的乘客证件类型
 * 
 * @author CaiChunye
 */
@Dic(overClass = AFCCertificateType.class)
public class AFCCertificateType extends Definition {

	private static AFCCertificateType instance = new AFCCertificateType();

	public static AFCCertificateType getInstance() {
		return instance;
	}

	/** 身份证 */
	@DicItem(name = "身份证")
	public static Integer IDENTTITY_CARD = 0;

	/** 学生证 */
	@DicItem(name = "学生证")
	public static Integer STUDENT_CARD = 1;

	/** 军官证 */
	@DicItem(name = "军官证")
	public static Integer ARMY_CARD = 3;

	/** 老人证 */
	@DicItem(name = "老人证")
	public static Integer AGED_PERSON_CARD = 4;

	/** 护照 */
	@DicItem(name = "护照")
	public static Integer PASSPORT = 5;

	/** 入境证（仅限于香港/台湾居 民使用） */
	@DicItem(name = "入境证")
	public static Integer ARRIVAL_CARD = 6;
}
