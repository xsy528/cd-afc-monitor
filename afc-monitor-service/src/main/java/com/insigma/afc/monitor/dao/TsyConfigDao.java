package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TsyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-29:09:46
 */
@Repository
public interface TsyConfigDao extends JpaRepository<TsyConfig,String> {
}
