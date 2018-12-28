/**
 *
 */
package com.insigma.commons.ui.style;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Column")
public class Column {

	@XStreamAsAttribute
	private String text = "";

	@XStreamAsAttribute
	private Integer index;

	@XStreamAsAttribute
	private String format;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
