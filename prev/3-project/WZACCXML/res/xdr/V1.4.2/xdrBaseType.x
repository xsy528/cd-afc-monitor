%/*************************************************************************************************************
%
%    ����������·AFCϵͳ����������׼
%
%    Title       : xdrBaseType.x
%   @Version     : 1.2.0
%    Author      : ���Ʒ�
%    Date        : 2016/06/20
%    Description : ��������ֶμ�ϵͳ����
%**************************************************************************************************************/

%/*  �޶���ʱ��¼:
%**************************************************************************************************************
%*   Date          *    Author           *     Changes
%**************************************************************************************************************
%*   2016/06/20    *    ���Ʒ�           *     1.2.0 ��������������·AFCϵͳ����������׼V1.2�����ĵ�
%*   2017/06/20    *    ���Ʒ�           *     �޸�FileHeaderTag_t
%*   2017/08/21    *    �½���           *     DeviceType_t��DT_BOM��DT_TVM��DT_ISM��DT_PCA��DT_ES���ͷֱ��޸�Ϊ0x0A��0x0B��0x0C��0x0D��0x0E
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
%// ---------- �������Ͷ���
%// -------------------------------------------------------------------------------------------------------------

typedef unsigned char       U8_t;

typedef unsigned short      U16_t;

typedef unsigned int        U32_t;

typedef char                S8_t;

typedef short               S16_t;

typedef int                 S32_t;

typedef S32_t               ValueCent_t;					/* ���ڵ�λ���� (���磺1��ʾ1�֣�10��ʾ1�ǣ�100��ʾ1Ԫ) */

struct ValueCent64_t {									                                    
	S32_t                  ValueCent64_LO;           
	S32_t                  ValueCent64_HI;
};                                                                        /* ���ڵ�λ���� (���磺1��ʾ1�֣�10��ʾ1�ǣ�100��ʾ1Ԫ) ���������Ƚϴ�Ľ��*/


typedef U32_t               MilliSecond_t;					/* ��ʱ�䵥λ������ */

typedef U32_t               Seconds_t;						/* ��ʱ�䵥λ���� */

typedef U32_t               SecondsSinceMidNight_t;			/* ���ʱ�䵥λ����ʾΪ�ݱ��ص���0���������ȡֵ��Χ��0��86400,���籾��1:00AM�ɱ�ʾΪ3600��2:00AM�ɱ�ʾΪ7200*/

%// UTC,ͨ��Э��ʱ��(UTC, Universal Time Coordinated)����ʾ��������ʱ�䣬��ʱ���ȡֵ��ʱ���޹ء�
%// ��������ϵͳ��Ӧ��������ͬ�ķ�ʽ����ȡ��ʱ�䡣
%// 0����ʾ��������1970��1��1��0��0��  
%// 1����ʾ��������1970��1��1��0��1��
%// ת���ɱ�������ʱ��ʱ�����뿼�Ǳ���ʱ��8��Сʱ��ʱ���ڱ�ϵͳ��ĳ�α���ʱ��Ϊ����ʱ�䣬�����ϵͳ�������޹ء� 
%// Time_HI��Ԥ�����ֶΣ���Ŀǰ��ϵͳ�����ΪԤ���ֶ�ʹ�� Time_LO��UTCʱ��
struct AFCTime64_t{                                     /* ͨ��Э��ʱ�� */                            
	S32_t                   Time_HI;
	S32_t                   Time_LO;
};    
%// ����ʱ����ָ�ڸ�������ʱ��Ļ����ϣ�ͬʱ���ǵ���ʱ�ӵĲ��졣
%// ���籱��ΪUTC+8:00ʱ�����ڼ��㱾��ʱ��ʱ����������ϵͳ���ǿ������ñ���ʱ��ģ����Ը�����ϵͳҲ�ṩ����λ�ȡ����ʱ��ķ�����
%// ��������ACC/AFCϵͳ�Ӵ����漰�����ն��豸�ڶ࣬Ϊ��ǿ��ϵͳʱ��ͬ�������б���ʱ��Ļ�ȡ����ȡ��UTCʱ�䣬
%// �ټ��㱱��ʱ�������ֵ�����м��㱾��ʱ�䡣
%// ��ֱֹ�ӻ����ϵͳ�ṩ�ı���ʱ�䡣
%// �������ĺô�����ʹ�������豸����ʱ�ӷ������������ͬ�ı���ʱ�䡣
%// ���뱾��ʱ�����õ�ֵ�޹ء�
%// 0��1970��1��1��
%// 1��1970��1��2��
typedef U16_t               Date2_t;						/* ���� */

%// ��ʱ��Ϊ����ʱ�䣬Ӧ����Ʊ���ڡ�
%// 0����ʾ2000��1��1��0��0��
%// 1����ʾ2000��1��1��0��1��
typedef U32_t               TicketTime32_t;				/* Ʊ���ڲ�ʱ�� */


%// ����ֵ
enum Boolean_t {	
	FalseFlag           = 0, 
	TrueFlag            = 1 
}; 

typedef opaque              UnicodeString_t<>;				/* �����ַ�(Unicode�ַ�) */

typedef U8_t                Percentage_t;			        /* �ٷֱ�(ȡֵ��Χ0--100����ʾΪ0%��100%) */

