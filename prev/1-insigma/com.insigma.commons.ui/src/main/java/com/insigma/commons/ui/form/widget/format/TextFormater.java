/**
 * 
 */
package com.insigma.commons.ui.form.widget.format;

/**
 * @author DingLuofeng
 *
 */
public class TextFormater implements ITextFormater {

	private String formatString;

	private String nullValue = "";

	public TextFormater(String formatString) {
		this.formatString = formatString;
	}

	@Override
	public String format(Object value) {
		try {
			return String.format(formatString, value);
		} catch (Exception e) {
			return value == null ? nullValue : value.toString();
		}
	}

	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
	}

	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}

}
