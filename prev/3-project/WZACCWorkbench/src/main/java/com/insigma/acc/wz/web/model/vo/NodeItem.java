package com.insigma.acc.wz.web.model.vo;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.List;

public class NodeItem implements Serializable {

    public interface required extends Result.Base{}

    private static final long serialVersionUID = 1L;

    @JsonView(required.class)
    private String name;

    @JsonView(required.class)
    private long nodeId;

    @JsonView(required.class)
    private String nodeType;

    private int status;

    private int imageUrl;

    private ImageLocation icon;

    private TextLocation text;

    @JsonView(required.class)
    private List<NodeItem> subItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
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

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
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
