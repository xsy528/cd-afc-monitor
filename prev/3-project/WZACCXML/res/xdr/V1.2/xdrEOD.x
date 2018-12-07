%/*************************************************************************************************************
%
%   ����������·AFCϵͳ����������׼
%
%   Title       : xdrEOD.x
%   @Version    : 1.2.0
%   @author     : ���Ʒ�
%   @date       : 2016/06/20
%   Description : ����ϵͳ���豸�����ļ��������ļ���ʽ
%**************************************************************************************************************/

%/*  �޶���ʱ��¼:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    ���Ʒ�           *     1.2.0 ��������������·AFCϵͳ����������׼V1.2�����ĵ�                                      
%**************************************************************************************************************/

%#ifndef XDREOD_H
%#define XDREOD_H
%
%#include "xdrBaseType.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- EOD��������
%// -------------------------------------------------------------------------------------------------------------

%// ʱ��ͬ���ṹ
struct SysTimeSync_t{															
	Seconds_t                                TimeDeviationPeriod;                   /* ʱ��ͬ��У׼��� */
	Seconds_t                                MinTimeDeviation;                      /* ʱ��ͬ����С��ֵ */	
	Seconds_t                                WarnTimeDeviation;                     /* ʱ��ͬ�����淧ֵ */
	Seconds_t                                MaxTimeDeviation;                      /* ʱ��ͬ�����ֵ */
};

%// �豸��¼�ṹ
struct DeviceLoginParam_t{                                                          
	Seconds_t                                LoginOvertime;                         /* ��¼��ʱʱ�� */
	Times_t                                  MaxPINEntryRetries;                    /* �����¼�������� */	
};


%// SC�ļ��ϱ������ṹ
struct SCUploadFile_t{	                                                  
        RecordsInFile_t                          SingleUDFileMaxDeals;                  /* SC�����ļ�������¼���� */
	RecordsInFile_t                          SingleARFileMaxDeals;                  /* SC����ļ�������¼���� */
	Seconds_t                                IntervalOfBuildYPTUDFile;              /* SC�����ļ�����ϴ�ʱ������һƱͨ�� */
        Seconds_t                                IntervalOfBuildYKTUDFile;              /* SC�����ļ�����ϴ�ʱ������һ��ͨ�� */
	Seconds_t                                IntervalOfBuildARFile;                 /* SC����ļ�����ϴ�ʱ���� */
	Seconds_t                                IntervalOfBuildStockFile;              /* SC����ⵥ�ļ��ϴ�ʱ���� */
};

%// ---- EODϵͳ����
struct EOD_SystemParam_t {                                                        
	SysTimeSync_t                            SysTimeSync;                           /* ʱ��ͬ������ */
	SCUploadFile_t                           SCUploadFile;                          /* SC�ļ��ϱ����� */
	DeviceLoginParam_t                       DeviceLoginParam;                      /* �豸��¼���� */
};


%// ��·�ṹ
struct Line_t{                                                            
	LineID_t                                 LineID;                                /* ��·���� */
	UnicodeString_t                          LineCNName;                            /* ��·�������� */
	string                                   LineENName<64>;                        /* ��·Ӣ��ȫ�� */
	Boolean_t                                EnableFlag;                            /* ���ñ�־λ */
};

%// ��վ�ṹ
struct Station_t{                                                                  
	StationID_t                              StationID;                             /* ��վ���� */
	UnicodeString_t                          StationCNName;                         /* ��վ�������� */
	string                                   StationENName<64>;                     /* ��վӢ��ȫ�� */
	LineID_t                                 LineID;                                /* ��վ������·��� */
	Boolean_t                                EnableFlag;                            /* ���ñ�־λ */
};

%// ���˳�վ�ṹ
struct TransferStation_t {                                                                   
	TransferStationID_t                      TransferStationID;                     /* ����������� */	
	UnicodeString_t                          TransferStationCNName;                 /* ���������������� */	
	string                                   TransferStationENName<64>;             /* ��������Ӣ�ļ�� */
	StationID_t                              StationIDs<>;                          /* ���������ڳ�վ�б� */
	Boolean_t                                EnableFlag;                            /* ���ñ�־λ */	
};

%// ·����Ϣ����
struct EOD_NetworkTopologyParam_t{                                              
	Line_t                                   Lines<>;                               /* ��·�б� */
	Station_t                                Stations<>;                            /* ��վ�б� */
        TransferStation_t                        TransferStations<>;                    /* ���˳�վ�б� */
};

%// �������ڽṹ
struct SpecialDate_t {                                                             
	Date2_t                                  SpecialDate;                           /* �������� */
	DateTypeID_t                             DateTypeID;                            /* �������� */
};

%// ʱ��νṹ ��0=(0,25200] 1=(25200,32400]
struct TimeInterval_t {                                                         
	TimeIntervalID_t                         TimeIntervalID;                        /* ʱ��α��  */
	SecondsSinceMidNight_t                   TimeIntervalValue;                     /* ʱ��ν���ʱ�� */
};

%// �������ʱ��ṹ
struct FareTierTimeTable_t {                                                      
	FareTier_t                               FareTier;                              /* ���ʵȼ����� */
	Seconds_t                                AllowedTravelPeriod;                   /* ����վ�ڵĶ���ʱ�� */
};

%// ---- ����ʱ���������
struct EOD_CalendarParam_t { 
        SecondsSinceMidNight_t                   RunStartofDay;                         /* ��Ӫ���л�ʱ��, ������2:00 AM*/	                      
	SecondsSinceMidNight_t                   RunStartTime;                          /* ��Ӫ��ʼʱ�� */
	SecondsSinceMidNight_t                   RunEndTime;                            /* ��Ӫ����ʱ�� */
	SpecialDate_t                            SpecialDates<>;                        /* ���������б� */
	TimeInterval_t                           TimeIntervals<>;                       /* ʱ����б�  */
	FareTierTimeTable_t                      FareTierTimeTables<>;                  /* �������ʱ���б� */	
};

%// ��Ʊ����������
struct FareGroup_t {                                                            
	TicketType_t                             TicketType;                            /* ��Ʊ���ʹ��� */
	DateTypeID_t                             DateTypeID;                            /* �������ʹ��� */
	TimeIntervalID_t                         TimeIntervalID;                        /* ʱ��δ��� */
	FareGroupID_t                            FareGroupID;                           /* ��Ʊ��������� */
};

