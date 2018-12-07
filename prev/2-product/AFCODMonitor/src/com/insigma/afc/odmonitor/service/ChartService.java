/* 
 * 日期：2010-4-23
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.odmonitor.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.afc.dic.AFCTicketFamily;
import com.insigma.afc.odmonitor.form.BarForm;
import com.insigma.afc.odmonitor.form.ConditionForm;
import com.insigma.afc.odmonitor.form.ODForm;
import com.insigma.afc.odmonitor.form.PieForm;
import com.insigma.afc.odmonitor.form.SeriesForm;
import com.insigma.afc.odmonitor.form.TicketPieForm;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.framework.view.chart.data.BarChartData;
import com.insigma.commons.framework.view.chart.data.MultiPieData;
import com.insigma.commons.framework.view.chart.data.PieChartData;
import com.insigma.commons.framework.view.chart.data.SeriesChartData;
import com.insigma.commons.framework.view.chart.data.BarChartData.ColumnData;
import com.insigma.commons.framework.view.chart.data.PieChartData.PieData;
import com.insigma.commons.framework.view.chart.data.SeriesChartData.SeriesData;
import com.insigma.commons.op.OPException;
import com.insigma.commons.op.SqlRestrictions;
import com.insigma.commons.service.Service;
import com.insigma.commons.util.lang.DateTimeUtil;
import com.insigma.commons.util.lang.StringUtil;

/**
 * 图表数据服务
 * 
 * @author lhz
 */
@SuppressWarnings("unchecked")
public class ChartService extends Service implements IChartService {

	/**
	 * @param isSeries
	 *            是否曲线图
	 * @param form
	 *            包括车站编号，日期，起始时间，结束时间
	 * @return
	 * @throws OPException
	 */
	private List<Object[]> getODDataByDAO(boolean isSeries, ODForm form, Integer ticketType) throws OPException {
		if (form == null || form.getDate() == null || form.getStartTimeIndex() < 0
				|| form.getEndTimeIndex() < form.getStartTimeIndex()) {
			logger.warn("参数context非法，无法获取客流数据");
			return null;
		}
		String hql = "select traffic.id.stationId, sum(traffic.totalIn) as odin ," + "sum(traffic.totalOut) as odout, "
				+ "sum(traffic.saleCount) as odbuy, " + "sum(traffic.addCount) as odadd "
				+ " from TmoOdFlowStats as traffic  where "
				+ " traffic.id.gatheringDate=? and traffic.id.timeIntervalId>=? and traffic.id.timeIntervalId<=? ";
		if (isSeries) {
			hql = "select traffic.id.stationId,traffic.id.timeIntervalId , sum(traffic.totalIn) as odin ,"
					+ "sum(traffic.totalOut) as odout, " + "sum(traffic.saleCount) as odbuy, "
					+ "sum(traffic.addCount) as odadd " + " from TmoOdFlowStats as traffic  where "
					+ " traffic.id.gatheringDate=? and traffic.id.timeIntervalId>=? and traffic.id.timeIntervalId<=? ";
		}
		Integer[] stationId = form.getStationId();
		if (stationId != null) {
			hql += SqlRestrictions.in("traffic.id.stationId", StringUtil.array2StrOfCommaSplit(stationId));
		}
		if (ticketType != null) {
			hql += " and traffic.id.ticketFamily=" + ticketType;
		}
		if (isSeries) {
			hql += " group by traffic.id.stationId, traffic.id.timeIntervalId  order by traffic.id.stationId, traffic.id.timeIntervalId ";
		} else {
			hql += " group by traffic.id.stationId order by traffic.id.stationId";
		}

		Date date = form.getDate();
		int startTimeIndex = form.getStartTimeIndex();
		int endTimeIndex = form.getEndTimeIndex();
		return commonDAO.getEntityListHQL(hql, date, startTimeIndex, endTimeIndex);
	}

