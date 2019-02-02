package com.insigma.afc.topology.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insigma.afc.topology.model.entity.MetroACC;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-25:13:38
 */
@Repository
public interface MetroACCDao extends JpaRepository<MetroACC,Short> {
}