%// ���ʵȼ��ṹ
struct FareTierMatrix_t{                                                           
	FareTier_t                               FareTier;                              /* ���ʵȼ����� */
	StationID_t                              StationIDIn;                            /* ��վ��վ��� */
	StationID_t                              StationIDOut;                            /* ��վ��վ��� */
};

%// �������ʽṹ
struct BaseFare_t {                                                                 
	FareGroupID_t                            FareGroupID;                           /* ��������� */
	FareTier_t                               FareTier;                              /* ���ʵȼ����� */
	ValueCent_t                              FareAmount;                            /* ����ֵ */
};

%// ���п���ֵ���ƽṹ
struct BankAddingValuePara_t{                                                     
	ValueCent_t                              BankCardPaymentMinAmount;              /* ���п���ֵ����С��� */
	ValueCent_t                              BankCardPaymentMaxAmount;              /* ���п���ֵ������� */
};

%// ---- ���ʱ����
struct EOD_FareParam_t {                                                            
	BankAddingValuePara_t                    BankAddingValuePara;                   /* ���п���ֵ���� */
	FareGroup_t                              FareGroups<>;                          /* ��Ʊ�����鶨���б� */
	FareTierMatrix_t                         FareTierMatrixDatas<>;                 /* ���ʵȼ������б� */
	BaseFare_t                               BaseFares<>;                           /* �������ʶ����б� */	
};

%// оƬ���ͽṹ
struct ChipDefinition_t {                                                         
	ChipType_t                               ChipType;                              /* оƬ���ʹ��� */
	Duration_t                               PhycialValidityPeriod;                 /* Ĭ��оƬ������Ч�ڳ���(��) */
	Times_t	                                 MaxChipIONumber;                       /* RFU Ĭ��оƬ����״���*/
};

%// ��Ʊ���۶���ṹ
struct TicketDefinition_t{                                                          
	TicketType_t                             TicketType;                            /* ��Ʊ���ʹ��� */
	UnicodeString_t                          TicketTypeCNName;                      /* ��Ʊ������������ */
	string                                    TicketTypeENName<64>;                  /* ��Ʊ����Ӣ�ļ�� */	
	TicketFamilyType_t                       TicketFamilyType;                      /* Ʊ�ִ������ʹ��� */
	AreaTicketFlag_t                         AreaTicketFlag;                        /* ��Ʊʹ�÷�Χ���Ʊ�־(0: ��ʹ�÷�Χ���� 1: ���޶��ķ�Χʹ��  */
	ValueCent_t                              SaleFixedPrice;                        /* �̶����ۼ۸� */ 
	Boolean_t                                SouvenirFlag;                          /* ����Ʊ��־(0: �Ǽ���Ʊ; 1: ����Ʊ) */
	Boolean_t                                MemberTicketFlag;                      /* ����Ʊ��־λ(0: �Ǽ���Ʊ; 1: ����Ʊ) */
	SoundDisplayID_t                         SoundDisplayID;                        /* AGM��ʾ����� */
	ConcessionalLampID_t                     ConcessionalLampID;                    /* AGM��ʾ����ɫ��ţ�����Ƶ�ʺ�ʱ���豸�ɱ��ؿ��ƣ� */	
	Boolean_t                                AddValueAuthorized;                    /* �Ƿ�ɳ�ֵ(0-��1-��) */	
	ValueCent_t                              MaxAddValue;                           /* ��ֵ�����Զ���ֵ���� */
	ValueCent_t                              DefaultAddValue;                       /* ��ֵ����� */
	ValueCent_t                              MinAddValue;                           /* ��ֵ������� */
	ValueCent_t                              MaxRemainingValue;                     /* ��ֵ��Ʊ������ */
	ValueCent_t                              MinEntryAmount;                        /* ��С��վ��� */
	ValueCent_t                              MinExitAmount;                         /* ��С��վ��Ԥ����*/
	ValueCent_t                              DepositValue;                          /* Ʊ������Ѻ�� */
	ValueCent_t                              DepreciationValue;                     /* �۾ɼ������,�̶�Ϊÿ���ڿ۳����۾ɷ� */
	DepreciationCyc_t                        DepreciationCyc;                       /* �۾ɼ��㵥λ����  0-���� 1-���� 2���� */ 
	Boolean_t                                RefundAuthorized;                      /* ��Ʊ��ʽ 0����������Ʊ��1��ֻ������Ʊվ��Ʊ 2-������Ʊ */
	ValueCent_t                              MaxImmediateRefundAmount;              /* ���ʱ��Ʊ��� */
	ValueCent_t                              RefundCharge;                          /* ��Ʊ������ */
	MultiRideNumber_t                        MaxRideNumber;                         /* ����������(���Ƽ�ʱƱ) */
	MultiRideNumber_t                        DayMaxRideNumber;                      /* ��������������(���Ƽ�ʱƱ) */
	DurationMode_t                           DurationMode;                          /* ��Ʊ��Ч����� */
	Duration_t                               Duration;                              /* ����ʱ���(��) */
	Date2_t                                  FixedEndDate;                          /* �̶�������Ч���� */
        Boolean_t                                ExtendAuthorized;                      /* �Ƿ��������� (0����1����) */
        Duration_t                               DefaultExtendDays;                     /* Ĭ���������� */
        Boolean_t                                PullInAuthorized;                      /* �Ƿ�ֻ������Ʊվ��վ (0����1����) */
        Seconds_t                                PeriodofFreeAdjustForNoExit;           /* �ظ���վ��Ѹ������ʱ�� */
	ValueCent_t                              OverstayingFeeValue;                   /* ��ʱ������ */
        OverstayingMode_t                        OverstayingMode;                       /* ��ʱ���ʽ */
	ValueCent_t                              NoExitFeeValue;                        /* ����վ��ƥ�䲹���վ�ˣ� */
	ValueCent_t                              SurchargeStationMisMatch;              /* �ǲ��վ��վʱ�ٴβ������վ�ˣ� */	
	ValueCent_t                              AGMSoundMinBalance;                    /* ��ʾ��С��� */
	ValueCent_t                              SaleInitAmount;                    	/* ����ʱ��ʼ��� */
	MultiRideNumber_t                        SaleInitRideNumber;                    /* ����ʱ��ʼ���� */
	ValueCent_t                              EveryTimeAmount;                    	/* ���������� */
	ConcessionType_t                         ConcessionType;                        /* ��ֵƱ�Żݷ�ʽ */
	ConcessionID_t                           JoinConcessionID;                      /* �����Żݴ��� */
	ConcessionID_t                           PileConcessionID;                      /* �ۻ��Żݴ��� */
	Boolean_t                                EnableFlag;                            /* ���ñ�־λ, ���е��豸��Ҫ���˱�־λ(0: δ���ã�1:����) */	
};

