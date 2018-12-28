/*

 * 日期：2010-8-12
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.constant;

/**
 * 维护Application类中自己的数据KEY Ticket:
 * 
 * @author fenghong
 */
public class ApplicationKey {
	/**
	 * 定义全局的票种代码、票种归类类型map
	 */
	public static final int TICKET_TYPE_KEY = 103000;

	/** 客流统计时间间隔，单位分钟 */
	public static final int TIME_INTERVAL = 105000;

	/** 客流告警 */
	public final static String MONITOR_OD_WARNING_LIST = "MONITOR_OD_WARNING_LIST";

	public final static String STATUS_DATABASE = "STATUS_DATABASE";

	public final static String STATUS_COMMUNICATION = "STATUS_COMMUNICATION";

	public final static String STATUS_MODE = "STATUS_MODE";

	public final static String STATUS_UPS = "STATUS_UPS";
	/** 日终状态栏*/
	public final static String STATUS_DAILY = "STATUS_DAILY";

	/** 车站信息列表(Map<Integer, MetroStation>) */
	public static final String STATION_LIST_KEY = "STATION_LIST_KEY";

	/** 设备信息列表MAP<Long,MetroDevice> */
	public final static String DEVICE_LIST_KEY = "DEVICE_LIST_KEY";

	/** 设备信息列表MAP<Long,MetroDevice> */
	public final static String LINE_LIST_KEY = "LINE_LIST_KEY";

	/** 设备状态等级MAP<MetroDevice,Integer> */
	public final static String STATUS_CONFIG_KEY = "STATUS_CONFIG_KEY";

	/** 状态报文MAP<Long,DeviceRecord_t> */
	public final static String STATUS_REQ_KEY = "STATUS_REQ_KEY";

	/** 数据库状态MAP<Long,Map> */
	public final static String STATUS_DB_KEY = "STATUS_DB_KEY";

	/** 设备整体状态 */
	public final static String DEVICE_STATUS_KEY = "DEVICE_STATUS_KEY";

	/**UPS状态列表MAP<Integer,String> */
	public final static String UPS_DB_KEY = "UPS_DB_KEY";

	/**paramInfoMap<String,Object>**/
	public final static String PARAM_INFO = "PARAM_INFO";

	/** 节点信息 */
	public final static String TOPLOGY_NODES = "toplogyNodes";

	/** 设备节点配置信息 */
	public final static String DEVICE_IP_CONFIG = "DEVICE_IP_CONFIG";

	/** 车站节点IP配置信息 */
	public final static String STATION_IP_CONFIG = "STATION_IP_CONFIG";

	// FTP信息的KEY,自己使用
	public static final int FTP_KEY = 10000;

	// Start*******************************清分规则相关*****************************Start

	/** 线路代码和线路信息的对应关系(Map<Short, MetroLine>) */
	public final static String LINE_METRO_MAP = "MetroLineMap";

	// End*******************************清分规则相关*****************************End

}
