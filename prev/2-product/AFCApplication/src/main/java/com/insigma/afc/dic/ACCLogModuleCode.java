package com.insigma.afc.dic;

import com.insigma.commons.dic.ModuleCode;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

@Dic(overClass = ACCLogModuleCode.class)
public class ACCLogModuleCode extends ModuleCode {

	private static ACCLogModuleCode instance = new ACCLogModuleCode();

	private ACCLogModuleCode() {

	}

	public static ACCLogModuleCode getInstance() {
		return instance;
	}

	@DicItem
	public static Integer LOG_LOGIN_LOGOUT = 1000;

	// 1000：第一位预留,第二位子系统编号，第三位和第四位为子系统中各模块逻辑编号
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

	// -------------------------------------------

	// BOM模块
	@DicItem
	public static Integer MODULE_BOM = 1100;

	// 其它模块
	@DicItem
	public static Integer MODULE_OTHER = 1900;

	// 文件服务模块编号基数
	@DicItem
	public static Integer FILE_SERVER_BASIC = 1203;

	// 1000：第一位预留,第二位子系统编号，第三位和第四位为子系统中各模块逻辑编号
	// ------------系统管理子系统-----------------
	// 日志管理模块编号
	@DicItem
	public static Integer MODULE_LOG_MANAGER = 5304;

	// 日志查询
	@DicItem
	public static Integer MODULE_LOG_MANAGER_LOG_QUERY = 530401;

	// 权限管理模块编号
	@DicItem
	public static Integer MODULE_AUTH_MANAGER = 5301;

	// ------------参数管理子系统-----------------
	/******运营参数编辑******/
	// 参数编辑模块编号
	@DicItem
	public static Integer MODULE_PARAM_EDITOR = 4201;

	// 一票通黑名单
	@DicItem
	public static Integer MODULE_PARAM_EDITOR_YPT_BLACK = 420101;

	// 一票通号段黑名单
	@DicItem
	public static Integer MODULE_PARAM_EDITOR_RANGE_YPT_BLACK = 420102;

	// 员工黑名单
	@DicItem
	public static Integer MODULE_PARAM_EDITOR_STAFF_BLACK = 420103;

	// 一卡通文件导入
	@DicItem
	public static Integer MODULE_PARAM_EDITOR_IMPORT_YKT = 420104;

	// 黑名单转正
	@DicItem
	public static Integer MODULE_PARAM_EDITOR_BLK_REGULATE = 420105;

	// 运营参数发布
	@DicItem
	public static Integer MODULE_PARAM_EDITOR_PARAMETER_PUBLISH = 420106;

	// 运营参数导出
	@DicItem
	public static Integer MODULE_PARAM_EDITOR_OPT_EXPORT = 420107;

	/******参数管理******/
	// 版本管理模块编号
	@DicItem
	public static Integer MODULE_PARAM_MANAGER = 4202;
	// 版本查询
	@DicItem
	public static Integer MODULE_PARAM_MANAGER_VERSION_QUERY = 420201;
	// 下发/发布查询
	@DicItem
	public static Integer MODULE_PARAM_MANAGER_PUBLISH_DOWN = 420202;
	// 参数导入
	@DicItem
	public static Integer MODULE_PARAM_MANAGER_EOD_IMPORT = 420203;
	// ACC参数发布
	@DicItem
	public static Integer MODULE_PARAM_MANAGER_PARAMETER_PUBLISH = 420204;
	// ACC参数导出
	@DicItem
	public static Integer MODULE_PARAM_MANAGER_PARAMETER_EXPORT = 420205;
	// 参数切换查询
	@DicItem
	public static Integer MODULE_PARAM_MANAGER_PARAMETER_SWITCH = 420206;

	// 运营参数管理模块编号
	@DicItem
	public static Integer MODULE_PARAM_CONFIG = 4203;
	// 黑灰名详细信息
	@DicItem
	public static Integer MODULE_PARAM_CONFIG_BLK_DETAIL = 420301;
	// 模式履历信息查询
	@DicItem
	public static Integer MODULE_PARAM_CONFIG_WAIVER_DETIAL = 420302;

