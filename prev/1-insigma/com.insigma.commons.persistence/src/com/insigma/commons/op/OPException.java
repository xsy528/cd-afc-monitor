package com.insigma.commons.op;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OPException extends Exception {
	private static final long serialVersionUID = 1L;

	private Exception exception;

	protected boolean fatal;

	public OPException() {
	}

	public OPException(String message) {
		super(message);
	}

	public OPException(Exception e) {
		this(e, e.getMessage());
	}

	public OPException(Exception e, String message) {
		super(message);
		setException(e);
	}

	public OPException(Exception e, String message, boolean fatal) {
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
		if (getException() != null)
			getException().printStackTrace();
	}

	public void printStackTrace(PrintStream printStream) {
		super.printStackTrace(printStream);
		if (getException() != null)
			getException().printStackTrace(printStream);
	}

	public String toString() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream s = new PrintStream(out);
		exception.printStackTrace(s);
		if (getException() != null)
			return super.toString() + " wraps: [" + out.toString() + "]";
		else
			return super.toString();
	}

	/**
	 * @param exception the exception to set
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}

	/**
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}
}
