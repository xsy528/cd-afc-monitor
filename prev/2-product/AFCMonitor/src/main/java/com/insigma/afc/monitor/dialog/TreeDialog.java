package com.insigma.afc.monitor.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.afc.topology.MetroNode;
import com.insigma.commons.editorframework.EditorFrameWork;
import com.insigma.commons.editorframework.FrameWorkDialog;
import com.insigma.commons.ui.tree.ITreeProvider;
import com.insigma.commons.ui.tree.ObjectTree;

@SuppressWarnings("unchecked")
public class TreeDialog extends FrameWorkDialog {

	protected Class nodeClass;

	protected int treeDepth = -1;

	protected ObjectTree nodeTree;

	protected ITreeProvider treeProvider;

	protected List<Object> selections;
	//    
	//    protected  SashForm sashForm;

	private int expandedDepth;

	public int getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	public ITreeProvider getTreeProvider() {
		return treeProvider;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	public List<Object> getSelections() {
		if (nodeTree != null)
			return nodeTree.getChecked(nodeClass);
		else
			return selections;
	}

	public void setSelections(List<Object> selections) {
		this.selections = selections;
	}

	public List<Long> getIDs() {
		List<Long> ids = new ArrayList<Long>();
		if (nodeTree != null) {
			List<Object> objs = nodeTree.getChecked(nodeClass);
			for (Object obj : objs) {
				if (obj instanceof MetroNode) {
					ids.add(((MetroNode) obj).id());
				}
			}
		} else {
			for (Object obj : selections) {
				if (obj instanceof MetroNode) {
					ids.add(((MetroNode) obj).id());
				}
			}
		}
		return ids;
	}

	public Class getNodeClass() {
		return nodeClass;
	}

	public void setNodeClass(Class nodeClass) {
		this.nodeClass = nodeClass;
	}

	public TreeDialog(EditorFrameWork parent, int style) {
		super(parent, style | FrameWorkDialog.BUTTONBAR);
	}

	protected void createContents(Composite parent) {

		final GridLayout gridLayout = new GridLayout();
		if (treeProvider != null) {
			gridLayout.numColumns = 2;
		} else {
			gridLayout.numColumns = 1;
		}
		parent.setLayout(gridLayout);

		if (treeProvider != null) {
			nodeTree = new ObjectTree(parent, SWT.CHECK | SWT.BORDER);
			nodeTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			nodeTree.setTreeProvider(treeProvider);

			if (selections != null) {
				nodeTree.setChecked(selections);
			}
			if (treeDepth > 0) {
				nodeTree.setDepth(treeDepth);
			}

			nodeTree.setExpanded(expandedDepth);
		}
	}

	/**
	 * @param expandedDepth the expandedDepth to set
	 */
	public void setExpandedDepth(int expandedDepth) {
		this.expandedDepth = expandedDepth;
	}

	/**
	 * @return the expandedDepth
	 */
	public int getExpandedDepth() {
		return expandedDepth;
	}
}