	// EOD参数编辑模块编号
	@DicItem
	public static Integer MODULE_PARAM_EOD = 4204;
	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_NEW = 420401;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_TESTSWITCH = 420401;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_REMOVE = 420401;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_EXCEL_IMPORT = 420401;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_SAVE = 420401;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_REFRESH = 420401;

	@DicItem
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_HISTORY_SEARCH = 420401;

	// ------------监控管理子系统-----------------
	/******路网监控******/
	// 路网监控模块编号
	@DicItem
	public static Integer MODULE_MONITOR = 3101;

	// 运营模式
	@DicItem
	public static Integer MODULE_MONITOR_MODE = 310101;

	// 发送命令
	@DicItem
	public static Integer MODULE_MONITOR_COMMAND = 310102;

	// 监控配置
	@DicItem
	public static Integer MODULE_MONITOR_CONFIG = 310103;

	// 刷新
	@DicItem
	public static Integer MODULE_MONITOR_REFRESH = 310104;

	// 节点管理模块编号
	@DicItem
	public static Integer MODULE_MAP_EDITOR = 3106;

	// 节点配置模块编号
	@DicItem
	public static Integer MODULE_MAP_NODE_CONFIG = 3107;

	/******客流监控相关******/

	// 客流监控模块编号
	@DicItem
	public static Integer MODULE_OD_MONITOR = 3104;

	// 柱状图
	@DicItem
	public static Integer MODULE_OD_MONITOR_CHART_BAR = 310401;

	// 饼图
	@DicItem
	public static Integer MODULE_OD_MONITOR_CHART_PIE = 310402;

	// 曲线图
	@DicItem
	public static Integer MODULE_OD_MONITOR_SERIES = 310403;

	// 客流查询
	@DicItem
	public static Integer MODULE_OD_MONITOR_OD_SEARCH = 310404;

	// 断面客流
	@DicItem
	public static Integer MODULE_OD_MONITOR_SECTION_FLOW = 3105;

	// 断面客流地图
	@DicItem
	public static Integer MODULE_OD_MONITOR_SECTION_FLOW_MAP = 310501;

	// 断面客流查询
	@DicItem
	public static Integer MODULE_OD_MONITOR_SECTION_FLOW_SEARCH = 310502;

	/******设备查询相关******/

	// 设备查询模块编号
	@DicItem
	public static Integer MODULE_DEVICE_SEARCH = 3103;

	// 寄存器查询
	@DicItem
	public static Integer MODULE_DEVICE_SEARCH_REGISTER = 310301;

	// 模式广播查询
	@DicItem
	public static Integer MODULE_DEVICE_SEARCH_MODE_BROADCAST = 310302;

	// 命令日志查询
	@DicItem
	public static Integer MODULE_DEVICE_SEARCH_CMDLOG = 310303;

	// 模式上传查询
	@DicItem
	public static Integer MODULE_DEVICE_SEARCH_MODEUPLOAD = 310304;

	// ------------------------------------------
	// 报表模块编号
	@DicItem
	public static Integer MODULE_REPORT = 1500;

	// 票务处理模块编号
	@DicItem
	public static Integer MODULE_DATA_TICKET = 1600;

	// ------------计划任务管理子系统-----------------
	/******计划任务管理******/
	// 计划任务
	@DicItem
	public static Integer MODULE_TASK = 5401;

	// 计划任务:一卡通文件上传
	@DicItem
	public static Integer MODULE_TASK_YKT_UPLOAD = 1711;

	// 计划任务:一卡通文件下载
	@DicItem
	public static Integer MODULE_TASK_YKT_DOWNLOAD = 1712;

	// 计划任务:可疑交易调整文件生成
	@DicItem
	public static Integer MODULE_TASK_ADJ_FILE = 1713;

	// 计划任务:FTP审计文件生成
	@DicItem
	public static Integer MODULE_TASK_FTP_AR = 1714;

