package com.insigma.afc.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.tree.TreeNode;
import com.insigma.commons.ui.widgets.Combo;
import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IInputControl;

/**
 * 
 * @author wangxinhao
 * 
 */
public class ComboGroup extends EnhanceComposite implements IInputControl {

	public static class Target {

		private Integer key;

		private Integer value;

		public Integer getKey() {
			return key;
		}

		public void setKey(Integer key) {
			this.key = key;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

	}

	protected ITreeProvider treeProvider;

	protected TreeNode treeNode;

	protected int depth;

	protected List<Combo> combos;

	private String[] labels;

	private List<Button> buttons;

	private Object[] values;

	private int style;

	private GridLayout layout;

	public ComboGroup(Composite parent, int style) {
		super(parent, style);
		this.style = style;
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.numColumns = 4;
		this.setLayout(layout);
		buttons = new ArrayList<Button>();
		combos = new ArrayList<Combo>();
	}

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
		treeNode = treeProvider.getTree();
		Combo combo = combos.get(0);
		combo.setData(treeNode);
		if (treeNode.getChilds().size() != depth) {
			return;
		}
		for (int i = 0; i < depth; i++) {
			combo = combos.get(i);
			TreeNode childNode = treeNode.getChilds().get(i);
			buttons.get(i).setText(childNode.getText());
			combo.setData(childNode);
			for (TreeNode subnode : childNode.getChilds()) {
				combo.add(subnode.getText());
			}
			combo.select(0);
			combo.setEnabled(false);
		}
		buttons.get(0).setSelection(true);
		combos.get(0).setEnabled(true);

	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
		clear();
		combos.clear();
		for (int i = 0; i < depth; i++) {
			Button button = new Button(this, SWT.RADIO);
			// buttons[i].setText((String) values[i]);
			button.setBackground(this.getBackground());
			button.addSelectionListener(new ButtonSelectionAdapter(i));
			buttons.add(button);
			Combo combo = new Combo(this, SWT.READ_ONLY);
			combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			combo.addSelectionListener(new ComboSelectionAdapter(i));
			combos.add(combo);
		}
	}

	public class ButtonSelectionAdapter extends SelectionAdapter {

		private int buttonIndex;

		public ButtonSelectionAdapter(int buttonIndex) {
			this.buttonIndex = buttonIndex;
		}

		public void widgetSelected(SelectionEvent arg0) {
			if (buttonIndex < depth) {
				for (int i = 0; i < combos.size(); i++) {
					if (buttonIndex != i) {
						combos.get(i).setEnabled(false);
					} else {
						combos.get(i).setEnabled(true);
					}
				}
			}
		}
	}

	public class ComboSelectionAdapter extends SelectionAdapter {

		private int comboIndex;

		public ComboSelectionAdapter(int comboIndex) {
			this.comboIndex = comboIndex;
		}

		public void widgetSelected(SelectionEvent arg0) {

		}
	}

	public Object getObjectValue() {
		for (int i = depth - 1; i >= 0; i--) {
			if (buttons.get(i).getSelection()) {
				TreeNode node = (TreeNode) combos.get(i).getData();
				Target target = new Target();
				target.setKey(i);
				target.setValue((Integer) node.getChilds().get(Math.abs(combos.get(i).getSelectionIndex())).getKey());
				return target;
			}
		}
		return null;
	}

	public void setObjectValue(Object value) {

	}

	public void reset() {
		for (Combo combo : combos) {
			combo.select(0);
		}
	}

	public Object[] getValues() {
		return this.values;
	}

	public void setValues(Object[] values) {
		this.values = values;
		for (int i = 0; i < values.length; i++) {
			buttons.get(i).setData(values[i]);
		}
	}
}
