package com.insigma.acc.monitor.model.vo;

import java.io.Serializable;

/**
 * Ticket: 模式广播信息
 *
 * @author xuzhemin
 * 2019-01-03:14:56
 */
public class ModeBroadcastInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    //节点名称
    private String name;
    //上传源车站名称
    private String sourceName;
    //模式
    private String mode;
    //广播时间
    private String modeBroadcastTime;
    //目的车站名称
    private String targetName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getModeBroadcastTime() {
        return modeBroadcastTime;
    }

    public void setModeBroadcastTime(String modeBroadcastTime) {
        this.modeBroadcastTime = modeBroadcastTime;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
