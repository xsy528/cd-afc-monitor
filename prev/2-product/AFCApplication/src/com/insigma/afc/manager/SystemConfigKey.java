/*
 * 日期：2010-12-13
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.manager;

/**
 * 系统配置选项，SC,LC,ACC系统中的系统参数配置、EOD参数配置都直接从表TSY_CONFIG获取<br>
 * KEY统一用大写字符 Ticket:
 * 
 * @author fenghong
 */
public class SystemConfigKey {

	// 线路编号
	public final static String LINE_ID = "LINE_ID";

	// 车站代码
	public final static String STATION_ID = "STATION_ID";

	// 节点序号
	public final static String NODE_ID = "NODE_ID";

	// 清分中心级EOD大版本号(为以后版本切换使用，预留)
	public final static String ACC_EOD_VERSION_NUMBER = "ACC_EOD_VERSION_NUMBER";

	// 线路级EOD大版本号(为以后版本切换使用，预留)
	public final static String LC_EOD_VERSION_NUMBER = "LC_EOD_VERSION_NUMBER";

	// 日始时间
	public final static String DAILY_START_TIME = "DAILY_START_TIME";

	// 日终时间
	public final static String DAILY_END_TIME = "DAILY_END_TIME";

	// 运营开始时间(距离凌晨秒数)
	public final static String BUSINESS_DAY_START_TIME = "BUSINESS_DAY_START_TIME";

	// 运营结束时间(距离凌晨秒数)
	public final static String BUSINESS_DAY_END_TIME = "BUSINESS_DAY_END_TIME";

	// 客流统计频率(分钟)
	public final static String OD_INTERVAL = "OD_INTERVAL";

	// 审计的天数区域
	public final static String MAX_AUDIT_DAYS = "MAX_AUDIT_DAYS";

	// 时间同步最小校准间隔(秒数)
	public final static String MIN_TIME_DEVIATION = "MIN_TIME_DEVIATION";

	// 时间同步最大校准间隔(秒数)
	public final static String MAX_TIME_DEVIATION = "MAX_TIME_DEVIATION";

	// 日终调度轮循时间(秒数)
	public final static String DAILY_TASK_INTERVAL = "DAILY_TASK_INTERVAL";

	// 日终状态:true false
	public final static String DAILY_TASK_IS_STOP = "DAILY_TASK_IS_STOP";

	// 是否需要检查前置任务成功:true false
	public final static String IS_CHECK_PRE_DAILY_TASK = "IS_CHECK_PRE_DAILY_TASK";

	// 是否需要自动调用后续任务:true false
	public final static String IS_HANDLE_FOLLOW_UP_DAILY_TASK = "IS_HANDLE_FOLLOW_UP_DAILY_TASK";

	// 是否需要自动调用后续任务:true false
	public final static String DAILY_TASK_TIME_DEFAULT_KEY = "DAILY_TASK_TIME_DEFAULT_KEY";

	// 任务组YPT_CC的最新一次执行日期
	public final static String YPT_CC_LAST_HANDLE_DAY = "YPT_CC_LAST_HANDLE_DAY";

	// 正式目录下文件过期时间
	public final static String DAYS_OF_LOCAL_FOLDER_EXPRIED = "DAYS_OF_LOCAL_FOLDER_EXPRIED";

	// 备份目录下文件过期时间
	public final static String DAYS_OF_BACKUP_FOLDER_EXPRIED = "DAYS_OF_BACKUP_FOLDER_EXPRIED";

	// 交易文件文件过期时间
	public final static String DAYS_OF_UD_DATA_EXPRIED = "DAYS_OF_UD_DATA_EXPRIED";

	// 是否需要清理临时目录
	public final static String IS_CLEAN_TEMP_FOLDER = "IS_CLEAN_TEMP_FOLDER";

	// 任务组YKT_CC的最新一次执行日期
	public final static String YKT_CC_LAST_HANDLE_DAY = "YKT_CC_LAST_HANDLE_DAY";

	// 乘车消费清分类别码
	public final static String CLEAR_TYPE_ID_FARE = "CLEAR_TYPE_ID_FARE";

	// ACC清分服务费清分类别码
	public final static String CLEAR_TYPE_ID_ACC_FEE = "CLEAR_TYPE_ID_ACC_FEE";

	// 一卡通中心清分服务费清分类别码
	public final static String CLEAR_TYPE_ID_ACC_YKT_FEE = "CLEAR_TYPE_ID_ACC_YKT_FEE";

	// 票务服务费清分类别码
	public final static String CLEAR_TYPE_ID_FEE = "CLEAR_TYPE_ID_FEE";

	// 清算界面显示日志列表的天数
	public final static String DAILY_LOG_DAYS = "DAILY_LOG_DAYS";

	// 客流初始化天数
	public final static String OD_INIT_DAYS = "OD_INIT_DAYS";

	// 一票通
	public final static String YPT_CC = "YPT_CC";

	// 一卡通
	public final static String YKT_CC = "YKT_CC";

	// 用户配置的晚传数据对单边交易的影响规定的有效期
	public static final String ACC_YPT_SINGLE_EFFECTIVE = "ACC_YPT_SINGLE_EFFECTIVE";

	// 自动广播
	public static final String IS_AUTO_BROADCAST = "IS_AUTO_BROADCAST";

	// 警告临界值
	public static final String WARNING_THRESHHOLD = "WARNING_THRESHHOLD";

	// 报警临界值
	public static final String ALARM_THRESHHOLD = "ALARM_THRESHHOLD";

	// 界面刷新间隔
	public static final String VIEW_REFRESH_INTERVAL = "VIEW_REFRESH_INTERVAL";

	// 用户过期时间天数设置
	public static final String USER_EFFECTTIME_WARNING = "USER_EFFECTTIME_WARNING";

	//断面客流刷新周期
	public static final String SECTION_OD_REFRESH_INTERVAL = "SECTION_OD_REFRESH_INTERVAL";
	//断面客流
	public static final String SECTION_OD_PERIOD_COUNT = "SECTION_OD_PERIOD_COUNT";
	//断面客流
	public static final String SECTION_OD_DELAY_TIME = "SECTION_OD_DELAY_TIME";

	//4001转发
	public final static String MESS_4001_RELAY = "MESS_4001_RELAY";

	//一卡通上传下载文件备份目录清理天数
	public final static String YKT_DAYS_OF_BACKUP_EXPRIED = "YKT_DAYS_OF_BACKUP_EXPRIED";

	//一卡通正式目录清理天数
	public final static String YKT_DAYS_OF_CONTENT_EXPRIED = "YKT_DAYS_OF_CONTENT_EXPRIED";

	// 银联闪付
	public final static String BANK_CC = "BANK_CC";

	// 任务组BANK_CC的最新一次执行日期
	public final static String BANK_CC_LAST_HANDLE_DAY = "BANK_CC_LAST_HANDLE_DAY";

	// 二维码闪付
	public final static String QR_CC = "QR_CC";

	// 任务组QR_CC的最新一次执行日期
	public final static String QR_CC_LAST_HANDLE_DAY = "QR_CC_LAST_HANDLE_DAY";
}
