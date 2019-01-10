package com.insigma.acc.wz.web.controller;

import com.insigma.acc.wz.monitor.form.WZMonitorConfigInfo;
import com.insigma.acc.wz.web.exception.ErrorCode;
import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.acc.wz.web.service.MonitorConfigService;
import com.insigma.acc.wz.web.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ticket: 监控配置接口
 *
 * @author xuzhemin
 * 2019-01-09:14:37
 */
public class MonitorConfigController extends BaseMultiActionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorConfigController.class);

    static{
        methodMapping.put("/monitor/config/get","getMonitorConfig");
        methodMapping.put("/monitor/config/save","saveMonitorConfig");
    }

    private MonitorConfigService monitorConfigService;

    @Autowired
    public MonitorConfigController(MonitorConfigService monitorConfigService){
        this.monitorConfigService = monitorConfigService;
    }

    //获取监控配置信息
    public Result<WZMonitorConfigInfo> getMonitorConfig(){
        return monitorConfigService.getMonitorConfig();
    }

    //获取保存配置监控信息
    public Result<WZMonitorConfigInfo> saveMonitorConfig(HttpServletRequest request, HttpServletResponse response){
        WZMonitorConfigInfo wzMonitorConfigInfo;
        try {
            wzMonitorConfigInfo = JsonUtils.readObject(request.getInputStream(), WZMonitorConfigInfo.class);
        } catch (IOException e) {
            LOGGER.error("",e);
            return Result.error(ErrorCode.READ_PARAMETER_ERROR);
        }
        return monitorConfigService.save(wzMonitorConfigInfo);
    }
}
