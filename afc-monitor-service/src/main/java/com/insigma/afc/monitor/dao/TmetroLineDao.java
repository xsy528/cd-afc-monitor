package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmetroLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/26 14:40.
 * @Ticket :
 */
@Repository
public interface TmetroLineDao  extends JpaRepository<TmetroLine,Integer>, JpaSpecificationExecutor<TmetroLine> {
    /**
     * 根据线路Id获取线路名
     * @param lineId
     * @return
     */
    TmetroLine findByLineId(Short lineId);
}