	private PieChartData getPieChartData(List<Object[]> data, int index) {
		PieChartData pie = new PieChartData();
		pie.setShowRows(null);
		pie.getPartNames().add("总数");
		List<PieData> pieDateList = new ArrayList<PieData>();
		for (int i = 0; i < data.size(); i++) {
			Object[] objs = data.get(i);
			PieData pieData = new PieData();

			Short type = null;
			if (objs[0] instanceof Short) {
				type = (Short) objs[0];
			} else if (objs[0] instanceof BigDecimal) {
				type = ((BigDecimal) objs[0]).shortValue();
			}

			if (type == null) {
				logger.error("获取票卡类型失败");
				return pie;
			}

			Integer odin = null;
			if (objs[index] instanceof Integer) {
				odin = (Integer) objs[index];
			} else if (objs[index] instanceof BigDecimal) {
				odin = ((BigDecimal) objs[index]).intValue();
			}

			if (odin == null) {
				logger.error("获取比例值失败");
				return pie;
			}

			pieData.setPieName(AFCTicketFamily.getInstance().getNameByValue(type.intValue()));
			pieData.getValueOfPies().add(odin.doubleValue());
			pieDateList.add(pieData);
		}
		pie.setPieItems(pieDateList);
		return pie;
	}

	private boolean sameDate(Date beforeDate, Date endDate) {
		String beforeDateString = DateTimeUtil.formatDateToString(beforeDate, "yyyy-MM-dd");
		String endDateString = DateTimeUtil.formatDateToString(endDate, "yyyy-MM-dd");
		if (beforeDateString.equals(endDateString)) {
			return true;
		}
		return false;
	}

	/**
	 * @param transType
	 * @param lineId
	 * @param stationList
	 *            车站列表
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param timePeriod
	 *            客流最小时间间隔
	 * @return
	 */
	private List<Object[]> getTicketChartData(TicketPieForm context) {

		boolean sameDate = sameDate(context.getStartDate(), context.getEndDate());

		String hql = "select t.ticket_family";

		hql += " ,sum(t.total_in) ";

		hql += " ,sum(t.total_out) ";

		hql += " ,sum(t.sale_count) ";

		hql += " ,sum(t.add_count) ";

		hql += " from tmo_od_flow_stats t where 1= 1 ";
		if (sameDate) {
			hql += " and (t.gathering_date =? and t.time_interval_id >=? " + " and t.time_interval_id<? )";
		} else {
			hql += " and ((t.gathering_date =? and t.time_interval_id >=?) "
					+ " or (t.gathering_date>? and t.gathering_date<? )"
					+ " or (t.gathering_date =? and t.time_interval_id<? ))";
		}

		if (context.getStationId() != null) {
			hql += SqlRestrictions.in(" t.station_id", context.getStationId());
		}
		hql += " group by t.ticket_family ";

		List<Object[]> list = null;
		try {
			Timestamp beginGatheringDate = new Timestamp(DateTimeUtil.beginDate(context.getStartDate()).getTime());

			Timestamp endGatheringDate = new Timestamp(DateTimeUtil.beginDate(context.getEndDate()).getTime());

			int beginInterval = DateTimeUtil.convertTimeToIndex(context.getStartDate(), context.getTimeInterval());
			int endInterval = DateTimeUtil.convertTimeToIndex(context.getEndDate(), context.getTimeInterval());
			if (sameDate) {
				list = commonDAO.getEntityListSQL(hql, beginGatheringDate, beginInterval, endInterval);
			} else {
				list = commonDAO.getEntityListSQL(hql, beginGatheringDate, beginInterval, beginGatheringDate,
						endGatheringDate, endGatheringDate, endInterval);
			}
		} catch (Exception e) {
			throw new ApplicationException("获取车票使用信息失败。", e);
		}
		return list;
	}

