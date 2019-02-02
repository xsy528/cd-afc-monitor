package com.insigma.afc.monitor.exception.handler;

import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.afc.monitor.model.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
