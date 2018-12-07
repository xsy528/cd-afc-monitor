/**
 * 
 */
package com.insigma.afc.topology;

import java.util.List;

import com.insigma.afc.application.AFCNodeLevel;

/**
 * <b> nodeId的转换策略</b><br/>
 * <li>根据线路，车站编号转换成NodeId</li>
 * <li>反之，根据NodeId获取线路编号、车站编号、设备编号</li>
 * @author qcj(qiuchangjin@zdwxgd.com)
 *
 */
public interface INodeIdStrategy {

	/**
	 * 根据普通的id获取<b>NodeId</b>
	 * @param id 可以是线路编号、车站编号、设备编号
	 * @return
	 */
	public long getNodeId(long id);

	/**
	 * 根据nodeId获取<b>线路编号</b>
	 * 如果传入的是线路或车站编号，转换成线路号
	 * @param nodeId
	 * @return 比如：1（0x01）
	 */
	public short getLineId(long nodeId);

	/**
	 * 根据nodeId 获取<b>车站编号</b>
	 * @param nodeId 如果传入的是线路编号或者线路nodId抛异常
	 * @return 比如：257（0x0101）
	 */
	public int getStationId(long nodeId);

	/**
	 * 根据nodeId 获取<b>车站编号</b>
	 * @param 
	 * @return 仅仅是车站编号
	 */
	public short getStationOnlyId(long nodeId);

	/**
	 * <b>根据nodeId获取设备类型</b>
	 * 如果传入的是线路或车站编号，抛异常
	 * @param nodeId
	 * @return
	 */
	public short getNodeType(long nodeId);

	/**
	 * <b>根据nodeId获取节点所在的级别</b>
	 * @param nodeId
	 * @return
	 */
	public AFCNodeLevel getNodeLevel(long nodeId);

	/**
	 * <b>根据nodeId获取节点设备代码</b>
	 * @param nodeId
	 * @return
	 */
	public short getDeviceId(long nodeId);

	/**
	 * <b>根据nodeId获取上级节点号</b>
	 * @param nodeId
	 * @return
	 */
	public long getFatherNode(long nodeId);

	/**
	 * <b>获取所有线路的节点</b>
	 * @param nodeId
	 * @return
	 */
	public List<Object[]> getAllMetroNode();
}
