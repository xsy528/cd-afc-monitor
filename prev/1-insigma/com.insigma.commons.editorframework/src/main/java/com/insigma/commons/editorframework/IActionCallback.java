package com.insigma.commons.editorframework;

/**
 * 
 * @author DingLuofeng
 *
 */
public interface IActionCallback<Result> {

	void error(Exception e);

	void callback(Result result);

}
