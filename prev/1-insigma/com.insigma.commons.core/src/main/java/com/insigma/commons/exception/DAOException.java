package com.insigma.commons.exception;

/**
 * Ticket: dao层处理异常
 *
 * @author xuzhemin
 * 2019-01-11:11:56
 */
public class DAOException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public DAOException(String message,Throwable throwable){
        super(message,throwable);
    }
}
