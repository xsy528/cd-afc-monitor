%/*************************************************************************************************************
%
%    ����������·AFCϵͳ����������׼
%
%   Title       : xdrESCOM.x
%   @Version     : 1.2.0
%    Author      : ���Ʒ�
%    Date        : 2016/06/20
%   Description : ������Ʊ������ּ���ı���ͨ�Ÿ�ʽ
%**************************************************************************************************************/

%/*  �޶���ʱ��¼:
%**************************************************************************************************************
%*    Date         *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    ���Ʒ�           *     1.2.0 ��������������·AFCϵͳ����������׼V1.2�����ĵ�
%*   2018/03/16    *    ���Ʒ�           *     1.4.4 ESLogin_Ans_t��OperatorLogin_Ans_t��Task_Ans_t�ṹ���Ӧ�����ֶ�
%*   2018/03/16    *    ���Ʒ�           *     1.4.4 SVTInitComm_t�ṹ����InitBatchCode�ֶ�
%*   2018/04/22    *    ���Ʒ�           *     1.4.5 OperatorLogin_Ans_t�ṹɾ��OperatorName�ֶΣ����OperatorID�ֶ�;   
%**************************************************************************************************************/

%#ifndef XDRESCOM_H
%#define XDRESCOM_H
%
%#include "xdrBaseType.h"

%// -------------------------------------------------------------------------------------------------------------
%// ---------- ESͨѶ�ӿ�
%// -------------------------------------------------------------------------------------------------------------

%// ---- �豸ǩ��������
struct ESLogin_Req_t{
	DeviceID_t                              DeviceID;				/* �������ʶ */
	AFCTime64_t                             LoginTime;				/* ǩ��ʱ�� */
};

%// ---- �豸ǩ��Ӧ����
struct ESLogin_Ans_t{
        MACK_t                                  MackCode;			/* Ӧ���� */
	AFCTime64_t                             VerifyTime;			/* У��ʱ�� */
	Seconds_t				PeriodOfStatusReport;	        /* �豸״̬������ */
	Seconds_t				PeriodOfGetTask;		/* ESȡ������ */
};


%// ---- �豸ǩ��������
struct ESLogout_Req_t{
	DeviceID_t                              DeviceID;			 /* �������ʶ */
	OperatorID_t                            OperatorID;                      /* ����Ա��� */
	AFCTime64_t                             LogoutTime;			 /* ǩ��ʱ�� */
};


%// ---- ����Աǩ��������
struct OperatorLogin_Req_t{
	DeviceID_t                              DeviceID;				/* �������ʶ */
	OperatorID_t                            OperatorID;                             /* ����Ա��� */
	string					OperatorPassword<32>;                   /* �û����� */
	AFCTime64_t                             LoginTime;				/* ǩ��ʱ�� */
};

%// ---- ����Աǩ��Ӧ����
struct OperatorLogin_Ans_t{
        MACK_t                                  MackCode;			        /* Ӧ���� */
	OperatorID_t                            OperatorID;                             /* ����Ա��� */
	OperatorProperty_t                      OperatorProperty;		        /* ����Ա���� */
};


%// ---- ����Աǩ��������
struct OperatorLogout_Req_t{
	DeviceID_t                              DeviceID;				/* �������ʶ */
	OperatorID_t                            OperatorID;                             /* ����Ա��� */
	AFCTime64_t                             LogoutTime;				/* ǩ��ʱ�� */
};


%// ---- �豸��������������
struct Task_Req_t{
	DeviceID_t                              DeviceID;				/* �������ʶ */
	Date2_t					TaskDate;				/* ������������� */
};


%// ---- �������Խṹ
struct TaskAttribute_t{
	TaskType_t							TaskType;			/* �������� */
	TaskID_t							TaskID;				/* �����ʶ */
	TicketQuantity_t						TicketQuantity;			/* Ʊ������ */
};	


%// ---- ����Ʊ��ʼ������ṹ
struct TaskOfSJTInit_t{
	TaskAttribute_t				TaskAttribute;          /* �������Խṹ */
	ChipType_t				ChipType;               /* оƬ���� */
	TicketType_t				TicketType;             /* ��Ʊ���� */
	Date2_t					CardInitDate;           /* Ʊ����ʼ������ */
	InitBatchCode_t				InitBatchCode;          /* Ʊ����ʼ������ */
	LanguageFlag_t				LanguageFlag;           /* ���Ա�� */ 
	TestFlag_t				TestFlag;		/* ���Ա�� */
	U32_t					Reserve1;		/* Ԥ��1 */
	U32_t					Reserve2;		/* Ԥ��2 */		
	ValueCent_t				TicketValue;		/* Ʊ����ʼ���/�� */
	FareTier_t                              FareTier;               /* ���ʵȼ����� */
	Date2_t				        ValidStartDate;         /* ��Ч��ʼ���� */
	Date2_t					ValidEndDate;           /* ��Ч�������� */
	Duration_t                              Duration;               /*��Ч����*/
};	

