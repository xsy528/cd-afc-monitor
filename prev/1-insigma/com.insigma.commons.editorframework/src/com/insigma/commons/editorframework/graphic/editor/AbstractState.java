package com.insigma.commons.editorframework.graphic.editor;

public abstract class AbstractState implements IState {

	MapItem self;

	public AbstractState(MapItem self) {
		this.self = self;
		if (self == null) {
			throw new NullPointerException("节点不允许为空");
		}
	}

}