/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Ticket: 曲线图数据
 *
 * @author xuzhemin
 * 2019/4/25 13:03
 */
@ApiModel("曲线图数据")
public class SeriesChartDTO {

    public SeriesChartDTO(String name, List<SeriesData> points) {
        this.name = name;
        this.points = points;
    }

    @ApiModelProperty("车站名称")
    private String name;

    @ApiModelProperty("曲线图数据点")
    private List<SeriesData> points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SeriesData> getPoints() {
        return points;
    }

    public void setPoints(List<SeriesData> points) {
        this.points = points;
    }
}