%// ---- ��ֵƱ��ʼ�������ṹ
struct SVTInitComm_t{
	TaskAttribute_t				TaskAttribute;          /* �������Խṹ */
	ChipType_t				ChipType;               /* оƬ���� */
	TicketType_t			        TicketType;             /* ��Ʊ���� */
	TicketCatalogID_t			TicketCatalogID;        /* Ʊ��Ŀ¼ */
	IssuerCode_t				IssuerCode;             /* ���������� */
	TradeCode_t				TradeCode;		/* ��ҵ���� */
	CityCode_t				CityCode;               /*���д���*/
	Date2_t					CardInitDate;           /*Ʊ����ʼ������*/
	InitBatchCode_t				InitBatchCode;          /* Ʊ����ʼ������ */
	TicketFamilyType_t			TicketFamilyType;       /*Ʊ����������*/
	ValueCent_t				TicketDepositValue;     /* Ѻ���� */
	Date2_t					CardValidDate;		/* ����Ч���� */
	U32_t					Reserve;		/* Ԥ�� */
	TestFlag_t				TestFlag;		/* ���Ա�� */
	AppVersion_t				AppVersion;		/* Ӧ�ð汾 */
	Date2_t					AppStartDate;           /* Ӧ����Ч��ʼ���� */
	Date2_t					AppValidDate;           /* Ӧ����Ч�������� */
        AreaTicketFlag_t                        AreaTicketFlag;         /*��Χ��־*/
        BitMap_t                                BitMap1;                /*��·��վλͼ*/    
	BitMap_t                                BitMap2;                /*��վλͼ*/   
	ValueCent_t				TicketValue;		/* Ʊ����ʼ���/�� */
        U32_t					Reserve1;		/* Ԥ��1 */
	U32_t					Reserve2;		/* Ԥ��2 */	
};

%// ---- ��ֵƱ��ʼ������ṹ
struct TaskOfSVTInit_t{
        SVTInitComm_t							SVTInitComm;            /* ��ֵƱ��ʼ�������ṹ */
	
};	

%// ---- Ʊ��ע������ṹ
struct TaskOfTicketCancel_t{
	TaskAttribute_t				TaskAttribute;          /* �������Խṹ */
	ChipType_t				ChipType;               /* оƬ���� */
	TicketType_t				TicketType;             /* ��Ʊ���� */
	TicketCatalogID_t			TicketCatalogID;        /* Ʊ��Ŀ¼ */
	KeyVersion_t				KeyVersion;		/* �汾�� */
	Date2_t                                 UseDate;                /* ʹ��ʱ�� */
	Times_t					UseTimes;		/* ʹ�ô��� */
        U32_t					Reserve1;		/* Ԥ��1 */
	U32_t					Reserve2;		/* Ԥ��2 */	
};	


%// ---- Ʊ���ּ�����ṹ
struct TaskOfTicketEncoding_t{
	TaskAttribute_t				TaskAttribute;          /* �������Խṹ */
	ChipType_t				ChipType;               /* оƬ���� */
	TicketType_t				TicketType;             /* ��Ʊ���� */
	TicketCatalogID_t			TicketCatalogID;        /* Ʊ��Ŀ¼ */
	KeyVersion_t				KeyVersion;		/* �汾�� */
	Times_t					UseTimes;		/* ʹ�ô��� */
        U32_t					Reserve1;		/* Ԥ��1 */
	U32_t					Reserve2;		/* Ԥ��2 */	
	Date2_t					CardInitDate;           /* Ʊ����ʼ������ */
	InitBatchCode_t			        InitBatchCode;          /* Ʊ����ʼ������ */
	ValueCent_t			        TicketValue;		/* �ּ�Ʊ�� */
};


%// ---- ��ֵƱ���Ի��б�ṹ
struct SVTPersonalList_t{
	string					ApplyNo<32>;			    /* ���뵥�� */
	PassengerTypeID_t                       PassengerTypeID;                    /*�ֿ������ͱ�ʶ*/
        StaffFlag_t                             PassengerStaffFlag;                 /*�ֿ���ְ�����*/
	UnicodeString_t                         PassengerCNName;		    /*����*/
	Gender_t				PassengerCNSex;			    /*�Ա�*/
	IdentificationType_t                    IdentificationType;                 /*֤������*/	
	string					IdentificationCode<32>;             /*֤������*/
	string				        StaffId<8>;			    /*Ա����*/	
	AllowRightType_t			AllowRightType;			    /*Ȩ������*/
	MultiRideNumber_t			MultiRideNumber;		    /*��������*/	
	TicketPhyID_t				TicketPhyID;                        /*��Ʊ������*/
	string			                TicketLogicID<20>;                  /*��Ʊ�߼�����*/
};

