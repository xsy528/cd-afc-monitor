/**
 * 
 */
package com.insigma.commons.editorframework;

import com.insigma.commons.ui.MessageForm;

/**
 * @author DingLuofeng
 *
 */
public class ActionCallbackAdapter<Result> implements IActionCallback<Result> {

	@Override
	public void error(Exception e) {
		MessageForm.Message(e);
	}

	@Override
	public void callback(Result result) {
	}

}
