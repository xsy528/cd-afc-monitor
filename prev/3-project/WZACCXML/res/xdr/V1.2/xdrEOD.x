%/*************************************************************************************************************
%
%   温州市域铁路AFC系统线网技术标准
%
%   Title       : xdrEOD.x
%   @Version    : 1.2.0
%   @author     : 华科峰
%   @date       : 2016/06/20
%   Description : 定义系统的设备生成文件及参数文件格式
%**************************************************************************************************************/

%/*  修订历时记录:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    华科峰           *     1.2.0 基于温州市域铁路AFC系统线网技术标准V1.2生成文档                                      
%**************************************************************************************************************/

%#ifndef XDREOD_H
%#define XDREOD_H
%
%#include "xdrBaseType.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- EOD参数定义
%// -------------------------------------------------------------------------------------------------------------

%// 时钟同步结构
struct SysTimeSync_t{															
	Seconds_t                                TimeDeviationPeriod;                   /* 时间同步校准间隔 */
	Seconds_t                                MinTimeDeviation;                      /* 时钟同步最小阀值 */	
	Seconds_t                                WarnTimeDeviation;                     /* 时钟同步警告阀值 */
	Seconds_t                                MaxTimeDeviation;                      /* 时间同步最大阀值 */
};

%// 设备登录结构
struct DeviceLoginParam_t{                                                          
	Seconds_t                                LoginOvertime;                         /* 登录超时时间 */
	Times_t                                  MaxPINEntryRetries;                    /* 错误登录次数上限 */	
};


%// SC文件上报参数结构
struct SCUploadFile_t{	                                                  
        RecordsInFile_t                          SingleUDFileMaxDeals;                  /* SC交易文件包最大记录数量 */
	RecordsInFile_t                          SingleARFileMaxDeals;                  /* SC审计文件包最大记录数量 */
	Seconds_t                                IntervalOfBuildYPTUDFile;              /* SC交易文件打包上传时间间隔（一票通） */
        Seconds_t                                IntervalOfBuildYKTUDFile;              /* SC交易文件打包上传时间间隔（一卡通） */
	Seconds_t                                IntervalOfBuildARFile;                 /* SC审计文件打包上传时间间隔 */
	Seconds_t                                IntervalOfBuildStockFile;              /* SC出入库单文件上传时间间隔 */
};

%// ---- EOD系统参数
struct EOD_SystemParam_t {                                                        
	SysTimeSync_t                            SysTimeSync;                           /* 时间同步参数 */
	SCUploadFile_t                           SCUploadFile;                          /* SC文件上报参数 */
	DeviceLoginParam_t                       DeviceLoginParam;                      /* 设备登录参数 */
};


%// 线路结构
struct Line_t{                                                            
	LineID_t                                 LineID;                                /* 线路代码 */
	UnicodeString_t                          LineCNName;                            /* 线路中文名称 */
	string                                   LineENName<64>;                        /* 线路英文全称 */
	Boolean_t                                EnableFlag;                            /* 启用标志位 */
};

%// 车站结构
struct Station_t{                                                                  
	StationID_t                              StationID;                             /* 车站代码 */
	UnicodeString_t                          StationCNName;                         /* 车站中文名称 */
	string                                   StationENName<64>;                     /* 车站英文全称 */
	LineID_t                                 LineID;                                /* 车站所在线路编号 */
	Boolean_t                                EnableFlag;                            /* 启用标志位 */
};

%// 换乘车站结构
struct TransferStation_t {                                                                   
	TransferStationID_t                      TransferStationID;                     /* 换乘区域代码 */	
	UnicodeString_t                          TransferStationCNName;                 /* 换乘区域中文名称 */	
	string                                   TransferStationENName<64>;             /* 换乘区域英文简称 */
	StationID_t                              StationIDs<>;                          /* 换乘区域内车站列表 */
	Boolean_t                                EnableFlag;                            /* 启用标志位 */	
};

%// 路网信息参数
struct EOD_NetworkTopologyParam_t{                                              
	Line_t                                   Lines<>;                               /* 线路列表 */
	Station_t                                Stations<>;                            /* 车站列表 */
        TransferStation_t                        TransferStations<>;                    /* 换乘车站列表 */
};

%// 特殊日期结构
struct SpecialDate_t {                                                             
	Date2_t                                  SpecialDate;                           /* 特殊日期 */
	DateTypeID_t                             DateTypeID;                            /* 日期类型 */
};

%// 时间段结构 如0=(0,25200] 1=(25200,32400]
struct TimeInterval_t {                                                         
	TimeIntervalID_t                         TimeIntervalID;                        /* 时间段编号  */
	SecondsSinceMidNight_t                   TimeIntervalValue;                     /* 时间段结束时间 */
};

%// 最大滞留时间结构
struct FareTierTimeTable_t {                                                      
	FareTier_t                               FareTier;                              /* 费率等级代码 */
	Seconds_t                                AllowedTravelPeriod;                   /* 允许站内的逗留时间 */
};

%// ---- 日历时间参数类型
struct EOD_CalendarParam_t { 
        SecondsSinceMidNight_t                   RunStartofDay;                         /* 运营日切换时间, 建议是2:00 AM*/	                      
	SecondsSinceMidNight_t                   RunStartTime;                          /* 运营起始时间 */
	SecondsSinceMidNight_t                   RunEndTime;                            /* 运营结束时间 */
	SpecialDate_t                            SpecialDates<>;                        /* 特殊日期列表 */
	TimeInterval_t                           TimeIntervals<>;                       /* 时间段列表  */
	FareTierTimeTable_t                      FareTierTimeTables<>;                  /* 最大滞留时间列表 */	
};

%// 车票费率组类型
struct FareGroup_t {                                                            
	TicketType_t                             TicketType;                            /* 车票类型代码 */
	DateTypeID_t                             DateTypeID;                            /* 日期类型代码 */
	TimeIntervalID_t                         TimeIntervalID;                        /* 时间段代码 */
	FareGroupID_t                            FareGroupID;                           /* 车票费率组代码 */
};

