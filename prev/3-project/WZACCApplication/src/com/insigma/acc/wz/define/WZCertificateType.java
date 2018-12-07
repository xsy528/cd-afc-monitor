/* 
 * $Source: $
 * $Revision: $ 
 * $Date: $
 * 
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.acc.wz.define;

import com.insigma.afc.dic.AFCCertificateType;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 产品级的乘客证件类型
 * 
 * @author CaiChunye
 */
@Dic(overClass = AFCCertificateType.class)
public class WZCertificateType extends Definition {

	private static WZCertificateType instance = new WZCertificateType();

	public static WZCertificateType getInstance() {
		return instance;
	}

	/** 身份证 */
	@DicItem(name = "身份证", group = "IDCARDTYPE")
	public static Integer IDENTTITY_CARD = 0;

	/** 社保卡 */
	@DicItem(name = "社保卡", group = "IDCARDTYPE")
	public static Integer SOCIAL_CARD = 1;

	/** 学生证 */
	@DicItem(name = "学生证", group = "IDCARDTYPE")
	public static Integer STUDENT_CARD = 2;

	/** 残疾人证 */
	@DicItem(name = "残疾人证", group = "IDCARDTYPE")
	public static Integer DISABLED_CARD = 3;

	/** 军人证 */
	@DicItem(name = "军人证", group = "IDCARDTYPE")
	public static Integer ARMY_CARD = 4;

	/** 护照 */
	@DicItem(name = "护照", group = "IDCARDTYPE")
	public static Integer PASSPORT = 5;

	/** 入境证（仅限于香港/台湾居 民使用） */
	@DicItem(name = "入境证", group = "IDCARDTYPE")
	public static Integer ARRIVAL_CARD = 6;

	/** 老人证 */
	@DicItem(name = "老人证", group = "IDCARDTYPE")
	public static Integer AGED_PERSON_CARD = 7;

	/** 临时身份证 */
	@DicItem(name = "临时身份证", group = "IDCARDTYPE")
	public static Integer TEMP_IDCARD = 8;

}
