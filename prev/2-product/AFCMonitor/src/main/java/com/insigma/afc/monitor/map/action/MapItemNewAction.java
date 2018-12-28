/**
 * 
 */
package com.insigma.afc.monitor.map.action;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;

import com.insigma.afc.monitor.map.builder.AbstractGraphicMapBuilder;
import com.insigma.afc.topology.AFCNodeLocation;
import com.insigma.afc.topology.MetroACC;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceId;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.afc.topology.MetroStationId;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.dialog.ObjectEditorDialog;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.function.form.Form.FormMode;
import com.insigma.commons.reflect.BeanUtil;
import com.insigma.commons.ui.MessageForm;

/**
 * @author Administrator
 *
 */
public class MapItemNewAction extends MapItemAction<String> {
	private static final String NEW_PASTE_ITEM = "new_add_item";

	AFCNodeLocation newImageLocation = new AFCNodeLocation(30, 30, 0);
	AFCNodeLocation newTextLocation = new AFCNodeLocation(30, 30, 0);

	private MetroNode beanData;

	private MapItem mapItem;

	private ObjectEditorDialog dialog;

	private AbstractGraphicMapBuilder mapItembuilder;

	private MetroNode beforeNode;

	public static List<MetroNode> addItemList = new ArrayList<MetroNode>();

	public MapItemNewAction(String name, MapItem item, AbstractGraphicMapBuilder mapItembuilder, MetroNode data) {
		super(name);
		setImage("/com/insigma/commons/ui/toolbar/new.png");
		setData(item);
		this.beanData = data;
		this.mapItem = item;
		this.mapItembuilder = mapItembuilder;
		setHandler(new ActionHandlerAdapter() {

			@Override
			public void perform(final ActionContext context) {
				MapItem newMapItem = (MapItem) context.getData(NEW_PASTE_ITEM);
				if (newMapItem != null) {
					mapItem.getDataState().add(newMapItem);
					return;
				}
				final EditorFrameWork editorFrameWork = context.getFrameWork();
				if (editorFrameWork == null) {
					return;
				}

				Form<MetroNode> form = null;
				if (beanData instanceof MetroLine) {
					MetroLine line = (MetroLine) beanData;
					MetroStation newData = new MetroStation();
					newData.setId(new MetroStationId(line.getLineID(), null));
					newData.setLineName(line.getLineName());
					newData.setImageLocation(newImageLocation);
					newData.setTextLocation(newTextLocation);

					try {
						MetroNode cloneNode = (MetroNode) BeanUtil.cloneObject(newData);
						setBeforeNode(cloneNode);
					} catch (Exception e) {
						throw new ApplicationException("", e);
					}

					form = new Form<MetroNode>(newData);
					form.setFormMode(FormMode.MODIFY);
				} else if (beanData instanceof MetroStation) {
					MetroStation station = (MetroStation) beanData;
					MetroDevice newData = new MetroDevice();
					// TODO 
					//					newData.setId(new MetroDeviceId(station.getId().getLineId(), station.getId().getStationId(), null));
					newData.setId(new MetroDeviceId(station.getId().getLineId(),
							Integer.valueOf(Long.toHexString(station.getId().getStationId())), null));
					newData.setLineName(station.getLineName());
					newData.setStationName(station.getStationName());
					newData.setImageLocation(newImageLocation);
					newData.setTextLocation(newTextLocation);

					try {
						MetroNode cloneNode = (MetroNode) BeanUtil.cloneObject(newData);
						setBeforeNode(cloneNode);
					} catch (Exception e) {
						throw new ApplicationException("", e);
					}

					form = new Form<MetroNode>(newData);
					form.setFormMode(FormMode.MODIFY);
					// 增加ACC节点
				} else if (beanData instanceof MetroACC) {
					MetroACC acc = (MetroACC) beanData;
					MetroLine newData = new MetroLine();
					newData.setLineID(acc.getLineId());
					newData.setRemark(acc.getAccName());
					newData.setImageLocation(newImageLocation);
					newData.setTextLocation(newTextLocation);

					try {
						MetroNode cloneNode = (MetroNode) BeanUtil.cloneObject(newData);
						setBeforeNode(cloneNode);
					} catch (Exception e) {
						throw new ApplicationException("", e);
					}

					form = new Form<MetroNode>(newData);
					form.setFormMode(FormMode.MODIFY);
				}

				dialog = new ObjectEditorDialog(editorFrameWork, form, SWT.None);
				dialog.setText(getName());
				dialog.setDescription(getName());
				dialog.setSize(500, 600);
				SaveAction saveaction = new SaveAction(context);
				dialog.getActions().add(saveaction);
				dialog.open();

			}

			@Override
			public void unPerform(ActionContext context) {
				MapItem newMapItem = (MapItem) context.getData(NEW_PASTE_ITEM);
				if (newMapItem != null) {
					mapItem.getDataState().delete(newMapItem);
					//刷新树
					removeTreeNode(newMapItem);
				}
			}

		});
	}

	@Override
	public boolean IsEnable() {
		Object selectedItem = getData(ActionContext.SELECTED_ITEM);
		if (selectedItem != null) {
			return false;
		}
		return true;
	}

	public class SaveAction extends Action {

		ActionContext parentContext;

		public SaveAction(ActionContext context) {
			super("确定(&O)");
			this.parentContext = context;
			setHandler(new SaveActionHandler());
		}

		private class SaveActionHandler extends ActionHandlerAdapter {

