package com.insigma.acc.wz.web.model.vo;

import java.io.Serializable;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-02:09:51
 */
public class EquStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String status;
    private String updateTime;
    private boolean isOnline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