%// Ʊ��������,��8���ֽ�
%// U64_t�Ӹ�λ����λ8���ֽڷֱ��ʾ���£�
%// 1. ��λ���ֽ�: оƬ����(ChipType_t)
%// 2. ���Ʊ��������Ϊ7���ֽڣ��򣺸�λ�ڶ��ֽڵ����һ���ֽڣ�7���ֽڣ���ʾΪƱ����λ����λ��7���ֽڡ�
%// 3. ���Ʊ��������Ϊ4���ֽڣ���
%//    1����λ���2��3��4�ֽڵ�ȫ����д0x00.
%//    2����λ���5��6��7��8�ֽڵ���дƱ����λ����λ��4���ֽڡ�
%// 4����������Ʊ��������ΪCSN
struct TicketPhyID_t {										/* Ʊ�������� */                                             
	U32_t                  TicketPhyID_LO;
	U32_t                  TicketPhyID_HI;
};



%// �ն˻���� ��6���ֽڣ�ǰ�����ֽ�Ϊ���д���(������) 
struct TerminateNumber_t {									                                                
	U16_t					CityCode;								/* ���д��� */          
	U32_t					SerialID;							/* ���к� */      
};

typedef U32_t               TicketQuantity_t;				/* Ʊ������ */


typedef U32_t               RMBQuantity_t;				/* ֽ�һ�Ӳ�ҵ����� */

typedef U16_t               Minutes_t;					/* ��ʱ�䵥λ������*/
                                                        
%// �ļ�У����
struct MD5_t {										
	U32_t                   MD5Tag[4];
};


%// Ʊ��Ӧ�����к�
%// ��10���ֽ�,�ֱ�Ϊ�����ұ�ʶ(1�ֽ�)+���貿��ʶ(1�ֽ�)+���д���(2�ֽ�)+Ӧ�ô���(2�ֽ�)+�����к�(4�ֽ�)
struct TicketAppSerialID_t {									                                           
	U8_t					CountryTag;								/* ���ұ�ʶ */      
	U8_t					MOCTag;									/* ���貿��ʶ */      
	U16_t					CityCode;								/* ���д��� */      
	U16_t					AppCode;								/* Ӧ�ô��� */      
	U32_t					TicketSerialID;							/* �����к� */      
};

%// �������Ͷ���
enum TransactionType_t {                    
%// һƱͨ���� 	
	TX_SJTSale                              = 0x01, /* ����Ʊ���� */
	TX_NonNamedCPUSale                      = 0x02, /* �Ǽ�����ֵƱ���� */
	TX_NamedCPUSale                         = 0x03, /* ������ֵƱ���� */
	TX_CPUCardAddValue                      = 0x04, /* ��ֵƱ��ֵ */
	TX_SJTRefund                            = 0x05, /* ����Ʊ��ʱ��Ʊ */
	TX_CPUCardImmediateRefund               = 0x06, /* ��ֵƱ��ʱ��Ʊ */
	TX_CPUCardNonImmediateRefund            = 0x07, /* ��ֵƱ�Ǽ�ʱ��Ʊ */
	TX_TicketValidityPeriod                 = 0x08, /* ��ֵƱ���� */
	TX_CPUCardBlock                         = 0x09, /* ��ֵƱ���� */
	TX_CPUCardUnblock                       = 0x0A, /* ��ֵƱ���� */
	TX_TicketSurcharge                      = 0x0B, /* һƱͨ���� */
	TX_TicketEntry                          = 0x0C, /* һƱͨ��վ */
	TX_TicketExit                           = 0x0D, /* һƱͨ��վ*/	
	TX_CPUCardDeduction                     = 0x0E, /* ��ֵƱ�ۿԤ���� */
%// һ��ͨ����
	TX_YKTSale                              = 0x20, /* һ��ͨ������Ԥ���� */
	TX_YKTCardAddValue                      = 0x21, /* һ��ͨ��ֵ��Ԥ���� */
	TX_YKTCardBlock                         = 0x22, /* һ��ͨ������Ԥ���� */
	TX_YKTCardUnblock                       = 0x23, /* һ��ͨ������Ԥ���� */
	TX_YKTCardSurcharge                     = 0x24, /* һ��ͨ���� */
	TX_YKTCardEntry                         = 0x25, /* һ��ͨ��վ */
	TX_YKTCardExit                          = 0x26, /* һ��ͨ��վ*/	
	TX_YKTCardDeduction                     = 0x27, /* һ��ͨ�ۿԤ���� */
%//  �ֻ�������
	TX_MobileSurcharge                      = 0x40, /* �ֻ������� */
	TX_MobileEntry                          = 0x41, /* �ֻ�����վ */
	TX_MobileExit                           = 0x42, /* �ֻ�����վ*/	
	TX_MobileDeduction                      = 0x43, /* �ֻ����ۿ� ��Ԥ���� */
%// ���п�����
	TX_BankCardDeduction                    = 0x60, /* ���п��ۿԤ���� */
%// ES����
	TX_SJTInit				= 0x80, /* ����Ʊ��ʼ�� */
	TX_CPUCardInit				= 0x81, /* ��ֵƱ��ʼ�� */
	TX_TicketCancel				= 0x82, /* Ʊ��ע�� */
	TX_CPUCardPersonal			= 0x83, /* ��ֵƱ���Ի� */
	TX_PrePaySJTOffset			= 0x84  /* Ԥ��ֵ����Ʊ���� */
};

