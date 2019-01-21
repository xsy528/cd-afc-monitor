package com.insigma.acc.monitor.provider;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.dic.AFCDeviceSubType;
import com.insigma.afc.dic.AFCDeviceType;
import com.insigma.afc.topology.*;
import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.tree.TreeNode;

import java.util.List;

/**
 * 构造树的数据提供者
 * 
 * @author 廖红自
 */
public class CommonTreeProvider implements ITreeProvider {

	private AFCNodeLevel depth = AFCNodeLevel.LC;

	private MetroNode rootNode = AFCApplication.getAFCNode();

	private boolean nodeChecked = false;

	//true时 树节点的二级节点会多一个所有节点的子项，用于文件上传查询中，节点id=0的情况
	private boolean ViewAll = false;

	private String POST = "BOM";
	private String GATE = "AGM";
	private String TVM = "TVM";
	private String TCM = "TCM";
	private String AVM = "AVM";
	private String TSM = "ISM";
	private String PCA = "PCA";

	/**
	 * 设置树显示的深度。 <br>
	 * 参数可以选DISPLAY_LEVEL_ACC、DISPLAY_LEVEL_lC、DISPLAY_LEVEL_SC、DISPLAY_LEVEL_SLE
	 * 
	 * @param depth
	 *            树显示的深度，包括DISPLAY_LEVEL_ACC、DISPLAY_LEVEL_lC、DISPLAY_LEVEL_SC、DISPLAY_LEVEL_SLE。
	 */
	public void setDepth(AFCNodeLevel depth) {
		this.depth = depth;
	}

	@Override
	public TreeNode getTree() {

		TreeNode rootTreeNode = new TreeNode();
		rootTreeNode.setKey(rootNode);
		rootTreeNode.setText(rootNode.name());

		// ACC
		if (rootNode.level() == AFCNodeLevel.ACC) {
			List<MetroLine> metroLineList = ((MetroACC) rootNode).getLineList();
			for (MetroLine line : metroLineList) {
				// 如果线路未启用不加载-yang
				if (line.getStatus().shortValue() == 1) {
					continue;
				}
				TreeNode lineNode = new TreeNode();
				lineNode.setKey(line);
				lineNode.setText(line.name());

				//add to rootTreeNode
				rootTreeNode.addNode(lineNode);

				//show SC
				if (depth.ordinal() > AFCNodeLevel.LC.ordinal()) {
					List<MetroStation> ss = line.getStationList();
					for (MetroStation metroStation : ss) {
						// 如果车站未启用不加载-yang
						if (metroStation.getStatus().shortValue() == 1) {
							continue;
						}
						TreeNode stationNode = new TreeNode();
						stationNode.setKey(metroStation);
						stationNode.setText(metroStation.name());

						//add to lineNode
						lineNode.addNode(stationNode);

						if (depth.ordinal() > AFCNodeLevel.SC.ordinal()) {
							getDevice(stationNode, metroStation);
						}
					}
				}

			}
		} else if (rootNode.level() == AFCNodeLevel.LC) {
			rootTreeNode.setChecked(isNodeChecked());
			MetroLine line = (MetroLine) rootNode;
			List<MetroStation> ss = line.getStationList();
			for (MetroStation metroStation : ss) {
				// 如果车站未启用不加载-yang
				if (metroStation.getStatus().shortValue() == 1) {
					continue;
				}
				TreeNode stationNode = new TreeNode();
				stationNode.setChecked(isNodeChecked());
				stationNode.setKey(metroStation);
				stationNode.setText(metroStation.name());

				//add to lineNode
				rootTreeNode.addNode(stationNode);

				if (depth.ordinal() > AFCNodeLevel.SC.ordinal()) {
					getDevice(stationNode, metroStation);
				}
			}

			if (ViewAll) {

				TreeNode node = new TreeNode();
				MetroStation metroNode = new MetroStation();
				MetroStationId id = new MetroStationId();
				id.setStationId(-1);
				metroNode.setId(id);

				node.setKey(metroNode);
				node.setText("所有节点");

				//add to StationNode
				rootTreeNode.addNode(node);
			}

		} else {
			rootTreeNode.setChecked(isNodeChecked());
			MetroStation metroStation = (MetroStation) rootNode;
			//SC不显示设备列表
			if (depth.ordinal() != AFCNodeLevel.SC.ordinal()) {
				getDevice(rootTreeNode, metroStation);
			}
			{
				if (ViewAll) {
					TreeNode node = new TreeNode();
					MetroDevice metroNode = new MetroDevice();
					MetroDeviceId deviceId = new MetroDeviceId();
					deviceId.setDeviceId(-1L);
					metroNode.setId(deviceId);

					node.setKey(metroNode);
					node.setText("所有节点");

					//add to StationNode
					rootTreeNode.addNode(node);
				}
			}
		}

		return rootTreeNode;
	}

