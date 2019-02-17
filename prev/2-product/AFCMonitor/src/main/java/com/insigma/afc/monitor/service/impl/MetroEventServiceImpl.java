package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.dao.TmoEquStatusCurDao;
import com.insigma.afc.monitor.model.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.service.IMetroEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 事件查询
 * 
 * @author CaiChunye
 */
@Service
public class MetroEventServiceImpl implements IMetroEventService {

	private static final Logger logger = LoggerFactory.getLogger(MetroEventServiceImpl.class);

	private TmoEquStatusCurDao tmoEquStatusCurDao;

	@Autowired
	public MetroEventServiceImpl(TmoEquStatusCurDao tmoEquStatusCurDao){
		this.tmoEquStatusCurDao = tmoEquStatusCurDao;
	}

	@Override
	public List<TmoEquStatusCur> getEquStatusEntity(Long nodeId, Short statusId) {

		return tmoEquStatusCurDao.findAll((root,query,builder)->{

			//根据deviceId和occurTime排序
			query.orderBy(builder.desc(root.get("deviceId")),builder.desc(root.get("occurTime")));
			List<Predicate> predicates = new ArrayList<>();
			if (nodeId!=null){
				predicates.add(builder.equal(root.get("nodeId"),nodeId));
			}
			if (statusId!=null){
				predicates.add(builder.equal(root.get("statusId"),statusId));
			}
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
	}

}
