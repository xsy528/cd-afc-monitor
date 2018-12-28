/**
 * 
 */
package com.insigma.commons.ui.anotation.data;

import java.util.Map;

import org.eclipse.swt.SWT;

import com.insigma.commons.ui.anotation.View;

/**
 * @author DingLuofeng
 *
 */
public class ViewData extends AnnotationData {

	int index = -1;

	String group = "";

	String type = "Text";

	String label = "";

	String postfix = "";

	String description = "";

	String format = "";

	boolean checkable;

	boolean checked;

	String nullValue = "";

	int rowspan = 1;

	int columnspan = 1;

	String regexp = "";

	int length = 128;

	int heightHint = SWT.DEFAULT;

	boolean wrap;

	int style = SWT.DEFAULT;

	boolean modify = true;

	boolean editable = true;

	boolean hasMD5Format = false;

	long fileSize = -1;

	int formatLength = -1;

	boolean zero = true;
	int radix = 10;

	ViewData() {
	}

	public ViewData(Map<String, Object> ViewMap) {
		super(ViewMap);
		this.index = get("index", int.class, -1);
		this.group = get("group", String.class, "");
		this.type = get("type", String.class, "Text");
		this.label = get("label", String.class, "");
		this.postfix = get("postfix", String.class, "");
		this.description = get("description", String.class, "");
		this.format = get("format", String.class, "");
		this.checkable = get("checkable", Boolean.class, false);
		this.checked = get("checked", Boolean.class, false);
		this.nullValue = get("nullValue", String.class, "");
		this.rowspan = get("rowspan", int.class, 1);
		this.columnspan = get("columnspan", int.class, 1);
		this.regexp = get("regexp", String.class, "");
		this.length = get("length", int.class, 128);
		this.heightHint = get("heightHint", int.class, SWT.DEFAULT);
		this.wrap = get("wrap", Boolean.class, false);
		this.style = get("style", int.class, SWT.DEFAULT);
		this.modify = get("modify", Boolean.class, true);
		this.editable = get("editable", Boolean.class, true);
		this.hasMD5Format = get("hasMD5Format", Boolean.class, false);
		this.fileSize = get("fileSize", Long.class, -1L);
		this.formatLength = get("formatLength", int.class, -1);
		this.zero = get("zero", Boolean.class, true);
		this.radix = get("radix", int.class, 10);
	}

	public ViewData(View view) {
		this.index = view.index();
		this.group = view.group();
		this.type = view.type();
		this.label = view.label();
		this.postfix = view.postfix();
		this.description = view.description();
		this.format = view.format();
		this.checkable = view.checkable();
		this.checked = view.checked();
		this.nullValue = view.nullvalue();
		this.rowspan = view.rowspan();
		this.columnspan = view.coloumnspan();
		this.regexp = view.regexp();
		this.length = view.length();
		this.heightHint = view.heightHint();
		this.wrap = view.wrap();
		this.style = view.style();
		this.modify = view.modify();
		this.editable = view.editable();
		this.hasMD5Format = view.hasMD5Format();
		this.fileSize = view.fileSize();
		this.formatLength = view.formatLength();
		this.zero = view.zero();
		this.radix = view.radix();
	}

	public final int index() {
		return index;
	}

	public final String group() {
		return group;
	}

	public final String type() {
		return type;
	}

	public final String label() {
		return label;
	}

	public final String postfix() {
		return postfix;
	}

	public final String description() {
		return description;
	}

	public final String format() {
		return format;
	}

	public final boolean checkable() {
		return checkable;
	}

	public final boolean checked() {
		return checked;
	}

	public final String nullvalue() {
		return nullValue;
	}

	public final int rowspan() {
		return rowspan;
	}

	public final int coloumnspan() {
		return columnspan;
	}

	/**
	 * 创建时间 2010-10-20 上午11:47:04<br>
	 * 描述：输入正则表达式验证
	 * 
	 * @return
	 */
	public final String regexp() {
		return regexp;
	}

	/**
	 * 创建时间 2010-10-13 上午09:58:47<br>
	 * 描述：输入长度限制，默认128位
	 * 
	 * @return
	 */
	public final int length() {
		return length;
	}

	/**
	 * 文本框高度
	 * 
	 * @return
	 */
	public final int heightHint() {
		return heightHint;
	}

	/**
	 * 文本框自动换行，如果passwordEchoChar()不为空，必须置为false
	 * 
	 * @return
	 */
	public final boolean wrap() {
		return wrap;
	}

	/**
	 * 控件的样式
	 * 
	 * @return
	 */
	public final int style() {
		return style;
	}

	public final boolean modify() {
		return modify;
	}

	public boolean isEditable() {
		return editable;
	}

	public boolean isHasMD5Format() {
		return hasMD5Format;
	}

	public final long fileSize() {
		return fileSize;
	}

	public final int formatLength() {
		return formatLength;
	}

	public boolean zero() {
		return zero;
	}

	/**
	 * @return the radix
	 */
	public int radix() {
		return radix;
	}

}
