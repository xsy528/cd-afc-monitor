package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.SectionFlowCondition;
import com.insigma.afc.monitor.model.entity.TmoSectionOdFlowStats;
import com.insigma.afc.monitor.model.vo.SectionOdFlowStatsView;

import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 14:22
 */
public interface SectionODFlowService {

    /**
     * 查询断面客流数据
     * @param condition
     * @return
     */
    List<SectionOdFlowStatsView> getSectionODFlowStatsViewList(SectionFlowCondition condition);
}
