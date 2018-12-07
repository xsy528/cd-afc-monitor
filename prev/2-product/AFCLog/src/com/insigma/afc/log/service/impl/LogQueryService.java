package com.insigma.afc.log.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.constant.LogDefines;
import com.insigma.afc.log.entity.TsyOpLog;
import com.insigma.afc.log.form.LogInfo;
import com.insigma.commons.application.Application;
import com.insigma.commons.collection.ArrayList;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.io.ColumnValuePattern;
import com.insigma.commons.op.ImExportResult;
import com.insigma.commons.op.OPException;
import com.insigma.commons.service.Service;

/**
 * 作用：封装了查询日志的数据库查询操作方法（包括日志数量查询、日志实体查询）
 *
 * @author filefox
 *
 */
public class LogQueryService extends Service implements ILogQueryService {

	public String in(String fieldName, List<?> list) {
		String sqlWhere = "";
		if ((list != null) && (list.size() > 0)) {
			sqlWhere += " and " + fieldName + " in( " + list.get(0);
			for (int i = 1; i < list.size(); i++) {
				sqlWhere += " ," + list.get(i);
			}
			sqlWhere += " )";
		}
		return sqlWhere;
	}

	/**
	 * 获取查询条件的hql，用于拼装完整的日志查询或日志数量查询语句
	 *
	 * @param form
	 * @return
	 */
	private QueryCondition getConditionSql(LogInfo form, List<Integer> modules) {
		List<Object> args = new ArrayList<Object>();
		String hql = "select l.LINE_ID,l.STATION_ID, l.NODE_ID, l.OCCUR_TIME, l.LOG_DESC,l.OPERATOR_ID, l.MODULE_CODE,"
				+ " l.LOG_CLASS,l.IP_ADDRESS from Tsy_Op_Log l where 1=1";
		if (modules != null) {
			if (modules.size() == 0) {
				return null;
			}
			hql += in("l.module_Code", modules);
		}
		if (form.getLogClass() != null) {
			hql += " and l.log_Class=?";
			args.add(form.getLogClass());
		}
		if (form.getStartTime() != null) {
			hql += " and l.occur_Time>=?";
			args.add(new Timestamp(formatStartTime(form.getStartTime()).getTime()));
		}
		if (form.getEndTime() != null) {
			hql += " and l.occur_Time<?";
			args.add(new Timestamp(formatEndTime(form.getEndTime()).getTime()));
		}
		if (form.getOperatorId() != null) {
			hql += " and l.operator_Id=?";
			args.add(form.getOperatorId());
		}
		if (form.getLogDesc() != null) {
			hql += " and l.log_Desc like '%" + form.getLogDesc() + "%'";
		}
		hql += " order by l.occur_Time desc";
		return new QueryCondition(true, hql, args);
	}

	/**
	 * 获取查询条件的hql，用于拼装完整的日志查询或日志数量查询语句
	 *
	 * @param form
	 * @return
	 */
	private QueryCondition getConditionHql(LogInfo form, List<Integer> modules) {
		List<Object> args = new ArrayList<Object>();
		String hql = " from TsyOpLog l where 1=1";
		if (modules != null) {
			if (modules.size() == 0) {
				return null;
			}
			hql += in("l.moduleCode", modules);
		}
		if (form.getLogClass() != null) {
			hql += " and l.logClass=?";
			args.add(form.getLogClass());
		}
		if (form.getStartTime() != null) {
			hql += " and l.occurTime>=?";
			args.add(formatStartTime(form.getStartTime()));
		}
		if (form.getEndTime() != null) {
			hql += " and l.occurTime<?";
			args.add(formatEndTime(form.getEndTime()));
		}
		if (form.getOperatorId() != null) {
			hql += " and l.operatorId=?";
			args.add(form.getOperatorId());
		}
		if (form.getLogDesc() != null) {
			hql += " and l.logDesc like '%" + form.getLogDesc() + "%'";
		}

		return new QueryCondition(hql, args);
	}

	/**
	 * 获取精确到纳秒的起始时间
	 *
	 * @param startTime
	 * @return
	 */
	private Date formatStartTime(Date startTime) {
		Timestamp stamp = new Timestamp(startTime.getTime());
		stamp.setNanos(0);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(stamp.getTime());
		return cal.getTime();
	}

