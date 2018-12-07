package com.insigma.afc.odmonitor.service;

import java.util.Date;
import java.util.List;

import com.insigma.afc.odmonitor.entity.TccSectionValues;
import com.insigma.afc.odmonitor.entity.TmoSectionOdFlowStats;
import com.insigma.afc.odmonitor.listview.item.SectionOdFlowStatsView;
import com.insigma.commons.application.IService;

/**
 * 创建时间 2010-10-8 下午08:46:28 <br>
 * 描述: <br>
 * Ticket：
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public interface ISectionODFlowService extends IService {

	public List<TmoSectionOdFlowStats> getSectionOdFlowStatsList(Short[] lines, Integer[] timeIntervalIds, Date date,
			int page, int pageSize);

	public List<SectionOdFlowStatsView> getSectionODFlowStatsViewList(Short[] lines, Integer[] timeIntervalIds,
			Short direction, Date date, int page, int pageSize);

	public int getMaxofSectionODFlow(Short[] lines, Integer[] timeIntervalIds, Date date);

	public List<TccSectionValues> getSectionValuesList(Short[] lines);
}