%// EOD����������Ͷ���
enum EODComponentType_t {                       
	EOD_SYSTEM				= 0x21, /* EOD: ϵͳ���� */
	EOD_SYSNetwork			        = 0x22, /* EOD: ·����Ϣ���� */
	EOD_SYSCalendar                         = 0x23, /* EOD: �������� */
	EOD_SYSFare                             = 0x24, /* EOD: ���ʱ���� */
	EOD_SYSTicket                           = 0x25, /* EOD: ��Ʊ���� */	
	EOD_Concession                          = 0x26, /* EOD: �Żݷ������� */	
	EOD_AGM				        = 0x27, /* EOD: AGM�豸���� */
        EOD_RG				        = 0x28, /* EOD: RGͨ�п��Ʋ��� */
	EOD_BOM				        = 0x29, /* EOD: BOM�豸���� */
	EOD_TVM				        = 0x2A, /* EOD: TVM�豸���� */
        EOD_TVM_GUI                             = 0x2B, /* EOD: TVM������� */
	EOD_ISM				        = 0x2C, /* EOD: ISM���� */
	EOD_PCA				        = 0x2D, /* EOD: PCA���� */
        EOD_SLESoft                             = 0x2E, /* EOD: �豸������� */
        EOD_TPSoft                              = 0x2F  /* EOD: TP������� */
};

%// ��Ӫ���Ʋ������Ͷ���
enum RUNComponentType_t {                         
        RUN_BLKACCFULLLIST                      = 0x41, /* һƱͨȫ����ϸ������ */
	RUN_BLKACCINCRLIST                      = 0x42, /* һƱͨ������ϸ������ */
	RUN_BLKACCFULLSECTLIST                  = 0x43, /* һƱͨȫ���Ŷκ����� */	
	RUN_BLKYKTFULLLIST                      = 0x44, /* һ��ͨȫ����ϸ��������Ԥ���� */	
	RUN_BLKSTAFFFULLLIST                    = 0x45, /* Ա����ȫ����ϸ������ */
	RUN_SJTRECYCLE                          = 0x46, /* ����Ʊ�������� */
	RUN_WAIVERDATE                          = 0x47, /* ģʽ���� */
	RUN_TICKETCATALOG                       = 0x48, /* Ʊ�����Ŀ¼ */
	RUN_STATIONEQUNODE                      = 0x49, /* ��վ�豸�ڵ�������Ϣ */
	RUN_OPERINFO                            = 0x50, /* ����Ա������Ϣ�ļ� */
	RUN_OPERRIGHT				= 0x51  /* ����ԱȨ���ļ� */
	
};

%// �ļ�������
enum FileHeaderTag_t {                          
	FILE_YPT_TRANSACTION                   = 0x01,  /* һƱͨ�����ļ� */			
	FILE_YKT_TRANSACTION                   = 0x02,  /* һ��ͨ�����ļ� */
	FILE_MOBILE_TRANSACTION                = 0x03,  /* �ֻ��������ļ� */
	FILE_BANK_TRANSACTION                  = 0x04,  /* ���п������ļ� */
        FILE_AGM_AUDIT_REGISTER                = 0x05,  /* AGM����ļ� */
	FILE_BOM_AUDIT_REGISTER                = 0x06,  /* BOM����ļ� */
	FILE_TVM_AUDIT_REGISTER                = 0x07,  /* TVM����ļ� */
	FILE_ISM_AUDIT_REGISTER                = 0x08,  /* ISM����ļ� */
	FILE_PCA_AUDIT_REGISTER                = 0x09,  /* PCA����ļ� */
	FILE_ES_TRANSACTION		       = 0x0A,  /* ES�����ļ� */
	FILE_ES_ENCODING		       = 0x0B,  /* ES���뱨���ļ� */

	FILE_EOD_CONTROL                       = 0x20,  /* EOD���������ļ� */
	FILE_EOD_SYSTEM			       = 0x21,  /* EOD: ϵͳ�����ļ� */
	FILE_EOD_SYSNetwork		       = 0x22,  /* EOD: �������˲����ļ� */
	FILE_EOD_SYSCalendar                   = 0x23,  /* EOD: ����ʱ������ļ� */
	FILE_EOD_SYSFare                       = 0x24,  /* EOD: ���ʱ�����ļ� */
	FILE_EOD_SYSTicket                     = 0x25,  /* EOD: ��Ʊ�����ļ� */	
	FILE_EOD_Concession                    = 0x26,  /* EOD: �Żݷ��������ļ� */	
	FILE_EOD_AGM			       = 0x27,  /* EOD: AGM�豸�����ļ� */
        FILE_EOD_RG                            = 0x28,  /* EOD: RGͨ�п��Ʋ����ļ� */
	FILE_EOD_BOM			       = 0x29,  /* EOD: BOM�豸�����ļ� */
	FILE_EOD_TVM			       = 0x2A,  /* EOD: TVM�豸�����ļ� */
        FILE_EOD_TVM_GUI                       = 0x2B,  /* EOD: TVM��������ļ� */
	FILE_EOD_ISM			       = 0x2C,  /* EOD: ISM�����ļ� */
	FILE_EOD_PCA			       = 0x2D,  /* EOD: PCA�����ļ� */
        FILE_EOD_SLESoft                       = 0x2E,  /* EOD: �豸�����������ļ� */
	FILE_EOD_TPSoft                        = 0x2F,  /* EOD: TP�����������ļ� */

	FILE_AFC_MAIN_CONTROL                  = 0x40,  /* AFC��Ӫ�����ļ� */
	FILE_BLK_ACC_FULL_LIST                 = 0x41,  /* һƱͨȫ����ϸ�������ļ� */
	FILE_BLK_ACC_INCR_LIST                 = 0x42,  /* һƱͨ������ϸ�������ļ� */
	FILE_BLK_ACC_FULL_SECT                 = 0x43,  /* һƱͨȫ���Ŷκ������ļ� */
	FILE_BLK_YKT_FULL_LIST                 = 0x44,  /* һ��ͨȫ����ϸ�������ļ���Ԥ���� */
	FILE_BLK_STAFF_FULL_LIST               = 0x45,  /* Ա����ȫ����ϸ�������ļ� */
	FILE_SJT_RECYCLE                       = 0x46,  /* ����Ʊ���������ļ� */
	FILE_WAIVERDATE                        = 0x47,  /* ģʽ�����ļ� */
	FILE_TICKET_CATALOG                    = 0x48,  /* Ʊ�����Ŀ¼�ļ� */
        FILE_STATION_EQUNODE_CONFIG            = 0x49,  /* ��վ�豸�ڵ�������Ϣ�ļ� */
        FILE_OPERINFO                          = 0x50,  /* ����Ա������Ϣ�ļ� */
	FILE_OPERRIGHT			       = 0x51,  /* ����ԱȨ���ļ� */
	