	/**
	 * 获取精确到纳秒的结束时间（因为取得结束时间的方法相当于下一整秒的时间，故hql\sql使用时要用小于号）
	 *
	 * @param startTime
	 * @return
	 */
	private Date formatEndTime(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.SECOND, 1);
		Timestamp endTime = new Timestamp(cal.getTime().getTime());
		endTime.setNanos(0);
		cal.setTimeInMillis(endTime.getTime());
		return cal.getTime();
	}

	/**
	 * 获取日志总数
	 *
	 * @param form
	 *            设置查询参数
	 * @return
	 */
	public long getOpLogCount(LogInfo form, List<Integer> moduleCodes) {
		String hql = "select count(*)";
		QueryCondition condition = getConditionHql(form, moduleCodes);
		if (condition == null) {
			return 0;
		}
		try {
			Long count = commonDAO.getCount(hql + condition.getHql(), condition.getArgs().toArray());
			if (count == null) {
				return 0;
			}
			return count;
		} catch (OPException e) {
			throw new ApplicationException("获取日志记录数失败。", e);
		}
	}

	/**
	 * 按页获取日志实体
	 *
	 * @param form
	 *            设置查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TsyOpLog> getOpLogs(LogInfo form, List<Integer> moduleCodes, int page, int pageSize) {
		QueryCondition condition = getConditionHql(form, moduleCodes);
		if (condition == null) {
			return null;
		}
		try {
			return commonDAO.retrieveEntityListHQL(condition.getHql() + " order by l.occurTime desc,l.logDesc desc",
					page, pageSize, condition.getArgs().toArray());
		} catch (OPException e) {
			throw new ApplicationException("获取日志记录失败。");
		}
	}

	/**
	 * 将数据库中的日志到处到CSV文件中
	 *
	 * @param file
	 * @param form
	 * @param moduleList
	 * @return
	 */
	public Future<ImExportResult> logToCvs(File file, LogInfo form, List<Integer> moduleList) {

		Future<ImExportResult> f = null;

		Calendar.getInstance();
		Map<String, String> colNameMap = new HashMap<String, String>();
		// colNameMap.put("LOG_ID", "序号");
		colNameMap.put("LINE_ID", "线路编号");
		colNameMap.put("STATION_ID", "车站编号");
		colNameMap.put("NODE_ID", "节点编号");
		colNameMap.put("MODULE_CODE", "模块编号");
		colNameMap.put("OCCUR_TIME", "发生时间");
		colNameMap.put("LOG_DESC", "日志描述");
		colNameMap.put("OPERATOR_ID", "操作员编号");
		colNameMap.put("SUB_SYS_ID", "工作台类型");
		colNameMap.put("LOG_CLASS", "日志等级");
		colNameMap.put("IP_ADDRESS", "IP 地址");

		// 初始化列值

		Map<String, ColumnValuePattern> colPattern = new HashMap<String, ColumnValuePattern>();

		colPattern.put("LOG_CLASS", new ColumnValuePattern() {
			public String computeColumnValue(Object originVal) {
				if (originVal == null) {
					return "未知";
				}
				BigDecimal bd = new BigDecimal(originVal.toString());
				if (bd.intValue() == LogDefines.NORMAL_LOG) {
					return LogDefines.logClassName[0];
				} else if (bd.intValue() == LogDefines.WARNING_LOG) {
					return LogDefines.logClassName[1];
				} else {
					return LogDefines.logClassName[2];
				}
			}
		});
		colPattern.put("MODULE_CODE", new ColumnValuePattern() {
			public String computeColumnValue(Object originVal) {
				if (originVal == null) {
					return "未知";
				}
				BigDecimal bd = new BigDecimal(originVal.toString());
				String str = Application.getData(bd.intValue()) == null ? "未知模块"
						: Application.getData(bd.intValue()).toString();
				return str;
			}
		});

		colPattern.put("NODE_ID", new ColumnValuePattern() {
			public String computeColumnValue(Object originVal) {
				if (originVal == null) {
					return "未知";
				}
				BigDecimal bd = new BigDecimal(originVal.toString());
				return String.format(AFCApplication.getDeviceIdFormat(), bd.longValue());
			}
		});

		QueryCondition condition = getConditionSql(form, moduleList);
		if (condition == null) {
			return null;
		}
		logger.debug("QueryCondition is : ****** " + condition.getSql());
		try {
			f = commonDAO.exportTableToCSVFile(condition.getSql(), file, true, true, colNameMap, colPattern,
					condition.getArgs().toArray());
		} catch (OPException e) {
			logger.error("CSV文件导出异常。", e);
			throw new ApplicationException("CSV文件导出异常。", e);
		}
		return f;
	}

	/**
	 * 作用：封装了常用的HQL字符串和实体传参列表
	 *
	 * @author filefox
	 *
	 */
	private class QueryCondition {

		private String sql;

		private String hql;

		private List<Object> args;

		public QueryCondition(String hql, List<Object> args) {
			super();
			this.hql = hql;
			this.args = args;
		}

		public QueryCondition(boolean isSql, String hql, List<Object> args) {
			super();
			if (isSql) {
				this.sql = hql;
			} else {
				this.hql = hql;
			}
			this.args = args;
		}

		public String getHql() {
			return hql;
		}

		public String getSql() {
			return sql;
		}

		public List<Object> getArgs() {
			return args;
		}

	}

}