%// Ʊ����ӳ��ṹ(����һ��ͨ���ƶ��ֻ�����
struct TicketTypeMap_t {                                                     
	TicketType_t                             TicketType;                            /* ������Ʊ���ʹ��� */                         
	U8_t                                     YKTTicketType;                         /* �ⲿƱ�����ʹ��� */  
	CityCode_t	                         CityCode;                              /* ���д��� */
        IssuerCode_t                             IssuerCode;                            /* ���������� */
	ChipType_t                               ChipType;                              /* оƬ���� */              
};

%// ---- ��Ʊ����
struct EOD_TicketParam_t{	                                                      
	ChipDefinition_t                         ChipDefinitions<>;                     /* оƬ�б� */
	TicketDefinition_t                       TicketDefinitions<>;                   /* ��Ʊ���۱� */
	TicketTypeMap_t                          TicketTypeMaps<>;                      /* Ʊ����ӳ��� */	
};

%// �����Żݽṹ
struct JoinConcessionDefinition_t{	                                           
        ConcessionID_t                           JoinConcessionID;                      /* �����Żݴ��� */
	IndustryScope_t                          JoinConcessionIndustryScope;           /* �����Żݻ��˴��� */	
	Minutes_t                                JoinConcessionValidTime;               /* �����Ż���Чʱ�䷶Χ(��λ������) */	
	JoinConcessionType_t                     JoinConcessionType;                    /* �����Żݷ�ʽ(0����1������) */	
	ValueCent_t                              JoinConcessionAmount;                  /* �����Żݽ�� */	
	Percentage_t                             JoinConcessionPercentage;              /* �����Żݱ��� */	
};

%// �ۻ��Żݽṹ
struct PileConcessionDefinition_t{	                                               
	ConcessionID_t                           PileConcessionID;                      /* �ۻ��Żݴ��� */
	PileConcessionType_t                     PileConcessionType;                    /* �ۻ��Żݷ�ʽ(0���ۻ������Żݣ�1���ۻ�����Ż�) */	
	Times_t                                  PileConcessionTime;                    /* �ۻ�������ʼ�Żݴ��� */
	ValueCent_t                              PileConcessionAmount;                  /* �ۻ�������ʼ�Żݽ�� */	
	Percentage_t                             JoinConcessionPercentage;              /* �ۻ������Żݱ��� */	
	ConcessionResetType_t                    ConcessionResetType;                   /* �Ż���������(0���������� 1���������� 2���������� 3��������) */
};

%// ---- �Żݷ�������
struct EOD_ConcessionParam_t{	                                                  
	JoinConcessionDefinition_t               JoinConcessionDefinitions<>;           /* �����Ż��б� */
	PileConcessionDefinition_t               PileConcessionDefinitions<>;           /* �ۻ��Ż��б� */	
};

%// բ�����ƽṹ
struct AGMControlDefinition_t{	                                                    
	RecordsInFile_t                          SingleFileMaxDeals;                    /* �����ļ���������� */
	Seconds_t                                MaxIntervalToRejectSameTicket;         /* �ܾ�ͬ��Ʊ���ظ���Ѱ�������ʱ�� */	
	Seconds_t                                MaxDelayAfterRejectInvalidTicket;      /* �ܾ��Ƿ�Ʊ������ӳ�ʱ�� */	
	Percentage_t                             MaxWarnOfTicketBox;                    /* Ʊ�佫������������ */
%//	TicketQuantity_t                         MaxWarnOfInvalidTicketBox;             /* ��Ʊ�佫������������ */
	PatronQueue_t                            PatronQueue;                           /* �˿͹�բ��������� */
	Seconds_t                                MaxDelayOfShowPicture;                 /* ͼ����ʾ����ʱ�� */
	Boolean_t                                ReaderWorkInGateInbreak;               /* բ������״̬ʱ������������־(0��ֹͣ��1������) */
	Seconds_t                                MaxWaitAfterReadCard;                  /* �˿�ˢ����բ�����ȴ�ʱ�� */
	Seconds_t                                MaxIntervalOfShutGate;                 /* �˿�����ͨ��բ��ʱ�����ű��ִ�״̬�ļ��ʱ�� */
	Seconds_t                                MaxStayInGate;                         /* �˿�ͨ�����������ʱ�� */
};

%// �ļ�����ʱ�����ṹ
struct IntervalOfMakeFile_t{	                                                  
	Seconds_t                                IntervalOfBuildYPTUDFile;               /* һƱͨ���������ļ�����ʱ���� */
	Seconds_t                                IntervalOfBuildYKTUDFile;               /* һ��ͨ���������ļ�����ʱ���� */
	Seconds_t                                IntervalOfBuildARFile;                  /* ��������ļ�����ʱ���� */
};

%// AGM��ʾ�ƽṹ
struct ConcessionalLampType_t{                                                    
	ConcessionalLampID_t                    ConcessionalLampID;                     /* �ƹ���ʾ��� */
	LampColorTypeID_t                       LampColorTypeID;                        /* �ƹ���ɫ���� */
	FrequencyWithinMin_t                    Frequency;                              /* �ƹ�����Ƶ��,ÿ���Ӵ��� */
	Seconds_t                               DisplayPeriod;                          /* �ƹ���ʾ��ʱ�� */
};

%// AGM������ʾ�ṹ
struct SoundDisplay_t {                                                           
	SoundDisplayID_t                         SoundDisplayID;                        /* AGM��ʾ����� */	
	string                                   SoundDisplayFileName<32>;              /* AGM��ʾ���ļ��� */
	MD5_t                                    SoundDisplayFileMD5;                   /* AGM��ʾ���ļ������ļ����������ݵ�MD5�� */
};

%// ---- AGM�豸����
struct EOD_AGMParam_t {                                                   
	AGMControlDefinition_t                   AGMControlDefinition;                  /* AGM���Ʋ��� */
	IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* AGM�ļ�����ʱ���� */
	ConcessionalLampType_t                   ConcessionalLampTypes<>;               /* AGM��ʾ���б� */
	SoundDisplay_t                           SoundDisplays<>;	                /* AGM������ʾ�б� */
};

%// ͨ��ģʽ�ṹ
struct PassageModeInfo_t {                                                           
	PassageMode_t                            PassageMode;                            /* ͨ��ģʽ */	
	SecondsSinceMidNight_t                   BeginTime;                              /* ��ʼʱ�� */
	SecondsSinceMidNight_t                   EndTime;                                /* ����ʱ�� */
};

