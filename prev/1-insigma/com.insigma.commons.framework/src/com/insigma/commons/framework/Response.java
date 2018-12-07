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

import java.util.ArrayList;
import java.util.List;

import com.insigma.commons.database.DBConnectException;
import com.insigma.commons.framework.LogMessage.LogLevel;

public class Response {

	protected int code;

	protected List<ErrorMessage> errors;

	protected List<InformationMessage> messages;

	protected List<LogMessage> logs = new ArrayList<LogMessage>();

	protected Object value;

	public void reset() {
		errors = null;
		messages = null;
		code = 0;
		value = null;
	}

	public List<ErrorMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}

	public void addError(String message) {
		if (errors == null) {
			errors = new ArrayList<ErrorMessage>();
		}
		ErrorMessage error = new ErrorMessage(message);
		errors.add(error);
		LogMessage log = new LogMessage(LogLevel.ERROR, message);
		logs.add(log);
	}

	public void addError(String message, Exception e) {
		if (e instanceof DBConnectException) {
			message = "数据库链接异常，请检查数据库链接。";
		}
		if (errors == null) {
			errors = new ArrayList<ErrorMessage>();
		}
		ErrorMessage error = new ErrorMessage(message);
		errors.add(error);
		LogMessage log = new LogMessage(message, e);
		log.setLevel(LogLevel.ERROR);
		logs.add(log);
	}

	public void addInformation(String message) {
		if (messages == null) {
			messages = new ArrayList<InformationMessage>();
		}
		InformationMessage error = new InformationMessage(message);
		messages.add(error);
	}

	public List<InformationMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<InformationMessage> messages) {
		this.messages = messages;
	}

	public List<LogMessage> getLogs() {
		return logs;
	}

	public void setLogs(List<LogMessage> logs) {
		this.logs = logs;
	}

	public void addLog(String log) {
		logs.add(new LogMessage(log));
	}

	public void addLog(LogLevel type, String log) {
		logs.add(new LogMessage(type, log));
	}

	public void addLog(String log, Exception e) {
		logs.add(new LogMessage(log, e));
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
