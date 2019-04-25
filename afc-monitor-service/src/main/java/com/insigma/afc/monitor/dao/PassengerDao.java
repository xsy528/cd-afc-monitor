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

    class CountSpecification implements Specification<TmoOdFlowStats>{

        private Date date;
        private List<Integer> timeIntervals;
        private List<Integer> stationIds;
        private Short statType;

        public CountSpecification(Date date,List<Integer> timeIntervals,List<Integer> stationIds,Short statType){
            this.date = date;
            this.timeIntervals = timeIntervals;
            this.stationIds = stationIds;
            this.statType = statType;
        }

        @Override
        public Predicate toPredicate(Root<TmoOdFlowStats> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("gatheringDate"),date));
            predicates.add(builder.greaterThanOrEqualTo(root.get("timeIntervalId"),timeIntervals.get(0)));
            predicates.add(builder.lessThanOrEqualTo(root.get("timeIntervalId"),timeIntervals.get(1)));
            if (stationIds!=null&&!stationIds.isEmpty()) {
                predicates.add(root.get("stationId").in(stationIds));
            }
            if (statType==0) {
                query.groupBy(root.get("stationId"),root.get("ticketFamily"));
            }else {
                query.groupBy(root.get("stationId"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        }
    }
}
