package com.insigma.acc.wz.web.service.impl;

import com.insigma.acc.wz.web.model.vo.ImageLocation;
import com.insigma.acc.wz.web.model.vo.NodeItem;
import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.acc.wz.web.model.vo.TextLocation;
import com.insigma.acc.wz.web.service.FileService;
import com.insigma.acc.wz.web.service.NodeService;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.constant.ApplicationKey;
import com.insigma.afc.dic.DeviceStatus;
import com.insigma.afc.manager.SystemConfigKey;
import com.insigma.afc.monitor.listview.equstatus.EquStatusViewItem;
import com.insigma.afc.monitor.listview.equstatus.StationStatustViewItem;
import com.insigma.afc.monitor.map.GraphicMapGenerator;
import com.insigma.afc.monitor.map.builder.GraphicMapBuilder;
import com.insigma.afc.topology.*;
import com.insigma.afc.view.provider.CommonTreeProvider;
import com.insigma.commons.application.Application;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.graphic.ImageGraphicItem;
import com.insigma.commons.editorframework.graphic.TextGraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.ui.tree.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ticket: 节点服务实现类
 * author: xuzhemin
 */
@Service
public class NodeServiceImpl implements NodeService {

    private static Logger logger = LoggerFactory.getLogger(NodeServiceImpl.class);

    private static int online = 0;
    private static int OFF_LINE = 1;
    private static int STATION_OFF = 3;
    private static int STATION_USELESS = 9;

    @Autowired
    private FileService fileService;

    @Override
    public Result<NodeItem> getNodeTree(){
        GraphicMapGenerator mapGenerator = new GraphicMapGenerator();
        GraphicMapBuilder graphicMapBuilder = new GraphicMapBuilder();
        mapGenerator.setGraphicMapBuilder(graphicMapBuilder);
        //获取根结点
        ActionTreeNode actionTreeNode = mapGenerator.getMapTreeRootNode(true);
        //转化图片
        ActionTreeNode root = perform(actionTreeNode);

        MapItem rootMapItem = (MapItem) root.getValue();
        NodeItem nodeItem = mapItemToNodeItem(rootMapItem);
        return Result.success(nodeItem);
    }

    @Override
    public Result<NodeItem> getNodeSimpleTree(){
        CommonTreeProvider commonTreeProvider = new CommonTreeProvider();
        commonTreeProvider.setDepth(AFCNodeLevel.SLE);
        TreeNode treeNode = commonTreeProvider.getTree();
        return Result.success(treeNodeToNodeItem(treeNode));
    }

    public NodeItem treeNodeToNodeItem(TreeNode treeNode){
        NodeItem root = new NodeItem();
        Object key = treeNode.getKey();
        if (key!=null&&key instanceof MetroNode){
            MetroNode metroNode = (MetroNode)key;
            root.setNodeId(metroNode.id());
            root.setNodeType(metroNode.level().toString());
        }else {
            root.setNodeId(-1);
        }
        root.setName(treeNode.getText());
        List<TreeNode> treeNodes = treeNode.getChilds();
        if (treeNodes!=null&&!treeNodes.isEmpty()){
            List<NodeItem> subNodeItems = new ArrayList<>();
            root.setSubItems(subNodeItems);
            for (TreeNode subTreeNode:treeNodes) {
                NodeItem subNodeItem = treeNodeToNodeItem(subTreeNode);
                subNodeItem.setPid(root.getNodeId());
                subNodeItems.add(subNodeItem);
            }
        }
        return root;
    }