%// 费率等级结构
struct FareTierMatrix_t{                                                           
	FareTier_t                               FareTier;                              /* 费率等级代码 */
	StationID_t                              StationIDIn;                            /* 进站车站编号 */
	StationID_t                              StationIDOut;                            /* 出站车站编号 */
};

%// 基本费率结构
struct BaseFare_t {                                                                 
	FareGroupID_t                            FareGroupID;                           /* 费率组代码 */
	FareTier_t                               FareTier;                              /* 费率等级代码 */
	ValueCent_t                              FareAmount;                            /* 费率值 */
};

%// 银行卡充值限制结构
struct BankAddingValuePara_t{                                                     
	ValueCent_t                              BankCardPaymentMinAmount;              /* 银行卡充值的最小金额 */
	ValueCent_t                              BankCardPaymentMaxAmount;              /* 银行卡充值的最大金额 */
};

%// ---- 费率表参数
struct EOD_FareParam_t {                                                            
	BankAddingValuePara_t                    BankAddingValuePara;                   /* 银行卡充值数据 */
	FareGroup_t                              FareGroups<>;                          /* 车票费率组定义列表 */
	FareTierMatrix_t                         FareTierMatrixDatas<>;                 /* 费率等级定义列表 */
	BaseFare_t                               BaseFares<>;                           /* 基本费率定义列表 */	
};

%// 芯片类型结构
struct ChipDefinition_t {                                                         
	ChipType_t                               ChipType;                              /* 芯片类型代码 */
	Duration_t                               PhycialValidityPeriod;                 /* 默认芯片物理有效期长度(天) */
	Times_t	                                 MaxChipIONumber;                       /* RFU 默认芯片最大交易次数*/
};

%// 车票发售定义结构
struct TicketDefinition_t{                                                          
	TicketType_t                             TicketType;                            /* 车票类型代码 */
	UnicodeString_t                          TicketTypeCNName;                      /* 车票类型中文名称 */
	string                                    TicketTypeENName<64>;                  /* 车票类型英文简称 */	
	TicketFamilyType_t                       TicketFamilyType;                      /* 票种大类类型代码 */
	AreaTicketFlag_t                         AreaTicketFlag;                        /* 车票使用范围限制标志(0: 无使用范围限制 1: 在限定的范围使用  */
	ValueCent_t                              SaleFixedPrice;                        /* 固定发售价格 */ 
	Boolean_t                                SouvenirFlag;                          /* 纪念票标志(0: 非纪念票; 1: 纪念票) */
	Boolean_t                                MemberTicketFlag;                      /* 记名票标志位(0: 非记名票; 1: 记名票) */
	SoundDisplayID_t                         SoundDisplayID;                        /* AGM提示音编号 */
	ConcessionalLampID_t                     ConcessionalLampID;                    /* AGM显示灯颜色编号（闪动频率和时常设备由本地控制） */	
	Boolean_t                                AddValueAuthorized;                    /* 是否可充值(0-否，1-是) */	
	ValueCent_t                              MaxAddValue;                           /* 充值最大金额，自动充值上限 */
	ValueCent_t                              DefaultAddValue;                       /* 充值起充金额 */
	ValueCent_t                              MinAddValue;                           /* 充值步长金额 */
	ValueCent_t                              MaxRemainingValue;                     /* 储值车票最大余额 */
	ValueCent_t                              MinEntryAmount;                        /* 最小进站金额 */
	ValueCent_t                              MinExitAmount;                         /* 最小出站金额（预留）*/
	ValueCent_t                              DepositValue;                          /* 票卡售卖押金 */
	ValueCent_t                              DepreciationValue;                     /* 折旧计算参数,固定为每周期扣除的折旧费 */
	DepreciationCyc_t                        DepreciationCyc;                       /* 折旧计算单位周期  0-按天 1-按月 2按年 */ 
	Boolean_t                                RefundAuthorized;                      /* 退票方式 0：不允许退票，1：只允许售票站退票 2-允许退票 */
	ValueCent_t                              MaxImmediateRefundAmount;              /* 最大即时退票金额 */
	ValueCent_t                              RefundCharge;                          /* 退票手续费 */
	MultiRideNumber_t                        MaxRideNumber;                         /* 最多乘坐次数(限制计时票) */
	MultiRideNumber_t                        DayMaxRideNumber;                      /* 单日最多乘坐次数(限制计时票) */
	DurationMode_t                           DurationMode;                          /* 车票有效期类别 */
	Duration_t                               Duration;                              /* 期限时间段(天) */
	Date2_t                                  FixedEndDate;                          /* 固定截至有效日期 */
        Boolean_t                                ExtendAuthorized;                      /* 是否允许延期 (0：否，1：是) */
        Duration_t                               DefaultExtendDays;                     /* 默认延期天数 */
        Boolean_t                                PullInAuthorized;                      /* 是否只允许售票站进站 (0：否，1：是) */
        Seconds_t                                PeriodofFreeAdjustForNoExit;           /* 重复进站免费更新最大时限 */
	ValueCent_t                              OverstayingFeeValue;                   /* 超时补款金额 */
        OverstayingMode_t                        OverstayingMode;                       /* 超时补款方式 */
	ValueCent_t                              NoExitFeeValue;                        /* 进出站不匹配补款（进站端） */
	ValueCent_t                              SurchargeStationMisMatch;              /* 非补款车站出站时再次补款金额（出站端） */	
	ValueCent_t                              AGMSoundMinBalance;                    /* 提示最小余额 */
	ValueCent_t                              SaleInitAmount;                    	/* 售卖时初始金额 */
	MultiRideNumber_t                        SaleInitRideNumber;                    /* 售卖时初始次数 */
	ValueCent_t                              EveryTimeAmount;                    	/* 单次折算金额 */
	ConcessionType_t                         ConcessionType;                        /* 储值票优惠方式 */
	ConcessionID_t                           JoinConcessionID;                      /* 联程优惠代码 */
	ConcessionID_t                           PileConcessionID;                      /* 累积优惠代码 */
	Boolean_t                                EnableFlag;                            /* 启用标志位, 所有的设备都要检查此标志位(0: 未启用；1:启用) */	
};

