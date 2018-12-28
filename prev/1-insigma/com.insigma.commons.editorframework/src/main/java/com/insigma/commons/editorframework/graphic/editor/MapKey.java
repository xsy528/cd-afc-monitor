package com.insigma.commons.editorframework.graphic.editor;

public final class MapKey {
	int stateMask = 0;
	int keyCode = 0;

	public MapKey(int stateMask, int keyCode) {
		super();
		this.stateMask = stateMask;
		this.keyCode = keyCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + keyCode;
		result = prime * result + stateMask;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapKey other = (MapKey) obj;
		if (keyCode != other.keyCode)
			return false;
		if (stateMask != other.stateMask)
			return false;
		return true;
	}

}