/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.model.dto.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insigma.commons.constant.AFCNodeLevel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/5/7 17:16
 */
@ApiModel("监控树查询条件")
public class MonitorTreeCondition {

    @ApiModelProperty("节点等级")
    private AFCNodeLevel level = AFCNodeLevel.SLE;

    @ApiModelProperty("车站id（只查询该车站的设备）")
    @JsonProperty("station_id")
    private Integer stationId;

    public AFCNodeLevel getLevel() {
        return level;
    }

    public void setLevel(AFCNodeLevel level) {
        this.level = level;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }
}
