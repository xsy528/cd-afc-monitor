package com.insigma.acc.wz.web.model.vo;

import com.insigma.acc.wz.web.exception.ErrorCode;

/**
 * Ticket: 接口返回结果
 *
 * @author xuzhemin
 * 2018-12-25:11:01
 */
public class Result<T> {

    private int code;
    private String message;
    private T data;

    private Result(ErrorCode errorCode,T data){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }
    public static <T> Result<T> success(T data){
        return new Result(ErrorCode.SUCCESS,data);
    }

    public static <T> Result<T> success(){
        return new Result(ErrorCode.SUCCESS,null);
    }

    public static <T> Result<T> error(ErrorCode errorCode){
        return new Result(errorCode,null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