	/**
	 * 将各车站数据放到各个List。data中的数据一定是按照车站和时间升序排序才行
	 * 
	 * @param data
	 * @param stationIds
	 * @return
	 */
	private Map<String, List<Object[]>> sortData(List<Object[]> data, Integer[] stationIds) {
		Map<String, List<Object[]>> dataMap = new HashMap<String, List<Object[]>>();
		for (int j = 0; j < stationIds.length; j++) {
			dataMap.put(stationIds[j] + "", new ArrayList<Object[]>());
		}
		// 将数据按照各自的车站分配到数组中
		for (Object[] odData : data) {
			int id = (Integer) odData[0];
			if (dataMap.containsKey("" + id)) {
				dataMap.get("" + id).add(odData);
			}
		}
		return dataMap;
	}

	/**
	 * 按照设置的时间间隔（例如5分），将输入的时间转换为从0：00开始的编号
	 * 
	 * @param time
	 *            格式为"hh:mm" 例如"07:00"
	 * @param timePeriod
	 *            时间间隔
	 * @return 按照设置的时间间隔，将输入的时间转换为从0：00开始的编号
	 */
	public static int convertTimeToIndex(String time, int timePeriod) {
		int hour = Integer.parseInt(time.substring(0, 2));
		int minute = Integer.parseInt(time.substring(3, 5));
		if (timePeriod <= 0) {
			timePeriod = 1;
		}
		int minites = hour * 60 + minute;
		if (minites <= 0) {
			return 1;
		} else {
			if (minites % timePeriod == 0) {
				return minites / timePeriod;
			} else {
				return minites / timePeriod + 1;
			}
		}
	}

	/**
	 * 获取客流柱状图数据
	 * 
	 * @param form
	 *            包括车站编号，日期，起始时间，结束时间，显示项(比如进站、出站、购票、充值等)，车站编号对应的车站名（ Map<Integer,String> 不能为空）
	 * @return
	 */
	public BarChartData getBarChartData(BarForm form) {

		List<Object[]> data = null;
		try {
			data = getODDataByDAO(false, form, form.getTicketFamily());
		} catch (Exception e) {
			throw new RuntimeException("获取车站客流数据失败。", e);
		}
		if (data == null || data.isEmpty()) {
			logger.warn("柱状图客流数据为空。");
			return null;
		}

		String[] partNames = form.getPartNames();
		if (partNames == null || partNames.length <= 0) {
			return null;
		}
		BarChartData chartData = new BarChartData();
		List<String> names = new ArrayList<String>();
		for (String name : partNames) {
			names.add(name);
		}
		chartData.setRowNames(names);

		Map<Integer, String> stationNameMap = form.getStationNameMap();
		List<ColumnData> columnItems = new ArrayList<ColumnData>();
		int columnIndex = 0;
		for (Object[] objects : data) {
			// 车站名、进站、出站、购票、充值
			int stationId = (Integer) objects[0];
			long odin = (Long) objects[1];
			long odout = (Long) objects[2];
			long odbuy = (Long) objects[3];
			long odadd = (Long) objects[4];
			long total = odin + odout + odbuy + odadd;
			List<Number> valueOfRows = new ArrayList<Number>();
			for (int j = 0; j < names.size(); j++) {
				if (j >= 4) {
					valueOfRows.add(total);
				} else {
					valueOfRows.add((Long) objects[j + 1]);
				}
			}
			String culumnName = stationNameMap.get(stationId);
			if (culumnName.equals("车站名未知")) {
				culumnName = culumnName + columnIndex;
				columnIndex++;
			}
			ColumnData d = new ColumnData(culumnName, valueOfRows);
			columnItems.add(d);
		}
		chartData.setColumnItems(columnItems);
		return chartData;
	}