	FILE_STOCK_INOUT_BILL                  = 0x60,  /* ������嵥�ļ� */
	FILE_ENDOFDAY_STOCK_SANPSHOT           = 0x61,  /* ���տ������ļ� */
	FILE_ENDOFDAY_STOCK_DIFF               = 0x62,  /* ���տ������ļ� */
	FILE_TRANS_ACCOUNT                     = 0x63,  /* ���׶����ļ� */
	FILE_DOUBTFULDEAL_RESULT               = 0x64,  /* ���ɽ��״����ļ� */
	FILE_COMPARISON_WITH_YKT               = 0x65,  /* �����񿨶����ļ� */
	FILE_COMPARISON_WITH_MOBILE            = 0x66,  /* ���ƶ������ļ� */
	FILE_COMPARISON_WITH_BANK              = 0x67   /* �����������ļ� */
};

%// �Ա�
enum Gender_t {                        
	GENDER_FEMALE                          =  0x01,  /* Ů�� */
	GENDER_MANKIND                         =  0x02,  /* ���� */
	GENDER_UNKNOWN                         =  0x03   /* δ֪ */
};

%// �豸����
enum DeviceType_t {                                 
	DT_ACC_SERVER                         =  0x01,  /* ACC������ */
        DT_SC_SERVER                          =  0x02,  /* SC������ */
        DT_MC_SERVER                          =  0x03,  /* MC������ */
	DT_ACC_WS                             =  0x04,  /* ACC����վ */
        DT_SC_WS                              =  0x05,  /* SC����վ */
        DT_MC_WS                              =  0x06,  /* MC����վ */
        DT_ENG                                =  0x07,  /* ��վAGM */
        DT_EXG                                =  0x08,  /* ��վAGM */
	DT_RG                                 =  0x09,  /* ˫��AGM */
	DT_BOM                                =  0x0A,  /* BOM */
	DT_TVM                                =  0x0B,  /* TVM */
	DT_ISM                                =  0x0C,  /* ISM */
	DT_PCA                                =  0x0D,  /* PCA */
	DT_ES                                 =  0x0E   /* ES*/
};


%// ��������
enum DateTypeID_t{                              
	DTI_WORKDAY                                = 0x01,  /* ���������� */
	DTI_SATURDAY                               = 0x02,  /* ������ */
	DTI_SUNDAY                                 = 0x03,  /* ������ */
	DTI_SPECIALDAY                             = 0x04   /* �������� */
};


%// оƬ����
enum ChipType_t{                       
	CT_ULTRALIGHT                               = 0x01,  /* Ultra Light�� */
	CT_METROCPU                                 = 0x02,  /* ������·���е�CPU�� */
	CT_CITIZENYKT                               = 0x03,  /* һ��ͨ�� */
	CT_MOBILE                                   = 0x04   /* �ֻ��� */
};

%// Ʊ�ִ���
enum TicketFamilyType_t{                          
	TF_SJT                                    = 0x01,  /* ����Ʊ */
	TF_EXIT                                   = 0x02,  /* ��վƱ */
	TF_MILE                                   = 0x03,  /* �Ƴ�Ʊ */
	TF_TIME                                   = 0x04,  /* ��ʱƱ */
	TF_RIDE                                   = 0x05,  /* �ƴ�Ʊ */
	TF_STAFF                                  = 0x06,  /* Ա��Ʊ */
	TF_CITIZEN                                = 0x07,  /* ���� */
	TF_MOBILE                                 = 0x08   /* �ֻ��� */
};

%// Ʊ����Ч�ڷ�ʽ
enum DurationMode_t{                                  
	DM_NOTCHECK                               = 0x00,  /* ��Ʊ����Ч�ڲ������ */
	DM_FIRSTUSERAFTER                         = 0x01,  /* ��Ʊ����һ��ʹ�ú��һ��ʱ������Ч */
	DM_STARTANDEND                            = 0x02,  /* ��Ʊ������ʱ�趨����ֹ���� */
	DM_SELLAFTER                              = 0x03,  /* ��Ʊ������֮����һ��ʱ������Ч */
	DM_LASTTIMEAFTER                          = 0x04   /* ���ϴ�ʹ������֮���һ��ʱ������Ч */
};


%//�Żݷ�ʽ
enum ConcessionType_t                    
{
	CTYPE_NoConcession                           = 0,		/* ���Ż� */
	CTYPE_RepeatConcession                       = 1,		/* �ظ��Ż� */
	CTYPE_JoinConcession                         = 2,		/* �����Ż� */
	CTYPE_PileConcession                         = 3		/* �ۻ��Ż� */
};


%// ��������
enum ComType_t{											
	COM_COMM_CONNECT_APPLY					= 0x1001,   /* ͨѶ�������� */
	COM_PING						= 0x1101,   /* Ӧ�ò�PING */
	COM_TEXT						= 0x1102,   /* �ı���Ϣ */
	COM_SLE_CONTROL_CMD					= 0x1103,   /* �豸�������� */
	COM_TIME_SYNC						= 0x1104,   /* ǿ��ʱ��ͬ������ */

