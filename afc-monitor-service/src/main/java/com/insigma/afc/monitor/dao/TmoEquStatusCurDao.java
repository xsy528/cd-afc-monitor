package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmoEquStatusCur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-28:20:12
 */
@Repository
public interface TmoEquStatusCurDao extends JpaRepository<TmoEquStatusCur,Long>,
        JpaSpecificationExecutor<TmoEquStatusCur> {
}
