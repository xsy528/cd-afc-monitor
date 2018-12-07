/* 
 * 日期：2014-9-2
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.acc.wz.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.acc.wz.define.WZDeviceType;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.commons.application.Application;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.tree.TreeNode;

/**
 * Ticket:增加设备类型记录
 * 
 * @author hexingyue
 */

@SuppressWarnings("unchecked")
public class WZDeviceEventTreeProvider implements ITreeProvider {
	private static final Log s_log = LogFactory.getLog(WZDeviceEventTreeProvider.class);

	private static IMetroNodeService metroNodeService;

	static {
		try {
			metroNodeService = (IMetroNodeService) Application.getService(IMetroNodeService.class);
		} catch (ServiceException e) {
			s_log.error("获取metroNodeService异常", e);
		}
	}

	/** 根节点ID 0:ACC 1:LC 2:SC 3:Device */
	private int rootId = 0;

	/** 节点层数 */
	private int depth = 2;

	/** 显示线路 null为全显示 */
	private Short[] lineIDs = null;

	/** 显示车站 null为全显示 */
	private Integer[] stationIDs = null;

	/** 显示设备 null为全显示 */
	private Long[] deviceIDs = null;

	public TreeNode getTree() {
		TreeNode result = null;

		List list = new ArrayList<>();
		if (0 == rootId) {
			list.add(metroNodeService.getMetroACC());
		} else if (1 == rootId) {
			list = metroNodeService.getMetroLine(lineIDs);
		} else if (2 == rootId) {
			list = metroNodeService.getMetroStation(lineIDs, stationIDs);
		} else if (3 == rootId) {
			list = metroNodeService.getMetroDevice(stationIDs, deviceIDs);
		}

		List<TreeNode> treeNodes = getTree(list, depth);
		if (null != treeNodes && treeNodes.size() > 0) {
			result = treeNodes.get(0);
		}
		return result;
	}

	public List<TreeNode> getTree(List list, int depth) {

		List<TreeNode> result = new ArrayList<TreeNode>();

		if (null != list) {
			depth--;
			for (Object object : list) {
				if (null != object) {
					TreeNode treeNode = new TreeNode();
					if (object instanceof MetroACC) {
						MetroACC metroAcc = (MetroACC) object;
						treeNode.setKey(metroAcc);
						treeNode.setText(metroAcc.getAccName());
						// treeNode.setFlag((short) 0);// ACC级别

						if (depth > 0) {
							List<MetroLine> metroLineList = metroNodeService.getMetroLine(lineIDs);
							List<TreeNode> childs = getTree(metroLineList, depth);
							if (childs.size() > 0) {
								treeNode.setChilds(childs);
							}
						}
						result.add(treeNode);
					} else if (object instanceof MetroLine) {
						MetroLine metroLine = (MetroLine) object;
						treeNode.setKey(metroLine);
						treeNode.setText(metroLine.getLineName());
						//treeNode.setFlag((short) 1);// 线路级别

						if (depth > 0) {
							Short[] lineIDs = { metroLine.getLineID() };
							List<MetroStation> metroStationList = metroNodeService.getMetroStation(lineIDs, null);
							List<TreeNode> childs = getTree(metroStationList, depth);
							if (childs.size() > 0) {
								treeNode.setChilds(childs);
							}
						}

						result.add(treeNode);
					} else if (object instanceof MetroStation) {
						MetroStation metroStation = (MetroStation) object;
						treeNode.setKey(metroStation);
						treeNode.setText(metroStation.getStationName());
						// treeNode.setFlag((short) 2);// 车站级别

						if (depth > 0) {
							Integer[] stationIDs = { metroStation.getId().getStationId() };
							List<MetroDevice> metroDeviceList = metroNodeService.getMetroDevice(stationIDs, null);
							Map<Short, List<MetroDevice>> deviceMap = new HashMap<Short, List<MetroDevice>>();
							for (MetroDevice metroDevice : metroDeviceList) {
								short deviceType = metroDevice.getDeviceType();
								if (deviceType == WZDeviceType.PCA) {
									continue;
								}
								if (!deviceMap.containsKey(deviceType)) {
									List<MetroDevice> deviceList = new ArrayList<MetroDevice>();
									deviceMap.put(deviceType, deviceList);
								}
								deviceMap.get(deviceType).add(metroDevice);
							}

							for (Short deviceType : deviceMap.keySet()) {
								String nodeName = AFCDeviceType.getInstance().getNameByValue(deviceType.intValue());
								TreeNode deviceTypeNode = new TreeNode();
								deviceTypeNode.setText(nodeName);

								List<TreeNode> chi = getTree(deviceMap.get(deviceType), depth);
								if (chi.size() > 0) {
									List<TreeNode> childsList = treeNode.getChilds();
									if (childsList == null) {
										childsList = new ArrayList<TreeNode>();
									}

									childsList.add(deviceTypeNode);
									treeNode.setChilds(childsList);
									deviceTypeNode.setChilds(chi);
								}

							}

						}
						result.add(treeNode);
					}

					else if (object instanceof MetroDevice) {

						MetroDevice metroDevice = (MetroDevice) object;
						treeNode.setKey(metroDevice);
						treeNode.setText(metroDevice.getDeviceName());
						// treeNode.setFlag((short) 2);// 设备级别
						result.add(treeNode);
						// if (metroDevice.getDeviceType() == AFCDeviceType.POST) {
						// } else if (metroDevice.getDeviceType() == AFCDeviceType.GATE) {
						// deviceGateType.addNode(treeNode);
						// } else if (metroDevice.getDeviceType() == AFCDeviceType.TVM) {
						// deviceTvmType.addNode(treeNode);
						// } else if (metroDevice.getDeviceType() == AFCDeviceType.TSM) {
						// deviceTsmType.addNode(treeNode);
						// } else if (metroDevice.getDeviceType() == AFCDeviceType.TCM) {
						// deviceTcmType.addNode(treeNode);
						// }

					}
				}
			}
		}
		return result;
	}

	public int getRootId() {
		return rootId;
	}

	public void setRootId(int rootId) {
		this.rootId = rootId;
	}

	public Short[] getLineIDs() {
		return lineIDs;
	}

	public void setLineIDs(Short[] lineIDs) {
		this.lineIDs = lineIDs;
	}

	public Integer[] getStationIDs() {
		return stationIDs;
	}

	public void setStationIDs(Integer[] stationIDs) {
		this.stationIDs = stationIDs;
	}

	public Long[] getDeviceIDs() {
		return deviceIDs;
	}

	public void setDeviceIDs(Long[] deviceIDs) {
		this.deviceIDs = deviceIDs;
	}

	public static IMetroNodeService getMetroNodeService() {
		return metroNodeService;
	}

	public static void setMetroNodeService(IMetroNodeService metroNodeService) {
		WZDeviceEventTreeProvider.metroNodeService = metroNodeService;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public static Log getS_log() {
		return s_log;
	}
}
