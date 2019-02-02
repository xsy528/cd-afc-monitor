package com.insigma.afc.monitor.model.dto;

import com.insigma.afc.topology.constant.AFCNodeLevel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Ticket: 创建和更新节点的数据模型
 *
 * @author xuzhemin
 * 2019-01-25:14:56
 */
public class NodeData {

    private AFCNodeLevel nodeType;

    private Node node;

    public AFCNodeLevel getNodeType() {
        return nodeType;
    }

    public void setNodeType(AFCNodeLevel nodeType) {
        this.nodeType = nodeType;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public class Node{

        //编号
        @Max(99)
        @Min(0)
        private Short id;

        //父节点id
        private Long pid;

        //节点名称
        @NotBlank
        private String name;

        //启动状态
        @NotNull
        private Short status;

        //ip
        @NotNull
        private String ip;

        //设备类型
        @NotNull
        private Short deviceType;

        //设备逻辑地址
        @NotNull
        private Long logicAddr;

        private Long nodeId;

        private Integer textX;

        private Integer textY;

        private Integer textAngle;

        private Integer iconX;

        private Integer iconY;

        private Integer iconAngle;

        public Long getNodeId() {
            return nodeId;
        }

        public void setNodeId(Long nodeId) {
            this.nodeId = nodeId;
        }

        public Integer getTextX() {
            return textX;
        }

        public void setTextX(Integer textX) {
            this.textX = textX;
        }

        public Integer getTextY() {
            return textY;
        }

        public void setTextY(Integer textY) {
            this.textY = textY;
        }

        public Integer getTextAngle() {
            return textAngle;
        }

        public void setTextAngle(Integer textAngle) {
            this.textAngle = textAngle;
        }

        public Integer getIconX() {
            return iconX;
        }

        public void setIconX(Integer iconX) {
            this.iconX = iconX;
        }

        public Integer getIconY() {
            return iconY;
        }

        public void setIconY(Integer iconY) {
            this.iconY = iconY;
        }

        public Integer getIconAngle() {
            return iconAngle;
        }

        public void setIconAngle(Integer iconAngle) {
            this.iconAngle = iconAngle;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Short getId() {
            return id;
        }

        public void setId(Short id) {
            this.id = id;
        }

        public Long getPid() {
            return pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Short getStatus() {
            return status;
        }

        public void setStatus(Short status) {
            this.status = status;
        }

        public Short getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Short deviceType) {
            this.deviceType = deviceType;
        }

        public Long getLogicAddr() {
            return logicAddr;
        }

        public void setLogicAddr(Long logicAddr) {
            this.logicAddr = logicAddr;
        }
    }
}