	/**
	 * 获取客流饼图数据
	 * 
	 * @param form
	 *            包括车站编号，日期，起始时间，结束时间，显示项(比如进站、出站、购票、充值等)，车站编号对应的车站名（ Map<Integer,String> 不能为空）
	 * @return
	 */
	public PieChartData getPieChartData(PieForm form) {

		List<Object[]> data = null;
		try {
			data = getODDataByDAO(false, form, form.getTicketFamily());
		} catch (Exception e) {
			throw new RuntimeException("获取车站客流数据失败。", e);
		}

		if (data == null || data.isEmpty()) {
			logger.warn("饼图客流数据为空");
			return null;
		}

		String[] partNames = form.getPartNames();
		if (partNames == null || partNames.length <= 0) {
			return null;
		}

		PieChartData chartData = new PieChartData();

		List<String> names = new ArrayList<String>();
		for (String name : partNames) {
			names.add(name);
		}
		chartData.setPartNames(names);

		Map<Integer, String> stationNameMap = form.getStationNameMap();
		List<PieData> pieItems = new ArrayList<PieData>();
		// double[] v = new double[names.size()];
		int pieIndex = 0;
		for (Object[] objects : data) {
			int stationId = (Integer) objects[0];
			long odin = (Long) objects[1];
			long odout = (Long) objects[2];
			long odbuy = (Long) objects[3];
			long odadd = (Long) objects[4];
			long total = odin + odout + odbuy + odadd;
			List<Double> valueOfPies = new ArrayList<Double>();
			for (int j = 0; j < names.size(); j++) {
				double value = 0.0;
				if (j >= 4) {
					value = total;
				} else {
					value = (Long) objects[j + 1];
				}
				valueOfPies.add(value);
			}
			String pieName = stationNameMap.get(stationId);
			if (pieName.equals("车站名未知")) {
				pieName = pieName + pieIndex;
				pieIndex++;
			}
			PieData d = new PieData(pieName, valueOfPies);
			pieItems.add(d);
		}
		chartData.setPieItems(pieItems);
		return chartData;
	}

