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
    private String stationId;

    @ApiModelProperty("车站名称")
    @JsonProperty("station_name")
    private String stationName;

    @ApiModelProperty("票种/编码")
    @JsonProperty("ticket_family")
    private String ticketFamily;

    @ApiModelProperty("进站客流")
    @JsonProperty("od_in")
    private String odIn;

    @ApiModelProperty("出站客流")
    @JsonProperty("od_out")
    private String odOut;

    @ApiModelProperty("购票客流")
    @JsonProperty("od_buy")
    private String odBuy;

    @ApiModelProperty("充值客流")
    @JsonProperty("od_add")
    private String odAdd;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
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

    public String getOdIn() {
        return odIn;
    }

    public void setOdIn(String odIn) {
        this.odIn = odIn;
    }

    public String getOdOut() {
        return odOut;
    }

    public void setOdOut(String odOut) {
        this.odOut = odOut;
    }

    public String getOdBuy() {
        return odBuy;
    }

    public void setOdBuy(String odBuy) {
        this.odBuy = odBuy;
    }

    public String getOdAdd() {
        return odAdd;
    }

    public void setOdAdd(String odAdd) {
        this.odAdd = odAdd;
    }
}
