/* 
 * 日期：2010-10-14
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.OrderDirection;
import com.insigma.afc.monitor.dao.TmoEquStatusCurDao;
import com.insigma.afc.monitor.dao.TmoItemStatusDao;
import com.insigma.afc.monitor.dao.TmoModeBroadcastDao;
import com.insigma.afc.monitor.dao.TmoModeUploadInfoDao;
import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
import com.insigma.afc.monitor.model.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.model.entity.TmoItemStatus;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.monitor.service.ModeService;
import com.insigma.afc.topology.constant.AFCNodeLevel;
import com.insigma.afc.topology.exception.NodeNotFoundException;
import com.insigma.afc.topology.service.TopologyService;
import com.insigma.afc.topology.util.NodeIdUtils;
import com.insigma.commons.util.lang.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Ticket: 模式服务实现类
 * @author xuzhemin
 */
@Service
public class ModeServiceImpl implements ModeService {

	private static final Logger logger = LoggerFactory.getLogger(ModeServiceImpl.class);

	@Autowired
	private TopologyService topologyService;
	@Autowired
	private IMetroNodeStatusService modeNodeStatusService;
	@Autowired
	private TmoModeUploadInfoDao tmoModeUploadInfoDao;
	@Autowired
	private TmoModeBroadcastDao tmoModeBroadcastDao;

	@Autowired
	private TmoItemStatusDao tmoItemStatusDao;
	@Autowired
	private TmoEquStatusCurDao tmoEquStatusCurDao;