			@Override
			public void perform(ActionContext context) {
				MetroNode metroNode = (MetroNode) context.getData(ActionContext.RESULT_DATA);
				if (metroNode instanceof MetroDevice) {
					MetroDevice metroDevice = (MetroDevice) metroNode;
					metroDevice.getId()
							.setStationId(Integer.valueOf(metroDevice.getId().getStationId().toString(), 16));
					MetroDevice metroDeviceBefore = (MetroDevice) getBeforeNode();
					if (metroDevice.getId().getDeviceId() == null) {
						MessageForm.Message("设备编号不能为空。");
						return;
					}
					//					if ((metroDevice.getId().getDeviceId() > 255 && metroDevice.getId().getDeviceId() < 999)
					//							|| metroDevice.getId().getDeviceId() > 999 && metroDevice.getId().getDeviceId() < 10000000) {
					//						MessageForm.Message("设备编号不合法,输3位设备ID(<255)或是完整节点号");
					//						return;
					//					}
					if (metroDevice.getId().getDeviceId() > 99) {
						//TODO：由于目前数据库无法兼容3位设备ID，因此此处需转化为完整节点号，后续希望可以调整，兼容——yang
						//						metroDevice.getId().setDeviceId(metroDevice.getId().getStationId() * 10000
						//								+ metroDevice.getDeviceType() * 100 + metroDevice.getId().getDeviceId());
						MessageForm.Message("设备编号不合法。");
						return;
					}
					if (metroDevice.getId().getDeviceId() <= 99) {
						Integer stationId16 = Integer.valueOf(Integer.toHexString(metroDevice.getId().getStationId()));
						Integer deviceType16 = Integer.valueOf(Integer.toHexString(metroDevice.getDeviceType()));
						metroDevice.getId().setDeviceId(
								stationId16 * 10000 + deviceType16 * 100 + metroDevice.getId().getDeviceId());
						String deviceId = metroDevice.getId().getDeviceId().toString();
						BigInteger srch1 = new BigInteger(deviceId, 16);
						metroDevice.getId().setDeviceId(Long.valueOf(srch1.toString()));
					}
					if (metroDevice.getDeviceName() == null) {
						MessageForm.Message("设备名称不能为空。");
						return;
					}
					if (!metroDevice.getId().getLineId().equals(metroDeviceBefore.getId().getLineId())) {
						MessageForm.Message("线路编号不可修改。");
						return;
					}
					if (!metroDevice.getId().getStationId()
							.equals(Integer.valueOf(metroDeviceBefore.getId().getStationId().toString(), 16))) {
						MessageForm.Message("车站编号不可修改。");
						return;
					}
					if (!metroDevice.getLineName().equals(metroDeviceBefore.getLineName())) {
						MessageForm.Message("线路名不可修改。");
						return;
					}
					if (!metroDevice.getStationName().equals(metroDeviceBefore.getStationName())) {
						MessageForm.Message("车站名不可修改。");
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
					//						MessageForm.Message("车站编号不能小于21。");
					//						return;
					//					}
					//					if (metroStation.getId().getStationId() > 499) {
					//						MessageForm.Message("车站编号不能大于99。");
					//						return;
					//					}
					if (metroStation.getId().getStationId() > 99) {
						MessageForm.Message("车站编号不合法.");
						return;
					}

					if (metroStation.getId().getStationId() < 99) {
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
					if (!metroStation.getId().getLineId().equals(metroStationBefore.getId().getLineId())) {
						MessageForm.Message("线路编号不可修改。");
						return;
					}
					if (!metroStation.getLineName().equals(metroStationBefore.getLineName())) {
						MessageForm.Message("线路名不可修改。");
						return;
					}
					File file = new File(metroStation.getPicName());
					if (file.isFile() && file.length() > 1048575) {
						MessageForm.Message("车站图片不能超过1M。");
						return;
					}

					// 	保存线路信息
				} else if (metroNode instanceof MetroLine) {
					MetroLine metroLine = (MetroLine) metroNode;
					//MetroLine metroLineBefore = (MetroLine) getBeforeNode();
					if (Short.valueOf(metroLine.getLineID()) == null) {
						MessageForm.Message("线路编号不能为空。");
						return;
					}

					if (Short.valueOf(metroLine.getLineID()) > 99) {
						MessageForm.Message("线路编号不合法.");
						return;
					}

					//					if (metroStation.getId().getStationId() < 99) {
					//						metroStation.getId().setStationId(
					//								metroStation.getId().getLineId() * 100 + metroStation.getId().getStationId());
					//					}

					if (metroLine.getLineName() == null) {
						MessageForm.Message("线路名称不能为空。");
						return;
					}
					if (metroLine.getPicName() == null) {
						MessageForm.Message("线路图片不能为空。");
						return;
					}
					//					if (!metroStation.getId().getLineId().equals(metroLineBefore.getId().getLineId())) {
					//						MessageForm.Message("ACC编号不可修改。");
					//						return;
					//					}
					//					if (!metroStation.getLineName().equals(metroLineBefore.getLineName())) {
					//						MessageForm.Message("ACC名不可修改。");
					//						return;
					//					}
					File file = new File(metroLine.getPicName());
					if (file.isFile() && file.length() > 1048575) {
						MessageForm.Message("线路图片不能超过1M。");
						return;
					}

				}

				MapItem newMapItem = mapItembuilder.buildGraphicMap(metroNode);
				newMapItem.setValue(metroNode);
				if (mapItem.isExists(newMapItem)) {
					MessageForm.Message("节点ID" + metroNode.id() + "已经存在。");
					return;
				}
				mapItem.getDataState().add(newMapItem);
				parentContext.setData(NEW_PASTE_ITEM, newMapItem);

				//刷新树
				addTreeNode(newMapItem);
				dialog.close();
			}
		}
	}

	public MetroNode getBeforeNode() {
		return beforeNode;
	}

	public void setBeforeNode(MetroNode beforeNode) {
		this.beforeNode = beforeNode;
	}

}
