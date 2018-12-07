package com.insigma.commons.op;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.insigma.commons.database.GlobalNames;

/**
 * <p>
 * Title: insigma信息管理系统
 * </p>
 * <p>
 * Description: 对参数表的操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: insigma软件
 * </p>
 * 
 * @author jensol</a>
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class SqlRestrictions {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String oracleDateString = "yyyy-mm-dd   hh24:mi:ss";

	/**
	 * 如果hm对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName:字段名字
	 * @param hm:查询的集合
	 * @param queryName:查询字段的名字
	 * @return 部分sql
	 */

	public static String eq(String fieldName, HashMap hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " = '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String eq(String fieldName, Object matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " = '" + matchObject + "'";
		}
		return sqlWhere;
	}

	public static String eq(String fieldName, Short matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " = " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String eq(String fieldName, String matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " = '" + matchObject + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成String，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String eq(String fieldName, Long matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " = " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String eq(String fieldName, Integer matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " = " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String eq(String fieldName, Date matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " = to_date('" + matchObject + "','" + oracleDateString + "')";
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param hm
	 * @param queryName
	 * @return 部分sql
	 */
	public static String ne(String fieldName, HashMap hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " <> '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String ne(String fieldName, String matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " <> '" + matchObject + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String ne(String fieldName, Long matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " <> " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String ne(String fieldName, Integer matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " <> " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String ne(String fieldName, Date matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " <> to_date('" + matchObject + "','" + oracleDateString + "')";
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时，将其连接成匹配的sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param hm
	 * @param queryName
	 * @param matchMode:1-不加%
	 *            2-左边加% 3-两边加% 4-右边加%
	 * @return 部分sql
	 */
	public static String like(String fieldName, HashMap hm, String queryName, int matchMode) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			if (matchMode == LikeMatchMode.NOTADD) {
				sqlWhere = " and " + fieldName + " like '" + hm.get(queryName) + "'";
			} else if (matchMode == LikeMatchMode.LEFTADD) {
				sqlWhere = " and " + fieldName + " like '%" + hm.get(queryName) + "'";
			} else if (matchMode == LikeMatchMode.BOTHADD) {
				sqlWhere = " and " + fieldName + " like '%" + hm.get(queryName) + "%'";
			} else if (matchMode == LikeMatchMode.RIGHTADD) {
				sqlWhere = " and " + fieldName + " like '" + hm.get(queryName) + "%'";
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果matchMode对象或值不为空时，将其连接成匹配的sql串，返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @param matchMode
	 * @return 部分sql
	 */
	public static String like(String fieldName, String matchObject, int matchMode) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			if (matchMode == LikeMatchMode.NOTADD) {
				sqlWhere = " and " + fieldName + " like '" + matchObject + "'";
			} else if (matchMode == LikeMatchMode.LEFTADD) {
				sqlWhere = " and " + fieldName + " like '%" + matchObject + "'";
			} else if (matchMode == LikeMatchMode.BOTHADD) {
				sqlWhere = " and " + fieldName + " like '%" + matchObject + "%'";
			} else if (matchMode == LikeMatchMode.RIGHTADD) {
				sqlWhere = " and " + fieldName + " like '" + matchObject + "%'";
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param hm
	 * @param queryName
	 * @return 部分sql
	 */
	public static String gt(String fieldName, HashMap hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " > '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchMode对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String gt(String fieldName, String matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " > '" + matchObject + "'";
		}
		return sqlWhere;
	}

	public static String gt(String fieldName, Short matchObject) {
		String sqlWhere = "";
		if (matchObject != null) {
			sqlWhere = " and " + fieldName + " > " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchMode对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String gt(String fieldName, Long matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " > " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String gt(String fieldName, Integer matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " > " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String gt(String fieldName, Date matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " > to_date('" + matchObject + "','" + oracleDateString + "')";
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param hm
	 * @param queryName
	 * @return 部分sql
	 */
	public static String lt(String fieldName, HashMap hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " < '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String lt(String fieldName, String matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " < '" + matchObject + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String lt(String fieldName, Long matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " < " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String lt(String fieldName, Integer matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " < " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String lt(String fieldName, Date matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " < to_date('" + matchObject + "','" + oracleDateString + "')";
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param hm
	 * @param queryName
	 * @return 部分sql
	 */
	public static String ge(String fieldName, HashMap hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " >= '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String ge(String fieldName, String matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " >= '" + matchObject + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String ge(String fieldName, Long matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " >= " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String ge(String fieldName, Integer matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " >= " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String ge(String fieldName, java.util.Date matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " >= to_date('" + sdf.format(matchObject) + "','" + oracleDateString
					+ "')";
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param hm
	 * @param queryName
	 * @return 部分sql
	 */
	public static String le(String fieldName, HashMap hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " <= '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String le(String fieldName, String matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " <= '" + matchObject + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String le(String fieldName, Long matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " <= " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String le(String fieldName, Integer matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " <= " + matchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String le(String fieldName, Date matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " <= to_date('" + sdf.format(matchObject) + "','" + oracleDateString
					+ "')";
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param hm
	 * @param loQueryName
	 * @param hiQueryName
	 * @return 部分sql
	 */
	public static String between(String fieldName, HashMap hm, String loQueryName, String hiQueryName) {
		String sqlWhere = "";
		if (hm.containsKey(loQueryName) && !hm.get(loQueryName).equals("")) {
			sqlWhere = " and " + fieldName + " >= '" + hm.get(loQueryName) + "'";
		}
		if (hm.containsKey(hiQueryName) && !hm.get(hiQueryName).equals("")) {
			sqlWhere += " and " + fieldName + " <= '" + hm.get(hiQueryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果loMatchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param loMatchObject
	 * @param hiMatchObject
	 * @return 部分sql
	 */
	public static String between(String fieldName, String loMatchObject, String hiMatchObject) {
		String sqlWhere = "";
		if (loMatchObject != null && !loMatchObject.equals("")) {
			sqlWhere = " and " + fieldName + " >= '" + loMatchObject + "'";
		}
		if (hiMatchObject != null && !hiMatchObject.equals("")) {
			sqlWhere += " and " + fieldName + " <= '" + hiMatchObject + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果loMatchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param loMatchObject
	 * @param hiMatchObject
	 * @return 部分sql
	 */
	public static String between(String fieldName, Long loMatchObject, Long hiMatchObject) {
		String sqlWhere = "";
		if (loMatchObject != null && !loMatchObject.equals("")) {
			sqlWhere = " and " + fieldName + " >= " + loMatchObject;
		}
		if (hiMatchObject != null && !hiMatchObject.equals("")) {
			sqlWhere += " and " + fieldName + " <= " + hiMatchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果loMatchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param loMatchObject
	 * @param hiMatchObject
	 * @return 部分sql
	 */
	public static String between(String fieldName, Integer loMatchObject, Integer hiMatchObject) {
		String sqlWhere = "";
		if (loMatchObject != null && !loMatchObject.equals("")) {
			sqlWhere = " and " + fieldName + " >= " + loMatchObject;
		}
		if (hiMatchObject != null && !hiMatchObject.equals("")) {
			sqlWhere += " and " + fieldName + " <= " + hiMatchObject;
		}
		return sqlWhere;
	}

	/**
	 * 如果loMatchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param loMatchObject
	 * @param hiMatchObject
	 * @return 部分sql
	 */
	public static String between(String fieldName, Date loMatchObject, Date hiMatchObject) {
		String sqlWhere = "";
		if (loMatchObject != null && !loMatchObject.equals("")) {
			if (GlobalNames.DB_TYPE.equals(Dictionarys.DB_ORACLE)) {
				sqlWhere = " and " + fieldName + " >= to_date('" + sdf.format(loMatchObject) + "','" + oracleDateString
						+ "')";
			} else {
				sqlWhere = " and " + fieldName + " >'" + sdf.format(loMatchObject) + "')";
			}
		}
		if (hiMatchObject != null && !hiMatchObject.equals("")) {
			if (GlobalNames.DB_TYPE.equals(Dictionarys.DB_ORACLE)) {
				sqlWhere += " and " + fieldName + " <= to_date('" + sdf.format(hiMatchObject) + "','" + oracleDateString
						+ "')";
			} else {
				sqlWhere += " and " + fieldName + " <= '" + sdf.format(hiMatchObject) + "')";
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param hm
	 * @param queryName
	 * @return 部分sql
	 */
	public static String in(String fieldName, HashMap hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " in " + hm.get(queryName) + "";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String in(String fieldName, String matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			sqlWhere = " and " + fieldName + " in (" + matchObject + ")";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String in(String fieldName, Integer[] matchObject) {
		String sqlWhere = "";
		if (matchObject != null && matchObject.length > 0) {
			sqlWhere += " and " + fieldName + " in( " + matchObject[0];
			for (int i = 1; i < matchObject.length; i++) {
				sqlWhere += " ," + matchObject[i];
			}
			sqlWhere += " )";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String in(String fieldName, Short[] matchObject) {
		String sqlWhere = "";
		if (matchObject != null && matchObject.length > 0) {
			sqlWhere += " and " + fieldName + " in( " + matchObject[0];
			for (int i = 1; i < matchObject.length; i++) {
				sqlWhere += " ," + matchObject[i];
			}
			sqlWhere += " )";
		}
		return sqlWhere;
	}

	/**
	 * 如果list对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 *            表字段
	 * @param matchObject
	 *            a list of <code>Integer</code>
	 * @return 部分sql
	 */
	public static String in(String fieldName, List list, boolean hasAddQuotationMarks) {
		String sqlWhere = "";
		if (list != null && list.size() > 0) {
			if (hasAddQuotationMarks) {

				sqlWhere += " and " + fieldName + " in( '" + list.get(0) + "'";
				for (int i = 1; i < list.size(); i++) {
					sqlWhere += " ,'" + list.get(i) + "'";
				}
				sqlWhere += " )";
			} else {

				sqlWhere += " and " + fieldName + " in( " + list.get(0);
				for (int i = 1; i < list.size(); i++) {
					sqlWhere += " ," + list.get(i);
				}
				sqlWhere += " )";

			}
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 * @param matchObject
	 * @return 部分sql
	 */
	public static String in(String fieldName, Long[] matchObject) {
		String sqlWhere = "";
		if (matchObject != null && matchObject.length > 0) {
			sqlWhere += " and " + fieldName + " in( " + matchObject[0];
			for (int i = 1; i < matchObject.length; i++) {
				sqlWhere += " ," + matchObject[i];
			}
			sqlWhere += " )";
		}
		return sqlWhere;
	}

	/**
	 * 如果fieldName的值不为空，将sqlWhere进行排序，返回一个S
	 * 
	 * @param fieldName
	 * @param orderType:1-升序
	 *            2-降序
	 * @return 部分sql
	 */
	public static String order(String fieldName, int orderType) {
		String sqlWhere = "";
		if (!fieldName.equals("")) {
			sqlWhere = " order by " + fieldName + (orderType == 1 ? "" : " desc");
		}
		return sqlWhere;
	}

	/**
	 * 返回一个排序后的String
	 * 
	 * @param fieldName
	 * @return 部分sql
	 */
	public static String order(String fieldName) {
		return order(fieldName, 1);
	}
}