package com.insigma.commons.ui.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.tree.TreeNode;

public class MultiCombo extends EnhanceComposite implements IInputControl {

	protected ITreeProvider treeProvider;

	protected TreeNode treeNode;

	protected int depth;

	protected List<Combo> combos;

	private Combo rootCombo;

	public MultiCombo(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = (GridLayout) getLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 5;
		combos = new ArrayList<Combo>();
		setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
		treeNode = treeProvider.getTree();
		rootCombo = combos.get(0);
		rootCombo.setData(treeNode);
		for (TreeNode subnode : treeNode.getChilds()) {
			rootCombo.add(subnode.getText());
		}
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
		clear();
		combos.clear();
		GridLayout layout = (GridLayout) getLayout();
		layout.numColumns = depth;
		setLayout(layout);
		for (int i = 0; i < depth; i++) {
			Combo combo = new Combo(this, SWT.READ_ONLY);
			combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			combo.addSelectionListener(new ComboSelectionAdapter(i));
			combos.add(combo);
		}
	}

	public class ComboSelectionAdapter extends SelectionAdapter {

		private int comboIndex;

		public ComboSelectionAdapter(int comboIndex) {
			this.comboIndex = comboIndex;
		}

		public void widgetSelected(SelectionEvent sevent) {
			if (comboIndex < depth - 1) {
				Combo combo = combos.get(comboIndex);
				TreeNode node = (TreeNode) combo.getData();
				TreeNode selectNode = node.getChilds().get(combo.getSelectionIndex());
				for (int ci = comboIndex + 1; ci < depth; ci++) {
					combos.get(ci).removeAll();
				}
				Combo subcombo = combos.get(comboIndex + 1);
				subcombo.setData(selectNode);
				if (selectNode.getChilds().isEmpty()) {
					subcombo.setVisible(false);
				} else {
					subcombo.setVisible(true);
				}
				for (TreeNode subnode : selectNode.getChilds()) {
					subcombo.add(subnode.getText());
				}
			}
		}
	}

	public Object getObjectValue() {
		for (int i = depth - 1; i >= 0; i--) {
			if (combos.get(i).getSelectionIndex() >= 0) {
				TreeNode node = (TreeNode) combos.get(i).getData();
				return node.getChilds().get(combos.get(i).getSelectionIndex()).getKey();
			}
		}
		return null;
	}

	public void setObjectValue(Object objectValue) {
		if (rootCombo == null) {
			return;
		}
		if (objectValue != null) {
			int in[] = getIndex(objectValue);
			if (in[0] == -1) {
				return;
			}
			rootCombo.select(in[0]);
			new ComboSelectionAdapter(0).widgetSelected(null);
			if (in.length > 1 && combos.size() > 1) {
				if (combos.get(1).getItemCount() > in[1]) {
					combos.get(1).select(in[1]);
				}
			}
			rootCombo.layout();
		}
	}

	private int[] getIndex(Object val) {
		if (val == null) {
			return new int[] { -1 };
		}

		if (treeNode == null) {
			return new int[] { -1 };
		}
		boolean isArray = val.getClass().isArray();
		List<TreeNode> cs = treeNode.getChilds();
		int i = 0;
		for (TreeNode treeNode : cs) {
			if (isArray && treeNode.getKey().getClass().isArray()
					&& Arrays.equals((Object[]) val, (Object[]) treeNode.getKey())) {
				return new int[] { i };
			} else if (treeNode.getKey().equals(val)) {
				return new int[] { i };
			} else {
				List<TreeNode> subch = treeNode.getChilds();
				if (subch != null) {
					int j = 0;
					for (TreeNode treeNode2 : subch) {
						if (isArray && treeNode2.getKey().getClass().isArray()
								&& Arrays.equals((Object[]) val, (Object[]) treeNode2.getKey())) {
							return new int[] { i, j };
						} else if (treeNode2.getKey().equals(val)) {
							return new int[] { i, j };
						}
						j++;
					}
				}
			}
			i++;
		}
		return new int[] { -1 };
	}
}
