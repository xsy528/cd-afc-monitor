package com.insigma.afc.monitor.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterForm {

	private List<Object> selections;

	private String orderField = "occurTime";

	private String orderType = "desc";

	private Map<Long, List<Object>> nodeSelectMap;

	public FilterForm() {
	}

	public List<Object> getSelections() {
		return selections;
	}

	public void setSelections(List<Object> selections) {
		this.selections = selections;
	}

	public void addSelection(Object select) {
		if (selections == null) {
			selections = new ArrayList<Object>();
		}
		selections.add(select);
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Map<Long, List<Object>> getNodeSelectMap() {
		return nodeSelectMap;
	}

	public void addNodeSelectMap(Long nodeId, List<Object> selectMap) {
		if (nodeSelectMap == null) {
			nodeSelectMap = new HashMap<Long, List<Object>>();
		}
		nodeSelectMap.put(nodeId, selectMap);

	}
}
