package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TccSectionValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/3/22 14:52
 */
@Repository
public interface TccSectionValuesDao extends JpaRepository<TccSectionValues,Long>,
        JpaSpecificationExecutor<TccSectionValues> {

    /**
     * 根据是否是换乘站查找 断面
     * @param transferFlag 换乘站标志位
     * @return 断面
     */
    List<TccSectionValues> findAllByTransferFlag(Short transferFlag);
}