%// ---- RGͨ�п��Ʋ���
struct EOD_RGParam_t {                                                   
	DeviceID_t				 DeviceID;                              /* �豸�ڵ��� */
	PassageModeInfo_t                        PassageModeInfos<>;	                /* ͨ��ģʽ��Ϣ�б� */
};

%// BOM���ƽṹ
struct BOMControlDefinition_t{	                                                 
	RecordsInFile_t                          SingleFileMaxDeals;                    /* �����ļ���������� */
%//	Duration_t                               MaxDaysOfSaveDeals;                    /* ���ױ��ر�����Ч���� */
	Seconds_t                                MaxExitNoOperation;                    /* ����޲����Զ��˳��ʱ�� */	
%//	Seconds_t                                MaxEnterOfFreeUpdate;                  /* �ظ���վ��Ѹ����������ʱ�� */	
	Percentage_t                         MinWarnAmountOfTicketBox;                  /* Ʊ�佫�վ��������� */
};

%// Ʊ��ѡ��ť�ṹ��BOM��
struct ExpressBtnForBOM_t {                                                            
	BtnIndex_t				 BtnIndex;				/* ��Ʊ��ťID */
	ValueCent_t                              BuyBtnValue;                           /* ��Ʊ��ť��ʾ��� */
	FareTier_t				 FareTier;				/* ��Ʊ��ť��Ӧ���ʵȼ� */
};

%// ---- BOM�豸����
struct EOD_BOMParam_t{                                                            
	BOMControlDefinition_t                   BOMControlDefinition;                  /* BOM���Ʋ��� */
        IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* �ļ�����ʱ���� */
	ExpressBtnForBOM_t                     ExpressBtnForBOM<>;                    /* Ʊ��ѡ��ť�ṹ��BOM��*/
};

%// TVM���Ʋ����ṹ
struct TVMControlDefinition_t{	                                           
	RecordsInFile_t                          SingleFileMaxDeals;                    /* �����ļ���������� */
	TicketQuantity_t     	                 MaxAmountOfBuyTicket;                  /* ���ι�Ʊ������� */
	Seconds_t                                MaxIdleToCancelDeal;                   /* ���޶���ʱ�������κκ�������������׽���ȡ������Ͷ���Ǯ�ҽ������˿� */
	ValueCent_t                              ValidCoinTypes<>;                      /* ����ʹ��Ӳ�����ͣ���ֵ�� */
	ValueCent_t                              ValidBillTypes<>;                      /* ����ʹ��ֽ�����ͣ���ֵ�� */
	RMBQuantity_t                            MaxAmountOfCoinChange;                 /* �����������Ӳ�Ҹ��� */
	RMBQuantity_t                            MaxAmountOfNoteChange;                 /* �����������ֽ������ */
	ValueCent_t                              MaxMoneyOfChangeMoney;                 /* ������������� */
	RMBQuantity_t                            AlmostFullLimitOfCallbackCoinBox;      /* Ӳ�һ����佫������ֵ */
	RMBQuantity_t                            FullLimitOfCallbackCoinBox;            /* Ӳ�һ�����������ֵ */
	RMBQuantity_t                            AlmostEmptyLimitOfReturnCoinBox;       /* Ӳ�������佫�ձ���ֵ */
	RMBQuantity_t                            EmptyLimitOfReturnCoinBox;             /* Ӳ��������ձ���ֵ */
	RMBQuantity_t                            AlmostFullLimitOfCallbackBillBox;      /* ֽ�һ����佫������ֵ */
	RMBQuantity_t                            FullLimitOfCallbackBillBox;            /* ֽ�һ�����������ֵ */
	RMBQuantity_t                            AlmostEmptyLimitOfReturnBillBox;       /* ֽ�������佫�ձ���ֵ */
	RMBQuantity_t                            EmptyLimitOfReturnBillBox;             /* ֽ��������ձ���ֵ */
	Percentage_t                             MinWarnOfTicketBox;                    /* Ʊ����;��������� */
	Percentage_t                             MaxWarnOfInvalidTicketBox;             /* ��Ʊ����󾯽������� */
};

%// ��Ļ�ߴ�ṹ
struct ScreenSize_t {                                                             
    CoordinateUnit_t                         ScreenWidth;                           /* ��Ļ��� */
    CoordinateUnit_t                         ScreenHeight;                          /* ��Ļ�߶� */
};

%// ��Դ�ļ��ṹ
struct ResourceFile_t {                                                            	
    string                               PictureFileName<32>;                   /* ͼƬ�ļ��� */
    MD5_t                                PictureFileMD5;                        /* ͼƬ�ļ���MD5�� */
};  

%// ��ʾ���ڣ���ť���ṹ
struct DisplayWindow_t {                                                         
    CoordinateUnit_t                         DisplayPictureX;                       /* �������Ͻ�X���� */
    CoordinateUnit_t                         DisplayPictureY;                       /* �������Ͻ�Y���� */
    CoordinateUnit_t                         DisplayPictureWidth;                   /* ������ʾ���򣨰�ť����� */
    CoordinateUnit_t                         DisplayPictureHeight;                  /* ������ʾ���򣨰�ť���߶� */
};

%// ���ṹ
struct ADItem_t {                                                                 
	ADItemID_t                               ADItemID;                              /* �����ID */
	ADType_t				 ADType;                                /* ������� */	
	string                                   ADItemFileName<32>;                    /* ����ý�岥���ļ����� */
	MD5_t                                    ADItemFileMD5;                         /* ����ý�岥���ļ������ļ����������ݵ�MD5�� */
};

%// ����ͼƬ�ṹ
struct TopBoard_t {                                                               
	TopBoardID_t                             TopBoardID;                            /* �������ݱ��ID */
	UnicodeString_t                          CNDisplay;                             /* ������ʾ���ݣ����ģ� */
	string                                   ENDisplay<32>;                         /* ������ʾ���ݣ�Ӣ�ģ� */
};

%// Ʊ��ѡ��ť�ṹ
struct ExpressBtn_t {                                                            
	BtnIndex_t				 BtnIndex;				/* ��Ʊ��ťID */
	ValueCent_t                              BuyBtnValue;                           /* ��Ʊ��ť��ʾ��� */
	FareTier_t                               FareTier;				/* ��Ʊ��ť��Ӧ���ʵȼ� */
	DisplayWindow_t                          BuyBtnDisplay;                         /* ��Ʊ��ť��ʾ���� */
};

