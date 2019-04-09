/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.monitor.util;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/8 16:55
 */
public class SecurityUtils {
    private static ThreadLocal<String> users = ThreadLocal.withInitial(()->null);

    public static String getUserId(){
        return users.get();
    }

    public static void setUserId(String userId){
        users.set(userId);
    }
}
