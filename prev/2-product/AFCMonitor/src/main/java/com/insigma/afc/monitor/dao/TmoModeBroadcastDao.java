package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-28:18:53
 */
@Repository
public interface TmoModeBroadcastDao extends JpaRepository<TmoModeBroadcast,Long>,
        JpaSpecificationExecutor<TmoModeBroadcast> {
}
