package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TstNodeStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-29:15:06
 */
@Repository
public interface TstNodeStocksDao extends JpaRepository<TstNodeStocks,Long> {
}
