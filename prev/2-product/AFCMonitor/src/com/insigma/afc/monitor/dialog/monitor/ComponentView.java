package com.insigma.afc.monitor.dialog.monitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.afc.application.AFCApplication;
import com.insigma.afc.constant.ColorConstants;
import com.insigma.afc.monitor.service.IDeviceModuleFactory;
import com.insigma.afc.topology.AFCNodeLocation;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroDeviceModule;
import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.ui.form.ObjectTableViewer;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.swtdesigner.SWTResourceManager;

public class ComponentView extends FrameWorkView {

	private MetroDevice equipment;

	private ObjectTableViewer viewer;

	private EnhanceComposite map;

	CLabel loadingLabel = null;

	public ComponentView(Composite arg0, int arg1) {
		super(arg0, arg1);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);
		viewer = new ObjectTableViewer(this, SWT.BORDER);
		viewer.setLayoutData(new GridData(GridData.FILL_BOTH));

		map = new EnhanceComposite(this, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 300;
		map.setLayoutData(gridData);
		loadingLabel = new CLabel(map, SWT.NONE);
		loadingLabel.setText("正在加载...");
		loadingLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
	}

	public MetroDevice getEquipment() {
		return equipment;
	}

	public void setEquipment(MetroDevice equipment) {
		this.equipment = equipment;
		if (equipment == null) {
			return;
		}
		refresh();

	}

	public List<MetroDeviceModule> initDeviceModules(MetroDevice device) throws Exception {
		IDeviceModuleFactory service = AFCApplication.getService(IDeviceModuleFactory.class);
		if (service == null) {
			throw new IllegalStateException("IDeviceModuleFactory无法初始化，可能是Spring还没有初始化.");
		}
		return service.getMetroDeviceModuleList(equipment);
	}

	public void refresh() {
		if (equipment != null) {
			List<MetroDeviceModule> subNodes = null;
			List<MetroDeviceModuleItem> viewItem = new ArrayList<MetroDeviceModuleItem>();
			try {
				subNodes = initDeviceModules(equipment);//初始化部件信息
			} catch (Exception e) {
				loadingLabel.setText("初始化部件信息异常");
				loadingLabel.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				return;
			}

			for (MetroDeviceModule metroDeviceModule : subNodes) {
				MetroDeviceModuleItem deviceModuleItem = new MetroDeviceModuleItem(metroDeviceModule.getModuleName(),
						metroDeviceModule.getStatus(), metroDeviceModule.getRemark());
				viewItem.add(deviceModuleItem);
			}
			this.viewer.setHeader(MetroDeviceModuleItem.class);
			this.viewer.setObjectList(viewItem);//表格数据加载
			map.clear();

			Image image = SWTResourceManager.getImage(DeviceMonitorDialog.class, equipment.getPicName());
			map.setBackgroundImage(image);//加载图片

			{
				GridData gridData = new GridData();
				gridData.heightHint = image.getBounds().height;
				gridData.widthHint = image.getBounds().width;
				map.setLayoutData(gridData);
			}

			//设置图片的状态文字说明
			if (subNodes != null) {
				for (MetroDeviceModule component : subNodes) {
					map.setLayout(null);
					CLabel label = new CLabel(map, SWT.CENTER | SWT.BORDER);
					label.setText(component.name());
					AFCNodeLocation textLocation = component.getTextLocation();
					label.setBounds(textLocation.getX(), textLocation.getY(), 80, 30);
					label.setBackground(getForeground(component.getStatus()));
				}
			}

			layout();

			getParent().layout(true);
		}
	}

	private Color getForeground(Short status) {
		if (status >= 2) {
			return ColorConstants.COLOR_ERROR;
		} else if (status >= 1) {
			return ColorConstants.COLOR_WARN;
		} else {
			return ColorConstants.COLOR_NORMAL;
		}
	}
}
