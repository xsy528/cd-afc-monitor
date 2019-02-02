package com.insigma.afc.monitor.model.entity;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-25:20:04
 */
public class AFCResourcePK implements Serializable {
    private static final long serialVersionUID = 1L;

    public AFCResourcePK(){

    }

    @Column(name = "NAME")
    private String name;
    @Column(name = "NAME_SPACE")
    private String nameSpace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AFCResourcePK that = (AFCResourcePK) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(nameSpace, that.nameSpace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nameSpace);
    }
}