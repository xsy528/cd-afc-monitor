package com.insigma.afc.monitor.constant.dic;

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

	@DicItem(name = "正常服务模式", group = AFCModeCode.MODE_SIGN_NORMAL,index = 0)
	public final static Integer NORMAL = 0x00;

//	@DicItem(name = "关闭服务模式", group = AFCModeCode.MODE_SIGN_NORMAL,index = 256)
//	public final static Integer TRAIN_SHUTDOWN = 0x100;

	@DicItem(name = "列车故障模式", group = AFCModeCode.MODE_SIGN_DESCEND,index = 1)
	public final static Integer TRAIN_BREAK_DOWN = 0x01;

	@DicItem(name = "时间免检模式", group = AFCModeCode.MODE_SIGN_DESCEND,index = 2)
	public final static Integer TIME_NO_CHECK = 0x02;

	@DicItem(name = "日期免检模式", group = AFCModeCode.MODE_SIGN_DESCEND,index = 4)
	public final static Integer DAY_NO_CHECK = 0x04;

	@DicItem(name = "车费免检模式", group = AFCModeCode.MODE_SIGN_DESCEND,index = 8)
	public final static Integer FEE_FREE_NO_CHECK = 0x08;

	@DicItem(name = "出站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND,index = 16)
	public static Integer ENTER_RODER_NO_CHECK = 0x10;

	@DicItem(name = "进站免检模式", group = AFCModeCode.MODE_SIGN_DESCEND,index = 32)
	public final static Integer ENTER_NO_CHECK = 0x20;

//	@DicItem(name = "24小时运营模式", group = AFCModeCode.MODE_SIGN_DESCEND,index = 64)
//	public final static Integer ALL_DAY = 0x40;

	@DicItem(name = "紧急放行模式", group = AFCModeCode.MODE_SIGN_URGENCY,index = 128)
	public final static Integer START_URGENCY = 0x80;

	public String getModeText(Object value){
		String name = this.getNameByValue(value);
		if (value==null||name==null){
			return "未知模式/"+(value==null?"":value.toString());
		}
		return String.format("%s/0x%02x",name,value);
	}

}
