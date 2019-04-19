/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ticket:认证过滤器
 * 在这里拦截请求，从header中获取用户信息，用认证器获取完整的用户认证信息
 *
 * @author xuzhemin
 * 2019/4/17 15:42
 */
public class AfcAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfcAuthenticationFilter.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private AuthenticationManager authenticationManager;
    private final static String UNKNOW = "unknow";

    public AfcAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        if (authorization==null){
            SecurityContextHolder.clearContext();
            chain.doFilter(request,response);
            return;
        }
        JsonNode json = objectMapper.readTree(authorization);
        if (json==null){
            SecurityContextHolder.clearContext();
            chain.doFilter(request,response);
            return;
        }
        JsonNode userIdNode = json.get("userId");
        if (userIdNode==null){
            SecurityContextHolder.clearContext();
            chain.doFilter(request,response);
            return;
        }
        Long userId = userIdNode.longValue();
        AfcAuthentication afcAuthentication = new AfcAuthentication(userId,getIpAddr(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(afcAuthentication));
        chain.doFilter(request,response);
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !UNKNOW.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.contains(",")){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
