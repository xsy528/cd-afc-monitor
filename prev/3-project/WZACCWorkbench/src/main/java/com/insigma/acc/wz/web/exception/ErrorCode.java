package com.insigma.acc.wz.web.exception;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2018-12-25:11:06
 */
public enum ErrorCode {

    SUCCESS(0,"成功"),
    RESOURCE_NOT_FOUND(1,"未找到资源"),
    REQUIRED_PARAMETER_NOT_FOUND(2,"缺少必要属性");

    private int code;
    private String message;

    ErrorCode(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
