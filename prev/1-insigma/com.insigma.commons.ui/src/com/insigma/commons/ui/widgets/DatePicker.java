/* 
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.insigma.commons.ui.calendar.SWTCalendarDialog;
import com.insigma.commons.util.lang.DateTimeUtil;
import com.swtdesigner.SWTResourceManager;

public class DatePicker extends EnhanceComposite implements IInputControl {

	private Text dateText;

	private String pattern = "yyyy-MM-dd";

	private SimpleDateFormat formatter;

	private SWTCalendarDialog cal = new SWTCalendarDialog(Display.getDefault());

	private Date defaultDate = new Date();

	public DatePicker(Composite parent, int style) {

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

		setObjectValue(new Date());
		Button bntselect = new Button(this, SWT.DOWN | SWT.ARROW | SWT.FLAT);
		bntselect.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		bntselect.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (arg0.widget instanceof Button) {
					try {
						cal = new SWTCalendarDialog(Display.getDefault());
						formatter = new SimpleDateFormat(pattern);
						Date d = formatter.parse(dateText.getText());
						cal.setDate(d);
					} catch (ParseException pe) {
					}
					Point pos = dateText.toDisplay(0, 0);
					pos.y = pos.y + dateText.getBounds().height;
					pos.y = pos.y - 2;
					// pos.x = pos.x - 2;
					pos.x = pos.x - 60;
					cal.setLocation(pos);

					Date newdate = cal.open();
					if (newdate != null) {
						setObjectValue(newdate);
						dateText.setFocus();
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
	}

	public Object getObjectValue() {
		Date dat = new Date();
		if (!dateText.getText().equals("")) {
			dat = DateTimeUtil.parseStringToDate(dateText.getText(), "yyyy-MM-dd");
		} else {
			dat = null;
		}
		return dat;
	}

	public Date getDateTime() {
		return (Date) getObjectValue();
	}

	public void setObjectValue(Object objectValue) {
		if (objectValue instanceof Date) {
			formatter = new SimpleDateFormat(pattern);
			dateText.setText(formatter.format((Date) objectValue));
		}
	}

	public Date getDefaultDate() {
		return defaultDate;
	}

	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
		setObjectValue(defaultDate);
	}

	//20150820 yjc设置文本框的可用状态
	public void setTextEnabled(boolean enable) {
		dateText.setEnabled(enable);
	}
}
