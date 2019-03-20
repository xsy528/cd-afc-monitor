/*
 * 日期：2010-10-14
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.OrderDirection;
import com.insigma.afc.monitor.constant.dic.DeviceStatus;
import com.insigma.afc.monitor.dao.*;
import com.insigma.afc.monitor.model.dto.condition.CommandLogCondition;
import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
import com.insigma.afc.monitor.model.dto.condition.ModeCmdCondition;
import com.insigma.afc.monitor.model.entity.*;
import com.insigma.afc.monitor.service.IMetroNodeStatusService;
import com.insigma.afc.monitor.service.ModeService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.constant.AFCNodeLevel;
import com.insigma.commons.util.DateTimeUtil;
import com.insigma.commons.util.NodeIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Ticket: 模式服务实现类
 *
 * @author xuzhemin
 */
@Service
public class ModeServiceImpl implements ModeService {

    private static final Logger logger = LoggerFactory.getLogger(ModeServiceImpl.class);

    private IMetroNodeStatusService modeNodeStatusService;
    private TmoModeUploadInfoDao tmoModeUploadInfoDao;
    private TmoModeBroadcastDao tmoModeBroadcastDao;
    private TmoItemStatusDao tmoItemStatusDao;
    private TmoEquStatusCurDao tmoEquStatusCurDao;
    private TmoCmdResultDao tmoCmdResultDao;

