package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmoSectionOdFlowStats;
import com.insigma.afc.monitor.model.entity.TmoSectionOdFlowStatsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 14:34
 */
@Repository
public interface TmoSectionOdFlowStatsDao extends JpaRepository<TmoSectionOdFlowStats,TmoSectionOdFlowStatsId>,
        JpaSpecificationExecutor<TmoSectionOdFlowStats> {
}
