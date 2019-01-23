package com.insigma.afc.monitor.model.vo;

import com.insigma.afc.monitor.model.dto.Location;

import java.io.Serializable;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-16:16:08
 */
public class DeviceModuleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String moduleName;

    private String remark;

    private Short status;

    private String statusText;

    private Location location;

    public DeviceModuleItem(String moduleName, Short status, String remark,Location location) {
        this.moduleName = moduleName;
        this.status = status;
        this.remark = remark;
        this.statusText = getText(status);
        this.location = location;
    }

    private String getText(short status){
        if (status >= 2) {
            return "报警";
        } else if (status >= 1) {
            return "警告";
        } else {
            return "正常";
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