	COM_EVENT_UPLOAD					= 0x1201,   /* �豸�¼��ϴ� */
	COM_QUERY_EOD_VERSION					= 0x1202,   /* ��ѯ�¼������汾 */
	COM_QUERY_FILE_VERSION				        = 0x1203,   /* ��ѯ���������ļ��汾 */
	COM_QUERY_TRANS_SN					= 0x1205,   /* ��ѯ�豸������ˮ�� */
	COM_UPLOAD_EOD_VERSION					= 0x1206,   /* �豸�����汾�仯�ϴ� */
	COM_UPLOAD_FILE_VERSION					= 0x1207,   /* �豸���������ļ��汾�ϴ� */
	COM_UPLOAD_SAM						= 0x1208,   /* �豸SAM��Ϣ�ϴ� */
	COM_UPLOAD_SN						= 0x1210,   /* �豸������ˮ���ϴ� */
        COM_EndTime_SET                                          = 0x1211,   /* ������Ӫ����ʱ�� */
	
	COM_OPERATOR_WORK					= 0x1301,   /* ����Ա��¼����ͣ���ָ����˳��Ȳ��� */
	COM_TICKETBOX_OPERATE					= 0x1303,   /* Ʊ����������ϴ� */
	COM_BANKNOTEBOX_OPERATE					= 0x1304,   /* ֽ��Ǯ����������ϴ� */
	COM_COINBOX_OPERATE					= 0x1305,   /* Ӳ��Ǯ����������ϴ� */
	COM_COINBOX_CLEAR					= 0x1306,   /* TVMӲ��Ǯ����������ϴ� */
        COM_NOTEBOX_CLEAR					= 0x1307,   /* ֽ��Ǯ����������ϴ� */
	COM_CASHBOX_QUERY					= 0x1308,   /* ��ѯ�豸Ǯ������ */
	COM_CASHBOX_UPLOAD					= 0x1309,   /* �豸Ǯ�������ϴ� */

	COM_FTP_APPLY						= 0x1401,   /* FTP���� */
	COM_FILE_UPDATE_NOTIFY					= 0x1402,   /* �ļ�����֪ͨ */
	COM_CURFILE_UPLOAD_NOTIFY				= 0x1403,   /* ��ǰ�ļ��ϴ�֪ͨ */
	COM_SPECFILE_UPLOAD_NOTIFY				= 0x1404,   /* ָ���ļ��ϴ�֪ͨ */

	COM_SEND_EMERGENCY_MODE					= 0x1501,   /* ����/���������Ӫģʽ */
	COM_SEND_OTHER_MODE					= 0x1502,   /* ����������Ӫģʽ���� */
	COM_MODE_STATUS_UPLOAD					= 0x1503,   /* ģʽ״̬�ϴ� */
	COM_MODE_CHANGE_BROADCAST				= 0x1504,   /* ģʽ����㲥 */
	COM_MODE_QUERY						= 0x1505,   /* ��վģʽ��ѯ */


	COM_TICKET_ALLOC_APPLY					= 0x1601,   /* Ʊ���������� */
	COM_TICKET_ALLOC_ORDER					= 0x1602,   /* Ʊ���������� */
	COM_UPLOAD_TICKET_STOCK_NOTIFY			        = 0x1604,   /* ֪ͨ�¼��ϴ�Ʊ�������Ϣ */
	COM_UPLOAD_TICKET_STOCK					= 0x1605,   /* �ϴ�Ʊ�������Ϣ */

	COM_UPLOAD_TICKET_INCOME				= 0x1606,   /* �ϴ�Ʊ��������Ϣ */
	COM_UPLOAD_TICKET_FILLINGMONEY				= 0x1607,   /* �ϴ��̿����Ϣ */

	COM_BOM_ADDVALUE_AUTH					= 0x1701,   /* BOM���߳�ֵ��֤ */
        COM_BOM_ADDVALUE_AFFIRM					= 0x1702,   /* BOM���߳�ֵȷ�� */
	COM_PERSONAL_CARD_APPLY                                 = 0x1703,   /* ���Ի������� */
	COM_NONIMMEDIATE_REFUND_APPLY                           = 0x1704,   /* �Ǽ�ʱ�˿����� */
	COM_NONIMMEDIATE_REFUND_QUERY                           = 0x1705,   /* �Ǽ�ʱ�˿��ѯ */
	COM_TICKET_LOST                                         = 0x1706,   /* Ʊ����ʧ */
	COM_TICKET_DISLOST					= 0x1707,   /* Ʊ����� */
	COM_NONIMMEDIATE_REFUND_SEND			        = 0x1708,   /* �Ǽ�ʱ�˿���Ϣ�·� */
	COM_CARD_ACCOUNT_QUERY					= 0x1709,   /* Ʊ���ʻ���ѯ */
	COM_CARD_ACCOUNT_SEND					= 0x1710,   /* Ʊ���˻���Ϣ�·� */

        COM_STATION_MAINTAIN_APPLY				= 0x1801,   /* ��վ�������� */
	COM_STATION_MAINTAIN_AFFIRM				= 0x1802,   /* ��վά�޽��ȷ�� */

	COM_UPDATE_PASSWORD					= 0x1901,	/* ����Ա������� */

	COM_ES_LOGIN						= 0x1A01,	/* �豸ǩ������ */
	COM_ES_LOGOUT						= 0x1A02,	/* �豸ǩ�� */
	COM_OPERATOR_LOGIN					= 0x1A03,	/* ����Աǩ������ */
	COM_OPERATOR_LOGOUT					= 0x1A04,	/* ����Աǩ�� */
	COM_WORK_TASK						= 0x1A05,	/* �豸������������ */
	COM_TASK_REPORT						= 0x1A06,	/* �������񱨸� */
	COM_STATUS_REPORT					= 0x1A07	/* �豸״̬���� */
};