    /**
     * 将MapItem转化为NodeItem
     * @param mapItem 图元
     * @return nodeItem
     */
    public NodeItem mapItemToNodeItem(MapItem mapItem) {

        MetroNode metroNode = (MetroNode) mapItem.getValue();
        NodeItem nodeItem = new NodeItem();
        nodeItem.setNodeId(metroNode.id());
        nodeItem.setName(metroNode.name());
        nodeItem.setNodeType(metroNode.level().toString());
        nodeItem.setImageUrl(fileService.getResourceIndex(metroNode.getPicName()));

        //获取图片信息
        AFCNodeLocation imgLocation = metroNode.getImageLocation();
        ImageGraphicItem imageGraphicItem = (ImageGraphicItem) mapItem.getItems().get(0);
        List<Integer> imageIndexList = new ArrayList<>();
        for (String imagePath:imageGraphicItem.getImagesPath()){
            imageIndexList.add(fileService.getResourceIndex(imagePath));
        }
        nodeItem.setIcon(new ImageLocation(imageIndexList, imgLocation.getX(), imgLocation.getY(),
                imgLocation.getAngle()));

        //获取文字信息
        AFCNodeLocation txtLocation = metroNode.getTextLocation();
        TextGraphicItem textGraphicItem = (TextGraphicItem)mapItem.getItems().get(1);
        nodeItem.setText(new TextLocation(textGraphicItem.getText(),txtLocation.getX(),txtLocation.getY(),
                txtLocation.getAngle()));

        //递归构造
        List<MapItem> mapItems = mapItem.getMapItemList();
        if (mapItems==null||mapItems.isEmpty()){
            return nodeItem;
        }
        List<NodeItem> subNodeItems = new ArrayList<>();
        nodeItem.setSubItems(subNodeItems);
        for (MapItem subMapItem:mapItems){
            NodeItem subNodeItem = mapItemToNodeItem(subMapItem);
            subNodeItem.setPid(nodeItem.getNodeId());
            subNodeItems.add(subNodeItem);
        }
        return nodeItem;
    }

