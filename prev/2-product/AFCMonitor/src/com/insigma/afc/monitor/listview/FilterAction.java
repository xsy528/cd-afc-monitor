package com.insigma.afc.monitor.listview;

import org.eclipse.swt.SWT;

import com.insigma.afc.topology.MetroNode;
import com.insigma.afc.view.provider.CommonTreeProvider;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionContext;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.ActionTreeNode;
import com.insigma.commons.editorframework.graphic.editor.MapItem;
import com.insigma.commons.editorframework.graphic.editor.MapTreeView;
import com.insigma.commons.editorframework.view.ListView;
import com.insigma.commons.ui.MessageForm;
import com.insigma.commons.ui.tree.ITreeProvider;

public class FilterAction extends Action {
	ListView view;

	public FilterAction(ListView parent) {
		super("设备状态过滤");
		this.view = parent;
		setImage("/com/insigma/commons/ui/toolbar/filter.png");
		setHandler(new EquStatusFilterActionHandler());
	}

	private String formKey;

	private FilterForm filterForm;

	private ITreeProvider treeProvider;

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public FilterForm getFilterForm() {
		return filterForm;
	}

	public void setFilterForm(FilterForm filterForm) {
		this.filterForm = filterForm;
	}

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	public class EquStatusFilterActionHandler extends ActionHandlerAdapter {

		public void perform(ActionContext action) {
			if (view == null) {
				MessageForm.Message("错误信息", "框架配置错误,未找到视图。", SWT.ICON_ERROR);
				return;
			}

			FilterForm form = (FilterForm) view.getData(formKey);

			if (form == null) {
				form = getFilterForm();
				view.setData(formKey, form);
			}

			FilterDialog dlg = new FilterDialog(action.getFrameWork(), SWT.NONE);
			MetroNode metroNode = getMetroNode();
			ITreeProvider treeProvider = getTreeProvider();
			if (treeProvider instanceof CommonTreeProvider) {
				((CommonTreeProvider) treeProvider).setRootNode(metroNode);
			}
			dlg.setTreeProvider(getTreeProvider());
			dlg.setFilerForm(form);
			FilterForm result = (FilterForm) dlg.open();
			if (result != null) {
				view.refresh();
			}
		}
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
