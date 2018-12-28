%/*************************************************************************************************************
%
%    ����������·AFCϵͳ����������׼
%
%   @Title       : xdrALS.x
%   @Version     : 1.2.0
%    Author      : ���Ʒ�
%    Date        : 2016/06/20
%   Description  : ����ACC��SC���ļ���ʽ 
%**************************************************************************************************************/

%/*  �޶���ʱ��¼:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    ���Ʒ�           *     1.2.0 ��������������·AFCϵͳ����������׼V1.2�����ĵ�
%*   2018/06/05    *    ���Ʒ�           *     ɾ�����տ������ļ�
%*   2018/08/14    *    ���Ʒ�           *     ����ALSDataFile_t�ṹ����FileHeaderTag_t�ֶ�λ��
%**************************************************************************************************************/

%#ifndef XDRALS_H 
%#define XDRALS_H

%#include "xdrDCP.h"


%// -------------------------------------------------------------------------------------------------------------
%// ---------- SC��ACC�����ļ��ṹ
%// -------------------------------------------------------------------------------------------------------------

%// SC��ACC������Ϣ�ṹ
union ALSMessage_t switch (FileHeaderTag_t TransactionTag) {					  	
	
	case FILE_ENDOFDAY_STOCK_SANPSHOT:											/*���տ������ļ�*/
		TicketStockSnapshotFile_t				TicketStockSnapshotFile;		
	
	case FILE_STOCK_INOUT_BILL:												/*������嵥�ļ�*/
		TicketStockInOutBillFile_t				TicketStockInOutBillFile;		

	case FILE_TRANS_ACCOUNT:												/*���׶����ļ�*/
		TransactionAccountFile_t				TransactionAccountFile;			

	case FILE_DOUBTFULDEAL_RESULT:												/*���ɽ��״����ļ�*/
		TransactionDoubtfulFile_t				TransactionDoubtfulFile;		
	
%//	case FILE_TRANS_CLEAN_RESULT:												/*��������ļ�*/
%//		TransactionClearFile_t					TransactionClearFile;			
};


%// ---- SC��ACC�����ļ��ṹ
struct ALSDataFile_t {														
	FileHeaderTag_t							FileHeaderTag;				/* �ļ����*/
	AFCTime64_t							FileCreationTime;                       /* �ļ�����ʱ�� */
	string								FileName<40>;                           /* �ļ��� */
	ALSMessage_t							ALSMessage;				/* ��Ϣ�ṹ*/
	MD5_t								MD5Value;			        /* MD5ǩ��*/
};

%// -------------------------------------------------------------------------------------------------------------

%#endif
%/********************************************** �ļ����� *******************************************************/



