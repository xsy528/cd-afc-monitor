package com.insigma.commons.ui.widgets;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Sash;

/**
 * 
 * @author DingLuofeng
 * 
 */
public class SashForm extends org.eclipse.swt.custom.SashForm {

	private List<Control> showControls = new LinkedList<Control>();

	//	private List<Control> contentControls = new LinkedList<Control>();
	private int[] oldWeights = new int[] { 1, 1 };

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SashForm(Composite parent, int style) {
		super(parent, style);
		if (parent instanceof SashForm) {
			((SashForm) parent).listShowControl();
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void minimize(Control control) {
		//移除显示控件
		removeShowControl(control);
		for (Control ctrl : showControls) {
			setMaximizedControl(ctrl);
			//windowLayout(this);
			return;
		}
		//如果当前SashForm控件都已经最小化，则显示上层控件，
		//如果上层也是SashForm且不为null
		Composite parent = getParent();
		if (parent != null && parent instanceof SashForm) {
			SashForm sashForm = (SashForm) parent;
			sashForm.minimize(this);
		}
	}

	public void listShowControl() {
		Control[] children = getChildren();
		for (Control ctrl : children) {
			if (!(ctrl instanceof Sash)) {
				if (!showControls.contains(ctrl)) {
					showControls.add(ctrl);
				}

			}
		}
	}

	public void addShowControl(Control control) {
		if (!showControls.contains(control)) {
			showControls.add(control);
		}
	}

	public void removeShowControl(Control control) {
		showControls.remove(control);
	}

	public void restore(Control control) {
		addShowControl(control);
		if (getShowControls().size() > 1) {
			setMaximizedControl(null);
			setOldWeights();
		} else {
			setMaximizedControl(control);
		}

		Composite parent = getParent();
		if (parent != null && parent instanceof SashForm) {
			SashForm sashForm = (SashForm) parent;
			sashForm.restore(this);
		}
	}

	@Override
	public void setWeights(int[] weights) {
		super.setWeights(weights);
		this.oldWeights = weights;
	}

	/**
	 * @return the showControls
	 */
	public List<Control> getShowControls() {
		return showControls;
	}

	/**
	 * @param showControls the showControls to set
	 */
	public void setShowControls(List<Control> showControls) {
		this.showControls = showControls;
	}

	/**
	 * @param oldWeights the oldWeights to set
	 */
	public void setOldWeights() {
		super.setWeights(oldWeights);
	}

}
