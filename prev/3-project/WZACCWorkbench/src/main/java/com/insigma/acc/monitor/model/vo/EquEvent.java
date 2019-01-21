package com.insigma.acc.monitor.model.vo;

import java.io.Serializable;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-03:17:52
 */
public class EquEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nodeName;
    private String statusName;
    private String statusDesc;
    private String applyDevice;
    private String occurTime;
    private Short item;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getApplyDevice() {
        return applyDevice;
    }

    public void setApplyDevice(String applyDevice) {
        this.applyDevice = applyDevice;
    }

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    public Short getItem() {
        return item;
    }

    public void setItem(Short item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "EquEvent{" +
                "nodeName='" + nodeName + '\'' +
                ", statusName='" + statusName + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", applyDevice='" + applyDevice + '\'' +
                ", occurTime='" + occurTime + '\'' +
                ", item=" + item +
                '}';
    }
}
