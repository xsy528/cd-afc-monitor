package com.insigma.afc.topology.constant;

/**
 * 节点类型 Ticket: <br/>
 * 
 * @author 邱昌进(qiuchangjin@zdwxjd.com)
 */
public enum AFCNodeLevel {
	//ACC节点
	ACC(0),
	//线路节点
	LC(1),
	//车站节点
	SC(2),
	//设备节点
	SLE(3),
	//设备模块节点
	MODULE(4);

	private final Integer nodeType;

	AFCNodeLevel(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public Integer getStatusCode() {
		return nodeType;
	}
}