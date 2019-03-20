package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmoEquStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author  xingshaoya
 */
@Repository
public interface TmoEquEventDao extends JpaRepository<TmoEquStatus, Long>, JpaSpecificationExecutor<TmoEquStatus> {


}
