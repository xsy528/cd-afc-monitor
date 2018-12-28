package com.insigma.commons.framework.function.dialog;

public class FileDialogFunction extends DialogFunction {

	public enum FileDialogType {
		OPEN_DIR, OPEN_FILE, SAVE_DIR, SAVE_FILE
	}

	protected String[] filter;

	protected FileDialogType dialogType = FileDialogType.OPEN_DIR;

	public FileDialogType getDialogType() {
		return dialogType;
	}

	public void setDialogType(FileDialogType dialogType) {
		this.dialogType = dialogType;
	}

	public String[] getFilter() {
		return filter;
	}

	public void setFilter(String[] filter) {
		this.filter = filter;
	}

}
