package com.insigma.acc.wz.module.code;

import com.insigma.afc.dic.ACCLogModuleCode;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 
 * Ticket: 温州线路ModuleCode
 * @author  yaoyue
 *
 */
@Dic(overClass = ACCLogModuleCode.class)
public class WZACCLogModuleCode {

	private static WZACCLogModuleCode instance = new WZACCLogModuleCode();

	public static WZACCLogModuleCode getInstance() {
		return instance;
	}

	@DicItem
	public static Integer LOG_DEFAULT = 2;

	@DicItem
	public static Integer LOG_LOGIN_LOGOUT = 1000;

	@DicItem
	public static Integer LOG_DATABASE_OPERATION = 0;

	@DicItem
	public static Integer MODULE_OTHER = 9900;

	/** -----------------------ACC工作台 1----------------------- */
	/** -----------------------参数管理10----------------------- */
	public static Integer MODULE_PARAMETER_MANAGE = 10;
	/* 设备参数管理 */
	public static Integer MODULE_PARAM_MANAGER = 1001;
	//车站版本查询
	public static Integer MODULE_PARAMETER_MANAGE_EOD_STATIONVERSION_REALTIME_QUERY = 100101;
	//设备版本查询
	public static Integer MODULE_PARAMETER_MANAGE_EOD_DEVICEVERSION_REALTIME_QUERY = 100102;
	//参数切换查询
	public static Integer MODULE_PARAMETER_MANAGE_EOD_SWITCH_QUERY = 100103;
	//设备参数导入
	public static Integer MODULE_PARAMETER_MANAGE_EOD_IMPORT = 100104;
	//参数发布
	public static Integer MODULE_PARAMETER_MANAGE_EOD_PUBLISH = 100105;
	//设备参数导出
	public static Integer MODULE_PARAMETER_MANAGE_EOD_EXPORT = 100106;

	//	@DicItem
	//	public static Integer MODULE_PARAMETER_MANAGE_EOD_PUBLISH_DOWN_QUERY = 100103;

	//	@DicItem
	//	public static Integer MODULE_PARAMETER_MANAGE_EOD_FILE_GENERATE = 100105;

	//	@DicItem
	//	public static Integer MODULE_PARAMETER_MANAGE_BLK_QUERY = 100108;
	//	@DicItem
	//	public static Integer MODULE_PARAMETER_MANAGE_WAIVER_QUERY = 100109;

	/* 设备参数编辑 */
	public static Integer MODULE_PARAM_EDITOR = 1002;
	//新建
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_NEW = 100201;
	//转换
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_TESTSWITCH = 100202;
	//移除
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_REMOVE = 100203;
	//保存
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_SAVE = 100204;
	//刷新
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_REFRESH = 100205;
	//查询
	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_HISTORY_SEARCH = 100206;
	//	@DicItem
	//	public static Integer MODULE_PARAMETER_MANAGE_EODEDITOR_EXCEL_IMPORT = 100207;

	/* 运营参数编辑 */
	public static Integer MODULE_PARAMETER_MANAGE_RUNEDITOR = 1003;
	//一票通黑名单
	public static Integer MODULE_PARAMETER_MANAGE_RUNEDITOR_YPT_BLK = 100301;
	//一票通号段黑名单
	public static Integer MODULE_PARAMETER_MANAGE_RUNEDITOR_YPT_RANGE_BLK = 100302;
	//员工卡黑名单
	public static Integer MODULE_PARAMETER_MANAGE_RUNEDITOR_STAFF_BLK = 100303;
	//单程票回收条件
	public static Integer MODULE_PARAMETER_MANAGE_RUNEDITOR_SINGLE_RECYCLE = 100304;
	//模式履历查询
	public static Integer MODULE_PARAMETER_MANAGE_RUNEDITOR_WAIVER = 100305;
	//票卡库存目录
	public static Integer MODULE_PARAMETER_MANAGE_RUNEDITOR_STOCK_CATEGORY = 100306;

	/* 操作员管理 */
	public static Integer MODULE_PARAMETER_MANAGE_OPERATOR = 1004;
	//操作员参数查看
	public static Integer MODULE_PARAMETER_MANAGE_OPERATOR_VIEW = 100401;
	//操作员参数编辑
	public static Integer MODULE_PARAMETER_MANAGE_OPERATOR_EDIT = 100402;