%// �����Ӧ���־
enum MessageType_t{									
	MT_REQMESSAGE                                           =0x00,		/*������Ϣ */
	MT_ANSMESSAGE                                           =0x01		/*Ӧ����Ϣ */
};

%// ES������������
enum ESTaskType_t{									
	EST_SJT_INIT                                            =0x01,		/* ����Ʊ��ʼ�� */
	EST_SVT_INIT                                            =0x02,		/* ��ֵƱ��ʼ�� */
	EST_TICKET_CANCEL                                       =0x03,	        /* Ʊ��ע�� */
	EST_TICKET_ENCODING                                     =0x04,		/* Ʊ���ּ� */
	EST_TICKET_PERSONAL                                     =0x05,		/* Ʊ�����Ի� */
	EST_SVT_OFFSET                                          =0x06,		/* Ԥ��ֵ����Ʊ���� */
	EST_TASK_CANCEL                                         =0x07		/* ����ȡ�� */
};


%// SAM������
enum SAMType_t{									
	ACC_ISAM  = 0x01,							/* һƱͨ��ֵSAM����Ŀǰϵͳ�����ڴ������͵Ŀ���ΪԤ�� */
   	ACC_PSAM  = 0x02,							/* һƱͨ����SAM�� */
   	YKT_ISAM  = 0x03,							/* һ��ͨ��ֵSAM�� */
   	YKT_PSAM  = 0x04,							/* һ��ͨ����SAM��(���貿��Կ) */
   	ES_SAM    = 0x05,							/* ����Ʊ������SAM�� */
   	PBOC_MAIN = 0x06, 						        /* PBOC����ĸ�� */
   	PBOC_AUTH = 0x07, 						        /* PBOC����ĸ����֤�� */
   	MOBILE_SAM = 0x08,							/* �ֻ�SAM��Ԥ�� */
   	YKT_PSAM_WZ = 0x09							/* һ��ͨ����SAM��(������Կ) */
};

%// Ӳ����ֵ(1-5�� 2-1Ԫ) */
enum CoinFaceValue_t{                       
	FiveJiao_c                            = 0x01,  /* 5�� */
	OneYuan_c                             = 0x02   /* 1Ԫ */

};

%// ֽ����ֵ(1-1Ԫ 2-2Ԫ 3-5Ԫ 4-10Ԫ 5-20Ԫ 6-50Ԫ 7-100Ԫ) */
enum NoteFaceValue_t{                       
	OneYuan_n                             = 0x01,  /* 1Ԫ */
	TwoYuan_n                             = 0x02,  /*2Ԫ */
	FiveYuan_n                            = 0x03,  /*5Ԫ*/
	TenYuan_n                             = 0x04,  /*10Ԫ*/
	TwentyYuan_n                          = 0x05,  /*20Ԫ*/
	FiftyYuan_n                           = 0x06,  /*50Ԫ*/
	HundredYuan_n                         = 0x07  /*100Ԫ*/
};

%// Ǯ����ֵ*/
enum FaceValue_t{           
        FiveJiao                            = 0x01,  /* 5�� */
	OneYuan                             = 0x02,  /* 1Ԫ */
	TwoYuan                             = 0x03,  /*2Ԫ */
	FiveYuan                            = 0x04,  /*5Ԫ*/
	TenYuan                             = 0x05,  /*10Ԫ*/
	TwentyYuan                          = 0x06,  /*20Ԫ*/
	FiftyYuan                           = 0x07,  /*50Ԫ*/
	HundredYuan                         = 0x08  /*100Ԫ*/
};

typedef U8_t               TestFlag_t;						/* ���Ա�־λ (0: �ǲ���Ʊ��1������Ʊ)*/
typedef U8_t               TransferStationID_t;                                 /* ����������� */
typedef U8_t               LineID_t;						/* ��·��� */
typedef U16_t              StationID_t;						/* ��վ��� ���ֽ�U8��ʾLineID_t�����ֽ��ֽڱ�ʾΪ����·�ĳ�վ���*/
typedef U32_t              DeviceID_t;						/* �豸���ID��4���ֽڣ��Ӹ�λ��λ�ֱ��ʾ,��·��U8_t������վ��U8_t�����豸���ͣ�U8_t�����豸�ڳ�վ�ڵı�ţ�U8_t�� */
typedef U32_t              SAMID_t;						/* SAM����ţ�4���ֽ�  U32_t�Ӹ�λ����λ�ֱ��ʾSAMID[0]��SAMID[3] s*/
typedef U32_t              OperatorID_t;					/* ����Ա��� 100000 - 999999 */       
typedef U32_t              BOMShiftID_t;					/* BOM��κ� */
typedef U8_t               TicketFamily_t;					/* Ʊ�ִ��� */
typedef U8_t               TicketType_t;					/* Ʊ��С��(�߼�����)*/
typedef U32_t              SN_t;						/* Ʊ�������������豸�������кš��ļ����кš������������ */
typedef U8_t               ModeCode_t;						/* ��վ��Ӫģʽ���� */
typedef U8_t               ZoneID_t;						/* ������ */
typedef U8_t               SectionID_t;						/* ���α�� */
typedef U8_t               FareTier_t;						/* ��̵ȼ� */
typedef U8_t               TicketFareTypeID_t;				        /* �������ʹ��� */
typedef U8_t               TimeIntervalID_t;				        /* ʱ��α�� */
typedef U8_t               FareGroupID_t;					/* ��Ʊ��������� */
typedef U16_t              CityCode_t;						/* ���д��� */
typedef U16_t              IssuerCode_t;					/* ���������� */
typedef U16_t              RoleID_t;						/* ��ɫ��� */  
typedef U32_t              EQPFuncID_t;						/* ���ܱ�� */

