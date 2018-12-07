/**
 * 
 */
package com.insigma.afc.monitor.map.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.application.AFCNodeLevel;
import com.insigma.afc.monitor.action.NodeAction;
import com.insigma.afc.monitor.map.action.MapItemDoubleClickAction;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceModule;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.view.ListView;

/**
 * @author Administrator
 *
 */
public class GraphicMapBuilder extends AbstractGraphicMapBuilder {

	MapItemDoubleClickAction lc_sc_doubleClickAction = new MapItemDoubleClickAction();
	Action device_doubleClickAction;

	public List<Action> contextActions = new ArrayList<Action>();

	@Override
	public MapItem buildGraphicMap(MetroNode node) {
		MapItem item = new MapItem(node.id());
		AFCNodeLevel type = node.level();
		switch (type) {
		case ACC:
			MetroACC acc = (MetroACC) node;
			item = getAcc(acc);
			item.setImage(acc.getPicName()); // 右侧监控布局底图
			item.setValue(node);
			item.setLoadSubItem(false);
			break;
		case LC:
			item = getLine((MetroLine) node);
			break;
		case SC:
			item = getStation((MetroStation) node);
			break;
		case SLE:
			item = getDeviceItem((MetroDevice) node);
			break;
		default:
			item = getModule((MetroDeviceModule) node);
			break;
		}
		if (item != null) {
			item.setText(node.name());
			if (type == AFCNodeLevel.SC || type == AFCNodeLevel.LC) {// lc,scL双击进入地图
				item.setDoubleClickAction(lc_sc_doubleClickAction);
			} else {
				// 设备双击显示监控图
				item.setDoubleClickAction(device_doubleClickAction);
				// 右键
				for (Action action : contextActions) {
					// 过滤非必要选择 2013-4-18shenchao
					if (action instanceof NodeAction && AFCApplication.getUser().hasFunction(action.getID())) {
						AFCNodeLevel targetType = ((NodeAction) action).getTargetType();
						if (targetType != null && targetType.equals(AFCNodeLevel.SLE)) {
							item.addContextAction(action);
						}
					}
				}
			}

		}
		return item;
	}

	public void setDevice_doubleClickAction(Action device_doubleClickAction) {
		this.device_doubleClickAction = device_doubleClickAction;
	}

	@Override
	public void treeNodeCreated(ActionTreeNode treeNode) {
		treeNode.addSelectionAction(new Action("select", new ActionHandlerAdapter() {

			@Override
			public void perform(ActionContext context) {
				Collection<FrameWorkView> views = context.getFrameWork().getViews();
				for (FrameWorkView view : views) {
					if (view instanceof ListView) {
						if (view.isVisible()) {
							view.refresh();
						}
					}
				}
				super.perform(context);
			}

		}));
		super.treeNodeCreated(treeNode);
	}
}