	/* 运营参数管理 */
	//TODO
	public static Integer MODULE_PARAMETER_MANAGE_RUN = 1005;
	//运营参数转正
	public static Integer MODULE_PARAMETER_MANAGE_RUN_CONVERT = 100501;
	//运营参数发布
	public static Integer MODULE_PARAMETER_MANAGE_RUN_PUBLISH = 100502;
	//黑名单版本信息查询
	public static Integer MODULE_PARAMETER_MANAGE_RUN_BLK_VERSION_QUERY = 100503;
	//模式履历版本信息查询
	public static Integer MODULE_PARAMETER_MANAGE_RUN_WAIVER_VERSION_QUERY = 100504;
	//单程票回收条件版本信息查询
	public static Integer MODULE_PARAMETER_MANAGE_RUN_SINGLERECYCLE_VERSION_QUERY = 100505;
	//票卡库存目录版本信息查询
	public static Integer MODULE_PARAMETER_MANAGE_RUN_STOCKCATEGORY_VERSION_QUERY = 100506;
	//操作员权限版本信息查询
	public static Integer MODULE_PARAMETER_MANAGE_RUN_OPERATORRIGHT_VERSION_QUERY = 100507;
	//操作员版本信息查询
	public static Integer MODULE_PARAMETER_MANAGE_RUN_OPERINFO_VERSION_QUERY = 100508;
	//车站运营参数版本查询
	public static Integer MODULE_PARAMETER_MANAGE_RUN_STATIONVERSION_REALTIME_QUERY = 100509;
	//运营参数导出
	public static Integer MODULE_PARAMETER_MANAGE_RUN_EXPORT = 100510;
	//设备运营参数版本查询
	public static Integer MODULE_PARAMETER_MANAGE_RUN_DEVICEVERSION_REALTIME_QUERY = 100511;

	/* 软件管理 */
	//TODO
	public static Integer MODULE_SOFT_MANAGER = 1006;
	//车站版本管理
	public static Integer MODULE_SOFT_MANAGER_STATION_VERSION = 100601;
	//设备版本管理
	public static Integer MODULE_SOFT_MANAGER_DEVICE_VERSION = 100602;
	//软件导入
	public static Integer MODULE_SOFT_MANAGER_SOFT_IMPORT = 100603;
	//软件导出
	public static Integer MODULE_SOFT_MANAGER_SOFT_EXPORT = 100604;
	//软件发布
	public static Integer MODULE_SOFT_MANAGER_SOFT_PUBLISH = 100605;
	//软件切换查询
	public static Integer MODULE_SOFT_MANAGER_SOFT_SWITCH = 100606;

	/* 二维码公钥参数管理*/
	//TODO
	public static Integer MODULE_QRKEY_MANAGE = 1007;
	//车站版本查询
	public static Integer MODULE_QRKEY_MANAGE_STATIONVERSION_REALTIME_QUERY = 100701;
	//设备版本查询
	public static Integer MODULE_QRKEY_MANAGE_DEVICEVERSION_REALTIME_QUERY = 100702;
	//参数发布
	public static Integer MODULE_QRKEY_MANAGE_PUBLISH = 100703;

	/* 二维码公钥参数编辑*/
	//TODO
	public static Integer MODULE_QRKEY_EDITOR = 1008;
	//新建
	public static Integer MODULE_QRKEY_EDITOR_NEW = 100801;
	//转正
	public static Integer MODULE_QRKEY_EDITOR_CONVERT = 100802;
	//查询
	public static Integer MODULE_QRKEY_EDITOR_HISTORY_SEARCH = 100803;