%// 票类型映射结构(包括一卡通、移动手机卡）
struct TicketTypeMap_t {                                                     
	TicketType_t                             TicketType;                            /* 地铁车票类型代码 */                         
	U8_t                                     YKTTicketType;                         /* 外部票种类型代码 */  
	CityCode_t	                         CityCode;                              /* 城市代码 */
        IssuerCode_t                             IssuerCode;                            /* 发卡方代码 */
	ChipType_t                               ChipType;                              /* 芯片类型 */              
};

%// ---- 车票参数
struct EOD_TicketParam_t{	                                                      
	ChipDefinition_t                         ChipDefinitions<>;                     /* 芯片列表 */
	TicketDefinition_t                       TicketDefinitions<>;                   /* 车票发售表 */
	TicketTypeMap_t                          TicketTypeMaps<>;                      /* 票类型映射表 */	
};

%// 联程优惠结构
struct JoinConcessionDefinition_t{	                                           
        ConcessionID_t                           JoinConcessionID;                      /* 联程优惠代码 */
	IndustryScope_t                          JoinConcessionIndustryScope;           /* 联程优惠换乘次数 */	
	Minutes_t                                JoinConcessionValidTime;               /* 联程优惠有效时间范围(单位：分钟) */	
	JoinConcessionType_t                     JoinConcessionType;                    /* 联程优惠方式(0：金额，1：比例) */	
	ValueCent_t                              JoinConcessionAmount;                  /* 联程优惠金额 */	
	Percentage_t                             JoinConcessionPercentage;              /* 联程优惠比例 */	
};

%// 累积优惠结构
struct PileConcessionDefinition_t{	                                               
	ConcessionID_t                           PileConcessionID;                      /* 累积优惠代码 */
	PileConcessionType_t                     PileConcessionType;                    /* 累积优惠方式(0：累积次数优惠，1：累积金额优惠) */	
	Times_t                                  PileConcessionTime;                    /* 累积乘坐起始优惠次数 */
	ValueCent_t                              PileConcessionAmount;                  /* 累积乘坐起始优惠金额 */	
	Percentage_t                             JoinConcessionPercentage;              /* 累积乘坐优惠比例 */	
	ConcessionResetType_t                    ConcessionResetType;                   /* 优惠重置类型(0－按月重置 1－按季重置 2－按年重置 3－不重置) */
};

%// ---- 优惠方案参数
struct EOD_ConcessionParam_t{	                                                  
	JoinConcessionDefinition_t               JoinConcessionDefinitions<>;           /* 联程优惠列表 */
	PileConcessionDefinition_t               PileConcessionDefinitions<>;           /* 累积优惠列表 */	
};

%// 闸机控制结构
struct AGMControlDefinition_t{	                                                    
	RecordsInFile_t                          SingleFileMaxDeals;                    /* 单个文件最大交易数量 */
	Seconds_t                                MaxIntervalToRejectSameTicket;         /* 拒绝同张票卡重复被寻卡最大间隔时间 */	
	Seconds_t                                MaxDelayAfterRejectInvalidTicket;      /* 拒绝非法票卡后的延迟时间 */	
	Percentage_t                             MaxWarnOfTicketBox;                    /* 票箱将满警戒线容量 */
%//	TicketQuantity_t                         MaxWarnOfInvalidTicketBox;             /* 废票箱将满警戒线容量 */
	PatronQueue_t                            PatronQueue;                           /* 乘客过闸最大滞留量 */
	Seconds_t                                MaxDelayOfShowPicture;                 /* 图像显示滞留时间 */
	Boolean_t                                ReaderWorkInGateInbreak;               /* 闸机入侵状态时读卡器启动标志(0：停止，1：继续) */
	Seconds_t                                MaxWaitAfterReadCard;                  /* 乘客刷卡后闸机最大等待时间 */
	Seconds_t                                MaxIntervalOfShutGate;                 /* 乘客连续通过闸机时，扇门保持打开状态的间隔时间 */
	Seconds_t                                MaxStayInGate;                         /* 乘客通道内最大滞留时间 */
};

%// 文件生成时间间隔结构
struct IntervalOfMakeFile_t{	                                                  
	Seconds_t                                IntervalOfBuildYPTUDFile;               /* 一票通交易数据文件生成时间间隔 */
	Seconds_t                                IntervalOfBuildYKTUDFile;               /* 一卡通交易数据文件生成时间间隔 */
	Seconds_t                                IntervalOfBuildARFile;                  /* 审计数据文件生成时间间隔 */
};

%// AGM显示灯结构
struct ConcessionalLampType_t{                                                    
	ConcessionalLampID_t                    ConcessionalLampID;                     /* 灯光显示编号 */
	LampColorTypeID_t                       LampColorTypeID;                        /* 灯光颜色类型 */
	FrequencyWithinMin_t                    Frequency;                              /* 灯光闪动频率,每分钟次数 */
	Seconds_t                               DisplayPeriod;                          /* 灯光显示总时长 */
};

%// AGM语音提示结构
struct SoundDisplay_t {                                                           
	SoundDisplayID_t                         SoundDisplayID;                        /* AGM提示音编号 */	
	string                                   SoundDisplayFileName<32>;              /* AGM提示音文件名 */
	MD5_t                                    SoundDisplayFileMD5;                   /* AGM提示音文件整个文件二进制内容的MD5码 */
};

%// ---- AGM设备参数
struct EOD_AGMParam_t {                                                   
	AGMControlDefinition_t                   AGMControlDefinition;                  /* AGM控制参数 */
	IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* AGM文件生成时间间隔 */
	ConcessionalLampType_t                   ConcessionalLampTypes<>;               /* AGM显示灯列表 */
	SoundDisplay_t                           SoundDisplays<>;	                /* AGM语音提示列表 */
};

%// 通行模式结构
struct PassageModeInfo_t {                                                           
	PassageMode_t                            PassageMode;                            /* 通行模式 */	
	SecondsSinceMidNight_t                   BeginTime;                              /* 开始时间 */
	SecondsSinceMidNight_t                   EndTime;                                /* 结束时间 */
};

