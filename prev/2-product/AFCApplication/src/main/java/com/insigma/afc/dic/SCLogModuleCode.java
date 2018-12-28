package com.insigma.afc.dic;

import com.insigma.commons.dic.ModuleCode;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = SCLogModuleCode.class)
public class SCLogModuleCode extends ModuleCode {

	private static SCLogModuleCode instance = new SCLogModuleCode();

	private SCLogModuleCode() {

	}

	public static SCLogModuleCode getInstance() {
		return instance;
	}

	// ------------系统管理子系统-----------------
	// 日志管理模块编号
	@DicItem
	public static Integer MODULE_LOG_MANAGER = 302;

	// 权限管理模块编号
	@DicItem
	public static final Integer MODULE_AUTH_MANAGER = 301;

	// 数据文件管理
	@DicItem
	public static Integer DATA_FILE_MANAGE = 303;

	// 交易明细查询
	@DicItem
	public static Integer TRANS_DETAIL_QUERY = 305;

	// 日始日终查询
	@DicItem
	public static Integer DAILY_LOG_QUERY = 306;

	// ------------参数管理子系统-----------------
	// 版本管理模块编号
	@DicItem
	public static Integer MODULE_PARAM_MANAGER = 202;

	// 参数查看模块编号
	@DicItem
	public static Integer MODULE_PARAM_EDITOR = 201;
	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_REFRESH = 20101;
	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_HISTORY_SEARCH = 20102;

	// 运营参数管理模块编号
	@DicItem
	public static Integer MODULE_PARAM_CONFIG = 203;

	// ------------监控管理子系统-----------------

	@DicItem
	public static Integer MODULE_MAP_EDITOR = 105;

	// 客流监控模块编号MODULE_MAP_EDITOR
	@DicItem
	public static Integer MODULE_OD_MONITOR = 104;

	// 设备查询模块编号
	@DicItem
	public static Integer MODULE_DEVICE_SEARCH = 103;

	// 车站监控模块编号
	@DicItem
	public static Integer MODULE_MONITOR_STATION = 102;

	// ------------后台处理子系统-----------------
	// 通信前置模块编号
	@DicItem
	public static Integer MODULE_COMM = 1400;

	// 数据处理 - 文件处理模块编号
	@DicItem
	public static Integer MODULE_FILE_HANDLE = 1401;

	// 数据处理 - 文件打包模块编号
	@DicItem
	public static Integer MODULE_FILE_PACK = 1402;

	// ------------------------------------------
	// 报表模块编号
	@DicItem
	public static Integer MODULE_REPORT = 1500;

	// 票务处理模块编号
	@DicItem
	public static Integer MODULE_DATA_TICKET = 1600;

	//任务管理
	@DicItem
	public static Integer MODULE_TASK = 5001;

}
