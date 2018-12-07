package com.insigma.commons.framework.view.status;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.application.Application;
import com.insigma.commons.framework.application.StatusPage;
import com.swtdesigner.SWTResourceManager;

/**
 * 创建时间 2010-12-30 下午02:24:54 <br>
 * 描述: <br>
 * Ticket：
 * 
 * @author wangxinhao<wangxinhao@zdwxjd.com>
 */
public class TextStatusPage extends StatusPage {

	private String[] statusDescS;

	private Map<String, Object> modeNameMap;

	public TextStatusPage() {
		this.composite = CLabel.class;
	}

	public String[] getStatusDesc() {
		return statusDescS;
	}

	public void setStatusDesc(String[] statusDesc) {
		this.statusDescS = statusDesc;
	}

	///yangyang
	public void setStatusDesc(Map<String, Object> modeNameMap) {
		this.modeNameMap = modeNameMap;
	}

	private boolean enable = true;

	@Override
	public void destroy() {
		enable = false;
	}

	@Override
	public void update() {

		if (key != null) {
			Object value = Application.getData(key);
			if (value == null) {
				value = 0;
				Application.setData(key, value);
			}

			if (value instanceof Long) {
				boolean s = Modeupdate(value);
				if (!s) {
					return;
				}

			} else {
				String tempStatusDesc = null;
				int newstatus = Integer.valueOf(String.valueOf(value));
				if (status != null && newstatus == status) {
					return;
				}
				setStatus(newstatus);

				if (status == null) {
					status = 0;
				}

				if (statusDescS != null) {
					if (status < statusDescS.length) {
						tempStatusDesc = statusDescS[status];
					}
				}

				final String statusDesc = tempStatusDesc;
				if (getControl() instanceof CLabel && !getControl().isDisposed()) {
					if (statusDescS != null) {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								if (!enable) {
									return;
								}
								CLabel clabel = (CLabel) getControl();
								if (clabel == null || clabel.isDisposed()) {
									return;
								}
								if (status != 0) {
									((CLabel) getControl())
											.setForeground(SWTResourceManager.getColor(new RGB(255, 0, 0)));
								} else {
									((CLabel) getControl())
											.setForeground(SWTResourceManager.getColor(new RGB(0, 0, 0)));
								}
								((CLabel) getControl()).setText(statusDesc);
								((CLabel) getControl()).setToolTipText(statusDesc);
								final GridData gdLblCommInfo = new GridData(SWT.LEFT, SWT.CENTER, false, true);
								((CLabel) getControl()).setLayoutData(gdLblCommInfo);
								getControl().getParent().layout();
							}
						});
					}
				}
			}
		}
	}

	public boolean Modeupdate(Object value) {

		long newstatus = Long.valueOf(String.valueOf(value));

		if (statuss != null && newstatus == statuss) {
			return false;
		}
		setStatuss(newstatus);
		if (statuss == null) {
			statuss = (long) 0;
		}

		String tempStatusDesc = "";
		if (modeNameMap != null) {
			if (statuss == 0) {
				tempStatusDesc = "正常运营模式";
			} else {
				long temp = statuss;
				while (true) {
					Iterator keyIteratorOfMap = modeNameMap.keySet().iterator();
					while (keyIteratorOfMap.hasNext()) {
						Object key = keyIteratorOfMap.next();
						if ((int) (temp % 100) == Integer.valueOf(modeNameMap.get(key).toString())) {
							tempStatusDesc = tempStatusDesc + key.toString();
						}
					}
					temp = temp / 100;
					if (temp == 0) {
						break;
					}
					tempStatusDesc = tempStatusDesc + "/";
				}

			}
		}
		final String statusDesc = tempStatusDesc;
		if (getControl() instanceof CLabel && !getControl().isDisposed()) {
			if (modeNameMap != null) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						if (!enable) {
							return;
						}
						CLabel clabel = (CLabel) getControl();
						if (clabel == null || clabel.isDisposed()) {
							return;
						}
						if (statuss != 0) {
							((CLabel) getControl()).setForeground(SWTResourceManager.getColor(new RGB(255, 0, 0)));
						} else {
							((CLabel) getControl()).setForeground(SWTResourceManager.getColor(new RGB(0, 0, 0)));
						}
						((CLabel) getControl()).setText(statusDesc);
						((CLabel) getControl()).setToolTipText(statusDesc);
						final GridData gdLblCommInfo = new GridData(SWT.LEFT, SWT.CENTER, false, true);
						((CLabel) getControl()).setLayoutData(gdLblCommInfo);
						getControl().getParent().layout();
					}
				});
			}
		}

		return true;
	}
}
