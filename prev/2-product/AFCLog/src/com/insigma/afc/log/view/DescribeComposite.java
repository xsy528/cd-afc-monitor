package com.insigma.afc.log.view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.insigma.afc.log.result.LogResult;
import com.insigma.commons.framework.IResponseDisplayer;
import com.insigma.commons.framework.Response;

public class DescribeComposite extends Composite implements IResponseDisplayer {

	private Text text;

	public DescribeComposite(Composite arg0, int arg1) {
		super(arg0, arg1);

		GridLayout layout = new GridLayout();
		this.setLayout(layout);

		text = new Text(this, SWT.BORDER | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	@SuppressWarnings("unchecked")
	public void setResponse(Response response) {
		List<Object> resultList = (List<Object>) response.getValue();
		if (resultList != null) {
			LogResult result = (LogResult) resultList.get(0);
			text.append("IP地址：" + result.getIpAddress() + "\n");
			text.append("模块：" + result.getModuleType() + "\n");
			text.append("时间：" + result.getOccurTime() + "\n");
			text.append("描述：" + result.getLogDesc());
		}
	}

	public void reset() {

	}

	public void refresh() {

	}

}
