package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TccSectionValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 14:52
 */
public interface TccSectionValuesDao extends JpaRepository<TccSectionValues,Long>,
        JpaSpecificationExecutor<TccSectionValues> {
}
