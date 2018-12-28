/* 
 * 日期：2011-10-18
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 * 
 */
package com.insigma.commons.config.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.commons.config.ConfigHelper;
import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.spring.datasource.DESPlus;

/**
 * Ticket: 
 * @author  DLF
 *
 */
public class DefaultDecryptAlgorithmHandler implements IDecryptAlgorithmHandler {

	private final Log log = LogFactory.getLog(DefaultDecryptAlgorithmHandler.class);

	private DESPlus plus;

	public final static String defaultkey = "*:@a$7!Z";

	public DefaultDecryptAlgorithmHandler() {
		initDESPlus();
	}

	private String loadKey(String key) {
		InputStream in = null;
		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		try {
			in = ConfigHelper.getUserResourceAsStream("AlgorithmKey.dat");
			reader = new InputStreamReader(in, "UTF-8");
			bufferedReader = new BufferedReader(reader);
			String readLine = bufferedReader.readLine();
			if (readLine != null) {
				DESPlus plus = new DESPlus(key);
				return plus.decrypt(readLine);
			}
		} catch (ApplicationException e) {
			log.warn("密钥文件未配置");
		} catch (Exception e) {
			log.error("获取密钥文件失败", e);
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error("", e);
				}
			}
		}
		return defaultkey;
	}

	private void initDESPlus() {
		try {
			String loadKey = loadKey(defaultkey);
			plus = new DESPlus(loadKey);
		} catch (Exception e) {
			log.error("初始化DESPlus异常,可能原因密钥错误，请重新生成密钥文件", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.insigma.commons.config.xml.IDecryptAlgorithmListener#decrypt(java.lang.String)
	 */
	public String decrypt(String encryptStr) {
		if (encryptStr != null) {
			String[] split = encryptStr.split(":");
			if (split.length > 1 && split[0].equalsIgnoreCase("DES")) {
				String result = encryptStr.substring(4);
				try {
					return plus.decrypt(result);
				} catch (Exception e) {
					log.error("解密字符串：<" + result + ">异常。返回未解密字符：" + result, e);
				}
			}
		}
		return encryptStr;
	}

}
