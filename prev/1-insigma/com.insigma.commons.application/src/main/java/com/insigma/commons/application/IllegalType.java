/* 
 * 日期：2010-8-19
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.application;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.insigma.commons.dic.Definition;
import com.insigma.commons.dic.annotation.Dic;
import com.insigma.commons.dic.annotation.DicItem;

/**
 * 创建时间 2010-8-19 下午04:57:32<br>
 * 描述：<br>
 * 数据处理文件异常类型定义 Ticket:
 * 
 * @author DingLuofeng
 */
@Dic(overClass = IllegalType.class)
public class IllegalType extends Definition {

	private static IllegalType instance = new IllegalType();

	public IllegalType() {

	}

	public static IllegalType getInstance() {
		return instance;
	}

	/**
	 * 文件处理完成(在文件处理表tsy_message_data中)
	 */
	@DicItem(name = "正常")
	public static Integer PROCESS_SUCCESSED = 0x0000;

	/**
	 * 文件名格式异常(在异常文件表中)
	 */
	@DicItem(name = "文件名格式异常")
	public static Integer FILE_NAME_INVALID = 0x0001;

	/**
	 * 签名不正确(在异常文件表中)
	 */
	@DicItem(name = "签名不正确")
	public static Integer FILE_SIGNATURE_INVALID = 0x0002;

	/**
	 * 文件名重复(在异常文件表中)
	 */
	@DicItem(name = "文件名重复")
	public static Integer FILE_NAME_DUPLICATE = 0x0004;

	/**
	 * 文件内容异常，解析不正确 (在文件处理表tsy_message_data中)
	 */
	@DicItem(name = "文件内容异常")
	public static Integer FILE_CONTENT_INVALID = 0x0008;

	/**
	 * 文件中存在不合法交易 (在文件处理表tsy_message_data中)
	 */
	@DicItem(name = "文件中存在不合法交易")
	public static Integer TRANSACTION_INVALID = 0x0010;

	/**
	 * 交易丢失，文件中的实际交易条数比文件头中的交易条数少 (在文件处理表tsy_message_data中)
	 */
	@DicItem(name = "交易丢失")
	public static Integer TRANSACTION_LOSS = 0x0020;

	/**
	 * 交易重复，文件中的交易，已经在历史文件中存在 (在文件处理表tsy_message_data中)
	 */
	@DicItem(name = "交易重复")
	public static Integer TRANSACTION_DUPLICATE = 0x0040;

	/**
	 * 文件解压异常
	 */
	@DicItem(name = "文件解压异常")
	public static Integer FILE_EXTRACT_INVALID = 0x0100;

	/**
	 * 未知异常
	 */
	@DicItem(name = "未知异常")
	public static Integer OTHER_EXCEPTION = 0x0200;

	/**
	 * 文件类型map
	 */
	private static Map<Integer, String> illegalTypeMap = new HashMap<Integer, String>();

	static {
		try {
			IllegalType ft = new IllegalType();
			Class<?> clazz = ft.getClass();
			Field[] fs = clazz.getDeclaredFields();
			for (Field field : fs) {
				DicItem view = field.getAnnotation(DicItem.class);
				if (view != null && (field.getModifiers() & Modifier.FINAL) != 0
						&& field.getType().isAssignableFrom(int.class)) {
					String name = view.name();
					field.setAccessible(true);
					Integer type = field.getInt(ft);
					illegalTypeMap.put(type, name);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the illegalTypeMap
	 */
	public static Map<Integer, String> getIllegalTypeMap() {
		return illegalTypeMap;
	}

	public static String getIllegeTypeName(Integer illegalType) {
		String illegalTypeName = "未知异常(" + illegalType + ")";
		if (IllegalType.getInstance().getCodeMap().get(illegalType) != null) {
			illegalTypeName = IllegalType.getInstance().getCodeMap().get(illegalType);
		}
		return illegalTypeName;
	}

	/**
	 * 获取未知的异常，因为数据处理会把多种异常的文件以“与运算”存储，例：0x0001&0x0002
	 * 
	 * @return
	 */
	public static String getOtherIllegalTypeSql() {
		String separator = " ,";
		String illegalSql = "(" + IllegalType.PROCESS_SUCCESSED + separator + IllegalType.FILE_NAME_INVALID + separator
				+ IllegalType.FILE_SIGNATURE_INVALID + separator + IllegalType.FILE_NAME_DUPLICATE + separator
				+ IllegalType.FILE_CONTENT_INVALID + separator + IllegalType.TRANSACTION_INVALID + separator
				+ IllegalType.TRANSACTION_LOSS + separator + IllegalType.TRANSACTION_DUPLICATE + separator
				+ IllegalType.FILE_EXTRACT_INVALID + ")";
		return illegalSql;
	}
}
