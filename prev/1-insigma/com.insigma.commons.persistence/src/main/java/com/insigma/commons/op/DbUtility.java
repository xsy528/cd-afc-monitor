package com.insigma.commons.op;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Title: Description: Copyright: Copyright (c) 2004-9-16 Company: DOIT
 * 
 * @author jensol
 * @version 1.0
 */

public class DbUtility {
	static Logger logger = Logger.getLogger(DbUtility.class.getName());

	/**
	 * 执行存储函数或存储过程，返回输出参数。假设输出参数为第一个参数
	 * 
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call <ProcName>(?, <ParamList>)}
	 * @param returnType
	 *            输出参数类型
	 * @return 输出参数的值
	 */
	static public Object execStoredFunction(Connection cnt, String sql, int returnType) throws OPException {
		CallableStatement stmt = null;
		Object obj = null;
		try {
			stmt = cnt.prepareCall(sql);
			stmt.registerOutParameter(1, returnType);
			stmt.execute();
			obj = stmt.getObject(1);

			if (stmt != null) {
				stmt.close();
			}

			logger.debug("execStoredFunction succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execStoredFunction failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return obj;
	}

	/**
	 * 执行存储函数或存储过程并返回指定字段的值。此时存储过程或函数只能返回单记录
	 * 
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call <ProcName>(?, <ParamList>)}
	 * @param fieldName
	 *            字段名称
	 * @param fieldType
	 *            字段类型
	 * @return 字段值。若存储过程返回空记录，则字段值为 null；存储过程返回多记录，则字段值取第一行记录的值
	 */
	static public Object execStoredFunction(Connection cnt, String sql, String fieldName, int fieldType)
			throws OPException {
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		Object fieldValue = null;
		try {
			stmt = cnt.prepareCall(sql);
			stmt.registerOutParameter(1, java.sql.Types.OTHER);
			stmt.execute();
			resultSet = (ResultSet) stmt.getObject(1);

			if (resultSet != null) {
				switch (fieldType) {
				case java.sql.Types.DECIMAL:
					fieldValue = resultSet.getBigDecimal(fieldName);
					break;
				case java.sql.Types.VARCHAR:
					fieldValue = resultSet.getString(fieldName);
					break;
				case java.sql.Types.DATE:
					fieldValue = resultSet.getTimestamp(fieldName);
					break;
				}
			}

			if (resultSet != null) {
				resultSet.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			logger.debug("execStoredFunction succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execStoredFunction failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return fieldValue;
	}

	/**
	 * 执行存储函数或存储过程并返回结果集
	 * 
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call <ProcName>(?, <ParamList>)}
	 * @return 结果集
	 */
	static public ResultSet execStoredFunction(Connection cnt, String sql) throws OPException {
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = cnt.prepareCall(sql);
			stmt.registerOutParameter(1, -10);
			stmt.execute();
			resultSet = (ResultSet) stmt.getObject(1);
			if (stmt != null) {
				stmt.close();
			}
			if (cnt != null) {
				cnt.close();
			}

			logger.debug("execStoredFunction succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execStoredFunction failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return resultSet;
	}

	/**
	 * 执行存储函数或存储过程并返回结果集
	 * 
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call <ProcName>(?, <ParamList>)}
	 * @return 结果集
	 */
	@SuppressWarnings("unchecked")
	static public List execStoredFunctionForList(Connection cnt, String sql) throws OPException {
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		List list = new ArrayList();

		try {
			stmt = cnt.prepareCall(sql);
			stmt.registerOutParameter(1, -10);
			stmt.execute();
			resultSet = (ResultSet) stmt.getObject(1);

			if (resultSet != null) {
				ResultSetMetaData resultMetaData = resultSet.getMetaData();
				int fieldCount = resultMetaData.getColumnCount();
				while (resultSet.next()) {
					HashMap hm = new HashMap();
					for (int i = 1; i <= fieldCount; i++) {
						String fieldName = resultMetaData.getColumnName(i);
						Object fieldValue = resultSet.getObject(fieldName);
						if (resultMetaData.getColumnType(i) == Types.VARCHAR && fieldValue == null) {
							fieldValue = "";
						}
						hm.put(fieldName, fieldValue);
					}
					list.add(hm);
				}
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
			logger.error("execStoredFunctionForList failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (Exception e) {
				logger.error("关闭记录集出错!");
			}
		}
		return list;
	}

	/**
	 * 执行存储过程，不返回结果集
	 * 
	 * @param sql
	 *            存储过程的调用语句，格式：{call <ProcName>(<ParamList>)}
	 */
	static public void execStoredProcedure(Connection cnt, String sql) throws OPException {
		Statement stmt = null;
		try {
			stmt = cnt.createStatement();
			stmt.executeUpdate(sql);

			if (stmt != null) {
				stmt.close();
			}

			logger.debug("execStoredProcedure succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execStoredProcedure failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
	}

	/**
	 * 执行sql，不返回结果集
	 * 
	 * @param sqls
	 *            更新sql语句
	 */
	static public void execUpdate(Connection cnt, String[] sqls) throws OPException {
		Statement stmt = null;
		try {
			int j = 0;
			stmt = cnt.createStatement();
			for (int i = 0; i < sqls.length; i++) {
				stmt.addBatch(sqls[i]);

				//这使用j++保证从j从0开始比较，这个方法会在中间提交，是无法保证全量回滚 liuchao
				if (j++ == sqls.length / 10) {
					j = 0;
					stmt.executeBatch();
					cnt.commit();
					//					stmt.clearBatch();
				}
			}

			if (stmt != null) {
				stmt.close();
			}

			logger.debug("execStoredProcedure succeed. sql: " + sqls);
		} catch (SQLException ex) {
			logger.error("execStoredProcedure failed. sql: " + sqls);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
	}

	/**
	 * 执行sql，不返回结果集
	 * 
	 * @param sql
	 *            更新sql语句
	 */
	static public void execUpdate(Connection cnt, String sql, List<Object[]> objList) throws OPException {
		PreparedStatement ps = null;

		try {
			ps = cnt.prepareStatement(sql);

			for (int i = 0; i < objList.size(); i++) {

				Object[] objects = objList.get(i);

				int n = 0;

				for (Object object : objects) {
					ps.setObject(++n, object);
				}
				ps.addBatch();
			}
			ps.executeBatch();
			if (ps != null) {
				ps.close();
			}
			logger.debug("execStoredProcedure succeed. sql: " + sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("execStoredProcedure failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("释放对象错误!");
			}
		}
	}

	static public void execUpdate(Connection cnt, String sql) throws OPException {
		execUpdate(cnt, new String[] { sql });
	}

	/**
	 * 执行sql，不返回结果集
	 * 
	 * @param sqls
	 *            更新sql语句
	 */
	@SuppressWarnings("unchecked")
	static public List execQueryList(Connection cnt, String sql) throws OPException {
		Statement stmt = null;
		ResultSet resultSet = null;
		List list = new ArrayList();
		try {
			stmt = cnt.createStatement();
			resultSet = stmt.executeQuery(sql);
			if (resultSet != null) {
				ResultSetMetaData resultMetaData = resultSet.getMetaData();
				int fieldCount = resultMetaData.getColumnCount();
				while (resultSet.next()) {
					HashMap hm = new HashMap();
					for (int i = 1; i <= fieldCount; i++) {
						String fieldName = resultMetaData.getColumnName(i);
						Object fieldValue = resultSet.getObject(fieldName);
						if (resultMetaData.getColumnType(i) == Types.VARCHAR && fieldValue == null) {
							fieldValue = "";
						}
						hm.put(fieldName, fieldValue);
					}
					list.add(hm);
				}
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			logger.debug("execQueryList succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execQueryList failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return list;
	}

	/**
	 * 执行sql，不返回结果集
	 * 
	 * @param sqls
	 *            更新sql语句
	 */
	static public int getRowCount(Connection cnt, String sql) throws OPException {
		Statement stmt = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			stmt = cnt.createStatement();
			resultSet = stmt.executeQuery(sql);
			if (resultSet.next()) {
				count = resultSet.getInt(1);
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}

			logger.debug("getRowCount succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("getRowCount failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return count;
	}

	/**
	 * 执行sql，返回大对象的字节码
	 * 
	 * @param sqls
	 *            sql语句
	 */
	static public byte[] getDataStream(Connection cnt, String sql) throws OPException {
		Statement stmt = null;
		ResultSet resultSet = null;
		byte[] b = null;
		try {
			stmt = cnt.createStatement();
			resultSet = stmt.executeQuery(sql);
			if (resultSet != null) {
				if (resultSet.next()) {
					b = resultSet.getBytes(1);
				}
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			logger.debug("getDataStream succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("getDataStream failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return b;
	}

	/**
	 * 执行sql，返回blob的字节
	 * 
	 * @param sqls
	 *            sql语句
	 */
	static public InputStream getBlob(Connection cnt, String sql) throws OPException {
		Statement stmt = null;

		ResultSet resultSet = null;
		InputStream ret = null;
		try {
			stmt = cnt.createStatement();
			resultSet = stmt.executeQuery(sql);
			if (resultSet != null) {
				if (resultSet.next()) {
					Blob blob = resultSet.getBlob(1);
					ret = blob.getBinaryStream();
				}
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			logger.debug("getBlob succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("getBlob failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} catch (Exception ex) {
			logger.error("getBlob failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return ret;
	}

	// /**
	// * 执行sql，返回blob的字节
	// *
	// * @param sqls
	// * sql语句
	// */
	// static public InputStream getOracleBlob(Connection cnt, String sql) throws OPException {
	// Statement stmt = null;
	//
	// ResultSet resultSet = null;
	// InputStream ret = null;
	// try {
	// stmt = cnt.createStatement();
	// resultSet = stmt.executeQuery(sql);
	// if (resultSet != null) {
	// if (resultSet.next()) {
	// oracle.sql.BLOB blob = (oracle.sql.BLOB) resultSet.getBlob(1);
	// ret = blob.getBinaryStream();
	// }
	// resultSet.close();
	// }
	// if (stmt != null) {
	// stmt.close();
	// }
	// logger.debug("getOracleBlob succeed. sql: " + sql);
	// } catch (SQLException ex) {
	// logger.error("getOracleBlob failed. sql: " + sql);
	// throw new OPException(ex.getMessage());
	// } catch (Exception ex) {
	// logger.error("getOracleBlob failed. sql: " + sql);
	// throw new OPException(ex.getMessage());
	// } finally {
	// try {
	// if (stmt != null) {
	// stmt.close();
	// }
	// } catch (Exception e) {
	// logger.error("释放对象错误!");
	// }
	// }
	// return ret;
	// }
}