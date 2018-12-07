package com.insigma.commons.converter;

import org.apache.commons.beanutils.ConvertUtils;

public class ConvertRegister {
	public ConvertRegister() {
	}

	public static void register() {
		ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
		ConvertUtils.register(new BigIntegerConverter(null), java.math.BigInteger.class);
		ConvertUtils.register(new BooleanConverter(null), Boolean.TYPE);
		ConvertUtils.register(new BooleanConverter(null), java.lang.Boolean.class);
		ConvertUtils.register(new ByteConverter(null), Byte.TYPE);
		ConvertUtils.register(new ByteConverter(null), java.lang.Byte.class);
		ConvertUtils.register(new CharacterConverter(null), Character.TYPE);
		ConvertUtils.register(new CharacterConverter(null), java.lang.Character.class);
		ConvertUtils.register(new DoubleConverter(null), Double.TYPE);
		ConvertUtils.register(new DoubleConverter(null), java.lang.Double.class);
		ConvertUtils.register(new FloatConverter(null), Float.TYPE);
		ConvertUtils.register(new FloatConverter(null), java.lang.Float.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.TYPE);
		ConvertUtils.register(new IntegerConverter(null), java.lang.Integer.class);
		ConvertUtils.register(new LongConverter(null), Long.TYPE);
		ConvertUtils.register(new LongConverter(null), java.lang.Long.class);
		ConvertUtils.register(new ShortConverter(null), Short.TYPE);
		ConvertUtils.register(new ShortConverter(null), java.lang.Short.class);
		ConvertUtils.register(new StringConverter(), java.lang.String.class);
		//		ConvertUtils
		//				.register(new UtilDateConverter(null), java.util.Date.class);
		ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
	}
}
