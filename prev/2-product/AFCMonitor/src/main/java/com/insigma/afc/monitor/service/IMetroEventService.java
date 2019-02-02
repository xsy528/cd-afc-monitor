package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.entity.TmoEquStatusCur;

import java.util.List;

/**
 * 事件相关的Service
 * 
 * @author CaiChunye
 */
public interface IMetroEventService {
	/**
	 * 获取TmoEquStatusCur表数据
	 * 
	 * @param nodeId
	 * @return
	 */
	List<TmoEquStatusCur> getEquStatusEntity(Long nodeId, Short statusId);

}