	/**
	 * 获取客流曲线图数据
	 * 
	 * @param form
	 *            包括车站编号，日期，起始时间，结束时间，显示项(比如进站、出站、购票、充值等)，<br>
	 *            库表中timeIntervalId时间点长度(单位为分钟)，每个曲线上的点包含timeIntervalId<br>
	 *            的个数inclueTimeIdCount(默认值为1),车站编号对应的车站名（ Map<Integer,String> 不能为空）
	 * @return
	 */
	public SeriesChartData getSeriesChartData(SeriesForm form) {
		List<Object[]> data = null;
		try {
			data = getODDataByDAO(true, form, form.getTicketFamily());
		} catch (Exception e) {
			throw new RuntimeException("获取车站客流数据失败。", e);
		}

		if (data == null || data.isEmpty()) {
			logger.warn("所有车站无客流数据");
			return null;
		}

		String[] partNames = form.getPartNames();
		if (partNames == null || partNames.length <= 0) {
			return null;
		}
		SeriesChartData chartData = new SeriesChartData();

		List<String> names = new ArrayList<String>();
		for (String name : partNames) {
			names.add(name);
		}
		chartData.setPartNames(names);

		Calendar cal = Calendar.getInstance();

		Map<String, List<Object[]>> sortData = sortData(data, form.getStationId());
		// 数据库中一个timeIntervalId数据点包含的分钟数，比如timePeriod=5，则表明timeIntervalId包含5分钟的客流数据。默认值为5
		// 通过该值可以获取对于的时间点
		int timePeriod = 5;
		if (form.getTimeInterval() > 1) {
			timePeriod = form.getTimeInterval();
		}
		// 每个曲线上的点包含timeIntervalId的个数inclueTimeIdCount(默认值为1)
		int inclueTimeIdCount = 1;
		if (form.getIntervalCount() > 1) {
			inclueTimeIdCount = form.getIntervalCount();
		}
		chartData.setIntervalCount(form.getIntervalCount());

		chartData.setTicketType(form.getTicketFamily());

		Map<Integer, String> stationNameMap = form.getStationNameMap();
		Map<String, List<SeriesData>> seriersItems = new HashMap<String, List<SeriesData>>();
		int stationIndex = 0;
		// 生成各条曲线数据
		for (String stationId : sortData.keySet()) {
			List<Object[]> dataList = sortData.get(stationId);
			if (dataList.isEmpty()) {
				logger.warn("车站ID=" + stationId + "无客流数据");
				continue;
			}

			long pointodin = 0;
			long pointodout = 0;
			long pointodbuy = 0;
			long pointodadd = 0;
			long pointtotal = 0;
			// 每个点包含的各项值
			int count = 0;
			int timeIntervalId = 0;
			// 曲线上数据点
			List<SeriesData> valueOfSeries = new ArrayList<SeriesData>();
			for (Object[] objects : dataList) {
				// int stationId = (Integer) objects[0];
				timeIntervalId = (Integer) objects[1];
				long odin = (Long) objects[2];
				long odout = (Long) objects[3];
				long odbuy = (Long) objects[4];
				long odadd = (Long) objects[5];
				long total = odin + odout + odbuy + odadd;

				pointodin += odin;
				pointodout += odout;
				pointodbuy += odbuy;
				pointodadd += odadd;
				pointtotal += total;

				count++;
				// 客流值点加入到曲线
				if (count >= inclueTimeIdCount) {
					Date date = form.getDate();
					cal.setTime(date);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.add(Calendar.MINUTE, timeIntervalId * timePeriod);

					SeriesData seriesData = new SeriesData();
					seriesData.setDate(cal.getTime());
					seriesData.setValueOfPart(new long[] { pointodin, pointodout, pointodbuy, pointodadd });
					valueOfSeries.add(seriesData);

					pointodin = 0;
					pointodout = 0;
					pointodbuy = 0;
					pointodadd = 0;
					pointtotal = 0;

					count = 0;
				}
			}
			// 判断是否还有剩余的最后数据点没加到曲线上，有的话就加上
			if (count > 0) {
				Date date = form.getDate();
				cal.setTime(date);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.add(Calendar.MINUTE, timeIntervalId * timePeriod);

				SeriesData seriesData = new SeriesData();
				seriesData.setDate(cal.getTime());
				seriesData.setValueOfPart(new long[] { pointodin, pointodout, pointodbuy, pointodadd });
				valueOfSeries.add(seriesData);
				logger.info("车站ID=" + stationId + " 将最后一点加入到曲线上");
			}
			String seriesName = stationNameMap.get(Integer.valueOf(stationId));
			if (seriesName.equals("车站名未知")) {
				seriesName = seriesName + stationIndex;
			}
			seriersItems.put(seriesName, valueOfSeries);
			stationIndex++;
		}

		chartData.setSeriersItems(seriersItems);
		return chartData;
	}

	/**
	 * 获取客流记录数
	 * 
	 * @param para
	 *            车站数组、日期、开始时间下标，结束时间下标，统计分类、票种归类
	 * @return 记录数
	 */
	public Long getODCount(ConditionForm form) {

		String hql = "select t.id.stationId from  TmoOdFlowStats t "
				+ " where t.id.gatheringDate =? and t.id.timeIntervalId >=? and t.id.timeIntervalId<=? ";

		if (form.getStationId() != null) {
			hql += SqlRestrictions.in("t.id.stationId", form.getStationId());
		}
		hql += " group by t.id.stationId " + (form.getStatType() == 0 ? ",t.id.ticketFamily " : "");
		try {
			List<Integer> list = commonDAO.getEntityListHQL(hql, form.getDate(), form.getStartTimeIndex(),
					form.getEndTimeIndex());
			if (!list.isEmpty()) {
				return (long) list.size();
			}
		} catch (Exception e) {
			throw new ApplicationException("获取客流记录数失败。", e);
		}
		return 0L;
	}

