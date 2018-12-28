/**
 * 
 */
package com.insigma.afc.monitor.map.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.ui.MessageForm;

/**
 * @author Administrator
 *
 */
public class MapDeviceDeleteAction extends MapItemAction<String> {

	MapItem parent;

	public static List<MetroNode> deleteDeviceList = new ArrayList<MetroNode>();

	public MapDeviceDeleteAction(String name, final MapItem map) {
		super(name);
		setData(map);
		setImage("/com/insigma/commons/ui/toolbar/delete.png");
		setHandler(new ActionHandlerAdapter() {
			@Override
			public void perform(final ActionContext context) {
				parent = map.getParent();
				final MetroNode value = (MetroNode) map.getValue();
				if (MessageForm.Query("删除确认", "确定要删除节点[" + value.name() + "]?",
						SWT.YES | SWT.NO | SWT.ICON_QUESTION) == SWT.YES) {
					parent.getDataState().delete(map);
					final MetroNode node = (MetroNode) parent.getValue();
					node.removeSubNode(value);
					deleteDeviceList.add(value);
					//刷新树
					removeTreeNode(map);
				}
			}

			@Override
			public void unPerform(ActionContext context) {
				final MetroNode value = (MetroNode) map.getValue();
				parent.getDataState().add(map);
				final MetroNode node = (MetroNode) parent.getValue();
				deleteDeviceList.remove(value);
				node.addSubNode(value);
				//刷新树
				addTreeNode(map);
			}
		});
	}

	@Override
	public boolean IsEnable() {
		Object selectedItem = getData(ActionContext.SELECTED_ITEM);
		if (selectedItem == null) {
			return false;
		}
		return true;
	}

}
