package com.insigma.commons.hibernate;

import org.hibernate.MappingException;
import org.hibernate.dialect.MySQL5InnoDBDialect;

public class Mysql5InnoDBDialectWithSequenceSupport extends MySQL5InnoDBDialect {

	private int incrementSize = 1;

	@Override
	protected String getCreateSequenceString(String sequenceName) throws MappingException {
		return "insert into mysqlsequence values('" + sequenceName + "',1)";
		// throw new MappingException("暂时不实现,若要实现请给 Mysql5InnoDBDialectWithSequenceSupport 类打补丁");
		// return super.getCreateSequenceString(sequenceName);
	}

	@Override
	public String[] getCreateSequenceStrings(String sequenceName, int initialValue, int incrementSize)
			throws MappingException {
		String s = "insert into mysqlsequence values('" + sequenceName + "'," + initialValue + ")";
		return new String[] { s };
		// throw new MappingException("暂时不实现,若要实现请给 Mysql5InnoDBDialectWithSequenceSupport 类打补丁");
	}

	@Override
	public String[] getCreateSequenceStrings(String sequenceName) throws MappingException {
		return new String[] { "insert into mysqlsequence values('" + sequenceName + "',1)" };
		// throw new MappingException("暂时不实现,若要实现请给 Mysql5InnoDBDialectWithSequenceSupport 类打补丁");
	}

	@Override
	protected String getDropSequenceString(String sequenceName) throws MappingException {
		return "delete from mysqlsequence where name=' " + sequenceName + "'";
		// throw new MappingException("暂时不实现,若要实现请给 Mysql5InnoDBDialectWithSequenceSupport 类打补丁");
	}

	@Override
	public String[] getDropSequenceStrings(String sequenceName) throws MappingException {
		return new String[] { "delete from mysqlsequence where name=' " + sequenceName + "'" };
		// throw new MappingException("暂时不实现,若要实现请给 Mysql5InnoDBDialectWithSequenceSupport 类打补丁");
	}

	@Override
	public String getQuerySequencesString() {
		return "select name from mysqlsequence";
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.dialect.Dialect#getSelectSequenceNextValString(java.lang.String)
	 */
	@Override
	public String getSelectSequenceNextValString(String sequenceName) throws MappingException {
		return "select nextval('" + sequenceName + "'," + incrementSize + ");";
	}

	@Override
	public String getSequenceNextValString(String sequenceName) throws MappingException {
		return "select nextval('" + sequenceName + "'," + incrementSize + ");";
	}

	/**
	 * 支持 sequence
	 */
	@Override
	public boolean supportsSequences() {
		return true;
	}

}