%// ---- RG通行控制参数
struct EOD_RGParam_t {                                                   
	DeviceID_t				 DeviceID;                              /* 设备节点编号 */
	PassageModeInfo_t                        PassageModeInfos<>;	                /* 通行模式信息列表 */
};

%// BOM控制结构
struct BOMControlDefinition_t{	                                                 
	RecordsInFile_t                          SingleFileMaxDeals;                    /* 单个文件最大交易数量 */
%//	Duration_t                               MaxDaysOfSaveDeals;                    /* 交易本地保存有效天数 */
	Seconds_t                                MaxExitNoOperation;                    /* 最近无操作自动退出最长时间 */	
%//	Seconds_t                                MaxEnterOfFreeUpdate;                  /* 重复进站免费更新最大允许时间 */	
	Percentage_t                         MinWarnAmountOfTicketBox;                  /* 票箱将空警戒线容量 */
};

%// 票价选择按钮结构（BOM）
struct ExpressBtnForBOM_t {                                                            
	BtnIndex_t				 BtnIndex;				/* 购票金额按钮ID */
	ValueCent_t                              BuyBtnValue;                           /* 购票金额按钮显示金额 */
	FareTier_t				 FareTier;				/* 购票金额按钮对应费率等级 */
};

%// ---- BOM设备参数
struct EOD_BOMParam_t{                                                            
	BOMControlDefinition_t                   BOMControlDefinition;                  /* BOM控制参数 */
        IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* 文件生成时间间隔 */
	ExpressBtnForBOM_t                     ExpressBtnForBOM<>;                    /* 票价选择按钮结构（BOM）*/
};

%// TVM控制参数结构
struct TVMControlDefinition_t{	                                           
	RecordsInFile_t                          SingleFileMaxDeals;                    /* 单个文件最大交易数量 */
	TicketQuantity_t     	                 MaxAmountOfBuyTicket;                  /* 单次购票最大数量 */
	Seconds_t                                MaxIdleToCancelDeal;                   /* 在限定的时间内无任何后续操作，该项交易将被取消，已投入的钱币将返还乘客 */
	ValueCent_t                              ValidCoinTypes<>;                      /* 允许使用硬币类型（面值） */
	ValueCent_t                              ValidBillTypes<>;                      /* 允许使用纸币类型（面值） */
	RMBQuantity_t                            MaxAmountOfCoinChange;                 /* 找零允许最大硬币个数 */
	RMBQuantity_t                            MaxAmountOfNoteChange;                 /* 找零允许最大纸币张数 */
	ValueCent_t                              MaxMoneyOfChangeMoney;                 /* 找零允许最大金额 */
	RMBQuantity_t                            AlmostFullLimitOfCallbackCoinBox;      /* 硬币回收箱将满报警值 */
	RMBQuantity_t                            FullLimitOfCallbackCoinBox;            /* 硬币回收箱满报警值 */
	RMBQuantity_t                            AlmostEmptyLimitOfReturnCoinBox;       /* 硬币找零箱将空报警值 */
	RMBQuantity_t                            EmptyLimitOfReturnCoinBox;             /* 硬币找零箱空报警值 */
	RMBQuantity_t                            AlmostFullLimitOfCallbackBillBox;      /* 纸币回收箱将满报警值 */
	RMBQuantity_t                            FullLimitOfCallbackBillBox;            /* 纸币回收箱满报警值 */
	RMBQuantity_t                            AlmostEmptyLimitOfReturnBillBox;       /* 纸币找零箱将空报警值 */
	RMBQuantity_t                            EmptyLimitOfReturnBillBox;             /* 纸币找零箱空报警值 */
	Percentage_t                             MinWarnOfTicketBox;                    /* 票箱最低警戒线容量 */
	Percentage_t                             MaxWarnOfInvalidTicketBox;             /* 废票箱最大警戒线容量 */
};

%// 屏幕尺寸结构
struct ScreenSize_t {                                                             
    CoordinateUnit_t                         ScreenWidth;                           /* 屏幕宽度 */
    CoordinateUnit_t                         ScreenHeight;                          /* 屏幕高度 */
};

%// 资源文件结构
struct ResourceFile_t {                                                            	
    string                               PictureFileName<32>;                   /* 图片文件名 */
    MD5_t                                PictureFileMD5;                        /* 图片文件的MD5码 */
};  

%// 显示窗口（按钮）结构
struct DisplayWindow_t {                                                         
    CoordinateUnit_t                         DisplayPictureX;                       /* 画面左上角X坐标 */
    CoordinateUnit_t                         DisplayPictureY;                       /* 画面左上角Y坐标 */
    CoordinateUnit_t                         DisplayPictureWidth;                   /* 画面显示区域（按钮）宽度 */
    CoordinateUnit_t                         DisplayPictureHeight;                  /* 画面显示区域（按钮）高度 */
};

%// 广告结构
struct ADItem_t {                                                                 
	ADItemID_t                               ADItemID;                              /* 广告编号ID */
	ADType_t				 ADType;                                /* 广告类型 */	
	string                                   ADItemFileName<32>;                    /* 广告多媒体播放文件名称 */
	MD5_t                                    ADItemFileMD5;                         /* 广告多媒体播放文件整个文件二进制内容的MD5码 */
};

%// 顶板图片结构
struct TopBoard_t {                                                               
	TopBoardID_t                             TopBoardID;                            /* 顶板内容编号ID */
	UnicodeString_t                          CNDisplay;                             /* 顶板显示内容（中文） */
	string                                   ENDisplay<32>;                         /* 顶板显示内容（英文） */
};

%// 票价选择按钮结构
struct ExpressBtn_t {                                                            
	BtnIndex_t				 BtnIndex;				/* 购票金额按钮ID */
	ValueCent_t                              BuyBtnValue;                           /* 购票金额按钮显示金额 */
	FareTier_t                               FareTier;				/* 购票金额按钮对应费率等级 */
	DisplayWindow_t                          BuyBtnDisplay;                         /* 购票金额按钮显示坐标 */
};

