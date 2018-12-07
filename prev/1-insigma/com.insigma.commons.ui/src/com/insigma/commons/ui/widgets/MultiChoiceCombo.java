/* 
 * 日期：2018-8-24
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.ui.widgets;

import java.util.ArrayList;
import java.util.Date;
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
import org.eclipse.swt.widgets.Text;

import com.insigma.commons.ui.dialog.MultiChoiceDialog;
import com.insigma.commons.ui.tree.ITreeProvider;
import com.swtdesigner.SWTResourceManager;

/**
 * Ticket:下拉多选框
 * 
 * @author chenhangwen
 */
public class MultiChoiceCombo extends EnhanceComposite implements IInputControl {

	private Text dateText;

	private MultiChoiceDialog cal;

	private Date defaultDate = new Date();

	private ITreeProvider treeProvider;

	public MultiChoiceCombo(Composite parent, int style) {

		super(parent, SWT.BORDER);

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
		dateText = new Text(this, SWT.NONE);
		dateText.setBackground(SWTResourceManager.getColor(255, 255, 255));
		dateText.setVisible(true);
		dateText.setEditable(false);
		dateText.setLayoutData(griddata);

		cal = new MultiChoiceDialog(Display.getDefault(), treeProvider == null ? null : treeProvider.getTree());

		Button bntselect = new Button(this, SWT.DOWN | SWT.ARROW | SWT.FLAT);
		bntselect.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		bntselect.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (arg0.widget instanceof Button) {

					cal = new MultiChoiceDialog(Display.getDefault(),
							treeProvider == null ? null : treeProvider.getTree());
					cal.setTreeItemsChecked(dateText.getData());

					String valueinit = "";
					for (int i = 0; i < cal.getSwtTree().getItem(0).getItemCount(); i++) {
						if (cal.getSwtTree().getItem(0).getItem(i).getChecked()) {
							valueinit = valueinit + "[" + cal.getSwtTree().getItem(0).getItem(i).getText() + "]";
						}
					}
					dateText.setText(valueinit);

					Point pos = dateText.toDisplay(0, 0);
					pos.y = pos.y + dateText.getBounds().height;
					pos.y = pos.y - 2;
					// pos.x = pos.x - 2;
					pos.x = pos.x;
					cal.setLocation(pos);

					List<String[]> dataList = cal.open();
					if (dataList != null) {
						if (dataList.size() > 0) {
							String value = changeListToString(dataList);
							dateText.setText(value);
						} else {
							dateText.setText("");
						}
						dateText.setFocus();
						dateText.setData(dataList);
					}

				}
			}
		});
	}

	public void addListener(int arg0, Listener arg1) {
		if (arg0 == SWT.FocusOut) {
			dateText.addListener(arg0, arg1);
		} else {
			super.addListener(arg0, arg1);
		}
	}

	public void reset() {
		setDefaultDate(defaultDate);
		dateText.setText("");
		dateText.setData(new ArrayList<String[]>());

	}

	public Object getObjectValue() {
		return dateText.getData();
	}

	public void setObjectValue(Object objectValue) {
		if (objectValue instanceof List) {
			dateText.setData(objectValue);
		}
	}

	public Date getDefaultDate() {
		return defaultDate;
	}

	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
		setObjectValue(defaultDate);
	}

	public boolean isEnabled() {
		return false;
	}

	public void setTreeProvider(ITreeProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	private String changeListToString(List<String[]> dataList) {
		StringBuffer value = new StringBuffer();
		for (String[] data : dataList) {
			if (null != data && data.length > 0) {
				value.append("[");
				boolean add = false;
				for (String temp : data) {
					if (add) {
						value.append(",");
					} else {
						add = true;
					}
					value.append(temp);
				}

				value.append("]");
			}
		}
		return value.toString();
	}
}
