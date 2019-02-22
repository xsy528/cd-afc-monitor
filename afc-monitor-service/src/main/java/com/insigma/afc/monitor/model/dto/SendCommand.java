package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-29:16:19
 */
@ApiModel("发送命令数据模型")
public class SendCommand {

    @ApiModelProperty("节点id数组")
    @JsonProperty("node_ids")
    private List<Long> nodeIds;

    @ApiModelProperty("命令编号")
    private Integer command;

    public List<Long> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<Long> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }
}
