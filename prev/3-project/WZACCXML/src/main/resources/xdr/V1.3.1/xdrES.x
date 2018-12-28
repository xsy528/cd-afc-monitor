%/*************************************************************************************************************
%
%    ����������·AFCϵͳ����������׼
%
%   @Title       : xdrES.x
%   @Version     : 1.2.0
%    Author      : ���Ʒ�
%    Date        : 2016/06/20
%   Description  : ����ES���ļ���ʽ 
%**************************************************************************************************************/

%/*  �޶���ʱ��¼:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    ���Ʒ�           *     1.2.0 ��������������·AFCϵͳ����������׼V1.2�����ĵ�
%**************************************************************************************************************/

%#ifndef XDRES_H 
%#define XDRES_H

%#include "xdrDCP.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- ES�����ļ��ṹ
%// -------------------------------------------------------------------------------------------------------------

%// ES���׽ṹ
union ESUD_t switch(TransactionType_t TransactionType) {					
	case TX_SJTInit:													/* ����Ʊ��ʼ�� */
	     ESSJTInit_TX                                ESSJTInit;					

	case TX_CPUCardInit:													/* ��ֵƱ��ʼ�� */
	     ESCPUCardInit_TX                            ESCPUCardInit;					

	case TX_TicketCancel:													/* Ʊ��ע�� */
	     ESTicketCancel_TX                           ESTicketCancel;				

	case TX_CPUCardPersonal:												/* ��ֵƱ���Ի� */
	     ESCPUCardPersonal_TX			    ESCPUCardPersonal;		

	case TX_PrePaySJTOffset:											        /* Ԥ��ֵ����Ʊ���� */
	     PrePaySJTOffset_TX			    PrePaySJTOffset;					
};

%// �豸һƱͨ������Ϣ�ṹ
struct ESUDMSG_t {															
	UDComm_t                             UDComm;									/*���׹����ṹ*/
	ESUD_t				     ESUDData;									/*ES�������ݽṹ*/
	TAC_t                                TAC;									/*����TAC��*/
};

%// �豸��Ϣ�ṹ
union ESMessage_t switch (FileHeaderTag_t TransactionTag) {					   	
	case FILE_ES_TRANSACTION:                                                               /*ES������Ϣ�ṹ*/
		ESUDMSG_t									ESUD<>;			

	case FILE_ES_ENCODING:                                                                 /*ES�����ļ��ṹ*/
		TicketEncodingReportFile_t              TicketEncodingReportFile;				
};

%// ---- ES��Ӫ�ļ�ͷ�ṹ
struct ESDataFileHeader_t {															
	FileHeaderTag_t							FileHeaderTag;				/* �ļ����*/
	AFCTime64_t							FileCreationTime;                       /* �ļ�����ʱ�� */
	string								FileName<40>;				/* �ļ��� */
	LineID_t							LineID;					/*��·���*/
	StationID_t							StationID;			        /*��վ���*/
	DeviceID_t							DeviceID;				/*�豸�ڵ���*/
	Date2_t								BusinessDay;				/*��Ӫ����*/
	SN_t								FileSN;					/*�ļ���ˮ��*/
	RecordsInFile_t							RecordsInFile;                          /*�ļ���¼��*/
};

%// ---- ES��Ӫ�ļ��ṹ
struct ESDataFile_t {															
	ESDataFileHeader_t						ESDataFileHeader;                      /* ES��Ӫ�ļ�ͷ�ṹ */
	ESMessage_t							ESMessages;			       /* ES�豸��Ϣ�ṹ*/
	MD5_t								MD5Value;			       /* MD5ǩ��*/
};

%// -------------------------------------------------------------------------------------------------------------

%#endif
%/********************************************** �ļ����� *******************************************************/



