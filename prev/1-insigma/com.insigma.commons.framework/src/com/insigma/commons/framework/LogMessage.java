/**
 * iFrameWork 框架
 * 
 * @author        Jiangxufeng
 * @email         jiangxufeng@zdwxjd.com
 * @version       1.0
 * @description   
 * @date          2009-4-2
 * @copyright     浙江浙大网新众合轨道交通工程有限公司 
 * @history       
 */
package com.insigma.commons.framework;

public class LogMessage {

	public enum LogLevel {
		INFO, WARN, ERROR
	}

	private LogLevel level = LogLevel.INFO;

	private String message;

	private Exception e;

	public LogMessage(String message) {
		this.message = message;
	}

	public LogMessage(LogLevel type, String message) {
		this.message = message;
		this.level = type;
	}

	public LogMessage(String message, Exception e) {
		this.message = message;
		this.e = e;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return e;
	}

	public void setException(Exception e) {
		this.e = e;
	}

	public LogLevel getLevel() {
		return level;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}
}
