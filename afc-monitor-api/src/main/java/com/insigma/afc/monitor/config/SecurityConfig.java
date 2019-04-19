/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.config;

import com.insigma.afc.monitor.security.AfcAuthenticationFilter;
import com.insigma.afc.monitor.security.AfcAuthenticationProvider;
import com.insigma.afc.monitor.security.AfcUserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Ticket: 安全配置
 *
 * @author xuzhemin
 * 2019/4/17 14:41
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 不拦截的路径
     */
    private String[] ignorePaths = {"/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/swagger-ui.html",
            "/swagger-resources",
            "/swagger-resources/configuration/ui",
            "/swagge‌​r-ui.html"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                    .csrf().disable() //关闭csrf验证
                .addFilterBefore(new AfcAuthenticationFilter(authenticationManager()),BasicAuthenticationFilter.class);
    }

    /**
     * 构造认证管理器
     * @param auth 构造器
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AfcAuthenticationProvider(new AfcUserDetailsService()));
    }

    /**
     * 配置不拦截的请求
     * @param web WebSecurity
     * @throws Exception 异常
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(ignorePaths);
    }
}
