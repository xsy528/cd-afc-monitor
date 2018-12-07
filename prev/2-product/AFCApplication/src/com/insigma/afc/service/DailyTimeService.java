/* 
 * 日期：2011-2-15
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.Service;
import com.insigma.commons.util.lang.DateTimeUtil;

public class DailyTimeService extends Service implements IDailyTimeService {

	public Date getYKTSettDay() {
		String sql = "select GET_YKT_SETT_DAY() from dual ";
		return getDay(sql);
	}

	public Date getYPTSettDay() {
		String sql = "select GET_YPT_SETT_DAY() from dual ";
		return getDay(sql);
	}

	public Date getBANKSettDay() {
		String sql = "select GET_BANK_SETT_DAY() from dual ";
		return getDay(sql);
	}

	public Date getQRSettDay() {
		String sql = "select GET_QR_SETT_DAY() from dual ";
		return getDay(sql);
	}

	/**
	 * 一卡通表名:按日期分表，供数据处理使用,以方法实现
	 */
	public String getYKTSettTransDetailTableName() {
		String sql = "select GET_YKT_SETT_TRA_DET_TB_NAME() from dual ";
		try {
			List list = commonDAO.getEntityListSQL(sql);
			return list.get(0).toString();
		} catch (OPException e) {
			throw new ApplicationException("获取一卡通结算日分区表表名失败！", e);
		}
	}

	/**
	 * 一票通表名:按日期分表，供数据处理使用
	 */
	public String getYPTSettTransDetailTableName() {
		String sql = "select GET_YPT_SETT_TRA_DET_TB_NAME() from dual ";
		try {
			List list = commonDAO.getEntityListSQL(sql);
			return list.get(0).toString();
		} catch (OPException e) {
			throw new ApplicationException("获取一票通结算日分区表表名失败！", e);
		}
	}

	public Date getYKTBusiDay() {
		String sql = "select GET_YKT_BUSI_DAY() from dual ";
		return getDay(sql);
	}

	public Date getYPTBusiDay() {
		String sql = "select GET_YPT_BUSI_DAY() from dual ";
		return getDay(sql);
	}

	public java.sql.Date getBusDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		java.sql.Date busDay = new java.sql.Date(c.getTimeInMillis());
		try {
			String sql = "select GET_BUSINESS_DAY() from dual";
			List list = commonDAO.getEntityListSQL(sql);
			if (list != null && !list.isEmpty()) {
				if (list.get(0) instanceof java.sql.Date) {
					busDay = (java.sql.Date) list.get(0);
				} else if (list.get(0) instanceof java.sql.Timestamp) {
					Timestamp t = (Timestamp) list.get(0);
					busDay = new java.sql.Date(t.getTime());
				}
			} else {
				logger.warn("从库表中获取运营日为空，采用默认的运营日。");
			}
		} catch (Exception e) {
			logger.error("获取运营日失败。", e);
		}
		return busDay;
	}

	/**
	 * 获取日期
	 * 
	 * @param sql
	 * @return
	 */
	private Date getDay(String sql) {
		try {
			List list = commonDAO.getEntityListSQL(sql);
			return (Date) list.get(0);
		} catch (OPException e) {
			throw new ApplicationException("获取日期失败！", e);
		}
	}

	/**
	 * 获取当前结算日，即tsy_config表中结算日加1
	 * 
	 * @param taskGroup
	 * @return
	 */
	public Date getCurrSettDay(String taskGroup) {
		try {
			String sql = "GET_HANDLE_DAY(?,?)";
			return (Date) commonDAO.callProcedureForObject4Oracle(sql, java.sql.Types.DATE, taskGroup);
		} catch (Exception e) {
			throw new ApplicationException("获取结算日失败！", e);
		}
	}

	public java.sql.Date getLastSettDay(String taskGroup) {
		Date settDate1 = DateTimeUtil.getDateDiff(getCurrSettDay(taskGroup), -1);
		java.sql.Date settDate2 = new java.sql.Date(settDate1.getTime());
		return settDate2;
	}

	public java.sql.Date getLastBusiDay() {
		Date busiDate1 = DateTimeUtil.getDateDiff(getBusDay(), -1);
		java.sql.Date busiDate2 = new java.sql.Date(busiDate1.getTime());
		return busiDate2;
	}

	public String getBusiStartTime() {
		String sql = "select GET_BUSINESS_STARTTIME_SEC() from dual ";
		try {
			List list = commonDAO.getEntityListSQL(sql);
			return list.get(0).toString();
		} catch (OPException e) {
			throw new ApplicationException("获取运营日开始字符串失败。", e);
		}
	}
}
