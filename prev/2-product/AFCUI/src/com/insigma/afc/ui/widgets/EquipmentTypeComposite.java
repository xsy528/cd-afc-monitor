package com.insigma.afc.ui.widgets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.ui.widgets.EnhanceComposite;
import com.insigma.commons.ui.widgets.IInputControl;
import com.swtdesigner.SWTResourceManager;

public class EquipmentTypeComposite extends EnhanceComposite implements IInputControl {

	private Button[] btnEquType;

	private Integer[] equType;

	private int selectedNum = 0;

	private Button chkAllEqu;

	public EquipmentTypeComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout());

		chkAllEqu = new Button(this, SWT.CHECK);
		chkAllEqu.setForeground(SWTResourceManager.getColor(255, 0, 0));
		chkAllEqu.setText("全部设备");

		chkAllEqu.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (chkAllEqu.getSelection()) {
					selectAll();
				} else {
					for (Button value : btnEquType) {
						value.setSelection(false);
					}
					selectedNum = 0;
				}
			}
		});

	}

	public void selectAll() {
		chkAllEqu.setSelection(true);
		for (Button value : btnEquType) {
			value.setSelection(true);
		}
		selectedNum = equType.length;
	}

	class EquSelectionAdapter extends SelectionAdapter {
		public void widgetSelected(SelectionEvent arg0) {
			if (((Button) arg0.getSource()).getSelection()) {
				selectedNum++;
				if (selectedNum == equType.length) {
					chkAllEqu.setSelection(true);
				}
			} else {
				selectedNum--;
				chkAllEqu.setSelection(false);
			}
		}
	}

	public void setSelectedEqu(Short[] equipment) {
		selectedNum = equipment.length;
		if (selectedNum == equType.length) {
			chkAllEqu.setSelection(true);
		} else {
			chkAllEqu.setSelection(false);
		}

		for (Button value : btnEquType) {
			value.setSelection(false);
		}
		for (Short equ : equipment) {
			int i = 0;
			for (Integer stationNum : this.equType) {
				if (stationNum.shortValue() == equ) {
					btnEquType[i].setSelection(true);
					break;
				}
				i++;
			}
		}
	}

	public Short[] getEquipmentList() {
		int index = 0;
		ArrayList<Short> selectedEquTypeList = new ArrayList<Short>();
		for (Button value : btnEquType) {
			if (value.getSelection()) {
				selectedEquTypeList.add(equType[index].shortValue());
			}
			index++;
		}
		if (selectedEquTypeList.size() > 0) {
			Short[] equType = new Short[selectedEquTypeList.size()];
			selectedEquTypeList.toArray(equType);
			return equType;
		} else {
			return null;
		}
	}

	public void setEquType(Integer[] equType) {
		this.equType = equType;
	}

	public void setEquTypeName(String[] equTypeName) {
		btnEquType = new Button[equTypeName.length];
		for (int i = 0; i < equTypeName.length; i++) {
			final Button btnGTX = new Button(this, SWT.CHECK);
			btnGTX.addSelectionListener(new EquSelectionAdapter());
			btnGTX.setText(equTypeName[i]);
			btnEquType[i] = btnGTX;
		}
	}

	public Object getObjectValue() {
		return getEquipmentList();
	}

	public void setObjectValue(Object value) {

	}
}
