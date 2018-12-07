package com.insigma.afc.monitor.listview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.insigma.afc.monitor.dialog.TreeDialog;
import com.insigma.afc.monitor.listview.equstatus.EquStatusFilterForm;
import com.insigma.afc.monitor.listview.event.EventFilterForm;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.afc.topology.MetroLine;
import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.topology.MetroStation;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.framework.function.form.Form;
import com.insigma.commons.framework.view.form.FormEditor;
import com.insigma.commons.ui.MessageForm;

public class FilterDialog extends TreeDialog {

	private FilterForm filerForm;

	private FormEditor<FilterForm> editor;

	public FilterForm getFilerForm() {
		return filerForm;
	}

	public void setFilerForm(FilterForm filerForm) {
		this.filerForm = filerForm;
	}

	public FilterDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	@Override
	protected void createAction(List<Action> actions) {
		actions.add(new Action("确定 (&O)", new ActionHandlerAdapter() {
			public void perform(ActionContext action) {
				List<Short> lines = new ArrayList<Short>();
				List<Integer> stations = new ArrayList<Integer>();
				List<Long> devices = new ArrayList<Long>();

				filerForm = editor.getForm().getEntity();
				if (filerForm instanceof EquStatusFilterForm) {
					Date startTime = ((EquStatusFilterForm) filerForm).getStartTime();
					Date endTime = ((EquStatusFilterForm) filerForm).getEndTime();
					if (startTime != null && endTime != null) {
						if (startTime.after(endTime)) {
							MessageForm.Message("提示信息", "开始时间不能大于结束时间。");
							return;
						}
					}
				} else if (filerForm instanceof EventFilterForm) {
					Date startTime = ((EventFilterForm) filerForm).getStartTime();
					Date endTime = ((EventFilterForm) filerForm).getEndTime();
					if (startTime != null && endTime != null) {
						if (startTime.after(endTime)) {
							MessageForm.Message("提示信息", "开始时间不能大于结束时间。");
							return;
						}
					}

				}

				List<Object> selections = nodeTree.getChecked();
				if (selections == null || selections.size() == 0) {
					MessageForm.Message("请至少选择一个节点。");
					return;
				}
				filerForm.setSelections(selections);
				filerForm.addNodeSelectMap(getMetroNode().getNodeId(), selections);

				for (Object o : selections) {
					if (o instanceof MetroLine) {
						lines.add(((MetroLine) o).getLineID());
					}
					if (o instanceof MetroStation) {
						stations.add(((MetroStation) o).getId().getStationId());
					}
					if (o instanceof MetroDevice) {
						devices.add(((MetroDevice) o).getId().getDeviceId());
					}
				}
				setResult(filerForm);
				getShell().close();
			}

		}));
		actions.add(new CloseAction());
	}

	protected void createContents(Composite parent) {
		setSize(650, 500);
		setDescription("说明：对信息列表的过滤条件进行设置");
		setTitle("信息列表过滤");
		setText("信息列表过滤");

		super.createContents(parent);

		if (nodeTree != null) {
			nodeTree.setChecked(filerForm.getSelections());
		}

		editor = new FormEditor<FilterForm>(parent, SWT.BORDER);
		editor.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		editor.setForm(new Form<FilterForm>(filerForm, 1));

		editor.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}

	public MetroNode getMetroNode() {
		MapTreeView tree = (MapTreeView) getFrameWork().getView(MapTreeView.class);
		if (tree != null) {
			ActionTreeNode treeNode = tree.getSelection();
			if (treeNode != null && treeNode.getValue() != null) {
				if (treeNode.getValue() instanceof MapItem) {
					MapItem map = (MapItem) treeNode.getValue();
					if (map != null && map.getValue() != null) {
						return (MetroNode) map.getValue();
					}
				}
			}
		}
		return null;
	}
}
