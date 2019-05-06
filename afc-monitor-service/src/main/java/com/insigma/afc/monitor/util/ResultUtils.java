/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.util;

import com.insigma.afc.monitor.exception.ErrorCode;
import com.insigma.commons.model.dto.Result;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/5/6 16:14
 */
public class ResultUtils {

    /**
     * 组装结果
     * @param errorCode 错误码
     * @return 结果
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> getResult(ErrorCode errorCode){
        return Result.error(errorCode.getCode(),errorCode.getMessage());
    }
}