typedef U32_t              Times_t;						/* ������¼ */
typedef U8_t               AreaTicketFlag_t;				        /* ��Χ��־*/
typedef U32_t              BitMap_t;						/* ��·&��վλͼ */
typedef U16_t              Duration_t;						/* ��Ч��(��λ����) */
typedef U32_t              MultiRideNumber_t;				        /* �ƴ�Ʊ�ĳ˳����� */
typedef U8_t               DepreciationCyc_t;					/* �۾ɼ��㵥λ���� */
typedef U8_t               PaymentMode_t;					/* ֧����ʽ */
typedef U8_t               OverstayingMode_t;                                   /* ��ʱ���ʽ  0-�������Ʊ�� 1-��վ��վ���Ʊ�� 2-�̶�ֵ*/
typedef U8_t               InitBatchCode_t;					/* Ʊ����ʼ������ */
typedef U16_t              TicketCatalogID_t;				        /* Ʊ��Ŀ¼��� */ 
typedef U8_t               SoundDisplayID_t;				        /* AGM��ʾ�� */
typedef U8_t               ConcessionalLampID_t;			        /* AGM�ƹ���ʾ��� */
typedef U8_t               PassageMode_t;                                       /* ͨ��ģʽ 0-��վ 1-��վ */
typedef U16_t              CoordinateUnit_t;				        /* ��ʾ������ */
typedef U8_t               BtnIndex_t;						/* ��ť��� */
typedef U8_t               LineZoneSelBtnID_t;				        /* ��·����ѡ��ť��� */
typedef U8_t               TopBoardID_t;				        /* �������ݱ�� */
typedef U8_t               ADItemID_t;						/* ����ļ���� */
typedef U8_t               ADType_t;						/* ������� */
typedef U8_t               PosType_t;						/* λ������ */

typedef U8_t               ConcessionID_t;					/* �Ż�ID */
typedef U8_t               IndustryScope_t;					/* �����Ż���ҵ��Χ */
typedef U16_t              FrequencyWithinMin_t;			        /* �ƹ�����Ƶ�� */
typedef U8_t               LampColorTypeID_t;                                   /* �ƹ���ɫ */

typedef U8_t               JoinConcessionType_t;			        /* �����Żݷ�ʽ */
typedef U8_t               PileConcessionType_t;			        /* �ۻ��Żݷ�ʽ */
typedef U8_t               ConcessionResetType_t;		         	/* �Ż��������� */
typedef U16_t              RecordsInFile_t;					/* �ļ��м�¼�� */
typedef U8_t               DeviceOwnerType_t;				        /* ʹ���豸���� */

typedef U8_t               IncBlackType_t;					/* ������������� */
typedef U8_t               BLKActionType_t;					/* ������������(0�C���ܾ� 1�C���ڳ�վʱ�ܾ� 2�C�ڽ�վ�ͳ�վʱ�ܾ� 3-���ڳ�վʱ�ܾ������������ͽ��� 4-�ڽ�վ�ͳ�վʱ�ܾ������������ͽ���) */
typedef U16_t              TicketDirClassID_t;				        /* Ʊ��Ŀ¼������ */
typedef U16_t              Angle_t;						/* �Ƕ� */
typedef U32_t              IPAddress_t;						/* IP��ַ */
typedef U16_t              TCPPort_t;						/* TCP�˿� */
typedef U8_t               Accessleve_t;                                        /* �û�Ȩ�޵ȼ� */

typedef U32_t              FileVersionID_t;					/* �������ļ��汾�� */
typedef U8_t               EODVersionType_t;			        	/* �汾���� */
typedef U8_t               VersionKind_t;					/* �汾���ʣ�0���Ƕ�д���ϰ汾 1����д���ϰ汾�� */
typedef U8_t               SLEFileVersionID_t;					/* �豸�ļ��汾�� */

typedef U8_t               TicketStatus_t;					/* Ʊ��״̬(0��δ��ʼ���� 1����ʼ����2��������3����Ʊ��4����������5����ʧ) */
typedef U8_t               Paymentmeans_t;					/* ֧����ʽ */
typedef U8_t               LanguageFlag_t;					/* ��Ʊ���Ա�� */ 
typedef U8_t               TransactionStatus_t;				        /* ����״̬ */

typedef U8_t               TicketACCStatus_t;	                                /* ACCƱ���ʺ�״̬ */
typedef U8_t               RefundStatus_t;	                                /* �˿��״̬ */
typedef U8_t               DealResult_t;	                                /* ������ */
typedef U8_t               FailReason_t;	                                /* ʧ��ԭ�� */

