package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.NodeStatusMonitorConfigDTO;
import com.insigma.afc.monitor.model.dto.SectionFlowMonitorConfigDTO;
import com.insigma.commons.model.dto.Result;

/**
 * Ticket: 监控配置信息服务
 *
 * @author xuzhemin
 * 2019-01-09:14:44
 */
public interface MonitorConfigService {
    /**
     * 获取节点状态监控配置
     * @return 配置信息
     */
    Result<NodeStatusMonitorConfigDTO> getMonitorConfig();

    /**
     * 保存节点状态监控配置
     * @param monitorConfigInfo 配置信息
     * @return 配置信息
     */
    Result<NodeStatusMonitorConfigDTO> save(NodeStatusMonitorConfigDTO monitorConfigInfo);

    /**
     * 获取断面客流监控配置
     * @return 配置信息
     */
    Result<SectionFlowMonitorConfigDTO> getSectionFlowMonitorConfig();

    /**
     * 保存断面客流监控配置
     * @param monitorConfigDTO 配置信息
     * @return 配置信息
     */
    Result<SectionFlowMonitorConfigDTO> save(SectionFlowMonitorConfigDTO monitorConfigDTO);
}
