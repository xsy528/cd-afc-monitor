package com.insigma.commons.query;

public class OrderBy {
	private String name;

	private boolean asc = false;

	public OrderBy(String name, boolean asc) {
		this.name = name;
		this.asc = asc;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the desc
	 */
	public boolean isAsc() {
		return asc;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OrderBy)) {
			return false;
		}
		OrderBy ob = (OrderBy) obj;
		if (asc == ob.asc && name.equals(ob.name)) {
			return true;
		}
		return super.equals(obj);
	}

}
