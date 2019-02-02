package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TstTvmBoxStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Ticket: 钱箱
 *
 * @author xuzhemin
 * 2019-01-29:15:01
 */
@Repository
public interface TstTvmBoxStocksDao extends JpaRepository<TstTvmBoxStocks,Long> {
}
