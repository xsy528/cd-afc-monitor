package com.insigma.commons.op;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.io.CSVReader;
import com.insigma.commons.io.CSVWriter;
import com.insigma.commons.io.ColumnValuePattern;
import com.insigma.commons.io.FileUtil;

/**
 * Title: Description: Copyright: Copyright (c) 2004-9-16 Company: DOIT
 * 
 * @author jensol
 * @version 1.0
 */

@SuppressWarnings("unchecked")
public class BaseHibernateDao extends HibernateDaoSupport {

	public static int PAGE_SIZE = 10;

	private static Logger logger = Logger.getLogger(BaseHibernateDao.class);

	private final ExecutorService executor = Executors.newCachedThreadPool();

	private static List emptyList = new ArrayList(0);

	public void setDelecatedSessionFacroty(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	/**
	 * 根据pk删除对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @throws OPException
	 */
	public void removeObj(Class c, Serializable id) throws OPException {
		try {
			Object obj = getHibernateTemplate().load(c, id);
			getHibernateTemplate().delete(obj);
		} catch (HibernateException e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 删除对象
	 * 
	 * @param o
	 * @throws OPException
	 */
	public void removeObj(Object o) throws OPException {
		getSession().delete(o);
	}

	/**
	 * 根据HQL删除对象集合（类似 from Entity as e where e.pk=*）
	 * 
	 * @param HQL
	 * @throws OPException
	 */
	public void removeObjs(String HQL) throws OPException {
		getSession().createQuery(HQL).executeUpdate();
	}

	/**
	 * 删除对象集合
	 * 
	 * @param objs
	 * @throws OPException
	 */
	public void removeObjs(Collection objs) throws OPException {
		if (objs == null) {
			throw new OPException("持久化对象为空。");
		}
		try {
			getHibernateTemplate().deleteAll(objs);
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 根据pk找回对象,没找到赋予null
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @return 实体对象
	 * @throws OPException
	 */
	public Object retrieveObjs(Class c, Serializable id) throws OPException {
		Object obj = null;
		try {
			obj = getHibernateTemplate().get(c, id, LockMode.READ);
		} catch (Exception he) {
			throw OPUtil.handleException(he);
		}
		return obj;
	}

	/**
	 * 根据pk找回对象(带锁模式),没找到抛出ObjectNotFoundException异常
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @param lockMode
	 *            锁模式
	 * @return 实体对象
	 * @throws OPException
	 * @throws NotFindException
	 */
	public Object retrieveObjForUpdate(Class c, Serializable id, LockMode lockMode) {
		Object obj;
		obj = null;

		obj = getHibernateTemplate().load(c, id, lockMode);

		return obj;
	}

	/**
	 * 保存对象集合
	 * 
	 * @param obj
	 *            对象
	 * @throws OPException
	 */
	public Serializable saveObj(Object obj) throws OPException {
		if (obj == null) {
			throw new OPException("保存失败，持久化对象为空。");
		}
		try {
			return getHibernateTemplate().save(obj);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 保存对象数据
	 * 
	 * @param objs
	 *            对象数据
	 * @throws OPException
	 */
	public void saveObjs(Object objs[]) throws OPException {
		if (objs == null) {
			throw new OPException("保存失败，持久化对象为空。");
		}
		try {
			for (Object element : objs) {
				if (element == null) {
					throw new OPException("保存失败，持久化对象为空。");
				}
				getHibernateTemplate().save(element);
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 保存和更新对象
	 * 
	 * @param saveAndUpdate
	 *            需要更新和新增的对象集合
	 * @throws OPException
	 */
	public void saveAndUpdateObjs(Object saveAndUpdate[]) throws OPException {
		try {
			if (saveAndUpdate != null) {
				for (Object element : saveAndUpdate) {
					if (element == null) {
						throw new OPException("保存失败，持久化对象为空。");
					}
					getHibernateTemplate().saveOrUpdate(element);
				}
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 保存和更新对象
	 * 
	 * @param saveAndUpdate
	 *            需要更新和新增的对象集合
	 * @throws OPException
	 */
	public void saveAndUpdateObj(Object saveAndUpdate) throws OPException {
		try {
			if (saveAndUpdate == null) {
				throw new OPException("保存失败，持久化对象为空。");
			}
			getHibernateTemplate().saveOrUpdate(saveAndUpdate);
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param obj
	 * @throws OPException
	 */
	public void updateObj(Object obj) throws OPException {
		updateObjs(new Object[] { obj });
	}

	/**
	 * 更新对象集合
	 * 
	 * @param objs
	 * @throws OPException
	 */
	public void updateObjs(Object objs[]) throws OPException {
		if (objs == null) {
			throw new OPException("持久化对象为空。");
		}
		try {
			for (Object element : objs) {
				if (element == null) {
					throw new OPException("更新失败，持久化对象为空。");
				}
				getHibernateTemplate().update(element);
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 利用hql更新 2007-10-17 add by fenghong
	 * 
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @throws OPException
	 */
	public void updateObjs(String hql, Object... objects) throws OPException {
		try {
			Query query = getSession().createQuery(hql);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			query.executeUpdate();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 利用预编译HQL查询记录总数
	 * 
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return Long 返回记录条数
	 */
	public Long getCount(String hql, Object... objects) throws OPException {
		try {
			Query query = getSession().createQuery(hql);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			List<Object> result = query.list();
			if (result == null || result.isEmpty()) {
				return 0l;
			} else {
				return (Long) result.iterator().next();
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 利用预编译SQL查询，如果查询不到，返回新的ArrayList，不返回NullPoint。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pageNum
	 * @param pageSize
	 * @param objects
	 *            变量绑定参数
	 * @return Query 返回Query
	 */
	public List retrieveSQLObjs(String sql, int pageNum, int pageSize, Class clazz, Object... objects)
			throws OPException {
		try {
			SQLQuery query = getSession().createSQLQuery(sql);
			query.addEntity(clazz);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			query.setFirstResult(pageSize * pageNum);
			query.setMaxResults(pageSize);
			query.setCacheable(true);
			query.setCacheRegion("frontpages");
			List result = query.list();
			if (result == null) {
				return emptyList;
			} else {
				return result;
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}

	}

	/**
	 * 利用预编译SQL查询，如果查询不到，返回新的ArrayList，基本是返回List<Object[]>，不返回NullPoint。 *
	 * 
	 * @param sql
	 *            SQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return Query 返回Query
	 */
	public List getEntityListSQL(String sql, Object... objects) throws OPException {
		return retrieveEntityListSQL(sql, -1, 0, objects);

	}

	/**
	 * 利用预编译SQL查询，如果查询不到，返回新的ArrayList，基本是返回List<Object[]>，不返回NullPoint。 *
	 * 
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            第一页从0开始 ,小于0不分页
	 * @param pageSize
	 *            每页显示数量
	 * @param objects
	 *            变量绑定参数
	 * @return Query 返回Query
	 */
	public List retrieveEntityListSQL(String sql, int page, int pageSize, Object... objects) throws OPException {
		Session session = null;
		try {
			session = getSession();
			SQLQuery query = session.createSQLQuery(sql);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			if (page >= 0) {
				query.setFirstResult(pageSize * page);
				query.setMaxResults(pageSize);
			}
			query.setCacheable(true);
			query.setCacheRegion("frontpages");
			List result = query.list();
			if (result == null) {
				return emptyList;
			} else {
				return result;
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			releaseSession(session);
		}

	}

	/**
	 * 根据sql获取列表
	 * 
	 * @param sql
	 *            sql
	 * @param objects
	 *            变量绑定参数
	 * @return list Object[]对象列表
	 * @throws OPException
	 */
	public List execSqlQuery(String sql, Object... objects) throws OPException {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List list = new ArrayList();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			int i = 0;
			for (Object object : objects) {
				setObject(pstmt, ++i, object);
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData resultMetaData = rs.getMetaData();
			int fieldCount = resultMetaData.getColumnCount();
			while (rs.next()) {
				if (fieldCount == 1) {
					list.add(getObject(rs, 1));
				} else {
					List<Object> l = new ArrayList<Object>();
					for (int j = 1; j <= fieldCount; j++) {
						l.add(getObject(rs, j));
					}
					list.add(l.toArray());
					l = null;
				}
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
			}
		}
		return list;
	}

	private void setObject(PreparedStatement pstmt, int paramIndex, Object paramObject) throws SQLException {
		if (paramObject != null && (paramObject instanceof java.sql.Date || paramObject instanceof Timestamp)) {
			// pstmt.setObject(paramIndex, paramObject);
		} else if (paramObject != null && paramObject instanceof Date) {
			paramObject = new Timestamp(((Date) paramObject).getTime());
		}
		pstmt.setObject(paramIndex, paramObject);
	}

	/**
	 * 创建时间 2010-11-4 上午11:25:58<br>
	 * 描述：
	 * 
	 * @param rs
	 * @param indexColumn
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private Object getObject(ResultSet rs, int indexColumn) throws SQLException, IOException {
		if (rs != null) {
			Object obj = rs.getObject(indexColumn);
			if (obj != null && obj instanceof Blob) {
				obj = FileUtil.toByteArray(((Blob) obj).getBinaryStream());
			}
			return obj;
		} else {
			return null;
		}
	}

	/**
	 * 利用预编译HQL查询，如果查询不到，返回新的ArrayList，不返回NullPoint。
	 * 
	 * @param hql
	 *            HQL语句
	 * @param objects
	 *            变量绑定参数
	 * @return List 返回对象实体List
	 */
	public List getEntityListHQL(String hql, Object... objects) throws OPException {
		return retrieveEntityListHQL(hql, -1, 0, objects);
	}

	/**
	 * 利用预编译HQL查询，如果查询不到，返回新的ArrayList，不返回NullPoint。
	 * 
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            第一页从0开始 ,小于0不分页
	 * @param pageSize
	 *            每页显示数量
	 * @param objects
	 *            变量绑定参数
	 * @return List 返回对象实体List
	 */
	public List retrieveEntityListHQL(String hql, int page, int pageSize, Object... objects) throws OPException {
		Session session = null;
		try {
			session = getSession();
			Query query = session.createQuery(hql);
			int i = 0;
			for (Object object : objects) {
				query.setParameter(i++, object);
			}
			if (page >= 0) {
				query.setFirstResult(pageSize * page);
				query.setMaxResults(pageSize);
			}
			query.setCacheable(true);
			query.setCacheRegion("frontpages");
			List result = query.list();
			if (result == null) {
				return emptyList;
			} else {
				return result;
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * ********************************以下部分支持jdbc******************************* ***
	 */

	/**
	 * 调用无返回值的存储过程
	 * 
	 * @param sql
	 *            eg: afc_dproc(?,?)
	 * @param objects
	 * @return
	 */
	public boolean callProcedure(String sql, Object... objects) {
		Connection conn = null;
		CallableStatement proc = null;
		try {
			conn = getConnection();
			proc = conn.prepareCall("{call " + sql + " }");
			int i = 1;
			for (Object object : objects) {
				setObject(proc, i, object);
				i++;
			}
			// proc.registerOutParameter(parameterIndex, sqlType);
			proc.execute();
		} catch (Exception e) {
			throw new ApplicationException("调用过程失败。", e);
		} finally {
			try {
				if (proc != null) {
					proc.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("调用过程失败。", e);
			}
		}
		return true;
	}

	/**
	 * @param sql
	 * @param returnType
	 * @return
	 * @throws OPException
	 */
	public Object execStoredFunction(String sql, int returnType) throws OPException {
		return DbUtility.execStoredFunction(getConnection(), sql, returnType);
	}

	public List execStoredFunctionForList(String sql) throws OPException {
		return DbUtility.execStoredFunctionForList(getConnection(), sql);
	}

	public void execStoredProcedure(String sql) throws OPException {
		DbUtility.execStoredProcedure(getConnection(), sql);
	}

	public void execSqlUpdate(String[] sqls) throws OPException {
		DbUtility.execUpdate(getConnection(), sqls);
	}

	public void execSqlUpdate(String sqls, List<Object[]> objList) throws OPException {
		DbUtility.execUpdate(getConnection(), sqls, objList);
	}

	public void execSqlUpdate(String sql) throws OPException {
		DbUtility.execUpdate(getConnection(), sql);
	}

	public List execSqlQueryList(String sql) throws OPException {
		return DbUtility.execQueryList(getConnection(), sql);
	}

	public int getSqlCount(String sql) throws OPException {
		return DbUtility.getRowCount(getConnection(), sql);
	}

	public byte[] getDataStream(String sql) throws OPException {
		return DbUtility.getDataStream(getConnection(), sql);
	}

	// public InputStream getOracleBlob(String sql) throws OPException {
	// return DbUtility.getOracleBlob(getConnection(), sql);
	// }

	@SuppressWarnings("deprecation")
	public Connection getConnection() throws OPException {
		return getSession().connection();
	}

	/**
	 * 根据sql获取列表
	 * 
	 * @param sql
	 *            sql
	 * @param objects
	 *            变量绑定参数
	 * @return list Object[]对象列表
	 * @throws OPException
	 */
	public int execSqlUpdate(String sql, Object... objects) throws OPException {

		Connection conn = null;
		int rs = 0;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			int i = 0;
			for (Object object : objects) {
				setObject(pstmt, ++i, object);
			}
			rs = pstmt.executeUpdate();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
			}
		}
		return rs;
	}

	/**
	 * @param sql
	 *            要导出的查询语句
	 * @param exportFile
	 *            要导出的文件
	 * @param append
	 *            是否加在文件末尾
	 * @return Future<ImExportResult> 将来的结果 描述：注意：这个方法只能用在mysql的数据库中
	 * @throws OPException
	 */
	public Future<ImExportResult> exportMySQLTableToCSVFile(final String sql, final File exportFile,
			final boolean append, final boolean includeColumnNames) throws OPException {
		Future<ImExportResult> imExportResult = executor.submit(new Callable<ImExportResult>() {
			public ImExportResult call() throws Exception {
				CSVWriter writer = null;
				Statement stmt = null;
				ResultSet rs = null;
				StatelessSession session = getSessionFactory().openStatelessSession();
				Connection conn = null;
				try {
					session = getSessionFactory().openStatelessSession();
					conn = session.connection();
					if (!conn.getMetaData().getDriverName().startsWith("MySQL")) {
						throw new OPException("只适用于mysql数据库。");
					}
					logger.debug("开始导出MYSQL数据");
					writer = new CSVWriter(new BufferedWriter(new FileWriter(exportFile, append)));
					ImExportResult er = new ImExportResult();
					Date startTime = new Date();
					stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					stmt.setFetchSize(Integer.MIN_VALUE);
					rs = stmt.executeQuery(sql);
					long rowCount = writer.writeAll(rs, includeColumnNames);
					Date endTime = new Date();
					er.setElapseTime(endTime.getTime() - startTime.getTime());
					er.setSucRowCount(rowCount);
					logger.debug("导出MYSQL数据结束");
					return er;
				} catch (Exception e) {
					throw new OPException(e);
				} finally {
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					if (writer != null) {
						writer.close();
					}
					if (session != null) {
						session.close();
					}
				}
			}
		});
		return imExportResult;
	}

	/**
	 * @param sql
	 *            要导出的预编译的查询语句
	 * @param exportFile
	 *            要导出的文件
	 * @param append
	 *            是否加在文件末尾
	 * @param includeColumnNames
	 *            是否包含table列名
	 * @param colNameMap
	 *            列命名规则
	 * @param colValPattern
	 *            列值规则
	 * @param paras
	 *            查询语句的参数
	 * @return Future<ImExportResult> 将来的结果
	 * @throws OPException
	 *             描述：
	 */
	private Future<ImExportResult> exportMySQLTableToCSVFile(final String preparedSql, final File exportFile,
			final boolean append, final boolean includeColumnNames, final Map<String, String> colNameMap,
			final Map<String, ColumnValuePattern> colValPattern, final Object... paras) throws OPException {
		Future<ImExportResult> imExportResult = executor.submit(new Callable<ImExportResult>() {
			public ImExportResult call() throws Exception {
				PreparedStatement pstmt = null;
				CSVWriter writer = null;
				ResultSet rs = null;
				StatelessSession session = null;
				Connection conn = null;
				try {
					session = getSessionFactory().openStatelessSession();
					conn = session.connection();
					ImExportResult er = new ImExportResult();
					Date startTime = new Date();
					logger.debug("开始导出MySQL数据");
					writer = new CSVWriter(new BufferedWriter(new FileWriter(exportFile, append)));
					pstmt = conn.prepareStatement(preparedSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					pstmt.setFetchSize(Integer.MIN_VALUE);
					int c = 1;
					for (Object object : paras) {
						pstmt.setObject(c++, object);
					}
					rs = pstmt.executeQuery();
					long rowCount = writer.writeAll(rs, includeColumnNames, colNameMap, colValPattern);
					writer.flush();
					Date endTime = new Date();
					er.setElapseTime(endTime.getTime() - startTime.getTime());
					er.setSucRowCount(rowCount);
					logger.debug("导出MySQL数据结束");
					return er;
				} catch (Exception e) {
					throw new OPException(e);
				} finally {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					if (writer != null) {
						writer.close();
					}
					if (session != null) {
						session.close();
					}
				}

			}
		});
		return imExportResult;
	}

	/**
	 * @param loadFile
	 *            需要导入的CSV 文件
	 * @param tableName
	 *            需要导入的table名称
	 * @param batchSize
	 *            批处理参数 一般 10-20之间
	 * @param commitEveryBatch
	 *            每次批处理是否提交 不提交可以提高性能，提交可以提高数据库并发性
	 * @return 导入统计报告
	 * @throws OPException
	 *             描述：注意: 1.这个方法目前暂时只适用于oracle数据库 2.尽量减少主键冲突可以提高性能
	 */
	public Future<ImExportResult> loadCSVToOracleTable(final File loadFile, final String tableName, final int batchSize,
			final boolean commitEveryBatch) throws OPException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Future<ImExportResult> importResult = null;
		// could not get the connection here?
		importResult = executor.submit(new Callable<ImExportResult>() {
			public ImExportResult call() throws Exception {
				ResultSetMetaData rsmd = null;
				PreparedStatement ops = null;
				StatelessSession session = null;
				Connection conn = null;
				try {
					session = getSessionFactory().openStatelessSession();
					conn = session.connection();
					ImExportResult result = new ImExportResult();
					Date startDate = new Date();
					String[] newLine = null;
					logger.debug("开始导入ORACLE数据");
					CSVReader reader = new CSVReader(new BufferedReader(new FileReader(loadFile)));
					// 得到table 的结构
					rsmd = conn.createStatement().executeQuery("select * from " + tableName + " where 1=2 ")
							.getMetaData();
					// 插入的语句
					String insertSql = "insert into " + tableName + " values(?";
					for (int i = 0; i < rsmd.getColumnCount() - 1; i++) {
						insertSql += ",?";
					}
					insertSql += ")";
					ops = conn.prepareStatement(insertSql);
					// 每回合计数器
					int round = 0;
					// 被循环使用的cache
					String[][] dataCache = new String[batchSize][];
					// 从cache加入batch时读的起始位置
					int startIndex = 0;
					// 从文件读出一行压入cache的位置
					int cacheInIndex = 0;
					while (true) {
						newLine = reader.readNext();
						dataCache[cacheInIndex++ % batchSize] = newLine;
						if ((++round == batchSize) || (newLine == null)) {
							int upper = newLine == null ? round - 1 : round;
							for (int k = 0; k < upper; k++) {
								for (int j = 1; j <= rsmd.getColumnCount(); j++) {
									switch (rsmd.getColumnType(j)) {
									case Types.DATE:
										ops.setObject(j, new java.sql.Date(
												sdf.parse(dataCache[(startIndex + k) % batchSize][j - 1]).getTime()));
										break;
									default:
										ops.setObject(j, dataCache[(startIndex + k) % batchSize][j - 1]);
										break;
									}
								}
								ops.addBatch();
							}
							try {
								ops.executeBatch();
								result.addSuccess(ops.getUpdateCount());
								logger.debug("部分导入成功");
							} catch (BatchUpdateException e) {
								logger.error("批量导入异常", e);
								// 异常时跳过一条
								int suc = ops.getUpdateCount();
								cacheInIndex = startIndex;
								startIndex = suc + 1;
								round = batchSize - suc - 1;
								ops.clearBatch();
								result.addFail();
								result.addSuccess(suc);
								// comit 已经成功的
								if (commitEveryBatch) {
									conn.commit();
								}
								continue;
							}
							// comit 已经成功的
							if (commitEveryBatch) {
								conn.commit();
							}
							cacheInIndex = startIndex;
							round = 0;
						}
						if (newLine == null) {
							break;
						}
					}
					Date endDate = new Date();
					result.setElapseTime(endDate.getTime() - startDate.getTime());
					logger.debug("导入ORACLE数据结束");
					return result;
				} catch (Exception e) {
					throw new OPException(e);
				} finally {
					if (ops != null) {
						ops.close();
					}
					if (session != null) {
						session.close();
					}
				}
			}
		});
		return importResult;

	}

	/**
	 * @param preparedSql
	 *            preparedSQL
	 * @param exportFile
	 *            导出的文件
	 * @param append
	 *            是否加在文件末尾
	 * @param includeColumnNames
	 *            是否包含表结构
	 * @param colNameMap
	 *            列命名规则 列名必须大写
	 * @param colValPattern
	 *            列值规则 列名必须大写
	 * @param paras
	 *            prepared sql 的参数
	 * @return 导入导出的结果
	 * @throws OPException
	 *             描述：这个方法把数据库表导出到csv文件，目前支持mysql和oracle
	 */
	public Future<ImExportResult> exportTableToCSVFile(String preparedSql, File logExportFile, boolean append,
			boolean includeColumnNames, final Map<String, String> colNameMap,
			final Map<String, ColumnValuePattern> colValPattern, final Object... paras) throws OPException {
		StatelessSession session = null;
		Connection conn = null;
		try {
			session = getSessionFactory().openStatelessSession();
			conn = session.connection();
			String driverName = conn.getMetaData().getDriverName();
			logger.debug("数据库驱动信息:" + driverName);
			if (driverName.startsWith("MySQL")) {
				return exportMySQLTableToCSVFile(preparedSql, logExportFile, append, includeColumnNames, colNameMap,
						colValPattern, paras);
			} else if (driverName.startsWith("Oracle")) {
				return exportOracleTableToCSVFile(preparedSql, logExportFile, append, includeColumnNames, colNameMap,
						colValPattern, paras);
			} else if (driverName.startsWith("PostgreSQL")) {
				return exportPostgreSQLTableToCSVFile(preparedSql, logExportFile, append, includeColumnNames,
						colNameMap, colValPattern, paras);
			} else {
				throw new OPException("此方法只支持mysql,postgresql和oracle数据库。");
			}
		} catch (Exception e) {
			logger.error("数据库表导出到csv文件异常", e);
			throw new OPException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * @param preparedSql
	 * @param logExportFile
	 * @param append
	 * @param includeColumnNames
	 * @param paras
	 * @return
	 * @throws OPException
	 *             具体参见 exportTableToCSVFile(String preparedSql,logExportFile, append,
	 *             includeColumnNames, null, null, final Object... paras)
	 */
	public Future<ImExportResult> exportTableToCSVFile(String preparedSql, File logExportFile, boolean append,
			boolean includeColumnNames, final Object... paras) throws OPException {
		return exportTableToCSVFile(preparedSql, logExportFile, append, includeColumnNames, null, null, paras);
	}

	/**
	 * @param preparedSql
	 *            preparedSQL
	 * @param exportFile
	 *            导出的文件
	 * @param append
	 *            是否加在文件末尾
	 * @param includeColumnNames
	 *            是否包含表结构
	 * @param colNameMap
	 *            列命名规则
	 * @param colValPattern
	 *            列值规则
	 * @param paras
	 *            prepared sql 的参数
	 * @return 导入导出的结果
	 * @throws OPException
	 *             描述：这个方法把postgresql的表导出到CSV 文件
	 */
	private Future<ImExportResult> exportPostgreSQLTableToCSVFile(final String preparedSql, final File exportFile,
			final boolean append, final boolean includeColumnNames, final Map<String, String> colNameMap,
			final Map<String, ColumnValuePattern> colValPattern, final Object... paras) throws OPException {
		Future<ImExportResult> imExportResult = executor.submit(new Callable<ImExportResult>() {
			public ImExportResult call() throws Exception {
				CSVWriter writer = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				StatelessSession session = null;
				Connection conn = null;
				try {
					session = getSessionFactory().openStatelessSession();
					conn = session.connection();
					ImExportResult er = new ImExportResult();
					Date startTime = new Date();
					logger.debug("开始导出PostgreSQL数据");
					writer = new CSVWriter(new BufferedWriter(new FileWriter(exportFile, append)));
					// 设置 connection 不是autocommit的以支持 cursor
					conn.setAutoCommit(false);
					pstmt = conn.prepareStatement(preparedSql);
					int c = 1;
					for (Object object : paras) {
						pstmt.setObject(c++, object);
					}
					pstmt.setFetchSize(1000);
					rs = pstmt.executeQuery();
					long rowCount = writer.writeAll(rs, includeColumnNames, colNameMap, colValPattern);
					writer.flush();
					Date endTime = new Date();
					er.setElapseTime(endTime.getTime() - startTime.getTime());
					er.setSucRowCount(rowCount);
					logger.debug("导出PostgreSQL数据结束");
					return er;
				} catch (Exception e) {
					throw new OPException(e);
				} finally {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					if (writer != null) {
						writer.close();
					}
					if (session != null) {
						session.close();
					}
				}
			}
		});
		return imExportResult;
	}

	/**
	 * @param preparedSql
	 *            preparedSQL
	 * @param exportFile
	 *            导出的文件
	 * @param append
	 *            是否加在文件末尾
	 * @param includeColumnNames
	 *            是否包含表结构
	 * @param colNameMap
	 *            列命名规则
	 * @param colValPattern
	 *            列值规则
	 * @param paras
	 *            prepared sql 的参数
	 * @return 导入导出的结果
	 * @throws OPException
	 *             描述：这个方法把oracle的表导出到CSV 文件
	 */
	private Future<ImExportResult> exportOracleTableToCSVFile(final String preparedSql, final File exportFile,
			final boolean append, final boolean includeColumnNames, final Map<String, String> colNameMap,
			final Map<String, ColumnValuePattern> colValPattern, final Object... paras) throws OPException {
		Future<ImExportResult> imExportResult = executor.submit(new Callable<ImExportResult>() {
			public ImExportResult call() throws Exception {
				CSVWriter writer = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				StatelessSession session = null;
				Connection conn = null;
				try {
					session = getSessionFactory().openStatelessSession();
					conn = session.connection();
					ImExportResult er = new ImExportResult();
					Date startTime = new Date();
					logger.debug("开始导出Oracle数据");
					writer = new CSVWriter(new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(exportFile, append), "GBK")));
					pstmt = conn.prepareStatement(preparedSql);
					int c = 1;
					for (Object object : paras) {
						pstmt.setObject(c++, object);
					}
					rs = pstmt.executeQuery();
					long rowCount = writer.writeAll(rs, includeColumnNames, colNameMap, colValPattern);
					writer.flush();
					Date endTime = new Date();
					er.setElapseTime(endTime.getTime() - startTime.getTime());
					er.setSucRowCount(rowCount);
					logger.debug("导出Oracle数据结束");
					return er;
				} catch (Exception e) {
					throw new OPException(e);
				} finally {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					if (writer != null) {
						writer.close();
					}
					if (session != null) {
						session.close();
					}
				}
			}
		});
		return imExportResult;
	}

	/**
	 * @param logImprtFile
	 * @param tableName
	 * @return
	 * @throws OPException
	 */
	protected Future<ImExportResult> loadCSVFileToTable(File logImprtFile, String tableName) throws OPException {
		StatelessSession session = null;
		Connection conn = null;
		try {
			session = getSessionFactory().openStatelessSession();
			conn = session.connection();
			String driverName = conn.getMetaData().getDriverName();
			logger.debug("数据库驱动信息:" + driverName);
			if (driverName.startsWith("Oracle")) {
				return loadCSVToOracleTable(logImprtFile, tableName, 20, true);
			} else if (driverName.startsWith("MySQL")) {
				return loadCSVToMySQLTable(logImprtFile, tableName, 20, true);
			} else {
				throw new OPException("此方法只支持mysql和oracle数据库。");
			}
		} catch (SQLException e) {
			throw new OPException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 导入CSV文件到MYSQL数据库中
	 * 
	 * @param conn
	 * @param loadFile
	 * @param tableName
	 * @param batchSize
	 * @param commitEveryBatch
	 * @return
	 * @throws OPException
	 */
	private Future<ImExportResult> loadCSVToMySQLTable(final File loadFile, final String tableName, final int batchSize,
			final boolean commitEveryBatch) throws OPException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Future<ImExportResult> importResult = executor.submit(new Callable<ImExportResult>() {
			public ImExportResult call() throws Exception {
				PreparedStatement ops = null;
				StatelessSession session = null;
				Connection conn = null;
				try {
					session = getSessionFactory().openStatelessSession();
					conn = session.connection();
					conn.setAutoCommit(false);
					ImExportResult result = new ImExportResult();
					Date startDate = new Date();
					String[] newLine = null;
					logger.debug("开始导入MySQL数据");
					CSVReader reader = new CSVReader(new BufferedReader(new FileReader(loadFile)));
					// 得到table 的结构
					ResultSetMetaData rsmd = conn.createStatement()
							.executeQuery("select * from " + tableName + " where 1=2").getMetaData();
					// 插入的语句
					String insertSql = "insert into " + tableName + " values(?";
					for (int i = 0; i < rsmd.getColumnCount() - 1; i++) {
						insertSql += ",?";
					}
					insertSql += ")";
					// mysql PreparedStatement
					ops = conn.prepareStatement(insertSql);
					int count = 0;
					logger.debug("loadCSVToMySQLTable sql:" + insertSql);
					while (true) {
						newLine = reader.readNext();
						// 处理batch
						if ((count == batchSize) || (newLine == null)) {
							try {
								int[] re = ops.executeBatch();
								// 添加成功和失败的
								for (int element : re) {
									if (element == 1) {
										result.addSuccess(1);
									} else {
										result.addFail();
									}
								}
							} catch (BatchUpdateException e) {
								// 添加成功和失败的
								int[] re = e.getUpdateCounts();
								for (int element : re) {
									if (element == 1) {
										result.addSuccess(1);
									} else {
										result.addFail();
									}
								}
							} finally {
								if (commitEveryBatch) {
									conn.commit();
								}
							}
							if (newLine == null) {
								break;
							}
						}
						for (int j = 1; j <= rsmd.getColumnCount(); j++) {
							switch (rsmd.getColumnType(j)) {
							case Types.DATE:
								ops.setObject(j, new java.sql.Date(sdf.parse(newLine[j - 1]).getTime()));
								break;
							default:
								ops.setObject(j, newLine[j - 1]);
								break;
							}
						}
						ops.addBatch();
						count++;

					}
					Date endDate = new Date();
					result.setElapseTime(endDate.getTime() - startDate.getTime());
					logger.debug("导入MySQL数据结束");
					return result;
				} catch (Exception e) {
					throw new OPException(e);
				} finally {
					if (ops != null) {
						ops.close();
					}
					if (session != null) {
						session.close();
					}
				}
			}
		});
		return importResult;
	}
}