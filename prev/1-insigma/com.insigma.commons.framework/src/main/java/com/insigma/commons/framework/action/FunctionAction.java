package com.insigma.commons.framework.action;

import com.insigma.commons.framework.Action;
import com.insigma.commons.framework.function.dialog.DialogFunction;

public class FunctionAction extends Action {

	protected DialogFunction dialogFunction;

	public void setHeight(int height) {
		dialogFunction.setHeight(height);
	}

	public void setImage(String image) {
		dialogFunction.setImage(image);
	}

	public void setTitle(String title) {
		dialogFunction.setTitle(title);
	}

	public void setWidth(int width) {
		dialogFunction.setWidth(width);
	}

	public FunctionAction() {
		super();
	}

	public FunctionAction(String text) {
		super(text);
	}

	public DialogFunction getDialogFunction() {
		return dialogFunction;
	}

	public void setDialogFunction(DialogFunction dialogFunction) {
		this.dialogFunction = dialogFunction;
		if (null != dialogFunction && null != this.getFunction() && dialogFunction.getModule() == null) {
			dialogFunction.setModule(this.getFunction().getModule());
		}
	}

}
