package com.insigma.afc.monitor.action;

import java.util.ArrayList;
import java.util.List;

import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.action.MapAction;
import com.insigma.commons.ui.tree.ITreeProvider;

public class NodeAction extends MapAction {

	public enum SelectionType {
		SINGLE, MULTI
	}

	protected List<MetroNode> nodes = new ArrayList<MetroNode>();

	protected SelectionType selectionType = SelectionType.SINGLE;

	protected AFCNodeLevel targetType = AFCNodeLevel.SLE;//action作用域

	protected int treeDepth;

	protected ITreeProvider treeProvider;

	protected boolean useMap = true;

	public SelectionType getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(SelectionType selectionType) {
		this.selectionType = selectionType;
	}

	public AFCNodeLevel getTargetType() {
		return targetType;
	}

	public void setTargetType(AFCNodeLevel targetType) {
		this.targetType = targetType;
	}

	public int getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	public MetroNode getNode() {
		if (nodes.size() > 0) {
			return nodes.get(0);
		}
		return null;
	}

	public List<MetroNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<MetroNode> nodes) {
		this.nodes = nodes;
	}

	public List<Long> getIDs() {
		List<Long> lds = new ArrayList<Long>();
		for (MetroNode node : nodes) {
			lds.add(node.id());
		}
		return lds;
	}

	public boolean IsEnable() {
		List<MapItem> gitems = null;
		if (getMap() != null) {
			gitems = getMap().getSelection();
		}

		List<MetroNode> mapselected;
		if (useMap) {
			mapselected = nodes;
			nodes.clear();
		} else {
			mapselected = new ArrayList<MetroNode>();
		}

		if (gitems != null && gitems.size() >= 1) {
			for (MapItem item : gitems) {
				MetroNode n = (MetroNode) item.getValue();
				if (n != null) {
					mapselected.add(n);
				} else {
					logger.error("无法找到" + item.getValue() + "对应的对象");
				}
			}
		}

		if (getTargetType() == null) {
			return true;
		}

		if (getSelectionType() == SelectionType.SINGLE && mapselected != null && mapselected.size() > 1) {
			return false;
		}

		if (getTargetType() == AFCNodeLevel.SLE && mapselected.size() > 0
				&& mapselected.get(0) instanceof MetroDevice) {
			return true;
		}
		if (getTargetType() == AFCNodeLevel.SC && mapselected.size() > 0
				&& mapselected.get(0) instanceof MetroStation) {
			return true;
		}

		return false;

	}

	public boolean isUseMap() {
		return useMap;
	}

	public void setUseMap(boolean useMap) {
		this.useMap = useMap;
	}
}
