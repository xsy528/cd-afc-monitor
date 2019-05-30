/*
 * 日期：2010-10-14
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.service.impl;

import com.insigma.afc.monitor.constant.OrderDirection;
import com.insigma.afc.monitor.constant.dic.AFCMackCode;
import com.insigma.afc.monitor.constant.dic.AFCModeCode;
import com.insigma.afc.monitor.dao.TmoCmdResultDao;
import com.insigma.afc.monitor.dao.TmoEquStatusCurDao;
import com.insigma.afc.monitor.dao.TmoModeBroadcastDao;
import com.insigma.afc.monitor.dao.TmoModeUploadInfoDao;
import com.insigma.afc.monitor.model.dto.condition.DeviceEventCondition;
import com.insigma.afc.monitor.model.dto.condition.ModeBroadcastCondition;
import com.insigma.afc.monitor.model.dto.condition.ModeCmdCondition;
import com.insigma.afc.monitor.model.dto.condition.ModeUploadCondition;
import com.insigma.afc.monitor.model.entity.TmoCmdResult;
import com.insigma.afc.monitor.model.entity.TmoEquStatusCur;
import com.insigma.afc.monitor.model.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.model.entity.TmoModeUploadInfo;
import com.insigma.afc.monitor.model.vo.ModeBroadcastInfo;
import com.insigma.afc.monitor.model.vo.ModeCmdInfo;
import com.insigma.afc.monitor.model.vo.ModeUploadInfo;
import com.insigma.afc.monitor.service.ModeService;
import com.insigma.afc.monitor.service.rest.TopologyService;
import com.insigma.commons.util.DateTimeUtil;
import com.insigma.commons.util.NodeIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.function.Function;

/**
 * Ticket: 模式服务实现类
 *
 * @author xuzhemin
 */