	/**
	 * 获取客流记录
	 * 
	 * @param para
	 *            车站数组、日期、开始时间下标，结束时间下标，统计分类、票种归类、当前页、页面大小
	 * @return 客流记录
	 */
	public List<Object[]> getODList(ConditionForm form, int page, int pageSize) {

		String sql = "select traffic.id.stationId,"
				+ (form.getStatType() == 0 ? " traffic.id.ticketFamily," : "'全部票种',") + "sum(traffic.totalIn) as odin ,"
				+ "sum(traffic.totalOut) as odout, " + "sum(traffic.saleCount) as odbuy, "
				+ "sum(traffic.addCount) as odadd " + " from TmoOdFlowStats  traffic  where "
				+ " traffic.id.gatheringDate=? and traffic.id.timeIntervalId>=? and traffic.id.timeIntervalId<=? ";

		if (form.getStationId() != null) {
			sql += SqlRestrictions.in("traffic.id.stationId", form.getStationId());
		}
		sql += " group by traffic.id.stationId " + (form.getStatType() == 0 ? ",traffic.id.ticketFamily " : "");
		sql += " order by traffic.id.stationId " + (form.getStatType() == 0 ? ",traffic.id.ticketFamily " : "");
		try {
			List<Object[]> list = commonDAO.retrieveEntityListHQL(sql, page, pageSize, form.getDate(),
					form.getStartTimeIndex(), form.getEndTimeIndex());
			return list;
		} catch (Exception e) {
			throw new ApplicationException("获取客流记录失败。", e);
		}
	}

	public MultiPieData getMultiPieData(TicketPieForm context) {

		MultiPieData multiPieData = new MultiPieData();

		List<PieChartData> pieCharDataList = new ArrayList<PieChartData>();
		List<String> pieName = new ArrayList<String>();
		List<Object[]> data = null;
		try {
			data = getTicketChartData(context);
		} catch (Exception e) {
			throw new RuntimeException("获取车站客流数据失败。", e);
		}
		int transType = context.getTransType().intValue();

		// 进站
		if ((transType & (0x01)) != 0) {

			int index = 1;
			PieChartData pie = getPieChartData(data, index);

			pieCharDataList.add(pie);
			pieName.add(context.getPartNames()[0]);

		}
		// 出站
		if ((transType & (0x02)) != 0) {
			int index = 2;
			PieChartData pie = getPieChartData(data, index);

			pieCharDataList.add(pie);
			pieName.add(context.getPartNames()[1]);
		}
		// 购票
		if ((transType & (0x04)) != 0) {
			int index = 3;
			PieChartData pie = getPieChartData(data, index);

			pieCharDataList.add(pie);
			pieName.add(context.getPartNames()[2]);
		}
		// 充值
		if ((transType & (0x08)) != 0) {
			int index = 4;
			PieChartData pie = getPieChartData(data, index);

			pieCharDataList.add(pie);
			pieName.add(context.getPartNames()[3]);
		}
		multiPieData.setData(pieCharDataList);
		multiPieData.setPieChartNames(pieName);
		return multiPieData;
	}

	/**
	 * 获取总客流记录
	 * 
	 * @param para
	 *            日期、开始时间下标，结束时间下标，统计分类、票种归类、当前页、页面大小
	 * @return 客流记录
	 */
	public List<Object[]> getTotalODList(ConditionForm form) {

		String sql = "select traffic.id.lineId," + (form.getStatType() == 0 ? " traffic.id.ticketFamily," : "'全部票种',")
				+ "sum(traffic.totalIn) as odin ," + "sum(traffic.totalOut) as odout, "
				+ "sum(traffic.saleCount) as odbuy, " + "sum(traffic.addCount) as odadd "
				+ " from TmoOdFlowStats  traffic  where "
				+ " traffic.id.gatheringDate=? and traffic.id.timeIntervalId>=? and traffic.id.timeIntervalId<=? ";

		sql += " group by traffic.id.lineId " + (form.getStatType() == 0 ? ",traffic.id.ticketFamily " : "");
		sql += " order by traffic.id.lineId " + (form.getStatType() == 0 ? ",traffic.id.ticketFamily " : "");
		try {
			List<Object[]> list = commonDAO.getEntityListHQL(sql, form.getDate(), form.getStartTimeIndex(),
					form.getEndTimeIndex());
			return list;
		} catch (Exception e) {
			throw new ApplicationException("获取总客流记录失败。", e);
		}
	}
}