%// ---- ��ֵƱ���Ի�����ṹ
struct TaskOfSVTPersonal_t{
        SVTInitComm_t					  SVTInitComm;                  /* ��ֵƱ��ʼ�������ṹ */	
	string						  PersonalFileName<28>;	        /* ���Ի������ļ� */
	U8_t					          IssueType;	  	        /* �ƿ���ʽ��0-˳���ƿ���1-�����ƿ� */
};

%// ---- ��ֵƱ���Ի������ļ��ṹ
struct PersonalFile_t{
	TaskID_t					  TaskID;		     /* �����ʶ */
	SVTPersonalList_t				  SVTPersonalList<>;         /* ��ֵƱ���Ի��б�ṹ */
	MD5_t						  MD5Value;		     /*MD5ǩ��*/
};

%// ---- Ԥ��ֵ����Ʊ��������ṹ
struct TaskOfSJTOffset_t{
	TaskAttribute_t				TaskAttribute;          /* �������Խṹ */
	ChipType_t				ChipType;               /* оƬ���� */
	TicketType_t				TicketType;             /* ��Ʊ���� */
	TicketCatalogID_t			TicketCatalogID;        /* Ʊ��Ŀ¼ */
	Date2_t					CardInitDate;           /* Ʊ����ʼ������ */
	InitBatchCode_t				InitBatchCode;        /* Ʊ����ʼ������ */
	KeyVersion_t			        KeyVersion;		/* �汾�� */
	ValueCent_t				TicketValue;	        /* Ʊ����ʼ���/�� */
	Date2_t					ValidStartDate;         /* ��Ч��ʼ���� */
	Date2_t					ValidEndDate;           /* ��Ч�������� */
};

%// ---- ȡ������ṹ
struct TaskOfCancel_t{
	TaskAttribute_t				TaskAttribute;          /* �������Խṹ */
	TaskID_t				CancelTaskIDList<>;	/* ��ȡ���������ʶ */	
};

%// ---- ��������ṹ
union Task_t switch (ESTaskType_t ESTaskType) {             
	case EST_SJT_INIT:										/* ����Ʊ��ʼ�� */
	     TaskOfSJTInit_t			TaskOfSJTInit;						

	case EST_SVT_INIT:										/* ��ֵƱ��ʼ�� */
             TaskOfSVTInit_t			TaskOfSVTInit;	
									
	case EST_TICKET_CANCEL:									        /* Ʊ��ע�� */
  	     TaskOfTicketCancel_t		TaskOfTicketCancel;		
								
	case EST_TICKET_ENCODING:								        /* Ʊ���ּ� */
	     TaskOfTicketEncoding_t	        TaskOfTicketEncoding;						

	case EST_TICKET_PERSONAL:									/* Ʊ�����Ի� */
	     TaskOfSVTPersonal_t		TaskOfSVTPersonal;						

	case EST_SVT_OFFSET:										/* Ԥ��ֵ����Ʊ���� */
	     TaskOfSJTOffset_t		        TaskOfSJTOffset;								
		   
	case EST_TASK_CANCEL:										/* ����ȡ�� */
	     TaskOfCancel_t			TaskOfCancel;									
};

%// ---- �豸��������Ӧ����
struct Task_Ans_t{
	MACK_t                                  MackCode;			        /* Ӧ���� */
	DeviceID_t                              DeviceID;				/* �������ʶ */
	TaskStatus_t			        TaskStatus;				/* ��������״̬ */
	RecordsNum_t				TaskNumber;				/* ������ */
	Task_t					Task<>;					/* �������� */
};

%// ---- �������񱨸�ṹ
struct TaskReport_Req_t{
	DeviceID_t                              DeviceID;		              /* �������ʶ */
	OperatorID_t                            OperatorID;                           /* ����Ա��� */
	TaskAttribute_t				TaskAttribute;                        /* �������Խṹ */
	TaskChangeFlag_t                        TaskChangeFlag;                       /* ��������� */
	ChipType_t				ChipType;                             /* оƬ���� */
	TicketType_t				TicketType;                           /* ��Ʊ���� */
	TicketCatalogID_t			TicketCatalogID;                      /* Ʊ��Ŀ¼ */
	TicketQuantity_t			CompleteQuantity;                     /* ������� */
	TicketQuantity_t			ValidTicketQuantity;                  /* �ɹ�������� */
	TicketQuantity_t			InvalidTicketQuantity;		      /* ��Ʊ���� */ 
	AFCTime64_t				TaskStartTime;                        /* ����ʼʱ�� */
	AFCTime64_t				TaskEndTime;                          /* �������ʱ�� */
};


