package com.insigma.afc.topology;

import java.util.List;

import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCDeviceType;

/**
 * 描述：<br>
 * Ticket:
 * 
 */
public class DefaultNodeIdStrategy implements INodeIdStrategy {

	@Override
	public short getLineId(long nodeId) {
		if (nodeId < 0xff) {
			return (short) nodeId;
		} else if (nodeId > 0xff && nodeId < 0xffff) {
			return (short) (nodeId >> 8);
		} else {
			return (short) (nodeId >> 24);
		}
	}

	@Override
	public int getStationId(long nodeId) {
		if (nodeId > 0xff && nodeId < 0xffff) {
			return (int) nodeId;
		} else if ((nodeId & 0x00FF0000) > 0) {
			return (int) ((nodeId & 0xFFFF0000) >> 16);
		} else {
			throw new RuntimeException("无效的nodeId");
		}
	}

	//节点号由线路+车站+设备类型+设备ID 组成
	//long id ：十进制线路、车站、设备号
	@Override
	public long getNodeId(long id) {
		long nodeId = 0;
		if (id < 0xff) {
			nodeId = (id << 24) | AFCDeviceType.LC << 8;
		} else if (id > 0xff && id < 0xffff) {
			nodeId = (id << 16) | AFCDeviceType.SC << 8;
		} else {
			return id;
		}
		return nodeId;
	}

	@Override
	public AFCNodeLevel getNodeLevel(long nodeId) {
		short deviceType = getNodeType(nodeId);
		if (deviceType == AFCDeviceType.CCHS) {
			return AFCNodeLevel.ACC;
		} else if (deviceType == AFCDeviceType.LC) {
			return AFCNodeLevel.LC;
		} else if (deviceType == AFCDeviceType.SC) {
			return AFCNodeLevel.SC;
		} else {
			return AFCNodeLevel.SLE;
		}
	}

	@Override
	public short getNodeType(long nodeId) {
		long newNodeID = getNodeId(nodeId);
		short nodeType = (short) ((newNodeID & 0x0000ff00) >> 8);
		return nodeType;
	}

	@Override
	public short getStationOnlyId(long nodeId) {
		return 0;
	}

	@Override
	public short getDeviceId(long nodeId) {
		return 0;
	}

	@Override
	public long getFatherNode(long nodeId) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.insigma.afc.topology.INodeIdStrategy#getAllMetroNode()
	 */
	@Override
	public List<Object[]> getAllMetroNode() {
		return null;
	}

}
