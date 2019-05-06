package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.commons.model.dto.Result;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel
public class NodeItem implements Serializable {

    public interface required extends Result.Base {}
    public interface monitor extends required{}
    public interface editor extends monitor{}

    private static final long serialVersionUID = 1L;

    @JsonView(editor.class)
    @ApiModelProperty("父节点id")
    private Long pid;

    @JsonView(required.class)
    @ApiModelProperty("节点名称")
    private String name;

    @JsonView(required.class)
    @ApiModelProperty("节点id")
    @JsonProperty("node_id")
    private Long nodeId;

    @JsonView(required.class)
    @ApiModelProperty("节点等级")
    @JsonProperty("node_type")
    private String nodeType;

    @JsonView(monitor.class)
    @ApiModelProperty("节点状态")
    private Integer status;

    @JsonView(monitor.class)
    @ApiModelProperty("节点底图")
    @JsonProperty("image_url")
    private String imageUrl;

    @JsonView(monitor.class)
    @ApiModelProperty("节点在父节点底图的图片位置")
    private ImageLocation icon;

    @JsonView(monitor.class)
    @ApiModelProperty("节点在父节点底图的文字位置")
    private TextLocation text;

    @JsonView(required.class)
    @ApiModelProperty("字节点")
    @JsonProperty("sub_items")
    private List<NodeItem> subItems;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ImageLocation getIcon() {
        return icon;
    }

    public void setIcon(ImageLocation icon) {
        this.icon = icon;
    }

    public TextLocation getText() {
        return text;
    }

    public void setText(TextLocation text) {
        this.text = text;
    }

    public List<NodeItem> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<NodeItem> subItems) {
        this.subItems = subItems;
    }

    @Override
    public String toString() {
        return "NodeItem{" +
                "name='" + name + '\'' +
                ", nodeId=" + nodeId +
                ", nodeType='" + nodeType + '\'' +
                ", status=" + status +
                ", imageUrl=" + imageUrl +
                ", icon=" + icon +
                ", text=" + text +
                ", subItems=" + subItems +
                '}';
    }
}