    @Override
    public Page<TmoModeUploadInfo> getModeUploadInfo(List<Integer> stationIds, Short modeCode,
                                                     Short broadCastStatus, Date startTime,
                                                     Date endTime, int page, int pageSize) {
        return tmoModeUploadInfoDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (stationIds != null && !stationIds.isEmpty()) {
                predicates.add(root.get("stationId").in(stationIds));
            }
            if (modeCode != null) {
                predicates.add(builder.equal(root.get("modeCode"), modeCode));
            }
            if (broadCastStatus != null) {
                predicates.add(builder.equal(root.get("broadcastStatus"), broadCastStatus));
            }
            if (startTime != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("modeUploadTime"), startTime));
            }
            if (endTime != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("modeUploadTime"), endTime));
            }
            query.orderBy(builder.desc(root.get("modeUploadTime")), builder.asc(root.get("modeCode")));
            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, pageSize));
    }

    @Override
    public Page<TmoModeBroadcast> getModeBroadcastInfo(List<Integer> stationIds, Short modeCode, String operatorId,
                                                       Integer desStationId, Short broadCastStatus, Short broadCastType,
                                                       Date startTime, Date endTime, int page, int pageSize) {
        return tmoModeBroadcastDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (stationIds != null && !stationIds.isEmpty()) {
                predicates.add(root.get("stationId").in(stationIds));
            }
            if (modeCode != null) {
                predicates.add(builder.equal(root.get("modeCode"), modeCode));
            }
            if (broadCastStatus != null) {
                predicates.add(builder.equal(root.get("broadcastStatus"), broadCastStatus));
            }
            if (broadCastType != null) {
                predicates.add(builder.equal(root.get("broadcastType"), broadCastType));
            }
            if (operatorId != null) {
                predicates.add(builder.equal(root.get("operatorId"), operatorId));
            }
            if (desStationId != null) {
                predicates.add(builder.equal(root.get("targetId"), desStationId));
            }
            if (startTime != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("modeEffectTime"), startTime));
            }
            if (endTime != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("modeEffectTime"), endTime));
            }
            query.orderBy(builder.desc(root.get("modeEffectTime")), builder.desc(root.get("modeBroadcastTime")),
                    builder.asc(root.get("targetId")));
            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, pageSize));
    }

    @Override
    public List<TmoModeUploadInfo> getModeUpload(long nodeId) {

        return tmoModeUploadInfoDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.greaterThanOrEqualTo(root.get("modeUploadTime"), DateTimeUtil.beginDate(new Date()
			)));
            predicates.add(builder.lessThanOrEqualTo(root.get("modeUploadTime"), new Date()));
            switch (NodeIdUtils.nodeIdStrategy.getNodeLevel(nodeId)) {
                case LC:
                    predicates.add(builder.equal(root.get("lineId"), (short) nodeId));
                    break;
                case SC:
                    predicates.add(builder.equal(root.get("stationId"), (int) nodeId));
                    break;
                default:
            }
            //排序
            query.orderBy(builder.desc(root.get("modeUploadTime")));
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Override
    public List<TmoModeBroadcast> getModeBroadcast() {
        return tmoModeBroadcastDao.findAll((root, query, builder) -> {
            //排序
            query.orderBy(builder.asc(root.get("modeBroadcastTime")), builder.asc(root.get("targetId")));
            //广播时间为当日的数据
            return builder.greaterThanOrEqualTo(root.get("modeBroadcastTime"), DateTimeUtil.beginDate(new Date()));
        });
    }

    @Override
    public Page<TmoCmdResult> getModeCmdSearch(ModeCmdCondition condition) {

        String operatorId = condition.getOperatorId();
        Integer cmdResult = condition.getCmdResult();
        List<Integer> stationIds = condition.getStationIds();
        Date startTime = condition.getStartTime();
        Date endTime = condition.getEndTime();
        Short cmdType = condition.getCmdType();
        int page = condition.getPageNumber();
        Integer pageSize = condition.getPageSize();

        //站点，操作员ID，指令结果，开始时间，结束时间，null，指令类型，页数，页数大小
        return tmoCmdResultDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (stationIds != null && stationIds.isEmpty()) {
                predicates.add(root.get("stationId").in(stationIds));
                //将站点ID存入root中
            }
            if (operatorId != null) {
                predicates.add(builder.equal(root.get("operatorId"), operatorId));
                //比较操作员ID
            }
            if (cmdResult != null) {
                predicates.add(builder.equal(root.get("cmdResult"), cmdResult));
                //比较指令结果
            }
            if (startTime != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("occurTime"), startTime));
            }
            if (endTime != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("occurTime"), endTime));
            }
            if (cmdType != null) {
                predicates.add(builder.equal(root.get("cmdType"), cmdType));
                //比较指令类型
            }
            query.orderBy(builder.desc(root.get("occurTime")));
            //以occurTime降序
            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, pageSize));
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

    @Override
    public boolean saveOrUpdateCurrentModeId(long nodeId, short newModeId, Date changeTime) {
        short lineId = NodeIdUtils.nodeIdStrategy.getLineId(nodeId);
        int stationId = NodeIdUtils.nodeIdStrategy.getStationId(nodeId);
        AFCNodeLevel nodeType = NodeIdUtils.nodeIdStrategy.getNodeLevel(nodeId);

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

        return tmoEquStatusCurDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Date startTime = condition.getStartTime();
            if (startTime != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("occurTime"), startTime));
            }
            Date endTime = condition.getEndTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
            predicates.add(builder.lessThanOrEqualTo(root.get("occurTime"), endTime == null ? calendar.getTime() :
					endTime));
            if (equType != null && !equType.isEmpty()) {
                root.get("deviceType").in(equType);
            }
            if (deviceList != null && !deviceList.isEmpty()) {
                root.get("nodeId").in(deviceList);
            }
            if (orderField != null && orderType != null) {
                switch (orderType) {
                    case ASC: {
                        query.orderBy(builder.asc(root.get(orderField)));
                        break;
                    }
                    case DESC: {
                        query.orderBy(builder.desc(root.get(orderField)));
                    }
                    default:
                }
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, condition.getMaxCount()))
                .getContent();
    }

    @Override
    public TmoItemStatus getCurrentTmoItemMode(long nodeId) {
        return tmoItemStatusDao.findTopByLineIdAndStationIdAndNodeId(NodeIdUtils.nodeIdStrategy.getLineId(nodeId),
                NodeIdUtils.nodeIdStrategy.getStationId(nodeId), nodeId);
    }

    @Override
    public List<TmoItemStatus> getAllTmoItemModeList(long stationID) {
        return tmoItemStatusDao.findByLineIdAndStationIdAndNodeId(NodeIdUtils.nodeIdStrategy.getLineId(stationID),
                NodeIdUtils.nodeIdStrategy.getStationId(stationID), NodeIdUtils.nodeIdStrategy.getNodeNo(stationID));
    }

    @Override
    public void saveModeUploadInfo(TmoModeUploadInfo tmoModeUploadInfo) {
        tmoModeUploadInfoDao.save(tmoModeUploadInfo);
    }

    @Override
    public void saveModeBroadcastInfo(TmoModeBroadcast modeBroadcast) {
        tmoModeBroadcastDao.save(modeBroadcast);
    }

    @Autowired
    public void setModeNodeStatusService(IMetroNodeStatusService modeNodeStatusService) {
        this.modeNodeStatusService = modeNodeStatusService;
    }

    @Autowired
    public void setTmoModeUploadInfoDao(TmoModeUploadInfoDao tmoModeUploadInfoDao) {
        this.tmoModeUploadInfoDao = tmoModeUploadInfoDao;
    }

    @Autowired
    public void setTmoModeBroadcastDao(TmoModeBroadcastDao tmoModeBroadcastDao) {
        this.tmoModeBroadcastDao = tmoModeBroadcastDao;
    }

    @Autowired
    public void setTmoItemStatusDao(TmoItemStatusDao tmoItemStatusDao) {
        this.tmoItemStatusDao = tmoItemStatusDao;
    }

    @Autowired
    public void setTmoEquStatusCurDao(TmoEquStatusCurDao tmoEquStatusCurDao) {
        this.tmoEquStatusCurDao = tmoEquStatusCurDao;
    }

    @Autowired
    public void setTmoCmdResultDao(TmoCmdResultDao tmoCmdResultDao) {
        this.tmoCmdResultDao = tmoCmdResultDao;
    }
}
