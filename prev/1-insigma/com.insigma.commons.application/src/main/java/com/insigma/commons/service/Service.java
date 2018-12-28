package com.insigma.commons.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.TransactionException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import com.insigma.commons.application.IService;
import com.insigma.commons.application.ServiceResult;

public class Service implements IService {

	protected Log logger = LogFactory.getLog(getClass());

	protected ILogService logService;

	protected ICommonDAO commonDAO;

	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	protected ServiceResult handleExceptionType(Object data, Exception e) {
		logger.error("文件处理异常,异常类型: " + e.getCause(), e);
		logger.error("异常数据:" + ReflectionToStringBuilder.toString(data));
		if ((e.getClass() == CannotCreateTransactionException.class)
				|| (e.getClass() == TransactionSystemException.class)
				|| (e.getClass() == DataAccessResourceFailureException.class
						|| (e.getClass() == TransactionException.class))) {
			logger.error("数据库连接异常,需要重复处理。");
			return ServiceResult.RETRY;
		} else if (e.getClass() == DataIntegrityViolationException.class) {
			logger.error("保存数据重复。");
			return ServiceResult.REPEAT;
		} else {
			logger.error("未知异常。 ");
			return ServiceResult.FAIL;
		}
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

}
