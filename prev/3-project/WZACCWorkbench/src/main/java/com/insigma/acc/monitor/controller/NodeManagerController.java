package com.insigma.acc.monitor.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.insigma.acc.monitor.exception.ErrorCode;
import com.insigma.acc.monitor.model.vo.Result;
import com.insigma.acc.monitor.service.FileService;
import com.insigma.acc.monitor.util.HttpUtils;
import com.insigma.acc.monitor.util.JsonUtils;
import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.topology.*;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.commons.op.OPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-10:14:47
 */
public class NodeManagerController extends BaseMultiActionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeManagerController.class);

    static {
        methodMapping.put("/editor/node/create", "nodeCreate");
        methodMapping.put("/editor/node/remove", "nodeRemove");
        methodMapping.put("/editor/node/update", "nodeUpdate");
        methodMapping.put("/editor/node/query", "nodeQuery");
    }

    private FileService fileService;

    private IMetroNodeService metroNodeService;

    @Autowired
    public NodeManagerController(FileService fileService, IMetroNodeService metroNodeService) {
        this.fileService = fileService;
        this.metroNodeService = metroNodeService;
    }

    public Result<Map<String, Object>> nodeQuery(HttpServletRequest request, HttpServletResponse response) {
        JsonNode jsonNode = HttpUtils.getBody(request);
        if (jsonNode == null || jsonNode.get("nodeId") == null) {
            return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
        }
        MetroNode metroNode = AFCApplication.getNode(jsonNode.get("nodeId").longValue());
        if (metroNode == null) {
            return Result.error(ErrorCode.NODE_NOT_EXISTS);
        }
        Map<String, Object> node = new HashMap<>();
        switch (metroNode.level()) {
            case ACC: {
                MetroACC metroACC = (MetroACC) metroNode;
                node.put("name", metroACC.getAccName());
                node.put("ip", metroACC.getIpAddress());
                node.put("img", metroACC.getPicName());
                break;
            }
            case LC: {
                MetroLine metroLine = (MetroLine) metroNode;
                node.put("name", metroLine.getLineName());
                node.put("ip", metroLine.getIpAddress());
                node.put("img", metroLine.getPicName());
                node.put("status", metroLine.getStatus());
                node.put("id", metroLine.getLineID());
                node.put("nodeId", metroLine.getLineID());
                break;
            }
            case SC: {
                MetroStation metroStation = (MetroStation) metroNode;
                node.put("name", metroStation.getStationName());
                node.put("ip", metroStation.getIpAddress());
                node.put("img", metroStation.getPicName());
                node.put("status", metroStation.getStatus());
                node.put("id", Integer.valueOf(Integer.toHexString(metroStation.getId().getStationId())) % 100);
                node.put("lineId", metroStation.getId().getLineId());
                node.put("lineName", metroStation.getLineName());
                node.put("nodeId", metroStation.getId().getStationId());
                break;
            }
            case SLE: {
                MetroDevice metroDevice = (MetroDevice) metroNode;
                node.put("name", metroDevice.getDeviceName());
                node.put("ip", metroDevice.getIpAddress());
                node.put("deviceType", metroDevice.getDeviceType());
                node.put("logicAddr", metroDevice.getLogicAddress());
                node.put("status", metroDevice.getStatus());
                node.put("id", Long.valueOf(Long.toHexString(metroDevice.getId().getDeviceId())) % 100);
                node.put("lineId", metroDevice.getId().getLineId());
                node.put("lineName", metroDevice.getLineName());
                node.put("stationId", Integer.valueOf(Integer.toHexString(metroDevice.getId().getStationId())));
                node.put("stationName", metroDevice.getStationName());
                node.put("nodeId", metroDevice.getId().getDeviceId());
                break;
            }
        }
        return Result.success(node);
    }

    //创建节点
    public Result<String> nodeCreate(HttpServletRequest request, HttpServletResponse response) {
        AFCNodeLocation newImageLocation = new AFCNodeLocation(30, 30, 0);
        AFCNodeLocation newTextLocation = new AFCNodeLocation(30, 30, 0);
        String filepath = null;
        try {
            //图片信息
            Part imagePart = request.getPart("img");
            if (imagePart != null) {
                String imageName = imagePart.getName();
                InputStream inputStream = imagePart.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) > -1) {
                    byteArrayOutputStream.write(bytes, 0, len);
                }
                //图片最大1M
                if (byteArrayOutputStream.size() > 1048575) {
                    return Result.error(ErrorCode.TOO_LARGE_FILE);
                }
                Result<String> result = fileService.saveTmpFile(byteArrayOutputStream.toByteArray(), imageName);
                if (!result.isSuccess()) {
                    //保存图片失败
                    return result;
                }
                filepath = result.getData();
            }
            //节点信息
            Part dataPart = request.getPart("data");
            JsonNode data = JsonUtils.readObject(dataPart.getInputStream());
            JsonNode nodeType = data.get("nodeType");
            JsonNode node = data.get("node");

            if (nodeType == null) {
                return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
            }
            AFCNodeLevel nodeLevel = AFCNodeLevel.valueOf(nodeType.textValue());
            switch (nodeLevel) {
                case LC: {
                    short id;
                    String name;
                    short status;
                    String ip;
                    try {
                        id = node.get("id").shortValue();
                        name = node.get("name").textValue();
                        status = node.get("status").shortValue();
                        ip = node.get("ip").textValue();
                    } catch (Exception e) {
                        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
                    }

                    if (id > 99 || id < 0) {
                        return Result.error(ErrorCode.INVALID_ARGUMENT);
                    }

                    MetroLine metroLine = new MetroLine();
                    metroLine.setLineID(id);
                    if (metroNodeService.exists(metroLine)) {
                        return Result.error(ErrorCode.NODE_EXISTS);
                    }
                    metroLine.setLineName(name);
                    metroLine.setStatus(status);
                    metroLine.setIpAddress(ip);
                    metroLine.setImageLocation(newImageLocation);
                    metroLine.setTextLocation(newTextLocation);
                    if (filepath == null) {
                        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
                    }
                    metroLine.setPicName(filepath);

                    metroNodeService.saveMetroNodes(metroLine, null);
                    break;
                }
                case SC: {
                    int id;
                    String name;
                    short status;
                    String ip;
                    short pid;
                    try {
                        id = node.get("id").intValue();
                        name = node.get("name").textValue();
                        status = node.get("status").shortValue();
                        ip = node.get("ip").textValue();
                        pid = node.get("pid").shortValue();
                    } catch (Exception e) {
                        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
                    }
                    if (id > 99 || id < 0) {
                        return Result.error(ErrorCode.INVALID_ARGUMENT);
                    }
                    //创建车站节点
                    MetroStation metroStation = new MetroStation();
                    //计算车站id
                    metroStation.setId(new MetroStationId(pid,
                            Integer.valueOf(new BigInteger(String.valueOf(pid * 100 + id), 16).toString())));
                    //判断是否已经存在
                    if (metroNodeService.exists(metroStation)) {
                        return Result.error(ErrorCode.NODE_EXISTS);
                    }
                    metroStation.setImageLocation(newImageLocation);
                    metroStation.setTextLocation(newTextLocation);
                    metroStation.setStationName(name);
                    MetroLine metroLine = (MetroLine) AFCApplication.getNode(pid);
                    //线路不存在
                    if (metroLine == null) {
                        return Result.error(ErrorCode.NODE_NOT_EXISTS);
                    }
                    metroStation.setLineName(metroLine.getLineName());
                    //必须要上传车站图片
                    if (filepath == null) {
                        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
                    }
                    metroStation.setBackPicName(filepath);
                    metroStation.setPicName(filepath);
                    metroStation.setIpAddress(ip);
                    metroStation.setStatus(status);
                    metroNodeService.saveMetroNodes(metroStation, null);
                    break;
                }
                case SLE: {
                    long id;
                    String name;
                    short status;
                    String ip;
                    short pid;
                    short deviceType;
                    long logicAddr;
                    try {
                        id = node.get("id").intValue();
                        name = node.get("name").textValue();
                        status = node.get("status").shortValue();
                        ip = node.get("ip").textValue();
                        pid = node.get("pid").shortValue();
                        deviceType = node.get("deviceType").shortValue();
                        logicAddr = node.get("logicAddr").longValue();
                    } catch (Exception e) {
                        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
                    }
                    if (id > 99 || id < 0) {
                        return Result.error(ErrorCode.INVALID_ARGUMENT);
                    }

                    //所属车站
                    MetroStation metroStation = (MetroStation) AFCApplication.getNode(pid);
                    //车站不存在
                    if (metroStation == null) {
                        return Result.error(ErrorCode.NODE_NOT_EXISTS);
                    }
                    //所属线路
                    MetroLine metroLine = (MetroLine) AFCApplication.getNode(metroStation.getId().getLineId());

                    //计算设备id
                    Integer stationId16 = Integer.valueOf(Integer.toHexString(metroStation.getId().getStationId()));
                    Integer deviceType16 = Integer.valueOf(Integer.toHexString(deviceType));
                    long deviceId = stationId16 * 10000 + deviceType16 * 100 + id;
                    BigInteger srch1 = new BigInteger(String.valueOf(deviceId), 16);

                    MetroDevice metroDevice = new MetroDevice();
                    metroDevice.setId(new MetroDeviceId(metroLine.getLineID(), metroStation.getId().getStationId(),
                            Long.valueOf(srch1.toString())));

                    //设备是否存在
                    if (metroNodeService.exists(metroDevice)) {
                        return Result.error(ErrorCode.NODE_EXISTS);
                    }

                    metroDevice.setImageLocation(newImageLocation);
                    metroDevice.setTextLocation(newTextLocation);
                    metroDevice.setStationName(metroStation.getStationName());
                    metroDevice.setLineName(metroLine.getLineName());
                    metroDevice.setStationId(metroStation.getStationId());
                    metroDevice.setLineId(metroLine.getLineID());
                    metroDevice.setDeviceName(name);
                    metroDevice.setIpAddress(ip);
                    metroDevice.setLogicAddress(logicAddr);
                    metroDevice.setStatus(status);
                    metroDevice.setDeviceType(deviceType);

                    metroNodeService.saveMetroNodes(metroDevice, null);
                    break;
                }
            }
            //刷新内存节点
            AFCApplication.refresh();
        } catch (IOException | ServletException e) {
            LOGGER.error("创建节点失败", e);
            return Result.error(ErrorCode.UNKNOW_ERROR);
        }
        return Result.success();
    }

    //修改节点
    public Result<String> nodeUpdate(HttpServletRequest request, HttpServletResponse response) {
        String filepath = null;
        try {
            //图片信息
            Part imagePart = request.getPart("img");
            if (imagePart != null) {
                String imageName = imagePart.getName();
                InputStream inputStream = imagePart.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) > -1) {
                    byteArrayOutputStream.write(bytes, 0, len);
                }
                //图片最大1M
                if (byteArrayOutputStream.size() > 1048575) {
                    return Result.error(ErrorCode.TOO_LARGE_FILE);
                }
                Result<String> result = fileService.saveTmpFile(byteArrayOutputStream.toByteArray(), imageName);
                if (!result.isSuccess()) {
                    //保存图片失败
                    return result;
                }
                filepath = result.getData();
            }
            //节点信息
            Part dataPart = request.getPart("data");
            JsonNode data = JsonUtils.readObject(dataPart.getInputStream());
            JsonNode nodeType = data.get("nodeType");
            JsonNode node = data.get("node");

            if (nodeType == null) {
                return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
            }
            AFCNodeLevel nodeLevel = AFCNodeLevel.valueOf(nodeType.textValue());
            switch (nodeLevel) {
                case ACC: {
                    //修改ACC节点信息
                    String name;
                    String ip;
                    try {
                        name = node.get("name").textValue();
                        ip = node.get("ip").textValue();
                    } catch (Exception e) {
                        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
                    }

                    MetroACC metroACC = metroNodeService.getMetroACC();
                    metroACC.setAccName(name);
                    metroACC.setIpAddress(ip);
                    if (filepath != null) {
                        metroACC.setPicName(filepath);
                    }
                    metroNodeService.saveMetroNodes(metroACC, null);
                    break;
                }
                case LC: {
                    //修改线路节点
                    String name;
                    long nodeId;
                    short id;
                    short status;
                    String ip;
                    int textX;
                    int textY;
                    int textAngle;
                    int iconX;
                    int iconY;
                    int iconAngle;
                    try {
                        nodeId = node.get("nodeId").shortValue();
                        id = node.get("id").shortValue();
                        name = node.get("name").textValue();
                        status = node.get("status").shortValue();
                        ip = node.get("ip").textValue();
                        textX = node.get("text").get("x").intValue();
                        textY = node.get("text").get("y").intValue();
                        textAngle = node.get("text").get("angle").intValue();
                        iconX = node.get("icon").get("x").intValue();
                        iconY = node.get("icon").get("y").intValue();
                        iconAngle = node.get("icon").get("angle").intValue();
                    } catch (Exception e) {
                        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
                    }

                    if (id > 99 || id < 0) {
                        return Result.error(ErrorCode.INVALID_ARGUMENT);
                    }

                    MetroNode metroNode = AFCApplication.getNode(nodeId);
                    if (metroNode==null||metroNode.level()!=AFCNodeLevel.LC){
                        return Result.error(ErrorCode.NODE_EXISTS);
                    }
                    //从内存中获取该线路
                    MetroLine metroLine = (MetroLine) metroNode;
                    Long oldNodeId = null;
                    //检查是否修改id
                    if (metroLine.getLineID() != id) {
                        //检查新id是否存在
                        metroLine.setLineID(id);
                        oldNodeId = nodeId;
                        if (metroNodeService.exists(metroLine)) {
                            return Result.error(ErrorCode.NODE_EXISTS);
                        }
                    }
                    metroLine.setLineName(name);
                    metroLine.setIpAddress(ip);
                    metroLine.getTextLocation().setX(textX);
                    metroLine.getTextLocation().setY(textY);
                    metroLine.getTextLocation().setAngle(textAngle);
                    metroLine.getImageLocation().setX(iconX);
                    metroLine.getImageLocation().setY(iconY);
                    metroLine.getImageLocation().setAngle(iconAngle);

                    if (status == 0 || status == 1) {
                        metroLine.setStatus(status);
                    }
                    if (filepath != null) {
                        metroLine.setPicName(filepath);
                    }
                    metroNodeService.saveMetroNodes(metroLine, oldNodeId);
                    break;
                }
                case SC: {
                    //修改车站节点
                    String name;
                    long nodeId;
                    short id;
                    short status;
                    String ip;
                    int textX;
                    int textY;
                    int textAngle;
                    int iconX;
                    int iconY;
                    int iconAngle;
                    try {
                        nodeId = node.get("nodeId").longValue();
                        id = node.get("id").shortValue();
                        name = node.get("name").textValue();
                        status = node.get("status").shortValue();
                        ip = node.get("ip").textValue();
                        textX = node.get("text").get("x").intValue();
                        textY = node.get("text").get("y").intValue();
                        textAngle = node.get("text").get("angle").intValue();
                        iconX = node.get("icon").get("x").intValue();
                        iconY = node.get("icon").get("y").intValue();
                        iconAngle = node.get("icon").get("angle").intValue();
                    } catch (Exception e) {
                        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
                    }
                    if (id > 99 || id < 0) {
                        return Result.error(ErrorCode.INVALID_ARGUMENT);
                    }

                    MetroNode metroNode = AFCApplication.getNode(nodeId);
                    if (metroNode==null||metroNode.level()!=AFCNodeLevel.SC){
                        return Result.error(ErrorCode.NODE_NOT_EXISTS);
                    }
                    MetroStation metroStation = (MetroStation) metroNode;
                    if (metroStation == null) {
                        return Result.error(ErrorCode.NODE_NOT_EXISTS);
                    }
                    metroStation.getId().setStationId(Integer.valueOf(new BigInteger(String.valueOf(metroStation
                            .getId().getLineId() * 100 + id), 16).toString()));

                    Long oldNodeId = null;
                    if (metroStation.getId().getStationId() != nodeId) {
                        //是新节点
                        oldNodeId = nodeId;
                        //节点存在检查
                        if (metroNodeService.exists(metroStation)) {
                            return Result.error(ErrorCode.NODE_EXISTS);
                        }
                    }

                    metroStation.setStationName(name);
                    metroStation.setIpAddress(ip);
                    metroStation.getTextLocation().setX(textX);
                    metroStation.getTextLocation().setY(textY);
                    metroStation.getTextLocation().setAngle(textAngle);
                    metroStation.getImageLocation().setX(iconX);
                    metroStation.getImageLocation().setY(iconY);
                    metroStation.getImageLocation().setAngle(iconAngle);

                    if (filepath != null) {
                        metroStation.setBackPicName(filepath);
                        metroStation.setPicName(filepath);
                    }
                    if (status == 0 || status == 1) {
                        metroStation.setStatus(status);
                    }

                    metroNodeService.saveMetroNodes(metroStation, oldNodeId);
                    break;
                }
                case SLE: {
                    String name;
                    short deviceType;
                    long logicAddr;
                    long nodeId;
                    short id;
                    short status;
                    String ip;
                    int textX;
                    int textY;
                    int textAngle;
                    int iconX;
                    int iconY;
                    int iconAngle;
                    try {
                        nodeId = node.get("nodeId").longValue();
                        id = node.get("id").shortValue();
                        name = node.get("name").textValue();
                        deviceType = node.get("deviceType").shortValue();
                        logicAddr = node.get("logicAddr").longValue();
                        status = node.get("status").shortValue();
                        ip = node.get("ip").textValue();
                        textX = node.get("text").get("x").intValue();
                        textY = node.get("text").get("y").intValue();
                        textAngle = node.get("text").get("angle").intValue();
                        iconX = node.get("icon").get("x").intValue();
                        iconY = node.get("icon").get("y").intValue();
                        iconAngle = node.get("icon").get("angle").intValue();
                    } catch (Exception e) {
                        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
                    }
                    MetroNode metroNode = AFCApplication.getNode(nodeId);
                    if (metroNode==null||metroNode.level()!=AFCNodeLevel.SLE){
                        return Result.error(ErrorCode.NODE_EXISTS);
                    }
                    MetroDevice metroDevice = (MetroDevice) metroNode;
                    if (id > 99 || id < 0) {
                        return Result.error(ErrorCode.INVALID_ARGUMENT);
                    }
                    Integer stationId16 = Integer.valueOf(Integer.toHexString(metroDevice.getId().getStationId()));
                    Integer deviceType16 = Integer.valueOf(Integer.toHexString(deviceType));
                    long deviceId = id;
                    deviceId = stationId16 * 10000 + deviceType16 * 100 + deviceId;
                    BigInteger srch1 = new BigInteger(String.valueOf(deviceId), 16);
                    metroDevice.getId().setDeviceId(Long.valueOf(srch1.toString()));

                    Long oldNodeId = null;
                    if (metroDevice.getId().getDeviceId() != nodeId) {
                        oldNodeId = nodeId;
                        //检查设备是否存在
                        if (metroNodeService.exists(metroDevice)) {
                            return Result.error(ErrorCode.NODE_EXISTS);
                        }
                    }

                    metroDevice.setDeviceName(name);
                    metroDevice.setIpAddress(ip);
                    metroDevice.getTextLocation().setX(textX);
                    metroDevice.getTextLocation().setY(textY);
                    metroDevice.getTextLocation().setAngle(textAngle);
                    metroDevice.getImageLocation().setX(iconX);
                    metroDevice.getImageLocation().setY(iconY);
                    metroDevice.getImageLocation().setAngle(iconAngle);
                    metroDevice.setLogicAddress(logicAddr);
                    metroDevice.setDeviceType(deviceType);

                    if (status == 0 || status == 1) {
                        metroDevice.setStatus(status);
                    }

                    metroNodeService.saveMetroNodes(metroDevice, oldNodeId);
                    break;
                }
            }
            //刷新内存节点
            AFCApplication.refresh();
        } catch (IOException | ServletException e) {
            LOGGER.error("修改节点失败", e);
            return Result.error(ErrorCode.UNKNOW_ERROR);
        }
        return Result.success();
    }

    //删除节点
    public Result<String> nodeRemove(HttpServletRequest request, HttpServletResponse response) {
        JsonNode jsonNode = HttpUtils.getBody(request);
        JsonNode nodeId = jsonNode.get("nodeId");
        if (nodeId == null) {
            return Result.success();
        }
        long id = nodeId.longValue();
        try {
            metroNodeService.delete(id);
            //刷新内存节点
            AFCApplication.refresh();
        } catch (OPException e) {
            LOGGER.error("删除节点异常", e);
            Result.error(ErrorCode.DATABASE_ERROR);
        }
        return Result.success();
    }
}
