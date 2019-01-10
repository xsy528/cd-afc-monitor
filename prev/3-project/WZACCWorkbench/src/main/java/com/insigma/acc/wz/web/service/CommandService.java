package com.insigma.acc.wz.web.service;

import com.insigma.acc.wz.web.model.vo.Result;
import com.insigma.afc.monitor.action.CommandResult;

import java.util.List;

/**
 * Ticket:
 *
 * @author xuzhemin
 * 2019-01-04:13:59
 */
public interface CommandService {

    Result<List<CommandResult>> sendChangeModeCommand(List<Long> nodeIds, int command);

    Result<List<CommandResult>> sendModeQueryCommand(List<Long> nodeIds);

    Result<List<CommandResult>> sendTimeSyncCommand(List<Long> nodeIds);

    Result<List<CommandResult>> sendNodeControlCommand(List<Long> nodeIds, short command);

}
