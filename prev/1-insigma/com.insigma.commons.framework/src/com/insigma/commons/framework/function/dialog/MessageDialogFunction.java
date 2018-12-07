package com.insigma.commons.framework.function.dialog;

public class MessageDialogFunction extends DialogFunction {

	private String title;

	private String description;

	public MessageDialogFunction(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