%// ����ѡ��ť�ṹ
struct NumbersBtn_t {                                                            
	BtnIndex_t				 BtnIndex;				 /* ������ťID */
	TicketQuantity_t                         TicketQuantity;                         /* ������ť��ʾ���� */
	DisplayWindow_t                          NumbersBtnDisplay;                      /* ������ť��ʾ���� */
};


%// ·�������г�վ��ť�ṹ
struct StationBtnInLineZone_t {                                                 
	StationID_t                              StationID;                             /* ��վѡ��ťID */
        DisplayWindow_t                          StationInLineZoneDisplay;              /* ��վѡ������������Ϣ */
	Boolean_t                                StationSelBtnEnable;                   /* ��վѡ��ť���ñ�־ */
};

%// ��·��ť�ṹ
struct LineBtn_t {                                                               
	LineID_t                                 LineID;                                /* ��·ѡ��ťID */  
	DisplayWindow_t                          LineBtnDisplay;                        /* ��·ѡ��ť������Ϣ */
	Boolean_t                                LineBtnEnableFlag;                     /* ��·ѡ��ť���ñ�־ 0�����ã�1���� */	
	ResourceFile_t                           CNLinePicture;                         /* ��·����ͼƬ */
	ResourceFile_t                           ENLinePicture;                         /* ��·Ӣ��ͼƬ */
        DisplayWindow_t                          LineDisplay;                           /* ��·������Ϣ */
};

%// ��·�г�վ��ť�ṹ
struct StationBtnInLine_t {                                                       
	StationID_t                              StationID;                             /* ��վѡ��ťID */
	LineID_t                                 LineID;                                /* ��·ѡ��ťID */ 
        DisplayWindow_t                          StationInLineDisplay;                  /* ��վѡ������������Ϣ */
	Boolean_t                                StationSelBtnEnable;                   /* ��վѡ��ť���ñ�־(0�����ã�1����) */
};

%// ֧�����������ʾλ�ýṹ
struct PayInterfacePosition_t {                                                       
	PosType_t				 PosType;                               /* λ������ */
        DisplayWindow_t                          PosDisplay;                            /* λ��������Ϣ */
};

%// ---- TVM�豸����
struct EOD_TVMParam_t{                                                           
	TVMControlDefinition_t                   TVMControlDefinition;                  /* TVM�Ŀ��Ʋ��� */
	IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* �ļ�����ʱ���� */
};

%// ��ֵѡ��ť�ṹ
struct AddValueBtn_t {                                                            
	BtnIndex_t			         AddValueBtnIndex;                      /* ��ֵ��ťID */
	ValueCent_t                              AddValueBtnValue;                      /* ��ֵ��ť��ʾ��� */
	DisplayWindow_t                          AddValueBtnDisplay;                    /* ��ֵ��ť��ʾ���� */
};

%//  TVM������� �Ƿ���Ҫ���·��ͼƬ��С��߶ȺͿ��   hkf 2016-6-22
struct EOD_TVM_GUIParam_t{                                                           
	ScreenSize_t                             ScreenSize;                            /* ��Ļ�ߴ� */
	ResourceFile_t                           StartupDisplayPicture;                 /* ��������ͼƬ�ļ� */
	DisplayWindow_t                          StartupDisplayWindow;                  /* ��������ͼƬ��ʾ���� */
	ResourceFile_t                           PauseDisplayPicture;                   /* ��ͣ���񴰿�ͼƬ�ļ� */
	DisplayWindow_t                          PauseDisplayWindow;                    /* ��ͣ���񴰿�ͼƬ��ʾ���� */
	ResourceFile_t                           CNLineZonePicture;                     /* ·������ͼƬ�ļ� */
	ResourceFile_t                           ENLineZonePicture;                     /* ·��Ӣ��ͼƬ�ļ� */
	DisplayWindow_t                          LineZoneDisplayWindow;                 /* ·��ͼƬ��ʾ���� */
  	DisplayWindow_t                          LanguageDisplayWindow;                 /* ��Ӣ�İ�ť��ʾ���� */
	DisplayWindow_t                          BackDisplayWindow;			/* ���ذ�ť��ʾ���� */
	DisplayWindow_t                          AddValueDisplayWindow;                 /* ��ֵ��ť��ʾ���� */
	AddValueBtn_t                            AddValueBtns<>;                        /* ��ֵѡ��ť�б� */
	DisplayWindow_t                          CheckCardDisplayWindow;		/* �鿨��ť��ʾ���� */
	DisplayWindow_t                          NowtimeDisplayWindow;                  /* ��ǰ����ʱ����ʾ���� */
	DisplayWindow_t                          NowStationDisplayWindow;               /* ��ǰ��վ��ʾ���� */
	DisplayWindow_t                          RunningStatusDisplayWindow;		/* ����״̬��ʾ���� */
	ADItem_t                                 ADItems<>;                             /* ����б�Ԥ���� */
	Boolean_t                                ADPlayType;                            /* ��沥�ŷ�ʽ 0��ͷ��ʼ��1�������ţ�Ԥ���� */	
	Seconds_t                                MaxIdleToPlayAD;                       /* ������ģʽ��������ʱ�䣨Ԥ���� */	
	TopBoard_t                               TopBoards<>;                           /* �����б� */	
	ExpressBtn_t                             FareSelBtns<>;                         /* Ʊ��ѡ��ť�б� */
	NumbersBtn_t                             NumbersSelBtns<>;                      /* ����ѡ��ť�б� */
	StationBtnInLineZone_t                   StationBtnInLineZones<>;               /* ·�������г�վ��ť�б� */
	LineBtn_t                                LineBtns<>;                            /* ��·ѡ��ť�б� */
	StationBtnInLine_t                       StationBtnInLines<>;                   /* ��·�г�վѡ��ť�б� */
	ResourceFile_t                           CNPayInterfacePicture;                 /* ֧����������ͼƬ�ļ� */
	ResourceFile_t                           ENPayInterfacePicture;                 /* ֧������Ӣ��ͼƬ�ļ� */
	DisplayWindow_t                          PayInterfaceDisplayWindow;             /* ֧��������ʾ���� */
	PayInterfacePosition_t			 PayInterfacePositions<>;		/* ֧���������λ����ʾ���� */
};

