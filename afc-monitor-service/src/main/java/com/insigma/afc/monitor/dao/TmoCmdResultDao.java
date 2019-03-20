package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmoCmdResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-28:17:49
 */
@Repository
public interface TmoCmdResultDao extends JpaRepository<TmoCmdResult,Long>,
        JpaSpecificationExecutor<TmoCmdResult> {
}
