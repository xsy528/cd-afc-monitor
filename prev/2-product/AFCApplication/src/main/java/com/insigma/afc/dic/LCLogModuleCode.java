package com.insigma.afc.dic;

import com.insigma.commons.dic.ModuleCode;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = LCLogModuleCode.class)
public class LCLogModuleCode extends ModuleCode {

	private static LCLogModuleCode instance = new LCLogModuleCode();

	private LCLogModuleCode() {

	}

	public static LCLogModuleCode getInstance() {
		return instance;
	}

	// 1000：第一位预留,第二位子系统编号，第三位和第四位为子系统中各模块逻辑编号
	// ------------系统管理子系统-----------------
	// 日志管理模块编号
	@DicItem
	public static Integer MODULE_LOG_MANAGER = 5304;

	// 权限管理模块编号
	@DicItem
	public static final Integer MODULE_AUTH_MANAGER = 5301;

	// 数据文件管理
	@DicItem
	public static Integer DATA_FILE_MANAGE = 5305;

	// 交易明细查询
	@DicItem
	public static Integer TRANS_DETAIL_QUERY = 5306;

	// 日始日终查询
	@DicItem
	public static Integer DAILY_LOG_QUERY = 5307;

	// ------------参数管理子系统-----------------
	// 版本管理模块编号
	@DicItem
	public static Integer MODULE_PARAM_MANAGER = 4202;

	// 参数编辑模块编号
	@DicItem
	public static Integer MODULE_PARAM_EDITOR = 4201;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_NEW = 420301;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_TESTSWITCH = 420301;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_REMOVE = 420301;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_EXCEL_IMPORT = 420301;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_SAVE = 420301;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_REFRESH = 420301;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_HISTORY_SEARCH = 420301;

	// 运营参数管理模块编号
	@DicItem
	public static Integer MODULE_PARAM_CONFIG = 4203;

	// 软件管理
	@DicItem
	public static Integer MODULE_SOFT_MANAGER = 4204;

	// ------------监控管理子系统-----------------
	// 路网监控模块编号
	@DicItem
	public static Integer MODULE_MONITOR = 3101;

	// 节点管理模块编号
	@DicItem
	public static Integer MODULE_MAP_EDITOR = 3106;

	// 节点配置模块编号
	@DicItem
	public static Integer MODULE_MAP_NODE_CONFIG = 3107;

	// 客流监控模块编号
	@DicItem
	public static Integer MODULE_OD_MONITOR = 3104;

	// 设备查询模块编号
	@DicItem
	public static Integer MODULE_DEVICE_SEARCH = 3103;

	// 车站监控模块编号
	@DicItem
	public static Integer MODULE_MONITOR_STATION = 3102;

	// ------------------------------------------
	// 报表模块编号
	@DicItem
	public static Integer MODULE_REPORT = 1500;

	// 票务处理模块编号
	@DicItem
	public static Integer MODULE_DATA_TICKET = 1600;

	//任务管理
	@DicItem
	public static Integer MODULE_TASK = 6601;

}