%// ISM���Ʋ����ṹ
struct ISMControlDefinition_t{	                                                
	RecordsInFile_t                          SingleFileMaxDeals;                    /* �����ļ���������� */
	Seconds_t                                MaxIdleToCancelDeal;                   /* �ȴ������ʱ�䣬����ʱ����ȡ������ */
%//	ValueCent_t                              ValidBillTypes<>;                      /* ����ʹ��ֽ�����ͣ���ֵ�� */
%//	Paymentmeans_t                           Paymentmeans;                          /* ��֧�ֵ�֧����ʽ1���ֽ�2�����п���3���ֽ�����п� */
	RMBQuantity_t                            AlmostFullLimitOfBillBox;              /* ֽ��Ǯ�佫������ֵ */
	RMBQuantity_t                            FullLimitOfBillBox;                    /* ֽ��Ǯ��������ֵ */
	ValueCent_t                              MaxMoneyOfCashAddValue;                /* �ֽ𵥴γ�ֵ����� */
%//	ValueCent_t                              MaxMoneyOfBankCardAddValue;            /* ���п����γ�ֵ����� */	
};


%// ---- ISM�豸����
struct EOD_ISMParam_t{                                                        
	ISMControlDefinition_t                   TSMControlDefinition;                  /* TSM�Ŀ��Ʋ��� */
	IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* �ļ�����ʱ���� */
	ScreenSize_t                             ScreenSize;                            /* ��Ļ�ߴ� */
	ResourceFile_t                           StartupDisplayPicture;                 /* ��������ͼƬ�ļ� */
	DisplayWindow_t                          StartupDisplayWindow;                  /* ��������ͼƬ��ʾ���� */
	ResourceFile_t                           PauseDisplayPicture;                   /* ��ͣ���񴰿�ͼƬ�ļ� */
	DisplayWindow_t                          PauseDisplayWindow;                    /* ��ͣ���񴰿�ͼƬ��ʾ���� */
	DisplayWindow_t                          NowtimeDisplayWindow;                  /* ��ǰ����ʱ����ʾ���� */
	DisplayWindow_t                          NowStationDisplayWindow;               /* ��ǰ��վ��ʾ���� */
	DisplayWindow_t                          RunningStatusDisplayWindow;		/* ����״̬��ʾ���� */
	ADItem_t                                 ADItems<>;                             /* ����б�Ԥ���� */
	Boolean_t                                ADPlayType;                            /* ��沥�ŷ�ʽ 0��ͷ��ʼ��1�������ţ�Ԥ���� */	
	Seconds_t                                MaxIdleToPlayAD;                       /* ������ģʽ��������ʱ�䣨Ԥ���� */	
	TopBoard_t                               TopBoards<>;                           /* �����б� */	
        DisplayWindow_t                          AddValueDisplayWindow;                 /* ��ֵ��ť��ʾ���� */
	AddValueBtn_t                            AddValueBtns<>;                        /* ��ֵѡ��ť�б� */
};


%// PCA���Ʋ����ṹ
struct PCAControlDefinition_t{	                                             
	RecordsInFile_t                          SingleFileMaxDeals;                    /* �����ļ���������� */
	Seconds_t                                MaxIdleToCancelDeal;                   /* �ȴ������ʱ�䣬����ʱ����ȡ�������������س�ʼ���� */
};

%// ---- PCA�豸����
struct EOD_PCAParam_t{                                                             
	PCAControlDefinition_t                   PCAControlDefinition;                 /* PCA�Ŀ��Ʋ��� */
	IntervalOfMakeFile_t                     IntervalOfMakeFile;                    /* �ļ�����ʱ���� */
};


%// ---- EOD ����
union EODParameter_t switch (EODComponentType_t eodCOMType) {                          
    case EOD_SYSTEM:                                                                 /* EOD: ϵͳ���� */
         EOD_SystemParam_t              EOD_SystemParam;

    case EOD_SYSNetwork:                                                             /* EOD: ·����Ϣ���� */
	 EOD_NetworkTopologyParam_t     EOD_NetworkTopologyParam;

    case EOD_SYSCalendar:                                                            /* EOD: �����¼����� */
         EOD_CalendarParam_t            EOD_CalendarParam;

    case EOD_SYSFare:                                                                /* EOD: ���ʱ���� */
         EOD_FareParam_t                EOD_FareParam;

    case EOD_SYSTicket:                                                             /* EOD: ��Ʊ���� */
         EOD_TicketParam_t              EOD_TicketParam;

    case EOD_Concession:                                                            /* EOD: �Żݷ������� */
         EOD_ConcessionParam_t          EOD_ConcessionParam;

    case EOD_AGM:                                                                   /* EOD: AGM�豸���� */
         EOD_AGMParam_t                 EOD_AGMParam;

    case EOD_RG:                                                                    /* EOD: RGͨ�п��Ʋ��� */
         EOD_RGParam_t                  EOD_RGParam;

    case EOD_BOM:                                                                   /* EOD: BOM�豸���� */
         EOD_BOMParam_t                 EOD_BOMParam;

    case EOD_TVM:                                                                   /* EOD: TVM�豸���� */
         EOD_TVMParam_t                 EOD_TVMParam;

    case EOD_TVM_GUI:                                                               /* EOD: TVM������� */
         EOD_TVM_GUIParam_t             EOD_TVM_GUIParam;

    case EOD_ISM:                                                                   /* EOD: ISM���� */
         EOD_ISMParam_t                 EOD_ISMParam;		

    case EOD_PCA:                                                                   /* EOD: PCA���� */
         EOD_PCAParam_t                 EOD_PCAParam;

 };

%// ---- ���������Ϣ�ṹ
struct EODComponent_t{                                                            
	EODComponentType_t              EODComponentType;                               /* EOD����������� */
	DeviceOwnerType_t               DeviceOwnerType;                                /* ʹ�������� */
	AFCTime64_t                     FileCreationTime;                               /* EOD ��������ļ��Ĵ���ʱ�� */
	string                          FileName<32>;                                   /* EOD��������ļ��� */
	FileVersionID_t                 ComponentEODVersionID;                          /* EOD��������汾�ţ���С�汾�� */	
};

%// ---- EOD ���������ļ��ṹ����
struct EODMasterConfigFile_t {    
	FileHeaderTag_t			FileHeaderTag;					/* �����ļ������� */
	AFCTime64_t                     FileCreationTime;                               /* �ļ��Ĵ���ʱ�� */
	string				FileName<32>;					/* �ļ��� */                         
	FileVersionID_t                 EODVersionID;                                   /* ������汾�� */
	AFCTime64_t                     EODVersionEffectTime;				/* ������汾��Чʱ�� */
	EODComponent_t                  EODComponents<>;                                /* EOD��������ļ��б� */
	MD5_t                           MD5Value;	                                /* MD5ǩ�� */
};

