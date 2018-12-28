package com.insigma.commons.op;

import java.io.PrintStream;

public class NotFindException extends Exception {
	private static final long serialVersionUID = 1L;

	protected Exception exception;

	protected boolean fatal;

	public NotFindException() {
	}

	public NotFindException(String message) {
		super(message);
	}

	public NotFindException(Exception e) {
		this(e, e.getMessage());
	}

	public NotFindException(Exception e, String message) {
		super(message);
		exception = e;
	}

	public NotFindException(Exception e, String message, boolean fatal) {
		this(e, message);
		setFatal(fatal);
	}

	public boolean isFatal() {
		return fatal;
	}

	public void setFatal(boolean fatal) {
		this.fatal = fatal;
	}

	public void printStackTrace() {
		super.printStackTrace();
		if (exception != null)
			exception.printStackTrace();
	}

	public void printStackTrace(PrintStream printStream) {
		super.printStackTrace(printStream);
		if (exception != null)
			exception.printStackTrace(printStream);
	}

	public String toString() {
		if (exception != null)
			return super.toString() + " wraps: [" + exception.toString() + "]";
		else
			return super.toString();
	}
}
