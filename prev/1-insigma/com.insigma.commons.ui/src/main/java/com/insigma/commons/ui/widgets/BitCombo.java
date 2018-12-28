/**
 * 
 */
package com.insigma.commons.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.insigma.commons.ui.widgets.BitCombo.ButtonGroupDialog.BitListResult;
import com.swtdesigner.SWTResourceManager;

/**
 * @author DingLuofeng
 *
 */
public class BitCombo extends EnhanceComposite implements IInputControl {

	private Text dateText;

	private ButtonGroupDialog buttonGroup;

	private String[] labels;

	private Object objectValue;

	private int numColumns = 2;

	private int style = SWT.CHECK;

	public BitCombo(Composite parent, int style) {
		this(parent, 2, style);
	}

	public BitCombo(Composite parent, int numColumns, int style) {
		super(parent, style);
		this.numColumns = numColumns;
		setLayout(new GridLayout(1, false));
		createContext();
		this.style = style;
	}

	private final void createContext() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginLeft = 0;
		gridLayout.marginTop = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.numColumns = 2;
		super.setLayout(gridLayout);

		GridData griddata = new GridData(SWT.FILL, SWT.FILL, true, true);
		dateText = new Text(this, SWT.READ_ONLY);
		dateText.setBackground(SWTResourceManager.getColor(255, 255, 255));

		dateText.setVisible(true);
		dateText.setEditable(false);
		dateText.setLayoutData(griddata);

		Button bntselect = new Button(this, SWT.DOWN | SWT.ARROW | SWT.FLAT);
		bntselect.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		bntselect.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent arg0) {
				if (arg0.widget instanceof Button) {
					buttonGroup = new ButtonGroupDialog(Display.getDefault(), numColumns, style);
					buttonGroup.setLabels(labels);
					if (objectValue != null) {
						buttonGroup.setObjectValue(objectValue);
					}
					Point pos = dateText.toDisplay(0, 0);
					int textHeight = dateText.getBounds().height;
					int textWidth = dateText.getBounds().width;
					pos.y = pos.y + textHeight;
					pos.y = pos.y - 2;
					pos.x = pos.x + textWidth;
					buttonGroup.setLocation(pos);

					BitListResult open = buttonGroup.open();
					if (open != null) {
						objectValue = open.value;
						//						dateText.setData(objectValue);
						String[] checkedLabels = open.checkedLabels;

						dateText.setText(array2String(checkedLabels));
						dateText.setFocus();
					}
				}
			}

		});
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	@Override
	public void setObjectValue(Object value) {
		objectValue = value;
		int intValue = Integer.decode(value + "");
		String showText = getShowText(intValue);
		dateText.setText(showText);
	}

	private String getShowText(int intValue) {
		if (labels != null) {
			List<String> checkedLabels = new ArrayList<String>();
			for (int i = 0; i < labels.length; i++) {
				int tempValue = 1 << i;
				if ((intValue & tempValue) != 0) {
					checkedLabels.add(labels[i]);
				}
			}
			return array2String(checkedLabels.toArray(new String[checkedLabels.size()]));
		}
		return "[]";
	}

	@Override
	public Object getObjectValue() {
		return objectValue;
	}

	@Override
	public void addListener(int arg0, Listener arg1) {
		if ((arg0 == SWT.FocusOut) || (arg0 == SWT.Selection)) {
			dateText.addListener(arg0, arg1);
		} else {
			super.addListener(arg0, arg1);
		}
	}

	private final String array2String(String[] strs) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		int index = 0;
		for (String string : strs) {
			sb.append(string);
			if (++index < strs.length) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public class ButtonGroupDialog {

		private BitListResult result;

		private Shell shell;

		private ButtonGroup swtcal;

		private Display display;

		public ButtonGroupDialog(Display display, int numColumns, int style) {
			this.display = display;

			shell = new Shell(display.getActiveShell(), SWT.NO_FOCUS | SWT.NO_TRIM);
			shell.setLayout(new GridLayout());
			shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

			swtcal = new ButtonGroup(shell, numColumns, true, style);

			final Composite composite = new Composite(shell, SWT.NONE);
			composite.setBackground(SWTResourceManager.getColor(255, 255, 255));
			final GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			composite.setLayout(gridLayout);

			Button btnOK = new Button(composite, SWT.NONE);
			btnOK.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent arg0) {
					Object objectValue = swtcal.getObjectValue();
					String[] checkedLabels = swtcal.getCheckedLabels();
					result = new BitListResult(checkedLabels, objectValue);
					shell.close();
				}
			});
			btnOK.setText("确  定 (&O)");

			final Button btnCancel = new Button(composite, SWT.NONE);
			btnCancel.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent arg0) {
					result = null;
					shell.close();
				}
			});
			btnCancel.setText("取  消 (&X)");

		}

		/**
		 * @param objectValue
		 * @see com.insigma.commons.ui.widgets.ButtonGroup#setObjectValue(java.lang.Object)
		 */
		public void setObjectValue(Object objectValue) {
			swtcal.setObjectValue(objectValue);
		}

		public void setLabels(String[] labels) {
			swtcal.setLabels(labels);
		}

		public BitListResult open() {
			shell.pack();
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			return result;
		}

		public void setLocation(Point point) {
			shell.pack();
			Point size = shell.getSize();
			point.x = point.x - size.x;
			shell.setLocation(point);
		}

		public Point getSize() {
			return shell.getSize();
		}

		public void close() {
			shell.close();
		}

		public final class BitListResult {

			String[] checkedLabels;

			Object value;

			/**
			 * @param checkedLabels
			 * @param value
			 */
			public BitListResult(String[] checkedLabels, Object value) {
				this.checkedLabels = checkedLabels;
				this.value = value;
			}

		}
	}

}
