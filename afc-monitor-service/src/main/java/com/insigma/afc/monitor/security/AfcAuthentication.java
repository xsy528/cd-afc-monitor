/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Ticket: 认证对象
 *
 * @author xuzhemin
 * 2019/4/17 15:22
 */
public class AfcAuthentication implements Authentication {

    /**
     *  用户id
     */
    private Long userId;

    /**
     * 详细信息
     */
    private Object details;

    /**
     * 是否已经认证过
     */
    private boolean authenticated;

    /**
     * ip地址
     */
    private String ip;

    public AfcAuthentication(Long userId,String ip) {
        this.userId = userId;
        this.ip = ip;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public String getIp() {
        return ip;
    }
}