	/** -----------------------监控管理11----------------------- */
	public static Integer MODULE_MONITOR_MANAGE = 11;
	/* 路网监控 */
	public static Integer MODULE_MONITOR = 1101;
	//运营模式
	public static Integer MODULE_MONITOR_MANAGE_LINE_OPERATING_MODE = 110101;
	//模式查询
	public static Integer MODULE_MONITOR_MANAGE_LINE_MODE_SEARCH = 110102;
	//时钟同步
	public static Integer MODULE_MONITOR_MANAGE_LINE_TIME_SYN = 110103;
	//发送控制命令
	public static Integer MODULE_MONITOR_MANAGE_LINE_CONTROL_COMMAND = 110104;
	//监视设备
	public static Integer MODULE_MONITOR_MANAGE_LINE_DEVICE_MONITOR = 110105;
	//	@DicItem
	//	public static Integer MODULE_MONITOR_MANAGE_LINE_REGISTER_DATA = 110106;
	//查看票箱/钱箱状态
	public static Integer MODULE_MONITOR_MANAGE_LINE_TICKET_BOX = 110107;
	//刷新
	public static Integer MODULE_MONITOR_MANAGE_LINE_REFRESH = 110108;
	//监控设置
	public static Integer MODULE_MONITOR_MANAGE_LINE_MONITOR_CONFIGURATION = 110109;

	/* 客流监控 */
	public static Integer MODULE_OD_MONITOR = 1102;
	//柱状图
	public static Integer MODULE_MONITOR_MANAGE_PASSAGER_BAR = 110201;
	//饼 图
	public static Integer MODULE_MONITOR_MANAGE_PASSAGER_PIE = 110202;
	//曲线图
	public static Integer MODULE_MONITOR_MANAGE_PASSAGER_CHAR = 110203;
	//	票卡对比
	//	public static Integer MODULE_MONITOR_MANAGE_PASSAGER_TICKETCARD = 110204;
	//客流查询
	public static Integer MODULE_MONITOR_MANAGE_PASSAGER_MOINTOR = 110205;

	/* 节点管理 */
	public static Integer MODULE_MAP_EDITOR = 1104;
	//线网布局
	public static Integer MODULE_MONITOR_MANAGE_NODE_LINE_MANAGE = 110401;
	//保存
	public static Integer MODULE_MONITOR_MANAGE_NODE_SAVE = 110402;
	//	@DicItem
	//	public static Integer MODULE_MONITOR_MANAGE_LINE_EXPORT_MAP = 110403;
	//地图同步
	public static Integer MODULE_MONITOR_MANAGE_LINE_AYNCHRONIZATION_MAP = 110404;
	//撤销
	public static Integer MODULE_MONITOR_MANAGE_LINE_DO = 110405;
	//重做
	public static Integer MODULE_MONITOR_MANAGE_LINE_UNDO = 110406;
	//车站配置文件导入
	public static Integer MODULE_MONITOR_MANAGE_NODE_STAION_MAP_IMPORT = 110407;

	/* 断面客流 */
	//TODO
	public static Integer MODULE_MONITOR_MANAGE_SECTION_PASSAGER = 1105;
	// 断面客流图监控
	public static Integer MODULE_MONITOR_MANAGE_SECTION_PASSAGER_MAP = 110501;
	// 断面客流表监控
	public static Integer MODULE_MONITOR_MANAGE_SECTION_PASSAGER_TABLE = 110502;
	// 断面客流配置
	public static Integer MODULE_MONITOR_MANAGE_SECTION_PASSAGER_CONFIG = 110503;

	/*各类查询 */
	public static Integer MODULE_DEVICE_SEARCH = 1103;
	//模式上传
	public static Integer MODULE_MONITOR_MANAGE_QUERY_MODE_UPLOAD = 110301;
	//模式广播
	public static Integer MODULE_MONITOR_MANAGE_QUERY_MODE_BROADCAST = 110302;
	//模式日志
	public static Integer MODULE_MONITOR_MANAGE_QUERY_MODE_LOG = 110303;
	//命令日志
	public static Integer MODULE_MONITOR_MANAGE_DEVICE_COMMAND = 110304;
	//设备事件
	public static Integer MODULE_MONITOR_MANAGE_DEVICE_ENEVT = 110305;
	//	@DicItem
	//	public static Integer MODULE_MONITOR_MANAGE_DEVICE_STATUS = 110306;
	//	@DicItem
	//	public static Integer MODULE_MONITOR_MANAGE_MODE_NOTIFY_SEARCH = 110307;
	//	@DicItem
	//	public static Integer MODULE_MONITOR_MANAGE_DEVICE_REGISTER_SEARCH = 110308;
	//	@DicItem
	//	public static Integer MODULE_MONITOR_MANAGE_FLOWUPLOAD_SEARCH = 110309;

