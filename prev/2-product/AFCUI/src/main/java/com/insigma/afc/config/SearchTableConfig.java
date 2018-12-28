package com.insigma.afc.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Table")
public class SearchTableConfig {

	@XStreamImplicit
	private List<SearchTable> searchTableList;

	/**
	 * @return the searchTableList
	 */
	public List<SearchTable> getSearchTableList() {
		return searchTableList;
	}

	/**
	 * @param searchTableList
	 *            the searchTableList to set
	 */
	public void setSearchTableList(List<SearchTable> searchTableList) {
		this.searchTableList = searchTableList;
	}

}