%// 张数选择按钮结构
struct NumbersBtn_t {                                                            
	BtnIndex_t				 BtnIndex;				 /* 张数按钮ID */
	TicketQuantity_t                         TicketQuantity;                         /* 张数按钮显示张数 */
	DisplayWindow_t                          NumbersBtnDisplay;                      /* 张数按钮显示坐标 */
};


%// 路网区域中车站按钮结构
struct StationBtnInLineZone_t {                                                 
	StationID_t                              StationID;                             /* 车站选择按钮ID */
        DisplayWindow_t                          StationInLineZoneDisplay;              /* 车站选择区域坐标信息 */
	Boolean_t                                StationSelBtnEnable;                   /* 车站选择按钮启用标志 */
};

%// 线路按钮结构
struct LineBtn_t {                                                               
	LineID_t                                 LineID;                                /* 线路选择按钮ID */  
	DisplayWindow_t                          LineBtnDisplay;                        /* 线路选择按钮坐标信息 */
	Boolean_t                                LineBtnEnableFlag;                     /* 线路选择按钮启用标志 0不启用，1启用 */	
	ResourceFile_t                           CNLinePicture;                         /* 线路中文图片 */
	ResourceFile_t                           ENLinePicture;                         /* 线路英文图片 */
        DisplayWindow_t                          LineDisplay;                           /* 线路坐标信息 */
};

%// 线路中车站按钮结构
struct StationBtnInLine_t {                                                       
	StationID_t                              StationID;                             /* 车站选择按钮ID */
	LineID_t                                 LineID;                                /* 线路选择按钮ID */ 
        DisplayWindow_t                          StationInLineDisplay;                  /* 车站选择区域坐标信息 */
	Boolean_t                                StationSelBtnEnable;                   /* 车站选择按钮启用标志(0不启用，1启用) */
};

%// 支付界面相关显示位置结构
struct PayInterfacePosition_t {                                                       
	PosType_t				 PosType;                               /* 位置类型 */
        DisplayWindow_t                          PosDisplay;                            /* 位置坐标信息 */
};

%// ---- TVM设备参数
struct EOD_TVMParam_t{                                                           
	TVMControlDefinition_t                   TVMControlDefinition;                  /* TVM的控制参数 */
	IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* 文件生成时间间隔 */
};

%// 充值选择按钮结构
struct AddValueBtn_t {                                                            
	BtnIndex_t			         AddValueBtnIndex;                      /* 充值金额按钮ID */
	ValueCent_t                              AddValueBtnValue;                      /* 充值金额按钮显示金额 */
	DisplayWindow_t                          AddValueBtnDisplay;                    /* 充值金额按钮显示坐标 */
};

%//  TVM界面参数 是否需要添加路网图片缩小后高度和宽度   hkf 2016-6-22
struct EOD_TVM_GUIParam_t{                                                           
	ScreenSize_t                             ScreenSize;                            /* 屏幕尺寸 */
	ResourceFile_t                           StartupDisplayPicture;                 /* 启动窗口图片文件 */
	DisplayWindow_t                          StartupDisplayWindow;                  /* 启动窗口图片显示坐标 */
	ResourceFile_t                           PauseDisplayPicture;                   /* 暂停服务窗口图片文件 */
	DisplayWindow_t                          PauseDisplayWindow;                    /* 暂停服务窗口图片显示坐标 */
	ResourceFile_t                           CNLineZonePicture;                     /* 路网中文图片文件 */
	ResourceFile_t                           ENLineZonePicture;                     /* 路网英文图片文件 */
	DisplayWindow_t                          LineZoneDisplayWindow;                 /* 路网图片显示坐标 */
  	DisplayWindow_t                          LanguageDisplayWindow;                 /* 中英文按钮显示坐标 */
	DisplayWindow_t                          BackDisplayWindow;			/* 返回按钮显示坐标 */
	DisplayWindow_t                          AddValueDisplayWindow;                 /* 充值按钮显示坐标 */
	AddValueBtn_t                            AddValueBtns<>;                        /* 充值选择按钮列表 */
	DisplayWindow_t                          CheckCardDisplayWindow;		/* 验卡按钮显示坐标 */
	DisplayWindow_t                          NowtimeDisplayWindow;                  /* 当前日期时间显示坐标 */
	DisplayWindow_t                          NowStationDisplayWindow;               /* 当前车站显示坐标 */
	DisplayWindow_t                          RunningStatusDisplayWindow;		/* 运行状态显示坐标 */
	ADItem_t                                 ADItems<>;                             /* 广告列表（预留） */
	Boolean_t                                ADPlayType;                            /* 广告播放方式 0从头开始，1继续播放（预留） */	
	Seconds_t                                MaxIdleToPlayAD;                       /* 进入广告模式的最大空闲时间（预留） */	
	TopBoard_t                               TopBoards<>;                           /* 顶板列表 */	
	ExpressBtn_t                             FareSelBtns<>;                         /* 票价选择按钮列表 */
	NumbersBtn_t                             NumbersSelBtns<>;                      /* 张数选择按钮列表 */
	StationBtnInLineZone_t                   StationBtnInLineZones<>;               /* 路网区域中车站按钮列表 */
	LineBtn_t                                LineBtns<>;                            /* 线路选择按钮列表 */
	StationBtnInLine_t                       StationBtnInLines<>;                   /* 线路中车站选择按钮列表 */
	ResourceFile_t                           CNPayInterfacePicture;                 /* 支付界面中文图片文件 */
	ResourceFile_t                           ENPayInterfacePicture;                 /* 支付界面英文图片文件 */
	DisplayWindow_t                          PayInterfaceDisplayWindow;             /* 支付界面显示坐标 */
	PayInterfacePosition_t			 PayInterfacePositions<>;		/* 支付界面相关位置显示坐标 */
};

