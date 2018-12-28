/**
 * 
 */
package com.insigma.commons.ui.casecade;

import com.insigma.commons.ui.widgets.IInputControl;

/**
 * @author DingLuofeng
 */
public interface ICasecadeControl extends IInputControl {

	void addCasecadeListener(CasecadeListener casecadeListener);

	void addCasecadeControl(ICasecadeControl casecadeControl);

	void valueChanged(CasecadeEvent casecadeEvent);

	void setFieldName(String fieldName);

}
