package com.insigma.acc.wz.web.service.impl;

import com.insigma.acc.wz.web.service.NodeStatusService;
import com.insigma.acc.wz.web.util.NodeUtils;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.entity.TmoModeBroadcast;
import com.insigma.afc.monitor.entity.TmoModeUploadInfo;
import com.insigma.commons.op.OPException;
import com.insigma.commons.query.QueryFilter;
import com.insigma.commons.service.CommonDAO;
import com.insigma.commons.util.lang.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-03:10:39
 */
@Service
public class NodeStatusServiceImpl implements NodeStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeStatusServiceImpl.class);

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public List<TmoModeUploadInfo> getModeUpload(long nodeId){
        List<TmoModeUploadInfo> tmoModeUploadInfos = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        QueryFilter filters = new QueryFilter();
        filters.propertyFilter("modeUploadTime", calendar.getTime(), ">=");
        filters.propertyFilter("modeUploadTime", new Date(), "<=");
        filters.addOrderBy("modeUploadTime", false);
        AFCNodeLevel nodeLevel = NodeUtils.getNodeLevel(nodeId);
        if (nodeLevel != null) {
            switch (nodeLevel) {
                case LC:
                    filters.propertyFilter("lineId", (short) nodeId);
                    break;
                case SC:
                    filters.propertyFilter("stationId", (int) nodeId);
                    break;
                default:
                    break;
            }
        }
        try{
            tmoModeUploadInfos = commonDAO.fetchListByFilter(filters, TmoModeUploadInfo.class);
        }catch (OPException e){
            LOGGER.error("查询模式上传信息异常",e);
        }
        return tmoModeUploadInfos;
    }

    @Override
    public List<TmoModeBroadcast> getModeBroadcast(){
        QueryFilter filters = new QueryFilter();
        Date date = DateTimeUtil.beginDate(new Date());
        filters.propertyFilter("modeBroadcastTime",date , ">=");
        filters.addOrderBy("modeBroadcastTime", true);
        filters.addOrderBy("targetId", true);
        List<TmoModeBroadcast> tmoModeBroadcasts = new ArrayList<>();
        try{
            tmoModeBroadcasts = commonDAO.fetchListByFilter(filters, TmoModeBroadcast.class);
        }catch (OPException e){
            LOGGER.error("查询模式上传信息异常",e);
        }
        return tmoModeBroadcasts;
    }


    public void setCommonDAO(CommonDAO commonDAO){
        this.commonDAO = commonDAO;
    }
}
