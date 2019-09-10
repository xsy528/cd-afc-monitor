package com.insigma.afc.monitor.model.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/26 11:38.
 * @Ticket :
 */
public class TimeShareVO {
    @ApiModelProperty("时间")
    @JsonProperty("time")
    private String time;

    @ApiModelProperty("进站客流")
    @JsonProperty("total_in")
    private String totalIn;

    @ApiModelProperty("出战客流")
    @JsonProperty("total_out")
    private String totalOut;

    @ApiModelProperty("购票客流")
    @JsonProperty("total_buy")
    private String totalBuy;

    @ApiModelProperty("充值客流")
    @JsonProperty("total_add")
    private String totalAdd;

    public String getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(String totalIn) {
        this.totalIn = totalIn;
    }

    public String getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(String totalOut) {
        this.totalOut = totalOut;
    }

    public String getTotalBuy() {
        return totalBuy;
    }

    public void setTotalBuy(String totalBuy) {
        this.totalBuy = totalBuy;
    }

    public String getTotalAdd() {
        return totalAdd;
    }

    public void setTotalAdd(String totalAdd) {
        this.totalAdd = totalAdd;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