%// ISM控制参数结构
struct ISMControlDefinition_t{	                                                
	RecordsInFile_t                          SingleFileMaxDeals;                    /* 单个文件最大交易数量 */
	Seconds_t                                MaxIdleToCancelDeal;                   /* 等待操作最长时间，超出时间则取消操作 */
%//	ValueCent_t                              ValidBillTypes<>;                      /* 允许使用纸币类型（面值） */
%//	Paymentmeans_t                           Paymentmeans;                          /* 可支持的支付方式1：现金，2：银行卡，3：现金和银行卡 */
	RMBQuantity_t                            AlmostFullLimitOfBillBox;              /* 纸币钱箱将满报警值 */
	RMBQuantity_t                            FullLimitOfBillBox;                    /* 纸币钱箱满报警值 */
	ValueCent_t                              MaxMoneyOfCashAddValue;                /* 现金单次充值最大金额 */
%//	ValueCent_t                              MaxMoneyOfBankCardAddValue;            /* 银行卡单次充值最大金额 */	
};


%// ---- ISM设备参数
struct EOD_ISMParam_t{                                                        
	ISMControlDefinition_t                   TSMControlDefinition;                  /* TSM的控制参数 */
	IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* 文件生成时间间隔 */
	ScreenSize_t                             ScreenSize;                            /* 屏幕尺寸 */
	ResourceFile_t                           StartupDisplayPicture;                 /* 启动窗口图片文件 */
	DisplayWindow_t                          StartupDisplayWindow;                  /* 启动窗口图片显示坐标 */
	ResourceFile_t                           PauseDisplayPicture;                   /* 暂停服务窗口图片文件 */
	DisplayWindow_t                          PauseDisplayWindow;                    /* 暂停服务窗口图片显示坐标 */
	DisplayWindow_t                          NowtimeDisplayWindow;                  /* 当前日期时间显示坐标 */
	DisplayWindow_t                          NowStationDisplayWindow;               /* 当前车站显示坐标 */
	DisplayWindow_t                          RunningStatusDisplayWindow;		/* 运行状态显示坐标 */
	ADItem_t                                 ADItems<>;                             /* 广告列表（预留） */
	Boolean_t                                ADPlayType;                            /* 广告播放方式 0从头开始，1继续播放（预留） */	
	Seconds_t                                MaxIdleToPlayAD;                       /* 进入广告模式的最大空闲时间（预留） */	
	TopBoard_t                               TopBoards<>;                           /* 顶板列表 */	
        DisplayWindow_t                          AddValueDisplayWindow;                 /* 充值按钮显示坐标 */
	AddValueBtn_t                            AddValueBtns<>;                        /* 充值选择按钮列表 */
};


%// PCA控制参数结构
struct PCAControlDefinition_t{	                                             
	RecordsInFile_t                          SingleFileMaxDeals;                    /* 单个文件最大交易数量 */
	Seconds_t                                MaxIdleToCancelDeal;                   /* 等待操作最长时间，超出时间则取消操作，并返回初始界面 */
};

%// ---- PCA设备参数
struct EOD_PCAParam_t{                                                             
	PCAControlDefinition_t                   PCAControlDefinition;                 /* PCA的控制参数 */
	IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* 文件生成时间间隔 */
};


%// ---- EOD 参数
union EODParameter_t switch (EODComponentType_t eodCOMType) {                          
    case EOD_SYSTEM:                                                                 /* EOD: 系统参数 */
         EOD_SystemParam_t              EOD_SystemParam;

    case EOD_SYSNetwork:                                                             /* EOD: 路网信息参数 */
	 EOD_NetworkTopologyParam_t     EOD_NetworkTopologyParam;

    case EOD_SYSCalendar:                                                            /* EOD: 日历事件参数 */
         EOD_CalendarParam_t            EOD_CalendarParam;

    case EOD_SYSFare:                                                                /* EOD: 费率表参数 */
         EOD_FareParam_t                EOD_FareParam;

    case EOD_SYSTicket:                                                             /* EOD: 车票参数 */
         EOD_TicketParam_t              EOD_TicketParam;

    case EOD_Concession:                                                            /* EOD: 优惠方案参数 */
         EOD_ConcessionParam_t          EOD_ConcessionParam;

    case EOD_AGM:                                                                   /* EOD: AGM设备参数 */
         EOD_AGMParam_t                 EOD_AGMParam;

    case EOD_RG:                                                                    /* EOD: RG通行控制参数 */
         EOD_RGParam_t                  EOD_RGParam;

    case EOD_BOM:                                                                   /* EOD: BOM设备参数 */
         EOD_BOMParam_t                 EOD_BOMParam;

    case EOD_TVM:                                                                   /* EOD: TVM设备参数 */
         EOD_TVMParam_t                 EOD_TVMParam;

    case EOD_TVM_GUI:                                                               /* EOD: TVM界面参数 */
         EOD_TVM_GUIParam_t             EOD_TVM_GUIParam;

    case EOD_ISM:                                                                   /* EOD: ISM参数 */
         EOD_ISMParam_t                 EOD_ISMParam;		

    case EOD_PCA:                                                                   /* EOD: PCA参数 */
         EOD_PCAParam_t                 EOD_PCAParam;

 };

%// ---- 参数组件信息结构
struct EODComponent_t{                                                            
	EODComponentType_t              EODComponentType;                               /* EOD参数组件类型 */
	DeviceOwnerType_t               DeviceOwnerType;                                /* 使用者类型 */
	AFCTime64_t                     FileCreationTime;                               /* EOD 参数组件文件的创建时间 */
	string                          FileName<32>;                                   /* EOD参数组件文件名 */
	FileVersionID_t                 ComponentEODVersionID;                          /* EOD参数组件版本号，即小版本号 */	
};

%// ---- EOD 参数控制文件结构定义
struct EODMasterConfigFile_t {    
	FileHeaderTag_t			FileHeaderTag;					/* 主控文件类型码 */
	AFCTime64_t                     FileCreationTime;                               /* 文件的创建时间 */
	string				FileName<32>;					/* 文件名 */                         
	FileVersionID_t                 EODVersionID;                                   /* 参数大版本号 */
	AFCTime64_t                     EODVersionEffectTime;				/* 参数大版本生效时间 */
	EODComponent_t                  EODComponents<>;                                /* EOD参数组件文件列表 */
	MD5_t                           MD5Value;	                                /* MD5签名 */
};

