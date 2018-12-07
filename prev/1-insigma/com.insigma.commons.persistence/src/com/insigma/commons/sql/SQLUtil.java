package com.insigma.commons.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.insigma.commons.lang.DateSpan;

public class SQLUtil {

	public static String getSimpleSQL(Object form) {
		ArrayList<String> list = new ArrayList<String>();
		Field[] fields = form.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);

			try {
				if (field.get(form) != null) {
					if (field.getType() == DateSpan.class) {
						DateSpan span = (DateSpan) field.get(form);
						list.add(field.getName() + " between (" + span.getStartDate() + " and " + span.getEndDate()
								+ ") ");
					} else if (field.getType().isArray()) {
						list.add(field.getName() + " in (" + concat((Object[]) field.get(form), ",") + " )");

					} else {
						list.add(field.getName() + " =" + field.get(form));
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String sql = "";
		if (list.size() > 1) {
			for (int i = 0; i < list.size() - 1; i++) {
				sql = sql + list.get(i) + " and ";
			}
		}
		sql = sql + list.get(list.size() - 1);
		return sql;
	}

	public static String concat(Object[] array, String seperator) {
		if (array == null || array.length == 0) {
			return null;
		}
		String str = "";
		if (array.length > 1) {
			for (int i = 0; i < array.length - 1; i++) {
				str = str + array[i] + seperator;
			}
		}
		str = str + array[array.length - 1];
		return str;
	}

}