    public ActionTreeNode perform(ActionTreeNode actionTreeNode) {
        ActionTreeNode root = actionTreeNode;
        try {
            logger.debug("开始监控设备信息 ");
            if (root == null) {
                logger.debug("根节点信息为空");
                return root;
            }
            while (root.getParentNode() != null) {
                root = root.getParentNode();
            }
            //根据status设置图片
            doimage(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    //根据状态转化图片
    public ActionTreeNode doimage(ActionTreeNode root) {
        MapItem mapitem = (MapItem) root.getValue();
        // 通信前置机是否在线
        Number onLine = (Number) Application.getData(ApplicationKey.STATUS_COMMUNICATION);
        // 设备
        for (MapItem item : mapitem.getMapItems()) {
            MetroNode node = (MetroNode) item.getValue();
            if (node == null || !(node instanceof MetroDevice)) {
                continue;
            }
            int status = DeviceStatus.OFF_LINE;
            MetroDevice metroDevice = (MetroDevice) node;
            long nodeId = metroDevice.getNodeId();

            // 1.如果通信前置机不在线，则节点不在线
            if (onLine != null && !onLine.equals(online)) {
                status = getStatus(false, status);
            } else {
                // 从数据库加载设备节点状态
                EquStatusViewItem equStatus = node.getAFCNodeInfo(EquStatusViewItem.class);
                // 2.若数据库不存在数据，则节点不在线
                if (equStatus == null) {
                    status = getStatus(false, status);
                } else {
                    status = getStatus(equStatus.isOnline(), equStatus.getStatus());
                }
                Application.addImagePath(nodeId, status + "");
                item.setStatus(status);
            }
        }

        MetroNode value = (MetroNode) mapitem.getValue();
        // ACC（本级节点）只要通信前置机在线即在线
        if (value instanceof MetroACC) {
            ImageGraphicItem images = (ImageGraphicItem) mapitem.getItems().get(0);
            int topStatus = OFF_LINE;
            if (onLine != null && onLine.equals(online)) {
                topStatus = online;
            }
            String imagePath = images.getImagesPath().get(topStatus);
            mapitem.setStatus(topStatus);
            int nodeId = ((MetroACC) value).getAccID();
            if (imagePath != null) {
                Application.addImagePath(AFCApplication.getNodeId(nodeId),topStatus + "");
                Application.addImagePath(nodeId, imagePath);
                // 修改节点图标时，同时修改展开和收缩的图标-yang
                root.setExpendIcon(imagePath);
                root.setCloseIcon(imagePath);
            }
        }

        // 线路节点在温州为虚拟节点因此与ACC判断逻辑一致
        if (value instanceof MetroLine) {
            ImageGraphicItem images = (ImageGraphicItem) mapitem.getItems().get(0);
            int topStatus = OFF_LINE;
            if (onLine != null && onLine.equals(online)) {
                topStatus = online;
            }
            String imagePath = images.getImagesPath().get(topStatus);
            mapitem.setStatus(topStatus);
            int nodeId = ((MetroLine) value).getLineID();
            if (imagePath != null) {
                Application.addImagePath(AFCApplication.getNodeId(nodeId), topStatus + "");
                Application.addImagePath(nodeId, imagePath);
                // 修改节点图标时，同时修改展开和收缩的图标-yang
                root.setExpendIcon(imagePath);
                root.setCloseIcon(imagePath);
            }
        }

        // 车站
        if (value instanceof MetroStation) {
            int status = STATION_OFF;
            int nodeId = ((MetroStation) value).getStationId();
            if (onLine != null && onLine.equals(online)) {
                StationStatustViewItem statusItem = value.getAFCNodeInfo(StationStatustViewItem.class);
                if (statusItem != null) {
                    status = getStationStatus(statusItem);
                    if (statusItem.getStatus() == DeviceStatus.NO_USE) {
                        status = STATION_USELESS;
                    }
                }
            }
            ImageGraphicItem images = (ImageGraphicItem) mapitem.getItems().get(0);
            String imagePath = images.getImagesPath().get(status);
            mapitem.setStatus(status);
            if (imagePath != null) {
                Application.addImagePath(nodeId, imagePath);
                Application.addImagePath(AFCApplication.getNodeId(nodeId), status + "");
                // 修改节点图标时，同时修改展开和收缩的图标-yang
                root.setExpendIcon(imagePath);
                root.setCloseIcon(imagePath);
            }
        }

        List<ActionTreeNode> subNodes = root.getSubNodes();
        if (subNodes == null) {
            return root;
        }
        for (ActionTreeNode snode : subNodes) {
            doimage(snode);
        }
        return root;
    }

    // 状态转化为对应的图片信息
    private int getStatus(boolean isOnline, int status) {
        if (isOnline) {
            if (status == DeviceStatus.NORMAL) {// 正常
                return 0;
            } else if (status == DeviceStatus.WARNING) {// 警告
                return 1;
            } else if (status == DeviceStatus.ALARM) {// 报警
                return 2;
            } else if (status == DeviceStatus.OFF_LINE) {// 离线
                return 4;
            } else if (status == DeviceStatus.STOP_SERVICE) {// 停止服务
                return 5;
            } else {
                return 4;
            }
        } else {
            return 4;
        }
    }

    private int getStationStatus(StationStatustViewItem statusItem) {
        long currentMode = statusItem.getMode();
        // 报警阀值
        Integer alarmNum = (Integer) Application.getData(SystemConfigKey.ALARM_THRESHHOLD);
        if (alarmNum == null) {
            alarmNum = 0;
        }
        // 警告阀值
        Integer warningNum = (Integer) Application.getData(SystemConfigKey.WARNING_THRESHHOLD);
        if (warningNum == null) {
            warningNum = 0;
        }
        // statusItem.setOnline(true);
        if (statusItem.isOnline()) {
            // 如果车站不属于任何一个降级模式，则车站属于正常模式，即currentmode==0
            if (currentMode == 0) {
                if (statusItem.getAlarmEvent() < alarmNum && statusItem.getAlarmEvent() < warningNum) {
                    return 0;
                } else if (statusItem.getAlarmEvent() < alarmNum && statusItem.getAlarmEvent() >= warningNum) {
                    return 1;
                } else if (statusItem.getAlarmEvent() >= alarmNum) {
                    return 2;
                } else {
                    return 3;
                }
            } else {
                if (statusItem.getAlarmEvent() < alarmNum && statusItem.getAlarmEvent() < warningNum) {
                    return 4;
                } else if (statusItem.getAlarmEvent() < alarmNum && statusItem.getAlarmEvent() >= warningNum) {
                    return 5;
                } else if (statusItem.getAlarmEvent() >= alarmNum) {
                    return 6;
                } else {
                    return 3;
                }
            }
        } else {
            return 3;
        }
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}
