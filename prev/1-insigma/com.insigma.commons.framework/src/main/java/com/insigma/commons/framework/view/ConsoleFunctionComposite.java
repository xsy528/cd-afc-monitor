package com.insigma.commons.framework.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.ConsoleFunction;

public class ConsoleFunctionComposite extends FunctionComposite {

	private StyledText text;

	public ConsoleFunctionComposite(Composite parent, int style) {
		super(parent, style);
	}

	public Request getRequest() {
		return null;
	}

	public void setResponse(Response response) {
		text.setText(response.getValue().toString());
	}

	public void setFunction(ConsoleFunction function) {
		GridLayout _gridLayout = new GridLayout();
		_gridLayout.marginWidth = 0;
		_gridLayout.marginHeight = 0;
		this.setLayout(_gridLayout);
		if (function.getActions() != null && function.getActions().size() > 0) {
			Composite buttonbar = new Composite(this, SWT.NONE);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = function.getActions().size();
			gridLayout.marginWidth = 0;
			gridLayout.marginHeight = 0;
			buttonbar.setLayout(gridLayout);
			buttonbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			buildButtonBar(buttonbar, function.getActions());
		}
		text = new StyledText(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

}
