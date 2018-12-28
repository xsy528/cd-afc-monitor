package com.insigma.afc.monitor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.afc.monitor.entity.TmoCmdResult;
import com.insigma.commons.database.StringHelper;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.op.SqlRestrictions;
import com.insigma.commons.service.Service;

@SuppressWarnings("unchecked")
public class CommandLogService extends Service implements ICommandLogService {

	/**
	 * 获取命令日志列表
	 * 
	 * @param lindId
	 *            线路编号
	 * @param stationId
	 *            车站编号
	 * @param operatorId
	 *            操作员编号
	 * @param cmdResult
	 *            发送结果
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param searchResultFlg
	 *            null：所有信息 true:正确信息 false:错误信息
	 * @param page
	 * @param pageSize
	 * @return List<TmoModeUploadInfo> 命令日志列表
	 */
	public List<TmoCmdResult> getCommandLogList(Short[] lineId, Integer[] stationId, Long[] nodeId, String operatorId,
			Integer cmdResult, Date startTime, Date endTime, Boolean successResult, Short cmdType, int page,
			int pageSize) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("from TmoCmdResult t where 1=1 ");
		if (lineId != null) {
			sb.append(SqlRestrictions.in("t.lineId", StringHelper.array2StrOfCommaSplit(lineId)));
		}
		if (stationId != null) {
			sb.append(SqlRestrictions.in("t.stationId", StringHelper.array2StrOfCommaSplit(stationId)));
		}

		if (nodeId != null) {
			sb.append(SqlRestrictions.in("t.nodeId", StringHelper.array2StrOfCommaSplit(nodeId)));
		}

		if (operatorId != null) {
			sb.append("and t.operatorId=? ");
			args.add(operatorId);
		}
		if (cmdResult != null) {
			sb.append("and t.cmdResult=? ");
			args.add(cmdResult.shortValue());
		}
		if (startTime != null) {
			sb.append("and t.occurTime>=? ");
			args.add(startTime);
		}
		if (endTime != null) {
			sb.append("and t.occurTime<=? ");
			args.add(endTime);
		}
		if (successResult != null) {
			if (successResult) {
				sb.append("and t.cmdResult=? ");
			} else {
				sb.append("and t.cmdResult<>? ");
			}
			args.add((short) 0);
		}

		if (cmdType != null) {
			sb.append(" and t.cmdType=? ");
			args.add(cmdType);
		}

		sb.append(" order by t.occurTime desc,t.lineId,t.stationId,t.nodeId");
		List<TmoCmdResult> commandLogList = null;
		try {
			commandLogList = commonDAO.retrieveEntityListHQL(sb.toString(), page, pageSize, args.toArray());
		} catch (OPException e) {
			logger.error("查询命令日志列表异常", e);
			throw new ApplicationException("查询命令日志列表异常", e);
		}
		return commandLogList;
	}

	/**
	 * 获取命令日志信息总条数
	 * 
	 * @param lindId
	 *            线路编号
	 * @param stationId
	 *            车站编号
	 * @param operatorId
	 *            操作员编号
	 * @param cmdResult
	 *            发送结果
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param page
	 * @param pageSize
	 * @return List<TmoModeUploadInfo> 命令日志列表
	 */
	public int getMaxofCommandLog(Short[] lindId, Integer[] stationId, Long[] nodeId, String operatorId,
			Integer cmdResult, Date startTime, Date endTime, Boolean successResult, Short cmdType) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from TmoCmdResult t where 1=1 ");
		if (lindId != null) {
			sb.append(SqlRestrictions.in("t.lineId", StringHelper.array2StrOfCommaSplit(lindId)));
		}
		if (stationId != null) {
			sb.append(SqlRestrictions.in("t.stationId", StringHelper.array2StrOfCommaSplit(stationId)));
		}

		if (nodeId != null) {
			sb.append(SqlRestrictions.in("t.nodeId", StringHelper.array2StrOfCommaSplit(nodeId)));
		}
		if (operatorId != null) {
			sb.append("and t.operatorId=? ");
			args.add(operatorId);
		}
		if (cmdResult != null) {
			sb.append("and t.cmdResult=? ");
			args.add(cmdResult.shortValue());
		}
		if (startTime != null) {
			sb.append("and t.occurTime>=? ");
			args.add(startTime);
		}
		if (endTime != null) {
			sb.append("and t.occurTime<=? ");
			args.add(endTime);
		}

		if (successResult != null) {
			if (successResult) {
				sb.append("and t.cmdResult=? ");
			} else {
				sb.append("and t.cmdResult<>? ");
			}
			args.add((short) 0);
		}

		if (cmdType != null) {
			sb.append(" and t.cmdType=? ");
			args.add(cmdType);
		}
		List list = null;
		try {
			list = commonDAO.getEntityListHQL(sb.toString(), args.toArray());
		} catch (OPException e) {
			logger.error("查询命令日志列表异常", e);
			throw new ApplicationException("查询命令日志列表异常", e);
		}
		return Integer.valueOf(list.get(0).toString());
	}
}