	@Override
	public List<TmoModeUploadInfo> getModeUpload(long nodeId){

		if(topologyService.getNode(nodeId)==null){
			throw new NodeNotFoundException(nodeId);
		}

		return tmoModeUploadInfoDao.findAll((root, query, builder)->{
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(builder.greaterThanOrEqualTo(root.get("modeUploadTime"), DateTimeUtil.beginDate(new Date())));
			predicates.add(builder.lessThanOrEqualTo(root.get("modeUploadTime"),new Date()));
			switch (NodeIdUtils.getNodeLevel(nodeId)) {
				case LC:
					predicates.add(builder.equal(root.get("lineId"),(short) nodeId));
					break;
				case SC:
					predicates.add(builder.equal(root.get("stationId"),(int) nodeId));
					break;
				default:
			}
			//排序
			query.orderBy(builder.desc(root.get("modeUploadTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
	}

	@Override
	public List<TmoModeBroadcast> getModeBroadcast(){
		return tmoModeBroadcastDao.findAll((root,query,builder)->{
			//排序
			query.orderBy(builder.asc(root.get("modeBroadcastTime")),builder.asc(root.get("targetId")));
			//广播时间为当日的数据
			return builder.greaterThanOrEqualTo(root.get("modeBroadcastTime"),DateTimeUtil.beginDate(new Date()));
		});
	}

	@Override
	public long handleModeCommand(final long senderId, long stationId, short newModeId, long operationId) {

		if (saveOrUpdateCurrentModeId(senderId, newModeId, new Date())) {
			// 保存成功
			return 0L;
		} else {
			// 保存不成功
			return 1L;
		}
	}

	/**
	 * 只简单更新数据库中的当前模式
	 * 
	 * @param nodeId
	 * @param newModeId
	 * @return
	 */
	@Override
	public boolean saveOrUpdateCurrentModeId(long nodeId, short newModeId, Date changeTime) {
		short lineId = NodeIdUtils.getLineId(nodeId);
		int stationId = NodeIdUtils.getStationId(nodeId);
		AFCNodeLevel nodeType = NodeIdUtils.getNodeLevel(nodeId);

		if (nodeType == null) {
			return false;
		}

		TmoItemStatus tmoItemStatus = modeNodeStatusService.getTmoItemStatus(lineId, stationId, nodeId);
		if (tmoItemStatus == null) {
			logger.warn("接收到上传车站模式更新信息，新增该节点。对应的节点：0x" + Long.toHexString(nodeId) + "状态信息为空。");
			tmoItemStatus = new TmoItemStatus();
			tmoItemStatus.setLineId(lineId);
			tmoItemStatus.setStationId(stationId);
			tmoItemStatus.setNodeId(nodeId);
			tmoItemStatus.setNodeType(nodeType.getStatusCode().shortValue());
			tmoItemStatus.setCurrentModeCode(newModeId);
			tmoItemStatus.setModeChangeTime(changeTime);
			tmoItemStatus.setUpdateTime(new Date());
			tmoItemStatusDao.save(tmoItemStatus);
			return true;
		}

		short lastModeCode = tmoItemStatus.getCurrentModeCode();

		if (newModeId == tmoItemStatus.getCurrentModeCode()) {
			return true;
		}

		//		if (newModeId == AFCModeCode.END_URGENCY_MODE_CODE) {
		//			// 解除紧急模式更新回上次的模式
		//			newModeId = tmoItemStatus.getLastModeCode();
		//			if (lastModeCode != AFCModeCode.START_URGENCY_MODE_CODE) {
		//				// 如果当前非紧急模式，收到解除紧急模式，直接返回
		//				return true;
		//			}
		//		}
		tmoItemStatus.setLastModeCode(lastModeCode);
		tmoItemStatus.setCurrentModeCode(newModeId);
		tmoItemStatus.setModeChangeTime(changeTime);
		tmoItemStatusDao.save(tmoItemStatus);
		return true;
	}

	@Override
	public List<TmoEquStatusCur> getEquStatusList(DeviceEventCondition condition) {

		List<Short> equType = condition.getDevTypeList();
		String orderField = condition.getOrderField();
		OrderDirection orderType = condition.getOrderType();
		List<Long> deviceList = condition.getNodeIds();

		return tmoEquStatusCurDao.findAll((root,query,builder)->{
			List<Predicate> predicates = new ArrayList<>();
			Date startTime = condition.getStartTime();
			if (startTime!=null){
				predicates.add(builder.greaterThanOrEqualTo(root.get("occurTime"),startTime));
			}
			Date endTime = condition.getEndTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
			predicates.add(builder.lessThanOrEqualTo(root.get("occurTime"),endTime==null?calendar.getTime():endTime));
			if (equType!=null&&!equType.isEmpty()){
				root.get("deviceType").in(equType);
			}
			if (deviceList!=null&&!deviceList.isEmpty()){
				root.get("nodeId").in(deviceList);
			}
			if (orderField!=null&&orderType!=null){
				switch (orderType){
					case ASC:{
						query.orderBy(builder.asc(root.get(orderField)));
						break;
					}
					case DESC:{
						query.orderBy(builder.desc(root.get(orderField)));
					}
					default:
				}
			}
			return builder.and(predicates.toArray(new Predicate[0]));
		}, PageRequest.of(0,condition.getMaxCount()))
				.getContent();
	}

	@Override
	public TmoItemStatus getCurrentTmoItemMode(long nodeId) {
		return tmoItemStatusDao.findTopByLineIdAndStationIdAndNodeId(NodeIdUtils.getLineId(nodeId),
				NodeIdUtils.getStationId(nodeId),nodeId);
	}

	@Override
	public List<TmoItemStatus> getAllTmoItemModeList(long stationID) {
		return tmoItemStatusDao.findByLineIdAndStationIdAndNodeId(NodeIdUtils.getLineId(stationID),
				NodeIdUtils.getStationId(stationID),NodeIdUtils.getNodeNo(stationID));
	}

	@Override
	public void saveModeUploadInfo(TmoModeUploadInfo tmoModeUploadInfo) {
		tmoModeUploadInfoDao.save(tmoModeUploadInfo);
	}

	@Override
	public void saveModeBroadcastInfo(TmoModeBroadcast modeBroadcast) {
		tmoModeBroadcastDao.save(modeBroadcast);
	}
}
