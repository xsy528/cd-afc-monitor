package com.insigma.commons.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.insigma.commons.ui.casecade.CasecadeEvent;
import com.insigma.commons.ui.casecade.CasecadeListener;
import com.insigma.commons.ui.casecade.ICasecadeControl;

/**
 * Ticket:下拉多选框
 * 
 * @author yangy
 * 
 */
public class MultipleSelectionCombo extends Composite implements IInputControl, ICasecadeControl {

	private Label _text = null;
	private String[] _items = null;
	private Object[] _values = null;
	private int[] _selection = null;
	private Shell _floatShell = null;
	private List _list = null;

	private ICasecadeControl casecadeControl;

	private String fieldName = "MultipleSelectionCombo";

	public MultipleSelectionCombo(Composite parent, String[] _items, Object[] _values, int style) {
		super(parent, style);
		this._items = _items;
		this._values = _values;
		init();
	}

	private void init() {
		GridLayout layout = new GridLayout();
		layout.marginBottom = 100;
		layout.marginTop = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(new GridLayout());
		_text = new Label(this, SWT.BORDER);

		_text.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		_text.setLayoutData(new GridData(GridData.FILL_BOTH));

		//增加监听，显示 shell 和 list
		_text.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				super.mouseDown(event);
				initFloatShell(event);
			}

		});
		_text.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseEnter(MouseEvent arg0) {
				_text.setToolTipText(_text.getText());
			}
		});
	}

	private void initFloatShell(MouseEvent e) {
		Point p = _text.getParent().toDisplay(_text.getLocation());
		Point size = _text.getSize();
		Rectangle shellRect = new Rectangle(p.x, p.y + size.y, size.x, 0);
		_floatShell = new Shell(MultipleSelectionCombo.this.getShell(), SWT.NO_TRIM | SWT.BORDER);

		GridLayout gl = new GridLayout();
		gl.marginBottom = 2;
		gl.marginTop = 2;
		gl.marginRight = 2;
		gl.marginLeft = 2;
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		_floatShell.setLayout(gl);

		_list = new List(_floatShell, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		for (String value : _items) {
			_list.add(value);
		}

		GridData gd = new GridData(GridData.FILL_BOTH);
		_list.setLayoutData(gd);
		_floatShell.setSize(shellRect.width, 100);
		_floatShell.setLocation(shellRect.x, shellRect.y);

		//实现list上的ctrl/shift多选
		_list.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent event) {
				super.mouseUp(event);
				_selection = _list.getSelectionIndices();
				if ((event.stateMask & SWT.CTRL) == 0 && (event.stateMask & SWT.SHIFT) == 0) {
					_floatShell.dispose();
					displayText(null);
					if (casecadeControl != null) {
						CasecadeEvent casecadeEvent = new CasecadeEvent(fieldName, MultipleSelectionCombo.this,
								casecadeControl);
						casecadeControl.valueChanged(casecadeEvent);
					}
				}
			}
		});

		// 指定shell的消失行为
		_floatShell.addShellListener(new ShellAdapter() {
			public void shellDeactivated(ShellEvent arg0) {
				if (_floatShell != null && !_floatShell.isDisposed()) {
					_selection = _list.getSelectionIndices();
					displayText(null);
					if (casecadeControl != null) {
						CasecadeEvent casecadeEvent = new CasecadeEvent(fieldName, MultipleSelectionCombo.this,
								casecadeControl);
						casecadeControl.valueChanged(casecadeEvent);
					}
					_floatShell.dispose();
				}
			}
		});
		_floatShell.open();
	}

	private void displayText(Integer index) {

		if (index == null) {
			if (_selection != null && _selection.length > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < _selection.length; i++) {
					if (i > 0)
						sb.append(",");
					sb.append(_items[_selection[i]]);
				}
				_text.setText(sb.toString());
			} else {
				_text.setText("");
			}
		} else {
			if (index > -1 && index < _items.length) {
				_text.setText(_items[index]);
			} else {
				_text.setText("");
			}
		}
	}

	public void setEnable(boolean enable) {
		this._text.setEnabled(enable);
	}

	public boolean isEnable() {
		return this._text.isEnabled();
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.widgets.IControl#reset()
	 */
	@Override
	public void reset() {
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.widgets.IInputControl#setObjectValue(java.lang.Object)
	 */
	@Override
	public void setObjectValue(Object value) {
		if (value != null) {
			if (_values != null) {
				displayText(getIndex(value));
			} else {
				displayText(Integer.valueOf(value.toString()));
			}
		}
	}

	private int getIndex(Object val) {
		for (int i = 0; i < _values.length; i++) {
			if (_values[i].toString().equals(String.valueOf(val)) || _values[i].equals(val)) {
				return i;
			}
		}
		return -1;
	}

	public String getCheckedLabels() {
		if (!this.isEnable()) {
			return "";
		}
		return this._text.getText();
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.widgets.IInputControl#getObjectValue()
	 */
	@Override
	public Object getObjectValue() {
		if (!this.isEnable()) {
			return null;
		}
		if (_selection != null) {
			Object[] result = new Object[_selection.length];
			for (int i = 0; i < _selection.length; i++) {
				result[i] = _values[_selection[i]];
			}
			return result;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.casecade.ICasecadeControl#addCasecadeListener(com.insigma.commons.ui.casecade.CasecadeListener)
	 */
	@Override
	public void addCasecadeListener(CasecadeListener casecadeListener) {
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.casecade.ICasecadeControl#addCasecadeControl(com.insigma.commons.ui.casecade.ICasecadeControl)
	 */
	@Override
	public void addCasecadeControl(ICasecadeControl casecadeControl) {
		this.casecadeControl = casecadeControl;
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.casecade.ICasecadeControl#valueChanged(com.insigma.commons.ui.casecade.CasecadeEvent)
	 */
	@Override
	public void valueChanged(CasecadeEvent casecadeEvent) {
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.ui.casecade.ICasecadeControl#setFieldName(java.lang.String)
	 */
	@Override
	public void setFieldName(String fieldName) {
	}

}