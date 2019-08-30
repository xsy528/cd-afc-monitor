package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author by Xinshao,
 * @Email xingshaoya@unittec.com,
 * @Time on 2019/8/30 16:32.
 * @Ticket :
 */
@ApiModel("票卡对比饼图柱状图")
public class TicketComparePieDTO {

    public TicketComparePieDTO() {
    }

    public TicketComparePieDTO(List<String> name, List<Long> values) {
        this.name = name;
        this.values = values;
    }

    @ApiModelProperty("名称")
    @JsonProperty("name")
    private List<String> name;
    @ApiModelProperty("记录所有项值")
    @JsonProperty("values")
    private List<Long> values;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }
}
