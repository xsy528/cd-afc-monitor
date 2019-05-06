/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/*

 */
package com.insigma.afc.monitor.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019/5/6 11:08
 */
@ConfigurationProperties("health")
public class HealthProperties {

    /**
     * 定时检测任务执行延时
     */
    private Long initialDelay = 10L;
    /**
     * 检测任务执行延迟间隔
     */
    private Long fixedDelay = 10L;

    public Long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(Long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public Long getFixedDelay() {
        return fixedDelay;
    }

    public void setFixedDelay(Long fixedDelay) {
        this.fixedDelay = fixedDelay;
    }
}