	/** -----------------------数据管理12----------------------- */
	//public static Integer MODULE_DATA_MANAGE = 12;
	//对账文件管理
	//	@DicItem
	//	public static Integer RECONCILE_FILE_MANAGE = 1205;
	//	@DicItem
	//	public static Integer MODULE_MONITOR_MANAGE_FTPAUDIT_FILE_SEARCH = 120501;
	//	@DicItem
	//	public static Integer MODULE_MONITOR_MANAGE_ACCOUNTINGCHECK_FILE_SEARCH = 120502;
	/** -----------------------系统管理13----------------------- */
	public static Integer MODULE_SYSTEM_MANAGE = 13;
	//权限管理
	public static Integer MODULE_AUTH_MANAGER = 1301;//具体权限配置在DefaultAuthorityConfig.xml中

	// 日志管理
	public static Integer MODULE_LOG_MANAGER = 1302;
	//日志查询
	public static Integer MODULE_SYSTEM_MANAGE_LOG_QUERY = 130201;

	// 数据文件管理
	public static Integer DATA_FILE_MANAGE = 1303;
	//正常数据文件
	public static Integer MODULE_DATA_MANAGE_NORMAL_DATA_FILE = 130301;
	//异常数据文件
	public static Integer MODULE_DATA_MANAGE_ABNORMAL_DATA_FILE = 130302;
	//	@DicItem
	//	public static Integer MODULE_DATA_MANAGE_FIN_DATA_FILE = 120103;
	//	@DicItem
	//	public static Integer MODULE_DATA_MANAGE_LINE_DATA_FILE_UPDATE = 120104;

	//交易明细查询
	public static Integer TRANS_DETAIL_QUERY = 1304;
	//正常交易明细查询
	public static Integer MODULE_DATA_MANAGE_NORMALTRAN_DATA = 130401;
	//异常交易明细查询
	public static Integer MODULE_DATA_MANAGE_ABNORMALNORMALTRAN_DATA = 130402;

	//数据管理
	public static Integer DATA_MANAGE = 1305;
	//线路对账
	public static Integer MODULE_SYSTEM_MANAGE_DATA_LINE_ACCOUNT_CHECK_FILE = 130501;
	//一卡通上传文件
	public static Integer MODULE_SYSTEM_MANAGE_DATA_YKT_UPLOAD_QUERY = 130502;
	//一卡通下载文件
	public static Integer MODULE_SYSTEM_MANAGE_DATA_YKT_DOWNLOAD_QUERY = 130503;

	/** -----------------------计划任务管理14----------------------- */
	public static Integer MODULE_TASK_MANAGE = 14;
	//计划任务
	public static Integer MODULE_TASK = 1401;
	// 任务列表显示/刷新
	public static Integer MODULE_TASK_LOAD = 140101;
	// 新建计划任务
	public static Integer MODULE_TASK_NEW = 140102;
	// 查看详细信息
	public static Integer MODULE_TASK_DETAIL = 140103;
	// 立即触发任务
	public static Integer MODULE_TASK_NEWNOW = 140104;
	// 移除任务
	public static Integer MODULE_TASK_DEL = 140105;
	// 启用任务
	public static Integer MODULE_TASK_START = 140106;
	// 停用任务
	public static Integer MODULE_TASK_STOP = 140107;
	/** -----------------------清算管理15----------------------- */
	public static Integer MODULE_CLEAR_MANAGE = 15;
	//一票通清算
	public static Integer MODULE_CLEAR_MANAGE_YPT = 1501;
	// 重做
	public static Integer MODULE_CLEAR_MANAGE_YPT_REDO = 150101;
	// 设置
	public static Integer MODULE_CLEAR_MANAGE_YPT_CONFIG = 150102;
	// 单步
	public static Integer MODULE_CLEAR_MANAGE_YPT_ONLY = 150103;
	// 多步
	public static Integer MODULE_CLEAR_MANAGE_YPT_MANY = 150104;

	//一卡通清算
	public static Integer MODULE_CLEAR_MANAGE_YKT = 1502;
	// 重做
	public static Integer MODULE_CLEAR_MANAGE_YKT_REDO = 150201;
	// 设置
	public static Integer MODULE_CLEAR_MANAGE_YKT_CONFIG = 150202;
	// 单步
	public static Integer MODULE_CLEAR_MANAGE_YKT_ONLY = 150203;
	// 多步
	public static Integer MODULE_CLEAR_MANAGE_YKT_MANY = 150204;

