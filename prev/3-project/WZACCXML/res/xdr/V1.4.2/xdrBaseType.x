%/*************************************************************************************************************
%
%    温州市域铁路AFC系统线网技术标准
%
%    Title       : xdrBaseType.x
%   @Version     : 1.2.0
%    Author      : 华科峰
%    Date        : 2016/06/20
%    Description : 定义基础字段及系统常量
%**************************************************************************************************************/

%/*  修订历时记录:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    华科峰           *     1.2.0 基于温州市域铁路AFC系统线网技术标准V1.2生成文档
%*   2017/06/20    *    华科峰           *     修改FileHeaderTag_t
%*   2017/08/21    *    陈锦鲂           *     DeviceType_t中DT_BOM、DT_TVM、DT_ISM、DT_PCA、DT_ES类型分别修改为0x0A、0x0B、0x0C、0x0D、0x0E
%**************************************************************************************************************/

%#ifndef XDR_BASETYPE_H
%#define XDR_BASETYPE_H
%
%#if defined(WIN32)
%#include <winsock.h>
%#include "d32-rpc.h"
%#elif defined(VXWORKS)
%#include <rpc/rpc.h>
%#include "VxWorksCompliant.h"
%#else
%#include <rpc/rpc.h>
%#endif 
%
%#ifdef VXWORKS
%#define xdr_proc xdrproc_t
%#define WINAPI
%#endif 
%
%#ifdef __cplusplus
%extern "C" {
%#endif

%// -------------------------------------------------------------------------------------------------------------
%// ---------- 数据类型定义
%// -------------------------------------------------------------------------------------------------------------

typedef unsigned char       U8_t;

typedef unsigned short      U16_t;

typedef unsigned int        U32_t;

typedef char                S8_t;

typedef short               S16_t;

typedef int                 S32_t;

typedef S32_t               ValueCent_t;					/* 金融单位：分 (例如：1表示1分，10表示1角，100表示1元) */

struct ValueCent64_t {									                                    
	S32_t                  ValueCent64_LO;           
	S32_t                  ValueCent64_HI;
};                                                                        /* 金融单位：分 (例如：1表示1分，10表示1角，100表示1元) ，用来表达比较大的金额*/


typedef U32_t               MilliSecond_t;					/* 段时间单位：毫秒 */

typedef U32_t               Seconds_t;						/* 段时间单位：秒 */

typedef U32_t               SecondsSinceMidNight_t;			/* 相对时间单位：表示为据本地当日0点的描述，取值范围是0到86400,比如本地1:00AM可表示为3600；2:00AM可表示为7200*/

%// UTC,通用协调时间(UTC, Universal Time Coordinated)，表示格林威治时间，此时间的取值与时区无关。
%// 各个操作系统都应有类似相同的方式来获取此时间。
%// 0：表示格林威治1970年1月1日0分0秒  
%// 1：表示格林威治1970年1月1日0分1秒
%// 转换成本地日期时间时，必须考虑北京时区8个小时的时差在本系统中某任本地时间为北京时间，与操作系统的设置无关。 
%// Time_HI是预留的字段，对目前的系统设计作为预留字段使用 Time_LO即UTC时间
struct AFCTime64_t{                                     /* 通用协调时间 */                            
	S32_t                   Time_HI;
	S32_t                   Time_LO;
};    
%// 本地时间是指在格林威治时间的基础上，同时考虑地区时钟的差异。
%// 例如北京为UTC+8:00时区。在计算本地时间时，各个操作系统都是可以设置本地时差的，所以各操作系统也提供了如何获取本地时间的方法。
%// 但是由于ACC/AFC系统庞大，所涉及到的终端设备众多，为了强化系统时间同步，所有本地时间的获取需先取得UTC时间，
%// 再计算北京时区差异的值，自行计算本地时间。
%// 禁止直接获操作系统提供的本地时间。
%// 这样做的好处可以使得所有设备共享时钟服务器，获得相同的本地时间。
%// 而与本地时区设置的值无关。
%// 0：1970年1月1日
%// 1：1970年1月2日
typedef U16_t               Date2_t;						/* 日期 */

%// 此时间为本地时间，应用与票卡内。
%// 0：表示2000年1月1日0分0秒
%// 1：表示2000年1月1日0分1秒
typedef U32_t               TicketTime32_t;				/* 票卡内部时间 */


%// 布尔值
enum Boolean_t {	
	FalseFlag           = 0, 
	TrueFlag            = 1 
}; 

typedef opaque              UnicodeString_t<>;				/* 中文字符(Unicode字符) */

typedef U8_t                Percentage_t;			        /* 百分比(取值范围0--100，表示为0%到100%) */

%// 票卡物理编号,共8个字节
%// U64_t从高位到低位8个字节分别表示如下：
%// 1. 高位首字节: 芯片类型(ChipType_t)
%// 2. 如果票卡物理编号为7个字节，则：高位第二字节到最后一个字节（7个字节）表示为票卡高位到低位的7个字节。
%// 3. 如果票卡物理编号为4个字节，则：
%//    1）高位起第2、3、4字节到全部填写0x00.
%//    2）高位起第5、6、7、8字节到填写票卡高位到低位的4个字节。
%// 4、公交卡的票卡物理编号为CSN
struct TicketPhyID_t {										/* 票卡物理编号 */                                             
	U32_t                  TicketPhyID_LO;
	U32_t                  TicketPhyID_HI;
};



%// 终端机编号 共6个字节：前两个字节为城市代码(卡属地) 
struct TerminateNumber_t {									                                                
	U16_t					CityCode;								/* 城市代码 */          
	U32_t					SerialID;							/* 序列号 */      
};

typedef U32_t               TicketQuantity_t;				/* 票卡数量 */


typedef U32_t               RMBQuantity_t;				/* 纸币或硬币的张数 */

typedef U16_t               Minutes_t;					/* 段时间单位：分钟*/
                                                        
%// 文件校验码
struct MD5_t {										
	U32_t                   MD5Tag[4];
};


%// 票卡应用序列号
%// 共10个字节,分别为：国家标识(1字节)+建设部标识(1字节)+城市代码(2字节)+应用代码(2字节)+卡序列号(4字节)
struct TicketAppSerialID_t {									                                           
	U8_t					CountryTag;								/* 国家标识 */      
	U8_t					MOCTag;									/* 建设部标识 */      
	U16_t					CityCode;								/* 城市代码 */      
	U16_t					AppCode;								/* 应用代码 */      
	U32_t					TicketSerialID;							/* 卡序列号 */      
};

%// 交易类型定义
enum TransactionType_t {                    
%// 一票通部分 	
	TX_SJTSale                              = 0x01, /* 单程票发售 */
	TX_NonNamedCPUSale                      = 0x02, /* 非记名储值票发售 */
	TX_NamedCPUSale                         = 0x03, /* 记名储值票发售 */
	TX_CPUCardAddValue                      = 0x04, /* 储值票充值 */
	TX_SJTRefund                            = 0x05, /* 单程票即时退票 */
	TX_CPUCardImmediateRefund               = 0x06, /* 储值票即时退票 */
	TX_CPUCardNonImmediateRefund            = 0x07, /* 储值票非即时退票 */
	TX_TicketValidityPeriod                 = 0x08, /* 储值票延期 */
	TX_CPUCardBlock                         = 0x09, /* 储值票锁卡 */
	TX_CPUCardUnblock                       = 0x0A, /* 储值票解锁 */
	TX_TicketSurcharge                      = 0x0B, /* 一票通更新 */
	TX_TicketEntry                          = 0x0C, /* 一票通进站 */
	TX_TicketExit                           = 0x0D, /* 一票通出站*/	
	TX_CPUCardDeduction                     = 0x0E, /* 储值票扣款（预留） */
%// 一卡通部分
	TX_YKTSale                              = 0x20, /* 一卡通售卖（预留） */
	TX_YKTCardAddValue                      = 0x21, /* 一卡通充值（预留） */
	TX_YKTCardBlock                         = 0x22, /* 一卡通锁卡（预留） */
	TX_YKTCardUnblock                       = 0x23, /* 一卡通解锁（预留） */
	TX_YKTCardSurcharge                     = 0x24, /* 一卡通更新 */
	TX_YKTCardEntry                         = 0x25, /* 一卡通进站 */
	TX_YKTCardExit                          = 0x26, /* 一卡通出站*/	
	TX_YKTCardDeduction                     = 0x27, /* 一卡通扣款（预留） */
%//  手机卡部分
	TX_MobileSurcharge                      = 0x40, /* 手机卡更新 */
	TX_MobileEntry                          = 0x41, /* 手机卡进站 */
	TX_MobileExit                           = 0x42, /* 手机卡出站*/	
	TX_MobileDeduction                      = 0x43, /* 手机卡扣款 （预留） */
%// 银行卡部分
	TX_BankCardDeduction                    = 0x60, /* 银行卡扣款（预留） */
%// ES部分
	TX_SJTInit				= 0x80, /* 单程票初始化 */
	TX_CPUCardInit				= 0x81, /* 储值票初始化 */
	TX_TicketCancel				= 0x82, /* 票卡注销 */
	TX_CPUCardPersonal			= 0x83, /* 储值票个性化 */
	TX_PrePaySJTOffset			= 0x84  /* 预付值单程票抵销 */
};

%// EOD参数组件类型定义
enum EODComponentType_t {                       
	EOD_SYSTEM				= 0x21, /* EOD: 系统参数 */
	EOD_SYSNetwork			        = 0x22, /* EOD: 路网信息参数 */
	EOD_SYSCalendar                         = 0x23, /* EOD: 日历数据 */
	EOD_SYSFare                             = 0x24, /* EOD: 费率表参数 */
	EOD_SYSTicket                           = 0x25, /* EOD: 车票参数 */	
	EOD_Concession                          = 0x26, /* EOD: 优惠方案参数 */	
	EOD_AGM				        = 0x27, /* EOD: AGM设备参数 */
        EOD_RG				        = 0x28, /* EOD: RG通行控制参数 */
	EOD_BOM				        = 0x29, /* EOD: BOM设备参数 */
	EOD_TVM				        = 0x2A, /* EOD: TVM设备参数 */
        EOD_TVM_GUI                             = 0x2B, /* EOD: TVM界面参数 */
	EOD_ISM				        = 0x2C, /* EOD: ISM参数 */
	EOD_PCA				        = 0x2D, /* EOD: PCA参数 */
        EOD_SLESoft                             = 0x2E, /* EOD: 设备软件参数 */
        EOD_TPSoft                              = 0x2F  /* EOD: TP软件参数 */
};

%// 运营控制参数类型定义
enum RUNComponentType_t {                         
        RUN_BLKACCFULLLIST                      = 0x41, /* 一票通全量明细黑名单 */
	RUN_BLKACCINCRLIST                      = 0x42, /* 一票通增量明细黑名单 */
	RUN_BLKACCFULLSECTLIST                  = 0x43, /* 一票通全量号段黑名单 */	
	RUN_BLKYKTFULLLIST                      = 0x44, /* 一卡通全量明细黑名单（预留） */	
	RUN_BLKSTAFFFULLLIST                    = 0x45, /* 员工卡全量明细黑名单 */
	RUN_SJTRECYCLE                          = 0x46, /* 单程票回收条件 */
	RUN_WAIVERDATE                          = 0x47, /* 模式履历 */
	RUN_TICKETCATALOG                       = 0x48, /* 票卡库存目录 */
	RUN_STATIONEQUNODE                      = 0x49, /* 车站设备节点配置信息 */
	RUN_OPERINFO                            = 0x50, /* 操作员基本信息文件 */
	RUN_OPERRIGHT				= 0x51  /* 操作员权限文件 */
	
};

%// 文件类型码
enum FileHeaderTag_t {                          
	FILE_YPT_TRANSACTION                   = 0x01,  /* 一票通交易文件 */			
	FILE_YKT_TRANSACTION                   = 0x02,  /* 一卡通交易文件 */
	FILE_MOBILE_TRANSACTION                = 0x03,  /* 手机卡交易文件 */
	FILE_BANK_TRANSACTION                  = 0x04,  /* 银行卡交易文件 */
        FILE_AGM_AUDIT_REGISTER                = 0x05,  /* AGM审计文件 */
	FILE_BOM_AUDIT_REGISTER                = 0x06,  /* BOM审计文件 */
	FILE_TVM_AUDIT_REGISTER                = 0x07,  /* TVM审计文件 */
	FILE_ISM_AUDIT_REGISTER                = 0x08,  /* ISM审计文件 */
	FILE_PCA_AUDIT_REGISTER                = 0x09,  /* PCA审计文件 */
	FILE_ES_TRANSACTION		       = 0x0A,  /* ES交易文件 */
	FILE_ES_ENCODING		       = 0x0B,  /* ES编码报告文件 */

	FILE_EOD_CONTROL                       = 0x20,  /* EOD参数控制文件 */
	FILE_EOD_SYSTEM			       = 0x21,  /* EOD: 系统参数文件 */
	FILE_EOD_SYSNetwork		       = 0x22,  /* EOD: 网络拓扑参数文件 */
	FILE_EOD_SYSCalendar                   = 0x23,  /* EOD: 日历时间参数文件 */
	FILE_EOD_SYSFare                       = 0x24,  /* EOD: 费率表参数文件 */
	FILE_EOD_SYSTicket                     = 0x25,  /* EOD: 车票参数文件 */	
	FILE_EOD_Concession                    = 0x26,  /* EOD: 优惠方案参数文件 */	
	FILE_EOD_AGM			       = 0x27,  /* EOD: AGM设备参数文件 */
        FILE_EOD_RG                            = 0x28,  /* EOD: RG通行控制参数文件 */
	FILE_EOD_BOM			       = 0x29,  /* EOD: BOM设备参数文件 */
	FILE_EOD_TVM			       = 0x2A,  /* EOD: TVM设备参数文件 */
        FILE_EOD_TVM_GUI                       = 0x2B,  /* EOD: TVM界面参数文件 */
	FILE_EOD_ISM			       = 0x2C,  /* EOD: ISM参数文件 */
	FILE_EOD_PCA			       = 0x2D,  /* EOD: PCA参数文件 */
        FILE_EOD_SLESoft                       = 0x2E,  /* EOD: 设备软件程序参数文件 */
	FILE_EOD_TPSoft                        = 0x2F,  /* EOD: TP软件程序参数文件 */

	FILE_AFC_MAIN_CONTROL                  = 0x40,  /* AFC运营主控文件 */
	FILE_BLK_ACC_FULL_LIST                 = 0x41,  /* 一票通全量明细黑名单文件 */
	FILE_BLK_ACC_INCR_LIST                 = 0x42,  /* 一票通增量明细黑名单文件 */
	FILE_BLK_ACC_FULL_SECT                 = 0x43,  /* 一票通全量号段黑名单文件 */
	FILE_BLK_YKT_FULL_LIST                 = 0x44,  /* 一卡通全量明细黑名单文件（预留） */
	FILE_BLK_STAFF_FULL_LIST               = 0x45,  /* 员工卡全量明细黑名单文件 */
	FILE_SJT_RECYCLE                       = 0x46,  /* 单程票回收条件文件 */
	FILE_WAIVERDATE                        = 0x47,  /* 模式履历文件 */
	FILE_TICKET_CATALOG                    = 0x48,  /* 票卡库存目录文件 */
        FILE_STATION_EQUNODE_CONFIG            = 0x49,  /* 车站设备节点配置信息文件 */
        FILE_OPERINFO                          = 0x50,  /* 操作员基本信息文件 */
	FILE_OPERRIGHT			       = 0x51,  /* 操作员权限文件 */
	
	FILE_STOCK_INOUT_BILL                  = 0x60,  /* 出入库清单文件 */
	FILE_ENDOFDAY_STOCK_SANPSHOT           = 0x61,  /* 日终库存快照文件 */
	FILE_ENDOFDAY_STOCK_DIFF               = 0x62,  /* 日终库存差异文件 */
	FILE_TRANS_ACCOUNT                     = 0x63,  /* 交易对账文件 */
	FILE_DOUBTFULDEAL_RESULT               = 0x64,  /* 可疑交易处理文件 */
	FILE_COMPARISON_WITH_YKT               = 0x65,  /* 与市民卡对账文件 */
	FILE_COMPARISON_WITH_MOBILE            = 0x66,  /* 与移动对账文件 */
	FILE_COMPARISON_WITH_BANK              = 0x67   /* 与银联对账文件 */
};

%// 性别
enum Gender_t {                        
	GENDER_FEMALE                          =  0x01,  /* 女性 */
	GENDER_MANKIND                         =  0x02,  /* 男性 */
	GENDER_UNKNOWN                         =  0x03   /* 未知 */
};

%// 设备类型
enum DeviceType_t {                                 
	DT_ACC_SERVER                         =  0x01,  /* ACC服务器 */
        DT_SC_SERVER                          =  0x02,  /* SC服务器 */
        DT_MC_SERVER                          =  0x03,  /* MC服务器 */
	DT_ACC_WS                             =  0x04,  /* ACC工作站 */
        DT_SC_WS                              =  0x05,  /* SC工作站 */
        DT_MC_WS                              =  0x06,  /* MC工作站 */
        DT_ENG                                =  0x07,  /* 进站AGM */
        DT_EXG                                =  0x08,  /* 出站AGM */
	DT_RG                                 =  0x09,  /* 双向AGM */
	DT_BOM                                =  0x0A,  /* BOM */
	DT_TVM                                =  0x0B,  /* TVM */
	DT_ISM                                =  0x0C,  /* ISM */
	DT_PCA                                =  0x0D,  /* PCA */
	DT_ES                                 =  0x0E   /* ES*/
};


%// 日期类型
enum DateTypeID_t{                              
	DTI_WORKDAY                                = 0x01,  /* 正常工作日 */
	DTI_SATURDAY                               = 0x02,  /* 星期六 */
	DTI_SUNDAY                                 = 0x03,  /* 星期天 */
	DTI_SPECIALDAY                             = 0x04   /* 特殊日期 */
};


%// 芯片类型
enum ChipType_t{                       
	CT_ULTRALIGHT                               = 0x01,  /* Ultra Light卡 */
	CT_METROCPU                                 = 0x02,  /* 市域铁路发行的CPU卡 */
	CT_CITIZENYKT                               = 0x03,  /* 一卡通卡 */
	CT_MOBILE                                   = 0x04   /* 手机卡 */
};

%// 票种大类
enum TicketFamilyType_t{                          
	TF_SJT                                    = 0x01,  /* 单程票 */
	TF_EXIT                                   = 0x02,  /* 出站票 */
	TF_MILE                                   = 0x03,  /* 计程票 */
	TF_TIME                                   = 0x04,  /* 计时票 */
	TF_RIDE                                   = 0x05,  /* 计次票 */
	TF_STAFF                                  = 0x06,  /* 员工票 */
	TF_CITIZEN                                = 0x07,  /* 市民卡 */
	TF_MOBILE                                 = 0x08   /* 手机卡 */
};

%// 票卡有效期方式
enum DurationMode_t{                                  
	DM_NOTCHECK                               = 0x00,  /* 对票卡有效期不做检查 */
	DM_FIRSTUSERAFTER                         = 0x01,  /* 在票卡第一次使用后的一段时间内有效 */
	DM_STARTANDEND                            = 0x02,  /* 在票卡发售时设定的起止日期 */
	DM_SELLAFTER                              = 0x03,  /* 在票卡发售之日起一段时间内有效 */
	DM_LASTTIMEAFTER                          = 0x04   /* 在上次使用日期之后的一段时间内有效 */
};


%//优惠方式
enum ConcessionType_t                    
{
	CTYPE_NoConcession                           = 0,		/* 不优惠 */
	CTYPE_RepeatConcession                       = 1,		/* 重复优惠 */
	CTYPE_JoinConcession                         = 2,		/* 联乘优惠 */
	CTYPE_PileConcession                         = 3		/* 累积优惠 */
};


%// 报文类型
enum ComType_t{											
	COM_COMM_CONNECT_APPLY					= 0x1001,   /* 通讯链接申请 */
	COM_PING						= 0x1101,   /* 应用层PING */
	COM_TEXT						= 0x1102,   /* 文本消息 */
	COM_SLE_CONTROL_CMD					= 0x1103,   /* 设备控制命令 */
	COM_TIME_SYNC						= 0x1104,   /* 强制时钟同步命令 */

	COM_EVENT_UPLOAD					= 0x1201,   /* 设备事件上传 */
	COM_QUERY_EOD_VERSION					= 0x1202,   /* 查询下级参数版本 */
	COM_QUERY_FILE_VERSION				        = 0x1203,   /* 查询运行配置文件版本 */
	COM_QUERY_TRANS_SN					= 0x1205,   /* 查询设备交易流水号 */
	COM_UPLOAD_EOD_VERSION					= 0x1206,   /* 设备参数版本变化上传 */
	COM_UPLOAD_FILE_VERSION					= 0x1207,   /* 设备运行配置文件版本上传 */
	COM_UPLOAD_SAM						= 0x1208,   /* 设备SAM信息上传 */
	COM_UPLOAD_SN						= 0x1210,   /* 设备最新流水号上传 */
        COM_EndTime_SET                                          = 0x1211,   /* 设置运营结束时间 */
	
	COM_OPERATOR_WORK					= 0x1301,   /* 操作员登录、暂停、恢复和退出等操作 */
	COM_TICKETBOX_OPERATE					= 0x1303,   /* 票箱操作数据上传 */
	COM_BANKNOTEBOX_OPERATE					= 0x1304,   /* 纸币钱箱操作数据上传 */
	COM_COINBOX_OPERATE					= 0x1305,   /* 硬币钱箱操作数据上传 */
	COM_COINBOX_CLEAR					= 0x1306,   /* TVM硬币钱箱清空数据上传 */
        COM_NOTEBOX_CLEAR					= 0x1307,   /* 纸币钱箱清空数据上传 */
	COM_CASHBOX_QUERY					= 0x1308,   /* 查询设备钱箱数据 */
	COM_CASHBOX_UPLOAD					= 0x1309,   /* 设备钱箱数据上传 */

	COM_FTP_APPLY						= 0x1401,   /* FTP申请 */
	COM_FILE_UPDATE_NOTIFY					= 0x1402,   /* 文件更新通知 */
	COM_CURFILE_UPLOAD_NOTIFY				= 0x1403,   /* 当前文件上传通知 */
	COM_SPECFILE_UPLOAD_NOTIFY				= 0x1404,   /* 指定文件上传通知 */

	COM_SEND_EMERGENCY_MODE					= 0x1501,   /* 启动/解除紧急运营模式 */
	COM_SEND_OTHER_MODE					= 0x1502,   /* 发送其他运营模式命令 */
	COM_MODE_STATUS_UPLOAD					= 0x1503,   /* 模式状态上传 */
	COM_MODE_CHANGE_BROADCAST				= 0x1504,   /* 模式变更广播 */
	COM_MODE_QUERY						= 0x1505,   /* 车站模式查询 */


	COM_TICKET_ALLOC_APPLY					= 0x1601,   /* 票卡调拨申请 */
	COM_TICKET_ALLOC_ORDER					= 0x1602,   /* 票卡调拨命令 */
	COM_UPLOAD_TICKET_STOCK_NOTIFY			        = 0x1604,   /* 通知下级上传票卡库存信息 */
	COM_UPLOAD_TICKET_STOCK					= 0x1605,   /* 上传票卡库存信息 */

	COM_UPLOAD_TICKET_INCOME				= 0x1606,   /* 上传票款收益信息 */
	COM_UPLOAD_TICKET_FILLINGMONEY				= 0x1607,   /* 上传短款补款信息 */

	COM_BOM_ADDVALUE_AUTH					= 0x1701,   /* BOM在线充值认证 */
        COM_BOM_ADDVALUE_AFFIRM					= 0x1702,   /* BOM在线充值确认 */
	COM_PERSONAL_CARD_APPLY                                 = 0x1703,   /* 个性化卡申请 */
	COM_NONIMMEDIATE_REFUND_APPLY                           = 0x1704,   /* 非即时退款申请 */
	COM_NONIMMEDIATE_REFUND_QUERY                           = 0x1705,   /* 非即时退款查询 */
	COM_TICKET_LOST                                         = 0x1706,   /* 票卡挂失 */
	COM_TICKET_DISLOST					= 0x1707,   /* 票卡解挂 */
	COM_NONIMMEDIATE_REFUND_SEND			        = 0x1708,   /* 非即时退款信息下发 */
	COM_CARD_ACCOUNT_QUERY					= 0x1709,   /* 票卡帐户查询 */
	COM_CARD_ACCOUNT_SEND					= 0x1710,   /* 票卡账户信息下发 */

        COM_STATION_MAINTAIN_APPLY				= 0x1801,   /* 车站报修申请 */
	COM_STATION_MAINTAIN_AFFIRM				= 0x1802,   /* 车站维修结果确认 */

	COM_UPDATE_PASSWORD					= 0x1901,	/* 操作员密码更新 */

	COM_ES_LOGIN						= 0x1A01,	/* 设备签到请求 */
	COM_ES_LOGOUT						= 0x1A02,	/* 设备签退 */
	COM_OPERATOR_LOGIN					= 0x1A03,	/* 操作员签到请求 */
	COM_OPERATOR_LOGOUT					= 0x1A04,	/* 操作员签退 */
	COM_WORK_TASK						= 0x1A05,	/* 设备工作任务请求 */
	COM_TASK_REPORT						= 0x1A06,	/* 工作任务报告 */
	COM_STATUS_REPORT					= 0x1A07	/* 设备状态报告 */
};

%// 请求和应答标志
enum MessageType_t{									
	MT_REQMESSAGE                                           =0x00,		/*请求消息 */
	MT_ANSMESSAGE                                           =0x01		/*应答消息 */
};

%// ES工作任务类型
enum ESTaskType_t{									
	EST_SJT_INIT                                            =0x01,		/* 单程票初始化 */
	EST_SVT_INIT                                            =0x02,		/* 储值票初始化 */
	EST_TICKET_CANCEL                                       =0x03,	        /* 票卡注销 */
	EST_TICKET_ENCODING                                     =0x04,		/* 票卡分拣 */
	EST_TICKET_PERSONAL                                     =0x05,		/* 票卡个性化 */
	EST_SVT_OFFSET                                          =0x06,		/* 预付值单程票抵销 */
	EST_TASK_CANCEL                                         =0x07		/* 任务取消 */
};


%// SAM卡类型
enum SAMType_t{									
	ACC_ISAM  = 0x01,							/* 一票通充值SAM卡，目前系统不存在此种类型的卡，为预留 */
   	ACC_PSAM  = 0x02,							/* 一票通消费SAM卡 */
   	YKT_ISAM  = 0x03,							/* 一卡通充值SAM卡 */
   	YKT_PSAM  = 0x04,							/* 一卡通消费SAM卡(建设部密钥) */
   	ES_SAM    = 0x05,							/* 单程票编码用SAM卡 */
   	PBOC_MAIN = 0x06, 						        /* PBOC发卡母卡 */
   	PBOC_AUTH = 0x07, 						        /* PBOC发卡母卡认证卡 */
   	MOBILE_SAM = 0x08,							/* 手机SAM，预留 */
   	YKT_PSAM_WZ = 0x09							/* 一卡通消费SAM卡(市民卡密钥) */
};

%// 硬币面值(1-5角 2-1元) */
enum CoinFaceValue_t{                       
	FiveJiao_c                            = 0x01,  /* 5角 */
	OneYuan_c                             = 0x02   /* 1元 */

};

%// 纸币面值(1-1元 2-2元 3-5元 4-10元 5-20元 6-50元 7-100元) */
enum NoteFaceValue_t{                       
	OneYuan_n                             = 0x01,  /* 1元 */
	TwoYuan_n                             = 0x02,  /*2元 */
	FiveYuan_n                            = 0x03,  /*5元*/
	TenYuan_n                             = 0x04,  /*10元*/
	TwentyYuan_n                          = 0x05,  /*20元*/
	FiftyYuan_n                           = 0x06,  /*50元*/
	HundredYuan_n                         = 0x07  /*100元*/
};

%// 钱币面值*/
enum FaceValue_t{           
        FiveJiao                            = 0x01,  /* 5角 */
	OneYuan                             = 0x02,  /* 1元 */
	TwoYuan                             = 0x03,  /*2元 */
	FiveYuan                            = 0x04,  /*5元*/
	TenYuan                             = 0x05,  /*10元*/
	TwentyYuan                          = 0x06,  /*20元*/
	FiftyYuan                           = 0x07,  /*50元*/
	HundredYuan                         = 0x08  /*100元*/
};

typedef U8_t               TestFlag_t;						/* 测试标志位 (0: 非测试票；1：测试票)*/
typedef U8_t               TransferStationID_t;                                 /* 换乘区域代码 */
typedef U8_t               LineID_t;						/* 线路编号 */
typedef U16_t              StationID_t;						/* 车站编号 高字节U8表示LineID_t，低字节字节表示为本线路的车站编号*/
typedef U32_t              DeviceID_t;						/* 设备编号ID，4个字节，从高位低位分别表示,线路（U8_t）、车站（U8_t）、设备类型（U8_t）、设备在车站内的编号（U8_t） */
typedef U32_t              SAMID_t;						/* SAM卡编号，4个字节  U32_t从高位到低位分别表示SAMID[0]到SAMID[3] s*/
typedef U32_t              OperatorID_t;					/* 操作员编号 100000 - 999999 */       
typedef U32_t              BOMShiftID_t;					/* BOM班次号 */
typedef U8_t               TicketFamily_t;					/* 票种大类 */
typedef U8_t               TicketType_t;					/* 票种小类(逻辑类型)*/
typedef U32_t              SN_t;						/* 票卡卡计数器、设备交易序列号、文件序列号、联机交易序号 */
typedef U8_t               ModeCode_t;						/* 车站运营模式代码 */
typedef U8_t               ZoneID_t;						/* 区域编号 */
typedef U8_t               SectionID_t;						/* 区段编号 */
typedef U8_t               FareTier_t;						/* 里程等级 */
typedef U8_t               TicketFareTypeID_t;				        /* 费率类型代码 */
typedef U8_t               TimeIntervalID_t;				        /* 时间段编号 */
typedef U8_t               FareGroupID_t;					/* 车票费率组代码 */
typedef U16_t              CityCode_t;						/* 城市代码 */
typedef U16_t              IssuerCode_t;					/* 发卡方代码 */
typedef U16_t              RoleID_t;						/* 角色编号 */  
typedef U32_t              EQPFuncID_t;						/* 功能编号 */

typedef U32_t              Times_t;						/* 次数记录 */
typedef U8_t               AreaTicketFlag_t;				        /* 范围标志*/
typedef U32_t              BitMap_t;						/* 线路&车站位图 */
typedef U16_t              Duration_t;						/* 有效期(单位：天) */
typedef U32_t              MultiRideNumber_t;				        /* 计次票的乘车次数 */
typedef U8_t               DepreciationCyc_t;					/* 折旧计算单位周期 */
typedef U8_t               PaymentMode_t;					/* 支付方式 */
typedef U8_t               OverstayingMode_t;                                   /* 超时补款方式  0-线网最高票价 1-本站出站最高票价 2-固定值*/
typedef U8_t               InitBatchCode_t;					/* 票卡初始化批次 */
typedef U16_t              TicketCatalogID_t;				        /* 票卡目录编号 */ 
typedef U8_t               SoundDisplayID_t;				        /* AGM提示音 */
typedef U8_t               ConcessionalLampID_t;			        /* AGM灯光显示编号 */
typedef U8_t               PassageMode_t;                                       /* 通行模式 0-进站 1-出站 */
typedef U16_t              CoordinateUnit_t;				        /* 显示器坐标 */
typedef U8_t               BtnIndex_t;						/* 按钮编号 */
typedef U8_t               LineZoneSelBtnID_t;				        /* 线路区域选择按钮编号 */
typedef U8_t               TopBoardID_t;				        /* 顶板内容编号 */
typedef U8_t               ADItemID_t;						/* 广告文件编号 */
typedef U8_t               ADType_t;						/* 广告类型 */
typedef U8_t               PosType_t;						/* 位置类型 */

typedef U8_t               ConcessionID_t;					/* 优惠ID */
typedef U8_t               IndustryScope_t;					/* 联乘优惠行业范围 */
typedef U16_t              FrequencyWithinMin_t;			        /* 灯光闪动频率 */
typedef U8_t               LampColorTypeID_t;                                   /* 灯光颜色 */

typedef U8_t               JoinConcessionType_t;			        /* 联程优惠方式 */
typedef U8_t               PileConcessionType_t;			        /* 累积优惠方式 */
typedef U8_t               ConcessionResetType_t;		         	/* 优惠重置类型 */
typedef U16_t              RecordsInFile_t;					/* 文件中记录数 */
typedef U8_t               DeviceOwnerType_t;				        /* 使用设备类型 */

typedef U8_t               IncBlackType_t;					/* 黑名单增量类别 */
typedef U8_t               BLKActionType_t;					/* 黑名单处理编号(0–不拒绝 1–仅在出站时拒绝 2–在进站和出站时拒绝 3-仅在出站时拒绝，锁卡并上送交易 4-在进站和出站时拒绝，锁卡并上送交易) */
typedef U16_t              TicketDirClassID_t;				        /* 票卡目录分类编号 */
typedef U16_t              Angle_t;						/* 角度 */
typedef U32_t              IPAddress_t;						/* IP地址 */
typedef U16_t              TCPPort_t;						/* TCP端口 */
typedef U8_t               Accessleve_t;                                        /* 用户权限等级 */

typedef U32_t              FileVersionID_t;					/* 参数类文件版本号 */
typedef U8_t               EODVersionType_t;			        	/* 版本类型 */
typedef U8_t               VersionKind_t;					/* 版本性质（0－非读写器上版本 1－读写器上版本） */
typedef U8_t               SLEFileVersionID_t;					/* 设备文件版本号 */

typedef U8_t               TicketStatus_t;					/* 票卡状态(0－未初始化， 1－初始化，2－售卖，3－退票，4－黑名单，5－挂失) */
typedef U8_t               Paymentmeans_t;					/* 支付方式 */
typedef U8_t               LanguageFlag_t;					/* 车票语言标记 */ 
typedef U8_t               TransactionStatus_t;				        /* 交易状态 */

typedef U8_t               TicketACCStatus_t;	                                /* ACC票卡帐号状态 */
typedef U8_t               RefundStatus_t;	                                /* 退款处理状态 */
typedef U8_t               DealResult_t;	                                /* 处理结果 */
typedef U8_t               FailReason_t;	                                /* 失败原因 */

typedef U8_t               Faultlevel_t;	                                /* 故障等级  1-警告；2-降级运行；3-停止服务*/
typedef U8_t               MaintainResult_t;	                                /* 维修结果  1-未修；2-部分修复；3-修复 */
typedef U8_t               IdentificationType_t;		      	        /* 证件类型 */
typedef U8_t               PassengerTypeID_t;				        /* 持卡人类型标识 */
typedef U8_t               StaffFlag_t;						/* 持卡人职工标记 */
typedef U8_t               SIMType_t;						/* 手机SIM卡类型 */
typedef U8_t               SIMStatus_t;						/* 手机SIM卡状态 */
typedef U8_t               SurchargeArea_t;					/* 更新区域 */
typedef U8_t               SurchargeCode_t;					/* 更新方式 */
typedef U8_t               SJTRecycleFlag_t;				        /* 单程票回收标记 */
typedef U8_t               DeduceLocation_t;				        /* 扣费位置 */
typedef U8_t               RefundReason_t;					/* 退票原因 */
typedef U8_t               BlockReasonCode_t;				        /* 锁卡原因 */
typedef U8_t               UnblockReasonCode_t;				        /* 解锁原因 */
typedef U32_t              TAC_t;					        /* 交易验证码 */
typedef U8_t               DownLoadCode_t;					/* 更新下载类型 */

typedef U8_t               ARFileTag_t;						/* 审计方式 */
typedef U32_t              ARCount_t;						/* AR计数器 */
typedef U32_t              ContainerID_t;					/* 票箱ID */

typedef U16_t              EventCode_t;						/* 事件代码 */
typedef U8_t		   EventValue_t;					/* 事件值 */

typedef U8_t               ControlCmd_t;					/* 设备控制命令 */
typedef U8_t               DeviceStatus_t;					/* 设备状态 */
typedef U8_t               ParamVersionStatus_t;				/* 参数版本状态 */
typedef U8_t               VersionQueryType_t;					/* 版本查询类型 */


typedef U8_t		   EventType_t;						/* 事件类型 */
typedef U8_t		   SPCode_t;						/* 服务商编码 */
typedef U8_t	           OperateFlag_t;					/* 设备票箱、钱箱操作方向 */
typedef U32_t	           UploadFileBits_t;					/* 上传的文件位信息 */
typedef U8_t		   TicketAllocType_t;				        /* 票卡调拨类型 */
typedef U32_t              RandomNum_t;						/* 随机数 */
typedef U8_t		   KeyUse_t;						/* 密钥用途 */
typedef U8_t		   KeyVersion_t;					/* 密钥版本 */
typedef U32_t		   MAC_t;						/* MAC码 */
typedef U16_t		   ProtocolVer_t;					/* 报文协议字 */
typedef U8_t		   AlgorithmicType_t;					/* 压缩加密算法 */
typedef U16_t		   PackLength_t;					/* 报长度 */
typedef U8_t		   MACK_t;						/* 应答码 */

typedef U8_t		   FileDealResult_t;					/* 文件处理结果（可疑帐代码定义） */
typedef U8_t		   SettlementStatus_t;					/* 文件结算状态（0-0—合法但交易可疑 1－MD5签名错误 2－设备黑名单 3－SAM卡黑名单 4－不匹配的交易笔数 5－解码失败 6—未知异常） */
typedef U32_t              RecordsNum_t;					/* 记录数量 */

typedef U8_t               InOutType_t;						/* 出入库类型*/
typedef U8_t               InOutFlag_t;						/* 出入库标记（0－入库 1－出库）*/

typedef U8_t               CoinOperatePos_t;					/* 操作位置(1－找零器1 2－找零器2 3－循环找零器1 4－循环找零器2 5－硬币钱箱1 6－硬币钱箱2) */
typedef U8_t               NoteOperatePos_t;					/* 操作位置(1－纸币找零箱1 2－纸币找零箱2 3－废币箱 4－纸币钱箱 5-纸币循环找零箱) */

typedef U8_t               CoinBoxType_t;
typedef U8_t               NoteBoxType_t;
typedef U8_t               CashBoxType_t;
typedef U8_t               BillType_t;	                                        /* 单据类型 */
typedef U8_t               BillStatus_t;	                                /* 单据状态 */
typedef U8_t               FareType_t;	                                        /* 票款类型  1-车票 2-现金 */
typedef U8_t               ExcuteStatus_t;	                                /* 短款执行状态  0：未补款，1： 部分补款，2：完成补款 */
typedef U8_t               FillingMoneyType_t;                                  /* 补款类型 1：单次，2：多次 */
typedef U8_t               ErrorType_t;                                         /* 差错类型 1:售票员核对；2：TVM核对；3：ISM核对 */
typedef U8_t               RouterTag_t;						/* 路由标记(0－单接收方接收 1－本节点层接收 2－本节点和以下所有层接收 3－本节点及本节点上层节点)*/
typedef U8_t               AllowRightType_t;					/* 权限控制*/

typedef U32_t              TaskID_t;						/* 任务标识*/
typedef U8_t               TaskType_t;						/* 任务类型*/
typedef U16_t	           TradeCode_t;					        /* 行业代码 */
typedef U8_t               AppVersion_t;					/* 应用版本 */

typedef U8_t               OperatorProperty_t;					/* 操作员属性（1－编码员 2－维修人员 3－管理人员） */
typedef U8_t               TaskStatus_t;					/* 工作任务状态（0: 无任务,1:有任务, 2: 任务已发送） */
typedef U8_t               TaskChangeFlag_t;					/* 任务变更标志 */
typedef U8_t               ESWorkStatus_t;					/* ES工作状态 */

typedef U8_t               PatronQueue_t;					/* 乘客过闸最大滞留量 */
typedef S8_t               CardSeries_t;					/* 卡系(1：一票通，2：一卡通)*/

%// -------------------------------------------------------------------------------------------------------------

%#ifdef __cplusplus
%}
%#endif
%
%#endif	
%/********************************************** 文件结束 *******************************************************/