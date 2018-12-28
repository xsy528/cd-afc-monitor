package com.insigma.commons.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public abstract class CustomCombo extends Composite {

	protected Button button;

	protected Text text;

	protected Shell popup;

	protected boolean focused;

	protected boolean popupfocused;

	protected Control control;

	public abstract Control createControl(Shell shell);

	public CustomCombo(Composite parent, int style) {
		super(parent, style | SWT.BORDER);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;

		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;

		layout.numColumns = 2;
		setLayout(layout);

		text = new Text(this, SWT.NONE);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));

		button = new Button(this, SWT.ARROW);
		button.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toggleHint();
			}
		});

		popup = new Shell(SWT.ON_TOP);
		popup.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		popup.setVisible(false);
		popup.setLayout(new FillLayout());

		control = createControl(popup);

		addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent e) {
				popup.setVisible(false);
			}

			public void controlResized(ControlEvent e) {
				popup.setVisible(false);
			}
		});

		text.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				focused = true;
				showHint();
			}
		});

		Display.getDefault().addFilter(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				for (Control control : popup.getChildren()) {
					if (control instanceof Composite) {
						if (check((Composite) control, event.widget)) {
							return;
						}
					}
					if (control == event.widget) {
						return;
					}
				}
				if (event.widget != text && event.widget != button) {
					hideHint();
				}
			}
		});
	}

	protected boolean check(Composite parent, Widget widget) {
		for (Control control : parent.getChildren()) {
			if (control == widget) {
				return true;
			}
			if (control instanceof Composite) {
				if (check((Composite) control, widget)) {
					return true;
				}

			}
		}
		return false;
	}

	@Override
	protected void checkSubclass() {

	}

	public void showHint() {
		setPos();
		popup.setVisible(true);
	}

	public void toggleHint() {
		setPos();
		popup.setVisible(!popup.getVisible());
	}

	public void hideHint() {
		popup.setVisible(false);
	}

	public Point getDropSize() {
		return new Point(getSize().x, 200);
	}

	protected void setPos() {
		Shell parentShell = getShell();
		int titlebar_x = (parentShell.getSize().x - parentShell.getClientArea().width) / 2;
		int titlebar_y = parentShell.getSize().y - parentShell.getClientArea().height;
		popup.setSize(getDropSize());
		Point pos = new Point(0, 0);
		Composite cmp = getParent();
		while (cmp != null) {
			pos.x += cmp.getLocation().x;
			pos.y += cmp.getLocation().y;
			cmp = cmp.getParent();
		}
		pos.x = pos.x + getLocation().x + titlebar_x;
		pos.y = pos.y + getLocation().y + titlebar_y + getSize().y;
		popup.setLocation(pos);
	}
}