	//银联闪付清算
	public static Integer MODULE_CLEAR_MANAGE_BANK = 1503;
	// 重做
	public static Integer MODULE_CLEAR_MANAGE_BANK_REDO = 150301;
	// 设置
	public static Integer MODULE_CLEAR_MANAGE_BANK_CONFIG = 150302;
	// 单步
	public static Integer MODULE_CLEAR_MANAGE_BANK_ONLY = 150303;
	// 多步
	public static Integer MODULE_CLEAR_MANAGE_BANK_MANY = 150304;

	//二维码清算
	public static Integer MODULE_CLEAR_MANAGE_QR = 1504;
	// 重做
	public static Integer MODULE_CLEAR_MANAGE_QR_REDO = 150401;
	// 设置
	public static Integer MODULE_CLEAR_MANAGE_QR_CONFIG = 150402;
	// 单步
	public static Integer MODULE_CLEAR_MANAGE_QR_ONLY = 150403;
	// 多步
	public static Integer MODULE_CLEAR_MANAGE_QR_MANY = 150404;

	//清算调整
	public static Integer MODULE_CLEAR_MANAGE_ADJUST = 1510;
	//清算结果调整
	public static Integer MODULE_CLEAR_MANAGE_ADJUST_CLEAR = 151001;
	//可疑帐调整
	public static Integer MODULE_CLEAR_MANAGE_ADJUST_FISHY = 151002;
	//单边查询
	public static Integer MODULE_CLEAR_MANAGE_ADJUST_SINGLE_QUERY = 151003;
	//一卡通未确认交易明细
	public static Integer MODULE_DAILY_MANAGE_ADJUST_UNCONFIRMED = 151004;
	// 一卡通正常交易明细
	public static Integer MODULE_DAILY_MANAGE_ADJUST_NORMAL = 151005;
	// 调整及拒付交易明细
	public static Integer MODULE_DAILY_MANAGE_ADJUST_ADJUSTMENT = 151006;

	//综合查询
	public static Integer MODULE_CLEAR_MANAGE_INTEGRATED = 1511;
	//对账数据查询
	public static Integer MODULE_CLEAR_MANAGE_INTEGRATED_BILLCHECK_QUERY = 151101;
	//分账查询
	public static Integer MODULE_CLEAR_MANAGE_INTEGRATED_FARE_QUERY = 151102;
	//ACC服务费查询
	public static Integer MODULE_CLEAR_MANAGE_INTEGRATED_ACCFEE_QUERY = 151103;
	//票务服务费查询
	public static Integer MODULE_CLEAR_MANAGE_INTEGRATED_CLEARFEE_QUERY = 151104;
	//收益查询
	public static Integer MODULE_CLEAR_MANAGE_INTEGRATED_PROFIT_QUERY = 151105;

	/** -----------------------SAM卡管理16----------------------- */
	// SAM卡管理
	public static Integer MODULE_SAM_MNG = 16;

	// ACC-SAM卡管理
	public static Integer MODULE_SAM_MNG_MODULE = 1601;

	// SAM卡跟踪管理
	public static Integer MODULE_SAM_MNG_MODULE_TRACK = 160101;

	// SAM卡绑定设备管理
	public static Integer MODULE_SAM_MNG_MODULE_BIND = 160102;

	// SAM卡黑名单
	public static Integer MODULE_SAM_MNG_MODULE_BLACK = 160103;

	// 设备上报状态查询
	public static Integer MODULE_SAM_MNG_MODULE_INFO = 160104;

	/** -----------------------票务管理17----------------------- */
	//票务管理
	public static Integer MODULE_TICKET_MANAGE = 17;

	//车票挂失解挂管理
	public static Integer MODULE_TICKET_MANAGE_LOST = 1701;

	//车票挂失解挂
	public static Integer MODULE_TICKET_MANAGE_LOST_QUERY = 170101;

	//车票退票退款管理 
	public static Integer MODULE_TICKET_MANAGE_REFUND = 1702;

	//车票退票退款
	public static Integer MODULE_TICKET_MANAGE_REFUND_QUERY = 170201;

	//员工票管理 //
	public static Integer MODULE_TICKET_MANAGE_STAFF = 1703;

