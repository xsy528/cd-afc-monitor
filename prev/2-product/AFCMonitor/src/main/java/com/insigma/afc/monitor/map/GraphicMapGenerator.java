package com.insigma.afc.monitor.map;

import java.util.List;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.application.Application;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.graphic.editor.IGraphicMapGenerator;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.action.MapTreeSelectionAction;

/**
 * 实现地图相关信息的生成，地图信息从库表获取
 */
public class GraphicMapGenerator implements IGraphicMapGenerator {

	private IGraphicMapBuilder graphicMapBuilder;

	final MapTreeSelectionAction mapTreeSelectionAction = new MapTreeSelectionAction();

	private volatile MapItem rootMapItem;

	private AFCNodeLevel depth = AFCNodeLevel.SC; //树的深度

	// jfq, true，表示编辑模式
	private boolean isEditUI = false;

	public AFCNodeLevel getDepth() {
		return depth;
	}

	public GraphicMapGenerator() {

	}

	// jfq, 加载所有的MapItem
	public ActionTreeNode getMapTreeRootNode(boolean force) {
		MapItem mapItem = getMapRootItem(force);
		if (mapItem == null) {
			return null;
		}
		return buildTreeNode(mapItem);
	}

	public ActionTreeNode buildTreeNode(MapItem mapItem) {
		MetroNode metroNode = (MetroNode) mapItem.getValue();//网络拓扑信息
		if (metroNode.level().ordinal() > depth.ordinal()) {
			return null;
		}
		ActionTreeNode rootNode = new ActionTreeNode(mapItem.getText());
		rootNode.isLeaf = true;
		rootNode.setValue(mapItem);
		rootNode.setId(mapItem.getMapId());

		rootNode.getSelectionAction().add(mapTreeSelectionAction);

		if (isEditUI()) {
			// 如果该车站未启用，则节点管理所在的树也灰显-yang
			if (mapItem.getValue() instanceof MetroStation
					&& ((MetroStation) mapItem.getValue()).getStatus().shortValue() == 1) {
				rootNode.setExpendIcon("/com/insigma/afc/ui/monitor/station/stop.png"); // 左侧树图标
			} else {
				rootNode.setExpendIcon("/com/insigma/afc/ui/monitor/station/green.png"); // 左侧树图标
			}
		} else {
			long nodeId = mapItem.getMapId();
			String imagePath = Application.getImagePath(nodeId);
			if (imagePath != null) {
				rootNode.setExpendIcon(imagePath);
			} else {
				rootNode.setExpendIcon("/com/insigma/afc/ui/monitor/station/green.png"); // 左侧树图标
			}
		}

		if (mapItem.isNew()) {
			rootNode.setExpendIcon("/com/insigma/afc/ui/monitor/station/green_new.png"); // 左侧树图标
		}

		MapItem[] items = mapItem.getMapItems();
		for (MapItem mapItem2 : items) {
			final ActionTreeNode buildTreeNode = buildTreeNode(mapItem2);
			if (buildTreeNode != null) {
				rootNode.addSubNode(buildTreeNode);
			}
		}
		graphicMapBuilder.treeNodeCreated(rootNode);
		return rootNode;
	}

	/**
	 * 获取地图结构的根节点
	 * @return
	 */
	public synchronized MapItem getMapRootItem(boolean force) {
		if (rootMapItem == null || force) {
			rootMapItem = getMapItem(AFCApplication.getAFCNode());
		}
		if (rootMapItem == null) {
			return null;
		}
		rootMapItem.changeToSaveState();//change to save state
		return rootMapItem;
	}

	// 获取图元，这里可以增加节点状态判断来修改节点的图片显示
	protected MapItem getMapItem(MetroNode afcNode) {
		if (afcNode == null) {
			return null;
		}
		MapItem mapItem = graphicMapBuilder.buildGraphicMap(afcNode);
		if (mapItem == null) {
			return null;
		}
		List<? extends MetroNode> subNodes = afcNode.getSubNodes();
		if (subNodes == null) {
			return mapItem;
		}

		for (MetroNode metroNode : subNodes) {
			MapItem subItem = getMapItem(metroNode);
			// 节点管理-监控图
			if (!isEditUI()) {
				if (metroNode instanceof MetroDevice) {
					MetroDevice metroDevice = (MetroDevice) metroNode;
					// 如果该设备未启用，监控图则不加载该设备-yang
					if (metroDevice.getStatus().shortValue() == 1) {
						subItem = null;
						continue;
					}
					String status = Application.getImagePath(metroNode.getNodeId());
					if (status != null) {
						subItem.setStatus(Integer.parseInt(status));
					}
				}
				if (metroNode instanceof MetroStation) {
					MetroStation metroStation = (MetroStation) metroNode;
					if (metroStation.getStatus().shortValue() != 1) {
						String status = Application.getImagePath(metroNode.getNodeId());
						if (status != null) {
							subItem.setStatus(Integer.parseInt(status));
						}
					} else {
						//之后作为常量提出
						//subItem.setStatus(9);
						// 如果该车站未启用,监控图不加载该车站-yang
						subItem = null;
						continue;
					}
				}
			}
			// 监控界面-监控图显示
			else {
				if (metroNode instanceof MetroDevice) {
					MetroDevice metroDevice = (MetroDevice) metroNode;
					// 如果该设备未启用，节点管理该设备灰显-yang
					if (metroDevice.getStatus().shortValue() == 1) {
						subItem.setStatus(4);
					}
				}
				if (metroNode instanceof MetroStation) {
					// 如果该车站未启用，则灰显-yang
					MetroStation metroStation = (MetroStation) metroNode;
					if (metroStation.getStatus().shortValue() == 1) {
						// 之后作为常量提出
						subItem.setStatus(9);
					}
				}
			}

			if (subItem != null) {
				mapItem.addMapItem(subItem);
			}
		}
		return mapItem;
	}

	/**
	 * @return the graphicMapBuilder
	 */
	public IGraphicMapBuilder getGraphicMapBuilder() {
		return graphicMapBuilder;
	}

	/**
	 * @param graphicMapBuilder the graphicMapBuilder to set
	 */
	public void setGraphicMapBuilder(IGraphicMapBuilder graphicMapBuilder) {
		this.graphicMapBuilder = graphicMapBuilder;
	}

	@Override
	public boolean isEditUI() {
		return isEditUI;
	}

	public void setEditUI(boolean isEditUI) {
		this.isEditUI = isEditUI;
	}

}
