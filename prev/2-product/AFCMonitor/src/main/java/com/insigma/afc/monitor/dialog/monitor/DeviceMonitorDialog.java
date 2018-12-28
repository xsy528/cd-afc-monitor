package com.insigma.afc.monitor.dialog.monitor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.editorframework.view.ListView;

public class DeviceMonitorDialog extends FrameWorkDialog {
	private Log logger = LogFactory.getLog(DeviceMonitorDialog.class);

	private MetroDevice device;

	private ListView eventView;

	private ComponentView componentView;

	public MetroDevice getDevice() {
		return device;
	}

	public void setDevice(MetroDevice device) {
		this.device = device;
	}

	public DeviceMonitorDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
		setSize(725, 700);
	}

	@Override
	public void createAction(List<Action> actions) {
		actions.add(new Action("刷新 (&R)", new ActionHandlerAdapter() {
			public void perform(Action action) {
				refresh();
			}
		}));
		actions.add(new CloseAction());
	}

	public void refresh() {
	}

	@Override
	protected void createContents(Composite parent) {

		setText("设备监视");
		setTitle("设备监视");
		setDescription("描述：设备状态监控");
		GridLayout gridLayout = new GridLayout();
		parent.setLayout(gridLayout);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		componentView = new ComponentView(parent, SWT.NONE);
		componentView.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		eventView = new ListView(parent, SWT.NONE, DeviceEventViewItem.class);
		eventView.setText("事件列表 ");
		eventView.setLayoutData(new GridData(GridData.FILL_BOTH));
		eventView.removeAll();

		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {
				refresh();
			}
		});

		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {
				componentView.setEquipment(device);
			}
		});

	}
}
