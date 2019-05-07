/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.dao.impl;

import com.insigma.afc.monitor.dao.PassengerRepository;
import com.insigma.afc.monitor.model.entity.TmoOdFlowStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/24 22:44
 */
@Component
public class PassengerRepositoryImpl implements PassengerRepository {

    private EntityManager entityManager;

    @Autowired
    public PassengerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tuple> findAllBarAndPie(Date gatheringDate, Integer startTimeInterval, Integer endTimeInterval,
                                                 List<Integer> stationIds, Short ticketFamily) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<TmoOdFlowStats> root = query.from(TmoOdFlowStats.class);

        //select
        Selection sumTotalIn = builder.sum(root.get("totalIn")).alias("totalIn");
        Selection sumTotalOut = builder.sum(root.get("totalOut")).alias("totalOut");
        Selection sumSaleCount = builder.sum(root.get("saleCount")).alias("saleCount");
        Selection sumAddCount = builder.sum(root.get("addCount")).alias("addCount");
        Selection stationId = root.get("stationId").alias("stationId");
        query.multiselect(sumTotalIn, sumTotalOut, sumSaleCount, sumAddCount, stationId);

        //where
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("gatheringDate"), gatheringDate));
        predicates.add(builder.greaterThanOrEqualTo(root.get("timeIntervalId"), startTimeInterval));
        predicates.add(builder.lessThanOrEqualTo(root.get("timeIntervalId"), endTimeInterval));
        if (stationIds != null && !stationIds.isEmpty()) {
            predicates.add(root.get("stationId").in(stationIds));
        }
        if (ticketFamily != null) {
            predicates.add(builder.equal(root.get("ticketFamily"), ticketFamily));
        }
        query.groupBy(root.get("stationId"));
        query.orderBy(builder.asc(root.get("stationId")));
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Tuple> findAllSeries(Date gatheringDate, Integer startTimeInterval, Integer endTimeInterval,
                                              List<Integer> stationIds, Short ticketFamily) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<TmoOdFlowStats> root = query.from(TmoOdFlowStats.class);

       //select
        Selection sumTotalIn = builder.sum(root.get("totalIn")).alias("totalIn");
        Selection sumTotalOut = builder.sum(root.get("totalOut")).alias("totalOut");
        Selection sumSaleCount = builder.sum(root.get("saleCount")).alias("saleCount");
        Selection sumAddCount = builder.sum(root.get("addCount")).alias("addCount");
        Selection stationId = root.get("stationId").alias("stationId");
        Selection timeIntervalId = root.get("timeIntervalId").alias("timeIntervalId");
        query.multiselect(sumTotalIn, sumTotalOut, sumSaleCount, sumAddCount, stationId, timeIntervalId);

        //where
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("gatheringDate"), gatheringDate));
        predicates.add(builder.greaterThanOrEqualTo(root.get("timeIntervalId"), startTimeInterval));
        predicates.add(builder.lessThanOrEqualTo(root.get("timeIntervalId"), endTimeInterval));
        if (stationIds != null && !stationIds.isEmpty()) {
            predicates.add(root.get("stationId").in(stationIds));
        }
        if (ticketFamily != null) {
            predicates.add(builder.equal(root.get("ticketFamily"), ticketFamily));
        }
        query.groupBy(root.get("stationId"), root.get("timeIntervalId"));
        query.orderBy(builder.asc(root.get("stationId")), builder.asc(root.get("timeIntervalId")));
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Page<Tuple> findAll(Date gatheringDate, Integer startTimeInterval, Integer endTimeInterval, List<Integer>
            stationIds, Short statType, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        CriteriaQuery<Tuple> countQuery = builder.createTupleQuery();

        Root<TmoOdFlowStats> root = query.from(TmoOdFlowStats.class);
        Root<TmoOdFlowStats> countRoot = countQuery.from(TmoOdFlowStats.class);

        //select
        List<Selection> selections = new ArrayList<>();
        selections.add(builder.sum(root.get("totalIn")).alias("totalIn"));
        selections.add(builder.sum(root.get("totalOut")).alias("totalOut"));
        selections.add(builder.sum(root.get("saleCount")).alias("saleCount"));
        selections.add(builder.sum(root.get("addCount")).alias("addCount"));
        selections.add(root.get("stationId").alias("stationId"));
        //count
        Selection countSelection = builder.count(countRoot.get("stationId")).alias("total");

        //where
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("gatheringDate"), gatheringDate));
        predicates.add(builder.greaterThanOrEqualTo(root.get("timeIntervalId"), startTimeInterval));
        predicates.add(builder.lessThanOrEqualTo(root.get("timeIntervalId"), endTimeInterval));
        if (stationIds != null && !stationIds.isEmpty()) {
            predicates.add(root.get("stationId").in(stationIds));
        }
        if (statType==0){
            //按票种分组
            selections.add(root.get("ticketFamily").alias("ticketFamily"));
            query.groupBy(root.get("stationId"),root.get("ticketFamily"));
            query.orderBy(builder.asc(root.get("stationId")),builder.asc(root.get("ticketFamily")));
        }else if (statType==1){
            //按车站分组
            query.groupBy(root.get("stationId"));
            query.orderBy(builder.asc(root.get("stationId")));
        }
        query.multiselect(selections.toArray(new Selection[0]));
        Predicate[] predicates1 = predicates.toArray(new Predicate[0]);
        query.where(predicates1);

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageSize()*pageable.getPageNumber());
        typedQuery.setMaxResults(pageable.getPageSize());

        countQuery.multiselect(countSelection);
        countQuery.where(predicates1);
        Long total = entityManager.createQuery(countQuery).getSingleResult().get("total",Long.class);

        return new PageImpl<>(typedQuery.getResultList(),pageable,total);
    }
}