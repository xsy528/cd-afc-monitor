package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-28:18:51
 */
@Repository
public interface TmoModeUploadInfoDao extends JpaRepository<TmoModeUploadInfo,Long>,
        JpaSpecificationExecutor<TmoModeUploadInfo> {
}
