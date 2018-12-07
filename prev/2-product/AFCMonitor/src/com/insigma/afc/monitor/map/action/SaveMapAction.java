package com.insigma.afc.monitor.map.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.topology.AFCNodeLocation;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.commons.editorframework.ActionCallbackAdapter;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.graphic.GraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.spring.Autowire;
import com.insigma.commons.ui.MessageForm;

public class SaveMapAction extends MapItemAction<String> {

	public SaveMapAction() {
		super("保存");
		setImage("/com/insigma/commons/ui/toolbar/save.png");
		setHandler(new SaveMapActionHandler());
	}

	public class SaveMapActionHandler extends ActionHandlerAdapter {

		@Autowire
		private IMetroNodeService metroNodeService;

		@Override
		public void perform(final ActionContext context) {
			EditorFrameWork editorFrameWork = context.getFrameWork();
			if (editorFrameWork == null) {
				return;
			}
			MapEditorView mapEditorView = (MapEditorView) editorFrameWork.getView(MapEditorView.class);
			MapItem activeMap = null;
			if (mapEditorView != null && (activeMap = mapEditorView.getActiveMap()) != null) {
				final List<MetroNode> metroItems = new ArrayList<MetroNode>();
				final List<MetroNode> metroItems2 = new ArrayList<MetroNode>();
				final List<MetroNode> metroItems3 = new ArrayList<MetroNode>();
				List<MapItem> items = mapEditorView.getMapItemList();
				final MetroNode metroNode = (MetroNode) activeMap.getValue();
				for (MapItem item : items) {
					if (item.getMapItems().length > 0) {
						for (MapItem item2 : item.getMapItems()) {
							if (item2.getMapItems().length > 0) {
								for (MapItem item3 : item2.getMapItems()) {
									MetroNode node = createMetroNode(item3);
									if (node != null) {
										metroItems3.add(node);
									}
								}
								metroNodeService.saveMetroNodes(metroNode, null, metroItems3,
										MapDeviceDeleteAction.deleteDeviceList, false);
								MapDeviceDeleteAction.deleteDeviceList.clear();
								metroItems3.clear();

							}
							MetroNode node = createMetroNode(item2);
							if (node != null) {
								metroItems2.add(node);
							}

						}
						metroNodeService.saveMetroNodes(metroNode, null, metroItems2,
								MapDeviceDeleteAction.deleteDeviceList, false);
						MapDeviceDeleteAction.deleteDeviceList.clear();
						metroItems2.clear();
					}
					MetroNode node = createMetroNode(item);
					if (node != null) {
						metroItems.add(node);
					}
				}

				BackgroundWorkor<MetroNode> backgroundWorkor = new BackgroundWorkor<MetroNode>(metroNode) {

					@Override
					public Object execute(MetroNode paramter) {
						metroNodeService.saveMetroNodes(metroNode, null, metroItems,
								MapDeviceDeleteAction.deleteDeviceList, false);
						MapDeviceDeleteAction.deleteDeviceList.clear();
						if (null != logService) {
							logService.doBizLog("保存地图item信息成功。");
						}
						return null;
					}
				};
				//设置UI的回调接口
				//					backgroundWorkor.setActionCallback(new ActionCallback());
				//异步执行
				asynExecute(backgroundWorkor, new ActionCallback());

			}
		}

		private MetroNode createMetroNode(MapItem item) {
			if (item != null && item.getValue() != null) {
				MetroNode parentMetroNode = (MetroNode) item.getParent().getValue();
				MetroNode data = (MetroNode) item.getValue();
				GraphicItem imageItem = item.getItems().get(0);
				GraphicItem textItem = item.getItems().get(1);
				AFCNodeLocation imageLocation = new AFCNodeLocation(imageItem.getX(), imageItem.getY(),
						(int) imageItem.getAngle());
				AFCNodeLocation textLocation = new AFCNodeLocation(textItem.getX(), textItem.getY(),
						(int) textItem.getAngle());

				data.setImageLocation(imageLocation);
				data.setTextLocation(textLocation);
				if (item.isNew()) {
					parentMetroNode.addSubNode(data);
				}
				return data;
			}
			return null;
		}
	}

	private final class ActionCallback extends ActionCallbackAdapter<String> {
		@Override
		public void error(Exception e) {
			logger.error("保存图元信息失败", e);
			MessageForm.Message("警告提示", "保存图元信息失败。", SWT.ICON_WARNING);
		}

		@Override
		public void callback(String result) {
			MessageForm.Message("提示信息", "保存图元信息成功。", SWT.ICON_INFORMATION);
			MapTreeView mapTreeView = (MapTreeView) getFrameWork().getView(MapTreeView.class);
			mapTreeView.setChanged(false);
			mapTreeView.getGraphicMapGenerator().getMapRootItem(false);
			if (mapTreeView != null) {
				AFCApplication.refresh();
				mapTreeView.refresh();
			}
			MapEditorView mapEditorView = (MapEditorView) getFrameWork().getView(MapEditorView.class);
			if (mapEditorView != null) {
				mapEditorView.getMap().clear();
				mapEditorView.getMap().redraw();
			}
		}

	}

}
