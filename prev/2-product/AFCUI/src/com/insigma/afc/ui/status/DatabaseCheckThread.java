package com.insigma.afc.ui.status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.afc.constant.ApplicationKey;
import com.insigma.commons.application.Application;
import com.insigma.commons.service.ICommonDAO;
import com.insigma.commons.thread.EnhancedThread;

public class DatabaseCheckThread extends EnhancedThread {

	private static Log logger = LogFactory.getLog(DatabaseCheckThread.class);

	public static final int NORMAL = 0;

	public static final int ABNORMAL = 1;

	public DatabaseCheckThread() {
		setImmune(true);
		setName("数据库检测线程");
		setInterval(1000 * 30);
	}

	@Override
	public void execute() {
		try {
			ICommonDAO commonDAO = (ICommonDAO) Application.getService(ICommonDAO.class);
			commonDAO.getEntityListHQL("from MetroLine");
			Application.setData(ApplicationKey.STATUS_DATABASE, NORMAL);
			// logger.debug("数据库正常");
		} catch (Exception e) {
			Application.setData(ApplicationKey.STATUS_DATABASE, ABNORMAL);
			logger.error("数据库异常", e);
		}
	}
}