	/**
	 * @return the rootNode
	 */
	public MetroNode getRootNode() {
		return rootNode;
	}

	/**
	 * 设置显示的根节点，默认为config配置的当前节点
	 * @param rootNode the rootNode to set
	 */
	public void setRootNode(MetroNode rootNode) {
		this.rootNode = rootNode;
	}

	/**
	 * @return the depth
	 */
	public AFCNodeLevel getDepth() {
		return depth;
	}

	public boolean isNodeChecked() {
		return nodeChecked;
	}

	public void setNodeChecked(boolean nodeChecked) {
		this.nodeChecked = nodeChecked;
	}

	public boolean isAnotherView() {
		return ViewAll;
	}

	public void setAnotherView(boolean anotherView) {
		this.ViewAll = anotherView;
	}

	public void getDevice(TreeNode rootTreeNode, MetroStation metroStation) {
		List<MetroDevice> devices = metroStation.getDevices();
		TreeNode devicePostType = new TreeNode();
		devicePostType.setText(this.POST);
		TreeNode deviceGateType = new TreeNode();
		deviceGateType.setText(this.GATE);
		TreeNode deviceTvmType = new TreeNode();
		deviceTvmType.setText(this.TVM);
		TreeNode deviceTcmType = new TreeNode();
		deviceTcmType.setText(this.TCM);
		TreeNode deviceAvmType = new TreeNode();
		deviceAvmType.setText(this.AVM);
		TreeNode deviceTsmType = new TreeNode();
		deviceTsmType.setText(this.TSM);
		TreeNode devicePcaType = new TreeNode();
		devicePcaType.setText(this.PCA);
		for (MetroDevice metroDevice : devices) {
			// 如果设备未启用不加载-yang
			if (metroDevice.getStatus().shortValue() == 1) {
				continue;
			}
			TreeNode deviceNode = new TreeNode();
			//								deviceNode.setChecked(isNodeChecked());
			deviceNode.setKey(metroDevice);
			deviceNode.setText(metroDevice.name());

			if (metroDevice.getDeviceType() == AFCDeviceType.POST) {
				devicePostType.addNode(deviceNode);
			} else if ((AFCDeviceType.GATE == null
					&& AFCDeviceSubType.getInstance().getDicItemMap().containsKey(metroDevice.getDeviceType()))
					|| (AFCDeviceType.GATE != null && metroDevice.getDeviceType() == AFCDeviceType.GATE)) {
				deviceGateType.addNode(deviceNode);
			} else if (metroDevice.getDeviceType() == AFCDeviceType.TVM) {
				deviceTvmType.addNode(deviceNode);
			}
			//			else if (metroDevice.getDeviceType() == AFCDeviceType.TCM) {
			//				deviceTcmType.addNode(deviceNode);
			//			} 
			else if (metroDevice.getDeviceType() == AFCDeviceType.TSM) {
				deviceTsmType.addNode(deviceNode);
			}
			//			else if (metroDevice.getDeviceType() == AFCDeviceType.AVM) {
			//				deviceAvmType.addNode(deviceNode);
			//			} 
			else if (metroDevice.getDeviceType() == AFCDeviceType.PCA) {
				devicePcaType.addNode(deviceNode);
			}
		}
		if (devicePostType.getChilds().size() > 0) {
			rootTreeNode.addNode(devicePostType);
		}
		if (deviceGateType.getChilds().size() > 0) {
			rootTreeNode.addNode(deviceGateType);
		}
		if (deviceTvmType.getChilds().size() > 0) {
			rootTreeNode.addNode(deviceTvmType);
		}
		if (deviceTcmType.getChilds().size() > 0) {
			rootTreeNode.addNode(deviceTcmType);
		}
		if (deviceTsmType.getChilds().size() > 0) {
			rootTreeNode.addNode(deviceTsmType);
		}
		if (deviceAvmType.getChilds().size() > 0) {
			rootTreeNode.addNode(deviceAvmType);
		}
		if (devicePcaType.getChilds().size() > 0) {
			rootTreeNode.addNode(devicePcaType);
		}

	}

}
