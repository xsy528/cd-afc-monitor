package com.insigma.afc.topology;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class MetroNode extends AFCNode implements Cloneable {

	private static final long serialVersionUID = 8369790461122870407L;

	public abstract short getNodeType();

	public abstract String getPicName();

	public abstract void setPicName(String picName);

	public abstract List<? extends MetroNode> getSubNodes();

	public abstract boolean addSubNode(MetroNode subMetroNode);

	public abstract boolean removeSubNode(MetroNode subMetroNode);

	public abstract void setImageLocation(AFCNodeLocation imageLocation);

	public abstract void setTextLocation(AFCNodeLocation TextLocation);

	protected volatile MetroNode parent;

	public void setParent(MetroNode parent) {
		this.parent = parent;
	}

	@JsonIgnore
	public MetroNode getParent() {
		return parent;
	}

}