	//员工票申请
	public static Integer MODULE_TICKET_MANAGE_STAFF_QUERY = 170301;

	//车票跟踪与查询
	public static Integer MODULE_TICKET_MANAGE_TRACK = 1704;

	//车票账户查询
	public static Integer MODULE_TICKET_MANAGE_TRACK_USER = 170401;

	/** -----------------------清分工作台2：清分规则管理----------------------- */
	//清分规则管理 
	public static Integer MODULE_CLEAN_MANAGE = 50;

	/* 基本规则设置 */
	public static Integer MODULE_CLEAN_MANAGE_CONFIG = 5001;

	// 清分算法参数
	public static Integer MODULE_CLEAN_MANAGE_CONFIG_TRANSFERFACTOR = 500101;

	// 收益方信息
	public static Integer MODULE_CLEAN_MANAGE_CONFIG_TRAFFICKET = 500102;

	// 线路信息
	public static Integer MODULE_CLEAN_MANAGE_CONFIG_LINEINFO = 500103;

	// 车站信息
	public static Integer MODULE_CLEAN_MANAGE_CONFIG_STATIONINFO = 500104;

	// 路段信息
	public static Integer MODULE_CLEAN_MANAGE_CONFIG_SECTIONINFO = 500105;

	// 换乘路径信息
	public static Integer MODULE_CLEAN_MANAGE_CONFIG_PATHINFO = 500106;

	// 清分比例
	public static Integer MODULE_CLEAN_MANAGE_CONFIG_CLEARLIST = 500107;

	/* 清分规则管理 */
	public static Integer MODULE_CLEAN_MANAGE_CLEAN = 5002;
	// 清分规则版本管理
	public static Integer MODULE_CLEAN_MANAGE_CLEAN_QUERY = 500201;

	/** -----------------------ES工作台3：编码发卡管理----------------------- */
	// ES管理工作台
	public static Integer MODULE_ES_MANAGE = 70;

	/* ES制卡管理 */
	public static Integer MODULE_ES_MANAGE_MADECARD = 7001;

	// ES设备管理
	public static Integer MODULE_ES_MANAGE_MADECARD_DEVICE = 700101;

	// ES设备状态
	public static Integer MODULE_ES_MANAGE_MADECARD_DEVICESTATUS = 700102;

	// ES任务管理
	public static Integer MODULE_ES_MANAGE_MADECARD_TASKQUERY = 700103;

	// ES增加任务
	public static Integer MODULE_ES_MANAGE_MADECARD_ADDTASK = 700104;

	// 设备签到签退查询
	public static Integer MODULE_ES_MANAGE_MADECARD_DEVICE_LOGIN = 700105;

	// 操作员签到签退查询
	public static Integer MODULE_ES_MANAGE_MADECARD_DEVICE_USERLOGIN = 700106;

	// 制卡文件管理
	public static Integer MODULE_ES_MANAGE_MADECARD_FILE = 700107;

	// 制卡统计查询
	public static Integer MODULE_ES_MANAGE_MADECARD_QUERY = 700108;

	// ES设备参数发布
	public static Integer MODULE_ES_MANAGE_MADECARD_PUBLISH = 700109;

	//	// TP文件导入
	//	public static Integer MODULE_ES_MANAGE_MADECARD_TPIMPORT = 700110;

	/* ES个性化管理 */
	public static Integer MODULE_ES_MANAGE_INDIVIDUATION = 7002;

	// 个性化管理
	public static Integer MODULE_ES_MANAGE_INDIVIDUATION_QUERY = 700201;

	/* ES打印管理 */
	public static Integer MODULE_ES_MANAGE_PRINT = 7003;

	// 个性化打印管理
	public static Integer MODULE_ES_MANAGE_PRINT_PERSONAL = 700301;

	//打印模板管理
	public static Integer MODULE_ES_MANAGE_PRINT_TEMPLATE = 700302;

	/* ES交易数据查询 */
	public static Integer MODULE_ES_MANAGE_TRANS = 7004;

	//正常交易数据查询
	public static Integer MODULE_ES_MANAGE_TRANS_NORMAL = 700401;

	//异常交易数据查询
	public static Integer MODULE_ES_MANAGE_TRANS_ERROR = 700402;

}
