package com.insigma.afc.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.insigma.afc.monitor.exception.ErrorCode;

/**
 * Ticket: 接口返回结果
 *
 * @author xuzhemin
 * 2018-12-25:11:01
 */
public class Result<T> {

    public interface Base{}

    @JsonView(Base.class)
    private int code;
    @JsonView(Base.class)
    private String message;
    @JsonView(Base.class)
    private T data;

    /**
     * 序列化需要用到构造方法
     */
    public Result(){}

    private Result(ErrorCode errorCode, T data){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }
    public static <T> Result<T> success(T data){
        return new Result(ErrorCode.SUCCESS,data);
    }

    public static <T> Result<T> success(){
        return new Result(ErrorCode.SUCCESS,"");
    }

    public static <T> Result<T> error(ErrorCode errorCode){
        return new Result(errorCode,"");
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

    @JsonIgnore
    public boolean isSuccess(){
        return this.code==ErrorCode.SUCCESS.getCode();
    }
}
