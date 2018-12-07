/* 
 * 日期：20117-11-23
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.constant;

import com.insigma.afc.constant.ApplicationKey;

/**
 * Ticket: 温州项目常量配置项
 * 
 * @author mengyifan
 */
public class WZApplicationKey extends ApplicationKey {

    // ES 设备状态报告间隔
    public static final String PERIOD_OF_STATUS_REPORT = "PERIOD_OF_STATUS_REPORT";

    // ES 取任务间隔
    public static final String PERIOD_OF_GET_TASK = "PERIOD_OF_GET_TASK";

    // 模式广播方式（注意：此变量值要与com.insigma.afc.monitor.ConfigKey中变量值一致）
    public static final String IS_AUTO_BROADCAST = "IS_AUTO_BROADCAST";

    // 警告阈值（注意：此变量值要与com.insigma.afc.monitor.ConfigKey中变量值一致）
    public static final String WARNING_THRESHHOLD = "WARNING_THRESHHOLD";

    // 报警阈值（注意：此变量值要与com.insigma.afc.monitor.ConfigKey中变量值一致）
    public static final String ALARM_THRESHHOLD = "ALARM_THRESHHOLD";

    // 监控地图刷新时间（注意：此变量值要与com.insigma.afc.monitor.ConfigKey中变量值一致）
    public static final String VIEW_REFRESH_INTERVAL = "VIEW_REFRESH_INTERVAL";

    // 断面客流中低分界阈值（注意：此变量值要与com.insigma.afc.odmonitor.config.ConfigKey中变量值一致）
    public static final String SectionPassengerflowLow = "SectionPassengerflowLow";

    // 断面客流中高分界阈值（注意：此变量值要与com.insigma.afc.odmonitor.config.ConfigKey中变量值一致）
    public static final String SectionPassengerflowHigh = "SectionPassengerflowHigh";

    // 断面客流刷新周期
    public static final String SectionPassengerFlowRefreshPeriod = "SectionPassengerFlowRefPeriod";

    // 信息管理客流监控刷新周期
    public static final String SCREEN_PASSENGER_FLOW_MONITOR = "SCREEN_PASSENGER_FLOW_MONITOR";

    /** ES设备信息列表MAP<Long,String> */
    public final static String ESDEVICE_LIST_KEY = "ESDEVICE_LIST_KEY";

    /** UPS信息列表 **/
    public final static String UPS_LIST_KEY = "UPS_LIST_KEY";

}
