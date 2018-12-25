package com.insigma.acc.workbench.monitor.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="TMETRO_ACC")
public class MetroACC extends MetroNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ACC_ID")
    private Short accId;

    @Column(name="ACC_NAME")
    private String accName;

    @Column(name="PIC_NAME")
    private String picName;

    public Short getAccId() {
        return accId;
    }

    public void setAccId(Short accId) {
        this.accId = accId;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    @Override
    protected void setNodeId() {
        this.nodeId = accId.toString();
    }

    @Override
    protected void setNodeType() {
        this.nodeType = NodeType.ACC.toString();
    }

    @Override
    public void setName() {
        this.name = accName;
    }
}