typedef U8_t               Faultlevel_t;	                                /* ���ϵȼ�  1-���棻2-�������У�3-ֹͣ����*/
typedef U8_t               MaintainResult_t;	                                /* ά�޽��  1-δ�ޣ�2-�����޸���3-�޸� */
typedef U8_t               IdentificationType_t;		      	        /* ֤������ */
typedef U8_t               PassengerTypeID_t;				        /* �ֿ������ͱ�ʶ */
typedef U8_t               StaffFlag_t;						/* �ֿ���ְ����� */
typedef U8_t               SIMType_t;						/* �ֻ�SIM������ */
typedef U8_t               SIMStatus_t;						/* �ֻ�SIM��״̬ */
typedef U8_t               SurchargeArea_t;					/* �������� */
typedef U8_t               SurchargeCode_t;					/* ���·�ʽ */
typedef U8_t               SJTRecycleFlag_t;				        /* ����Ʊ���ձ�� */
typedef U8_t               DeduceLocation_t;				        /* �۷�λ�� */
typedef U8_t               RefundReason_t;					/* ��Ʊԭ�� */
typedef U8_t               BlockReasonCode_t;				        /* ����ԭ�� */
typedef U8_t               UnblockReasonCode_t;				        /* ����ԭ�� */
typedef U32_t              TAC_t;					        /* ������֤�� */
typedef U8_t               DownLoadCode_t;					/* ������������ */

typedef U8_t               ARFileTag_t;						/* ��Ʒ�ʽ */
typedef U32_t              ARCount_t;						/* AR������ */
typedef U32_t              ContainerID_t;					/* Ʊ��ID */

typedef U16_t              EventCode_t;						/* �¼����� */
typedef U8_t		   EventValue_t;					/* �¼�ֵ */

typedef U8_t               ControlCmd_t;					/* �豸�������� */
typedef U8_t               DeviceStatus_t;					/* �豸״̬ */
typedef U8_t               ParamVersionStatus_t;				/* �����汾״̬ */
typedef U8_t               VersionQueryType_t;					/* �汾��ѯ���� */


typedef U8_t		   EventType_t;						/* �¼����� */
typedef U8_t		   SPCode_t;						/* �����̱��� */
typedef U8_t	           OperateFlag_t;					/* �豸Ʊ�䡢Ǯ��������� */
typedef U32_t	           UploadFileBits_t;					/* �ϴ����ļ�λ��Ϣ */
typedef U8_t		   TicketAllocType_t;				        /* Ʊ���������� */
typedef U32_t              RandomNum_t;						/* ����� */
typedef U8_t		   KeyUse_t;						/* ��Կ��; */
typedef U8_t		   KeyVersion_t;					/* ��Կ�汾 */
typedef U32_t		   MAC_t;						/* MAC�� */
typedef U16_t		   ProtocolVer_t;					/* ����Э���� */
typedef U8_t		   AlgorithmicType_t;					/* ѹ�������㷨 */
typedef U16_t		   PackLength_t;					/* ������ */
typedef U8_t		   MACK_t;						/* Ӧ���� */

typedef U8_t		   FileDealResult_t;					/* �ļ��������������ʴ��붨�壩 */
typedef U8_t		   SettlementStatus_t;					/* �ļ�����״̬��0-0���Ϸ������׿��� 1��MD5ǩ������ 2���豸������ 3��SAM�������� 4����ƥ��Ľ��ױ��� 5������ʧ�� 6��δ֪�쳣�� */
typedef U32_t              RecordsNum_t;					/* ��¼���� */

typedef U8_t               InOutType_t;						/* ���������*/
typedef U8_t               InOutFlag_t;						/* ������ǣ�0����� 1�����⣩*/

typedef U8_t               CoinOperatePos_t;					/* ����λ��(1��������1 2��������2 3��ѭ��������1 4��ѭ��������2 5��Ӳ��Ǯ��1 6��Ӳ��Ǯ��2) */
typedef U8_t               NoteOperatePos_t;					/* ����λ��(1��ֽ��������1 2��ֽ��������2 3���ϱ��� 4��ֽ��Ǯ�� 5-ֽ��ѭ��������) */

typedef U8_t               CoinBoxType_t;
typedef U8_t               NoteBoxType_t;
typedef U8_t               CashBoxType_t;
typedef U8_t               BillType_t;	                                        /* �������� */
typedef U8_t               BillStatus_t;	                                /* ����״̬ */
typedef U8_t               FareType_t;	                                        /* Ʊ������  1-��Ʊ 2-�ֽ� */
typedef U8_t               ExcuteStatus_t;	                                /* �̿�ִ��״̬  0��δ���1�� ���ֲ��2����ɲ��� */
typedef U8_t               FillingMoneyType_t;                                  /* �������� 1�����Σ�2����� */
typedef U8_t               ErrorType_t;                                         /* ������� 1:��ƱԱ�˶ԣ�2��TVM�˶ԣ�3��ISM�˶� */
typedef U8_t               RouterTag_t;						/* ·�ɱ��(0�������շ����� 1�����ڵ����� 2�����ڵ���������в���� 3�����ڵ㼰���ڵ��ϲ�ڵ�)*/
typedef U8_t               AllowRightType_t;					/* Ȩ�޿���*/

typedef U32_t              TaskID_t;						/* �����ʶ*/
typedef U8_t               TaskType_t;						/* ��������*/
typedef U16_t	           TradeCode_t;					        /* ��ҵ���� */
typedef U8_t               AppVersion_t;					/* Ӧ�ð汾 */

typedef U8_t               OperatorProperty_t;					/* ����Ա���ԣ�1������Ա 2��ά����Ա 3��������Ա�� */
typedef U8_t               TaskStatus_t;					/* ��������״̬��0: ������,1:������, 2: �����ѷ��ͣ� */
typedef U8_t               TaskChangeFlag_t;					/* ��������־ */
typedef U8_t               ESWorkStatus_t;					/* ES����״̬ */

typedef U8_t               PatronQueue_t;					/* �˿͹�բ��������� */
typedef S8_t               CardSeries_t;					/* ��ϵ(1��һƱͨ��2��һ��ͨ)*/

%// -------------------------------------------------------------------------------------------------------------

%#ifdef __cplusplus
%}
%#endif
%
%#endif	
%/********************************************** �ļ����� *******************************************************/