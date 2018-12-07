/* 
 * 日期：2017年7月11日
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.define;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Ticket: 温州事件常量定义
 * @author  wangzezhi
 *
 */
public enum EventEntry {

	C0300_0("0300", "EOD", "参数生效状态", EventEntry.NORMAL_LEVEL, "参数生效状态-成功", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0300_2("0300", "EOD", "参数生效状态", EventEntry.ALARM_LEVEL, "参数生效状态-失败", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0301_0("0301", "EOD", "EOD参数下载", EventEntry.NORMAL_LEVEL, "EOD参数下载-成功", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0301_2("0301", "EOD", "EOD参数下载", EventEntry.ALARM_LEVEL, "EOD参数下载-失败", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0302_0("0302", "EOD", "EOD参数激活", EventEntry.NORMAL_LEVEL, "EOD参数激活-成功", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0302_2("0302", "EOD", "EOD参数激活", EventEntry.ALARM_LEVEL, "EOD参数激活-失败", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0303_0("0303", "EOD", "运营控制文件下载", EventEntry.NORMAL_LEVEL, "运营控制文件下载-成功", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0303_2("0303", "EOD", "运营控制文件下载", EventEntry.ALARM_LEVEL, "运营控制文件下载-失败", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0304_0("0304", "EOD", "运营控制文件激活", EventEntry.NORMAL_LEVEL, "运营控制文件激活-成功", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0304_2("0304", "EOD", "运营控制文件激活", EventEntry.ALARM_LEVEL, "运营控制文件激活-失败", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0500_0("0500", "CPU", "主控单元总体状态", EventEntry.NORMAL_LEVEL, "主控单元总体状态-正常", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0500_1("0500", "CPU", "主控单元总体状态", EventEntry.WARNING_LEVEL, "主控单元总体状态-警告", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0500_2("0500", "CPU", "主控单元总体状态", EventEntry.ALARM_LEVEL, "主控单元总体状态-报警", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0502_0("0502", "DATA", "数据存储状态", EventEntry.NORMAL_LEVEL, "数据存储状态-正常", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0502_1("0502", "DATA", "数据存储状态", EventEntry.WARNING_LEVEL, "数据存储状态-将满", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0502_2("0502", "DATA", "数据存储状态", EventEntry.ALARM_LEVEL, "数据存储状态-已满", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0503_0("0503", "CLOCK", "时钟同步信息", EventEntry.NORMAL_LEVEL, "时钟同步信息-恢复正常", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0503_1("0503", "CLOCK", "时钟同步信息", EventEntry.WARNING_LEVEL, "时钟同步信息-警告", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0503_2("0503", "CLOCK", "时钟同步信息", EventEntry.ALARM_LEVEL, "时钟同步信息-异常", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0504_0("0504", "POWER", "电源状态", EventEntry.NORMAL_LEVEL, "电源状态-开", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0504_2("0504", "POWER", "电源状态", EventEntry.ALARM_LEVEL, "电源状态-关", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0505_0("0505", "POWER", "UPS电池工作状态", EventEntry.NORMAL_LEVEL, "UPS电池工作状态-正常", EventEntry.TYPE1, "7,8,9,10,11"),

	C0505_2("0505", "POWER", "UPS电池工作状态", EventEntry.ALARM_LEVEL, "UPS电池工作状态-故障", EventEntry.TYPE1, "7,8,9,10,11"),

	C0506_0("0506", "TICKET", "仅使用单程票", EventEntry.NORMAL_LEVEL, "仅使用单程票-正常", EventEntry.TYPE1, "7,8,9"),

	C0506_2("0506", "TICKET", "仅使用单程票", EventEntry.ALARM_LEVEL, "仅使用单程票-报警", EventEntry.TYPE1, "7,8,9"),

	C0507_0("0507", "TICKET", "仅使用储值票", EventEntry.NORMAL_LEVEL, "仅使用储值票-正常", EventEntry.TYPE1, "7,8,9"),

	C0507_2("0507", "TICKET", "仅使用储值票", EventEntry.ALARM_LEVEL, "仅使用储值票-报警", EventEntry.TYPE1, "7,8,9"),

	C0508_0("0508", "SWITCH", "降级运行开关", EventEntry.NORMAL_LEVEL, "允许TVM进入降级模式", EventEntry.TYPE1, "11"),

	C0508_2("0508", "SWITCH", "降级运行开关", EventEntry.ALARM_LEVEL, "禁止TVM进入降级模式", EventEntry.TYPE1, "11"),

	C0509_0("0509", "STATUS", "只售票状态", EventEntry.NORMAL_LEVEL, "只售票状态-正常", EventEntry.TYPE1, "11"),

	C0509_2("0509", "STATUS", "只售票状态", EventEntry.ALARM_LEVEL, "只售票状态-报警", EventEntry.TYPE1, "11"),

	C0510_0("0510", "STATUS", "只充值状态", EventEntry.NORMAL_LEVEL, "只充值状态-正常", EventEntry.TYPE1, "11"),

	C0510_2("0510", "STATUS", "只充值状态", EventEntry.ALARM_LEVEL, "只充值状态-报警", EventEntry.TYPE1, "11"),

	C0511_0("0511", "STATUS", "拒收硬币状态", EventEntry.NORMAL_LEVEL, "拒收硬币状态-正常", EventEntry.TYPE1, "11"),

	C0511_2("0511", "STATUS", "拒收硬币状态", EventEntry.ALARM_LEVEL, "拒收硬币状态-报警", EventEntry.TYPE1, "11"),

	C0512_0("0512", "STATUS", "拒收纸币状态", EventEntry.NORMAL_LEVEL, "拒收纸币状态-正常", EventEntry.TYPE1, "11"),

	C0512_2("0512", "STATUS", "拒收纸币状态", EventEntry.ALARM_LEVEL, "拒收纸币状态-报警", EventEntry.TYPE1, "11"),

	C0513_0("0513", "STATUS", "无找零状态", EventEntry.NORMAL_LEVEL, "无找零状态-正常", EventEntry.TYPE1, "11"),

	C0513_2("0513", "STATUS", "无找零状态", EventEntry.ALARM_LEVEL, "无找零状态-报警", EventEntry.TYPE1, "11"),

	C0600_0("0600", "READER1", "读写器1 总体状态", EventEntry.NORMAL_LEVEL, "读写器1 总体状态-正常", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	C0600_1("0600", "READER1", "读写器1 总体状态", EventEntry.WARNING_LEVEL, "读写器1 总体状态-警告", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	C0600_2("0600", "READER1", "读写器1 总体状态", EventEntry.ALARM_LEVEL, "读写器1 总体状态-报警", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0601_0("0601", "READER1", "读写器1 通讯状态", EventEntry.NORMAL_LEVEL, "读写器1 通讯状态-正常", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	C0601_2("0601", "READER1", "读写器1 通讯状态", EventEntry.ALARM_LEVEL, "读写器1 通讯状态-通讯中断", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	C0602_0("0602", "READER1", "读写器1 SAM卡状态", EventEntry.NORMAL_LEVEL, "读写器1 SAM卡状态-正常", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	//     C0602_1("0602", "READER1", "读写器1 SAM卡状态", EventEntry.WARNING_LEVEL, "读写器1 SAM卡状态-SAM卡出错",
	//             EventEntry.TYPE1, "7,8,9,10,11,12"),

	//     C0602_2("0602", "READER1", "读写器1 SAM卡状态", EventEntry.WARNING_LEVEL, "读写器1 SAM卡状态-SAM卡出错",
	//             EventEntry.TYPE1, "7,8,9,10,11,12"),

	C0602_99("0602", "READER1", "读写器1 SAM卡状态", EventEntry.WARNING_LEVEL, "读写器1 SAM卡状态-SAM卡出错", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	C0603_0("0603", "READER1", "读写器1 TP软件更新状态", EventEntry.NORMAL_LEVEL, "读写器1 TP软件更新状态-成功", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	C0603_2("0603", "READER1", "读写器1 TP软件更新状态", EventEntry.ALARM_LEVEL, "读写器1 TP软件更新状态-失败", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	C0604_0("0604", "READER1", "读写器1 EOD参数更新状态", EventEntry.NORMAL_LEVEL, "读写器1 EOD参数更新状态-成功", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	C0604_2("0604", "READER1", "读写器1 EOD参数更新状态", EventEntry.ALARM_LEVEL, "读写器1 EOD参数更新状态-失败", EventEntry.TYPE1,
			"7,8,9,10,11,12"),

	C0605_0("0605", "READER1", "读写器1 运营控制文件更新状态", EventEntry.NORMAL_LEVEL, "读写器1 运营控制文件更新状态-成功", EventEntry.TYPE1,
			"7,8,9,10,11"),

	C0605_2("0605", "READER1", "读写器1 运营控制文件更新状态", EventEntry.ALARM_LEVEL, "读写器1 运营控制文件更新状态-失败", EventEntry.TYPE1,
			"7,8,9,10,11"),

	C0606_0("0606", "READER1", "读写器连续读写失败", EventEntry.NORMAL_LEVEL, "读写器1连续读写失败-成功", EventEntry.TYPE1, ""),

	C0606_2("0606", "READER1", "读写器连续读写失败", EventEntry.ALARM_LEVEL, "读写器1连续读写失败-失败", EventEntry.TYPE1, ""),

	C0700_0("0700", "READER2", "读写器2 总体状态", EventEntry.NORMAL_LEVEL, "读写器2 总体状态-正常", EventEntry.TYPE1, "7,8,9,10"),

	C0700_1("0700", "READER2", "读写器2 总体状态", EventEntry.WARNING_LEVEL, "读写器2 总体状态-警告", EventEntry.TYPE1, "7,8,9,10"),

	C0700_2("0700", "READER2", "读写器2 总体状态", EventEntry.ALARM_LEVEL, "读写器2 总体状态-报警", EventEntry.TYPE1, "7,8,9,10"),

	C0701_0("0701", "READER2", "读写器2 通讯状态", EventEntry.NORMAL_LEVEL, "读写器2 通讯状态-正常", EventEntry.TYPE1, "7,8,9,10"),

	C0701_2("0701", "READER2", "读写器2 通讯状态", EventEntry.ALARM_LEVEL, "读写器2 通讯状态-通讯中断", EventEntry.TYPE1, "7,8,9,10"),

	C0702_0("0702", "READER2", "读写器2 SAM卡状态", EventEntry.NORMAL_LEVEL, "读写器2 SAM卡状态-正常", EventEntry.TYPE1, "7,8,9,10"),

	//     C0702_1("0702", "READER2", "读写器2 SAM卡状态", EventEntry.WARNING_LEVEL, "读写器2 SAM卡状态-SAM卡出错",
	//             EventEntry.TYPE1, "7,8,9,10"),

	//     C0702_2("0702", "READER2", "读写器2 SAM卡状态", EventEntry.WARNING_LEVEL, "读写器2 SAM卡状态-SAM卡出错",
	//             EventEntry.TYPE1, "7,8,9,10"),

	C0702_99("0702", "READER2", "读写器2 SAM卡状态", EventEntry.WARNING_LEVEL, "读写器2 SAM卡状态-SAM卡出错", EventEntry.TYPE1,
			"7,8,9,10"),

	C0703_0("0703", "READER2", "读写器2 TP软件更新状态", EventEntry.NORMAL_LEVEL, "读写器2 TP软件更新状态-成功", EventEntry.TYPE1,
			"7,8,9,10"),

	C0703_2("0703", "READER2", "读写器2 TP软件更新状态", EventEntry.ALARM_LEVEL, "读写器2 TP软件更新状态-失败", EventEntry.TYPE1,
			"7,8,9,10"),

	C0704_0("0704", "READER2", "读写器2 EOD参数更新状态", EventEntry.NORMAL_LEVEL, "读写器2 EOD参数更新状态-成功", EventEntry.TYPE1,
			"7,8,9,10"),

	C0704_2("0704", "READER2", "读写器2 EOD参数更新状态", EventEntry.ALARM_LEVEL, "读写器2 EOD参数更新状态-失败", EventEntry.TYPE1,
			"7,8,9,10"),

	C0705_0("0705", "READER2", "读写器2 运营控制文件更新状态", EventEntry.NORMAL_LEVEL, "读写器2 运营控制文件更新状态-成功", EventEntry.TYPE1,
			"7,8,9,10"),

	C0705_2("0705", "READER2", "读写器2 运营控制文件更新状态", EventEntry.ALARM_LEVEL, "读写器2 运营控制文件更新状态-失败", EventEntry.TYPE1,
			"7,8,9,10"),

	C0706_0("0706", "READER2", "读写器2连续读写失败", EventEntry.NORMAL_LEVEL, "读写器2连续读写失败-成功", EventEntry.TYPE1, ""),

	C0706_2("0706", "READER2", "读写器2连续读写失败", EventEntry.ALARM_LEVEL, "读写器2连续读写失败-失败", EventEntry.TYPE1, ""),

	C0900_0("0900", "PRINTER1", "打印机1总体状态", EventEntry.NORMAL_LEVEL, "打印机1总体状态-正常", EventEntry.TYPE1, "10,11,12"),

	C0900_1("0900", "PRINTER1", "打印机1总体状态", EventEntry.WARNING_LEVEL, "打印机1总体状态-警告", EventEntry.TYPE1, "10,11,12"),

	C0900_2("0900", "PRINTER1", "打印机1总体状态", EventEntry.ALARM_LEVEL, "打印机1总体状态-报警", EventEntry.TYPE1, "10,11,12"),

	C0901_0("0901", "PRINTER1", "打印机1通讯故障", EventEntry.NORMAL_LEVEL, "打印机1通讯故障-正常", EventEntry.TYPE1, "10,11,12"),

	C0901_2("0901", "PRINTER1", "打印机1通讯故障", EventEntry.ALARM_LEVEL, "打印机1通讯故障-通讯故障", EventEntry.TYPE1, "10,11,12"),

	C0902_0("0902", "PRINTER1", "打印机1缺纸", EventEntry.NORMAL_LEVEL, "打印机1缺纸-正常", EventEntry.TYPE1, "10,11,12"),

	C0902_2("0902", "PRINTER1", "打印机1缺纸", EventEntry.ALARM_LEVEL, "打印机1缺纸-缺纸", EventEntry.TYPE1, "10,11,12"),

	C0903_0("0903", "PRINTER1", "打印机1卡纸", EventEntry.NORMAL_LEVEL, "打印机1卡纸-正常", EventEntry.TYPE1, "10,11,12"),

	C0903_2("0903", "PRINTER1", "打印机1卡纸", EventEntry.ALARM_LEVEL, "打印机1卡纸-卡纸", EventEntry.TYPE1, "10,11,12"),

	C1000_0("1000", "PRINTER2", "打印机2总体状态", EventEntry.NORMAL_LEVEL, "打印机2总体状态-正常", EventEntry.TYPE1, "11,12"),

	C1000_1("1000", "PRINTER2", "打印机2总体状态", EventEntry.WARNING_LEVEL, "打印机2总体状态-警告", EventEntry.TYPE1, "11,12"),

	C1000_2("1000", "PRINTER2", "打印机2总体状态", EventEntry.ALARM_LEVEL, "打印机2总体状态-报警", EventEntry.TYPE1, "11,12"),

	C1001_0("1001", "PRINTER2", "打印机2通讯故障", EventEntry.NORMAL_LEVEL, "打印机2通讯故障-正常", EventEntry.TYPE1, "11,12"),

	C1001_2("1001", "PRINTER2", "打印机2通讯故障", EventEntry.ALARM_LEVEL, "打印机2通讯故障-通讯故障", EventEntry.TYPE1, "11,12"),

	C1002_0("1002", "PRINTER2", "打印机2缺纸", EventEntry.NORMAL_LEVEL, "打印机2缺纸-正常", EventEntry.TYPE1, "11,12"),

	C1002_2("1002", "PRINTER2", "打印机2缺纸", EventEntry.ALARM_LEVEL, "打印机2缺纸-缺纸", EventEntry.TYPE1, "11,12"),

	C1003_0("1003", "PRINTER2", "打印机2卡纸", EventEntry.NORMAL_LEVEL, "打印机2卡纸-正常", EventEntry.TYPE1, "11,12"),

	C1003_2("1003", "PRINTER2", "打印机2卡纸", EventEntry.ALARM_LEVEL, "打印机2卡纸-卡纸", EventEntry.TYPE1, "11,12"),

	C1100_0("1100", "DOOR", "维修门状态", EventEntry.NORMAL_LEVEL, "维修门状态-关闭", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C1100_1("1100", "DOOR", "维修门状态", EventEntry.ALARM_LEVEL, "维修门状态-用钥匙打开", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C1100_2("1100", "DOOR", "维修门状态", EventEntry.ALARM_LEVEL, "维修门状态-入侵", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C1200_0("1200", "PAPER", "纸币模块总体状态", EventEntry.NORMAL_LEVEL, "纸币模块总体状态-正常", EventEntry.TYPE1, "11,12"),

	C1200_1("1200", "PAPER", "纸币模块总体状态", EventEntry.WARNING_LEVEL, "纸币模块总体状态-警告", EventEntry.TYPE1, "11,12"),

	C1200_2("1200", "PAPER", "纸币模块总体状态", EventEntry.ALARM_LEVEL, "纸币模块总体状态-报警", EventEntry.TYPE1, "11,12"),

	C1201_0("1201", "PAPER", "纸币识别器通信", EventEntry.NORMAL_LEVEL, "纸币识别器通信-正常", EventEntry.TYPE1, "11,12"),

	C1201_2("1201", "PAPER", "纸币识别器通信", EventEntry.ALARM_LEVEL, "纸币识别器通信-通讯中断", EventEntry.TYPE1, "11,12"),

	C1202_0("1202", "PAPER", "纸币卡币", EventEntry.NORMAL_LEVEL, "纸币卡币-正常", EventEntry.TYPE1, "11,12"),

	C1202_2("1202", "PAPER", "纸币卡币", EventEntry.ALARM_LEVEL, "纸币卡币-卡币", EventEntry.TYPE1, "11,12"),

	C1203_0("1203", "PAPER", "纸币接收容量", EventEntry.NORMAL_LEVEL, "纸币接收容量-正常", EventEntry.TYPE1, "11,12"),

	C1203_1("1203", "PAPER", "纸币接收容量", EventEntry.WARNING_LEVEL, "纸币接收容量-将满", EventEntry.TYPE1, "11,12"),

	C1203_2("1203", "PAPER", "纸币接收容量", EventEntry.ALARM_LEVEL, "纸币接收容量-已满", EventEntry.TYPE1, "11,12"),

	C1204_0("1204", "PAPER", "纸币箱拔出", EventEntry.NORMAL_LEVEL, "纸币箱拔出-正常", EventEntry.TYPE1, "11,12"),

	C1204_2("1204", "PAPER", "纸币箱拔出", EventEntry.ALARM_LEVEL, "纸币箱拔出-未授权", EventEntry.TYPE1, "11,12"),

	C1205_0("1205", "PAPER", "纸币找零通信", EventEntry.NORMAL_LEVEL, "纸币找零通信-正常", EventEntry.TYPE1, "11"),

	C1205_2("1205", "PAPER", "纸币找零通信", EventEntry.ALARM_LEVEL, "纸币找零通信-通信中断", EventEntry.TYPE1, "11"),

	C1206_0("1206", "PAPER", "纸币找零箱容量", EventEntry.NORMAL_LEVEL, "纸币找零箱容量-正常", EventEntry.TYPE1, "11"),

	C1206_1("1206", "PAPER", "纸币找零箱容量", EventEntry.WARNING_LEVEL, "纸币找零箱容量-将空", EventEntry.TYPE1, "11"),

	C1206_2("1206", "PAPER", "纸币找零箱容量", EventEntry.ALARM_LEVEL, "纸币找零箱容量-已空", EventEntry.TYPE1, "11"),

	C1207_0("1207", "PAPER", "纸币找零箱拔出", EventEntry.NORMAL_LEVEL, "纸币找零箱拔出-正常", EventEntry.TYPE1, "11"),

	C1207_2("1207", "PAPER", "纸币找零箱拔出", EventEntry.ALARM_LEVEL, "纸币找零箱拔出-未授权", EventEntry.TYPE1, "11"),

	C1208_0("1208", "PAPER", "纸币模块故障", EventEntry.NORMAL_LEVEL, "纸币模块故障-正常", EventEntry.TYPE1, "11,12"),

	C1208_2("1208", "PAPER", "纸币模块故障", EventEntry.ALARM_LEVEL, "纸币模块故障-错误", EventEntry.TYPE1, "11,12"),

	C1300_0("1300", "COIN", "硬币模块总体状态", EventEntry.NORMAL_LEVEL, "硬币模块总体状态-正常", EventEntry.TYPE1, "11"),

	C1300_1("1300", "COIN", "硬币模块总体状态", EventEntry.WARNING_LEVEL, "硬币模块总体状态-警告", EventEntry.TYPE1, "11"),

	C1300_2("1300", "COIN", "硬币模块总体状态", EventEntry.ALARM_LEVEL, "硬币模块总体状态-报警", EventEntry.TYPE1, "11"),

	C1301_0("1301", "COIN", "硬币识别器通信", EventEntry.NORMAL_LEVEL, "硬币识别器通信-正常", EventEntry.TYPE1, "11"),

	C1301_2("1301", "COIN", "硬币识别器通信", EventEntry.ALARM_LEVEL, "硬币识别器通信-错误", EventEntry.TYPE1, "11"),

	C1302_0("1302", "COIN", "硬币卡币", EventEntry.NORMAL_LEVEL, "硬币卡币-正常", EventEntry.TYPE1, "11"),

	C1302_2("1302", "COIN", "硬币卡币", EventEntry.ALARM_LEVEL, "硬币卡币-卡币", EventEntry.TYPE1, "11"),

	C1303_0("1303", "COIN", "硬币接收容量", EventEntry.NORMAL_LEVEL, "硬币接收容量-正常", EventEntry.TYPE1, "11"),

	C1303_1("1303", "COIN", "硬币接收容量", EventEntry.WARNING_LEVEL, "硬币接收容量-将满", EventEntry.TYPE1, "11"),

	C1303_2("1303", "COIN", "硬币接收容量", EventEntry.ALARM_LEVEL, "硬币接收容量-已满", EventEntry.TYPE1, "11"),

	C1304_0("1304", "COIN", "硬币备用找零1容量", EventEntry.NORMAL_LEVEL, "硬币备用找零1容量-正常", EventEntry.TYPE1, "11"),

	C1304_1("1304", "COIN", "硬币备用找零1容量", EventEntry.WARNING_LEVEL, "硬币备用找零1容量-将空", EventEntry.TYPE1, "11"),

	C1304_2("1304", "COIN", "硬币备用找零1容量", EventEntry.ALARM_LEVEL, "硬币备用找零1容量-已空", EventEntry.TYPE1, "11"),

	C1305_0("1305", "COIN", "硬币备用找零2容量", EventEntry.NORMAL_LEVEL, "硬币备用找零2容量-正常", EventEntry.TYPE1, "11"),

	C1305_1("1305", "COIN", "硬币备用找零2容量", EventEntry.WARNING_LEVEL, "硬币备用找零2容量-将空", EventEntry.TYPE1, "11"),

	C1305_2("1305", "COIN", "硬币备用找零2容量", EventEntry.ALARM_LEVEL, "硬币备用找零2容量-已空", EventEntry.TYPE1, "11"),

	C1306_0("1306", "COIN", "硬币循环找零1容量", EventEntry.NORMAL_LEVEL, "硬币循环找零1容量-正常", EventEntry.TYPE1, "11"),

	C1306_1("1306", "COIN", "硬币循环找零1容量", EventEntry.WARNING_LEVEL, "硬币循环找零1容量-将满", EventEntry.TYPE1, "11"),

	C1306_2("1306", "COIN", "硬币循环找零1容量", EventEntry.ALARM_LEVEL, "硬币循环找零1容量-已满", EventEntry.TYPE1, "11"),

	C1307_0("1307", "COIN", "硬币循环找零2容量", EventEntry.NORMAL_LEVEL, "硬币循环找零2容量-正常", EventEntry.TYPE1, "11"),

	C1307_1("1307", "COIN", "硬币循环找零2容量", EventEntry.WARNING_LEVEL, "硬币循环找零2容量-将满", EventEntry.TYPE1, "11"),

	C1307_2("1307", "COIN", "硬币循环找零2容量", EventEntry.ALARM_LEVEL, "硬币循环找零2容量-已满", EventEntry.TYPE1, "11"),

	C1308_0("1308", "COIN", "硬币接收箱拔出", EventEntry.NORMAL_LEVEL, "硬币接收箱拔出-正常", EventEntry.TYPE1, "11"),

	C1308_2("1308", "COIN", "硬币接收箱拔出", EventEntry.ALARM_LEVEL, "硬币接收箱拔出-未授权", EventEntry.TYPE1, "11"),

	C1309_0("1309", "COIN", "硬币鼓拔出", EventEntry.NORMAL_LEVEL, "硬币鼓拔出-正常", EventEntry.TYPE1, "11"),

	C1309_2("1309", "COIN", "硬币鼓拔出", EventEntry.ALARM_LEVEL, "硬币鼓拔出-未授权", EventEntry.TYPE1, "11"),

	C1310_0("1310", "COIN", "硬币找零箱拔出", EventEntry.NORMAL_LEVEL, "硬币找零箱拔出-正常", EventEntry.TYPE1, "11"),

	C1310_2("1310", "COIN", "硬币找零箱拔出", EventEntry.ALARM_LEVEL, "硬币找零箱拔出-未授权", EventEntry.TYPE1, "11"),

	C1311_0("1311", "COIN", "硬币模块故障", EventEntry.NORMAL_LEVEL, "硬币模块故障-正常", EventEntry.TYPE1, "11"),

	C1311_2("1311", "COIN", "硬币模块故障", EventEntry.ALARM_LEVEL, "硬币模块故障-错误", EventEntry.TYPE1, "11"),

	C1400_0("1400", "SELL", "票卡发售模块总体状态", EventEntry.NORMAL_LEVEL, "票卡发售模块总体状态-正常", EventEntry.TYPE1, "10,11"),

	C1400_1("1400", "SELL", "票卡发售模块总体状态", EventEntry.WARNING_LEVEL, "票卡发售模块总体状态-警告", EventEntry.TYPE1, "10,11"),

	C1400_2("1400", "SELL", "票卡发售模块总体状态", EventEntry.ALARM_LEVEL, "票卡发售模块总体状态-报警", EventEntry.TYPE1, "10,11"),

	C1401_0("1401", "SELL", "发售模块通讯状态", EventEntry.NORMAL_LEVEL, "发售模块通讯状态-正常", EventEntry.TYPE1, "10,11"),

	C1401_2("1401", "SELL", "发售模块通讯状态", EventEntry.ALARM_LEVEL, "发售模块通讯状态-错误", EventEntry.TYPE1, "10,11"),

	C1402_0("1402", "SELL", "发售模块卡票", EventEntry.NORMAL_LEVEL, "发售模块卡票-正常", EventEntry.TYPE1, "10,11"),

	C1402_1("1402", "SELL", "发售模块卡票", EventEntry.WARNING_LEVEL, "发售模块卡票-卡票", EventEntry.TYPE1, "10,11"),

	C1403_0("1403", "SELL", "售票票箱1容量", EventEntry.NORMAL_LEVEL, "售票票箱1容量-正常", EventEntry.TYPE1, "10,11"),

	C1403_1("1403", "SELL", "售票票箱1容量", EventEntry.WARNING_LEVEL, "售票票箱1容量-将空", EventEntry.TYPE1, "10,11"),

	C1403_2("1403", "SELL", "售票票箱1容量", EventEntry.ALARM_LEVEL, "售票票箱1容量-已空", EventEntry.TYPE1, "10,11"),

	C1404_0("1404", "SELL", "售票票箱2容量", EventEntry.NORMAL_LEVEL, "售票票箱2容量-正常", EventEntry.TYPE1, "10,11"),

	C1404_1("1404", "SELL", "售票票箱2容量", EventEntry.WARNING_LEVEL, "售票票箱2容量-将空", EventEntry.TYPE1, "10,11"),

	C1404_2("1404", "SELL", "售票票箱2容量", EventEntry.ALARM_LEVEL, "售票票箱2容量-已空", EventEntry.TYPE1, "10,11"),

	C1405_0("1405", "NOTE", "废票箱容量", EventEntry.NORMAL_LEVEL, "废票箱容量-正常", EventEntry.TYPE1, "10,11"),

	C1405_1("1405", "NOTE", "废票箱容量", EventEntry.WARNING_LEVEL, "废票箱容量-将满", EventEntry.TYPE1, "10,11"),

	C1405_2("1405", "NOTE", "废票箱容量", EventEntry.ALARM_LEVEL, "废票箱容量-已满", EventEntry.TYPE1, "10,11"),

	C1406_0("1406", "SELL", "票卡发售模块故障", EventEntry.NORMAL_LEVEL, "票卡发售模块故障-正常", EventEntry.TYPE1, "10,11"),

	C1406_2("1406", "SELL", "票卡发售模块故障", EventEntry.ALARM_LEVEL, "票卡发售模块故障-错误", EventEntry.TYPE1, "10,11"),

	C1500_0("1500", "RECOLLECT", "票卡回收模块", EventEntry.NORMAL_LEVEL, "票卡回收模块总体状态-正常", EventEntry.TYPE1, "7,8,9"),

	C1500_1("1500", "RECOLLECT", "票卡回收模块", EventEntry.WARNING_LEVEL, "票卡回收模块总体状态-警告", EventEntry.TYPE1, "7,8,9"),

	C1500_2("1500", "RECOLLECT", "票卡回收模块", EventEntry.ALARM_LEVEL, "票卡回收模块总体状态-报警", EventEntry.TYPE1, "7,8,9"),

	C1501_0("1501", "RECOLLECT", "票卡回收模块通讯状态", EventEntry.NORMAL_LEVEL, "票卡回收模块通讯状态-正常", EventEntry.TYPE1, "7,8,9"),

	C1501_2("1501", "RECOLLECT", "票卡回收模块通讯状态", EventEntry.ALARM_LEVEL, "票卡回收模块通讯状态-错误", EventEntry.TYPE1, "7,8,9"),

	C1502_0("1502", "RECOLLECT", "卡票", EventEntry.NORMAL_LEVEL, "卡票-正常", EventEntry.TYPE1, "7,8,9"),

	C1502_2("1502", "RECOLLECT", "卡票", EventEntry.ALARM_LEVEL, "卡票-通道被卡", EventEntry.TYPE1, "7,8,9"),

	C1503_0("1503", "RECOLLECT", "票箱容量", EventEntry.NORMAL_LEVEL, "票箱容量-正常", EventEntry.TYPE1, "7,8,9"),

	C1503_1("1503", "RECOLLECT", "票箱容量", EventEntry.WARNING_LEVEL, "票箱容量-将满", EventEntry.TYPE1, "7,8,9"),

	C1503_2("1503", "RECOLLECT", "票箱容量", EventEntry.ALARM_LEVEL, "票箱容量-已满", EventEntry.TYPE1, "7,8,9"),

	C1504_0("1504", "RECOLLECT", "票箱被取出", EventEntry.WARNING_LEVEL, "票箱被取出-正常", EventEntry.TYPE1, "7,8,9"),

	C1504_2("1504", "RECOLLECT", "票箱被取出", EventEntry.ALARM_LEVEL, "票箱被取出-未授权", EventEntry.TYPE1, "7,8,9"),

	C1505_0("1505", "NOTE", "废票箱容量", EventEntry.NORMAL_LEVEL, "废票箱容量-正常", EventEntry.TYPE1, "7,8,9"),

	C1505_1("1505", "NOTE", "废票箱容量", EventEntry.WARNING_LEVEL, "废票箱容量-将满", EventEntry.TYPE1, "7,8,9"),

	C1505_2("1505", "NOTE", "废票箱容量", EventEntry.ALARM_LEVEL, "废票箱容量-已满", EventEntry.TYPE1, "7,8,9"),

	C1506_0("1506", "RECOLLECT", "票卡回收模块故障", EventEntry.NORMAL_LEVEL, "票卡回收模块故障-正常", EventEntry.TYPE1, "7,8,9"),

	C1506_2("1506", "RECOLLECT", "票卡回收模块故障", EventEntry.ALARM_LEVEL, "票卡回收模块故障-错误", EventEntry.TYPE1, "7,8,9"),

	C2100_0("2100", "CHANNEL", "自动检票机通行状态", EventEntry.NORMAL_LEVEL, "自动检票机通行状态-正常", EventEntry.TYPE1, "7,8,9"),

	C2100_1("2100", "CHANNEL", "自动检票机通行状态", EventEntry.WARNING_LEVEL, "自动检票机通行状态-警告", EventEntry.TYPE1, "7,8,9"),

	C2100_2("2100", "CHANNEL", "自动检票机通行状态", EventEntry.ALARM_LEVEL, "自动检票机通行状态-报警", EventEntry.TYPE1, "7,8,9"),

	C2101_0("2101", "CHANNEL", "通道方向", EventEntry.NORMAL_LEVEL, "通道方向-进站", EventEntry.TYPE1, "7,8,9"),

	C2101_1("2101", "CHANNEL", "通道方向", EventEntry.NORMAL_LEVEL, "通道方向-出站", EventEntry.TYPE1, "7,8,9"),

	C2101_2("2101", "CHANNEL", "通道方向", EventEntry.NORMAL_LEVEL, "通道方向-双向", EventEntry.TYPE1, "7,8,9"),

	C2102_0("2102", "CHANNEL", "通道模式", EventEntry.NORMAL_LEVEL, "通道模式-常开", EventEntry.TYPE1, "7,8,9"),

	C2102_1("2102", "CHANNEL", "通道模式", EventEntry.NORMAL_LEVEL, "通道模式-常闭", EventEntry.TYPE1, "7,8,9"),

	C2103_0("2103", "CHANNEL", "扇门状态", EventEntry.NORMAL_LEVEL, "扇门状态-正常", EventEntry.TYPE1, "7,8,9"),

	C2103_2("2103", "CHANNEL", "扇门状态", EventEntry.ALARM_LEVEL, "扇门状态-出错", EventEntry.TYPE1, "7,8,9"),

	C2200_0("2200", "COMMUNICATION", "通讯状态", EventEntry.NORMAL_LEVEL, "通讯状态-离线", EventEntry.TYPE1, "7,8,9,10,11,12"),

	C2200_1("2200", "COMMUNICATION", "通讯状态", EventEntry.NORMAL_LEVEL, "通讯状态-在线", EventEntry.TYPE1, "7,8,9,10,11,12");

	/**
	 * 0-正常
	 */
	public static final short NORMAL_LEVEL = 0;

	/**
	 * 1--警告
	 */
	public static final short WARNING_LEVEL = 1;

	/**
	 * 2--报警
	 */
	public static final short ALARM_LEVEL = 2;

	// 硬件部件事件
	public final static short HARD_WARE_EVENT = 0;

	// 运营事件
	public final static short TYPE1 = 1;

	// 软件事件
	public final static short TYPE2 = 2;

	private final short tagValue;

	private final int tagId;

	private final String componentType;

	private final String tagName;

	private final short eventLevel;

	private final String desc;

	private final short eventType;

	/**
	 * 设备类型： 7、8、9-AGM（自动检票机） 12-ISM（综合服务终端）  10-BOM（半自动售票机）  11-TVM（自动售票机）
	 */
	private final String remark;

	public short getEventType() {
		return eventType;
	}

	public String getRemark() {
		return remark;
	}

	public short getEventLevel() {
		return eventLevel;
	}

	public String getComponentType() {
		return componentType;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * 事件常量构造，如果eventType=EventEntry.HARD_WARE_EVENT，则componentType不能为空
	 * 
	 * @param componentType
	 * @param componentName
	 * @param eventLevel
	 * @param desc
	 * @param eventType
	 * @param remark
	 */
	private EventEntry(String tagId, String componentType, String tagName, short eventLevel, String desc,
			short eventType, String remark) {
		this.tagId = Integer.valueOf(tagId, 16);
		this.componentType = componentType;
		this.tagName = tagName;
		this.desc = desc;
		this.eventLevel = eventLevel;
		this.eventType = eventType;
		this.remark = remark;
		if (this.name().indexOf('_') > 0) {
			String[] _ts = this.name().split("_");
			this.tagValue = Short.parseShort(_ts[1]);
		} else {
			this.tagValue = 0;
		}
	}

	public String getTagName() {
		return tagName;
	}

	public short getTagValue() {
		return tagValue;
	}

	public int getTagId() {
		return tagId;
	}

	private static Log logger = LogFactory.getLog(EventEntry.class);

	// XDR解析后为十进制，事件ID是十六进制
	public static EventEntry getEventEntry(int tagId, short tagValue) {
		EventEntry entry = null;
		if (tagId == 602 && tagValue != 0) {
			return C0602_99;
		} else if (tagId == 702 && tagValue != 0) {
			return C0702_99;

		} else {
			String cid = String.format("C%04d_%d", tagId, tagValue);
			try {
				entry = EventEntry.valueOf(cid);
			} catch (Exception e1) {
				logger.error("事件常量表中找不到tagName：" + cid, e1);
			}
			return entry;
		}
	};
}
