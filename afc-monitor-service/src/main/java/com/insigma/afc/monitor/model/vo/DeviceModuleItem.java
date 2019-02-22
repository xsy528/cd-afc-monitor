package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-16:16:08
 */
@ApiModel("设备模块")
public class DeviceModuleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("模块名称")
    @JsonProperty("module_name")
    private String moduleName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态值")
    private Short status;

    @ApiModelProperty("状态描述")
    @JsonProperty("status_text")
    private String statusText;

    @ApiModelProperty("位置")
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
