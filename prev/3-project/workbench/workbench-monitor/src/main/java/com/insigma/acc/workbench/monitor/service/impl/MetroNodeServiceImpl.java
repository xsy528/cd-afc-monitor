package com.insigma.acc.workbench.monitor.service.impl;

import com.insigma.acc.workbench.monitor.dao.MetroACCDao;
import com.insigma.acc.workbench.monitor.dao.MetroDeviceDao;
import com.insigma.acc.workbench.monitor.dao.MetroLineDao;
import com.insigma.acc.workbench.monitor.dao.MetroStationDao;
import com.insigma.acc.workbench.monitor.model.entity.MetroACC;
import com.insigma.acc.workbench.monitor.model.entity.MetroLine;
import com.insigma.acc.workbench.monitor.model.entity.MetroNode;
import com.insigma.acc.workbench.monitor.model.vo.ImageLocation;
import com.insigma.acc.workbench.monitor.model.vo.NodeItem;
import com.insigma.acc.workbench.monitor.model.vo.TextLocation;
import com.insigma.acc.workbench.monitor.service.MetroNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetroNodeServiceImpl implements MetroNodeService {

    @Autowired
    private MetroDeviceDao metroDeviceDao;
    @Autowired
    private MetroACCDao metroACCDao;
    @Autowired
    private MetroLineDao metroLineDao;
    @Autowired
    private MetroStationDao metroStationDao;

    private Map<String,Integer> imagePathMap = new HashMap<>();

    @Override
    public NodeItem getNodeItem() {
        NodeItem root = new NodeItem();
        List<MetroACC> metroACCS = metroACCDao.findAll();
        if (metroACCS.isEmpty()){
            throw new RuntimeException();
        }

        List<MetroLine> metroLines = metroLineDao.findAll();
        return root;
    }

    public NodeItem metroNodeToNodeItem(MetroNode metroNode){
        NodeItem nodeItem = new NodeItem();
        nodeItem.setNodeType(metroNode.getNodeType());
        nodeItem.setNodeId(metroNode.getNodeId());
        nodeItem.setName(metroNode.getName());

        ImageLocation imageLocation = new ImageLocation();
        imageLocation.setX(metroNode.getImageX());
        imageLocation.setY(metroNode.getImageY());
        imageLocation.setAngle(metroNode.getImageAngle());

        TextLocation textLocation = new TextLocation();
        textLocation.setX(metroNode.getTextX());
        textLocation.setY(metroNode.getTextY());
        textLocation.setAngle(metroNode.getTextAngle());

        return nodeItem;
    }

    public void setMetroDeviceDao(MetroDeviceDao metroDeviceDao) {
        this.metroDeviceDao = metroDeviceDao;
    }

    public void setMetroACCDao(MetroACCDao metroACCDao) {
        this.metroACCDao = metroACCDao;
    }

    public void setMetroLineDao(MetroLineDao metroLineDao) {
        this.metroLineDao = metroLineDao;
    }

    public void setMetroStationDao(MetroStationDao metroStationDao) {
        this.metroStationDao = metroStationDao;
    }
}
