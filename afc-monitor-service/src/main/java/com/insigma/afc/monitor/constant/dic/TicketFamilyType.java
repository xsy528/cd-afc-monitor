/*
 * 日期：2011-6-1
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */

package com.insigma.afc.monitor.constant.dic;

import com.insigma.afc.monitor.constant.xdr.typedef.TicketFamilyType_t;
import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * Ticket:票种大类定义
 * 
 * @author Zhou-Xiaowei
 */
@Dic(overClass = AFCTicketFamily.class)
public class TicketFamilyType extends Definition {

	private static TicketFamilyType instance = new TicketFamilyType();

	public static TicketFamilyType getInstance() {
		return instance;
	}

	@DicItem(name = "天府通储值票", group = "TF_TFT", index = 0)
	public static Integer TF_SavingCard = TicketFamilyType_t.TFT_TFTSavingCard;

	@DicItem(name = "天府通现金卡", group = "TF_TFT", index = 1)
	public static Integer TF_CashCard = TicketFamilyType_t.TFT_TFTCashCard;

	@DicItem(name = "天府通学生卡", group = "TF_TFT", index = 2)
	public static Integer TF_StudentCard = TicketFamilyType_t.TFT_TFTStudentCard;

	@DicItem(name = "天府通老年卡", group = "TF_TFT", index = 3)
	public static Integer TF_ConventionCard = TicketFamilyType_t.TFT_TFTTheOldCard;

	@DicItem(name = "天府通会展卡", group = "TF_TFT", index = 4)
	public static Integer TF_TheOldCard = TicketFamilyType_t.TFT_TFTConventionCard;

	@DicItem(name = "天府通第三方机构卡", group = "TF_TFT", index = 5)
	public static Integer TF_ThirdPartyCard = TicketFamilyType_t.TFT_TFTThirdPartyCard;

	@DicItem(name = "天府通互通发卡", group = "TF_TFT", index = 6)
	public static Integer TF_InterconnectCard = TicketFamilyType_t.TFT_TFTInterconnectCard;

	@DicItem(name = "蓉城人才绿卡", group = "TF_TFT", index = 7)
	public static Integer TFT_TalentGreenCard = TicketFamilyType_t.TFT_TFTTalentGreenCard;

	@DicItem(name = "天府通普通CPU卡", group = "TF_TFT", index = 8)
	public static Integer TFT_TFTCommonCard = TicketFamilyType_t.TFT_TFTCommonCard;

	@DicItem(name = "天府通二维码电子票卡", group = "TF_TFTQR", index = 9)
	public static Integer TFT_TFTQrTicket = TicketFamilyType_t.TFT_TFTQrTicket;

	@DicItem(name = "天府通金融IC卡", group = "TF_TFTIC", index = 10)
	public static Integer TF_FinanceICCard = TicketFamilyType_t.TFT_TFTFinanceICCard;

	@DicItem(name = "计次纪念票", group = "TF_SJT", index = 11)
	public static Integer TF_CountSouvenirTicket = TicketFamilyType_t.TFT_CountSouvenirTicket;

	@DicItem(name = "定期纪念票", group = "TF_SJT", index = 12)
	public static Integer TF_RegularSouvenirTicket = TicketFamilyType_t.TFT_RegularSouvenirTicket;

	@DicItem(name = "定值纪念票", group = "TF_SJT", index = 13)
	public static Integer TF_FixedSouvenirTicket = TicketFamilyType_t.TFT_FixedSouvenirTicket;

	@DicItem(name = "定值纪念票1", group = "TF_SJT", index = 14)
	public static Integer TF_FixedSouvenirTicket1 = TicketFamilyType_t.TFT_FixedSouvenirTicket1;

	@DicItem(name = "可充限期次票", group = "TF_SJT", index = 15)
	public static Integer RechargeTimeLimitTicket = TicketFamilyType_t.TFT_RechargeTimeLimitTicket;

	@DicItem(name = "定值纪念票2", group = "TF_SJT", index = 16)
	public static Integer TF_FixedSouvenirTicket2 = TicketFamilyType_t.TFT_FixedSouvenirTicket2;

	@DicItem(name = "日次票", group = "TF_SJT", index = 17)
	public static Integer TF_DayCount = TicketFamilyType_t.TFT_DailyTicket;

	@DicItem(name = "外服储值卡", group = "TF_STAFF", index = 18)
	public static Integer TF_ServiceValueTicket = TicketFamilyType_t.TFT_ServiceValueTicket;

	@DicItem(name = "外服卡", group = "TF_STAFF", index = 19)
	public static Integer TF_ServiceTicket = TicketFamilyType_t.TFT_ServiceCard;

	@DicItem(name = "工作卡", group = "TF_STAFF", index = 20)
	public static Integer TF_WorkTicket = TicketFamilyType_t.TFT_WorkCard;

	@DicItem(name = "公安卡", group = "TF_STAFF", index = 21)
	public static Integer TF_PoliceCard = TicketFamilyType_t.TFT_PoliceCard;

	@DicItem(name = "员工票", group = "TF_STAFF", index = 22)
	public static Integer TF_StaffCard = TicketFamilyType_t.TFT_StaffCard;

	@DicItem(name = "特殊员工票", group = "TF_STAFF", index = 23)
	public static Integer TF_SpecialStaffCard = TicketFamilyType_t.TFT_SpecialStaffCard;

	@DicItem(name = "零元票", group = "TF_SJT", index = 24)
	public static Integer FreeTicket = TicketFamilyType_t.TFT_ZeroTicket;

	@DicItem(name = "单程票/出站票/预制票", group = "TF_SJT", index = 25)
	public static Integer SINGLE_JOURNEY_T = TicketFamilyType_t.TFT_SJTTicket;

	@DicItem(name = "单程纪念票", group = "TF_SJT", index = 26)
	public static Integer SINGLE_SONVENIR_T = TicketFamilyType_t.TFT_SJTSouvenirTicket;

	@DicItem(name = "地铁二维码电子票", group = "TF_METROQR", index = 27)
	public static Integer Qr_Ticket_ForMetro = TicketFamilyType_t.TFT_TFTQrTicketForMetro;

}
