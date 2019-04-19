package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.SectionMonitorDTO;
import com.insigma.afc.monitor.model.dto.SectionValuesDTO;
import com.insigma.afc.monitor.model.dto.TmoSectionOdFlowStatsDTO;
import com.insigma.afc.monitor.model.dto.condition.SectionFlowCondition;
import com.insigma.afc.monitor.model.dto.condition.SectionFlowMonitorCondition;
import com.insigma.afc.monitor.model.entity.TmoSectionOdFlowStats;
import com.insigma.afc.monitor.model.vo.SectionOdFlowStatsView;
import org.springframework.data.domain.Page;

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
     * 获取断面客流监控数据
     * @param condition 查询条件
     * @return 监控数据
     */
    SectionMonitorDTO getSectionODFlowDensity(SectionFlowMonitorCondition condition);

    /**
     * 查询断面客流数据
     * @param condition 查询条件
     * @return 查询数据
     */
    Page<SectionOdFlowStatsView> getSectionODFlowStatsViewList(SectionFlowCondition condition);

    /**
     * 获取断面客流统计信息
     * @param condition 查询统计条件
     * @return 断面数据
     */
    List<List<Object>> getSectionODFlowStatistics(SectionFlowMonitorCondition condition);

    /**
     * 获取断面信息
     * @return 断面信息，包含断面id和上下车站名称
     */
    List<SectionValuesDTO> getSectionValues();

}
