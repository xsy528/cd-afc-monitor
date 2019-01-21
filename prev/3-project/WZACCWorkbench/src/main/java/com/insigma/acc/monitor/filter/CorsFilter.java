package com.insigma.acc.monitor.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2018-12-25:13:19
 */
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //允许所有请求源
        response.setHeader("Access-Control-Allow-Origin", "*");
        //允许请求的方法
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        //预检请求有效期（秒）
        response.setHeader("Access-Control-Max-Age", "3600");
        //允许的请求头
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
        //允许发送Cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