%// ---- EOD ��������ļ��ṹ����
struct EODFile_t {                                                              
	EODComponentType_t              EODComponentType;                               /* EOD����������� */
	AFCTime64_t                     FileCreationTime;                               /* �ļ��Ĵ���ʱ�� */
	string				FileName<32>;					/* �ļ��� */           
	DeviceOwnerType_t               DeviceOwnerType;                                /* ʹ�������� */
	FileVersionID_t                 ComponentEODVersionID;                          /* EOD��������汾�ţ���С�汾�� */	
	EODParameter_t                  EODParameter;                                   /* EOD����������� */
	MD5_t                           MD5Value;	                                /* MD5ǩ�� */
};
%// ---------------------------------------------------------------------------------------


%// ---------------------------------------------------------------------------------------
%// ---- ������Ӫ��������
%// ---------------------------------------------------------------------------------------

%// ---- ȫ����ϸ�������ṹ
struct BLKACCFull_t{                                                                    
	string                          TicketLogicID<20>;                             /* �߼����� */
	BLKActionType_t                 BLKActionType;                                 /* ����������ʽ */
};

%// ---- ������ϸ�������ṹ
struct BLKACCInc_t{                                                            
	string                          TicketLogicID<20>;                             /* �߼����� */
	BLKActionType_t                 BLKActionType;                                 /* ����������ʽ */
        IncBlackType_t                  IncBlackType;                                  /* ������������־(0��������  1��������) */
};

%// ---- �Ŷκ������ṹ
struct BLKACCSect_t{                                                   
	string                          StartTicketLogicID<20>;                        /* ��ʼ�߼����� */
	string                          EndTicketLogicID<20>;                          /* ��ֹ�߼����� */	
	BLKActionType_t                 BLKActionType;                                 /* ����������ʽ */	
};


%// ---- Ա����ȫ����ϸ�������ṹ
struct BLKStaffFull_t{                                                
	string                          TicketLogicID<20>;                             /* �߼����� */
	BLKActionType_t                 BLKActionType;                                 /* ����������ʽ */
};

%// ---- ����Ʊ���������ṹ
struct SJTRecycleCond_t{                                                         
	Date2_t                         InitializeDate;                                /* �״γ�ʼ������ */
	InitBatchCode_t                 InitBatchCode;                                 /* �״γ�ʼ�����κ� */	
};

%// ---- ģʽ�����ṹ
struct WaiveDate_t{                                                      
 	ModeCode_t                      ModeCode;                                      /* ģʽ���� */  
	Date2_t                         BusinessDay;                                   /* ��Ӫ�� */
	LineID_t                        LineID;                                        /* ��·��� */	
	StationID_t                     StationID;                                     /* ��վ��� */	
	AFCTime64_t                     StartModeOccurTime;                            /* ����ģʽ������ʼʱ�� */
	AFCTime64_t                     EndModeOccurTime;                              /* ����ģʽ��������ʱ�� */
};

%// Ʊ��һ������ṹ
struct TicketFirstDirClass_t{                                                   
    TicketDirClassID_t              TicketDirClassID;                              /* Ʊ��һ�������� */  
    UnicodeString_t                 TicketFamilyName;                              /* Ʊ��һ���������� */	
};

%// Ʊ����������ṹ
struct TicketSecondDirClass_t{                                                  
    TicketDirClassID_t              TicketFirstDirClassID;			   /* Ʊ��һ�������� */  
    TicketDirClassID_t              TicketSecondDirClassID;                        /* Ʊ������������ */  
    UnicodeString_t                 TicketFamilyName;                              /* Ʊ�������������� */	
};

%// Ʊ������ṹ
struct TicketCatalog_t{                                                           
	TicketCatalogID_t               TicketCatalogID;                           /* Ʊ��Ŀ¼��� */ 
	UnicodeString_t                 TicketCatalogName;                         /* Ʊ��Ŀ¼���� */	
	ValueCent_t                     TicketValue;                               /* ��Ʊ��� */	
	MultiRideNumber_t		MultiRideNumber;	                   /* Ʊ������ */  
	TicketDirClassID_t              TicketFirstDirClassID;                     /* Ʊ��һ�������� */  
        TicketDirClassID_t              TicketSecondDirClassID;                    /* Ʊ������������ */
        ChipType_t                      ChipType;                                  /* оƬ���� */
        UnicodeString_t                 TicketUnit;                                /* ������λ */	
        UnicodeString_t                 TicketColor;                               /* Ʊ����ɫ */	
        UnicodeString_t                 TicketUse;                                 /* Ʊ����; */	
        Boolean_t                       EnableTag;                                 /* ��Ч��־ 0��Ч��1��Ч */
};

%// ---- Ʊ��Ŀ¼��Ϣ�ṹ
struct TicketDirCatalog_t{                                                   
	TicketFirstDirClass_t           TicketFirstDirClasses<>;                       /* Ʊ��һ�������б� */	
	TicketSecondDirClass_t          TicketSecondDirClasses<>;                      /* Ʊ�����������б� */	
	TicketCatalog_t			TicketCatalogs<>;							   /* Ʊ��Ŀ¼�б� */	
};

%// �豸�ڵ�ṹ
struct DeviceNode_t{                                                       
	DeviceID_t			DeviceID;                                      /* �豸�ڵ��� */
	UnicodeString_t			DeviceName;				       /* �豸���� */	
	DeviceType_t                    DeviceType;                                    /* �豸���� */	
	CoordinateUnit_t                DeviceDisplayX;                                /* ������ */
	CoordinateUnit_t                DeviceDisplayY;                                /* ������ */	
	Angle_t                         DeviceDisplayAngle;                            /* ��ʾ�Ƕ� */	
	IPAddress_t                     IPAddress;                                     /* �豸IP��ַ */
	Boolean_t                       EnableFlag;                                    /* ���ñ�� */
};

%// ��վ�豸�ڵ�ṹ
struct StationDeviceNode_t{                                                      
	StationID_t			StationID;                                     /* ��վ��� */
	ResourceFile_t			StationMap;				       /* ��վ��ͼ�ļ� */
	CoordinateUnit_t                StationDisplayX;                               /* ��վ������ */
	CoordinateUnit_t                StationDisplayY;                               /* ��վ������ */	
	Angle_t                         StationDisplayAngle;                           /* ������ʾ�Ƕ� */	
	DeviceNode_t			DeviceNodes<>;                                 /* ��վ�豸�ڵ��б� */	
};

