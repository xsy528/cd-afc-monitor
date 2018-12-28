package com.insigma.commons.converter;

import java.sql.Date;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public final class SqlDateConverter implements Converter {

	private Object defaultValue;

	private boolean useDefault;

	public SqlDateConverter() {
		defaultValue = null;
		useDefault = true;
		defaultValue = null;
		useDefault = false;
	}

	public SqlDateConverter(Object defaultValue) {
		this.defaultValue = null;
		useDefault = true;
		this.defaultValue = defaultValue;
		useDefault = true;
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null || "".equals(value.toString().trim()))
			return defaultValue;
		if (value instanceof Date)
			return value;
		try {
			return Date.valueOf(value.toString());
		} catch (Exception e) {
			if (useDefault)
				return defaultValue;
			else
				throw new ConversionException(e);
		}
	}
}
