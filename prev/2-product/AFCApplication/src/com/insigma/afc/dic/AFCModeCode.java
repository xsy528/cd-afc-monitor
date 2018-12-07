package com.insigma.afc.dic;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 产品级模式代码<br/>
 * Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
@Dic(overClass = AFCModeCode.class)
public class AFCModeCode extends Definition {

	public final static String MODE_SIGN_NORMAL = "正常";

	public final static String MODE_SIGN_DESCEND = "降级";

	public final static String MODE_SIGN_URGENCY = "紧急";

	public final static String MODE_SIGN_BREAKDOWN = "故障";

	private static AFCModeCode instance = new AFCModeCode();

	public static AFCModeCode getInstance() {
		return instance;
	}

	@DicItem(name = "正常模式", group = AFCModeCode.MODE_SIGN_NORMAL)
	public final static Integer NORMAL_MODE_CODE = 0x00;

	@DicItem(name = "关闭模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public final static Integer TRAIN_SHUTDOWN_MODE_CODE = 0x01;

	@DicItem(name = "进站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public final static Integer ENTER_NO_CHECK_MODE_CODE = 0x02;

	@DicItem(name = "出站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public final static Integer EXIT_NO_CHECK_MODE_CODE = 0x03;

	@DicItem(name = "时间免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public final static Integer TIME_NO_CHECK_MODE_CODE = 0x04;

	@DicItem(name = "日期免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public final static Integer DAY_NO_CHECK_MODE_CODE = 0x05;

	@DicItem(name = "车费免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public final static Integer FEE_FREE_NO_CHECK_MODE_CODE = 0x06;

	@DicItem(name = "列车故障模式", group = AFCModeCode.MODE_SIGN_BREAKDOWN)
	public final static Integer TRAIN_BREAK_DOWN_MODE_CODE = 0x07;

	@DicItem(name = "紧急放行模式", group = AFCModeCode.MODE_SIGN_URGENCY)
	public final static Integer START_URGENCY_MODE_CODE = 0x08;

	//    @DicItem(name = "正常运行模式", group = AFCModeCode.MODE_SIGN_NORMAL)
	//    public static Integer NORMAL_MODE_CODE = 0x00;
	//
	//    @DicItem(name = "临时关站模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	//    public static Integer TRAIN_SHUTDOWN_MODE_CODE = 0x01;
	//
	//    @DicItem(name = "进站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	//    public static Integer ENTER_NO_CHECK_MODE_CODE = 0x02;
	//
	//    @DicItem(name = "出站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	//    public static Integer EXIT_NO_CHECK_MODE_CODE = 0x03;
	//    
	//	@DicItem(name = "进出站次序免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	//	public static Integer ENTER_RODER_NO_CHECK_MODE_CODE = 0x09;
	//
	//    @DicItem(name = "日期免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	//    public static Integer DAY_NO_CHECK_MODE_CODE = 0x04;
	//
	//    @DicItem(name = "时间免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	//    public static Integer TIME_NO_CHECK_MODE_CODE = 0x05;
	//
	//    @DicItem(name = "车费免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	//    public static Integer FEE_FREE_NO_CHECK_MODE_CODE = 0x06;
	//
	//    @DicItem(name = "列车故障模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	//    public static Integer TRAIN_BREAK_DOWN_MODE_CODE = 0x07;
	//
	//    @DicItem(name = "启动紧急放行模式", group = AFCModeCode.MODE_SIGN_URGENCY)
	//    public static Integer START_URGENCY_MODE_CODE = 0x0800;

	@DicItem(name = "解除紧急放行模式", group = AFCModeCode.MODE_SIGN_URGENCY)
	public static Integer END_URGENCY_MODE_CODE = 0x0801;

	@DicItem(name = "进站免检模式+车费免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer ENTER_FEE_FREE_NO_CHECK_MODE_CODE = 0x81;

	@DicItem(name = "进站免检模式+出站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer ENTER_EXIT_NO_CHECK_MODE_CODE = 0x82;

	@DicItem(name = "进站免检模式+日期免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer ENTER_DAY_NO_CHECK_MODE_CODE = 0x83;

	@DicItem(name = "日期免检模式+车费免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer DAY_FEE_FREE_NO_CHECK_MODE_CODE = 0x84;

	@DicItem(name = "日期免检模式+出站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer DAY_EXIT_NO_CHECK_MODE_CODE = 0x85;

	@DicItem(name = "时间免检模式+车费免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer TIME_FEE_FREE_NO_CHECK_MODE_CODE = 0x86;

	@DicItem(name = "时间免检模式+进站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer TIME_ENTER_NO_CHECK_MODE_CODE = 0x87;

	@DicItem(name = "时间免检模式+出站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer TIME_EXIT_NO_CHECK_MODE_CODE = 0x88;

	@DicItem(name = "进站免检模式+出站免检模式+时间免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer TIME_EXIT_ENTER_NO_CHECK_MODE_CODE = 0x89;

	@DicItem(name = "进站免检模式+车费免检模式+时间免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer ENTER_FEE_FREE_TIME_NO_CHECK_MODE_CODE = 0x8a;

	@DicItem(name = "进站免检模式+出站免检模式+日期免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer ENTER_EXIT_DAY_NO_CHECK_MODE_CODE = 0x8b;

	@DicItem(name = "进站免检模式+车费免检模式+日期免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer ENTER_FEE_FREE_DAY_NO_CHECK_MODE_CODE = 0x8c;

	@DicItem(name = "进站免检模式+出站免检模式+车费免检模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer ENTER_EXIT_FEE_FREE_NO_CHECK_MODE_CODE = 0x8d;

	@DicItem(name = "停止运营模式", group = AFCModeCode.MODE_SIGN_DESCEND)
	public static Integer STOP_MODE_CODE = 0xff;

}
