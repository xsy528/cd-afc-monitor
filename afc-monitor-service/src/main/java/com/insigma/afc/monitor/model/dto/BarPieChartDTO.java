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
 * Ticket:饼图柱状图数据模型
 *
 * @author xuzhemin
 * 2019/4/24 10:14
 */
@ApiModel("饼图柱状图")
public class BarPieChartDTO {

    public BarPieChartDTO(String name, List<Long> values) {
        this.name = name;
        this.values = values;
    }

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("记录所有项值")
    private List<Long> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }
}
