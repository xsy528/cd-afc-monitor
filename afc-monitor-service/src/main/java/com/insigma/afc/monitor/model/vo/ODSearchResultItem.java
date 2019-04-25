package com.insigma.afc.monitor.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Ticket:客流查询显示实体
 *
 * @author: xingshaoya
 * create time: 2019-03-22 15:10
 */
@ApiModel("客流查询信息")
public class ODSearchResultItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("车站ID")
    @JsonProperty("station_id")
    private Integer stationId;

    @ApiModelProperty("车站名称")
    @JsonProperty("station_name")
    private String stationName;

    @ApiModelProperty("票种/编码")
    @JsonProperty("ticket_family")
    private String ticketFamily;

    @ApiModelProperty("进站客流")
    @JsonProperty("od_in")
    private Long odIn;

    @ApiModelProperty("出站客流")
    @JsonProperty("od_out")
    private Long odOut;

    @ApiModelProperty("购票客流")
    @JsonProperty("od_buy")
    private Long odBuy;

    @ApiModelProperty("充值客流")
    @JsonProperty("od_add")
    private Long odAdd;

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getTicketFamily() {
        return ticketFamily;
    }

    public void setTicketFamily(String ticketFamily) {
        this.ticketFamily = ticketFamily;
    }

    public Long getOdIn() {
        return odIn;
    }

    public void setOdIn(Long odIn) {
        this.odIn = odIn;
    }

    public Long getOdOut() {
        return odOut;
    }

    public void setOdOut(Long odOut) {
        this.odOut = odOut;
    }

    public Long getOdBuy() {
        return odBuy;
    }

    public void setOdBuy(Long odBuy) {
        this.odBuy = odBuy;
    }

    public Long getOdAdd() {
        return odAdd;
    }

    public void setOdAdd(Long odAdd) {
        this.odAdd = odAdd;
    }
}
