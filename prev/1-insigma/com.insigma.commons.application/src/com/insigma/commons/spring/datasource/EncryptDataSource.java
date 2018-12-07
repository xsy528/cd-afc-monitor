/* 
 * 日期：2008-10-24
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.spring.datasource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * Ticket:
 * 
 * @author Jiangxf
 */
public class EncryptDataSource extends BasicDataSource {

	private Logger logger = Logger.getLogger(EncryptDataSource.class);

	private DefaultDataSourceProvider dataSourceProvider;

	public DefaultDataSourceProvider getDataSourceProvider() {
		return dataSourceProvider;
	}

	public void setDataSourceProvider(DefaultDataSourceProvider dataSourceProvider) {

		this.dataSourceProvider = dataSourceProvider;

		String pwd = dataSourceProvider.getPassword();
		String decryPassword = null;
		try {
			decryPassword = DESUtil.decrypt(pwd);
		} catch (Exception e) {
			logger.error("解密数据库密码失败。", e);
		}
		setUrl(dataSourceProvider.getURL());
		setUsername(dataSourceProvider.getUserName());
		setPassword(decryPassword);
	}

}