%// ---- ��վ�豸�ڵ����ýṹ
struct StationDeviceNodeConfig_t{                                             
%//	string                        BOMPictureName<32>;                            /* BOMͼƬ�ļ����� */	
%//	string                        TVMPictureName<32>;                            /* TVMͼƬ�ļ����� */
%//	string                        TSMPictureName<32>;                            /* TSMͼƬ�ļ����� */
%//	string                        TCMPictureName<32>;                            /* TCMͼƬ�ļ����� */	
%//	string                        GIPictureName<32>;                             /* ��վ��Ʊ��ͼƬ�ļ����� */	
%//	string                        GOPictureName<32>;                             /* ��վ��Ʊ��ͼƬ�ļ����� */
%//	string                        WGBPictureName<32>;                            /* ˫���Ʊ��ͼƬ�ļ����� */
%//	string                        GBPictureName<32>;                             /* ���Ʊ��ͼƬ�ļ����� */
	LineID_t                      LineID;                                        /* ��·��� */	
        ResourceFile_t                LineMap;                                       /* ��·��ͼ�ļ� */
	StationDeviceNode_t	      StationDeviceNodes<>;			     /* ��վ�豸�ڵ��б� */	

};

%// ---- ��ɫ�����б�
struct RoleFunctionList_t{                                             
	RoleID_t                                 RoleID;                                /* ��ɫ��� */
	EQPFuncID_t                              EQPFuncIDs<>;                          /* ��ɫ�����б� */
};

%// ---- ����Ա������Ϣ�б�
struct OperInfo_t{                                                       
	OperatorID_t                             OperatorID;                            /* �û���� */
	string                                   OperatorPassword<32>;                  /* �û����� */
	Boolean_t                                OperatorEnableTag;                     /* ���ñ�־λ 0�����ã�1���� */
	AFCTime64_t                              StartEnableTime;                       /* ����ʱ�� */
	AFCTime64_t                              EndEnableTime;                         /* ����ʱ�� */
};

%// ---- ����Ա�б�
struct OperatorList_t{                                                       
	OperatorID_t                             OperatorID;                            /* �û���� */
        Accessleve_t	                         Accesslevel;                           /* �û�Ȩ�޵ȼ� */
        RoleID_t                                 OwnRoleIDs<>;                          /* ���ý�ɫ�б� */
	EQPFuncID_t                              EQPFuncID<>;                           /* �����豸Ȩ�޹��� */	
	StationID_t                              OwnStations<>;                         /* ���ó�վ�б� */
	DeviceType_t                             OwnDeviceTypes<>;                      /* �����ն��豸�����б� */
};

%// ---- ����ԱȨ�޲���
struct OperatorRight_t{                                                       
	OperatorList_t                           Operators<>;                           /* ����Ա�б� */
        RoleFunctionList_t                       RoleFunctions<>;                       /* ��ɫ�����б�*/
};


%// ---- ��Ӫ����
union RUNParameter_t switch (RUNComponentType_t RUNComponentType) {             
    case RUN_STATIONEQUNODE:                                                       /* ��վ�豸�ڵ������ļ� */
         StationDeviceNodeConfig_t               StationDeviceNodeConfig;
		    
    case RUN_BLKACCFULLLIST:                                                       /* һƱͨȫ����ϸ�������ļ� */
         BLKACCFull_t                            BLKACCFulls<>;

    case RUN_BLKACCINCRLIST:                                                       /* һƱͨ������ϸ�������ļ� */
	 BLKACCInc_t                             BLKACCIncs<>;

    case RUN_BLKACCFULLSECTLIST:                                                   /* һƱͨȫ���Ŷκ������ļ� */
         BLKACCSect_t                            BLKACCSects<>;

    case RUN_BLKSTAFFFULLLIST:                                                     /* Ա����ȫ����ϸ�������ļ� */
	 BLKStaffFull_t                          BLKStaffFulls<>;

    case RUN_SJTRECYCLE:                                                           /* ����Ʊ���������ļ� */
	 SJTRecycleCond_t                        SJTRecycleConds<>;

    case RUN_WAIVERDATE:                                                           /* ģʽ�����ļ� */
         WaiveDate_t                             WaiveDates<>;

    case RUN_TICKETCATALOG:                                                        /* Ʊ�����Ŀ¼�ļ� */
         TicketDirCatalog_t                      TicketDirCatalog;

    case RUN_OPERINFO:					                            /* ����Ա������Ϣ�ļ� */
         OperInfo_t				 OperInfos<>;

    case RUN_OPERRIGHT:					                            /* ����ԱȨ���ļ� */
         OperatorRight_t		         OperRight;                     
};

%// ---- ��Ӫ���������Ϣ
struct RUNParameterComponent_t{                                          
 	RUNComponentType_t			RUNComponentType;                               /* ��Ӫ����������� */
	string					FileName<32>;                                   /* ��Ӫ��������ļ��� */
	FileVersionID_t                         RUNParameterVersionID;                          /* ��Ӫ��������汾�� */	
};

%// ---- ��Ӫ���������ļ�
struct RUNParameterMasterConfigFile_t {                                   
	FileHeaderTag_t			RUNComponentType;                               /* ��Ӫ�����ļ����� */
	AFCTime64_t                     FileCreationTime;                               /* �����ļ��Ĵ���ʱ�� */
	string				FileName<32>;					/* �ļ��� */  
	RUNParameterComponent_t         RUNParameterComponents<>;                       /* ��Ӫ��������ļ��б� */
	MD5_t                           MD5Value;	                                /* MD5ǩ�� */
};

%// ---- ��Ӫ�����ļ�
struct RUNParameterFile_t {                      
	RUNComponentType_t		RUNComponentType;                               /* ��Ӫ����������� */                              
	AFCTime64_t                     FileCreationTime;                               /* ��Ӫ�����ļ��Ĵ���ʱ�� */
	string				FileName<40>;                                   /* ��Ӫ���ļ��� */
	FileVersionID_t                 RUNParameterVersionID;                          /* ��Ӫ�����汾�� */	
	RUNParameter_t                  RUNParameter;                                   /* ��Ӫ����������� */
	MD5_t                           MD5Value;	                                /* MD5ǩ�� */
};

%// --------------------------------------------------------------------------------------------------------------

%#endif 
%/********************************************** �ļ����� *******************************************************/
