/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Ticket: 用户详情服务类
 * 提供用户详情的查询功能，主要包括用户权限、状态等信息
 *
 * @author xuzhemin
 * 2019/4/17 15:56
 */
public class AfcUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return new AfcUserDetails("",true);
    }
}
