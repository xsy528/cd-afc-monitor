package com.insigma.acc.workbench.monitor.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("节点")
public class NodeItem {

    @ApiModelProperty("节点名称")
    private String name;

    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("节点类型")
    private String nodeType;

    @ApiModelProperty("节点状态")
    private int status;

    @ApiModelProperty("底图")
    private String imageUrl;

    @ApiModelProperty("图片信息")
    private ImageLocation icon;

    @ApiModelProperty("文字信息")
    private TextLocation text;

    @ApiModelProperty("子节点")
    private List<NodeItem> subItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
}