%// ---- �豸״̬����ṹ
struct StatusReport_Req_t{
	DeviceID_t                              DeviceID;				/* �������ʶ */
	OperatorID_t                            OperatorID;                             /* ����Ա��� */
	ESWorkStatus_t				ESWorkStatus;			        /* �豸����״̬ */
	TaskID_t				CurrentTaskID;			        /* ��ǰ�����ʶ */
	TaskType_t				CurrentTaskType;		        /* ��ǰ�������� */
	TicketQuantity_t			TicketQuantity;			        /* ��ǰ������ */
	TicketQuantity_t			CompleteQuantity;                       /* ��ǰ����������� */
	TicketQuantity_t			ValidTicketQuantity;		        /* ��ǰ����ɹ�������� */ 
	TicketQuantity_t			InvalidTicketQuantity;		        /* ��ǰ�����Ʊ���� */ 
	AFCTime64_t				TaskStartDate;                          /* ��ǰ����ʼʱ�� */
	AFCTime64_t				TaskEndDate;                            /* ��ǰ�������ʱ�� */
};


%// ---- MACKӦ����
struct ESMack_Ans_t{
	MACK_t                                  MackCode;							/* Ӧ���� */
};	
	

%// ---- ������
union ESREQMessage_t switch (ComType_t ComType) {             
	case COM_ES_LOGIN:										 /* (1A01) �豸ǩ������ */
	     ESLogin_Req_t			ESLogin_Req;						

	case COM_ES_LOGOUT:										 /* (1A02) �豸ǩ�� */
	     ESLogout_Req_t			ESLogout_Req;	
									
	case COM_OPERATOR_LOGIN:							                 /* (1A03) ����Աǩ������ */
  	     OperatorLogin_Req_t		OperatorLogin_Req;							
	
	case COM_OPERATOR_LOGOUT:									/* (1A04) ����Աǩ�� */
	     OperatorLogout_Req_t		OperatorLogout_Req;						

	case COM_WORK_TASK:										/* (1A05)�豸������������ */
	     Task_Req_t			        Task_Req;						

	case COM_TASK_REPORT:										/* (1A06) �������񱨸� */
	     TaskReport_Req_t			TaskReport_Req;						

	case COM_STATUS_REPORT:										/* (1A07) �豸״̬���� */
	     StatusReport_Req_t			StatusReport_Req;					
};


%// ---- Ӧ����
union ESANSMessage_t switch (ComType_t ComType) {     
	case COM_ES_LOGIN:										/* (1A01) �豸ǩ������ */
             ESLogin_Ans_t			ESLogin_Ans;						
									
	case COM_OPERATOR_LOGIN:									/* (1A03) ����Աǩ������ */
  	     OperatorLogin_Ans_t		OperatorLogin_Ans;		
													
	case COM_WORK_TASK:										/* (1A05)�豸������������ */
	     Task_Ans_t				Task_Ans;										

	default: 											/*  MACKӦ���� */
	     ESMack_Ans_t			Mack_Ans;							
};


%// ---- ����/Ӧ����
union ESComMessage_t switch (MessageType_t MessageType) {             
    case MT_REQMESSAGE:											/* ������ */
        ESREQMessage_t				ESREQMessage;							

    case MT_ANSMESSAGE:											/* Ӧ���� */
        ESANSMessage_t				ESANSMessage;							
};


%// ---- ��ͷ
struct ESPackHead_t{
	ProtocolVer_t                           ProtocolVer;						/* ����Э���� */
	ComType_t				ComType;						/* ��Ϣ������ */
	DeviceID_t				SendDeviceID;						/* ���ͷ���ʶ�� */
	DeviceID_t				ReceiveDeviceID;					/* ���ܷ���ʶ�� */
	RouterTag_t				RouterTag;						/* ·�ɱ�� */
	AFCTime64_t				SendTime;						/* ����ʱ�� */
	SN_t					SessionSN;						/* �Ự��ˮ�� */
	MessageType_t				MessageType;						/* ����Ӧ���־ */
	MACK_t					MACK;							/* Ӧ���� */
	AlgorithmicType_t			AlgorithmicType;					/* ѹ�������㷨 */	
};


%// ---- ��Ϣ���ṹ
struct ESPack_t{
	PackLength_t                            PackLength;                         /* ������ */
	ESPackHead_t                            PackHead;                           /* ��ͷ */
	ESComMessage_t                          PackBody;                           /* ���� */
	MD5_t                                   MD5Value;                           /* MD5����֤��ͷ�Ͱ��� */
};

%// -------------------------------------------------------------------------------------------------------------

%#endif 
%/********************************************** �ļ����� *******************************************************/