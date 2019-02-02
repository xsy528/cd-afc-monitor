package com.insigma.afc.topology.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insigma.afc.topology.model.entity.MetroLine;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-25:13:59
 */
@Repository
public interface MetroLineDao extends JpaRepository<MetroLine,Short> {

}