	// 计划任务:参数将来版本切换监控
	@DicItem
	public static Integer MODULE_TASK_EOD_SWITCH_MONITOR = 1715;

	// 计划任务:模式履历版本生成
	@DicItem
	public static Integer MODULE_TASK_WAIVER = 1716;

	// 计划任务:清分数据文件生成
	@DicItem
	public static Integer MODULE_TASK_CLEAR_DATA_FILE = 1717;

	// 计划任务:交易对账文件生成
	@DicItem
	public static Integer MODULE_TASK_TRAN_DATA_FILE = 1718;

	// 计划任务:一票通交易TAC验证
	@DicItem
	public static Integer MODULE_TASK_TAC = 1719;

	// 计划任务:库存文件入库
	@DicItem
	public static Integer MODULE_TASK_STOCK = 1720;

	// ------------------------------
	// 密钥生成
	@DicItem
	public static Integer MODULE_SAM_CREATE = 7401;

	// 密钥分散和导出
	@DicItem
	public static Integer MODULE_SAM_EXP = 7402;

	// ------------------------------
	// 基础资料管理
	@DicItem
	public static Integer MODULE_CLS_BASE = 8101;

	// 清分规则管理
	@DicItem
	public static Integer MODULE_CLS_RULE = 8102;

	// -------------清算管理-----------------
	/******清算管理******/
	// 一票通日终处理
	@DicItem
	public static Integer MODULE_YPT_DAILY = 8201;

	// 一卡通日终处理
	@DicItem
	public static Integer MODULE_YKT_DAILY = 8202;

	// 清算调整
	@DicItem
	public static Integer MODULE_CLS_ADJ = 8203;

	// 清算结果调整
	@DicItem
	public static Integer MODULE_CLS_ADJ_CLEAR = 820301;

	// 可疑帐调整
	@DicItem
	public static Integer MODULE_CLS_ADJ_FISHY = 820302;

	//    // 可疑帐的调整
	//    @DicItem
	//    public static Integer MODULE_BILL_ADJ = 8204;

	// 综合查询
	@DicItem
	public static Integer MODULE_COMM_SEARCH = 8204;

	// 对账数据
	@DicItem
	public static Integer MODULE_COMM_SEARCH_BILL_CHECK = 820401;

	// 分账查询
	@DicItem
	public static Integer MODULE_COMM_SEARCH_FARE_QUERY = 820402;

	// ACC服务费
	@DicItem
	public static Integer MODULE_COMM_SEARCH_ACC_FARE_QUERY = 820403;

	// 票务服务费
	@DicItem
	public static Integer MODULE_COMM_SEARCH_FEE_QUERY = 820404;

	// 收益查询
	@DicItem
	public static Integer MODULE_COMM_SEARCH_PROFIT_QUERY = 820405;

	// 对账文件导出
	@DicItem
	public static Integer MODULE_COMM_SEARCH_FILE_EXPORT = 820406;

	// 一卡通清分服务费
	@DicItem
	public static Integer MODULE_COMM_SEARCH_ACC_YKT_FEE_QUERY = 820407;

	public static Integer MODULE_CLS_MNG = 64;

	//-----------------SAM卡管理------------------
	/******SAM卡管理******/
	// SAM卡管理
	@DicItem
	public static Integer MODULE_SAM_MNG = 1801;

	// SAM卡绑定设备管理
	@DicItem
	public static Integer MODULE_SAM_MNG_BIND = 180101;

	// SAM卡跟踪管理
	@DicItem
	public static Integer MODULE_SAM_MNG_TRACK = 180102;

	// 黑名单
	@DicItem
	public static Integer MODULE_SAM_MNG_BLACK = 180103;

	//-----------------票务管理------------------
	/******车票跟踪和查询******/
	// 车票跟踪和查询
	@DicItem
	public static Integer MODULE_TICKET_TRACE_QUERY = 1601;

	// 设备交易跟踪
	@DicItem
	public static Integer MODULE_TICKET_QUERY_DEV_TRANS_TRACE = 160101;

	// 车票交易跟踪
	@DicItem
	public static Integer MODULE_TICKET_QUERY_TICKET_TRANS_TRACE = 160102;

