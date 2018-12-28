package com.insigma.afc.odmonitor.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.manager.SystemConfigManager;
import com.insigma.afc.odmonitor.config.ConfigKey;
import com.insigma.afc.odmonitor.entity.TccSectionValues;
import com.insigma.afc.odmonitor.entity.TmoSectionOdFlowStats;
import com.insigma.afc.odmonitor.entity.TmoSectionOdFlowStatsId;
import com.insigma.afc.odmonitor.listview.item.SectionOdFlowStatsView;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.database.StringHelper;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.op.OPException;
import com.insigma.commons.op.SqlRestrictions;
import com.insigma.commons.service.Service;
import com.insigma.commons.util.lang.DateTimeUtil;

/**
 * 创建时间 2010-10-8 下午08:44:23 <br>
 * 描述: 断面客流Service<br>
 * Ticket：
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
@SuppressWarnings("unchecked")
public class SectionODFlowService extends Service implements ISectionODFlowService {

	HashMap<Long, TccSectionValues> sectionmap;

	int lowFlow = 1000;

	int highFlow = 2000;

	@Override
	public List<SectionOdFlowStatsView> getSectionODFlowStatsViewList(Short[] lines, Integer[] timeIntervalIds,
			Short direction, Date date, int page, int pageSize) {
		List<TmoSectionOdFlowStats> secOdFlowStatslist = getSectionOdFlowStatsList(lines, timeIntervalIds, date, page,
				pageSize);
		List<SectionOdFlowStatsView> secOdFlowViewlist = new ArrayList<SectionOdFlowStatsView>();
		// TmoConfig conf = (TmoConfig) Application.getData(ApplicationKey.MONITOR_CONFIG_KEY);

		SectionOdFlowStatsView flowStatsView;
		if (secOdFlowStatslist == null) {
			return secOdFlowViewlist;
		}
		//
		lowFlow = SystemConfigManager.getConfigItemForInt(ConfigKey.SectionPassengerflowLow);
		highFlow = SystemConfigManager.getConfigItemForInt(ConfigKey.SectionPassengerflowHigh);
		int minutes = timeIntervalIds.length * 5;
		int index = 1;
		for (TmoSectionOdFlowStats flowStats : secOdFlowStatslist) {

			flowStatsView = new SectionOdFlowStatsView();
			if (flowStats.getId() == null) {
				continue;
			}
			TccSectionValues sectionValues = sectionmap.get(flowStats.getId().getSectionId());

			flowStatsView.setSectionId(flowStats.getId().getSectionId());

			flowStatsView.setLine(sectionValues.getLineName() + "/"
					+ String.format(AFCApplication.getLineIdFormat(), sectionValues.getLineId()));
			//
			flowStatsView.setUpstation(sectionValues.getPreStationName().trim() + "/"
					+ String.format(AFCApplication.getStationIdFormat(), (sectionValues.getPreStationId())));
			flowStatsView.setDownstation(sectionValues.getDownStationName().trim() + "/"
					+ String.format(AFCApplication.getStationIdFormat(), (sectionValues.getDownStationId())));

			flowStatsView.setBusinessday(DateTimeUtil.formatDate(flowStats.getId().getGatheringDate(), "yyyy-MM-dd"));
			// 统计客流
			double flowcount = 0;// 上行客流密度
			double downFlowcount = 0;// 下行客流密度
			BigDecimal decimal;
			if (direction == null || direction == 0) {
				flowcount = (double) flowStats.getUpCount() / 100.0;
				decimal = new BigDecimal(flowcount);
				flowStatsView.setUpcount(Integer.toString(decimal.setScale(0, RoundingMode.UP).intValue()));
			} else {
				flowStatsView.setUpcount(Integer.toString(0));
			}

			if (direction == null || direction == 1) {
				flowcount = (double) flowStats.getDownCount() / 100.0;
				decimal = new BigDecimal(flowcount);
				flowStatsView.setDowncount(Integer.toString(decimal.setScale(0, RoundingMode.UP).intValue()));
			} else {
				flowStatsView.setDowncount(Integer.toString(0));
			}

			if (direction == null) {
				// 上行客流密度
				flowcount = flowStats.getUpCount() / 100.0;
				// 下行客流密度
				downFlowcount = flowStats.getDownCount() / 100.0;
				// 总客流=上行客流加下行客流
				flowStatsView.setTotalcount(Integer.parseInt(flowStatsView.getUpcount())
						+ Integer.parseInt(flowStatsView.getDowncount()) + "");
			} else if (direction == 0) {
				flowcount = (flowStats.getTotalCount() - flowStats.getDownCount()) / 100.0;
				decimal = new BigDecimal(flowcount);
				flowStatsView.setTotalcount(Integer.toString(decimal.setScale(0, RoundingMode.UP).intValue()));
			} else if (direction == 1) {
				flowcount = (flowStats.getTotalCount() - flowStats.getUpCount()) / 100.0;
				decimal = new BigDecimal(flowcount);
				flowStatsView.setTotalcount(Integer.toString(decimal.setScale(0, RoundingMode.UP).intValue()));
			}

			// 获取车站位置
			MetroStation mapItem = (MetroStation) AFCApplication.getNode(sectionValues.getPreStationId());
			if (mapItem == null) {
				logger.debug("车站[" + sectionValues.getPreStationId() + "] 未找到。");
				continue;
			}
			flowStatsView.setUpStationPosX(mapItem.getImageLocation().getX());
			flowStatsView.setUpStationPosY(mapItem.getImageLocation().getY());

			mapItem = (MetroStation) AFCApplication.getNode(sectionValues.getDownStationId());
			if (mapItem == null) {
				logger.debug("车站[" + sectionValues.getDownStationId() + "] 未找到。");
				continue;
			}
			flowStatsView.setDownStationPosX(mapItem.getImageLocation().getX());
			flowStatsView.setDownStationPosY(mapItem.getImageLocation().getY());
			//
			short odLevelFlag = 0;
			double count = flowcount / minutes;

			odLevelFlag = getOdLevelFlag(count);
			// 上行客流密度
			flowStatsView.setOdLevelFlag(odLevelFlag);
			// 下行客流密度
			count = downFlowcount / minutes;
			odLevelFlag = getOdLevelFlag(count);
			flowStatsView.setDownOdLevelFlag(odLevelFlag);
			flowStatsView.setIndex(index++);
			secOdFlowViewlist.add(flowStatsView);
		}
		return secOdFlowViewlist;
	}

	/**
	 * @param count
	 * @return
	 */
	private short getOdLevelFlag(double count) {
		short odLevelFlag;
		if (count <= lowFlow) {
			odLevelFlag = 0;
		} else if (count >= highFlow) {
			odLevelFlag = 2;
		} else {
			odLevelFlag = 1;
		}
		return odLevelFlag;
	}

	@Override
	public List<TmoSectionOdFlowStats> getSectionOdFlowStatsList(Short[] lines, Integer[] timeIntervalIds, Date date,
			int page, int pageSize) {
		List<TccSectionValues> sectionList = getSectionValuesList(lines);
		if (sectionList == null || sectionList.isEmpty()) {
			return null;
		}
		//
		sectionmap = new HashMap<Long, TccSectionValues>();
		for (int i = 0; i < sectionList.size(); i++) {
			sectionmap.put(sectionList.get(i).getSectionId(), sectionList.get(i));
		}
		//
		List<Object> args = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select t.id.sectionId,sum(t.upCount),sum(t.downCount),sum(t.totalCount) from TmoSectionOdFlowStats t where 1=1 ");
		if (timeIntervalIds != null && date != null) {
			Integer[] timeIntervalIds1 = null;// 当天的时段
			Integer[] timeIntervalIds2 = null;// 昨天的时段
			// 判断是否跨天
			{
				if (timeIntervalIds[0] > timeIntervalIds[timeIntervalIds.length - 1]) {
					int splitIndex = 0;
					for (int i = 0; i < timeIntervalIds.length; i++) {
						if (i + 1 < timeIntervalIds.length) {
							splitIndex++;
							if (timeIntervalIds[i] > timeIntervalIds[i + 1]) {
								break;
							}
						}
					}
					timeIntervalIds2 = new Integer[splitIndex];
					timeIntervalIds1 = new Integer[timeIntervalIds.length - splitIndex];
					System.arraycopy(timeIntervalIds, 0, timeIntervalIds2, 0, timeIntervalIds2.length);
					System.arraycopy(timeIntervalIds, splitIndex, timeIntervalIds1, 0, timeIntervalIds1.length);

				} else {
					timeIntervalIds1 = timeIntervalIds;
				}
			}
			String tempSql = "";
			if (timeIntervalIds1 != null) {
				tempSql += " and ((1=1 " + SqlRestrictions.in("t.id.timeIntervalId",
						StringHelper.array2StrOfCommaSplit(timeIntervalIds1));

				tempSql += " and t.id.gatheringDate=? ) ";
				args.add(date);
			}
			if (timeIntervalIds2 != null) {
				tempSql += " or (1=1 " + SqlRestrictions.in("t.id.timeIntervalId",
						StringHelper.array2StrOfCommaSplit(timeIntervalIds2));

				tempSql += " and t.id.gatheringDate=? )) ";
				args.add(DateTimeUtil.getDateDiff(date, -1));
			} else {
				tempSql += ")";
			}
			sb.append(tempSql);
		}
		if (sectionmap != null) {
			sb.append(SqlRestrictions.in("t.id.sectionId",
					StringHelper.array2StrOfCommaSplit(sectionmap.keySet().toArray())));
		}
		// if (date != null) {
		// sb.append("and t.id.gatheringDate=? ");
		// args.add(date);
		// }
		sb.append(" group by t.id.sectionId");
		sb.append(" order by t.id.sectionId asc");
		List list = null;
		try {
			list = commonDAO.retrieveEntityListHQL(sb.toString(), page, pageSize, args.toArray());
		} catch (OPException e) {
			logger.error("查询命令日志列表异常", e);
			throw new ApplicationException("查询命令日志列表异常", e);
		}

		List<TmoSectionOdFlowStats> secOdFlowStatslist = new ArrayList<TmoSectionOdFlowStats>();
		for (Object o : list) {
			TmoSectionOdFlowStats value = new TmoSectionOdFlowStats();
			Object[] objs = (Object[]) o;
			value.setId(new TmoSectionOdFlowStatsId((Long) objs[0], date, 0L));
			value.setUpCount((Long) objs[1]);
			value.setDownCount((Long) objs[2]);
			value.setTotalCount((Long) objs[3]);
			secOdFlowStatslist.add(value);
		}
		return secOdFlowStatslist;
	}

	@Override
	public List<TccSectionValues> getSectionValuesList(Short[] lines) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("from TccSectionValues t where 1=1 ");
		if (lines != null) {
			sb.append(SqlRestrictions.in("t.lineId", StringHelper.array2StrOfCommaSplit(lines)));
		}
		sb.append(" and transferFlag=0 ");// 不包括换乘段
		// sb.append("order by t.sectionId asc");
		List<TccSectionValues> sectionList = null;
		try {
			sectionList = commonDAO.getEntityListHQL(sb.toString(), args.toArray());
		} catch (OPException e) {
			logger.error("查询命令日志列表异常", e);
			throw new ApplicationException("查询命令日志列表异常", e);
		}
		return sectionList;
	}

	@Override
	public int getMaxofSectionODFlow(Short[] lines, Integer[] timeIntervalIds, Date date) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from TmoSectionOdFlowStats t where 1=1 ");
		if (timeIntervalIds != null) {
			sb.append(SqlRestrictions.in("t.id.timeIntervalId", StringHelper.array2StrOfCommaSplit(timeIntervalIds)));
		}
		if (sectionmap != null) {
			sb.append(SqlRestrictions.in("t.id.sectionId",
					StringHelper.array2StrOfCommaSplit(sectionmap.keySet().toArray())));
		}
		if (date != null) {
			sb.append("and t.id.gatheringDate=? ");
			args.add(date);
		}
		sb.append("order by t.id.sectionId asc");
		List list = null;
		try {
			list = commonDAO.getEntityListHQL(sb.toString(), args.toArray());
		} catch (OPException e) {
			logger.error("查询断面客流列表异常", e);
			throw new ApplicationException("查询断面客流列表异常", e);
		}
		return Integer.valueOf(list.get(0).toString());
	}

}
