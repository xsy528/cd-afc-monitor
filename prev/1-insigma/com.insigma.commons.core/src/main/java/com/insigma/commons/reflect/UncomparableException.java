/* 
 * 日期：Nov 30, 2007
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.reflect;

@SuppressWarnings("serial")
public class UncomparableException extends RuntimeException {

	public UncomparableException() {
		super("This object is not a Comparable");
	}

	public UncomparableException(String message, Throwable cause) {
		super(message, cause);
	}

	public UncomparableException(String message) {
		super(message);
	}

	public UncomparableException(Throwable cause) {
		super(cause);
	}

}