	// 车票流通跟踪
	@DicItem
	public static Integer MODULE_TICKET_QUERY_CIRCULATE_TRACE = 160103;

	// 单程票对比查询
	@DicItem
	public static Integer MODULE_TICKET_QUERY_COMP = 160104;

	/******交易数据查询******/
	// 交易数据查询
	@DicItem
	public static Integer MODULE_TRAN_DATA_QUERY = 1602;

	// 正常交易明细查询
	@DicItem
	public static Integer MODULE_TRAN_DETAIL_QUERY = 160201;

	/******员工票申请管理******/
	// 员工票申请管理
	@DicItem
	public static Integer MODULE_EMP_TICKET_NMG = 1603;

	// 员工票申请管理
	@DicItem
	public static Integer MODULE_EMP_TICKET_NMG_APP = 160301;

	/******车票挂失解挂管理******/
	// 车票挂失解挂管理
	@DicItem
	public static Integer MODULE_TICKET_LOST_NMG = 1604;

	// 车票挂失解挂管理
	@DicItem
	public static Integer MODULE_TICKET_LOST_NMG_APP = 160401;

	/******车票退款管理******/
	// 车票退款管理
	@DicItem
	public static Integer MODULE_TICKET_REFUND_NMG = 1605;

	// 车票退款管理
	@DicItem
	public static Integer MODULE_TICKET_REFUND_NMG_APP = 160501;

	/******后台处理******/
	// 通信处理模块
	@DicItem
	public static Integer MODULE_MESSAGE_HANDLER = 1400;

	// 文件处理模块
	@DicItem
	public static Integer MODULE_FILE_HANDLER = 1401;

	/******其他******/
	// 数据库后台模块
	@DicItem
	public static Integer MODULE_DATABASE = 0;

	//-----------------密钥管理系统------------------
	/******发卡管理******/
	// 发卡管理
	@DicItem
	public static Integer MODULE_SAM_ISSU = 3301;

	//查询发行日志
	@DicItem
	public static Integer MODULE_SAM_ISSU_LOG = 330101;

	//发行信息统计
	@DicItem
	public static Integer MODULE_SAM_ISSU_STA = 330102;

	//端口测试
	@DicItem
	public static Integer MODULE_SAM_ISSU_PORT_TEST = 330103;

	//导入一卡通密钥
	@DicItem
	public static Integer MODULE_SAM_ISSU_YKT_IMPORT = 330104;

	//读取发行商主控卡及初始卡片密钥
	@DicItem
	public static Integer MODULE_SAM_ISSU_LOAD_MAINT_CTRL = 330105;

	//业务分散因子卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_FACTOR_CARD = 330106;

	//读取业务分散因子卡
	@DicItem
	public static Integer MODULE_SAM_ISSU_LOAD_FACTOR = 330107;

	//卡片初始化
	@DicItem
	public static Integer MODULE_SAM_ISSU_INIT = 330108;

	//领导因子卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_LEAD_FACTOR = 330109;

	//总控卡发行(领导因子)
	@DicItem
	public static Integer MODULE_SAM_ISSU_MAINT_CTRL_LEAD = 330110;

	//总控卡发行(加密机)
	@DicItem
	public static Integer MODULE_SAM_ISSU_MAINT_CTRL_ENCRY = 330111;

	//总控卡密钥导出到加密机
	@DicItem
	public static Integer MODULE_SAM_ISSU_MAINT_CTRL_KEY_EXP = 330112;

	//ISAM母卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_MAIN_ISAM = 330113;

	//PSAM母卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_MAIN_PSAM = 330114;

	//PBOC母卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_MAIN_PBOC = 330115;

	//PSAM卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_PSAM = 330116;

	//ISAM卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_ISAM = 330117;

	//PBOC卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_PBOC = 330118;

	// ESAM卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_ESAM = 330119;

	// l领导因子加密解密卡发行
	@DicItem
	public static Integer MODULE_SAM_ISSU_FACTOR_ENCODER = 330120;

}
