package com.insigma.afc.monitor.dao;

import com.insigma.afc.monitor.model.entity.TmoOdFlowStats;
import oracle.jdbc.proxy.annotation.Pre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ticket:客流查询Dao
 *
 * @author xingshaoya
 * create time: 2019-03-22 17:05
 */
@Repository
public interface PassengerDao extends JpaRepository<TmoOdFlowStats,Integer>,JpaSpecificationExecutor<TmoOdFlowStats>,
        PassengerRepository{
}
