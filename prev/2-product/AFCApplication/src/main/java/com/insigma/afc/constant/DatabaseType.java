/* 
 * 日期：2010-12-3
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.constant;

public enum DatabaseType {
	ORACLE(0, "ORACLE"), DB2(1, "DB2"), UNKNOW(3, "未知数据库类型");

	private short type;

	private String name;

	private DatabaseType(int fileType, String name) {
		this.type = Integer.valueOf(fileType).shortValue();
		this.name = name;
	}

	public Short getValue() {
		return type;
	}

	@Override
	public String toString() {
		return name;
	}

	public static DatabaseType parserParamType(short type) {
		DatabaseType[] vs = DatabaseType.values();
		for (DatabaseType paramStatus : vs) {
			if (paramStatus.type == type) {
				return paramStatus;
			}
		}
		return UNKNOW;
	}
}