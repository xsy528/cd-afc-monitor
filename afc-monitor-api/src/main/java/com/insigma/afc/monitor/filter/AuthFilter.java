/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.filter;

import com.insigma.afc.monitor.util.SecurityUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/4/4 17:56
 */
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String userId = request.getHeader("userId");
        if (userId==null){
            userId = "1";
        }
        SecurityUtils.setUserId(userId);
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