@Service
public class ModeServiceImpl implements ModeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModeServiceImpl.class);

    private TmoModeUploadInfoDao tmoModeUploadInfoDao;
    private TmoModeBroadcastDao tmoModeBroadcastDao;
    private TmoEquStatusCurDao tmoEquStatusCurDao;
    private TmoCmdResultDao tmoCmdResultDao;

    private TopologyService topologyService;

    @Override
    public Page<ModeUploadInfo> getModeUploadInfo(ModeUploadCondition condition) {
        List<Integer> stationIds = condition.getStationIds();
        Short modeCode = condition.getEntryMode();
        Date startTime = condition.getStartTime();
        Date endTime = condition.getEndTime();
        Page<TmoModeUploadInfo> tmoModeUploadInfoPage = tmoModeUploadInfoDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (stationIds != null && !stationIds.isEmpty()) {
                predicates.add(root.get("stationId").in(stationIds));
            }
            if (modeCode != null) {
                predicates.add(builder.equal(root.get("modeCode"), modeCode));
            }
            if (startTime != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("modeUploadTime"), startTime));
            }
            if (endTime != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("modeUploadTime"), endTime));
            }
            query.orderBy(builder.desc(root.get("modeUploadTime")), builder.asc(root.get("modeCode")));
            return builder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(condition.getPageNumber(), condition.getPageSize()));

        Set<Long> nodeIds = new HashSet<>();
        Map<Long, String> textMap = null;
        if (!tmoModeUploadInfoPage.isEmpty()) {
            for (TmoModeUploadInfo uploadInfo : tmoModeUploadInfoPage.getContent()) {
                nodeIds.add(uploadInfo.getLineId().longValue());
                nodeIds.add(uploadInfo.getStationId().longValue());
            }
            textMap = topologyService.getNodeTexts(nodeIds).getData();
        }
        return tmoModeUploadInfoPage.map(new TmoModeUploadInfoToModeUploadInfo(textMap));
    }

    @Override
    public Page<ModeBroadcastInfo> getModeBroadcastInfo(ModeBroadcastCondition condition) {
        List<Integer> stationIds = condition.getStationIds();
        Short modeCode = condition.getEntryMode();
        Long targetId = condition.getTargetId();
        Short broadCastStatus = condition.getBroadcastStatus();
        Short broadCastType = condition.getBroadcastType();
        Date startTime = condition.getStartTime();
        Date endTime = condition.getEndTime();
        int page = condition.getPageNumber();
        int pageSize = condition.getPageSize();
        String operatorId = condition.getOperatorId();

        Page<TmoModeBroadcast> tmoModeBroadcastPage = tmoModeBroadcastDao.findAll((root, query, builder) -> {
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
            if (targetId != null) {
                predicates.add(builder.equal(root.get("targetId"), NodeIdUtils.nodeIdStrategy.getNodeNo(targetId)));
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

        Set<Long> nodeIds = new HashSet<>();
        Map<Long, String> textMap = null;
        if (!tmoModeBroadcastPage.isEmpty()) {
            for (TmoModeBroadcast broadcast : tmoModeBroadcastPage.getContent()) {
                nodeIds.add(broadcast.getNodeId());
                nodeIds.add(broadcast.getTargetId());
                nodeIds.add(broadcast.getStationId().longValue());
            }
            textMap = topologyService.getNodeTexts(nodeIds).getData();
        }

        return tmoModeBroadcastPage.map(new TmoModeBroadcastToModeBroadcastInfo(textMap));
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
    public Page<ModeCmdInfo> getModeCmdSearch(ModeCmdCondition condition) {

        String operatorId = condition.getOperatorId();
        Integer cmdResult = condition.getCmdResult();
        List<Integer> stationIds = condition.getStationIds();
        Date startTime = condition.getStartTime();
        Date endTime = condition.getEndTime();
        Short cmdType = condition.getCmdType();

        //站点，操作员ID，指令结果，开始时间，结束时间，null，指令类型，页数，页数大小
        Page<TmoCmdResult> tmoCmdResultPage = tmoCmdResultDao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (stationIds != null && !stationIds.isEmpty()) {
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
        }, PageRequest.of(condition.getPageNumber(), condition.getPageSize()));

        Set<Long> nodeIds = new HashSet<>();
        Map<Long, String> textMap = null;
        if (!tmoCmdResultPage.isEmpty()) {
            for (TmoCmdResult tmoCmdResult : tmoCmdResultPage.getContent()) {
                nodeIds.add(tmoCmdResult.getStationId().longValue());
            }
            textMap = topologyService.getNodeTexts(nodeIds).getData();
        }
        return tmoCmdResultPage.map(new TmoCmdResultToModeCmdInfo(textMap));
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

    private class TmoModeUploadInfoToModeUploadInfo implements Function<TmoModeUploadInfo, ModeUploadInfo> {
        private Map<Long, String> textMap;

        TmoModeUploadInfoToModeUploadInfo(Map<Long, String> textMap) {
            this.textMap = textMap;
        }

        @Override
        public ModeUploadInfo apply(TmoModeUploadInfo tmoModeUploadInfo) {
            ModeUploadInfo modeUploadInfo = new ModeUploadInfo();
            modeUploadInfo.setLineName(textMap.get(tmoModeUploadInfo.getLineId().longValue()));
            modeUploadInfo.setStationName(textMap.get(tmoModeUploadInfo.getStationId().longValue()));
            modeUploadInfo.setUploadTime(DateTimeUtil.formatDate(tmoModeUploadInfo.getModeUploadTime()));
            modeUploadInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeUploadInfo
                    .getModeCode())));
            return modeUploadInfo;
        }
    }

    private class TmoModeBroadcastToModeBroadcastInfo implements Function<TmoModeBroadcast, ModeBroadcastInfo> {
        private Map<Long, String> textMap;

        TmoModeBroadcastToModeBroadcastInfo(Map<Long, String> textMap) {
            this.textMap = textMap;
        }

        @Override
        public ModeBroadcastInfo apply(TmoModeBroadcast tmoModeBroadcast) {
            ModeBroadcastInfo modeBroadcastInfo = new ModeBroadcastInfo();
            modeBroadcastInfo.setRecordId(tmoModeBroadcast.getRecordId());
            modeBroadcastInfo.setName(textMap.get(tmoModeBroadcast.getNodeId()));
            modeBroadcastInfo.setSourceName(textMap.get(tmoModeBroadcast.getStationId().longValue()));
            modeBroadcastInfo.setTargetName(textMap.get(tmoModeBroadcast.getTargetId()));
            modeBroadcastInfo.setModeBroadcastTime(DateTimeUtil.formatDate(tmoModeBroadcast
                    .getModeBroadcastTime()));
            modeBroadcastInfo.setMode(AFCModeCode.getInstance().getModeText(Integer.valueOf(tmoModeBroadcast
                    .getModeCode())));
            modeBroadcastInfo.setModeBroadcastType(tmoModeBroadcast.getBroadcastType() == 0 ? "手动" : "自动");
            modeBroadcastInfo.setModeEffectTime(DateTimeUtil.formatDate(tmoModeBroadcast.getModeEffectTime()));
            if (tmoModeBroadcast.getBroadcastStatus() == 0) {
                modeBroadcastInfo.setModeBroadcastStatus("未确认");
            } else if (tmoModeBroadcast.getBroadcastStatus() == 1) {
                modeBroadcastInfo.setModeBroadcastStatus("成功");
            } else if (tmoModeBroadcast.getBroadcastStatus() == 2) {
                modeBroadcastInfo.setModeBroadcastStatus("失败");
            }
            modeBroadcastInfo.setOperatorId(tmoModeBroadcast.getOperatorId());
            return modeBroadcastInfo;
        }
    }

    private class TmoCmdResultToModeCmdInfo implements Function<TmoCmdResult, ModeCmdInfo> {
        private Map<Long, String> textMap;

        TmoCmdResultToModeCmdInfo(Map<Long, String> textMap) {
            this.textMap = textMap;
        }

        @Override
        public ModeCmdInfo apply(TmoCmdResult tmoCmdResult) {
            //返回结果集合显示，显示实体类,一个代表一行
            ModeCmdInfo modeCmdInfo = new ModeCmdInfo();
            //序号，发送时间，操作员姓名/编号,车站/编号，模式名称,发送结果
            modeCmdInfo.setUploadTime(DateTimeUtil.formatDate(tmoCmdResult.getOccurTime()));
            modeCmdInfo.setOperatorId(tmoCmdResult.getOperatorId());
            modeCmdInfo.setStationName(textMap.get(tmoCmdResult.getStationId().longValue()));
            modeCmdInfo.setStationId(tmoCmdResult.getStationId());
            modeCmdInfo.setCmdName(tmoCmdResult.getCmdName());
            modeCmdInfo.setCmdResult(AFCMackCode.getInstance()
                    .getNameByValue(tmoCmdResult.getCmdResult().intValue())+"/"+tmoCmdResult.getCmdResult());
            return modeCmdInfo;
        }
    }

    @Autowired
    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
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
    public void setTmoEquStatusCurDao(TmoEquStatusCurDao tmoEquStatusCurDao) {
        this.tmoEquStatusCurDao = tmoEquStatusCurDao;
    }

    @Autowired
    public void setTmoCmdResultDao(TmoCmdResultDao tmoCmdResultDao) {
        this.tmoCmdResultDao = tmoCmdResultDao;
    }
}
