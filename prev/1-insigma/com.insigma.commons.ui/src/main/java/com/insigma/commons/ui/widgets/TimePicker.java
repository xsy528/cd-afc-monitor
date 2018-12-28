/*
 * 项目:    用户界面组件
 * 版本: 	2.0
 * 日期: 	2010-6-25
 * 作者:    姜旭锋
 * Email:   jiangxufeng@zdwxjd.com
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.widgets;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;

import com.swtdesigner.SWTResourceManager;

public class TimePicker extends EnhanceComposite implements IInputControl {

	public static final int HOUR = 0x01;

	public static final int MINITE = 0x04;

	public static final int SECOND = 0x08;

	private Spinner hour = null;

	private CLabel cLabel = null;

	private Spinner minites = null;

	private CLabel cLabel1 = null;

	private Spinner second = null;

	private CLabel cLabel2 = null;

	private int listMode = HOUR | MINITE | SECOND;

	private Date defaultValue;

	private int timePeriod = 1;

	public TimePicker(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.numColumns = 6;
		{
			if ((listMode & HOUR) == HOUR) {
				hour = new Spinner(this, SWT.BORDER);
				hour.setBackground(SWTResourceManager.getColor(255, 255, 255));
				hour.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent arg0) {
						if (minites.getSelection() == 0) {
							if (hour.getSelection() > 24) {
								hour.setSelection(24);
							}
						} else if (hour.getSelection() > 23) {
							hour.setSelection(23);
						}
					}
				});
				hour.setMaximum(24);
				cLabel = new CLabel(this, SWT.NONE);
				cLabel.setText("时 ");
			}
		}
		{
			if ((listMode & MINITE) == MINITE) {
				minites = new Spinner(this, SWT.BORDER);
				minites.setBackground(SWTResourceManager.getColor(255, 255, 255));
				minites.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent arg0) {
						if (minites.getSelection() > 59) {
							minites.setSelection(59);
						}
					}
				});
				minites.setMaximum(59);
				minites.setIncrement(timePeriod);
				cLabel1 = new CLabel(this, SWT.NONE);
				cLabel1.setText("分 ");
			}
		}
		{
			if ((listMode & SECOND) == SECOND) {
				second = new Spinner(this, SWT.BORDER);
				second.setBackground(SWTResourceManager.getColor(255, 255, 255));
				second.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent arg0) {
						if (second.getSelection() > 59) {
							second.setSelection(59);
						}
					}
				});
				cLabel2 = new CLabel(this, SWT.NONE);
				cLabel2.setText("秒 ");
			}
		}
		setObjectValue(new Date());
		setLayout(gridLayout);
	}

	public void addListener(int arg0, Listener arg1) {
		if (arg0 == SWT.FocusOut) {
			hour.addListener(arg0, arg1);
			minites.addListener(arg0, arg1);
			second.addListener(arg0, arg1);
		} else {
			super.addListener(arg0, arg1);
		}
	}

	public void reset() {
		if (defaultValue == null) {
			setObjectValue(new Date());
		} else {
			setObjectValue(defaultValue);
		}
	}

	public Object getObjectValue() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hour == null ? 0 : hour.getSelection());
		c.set(Calendar.MINUTE, minites == null ? 0 : minites.getSelection());
		c.set(Calendar.SECOND, second == null ? 0 : second.getSelection());
		return c.getTime();

	}

	public void setObjectValue(Object objectValue) {
		if (objectValue instanceof Date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date) objectValue);
			if (hour != null) {
				this.hour.setSelection(calendar.get(Calendar.HOUR_OF_DAY));
			}
			if (minites != null) {
				this.minites.setSelection(calendar.get(Calendar.MINUTE));
			}
			if (second != null) {
				this.second.setSelection(calendar.get(Calendar.SECOND));
			}
		}
	}

	public int getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
		Calendar calendar = Calendar.getInstance();
		if (minites != null) {
			minites.setMaximum(60 - timePeriod);
			minites.setIncrement(timePeriod);
			int minite = calendar.get(Calendar.MINUTE);
			this.minites.setSelection(minite - minite % timePeriod);
		}
	}

	public Date getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Date defaultValue) {
		this.defaultValue = defaultValue;
	}

}