%// ---- EOD 参数组件文件结构定义
struct EODFile_t {                                                              
	EODComponentType_t              EODComponentType;                               /* EOD参数组件类型 */
	AFCTime64_t                     FileCreationTime;                               /* 文件的创建时间 */
	string				FileName<32>;					/* 文件名 */           
	DeviceOwnerType_t               DeviceOwnerType;                                /* 使用者类型 */
	FileVersionID_t                 ComponentEODVersionID;                          /* EOD参数组件版本号，即小版本号 */	
	EODParameter_t                  EODParameter;                                   /* EOD参数组件内容 */
	MD5_t                           MD5Value;	                                /* MD5签名 */
};
%// ---------------------------------------------------------------------------------------


%// ---------------------------------------------------------------------------------------
%// ---- 其他运营参数定义
%// ---------------------------------------------------------------------------------------

%// ---- 全量明细黑名单结构
struct BLKACCFull_t{                                                                    
	string                          TicketLogicID<20>;                             /* 逻辑卡号 */
	BLKActionType_t                 BLKActionType;                                 /* 黑名单处理方式 */
};

%// ---- 增量明细黑名单结构
struct BLKACCInc_t{                                                            
	string                          TicketLogicID<20>;                             /* 逻辑卡号 */
	BLKActionType_t                 BLKActionType;                                 /* 黑名单处理方式 */
        IncBlackType_t                  IncBlackType;                                  /* 黑名单增量标志(0－正增量  1－负增量) */
};

%// ---- 号段黑名单结构
struct BLKACCSect_t{                                                   
	string                          StartTicketLogicID<20>;                        /* 起始逻辑卡号 */
	string                          EndTicketLogicID<20>;                          /* 终止逻辑卡号 */	
	BLKActionType_t                 BLKActionType;                                 /* 黑名单处理方式 */	
};


%// ---- 员工卡全量明细黑名单结构
struct BLKStaffFull_t{                                                
	string                          TicketLogicID<20>;                             /* 逻辑卡号 */
	BLKActionType_t                 BLKActionType;                                 /* 黑名单处理方式 */
};

%// ---- 单程票回收条件结构
struct SJTRecycleCond_t{                                                         
	Date2_t                         InitializeDate;                                /* 首次初始化日期 */
	InitBatchCode_t                 InitBatchCode;                                 /* 首次初始化批次号 */	
};

%// ---- 模式履历结构
struct WaiveDate_t{                                                      
 	ModeCode_t                      ModeCode;                                      /* 模式代码 */  
	Date2_t                         BusinessDay;                                   /* 运营日 */
	LineID_t                        LineID;                                        /* 线路编号 */	
	StationID_t                     StationID;                                     /* 车站编号 */	
	AFCTime64_t                     StartModeOccurTime;                            /* 降级模式发生开始时间 */
	AFCTime64_t                     EndModeOccurTime;                              /* 降级模式发生结束时间 */
};

%// 票卡一级分类结构
struct TicketFirstDirClass_t{                                                   
    TicketDirClassID_t              TicketDirClassID;                              /* 票卡一级分类编号 */  
    UnicodeString_t                 TicketFamilyName;                              /* 票卡一级分类名称 */	
};

%// 票卡二级分类结构
struct TicketSecondDirClass_t{                                                  
    TicketDirClassID_t              TicketFirstDirClassID;			   /* 票卡一级分类编号 */  
    TicketDirClassID_t              TicketSecondDirClassID;                        /* 票卡二级分类编号 */  
    UnicodeString_t                 TicketFamilyName;                              /* 票卡二级分类名称 */	
};

%// 票卡分类结构
struct TicketCatalog_t{                                                           
	TicketCatalogID_t               TicketCatalogID;                           /* 票卡目录编号 */ 
	UnicodeString_t                 TicketCatalogName;                         /* 票卡目录名称 */	
	ValueCent_t                     TicketValue;                               /* 车票金额 */	
	MultiRideNumber_t		MultiRideNumber;	                   /* 票卡次数 */  
	TicketDirClassID_t              TicketFirstDirClassID;                     /* 票卡一级分类编号 */  
        TicketDirClassID_t              TicketSecondDirClassID;                    /* 票卡二级分类编号 */
        ChipType_t                      ChipType;                                  /* 芯片类型 */
        UnicodeString_t                 TicketUnit;                                /* 计量单位 */	
        UnicodeString_t                 TicketColor;                               /* 票卡颜色 */	
        UnicodeString_t                 TicketUse;                                 /* 票卡用途 */	
        Boolean_t                       EnableTag;                                 /* 有效标志 0无效，1有效 */
};

%// ---- 票卡目录信息结构
struct TicketDirCatalog_t{                                                   
	TicketFirstDirClass_t           TicketFirstDirClasses<>;                       /* 票卡一级分类列表 */	
	TicketSecondDirClass_t          TicketSecondDirClasses<>;                      /* 票卡二级分类列表 */	
	TicketCatalog_t			TicketCatalogs<>;							   /* 票卡目录列表 */	
};

%// 设备节点结构
struct DeviceNode_t{                                                       
	DeviceID_t			DeviceID;                                      /* 设备节点编号 */
	UnicodeString_t			DeviceName;				       /* 设备名称 */	
	DeviceType_t                    DeviceType;                                    /* 设备类型 */	
	CoordinateUnit_t                DeviceDisplayX;                                /* 横坐标 */
	CoordinateUnit_t                DeviceDisplayY;                                /* 纵坐标 */	
	Angle_t                         DeviceDisplayAngle;                            /* 显示角度 */	
	IPAddress_t                     IPAddress;                                     /* 设备IP地址 */
	Boolean_t                       EnableFlag;                                    /* 启用标记 */
};

%// 车站设备节点结构
struct StationDeviceNode_t{                                                      
	StationID_t			StationID;                                     /* 车站编号 */
	ResourceFile_t			StationMap;				       /* 车站地图文件 */
	CoordinateUnit_t                StationDisplayX;                               /* 车站横坐标 */
	CoordinateUnit_t                StationDisplayY;                               /* 车站纵坐标 */	
	Angle_t                         StationDisplayAngle;                           /* 名称显示角度 */	
	DeviceNode_t			DeviceNodes<>;                                 /* 车站设备节点列表 */	
};

