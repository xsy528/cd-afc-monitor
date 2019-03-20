package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.CommandLogCondition;
import com.insigma.afc.monitor.model.entity.TmoCmdResult;
import org.springframework.data.domain.Page;

public interface CommandLogService {
    /**
     * 获得命令日志信息
     * 注意：使用此接口时，如果开始时间与结束时间相同，请将结束时间加一秒
     * 因为数据库中时间精确到了毫秒
     * @return
     */
    Page<TmoCmdResult> getCommandLogSearch(CommandLogCondition condition);
}
