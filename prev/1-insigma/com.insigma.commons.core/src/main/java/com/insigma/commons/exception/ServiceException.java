package com.insigma.commons.exception;

/**
 * Ticket:服务异常
 *
 * @author xuzhemin
 * 2019-02-01:09:22
 */
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ServiceException(String message){
        super(message);
    }
}
