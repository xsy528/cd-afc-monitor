package com.insigma.acc.workbench.monitor.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="TMETRO_LINE")
public class MetroLine extends MetroNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="LINE_ID")
    private Short lineId;

    @Column(name="LINE_NAME")
    private String lineName;

    @Column(name="PIC_NAME")
    private String picName;

    public Short getLineId() {
        return lineId;
    }

    public void setLineId(Short lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    @Override
    protected void setNodeId() {
        this.nodeId=lineId.toString();
    }

    @Override
    protected void setNodeType() {
        this.nodeType=NodeType.Line.toString();
    }

    @Override
    protected void setName() {
        this.name = lineName;
    }
}
