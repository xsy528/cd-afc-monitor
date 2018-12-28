package com.insigma.afc.monitor.dialog;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.editorframework.FrameWorkView;
import com.insigma.commons.editorframework.FrameWorkViewFolder;

/**
 * 
 * @author changjin_qiu
 *
 */
public class MetroNodeMonitorDialog extends FrameWorkDialog {
	protected MetroDevice device;
	protected FrameWorkViewFolder views;
	EditorFrameWork editorFrameWork;

	public MetroNodeMonitorDialog(MetroDevice device, EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
		editorFrameWork = parent;
		this.device = device;

		setText("设备监视");
		setTitle("设备监视");
		setDescription("描述：[" + device.name() + "] 设备状态监控");
		setSize(725, 610);
		views = new FrameWorkViewFolder(parent, SWT.BORDER);
	}

	protected void createAction(List<Action> actions) {
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {
		GridLayout gridLayout = new GridLayout();
		parent.setLayout(gridLayout);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		views.setParent(parent);
		views.setMaximizeVisible(false);
		views.setMinimizeVisible(false);
		views.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		views.setEditorFrameWork(editorFrameWork);
		parent.layout();
	}

	public void addView(FrameWorkView view) {
		this.views.addView(view);
	}

	/**
	 * @return the device
	 */
	public MetroDevice getDevice() {
		return device;
	}

}
