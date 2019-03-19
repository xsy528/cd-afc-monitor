package com.insigma.afc.monitor.service;

import com.insigma.afc.monitor.model.dto.Result;

/**
 * Ticket:
 *
 * @author: xuzhemin
 * 2019/3/12 20:48
 */
public interface RegisterPingService {

    Result<Integer> isRegisterOnline();
}
