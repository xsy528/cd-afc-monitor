package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.condition.CommandLogCondition;
import com.insigma.afc.monitor.model.entity.TmoCmdResult;
import org.springframework.data.domain.Page;

public interface CommandLogService {
    /**
     * 获得命令日志信息
     * @return
     */
    Page<TmoCmdResult> getCommandLogSearch(CommandLogCondition condition);
}
