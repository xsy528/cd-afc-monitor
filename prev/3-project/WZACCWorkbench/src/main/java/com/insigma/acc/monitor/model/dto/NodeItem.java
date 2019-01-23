package com.insigma.acc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.List;

public class NodeItem implements Serializable {

    public interface required extends Result.Base{}
    public interface monitor extends required{}
    public interface editor extends monitor{}

    private static final long serialVersionUID = 1L;

    @JsonView(editor.class)
    private Long pid;

    @JsonView(required.class)
    private String name;

    @JsonView(required.class)
    private Long nodeId;

    @JsonView(required.class)
    private String nodeType;

    @JsonView(monitor.class)
    private Integer status;

    @JsonView(monitor.class)
    private Integer imageUrl;

    @JsonView(monitor.class)
    private ImageLocation icon;

    @JsonView(monitor.class)
    private TextLocation text;

    @JsonView(required.class)
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

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
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
