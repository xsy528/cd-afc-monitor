/**
 * 
 */
package com.insigma.commons.ui.anotation.data;

import java.util.Map;

import com.insigma.commons.ui.anotation.DataSource;

/**
 * @author DLF
 *
 */
public class DataSourceData extends AnnotationData {

	private Class<?> provider = Object.class;

	private String[] list = {};

	private String[] values = {};

	/*public */ DataSourceData() {
		super();
	}

	public DataSourceData(DataSource dataSource) {
		super();
		this.provider = dataSource.provider();
		this.list = dataSource.list();
		this.values = dataSource.values();
	}

	public DataSourceData(Map<String, Object> annotationItems) {
		super(annotationItems);
		this.provider = get("provider", Class.class, Object.class);
		this.list = get("list", String[].class, new String[] {});
		this.values = get("values", String[].class, new String[] {});
	}

	public final Class<?> provider() {
		return provider;
	}

	public final String[] list() {
		return list;
	}

	public final String[] values() {
		return values;
	}
}
