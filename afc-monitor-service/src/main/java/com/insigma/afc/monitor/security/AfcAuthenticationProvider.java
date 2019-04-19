/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Ticket: 认证器
 * 根据传进来的认证对象做对象认证
 *
 * @author xuzhemin
 * 2019/4/17 15:08
 */
public class AfcAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public AfcAuthenticationProvider(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Long userId = (Long)authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(userId));
        AfcAuthentication afcAuthentication = (AfcAuthentication)authentication;
        afcAuthentication.setDetails(userDetails);
        afcAuthentication.setAuthenticated(true);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
