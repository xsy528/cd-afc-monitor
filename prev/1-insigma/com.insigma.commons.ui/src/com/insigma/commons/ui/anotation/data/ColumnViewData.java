/**
 * 
 */
package com.insigma.commons.ui.anotation.data;

import java.util.Map;

import org.eclipse.swt.SWT;

import com.insigma.commons.ui.anotation.ColumnView;
import com.insigma.commons.ui.convert.ColumnConvertor;
import com.insigma.commons.ui.convert.ConvertorAdapter;

/**
 * @author DingLuofeng
 *
 */
public class ColumnViewData extends AnnotationData {

	private String name;

	private int width = 100;

	private int alignment = SWT.CENTER;

	private boolean sortAble;

	private Class<? extends ColumnConvertor> convertor = ConvertorAdapter.class;

	private String nullValue = "";

	private String el = "";

	public ColumnViewData(ColumnView view) {
		this.name = view.name();
		this.width = view.width();
		this.alignment = view.alignment();
		this.sortAble = view.sortAble();
		this.convertor = view.convertor();
		this.nullValue = view.nullvalue();
		this.el = view.el();
	}

	@SuppressWarnings("unchecked")
	public ColumnViewData(Map<String, Object> viewMap) {
		super(viewMap);
		this.name = get("name", String.class, "");
		this.width = get("width", int.class, 100);
		this.alignment = get("alignment", int.class, SWT.CENTER);
		this.sortAble = get("sortAble", boolean.class, false);
		this.convertor = get("convertor", Class.class, ConvertorAdapter.class);
		this.nullValue = get("nullValue", String.class, "");
		this.el = get("el", String.class, "");
	}

	public final String name() {
		return name;
	}

	public final int width() {
		return width;
	}

	public final int alignment() {
		return alignment;
	}

	/**
	 * 排序
	 * @return
	 */
	public final boolean sortAble() {
		return sortAble;
	}

	/**
	 * value显示
	 */
	public final Class<? extends ColumnConvertor> convertor() {
		return convertor;
	}

	/**
	 * @return
	 */
	public final String nullvalue() {
		return nullValue;
	}

	public final String el() {
		return el;
	};

}
