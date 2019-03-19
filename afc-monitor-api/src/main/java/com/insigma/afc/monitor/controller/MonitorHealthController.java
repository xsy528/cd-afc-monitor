/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.insigma.afc.monitor.controller;

import com.insigma.afc.monitor.model.dto.Result;
import com.insigma.afc.monitor.healthIndicator.RegisterHealthIndicator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ticket:
 *
 * @author: xuzhemin
 * 2019/3/14 15:10
 */
@Api(tags = "健康检查")
@RestController
@RequestMapping("/monitor/health")
public class MonitorHealthController {

    private DataSourceHealthIndicator dataSourceHealthIndicator;
    private RegisterHealthIndicator registerHealthIndicator;

    @Autowired
    public MonitorHealthController(DataSourceHealthIndicator dataSourceHealthIndicator, RegisterHealthIndicator
            registerHealthIndicator) {
        this.dataSourceHealthIndicator = dataSourceHealthIndicator;
        this.registerHealthIndicator = registerHealthIndicator;
    }

    @ApiOperation("数据源状态")
    @PostMapping("/datasource")
    public Result<Health> datasource() {
        return Result.success(dataSourceHealthIndicator.health());
    }

    @ApiOperation("通讯状态")
    @PostMapping("/register")
    public Result<Health> register() {
        return Result.success(registerHealthIndicator.health());
    }
}
