/**
 * 
 */
package com.insigma.afc.monitor.map.action;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.monitor.map.builder.AbstractGraphicMapBuilder;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.service.IMetroNodeService;
import com.insigma.commons.application.exception.ServiceException;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.dialog.ObjectEditorDialog;
import com.insigma.commons.editorframework.graphic.ImageGraphicItem;
import com.insigma.commons.editorframework.graphic.TextGraphicItem;
import com.insigma.commons.editorframework.graphic.editor.MapEditorView;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.form.Form.FormMode;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.ResourceUtil;
import com.swtdesigner.SWTResourceManager;

/**
 * @author Administrator
 *
 */
public class MapDeviceModifyAction extends MapItemAction<String> {

	private static final String EDITOR_NEXT = "editor.next";

	private static final String EDITOR_PREVOUS = "editor.prevous";

	private ObjectEditorDialog dialog;

	MapItem map;

	private MetroNode beforeNode;

	public MetroNode getBeforeNode() {
		return beforeNode;
	}

	public void setBeforeNode(MetroNode beforeNode) {
		this.beforeNode = beforeNode;
	}

	public MapDeviceModifyAction(String name, final MapItem map) {
		super(name);
		setData(map);
		setImage("/com/insigma/commons/ui/toolbar/edit.gif");
		this.map = map;
		setHandler(new ActionHandlerAdapter() {

			@Override
			public void perform(final ActionContext context) {
				MetroNode nextNode = (MetroNode) context.getData(EDITOR_NEXT);
				if (nextNode != null) {//如果next不为空，则表示是redo
					map.setValue(nextNode);
					mapReset(map, nextNode);
					return;
				}
				final EditorFrameWork editorFrameWork = context.getFrameWork();
				if (editorFrameWork == null) {
					return;
				}
				MetroNode metroNode = (MetroNode) map.getValue();
				MetroNode copyNode = null;
				try {
					MetroNode beforeNode = (MetroNode) BeanUtil.cloneObject(metroNode);
					copyNode = (MetroNode) BeanUtil.cloneObject(metroNode);
					//此处做一个搓搓的转化——yangyang
					if (copyNode instanceof MetroDevice) {

						Integer stationId = ((MetroDevice) copyNode).getId().getStationId();
						Integer srch1 = Integer.valueOf(Integer.toHexString(stationId));
						((MetroDevice) copyNode).getId().setStationId(srch1);

						Long deviceId = ((MetroDevice) copyNode).getId().getDeviceId();
						Long srch2 = Long.valueOf(Long.toHexString(deviceId));
						((MetroDevice) copyNode).getId().setDeviceId(srch2 % 100);

					} else if (copyNode instanceof MetroStation) {
						// 转化
						Integer stationId = ((MetroStation) copyNode).getId().getStationId();
						Integer srch = Integer.valueOf(Integer.toHexString(stationId));
						((MetroStation) copyNode).getId().setStationId(srch % 100);
					}

					context.setData(EDITOR_PREVOUS, beforeNode);
					setBeforeNode(beforeNode);
				} catch (Exception e) {
					logger.error("clone before value error", e);
				}
				Form<MetroNode> form = new Form<MetroNode>(copyNode);
				form.setFormMode(FormMode.MODIFY);
				dialog = new ObjectEditorDialog(editorFrameWork, form, SWT.RESIZE);
				dialog.setText(getName());
				dialog.setDescription(getName());
				dialog.setSize(500, 600);
				dialog.getActions().add(new ModifyAction());
				//				dialog.setChangedListener(new IEditorChangedListener() {
				//					@Override
				//					public void editorChanged(boolean isChanged) {
				//						map.getDataState().update();
				//					}
				//				});
				dialog.open();
				try {
					context.setData(EDITOR_NEXT, BeanUtil.cloneObject(metroNode));
				} catch (Exception e) {
					logger.error("clone error after value error", e);
				}
			}

			@Override
			public void unPerform(ActionContext context) {
				MetroNode node = (MetroNode) context.getData(EDITOR_PREVOUS);
				map.setValue(node);
				mapReset(map, node);
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

	private void mapReset(MapItem map, MetroNode metroNode) {
		final ImageGraphicItem imageGraphicItem = (ImageGraphicItem) map.getItems().get(0);
		final TextGraphicItem textGraphicItem = (TextGraphicItem) map.getItems().get(1);

		textGraphicItem.setText(metroNode.name());
		if (metroNode instanceof MetroDevice) {
			final ArrayList<Image> images = AbstractGraphicMapBuilder.getDeviceImages((MetroDevice) metroNode);
			imageGraphicItem.setImages(images);
		}
		String path = null;
		File file = new File(map.getImage());
		if (file.isAbsolute()) {
			path = map.getImage();
		} else {
			path = ResourceUtil.ROOT_RESOURCE_PATH + "/" + map.getImage();
		}
		SWTResourceManager.disposeImage(path);
		map.setImage(metroNode.getPicName());
	}

	public class ModifyAction extends Action {

		public ModifyAction() {
			super("保存(&S)");
			setHandler(new SaveActionHandler());
		}

		private class SaveActionHandler extends ActionHandlerAdapter {
			@Override
			public void perform(ActionContext context) {
				boolean updateFlag = false;
				IMetroNodeService metroNodeService = null;

				try {
					metroNodeService = (IMetroNodeService) AFCApplication.getService(IMetroNodeService.class);
				} catch (ServiceException e) {
					e.printStackTrace();
				}

				MetroNode metroNode = (MetroNode) context.getData(ActionContext.RESULT_DATA);
				if (metroNode instanceof MetroDevice) {
					MetroDevice metroDevice = (MetroDevice) metroNode;
					if (metroDevice.getId().getDeviceId() == null) {
						MessageForm.Message("设备编号不能为空。");
						return;
					}
					if (metroDevice.getId().getDeviceId() > 99) {
						MessageForm.Message("设备编号不合法。");
						return;
					}
					if (metroDevice.getId().getDeviceId() <= 99) {
						//由于目前数据库无法兼容3位设备ID，因此此处需转化为完整节点号，后续希望可以调整，兼容——yang   现在改为两位DeviceId。
						Integer deviceType16 = Integer.valueOf(Integer.toHexString(metroDevice.getDeviceType()));
						metroDevice.getId().setDeviceId(metroDevice.getId().getStationId() * 10000 + deviceType16 * 100
								+ metroDevice.getId().getDeviceId());

						int stationId = metroDevice.getId().getStationId();
						BigInteger srch = new BigInteger(Integer.valueOf(stationId).toString(), 16);
						metroDevice.getId().setStationId(Integer.valueOf(srch.toString()));

						String deviceId = metroDevice.getId().getDeviceId().toString();
						BigInteger srch1 = new BigInteger(deviceId, 16);
						metroDevice.getId().setDeviceId(Long.valueOf(srch1.toString()));
					}
					if (metroDevice.getDeviceName() == null) {
						MessageForm.Message("设备名称不能为空。");
						return;
					}
				} else if (metroNode instanceof MetroStation) {
					MetroStation metroStation = (MetroStation) metroNode;
					MetroStation metroStationBefore = (MetroStation) getBeforeNode();
					if (metroStation.getId().getStationId() == null) {
						MessageForm.Message("车站编号不能为空。");
						return;
					}
					//					if (metroStation.getId().getStationId() < 421) {
					//						MessageForm.Message("车站编号不能小于421。");
					//						return;
					//					}
					//					if (metroStation.getId().getStationId() > 499) {
					//						MessageForm.Message("车站编号不能大于499。");
					//						return;
					//					}
					if (((metroStation.getId().getStationId() > 20 && metroStation.getId().getStationId() < 1)
							|| (metroStation.getId().getStationId() > 120
									&& metroStation.getId().getStationId() < 101))) {
						MessageForm.Message("车站编号不合法");
						return;
					}

					if (metroStation.getId().getStationId() > 0 && metroStation.getId().getStationId() < 21) {
						//同设备ID——yang
						metroStation.getId().setStationId(
								metroStation.getId().getLineId() * 100 + metroStation.getId().getStationId());

						String stationId = metroStation.getId().getStationId().toString();
						BigInteger srch = new BigInteger(stationId, 16);
						metroStation.getId().setStationId(Integer.valueOf(srch.toString()));

					}
					if (metroStation.getStationName() == null) {
						MessageForm.Message("车站名称不能为空。");
						return;
					}
					if (metroStation.getBackPicName() == null) {
						MessageForm.Message("车站图片不能为空。");
						return;
					}
					if (!metroStation.getStationName().equals(metroStationBefore.getStationName())) {
						updateFlag = true;
						// 转换10进制
						short lineId = metroStation.getId().getLineId();
						int stationId = metroStation.getId().getStationId();
						stationId = Integer.valueOf(lineId + "" + stationId, 16);
						metroStation.getId().setStationId(stationId);
					} else {
						metroStation.getId().setStationId(metroStationBefore.getId().getStationId());
					}
					File file = new File(metroStation.getPicName());
					if (file.isFile() && file.length() > 1048575) {
						MessageForm.Message("车站图片大小不能超过1M。");
						return;
					}
				} else if (metroNode instanceof MetroLine) {
					MetroLine metroLine = (MetroLine) metroNode;
					MetroLine MetroLineBefore = (MetroLine) getBeforeNode();
					if (metroLine.getLineName() == null) {
						MessageForm.Message("线路名称不能为空。");
						return;
					}
					if (metroLine.getPicName() == null) {
						MessageForm.Message("线路图片不能为空。");
						return;
					}
					if (!metroLine.getLineName().equals(MetroLineBefore.getLineName())) {
						updateFlag = true;
					}
					File file = new File(metroLine.getPicName());
					if (file.length() > 1048575) {
						MessageForm.Message("线路图片大小不能超过1M。");
						return;
					}
				} else if (metroNode instanceof MetroACC) {
					MetroACC metroAcc = (MetroACC) metroNode;
					MetroACC MetroAccBefore = (MetroACC) getBeforeNode();
					if (metroAcc.getAccName() == null) {
						MessageForm.Message("ACC名称不能为空。");
						return;
					}
					if (metroAcc.getPicName() == null) {
						MessageForm.Message("ACC图片不能为空。");
						return;
					}
					if (!metroAcc.getAccName().equals(MetroAccBefore.getAccName())) {
						updateFlag = true;
					}
					File file = new File(metroAcc.getPicName());
					if (file.length() > 1048575) {
						MessageForm.Message("监控图片大小不能超过1M。");
						return;
					}
				}

				if (!metroNodeService.checkMetroNode(metroNode, getBeforeNode())) {
					MessageForm.Message("提示信息", "保存图元信息失败，该节点已存在。", SWT.ICON_INFORMATION);
					return;
				}

				mapReset(map, metroNode);
				//				map.getDataState().update();

				try {
					metroNodeService.saveMetroNodes(metroNode, getBeforeNode(), null, null, updateFlag);
				} catch (Exception e) {
					throw new ApplicationException("保存地图信息异常。" + e);
				}

				MapTreeView mapTreeView = (MapTreeView) getFrameWork().getView(MapTreeView.class);
				mapTreeView.setChanged(false);
				mapTreeView.getGraphicMapGenerator().getMapRootItem(false);
				AFCApplication.refresh();
				if (mapTreeView != null) {
					mapTreeView.refresh();
				}

				MapEditorView mapEditorView = (MapEditorView) getFrameWork().getView(MapEditorView.class);
				if (mapEditorView != null) {
					mapEditorView.getMap().clear();
					mapEditorView.getMap().redraw();
				}

				//刷新界面
				//				EditorFrameWork editorFrameWork = context.getFrameWork();
				//				editorFrameWork.getShell().redraw();
				//				for (FrameWorkView view : editorFrameWork.getViews()) {
				//					view.refresh();
				//				}
				dialog.close();
				MessageForm.Message("提示信息", "保存图元信息成功。", SWT.ICON_INFORMATION);
				//				asynExecute(new BackgroundWorkor<MetroNode, String>(metroNode) {
				//					public String execute(MetroNode paramter) {
				//				        System.out.println(ReflectionToStringBuilder.toString(paramter));
				//						return null;
				//					}
				//				}, actionCallback);
			}
		}
	}

}