%// ---- 车站设备节点配置结构
struct StationDeviceNodeConfig_t{                                             
%//	string                        BOMPictureName<32>;                            /* BOM图片文件名称 */	
%//	string                        TVMPictureName<32>;                            /* TVM图片文件名称 */
%//	string                        TSMPictureName<32>;                            /* TSM图片文件名称 */
%//	string                        TCMPictureName<32>;                            /* TCM图片文件名称 */	
%//	string                        GIPictureName<32>;                             /* 进站检票机图片文件名称 */	
%//	string                        GOPictureName<32>;                             /* 出站检票机图片文件名称 */
%//	string                        WGBPictureName<32>;                            /* 双向检票机图片文件名称 */
%//	string                        GBPictureName<32>;                             /* 宽检票机图片文件名称 */
	LineID_t                      LineID;                                        /* 线路编号 */	
        ResourceFile_t                LineMap;                                       /* 线路地图文件 */
	StationDeviceNode_t	      StationDeviceNodes<>;			     /* 车站设备节点列表 */	

};

%// ---- 角色功能列表
struct RoleFunctionList_t{                                             
	RoleID_t                                 RoleID;                                /* 角色编号 */
	EQPFuncID_t                              EQPFuncIDs<>;                          /* 角色功能列表 */
};

%// ---- 操作员基本信息列表
struct OperInfo_t{                                                       
	OperatorID_t                             OperatorID;                            /* 用户编号 */
	string                                   OperatorPassword<32>;                  /* 用户密码 */
	Boolean_t                                OperatorEnableTag;                     /* 启用标志位 0不启用，1启用 */
	AFCTime64_t                              StartEnableTime;                       /* 启用时间 */
	AFCTime64_t                              EndEnableTime;                         /* 截至时间 */
};

%// ---- 操作员列表
struct OperatorList_t{                                                       
	OperatorID_t                             OperatorID;                            /* 用户编号 */
        Accessleve_t	                         Accesslevel;                           /* 用户权限等级 */
        RoleID_t                                 OwnRoleIDs<>;                          /* 配置角色列表 */
	EQPFuncID_t                              EQPFuncID<>;                           /* 配置设备权限功能 */	
	StationID_t                              OwnStations<>;                         /* 配置车站列表 */
	DeviceType_t                             OwnDeviceTypes<>;                      /* 配置终端设备类型列表 */
};

%// ---- 操作员权限参数
struct OperatorRight_t{                                                       
	OperatorList_t                           Operators<>;                           /* 操作员列表 */
        RoleFunctionList_t                       RoleFunctions<>;                       /* 角色功能列表*/
};


%// ---- 运营参数
union RUNParameter_t switch (RUNComponentType_t RUNComponentType) {             
    case RUN_STATIONEQUNODE:                                                       /* 车站设备节点配置文件 */
         StationDeviceNodeConfig_t               StationDeviceNodeConfig;
		    
    case RUN_BLKACCFULLLIST:                                                       /* 一票通全量明细黑名单文件 */
         BLKACCFull_t                            BLKACCFulls<>;

    case RUN_BLKACCINCRLIST:                                                       /* 一票通增量明细黑名单文件 */
	 BLKACCInc_t                             BLKACCIncs<>;

    case RUN_BLKACCFULLSECTLIST:                                                   /* 一票通全量号段黑名单文件 */
         BLKACCSect_t                            BLKACCSects<>;

    case RUN_BLKSTAFFFULLLIST:                                                     /* 员工卡全量明细黑名单文件 */
	 BLKStaffFull_t                          BLKStaffFulls<>;

    case RUN_SJTRECYCLE:                                                           /* 单程票回收条件文件 */
	 SJTRecycleCond_t                        SJTRecycleConds<>;

    case RUN_WAIVERDATE:                                                           /* 模式履历文件 */
         WaiveDate_t                             WaiveDates<>;

    case RUN_TICKETCATALOG:                                                        /* 票卡库存目录文件 */
         TicketDirCatalog_t                      TicketDirCatalog;

    case RUN_OPERINFO:					                            /* 操作员基本信息文件 */
         OperInfo_t				 OperInfos<>;

    case RUN_OPERRIGHT:					                            /* 操作员权限文件 */
         OperatorRight_t		         OperRight;                     
};

%// ---- 运营参数组件信息
struct RUNParameterComponent_t{                                          
 	RUNComponentType_t			RUNComponentType;                               /* 运营参数组件类型 */
	string					FileName<32>;                                   /* 运营参数组件文件名 */
	FileVersionID_t                         RUNParameterVersionID;                          /* 运营参数组件版本号 */	
};

%// ---- 运营参数控制文件
struct RUNParameterMasterConfigFile_t {                                   
	FileHeaderTag_t			RUNComponentType;                               /* 运营主控文件类型 */
	AFCTime64_t                     FileCreationTime;                               /* 主控文件的创建时间 */
	string				FileName<32>;					/* 文件名 */  
	RUNParameterComponent_t         RUNParameterComponents<>;                       /* 运营参数组件文件列表 */
	MD5_t                           MD5Value;	                                /* MD5签名 */
};

%// ---- 运营参数文件
struct RUNParameterFile_t {                      
	RUNComponentType_t		RUNComponentType;                               /* 运营参数组件类型 */                              
	AFCTime64_t                     FileCreationTime;                               /* 运营参数文件的创建时间 */
	string				FileName<40>;                                   /* 运营参文件名 */
	FileVersionID_t                 RUNParameterVersionID;                          /* 运营参数版本号 */	
	RUNParameter_t                  RUNParameter;                                   /* 运营参数组件内容 */
	MD5_t                           MD5Value;	                                /* MD5签名 */
};

%// --------------------------------------------------------------------------------------------------------------

%#endif 
%/********************************************** 文件结束 *******************************************************/
