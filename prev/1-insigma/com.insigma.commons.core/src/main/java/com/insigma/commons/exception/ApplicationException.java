package com.insigma.commons.exception;

/**
 * <p>
 * Title: insigma信息管理系统
 * </p>
 * <p>
 * Description: 应用程序异常类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: insigma软件
 * </p>
 * 
 * @author jensol
 * @version 1.0
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	protected Throwable myException;

	protected int errorCode = 0;

	protected String Msg = "";

	/**
	 * 默认的构造方法
	 * 
	 */
	public ApplicationException() {
		// ...
	}

	/**
	 * 带一个参数String参数的构造方法
	 * 
	 * @param msg
	 */
	public ApplicationException(String msg) {
		super(msg);
	}

	/**
	 * 带一个String和一个Throwable参数的构造方法
	 * 
	 * @param msg
	 * @param cause
	 */
	public ApplicationException(String msg, Throwable cause) {
		super(msg, cause);

		this.myException = cause;
	}

	/**
	 * 带一个Throwable的构造方法
	 * 
	 * @param cause
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(int errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public ApplicationException(int errorCode, String msg, Throwable e) {
		super(msg);
		this.errorCode = errorCode;
		this.myException = e;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getMessage() {
		if (myException != null) {
			Msg = super.getMessage() + myException.getMessage();
		} else {
			Msg = super.getMessage();
		}
		return Msg;
	}

	public String getMessageDetail() {
		String errorInfo = "";

		if (getErrorCode() != 0) {
			errorInfo += "错误代码：" + getErrorCode() + "\n";
		}

		if (myException != null) {
			errorInfo += "错误描述：" + super.getMessage() + "\n详细信息：" + myException.getMessage();
		} else {
			errorInfo += "错误描述：" + super.getMessage();
		}
		return errorInfo;
	}

	public void printStackTrace() {
		if (myException != null)
			myException.printStackTrace();
	}
}