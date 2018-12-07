package com.insigma.commons.converter;

import java.lang.reflect.Array;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Convert {

	protected static FastHashMap converters;

	protected static Log log;

	public Convert() {
	}

	@SuppressWarnings("unchecked")
	public static Object convert(String value, Class clazz) {
		Converter converter = (Converter) converters.get(clazz);
		if (converter == null)
			converter = (Converter) converters.get(java.lang.String.class);
		return converter.convert(clazz, value);
	}

	@SuppressWarnings("unchecked")
	public static Object convert(String values[], Class clazz) {
		Class type = clazz;
		if (clazz.isArray())
			type = clazz.getComponentType();
		Converter converter = (Converter) converters.get(type);
		if (converter == null)
			converter = (Converter) converters.get(java.lang.String.class);
		Object array = Array.newInstance(type, values.length);
		for (int i = 0; i < values.length; i++)
			Array.set(array, i, converter.convert(type, values[i]));

		return array;
	}

	private static void register() {
		converters.put(java.math.BigDecimal.class, new BigDecimalConverter(null));
		converters.put(java.math.BigInteger.class, new BigIntegerConverter(null));
		converters.put(Boolean.TYPE, new BooleanConverter(null));
		converters.put(java.lang.Boolean.class, new BooleanConverter(null));
		converters.put(Byte.TYPE, new ByteConverter(null));
		converters.put(java.lang.Byte.class, new ByteConverter(null));
		converters.put(Character.TYPE, new CharacterConverter(null));
		converters.put(java.lang.Character.class, new CharacterConverter(null));
		converters.put(Double.TYPE, new DoubleConverter(null));
		converters.put(java.lang.Double.class, new DoubleConverter(null));
		converters.put(Float.TYPE, new FloatConverter(null));
		converters.put(java.lang.Float.class, new FloatConverter(null));
		converters.put(Integer.TYPE, new IntegerConverter(null));
		converters.put(java.lang.Integer.class, new IntegerConverter(null));
		converters.put(Long.TYPE, new LongConverter(null));
		converters.put(java.lang.Long.class, new LongConverter(null));
		converters.put(Short.TYPE, new ShortConverter(null));
		converters.put(java.lang.Short.class, new ShortConverter(null));
		converters.put(java.lang.String.class, new StringConverter());
		//		converters.put(java.sql.Date.class, new UtilDateConverter(null));
	}

	static {
		converters = new FastHashMap();
		converters.setFast(false);
		register();
		converters.setFast(true);
		log = LogFactory.getLog(com.insigma.commons.converter.Convert.class);
	}
}
