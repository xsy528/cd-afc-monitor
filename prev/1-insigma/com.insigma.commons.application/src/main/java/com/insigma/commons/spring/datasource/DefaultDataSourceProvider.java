package com.insigma.commons.spring.datasource;

public class DefaultDataSourceProvider {

	private int type;

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public String getURL() {
		return null;
	}

	public String getUserName() {
		return null;
	}

	public String getPassword() {
		return null;
	}

}
