/**
 * 
 */
package com.insigma.commons.ui.casecade;

import java.lang.reflect.Field;

import com.insigma.commons.ui.widgets.IInputControl;

/**
 * @author DingLuofeng
 */
public class CasecadeEvent {
	/**
	 * changedControl 值发生变化的控件，即被监控的控件；
	 */
	public IInputControl changedControl;

	public Field changedField;

	public String changedFieldName;

	/**
	 * listenerControl 需要级联变化的控件，即监听的控件
	 */
	public IInputControl listenerControl;

	public Field listenerField;

	/**
	 * @param changedControl
	 * @param listenerControl
	 */
	public CasecadeEvent(IInputControl changedControl, IInputControl listenerControl) {
		this.changedControl = changedControl;
		this.listenerControl = listenerControl;
	}

	/**
	 * @param changedControl
	 * @param listenerControl
	 */
	public CasecadeEvent(String changedFieldName, IInputControl changedControl, IInputControl listenerControl) {
		this.changedFieldName = changedFieldName;
		this.changedControl = changedControl;
		this.listenerControl = listenerControl;
	}

	/**
	 * @param changedControl
	 * @param changedField
	 * @param listenerControl
	 * @param listenerField
	 */
	public CasecadeEvent(IInputControl changedControl, Field changedField, IInputControl listenerControl,
			Field listenerField) {
		this.changedControl = changedControl;
		this.changedField = changedField;
		this.listenerControl = listenerControl;
		this.listenerField = listenerField;
	}

}
