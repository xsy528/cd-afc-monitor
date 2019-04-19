/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.util;


import com.insigma.afc.monitor.security.AfcAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/18 11:18
 */
public class SecurityUtils {

    /**
     * 获取认证对象
     * @return 认证对象
     */
    public static AfcAuthentication getAuthentication(){
        return (AfcAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户id
     * @return 用户id
     */
    public static Long getUserId(){
        return (Long)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取当前用户ip
     * @return 用户ip
     */
    public static String getIp(){
        return ((AfcAuthentication)SecurityContextHolder.getContext().getAuthentication()).getIp();
    }
}
