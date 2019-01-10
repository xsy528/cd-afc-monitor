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
    REQUIRED_PARAMETER_NOT_FOUND(2,"缺少必要属性"),
    NO_NODE_SELECT(3,"没有选择节点"),
    NO_MODE_SELECT(4,"没有选择运行模式"),
    MODE_NOT_EXISTS(5,"模式不存在"),
    NOPERMISSION(6,"无权限"),
    COMMAND_SERVICE_NOT_CONNECTED(7,"通信服务器未连接"),
    READ_PARAMETER_ERROR(8,"读取请求参数异常"),
    THRESHOLD_INVALID(9,"报警阀值不得低于警告阀值，参数设置失败"),
    REFRESH_INTERVAL_INVALID(10,"刷新周期不能小于5秒"),
    UNKNOW_ERROR(100,"未知异常"),
    MONITOR_CONFIG_SAVE_ERROR(1000,"监控参数设置失败");


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
