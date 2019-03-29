package com.insigma.afc.monitor.exception;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.commons.exception.NodeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-24:11:35
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public Result defaultExceptionHandler(Throwable e){
        LOGGER.error("捕获到未处理异常",e);
        return Result.error(ErrorCode.UNKNOW_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        LOGGER.error("不支持的媒体类型",e);
        return Result.error(ErrorCode.MEDIATYPE_NOT_SUPPORT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result httpMediaTypeNotSupportedException(HttpRequestMethodNotSupportedException e){
        LOGGER.error("不支持的请求方法",e);
        return Result.error(ErrorCode.REQUEST_METHOD_NOT_SUPPORT);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result missingServletRequestParameterException(MissingServletRequestParameterException e){
        LOGGER.error("请求缺少必要参数",e.getMessage());
        return Result.error(ErrorCode.REQUIRED_PARAMETER_NOT_FOUND);
    }

    @ExceptionHandler(BindException.class)
    public Result bindException(BindException e){
        Object target = e.getTarget();
        String className;
        if (target==null){
            className = e.getObjectName();
        }else{
            className = target.getClass().getTypeName();
        }
        logValidError(e.getFieldErrors(),className);
        return Result.error(ErrorCode.INVALID_ARGUMENT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result illegalArgumentException(IllegalArgumentException e){
        LOGGER.error("参数异常",e);
        return Result.error(ErrorCode.INVALID_ARGUMENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e){
        Object target = e.getBindingResult().getTarget();
        String className;
        if (target==null){
            className = e.getBindingResult().getObjectName();
        }else{
            className = target.getClass().getTypeName();
        }
        logValidError(e.getBindingResult().getFieldErrors(),className);
        return Result.error(ErrorCode.INVALID_ARGUMENT);
    }

    private void logValidError(List<FieldError> fieldErrorList, String objectName){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("数据类:"+objectName);
        for(FieldError fieldError:fieldErrorList){
            stringBuilder.append(",[字段:"+fieldError.getField());
            stringBuilder.append(",错误信息:"+fieldError.getDefaultMessage());
            stringBuilder.append(",字段值:"+fieldError.getRejectedValue());
            stringBuilder.append("]");
        }
        LOGGER.error("请求参数绑定异常."+stringBuilder.toString());
    }

    @ExceptionHandler(NodeNotFoundException.class)
    public Result nodeNotFoundException(NodeNotFoundException e){
        LOGGER.error(e.getMessage());
        return Result.error(ErrorCode.NODE_NOT_EXISTS);
    }
}
