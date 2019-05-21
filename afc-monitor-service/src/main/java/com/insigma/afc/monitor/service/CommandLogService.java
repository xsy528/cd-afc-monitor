package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.CommandLogCondition;
import com.insigma.afc.monitor.model.vo.CommandLogInfo;
import org.springframework.data.domain.Page;

/**
 * Tiket: 命令日志相关服务接口
 * @author  xingshaoya
 */
public interface CommandLogService {
    /**
     * 获得命令日志信息
     * 注意：使用此接口时，如果开始时间与结束时间相同，请将结束时间加一秒
     * 因为数据库中时间精确到了毫秒
     * @param  condition 命令日志查询条件实体类
     * @return 分页数据
     */
    Page<CommandLogInfo> getCommandLogSearch(CommandLogCondition condition);
}
